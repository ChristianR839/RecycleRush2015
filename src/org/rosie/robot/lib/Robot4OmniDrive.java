/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.lib;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Utility class for handling Robot drive based on a definition of the motor configuration.
 * The robot drive class handles basic driving for a robot.
 * This class is specific to the 4 omni "BlainTrain" drive train.
 */
public class Robot4OmniDrive extends RobotDrive
{
    private final SpeedController m_ForwardFront;
    private final SpeedController m_ForwardBack;
    private final SpeedController m_SidewaysFront;
    private final SpeedController m_SidewaysBack;

    /**
     * Creates a Robot4OmniDrive "Blain Train". This assumes that the forward facing motors go the same direction with the same input,
     * and that the sideways do the same.
     * @param frontLeftMotor
     * @param rearLeftMotor
     * @param frontRightMotor
     * @param rearRightMotor
     * @param isFLForward Set this to true if the Front Left and Back Right motors are the ones facing forward
     */
    public Robot4OmniDrive(SpeedController frontLeftMotor,
                           SpeedController rearLeftMotor,
                           SpeedController frontRightMotor,
                           SpeedController rearRightMotor,
                           boolean         isFLForward) 
    {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

        if(isFLForward)
        {
            m_ForwardFront  = frontLeftMotor;
            m_ForwardBack   = rearRightMotor;
            m_SidewaysFront = frontRightMotor;
            m_SidewaysBack  = rearLeftMotor;
        }
        else
        {
            m_ForwardFront  = frontRightMotor;
            m_ForwardBack   = rearLeftMotor;
            m_SidewaysFront = frontLeftMotor;
            m_SidewaysBack  = rearRightMotor;
        }
    }

    /**
     * Scale the input by squaring it.
     */
    private double scaleInput(double dPower)
    {
        return dPower * Math.abs(dPower);
    }

//    /**
//     * Drives the robot without rotating.
//     * @param gameController The controller.
//     * @param forwardAxis The axis used for forward input.
//     * @param sidewaysAxis The axis used for sideways input.
//     * @param squareInputs Set this to true if you want to square the inputs.
//     */
//    public void omniDrive(GenericHID gameController, int forwardAxis, int sidewaysAxis, boolean squareInputs)
//    {
//        omniDrive(gameController.getRawAxis(forwardAxis), gameController.getRawAxis(sidewaysAxis), squareInputs);
//    }
//
//    /**
//     * Drives the robot without rotating.
//     * @param forwardInput The input used for the forward facing motors.
//     * @param sidewaysInput The input used for the sideways facing motors.
//     * @param squareInputs Set this to true if you want to square the inputs.
//     */
//        public void omniDrive(double forwardInput, double sidewaysInput, boolean squareInputs)
//    {
//        forwardInput  = limit(forwardInput);  //Make sure the absolute of the values are not above 1
//        sidewaysInput = limit(sidewaysInput); //
//
//        if(squareInputs)
//        {
//            forwardInput  = scaleInput( forwardInput  ); //Square the inputs by multiplying them by themselves, and by -1 if they were negative at first
//            sidewaysInput = scaleInput( sidewaysInput ); //
//        }
//
//        //Set the forward facing motors to forwardInput
//        m_ForwardFront.set(forwardInput);
//        m_ForwardBack.set(-forwardInput);
//
//        //Set the sideways facing motors to sidewaysInput
//        m_SidewaysFront.set(sidewaysInput);
//        m_SidewaysBack.set(-sidewaysInput);
//    }

    /**
     * Stop
     */
    public void stop()
    {
        m_ForwardFront .set(0);
        m_ForwardBack  .set(0);
        m_SidewaysFront.set(0);
        m_SidewaysBack .set(0);

        if (m_safetyHelper != null)
            m_safetyHelper.feed();
    }

    public void omniDrive( double forwardInput, double sidewaysInput, double rotateInput, double currentAngle, double targetAngle,  boolean squareInput)
    {
        omniDrive(forwardInput, sidewaysInput, rotateInput, true, currentAngle, targetAngle, squareInput);
    }
    
