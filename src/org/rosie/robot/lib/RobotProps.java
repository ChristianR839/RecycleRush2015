/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotProps
{
    public static final String ADMIN_PASSWORD = "frc2015";

    static Properties props = new Properties();
    static File propsFile;

    //Load the properties file
    static
    {
        propsFile = new File("/home/lvuser/robotProps.properties");
        try
        {
            if(!propsFile.exists())        //If the file does not yet exist,
                propsFile.createNewFile(); //create it
            
            FileInputStream fIS = new FileInputStream(propsFile);
            props.load(fIS);
            fIS.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        SmartDashboard.putString("Password", "");                 //Initialize the Password and Log fields on the SmartDashboard
        SmartDashboard.putString("Log", "PW: " + ADMIN_PASSWORD); //and tell the user the password in the log field
    }

    /**
     * Save changes to the Properties file so that they are persisted upon reboot of the robot
     */
    public static void save()
    {
        try
        {
            FileOutputStream fOS = new FileOutputStream(propsFile); 
            props.store(fOS, "");
            fOS.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Gets the String from the Properties file
     * @param prop The property
     * @param def The default value
     * @return The property value as a String
     */
    public static String getString(String prop, String def)
    {
        return props.getProperty(prop, def);
    }

    /**
     * Gets the double from the Properties file
     * @param prop The property
     * @param def The default value
     * @return The property value as a double
     */
    public static double getDouble(String prop, double def)
    {
        return Double.parseDouble(props.getProperty(prop, "" + def));
    }

    /**
     * Gets the double from the Properties file
     * @param prop The property
     * @param def The default value
     * @return The property value as an integer
     */
    public static double getInteger(String prop, int def)
    {
        return Integer.parseInt(props.getProperty(prop, "" + def));
    }

    /**
     * Gets the boolean from the Properties file
     * @param prop The property
     * @param def The default value
     * @return The property value as an boolean
     */
    public static boolean getBoolean(String prop, boolean def)
    {
        return Boolean.parseBoolean(props.getProperty(prop, "" + def));
    }

    /**
     * Sets the String value in the Properties value
     * @param prop The property
     * @param value The value as a String
     */
    public static void setString(String prop, String value)
    {
        props.setProperty(prop, value);
    }

    /**
     * Sets the double value in the Properties value
     * @param prop The property
     * @param value The value as a double
     */
    public static void setDouble(String prop, double value)
    {
        props.setProperty(prop, "" + value);
    }

    /**
     * Sets the integer value in the Properties value
     * @param prop The property
     * @param value The value as an integer
     */
    public static void setInteger(String prop, int value)
    {
        props.setProperty(prop, "" + value);
    }

    /**
     * Sets the boolean value in the Properties value
     * @param prop The property
     * @param value The value as an boolean
     */
    public static void setBoolean(String prop, boolean value)
    {
        props.setProperty(prop, "" + value);
    }

    /**Checks the SmartDashboard if the correct password was set, and either clears it or puts "Incorrect Password!"*/
    public static boolean checkPassword()
    {
        String pw = SmartDashboard.getString("Password");
        SmartDashboard.putString("Password", ""); //Clear the password field so that it must be reentered

        if(pw.equals(ADMIN_PASSWORD))
            return true;
        else
        {
            log("Password invalid"); //The user gave an incorrect password, inform them.
            return false;
        }
    }

    /**Sets the Log field on the SmartDashboard in order to convey a short message*/
    public static void log(String toLog)
    {
        System.out.println("Log: " + toLog);
        SmartDashboard.putString("Log", toLog);
    }

    private RobotProps()
    {
        //Private as this class should be used statically
    }
}
