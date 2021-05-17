/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.lib;

import java.util.Arrays;

import edu.wpi.first.wpilibj.SPI;

////////////////////////////////////////////////////////////////////////////
// LEDStrip_SPI Class
//
// Used to interact with the AdaFruit LPD8806-based LED Strip (PRODUCT ID: 1948)
// 
// Taken from the AdaFruit Arduino library...
// --------------------------------------------------------------------------
// Clearing up some misconceptions about how the LPD8806 drivers work: 
//  
// The LPD8806 is not a FIFO shift register.  The first data out controls the 
// LED *closest* to the processor (unlike a typical shift register, where the 
//  first data out winds up at the *farthest* LED).  Each LED driver 'fills up' 
//  with data and then passes through all subsequent bytes until a latch 
//  condition takes place.  This is actually pretty common among LED drivers. 
//   
//  All color data bytes have the high bit (128) set, with the remaining 
//  seven bits containing a brightness value (0-127).  A byte with the high 
//  bit clear has special meaning (explained later). 
//   
//  The rest gets bizarre... 
//   
//  The LPD8806 does not perform an in-unison latch (which would display the 
//  newly-transmitted data all at once).  Rather, each individual byte (even 
//  the separate G, R, B components of each LED) is latched AS IT ARRIVES... 
//  or more accurately, as the first bit of the subsequent byte arrives and 
//  is passed through.  So the strip actually refreshes at the speed the data 
//  is issued, not instantaneously (this can be observed by greatly reducing 
//  the data rate).  This has implications for POV displays and light painting 
//  applications.  The 'subsequent' rule also means that at least one extra 
//  byte must follow the last pixel, in order for the final blue LED to latch. 
//   
//  To reset the pass-through behavior and begin sending new data to the start 
//  of the strip, a number of zero bytes must be issued (remember, all color 
//  data bytes have the high bit set, thus are in the range 128 to 255, so the 
//  zero is 'special').  This should be done before each full payload of color 
//  values to the strip.  Curiously, zero bytes can only travel one meter (32 
//  LEDs) down the line before needing backup; the next meter requires an 
//  extra zero byte, and so forth.  Longer strips will require progressively 
//  more zeros.  *(see note below) 
//   
//  In the interest of efficiency, it's possible to combine the former EOD 
//  extra latch byte and the latter zero reset...the same data can do double 
//  duty, latching the last blue LED while also resetting the strip for the 
//  next payload. 
//   
//  So: reset byte(s) of suitable length are issued once at startup to 'prime' 
//  the strip to a known ready state.  After each subsequent LED color payload, 
//  these reset byte(s) are then issued at the END of each payload, both to 
//  latch the last LED and to prep the strip for the start of the next payload 
//  (even if that data does not arrive immediately).  This avoids a tiny bit 
//  of latency as the new color payload can begin issuing immediately on some 
//  signal, such as a timer or GPIO trigger. 
//   
//  Technically these zero byte(s) are not a latch, as the color data (save 
//  for the last byte) is already latched.  It's a start-of-data marker, or 
//  an indicator to clear the thing-that's-not-a-shift-register.  But for 
//  conversational consistency with other LED drivers, we'll refer to it as 
//  a 'latch' anyway. 
//   
//  * This has been validated independently with multiple customers' 
//    hardware.  Please do not report as a bug or issue pull requests for 
//    this.  Fewer zeros sometimes gives the *illusion* of working, the first 
//    payload will correctly load and latch, but subsequent frames will drop 
//    data at the end.  The data shortfall won't always be visually apparent 
//    depending on the color data loaded on the prior and subsequent frames. 
//    Tested.  Confirmed.  Fact. 
//
////////////////////////////////////////////////////////////////////////////

public class LEDStrip_SPI
{


		private SPI 	m_spi;
		private int 	m_nNumLEDs = 48;
		private byte[] 	m_colorBuffer;

		/**
		 *  Constructor for LPD8806 LED strip
		 *  
		 *  @param spiPort 	The SPI port that the LED strip is connected to
		 *  @param nNumLEDS	The number of LEDs on the strip
		 */
		public LEDStrip_SPI( SPI.Port spiPort, int nNumLEDs) 
		{
			
			m_nNumLEDs = nNumLEDs;
			m_colorBuffer = new byte[ m_nNumLEDs * 3 ];
			
			m_spi      = new SPI(spiPort);
			init();
		}