    public void omniDrive( double forwardInput, double sidewaysInput, double rotateInput, boolean scaleRotate, double currentAngle, double targetAngle,  boolean squareInput)
    {
        omniDrive(forwardInput, sidewaysInput, rotateInput, scaleRotate, currentAngle, targetAngle, squareInput, false);
    }
    
    /**
     * Rotates the robot without driving.
     * @param rotateInput The input used for all motors, where the fronts move at rotateInput speed, and the backs at -rotateInputSpeed.
     * @param squareInput Set this to true to square rotateInput.
     */
    public void omniDrive( double forwardInput, double sidewaysInput, double rotateInput, boolean scaleRotate, double currentAngle, double targetAngle,  boolean squareInput, boolean scaleBackwards)
    {
        double dFwdFront = 0;
        double dFwdBack  = 0;
        double dSwFront  = 0;
        double dSwBack   = 0;

        // Make sure the absolute of the values are not above 1
        forwardInput  = limit(  forwardInput );   //
        sidewaysInput = limit( -sidewaysInput); // negate sideways (reversed) 
        rotateInput   = limit( -rotateInput  );    //

        if (squareInput)
        {
            forwardInput  = scaleInput(forwardInput ); //
            sidewaysInput = scaleInput(sidewaysInput); // Square the inputs by multiplying them by themselves, and by -1 if they were negative at first
            rotateInput   = scaleInput(rotateInput  ); //
        }

        //forwardInput  /= 2;
        //sidewaysInput /= 2;
        if(scaleRotate)
            rotateInput   /= 1.75;

        // rotational movement
        if (Math.abs(rotateInput) > 0.1)
        {
            //Set all motors to rotateInput
            dFwdFront = rotateInput;
            dFwdBack  = rotateInput;
            dSwFront  = rotateInput;
            dSwBack   = rotateInput;
        }
        // process directional movement  fwd/back
        if (Math.abs(forwardInput) > 0.1)
        {
            //Set the forward facing motors to forwardInput
            dFwdFront += forwardInput;
            dFwdBack  += -forwardInput;
        }

        // Process directional movement sides
        if(Math.abs(sidewaysInput) > 0.1)
        {
            //Set the sideways facing motors to sidewaysInput
            dSwFront  += sidewaysInput;
            dSwBack   += -sidewaysInput;
        }   

        // -=>TODO: IMU uses -180 to 180, do we need to change code?
        //Adjust for target angle using the gyro  
        double angleOffset = (targetAngle - (currentAngle % 180));

        if(Math.abs(angleOffset) > 0.0001)
        {
            if((Math.abs(forwardInput) > 0.1) && (Math.abs(sidewaysInput) < 0.1))
            {
                double anglePower  = -limit( ( angleOffset / 75 ) );

                dSwFront += anglePower;
                dSwBack  += anglePower;
            }
            else if((Math.abs(forwardInput) < 0.1) && (Math.abs(sidewaysInput) > 0.1))
            {
                double anglePower  = limit( ( angleOffset / 75 ) );

                dFwdFront -= anglePower;
                dFwdBack  -= anglePower;
            }
        }

        double scale = Math.max(Math.max(Math.abs(dFwdFront), Math.abs(dFwdBack)),
                                Math.max(Math.abs(dSwFront ), Math.abs(dSwBack )));
        
        //If scale is < 1, we do not want to change the values given to the motors, otherwise at least one will always be going at full speed
        if(scale > 1)
        {
            dFwdFront /= scale;
            dFwdBack  /= scale;
            dSwFront  /= scale;
            dSwBack   /= scale;
        }

        m_ForwardFront .set(dFwdFront                             );
        m_ForwardBack  .set(dFwdBack * (scaleBackwards ? 0.75 : 1));
        m_SidewaysFront.set(dSwFront                              );
        m_SidewaysBack .set(dSwBack  * (scaleBackwards ? 0.75 : 1));
        
        if(m_safetyHelper != null)
            m_safetyHelper.feed();
    }
    
    
}