		/**
		 * Free the SPI port
		 */
		public void free()
		{
			m_spi.free();
		}

		/**
		 * Set SPI bus parameters and set format
		 */
		private void init()
		{
			m_spi.setClockRate(2000000);
			m_spi.setMSBFirst();
			m_spi.setSampleDataOnFalling();
			m_spi.setClockActiveLow();
			m_spi.setChipSelectActiveHigh();

			resetLatch();
		}

		/**
		 * Latch's the data to be displayed and resets strip to allow
		 * next set of color data to be sent
		 */
		private void resetLatch()
		{
		
			byte[] aData = new byte[] { 0 };
			
			for( int i=((m_nNumLEDs+31)/32); i>0; i--) 
				m_spi.write( aData, aData.length);			
		}

		
		public void allOff()
		{
			Arrays.fill( m_colorBuffer, (byte)0x80 );
			updateLEDs();
		}
		
		/**
		 * Sets LED color using integer value (0x00RRGGBB)
		 * @param nLEDid
		 * @param nColor
		 */
		public void setColor( int nLEDid, int nColor ) 
		{
			setColor( nLEDid, nColor, true );
		} 

		/**
		 * Sets LED color using integer value (0x00RRGGBB)
		 * @param nLEDid
		 * @param nColor
		 * @param bUpdate 
		 */
		public void setColor( int nLEDid, int nColor, boolean bUpdate ) 
		{
			int nRed   = (nColor >> 16) & 0xFF;
			int nGreen = (nColor >>  8) & 0xFF;
			int nBlue  =  nColor        & 0xFF;
			
			setColor( nLEDid, nRed, nGreen, nBlue, bUpdate );
		} 

		/**
		 * Set's a single LED's color.  Updates immediately
		 * 
		 * @param nLEDid	LED Index starting at 0 to NumLEDs
		 * @param nRed		Red Color component 0-127
		 * @param nGreen	Green Color component 0-127
		 * @param nBlue		Blue Color component 0-127
		 */
		
		public void setColor( int nLEDid, int nRed, int nGreen, int nBlue )
		{
			setColor( nLEDid, nRed, nGreen, nBlue, true );
		}

		/**
		 * Set's a single LED's color.
		 * 
		 * @param nLEDid	LED Index starting at 0 to NumLEDs
		 * @param nRed		Red Color component 0-127
		 * @param nGreen	Green Color component 0-127
		 * @param nBlue		Blue Color component 0-127
		 * @param bUpdate	If true, updates LED strip with new color.  False only updated internal buffer.
		 */
		public void setColor( int nLEDid, int nRed, int nGreen, int nBlue, boolean bUpdate )
		{
			if ((nLEDid < 0) || (nLEDid >= m_nNumLEDs))
			{
				// Out of Range!
				return;
			}
		
			int nIdx = nLEDid * 3;
			
			m_colorBuffer[ nIdx + 0 ] = (byte)(0x80 | nGreen);
			m_colorBuffer[ nIdx + 1 ] = (byte)(0x80 | nRed  );
			m_colorBuffer[ nIdx + 2 ] = (byte)(0x80 | nBlue );

			if (bUpdate)
				updateLEDs();
		}
		
		/**
		 * Updates the LED strip with the colors from the internal buffer
		 */
		public void updateLEDs()
		{
			m_spi.write( m_colorBuffer, m_colorBuffer.length);			
			
			resetLatch();
		}

		
		/**
		 * Convert separate R,G,B into combined 32-bit GRB color:
		 * 
		 * @param r 	Red Color component 0-127
		 * @param g		Green Color component 0-127
		 * @param b		Blue Color component 0-127
		 * 
		 */
		public int Color( byte r, byte g, byte b) 
		{ 
			return ((int)(g | 0x80) << 16) | 
		           ((int)(r | 0x80) <<  8) | 
		                  b | 0x80 ; 
		} 
		
}