
package org.usfirst.frc.team2198.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CANTalon;
//import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.CameraServer;
//import edu.wpi.first.wpilibj.Timer;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
    Compressor c = new Compressor(0);
    
    DoubleSolenoid Lift = new DoubleSolenoid(0,1);
    DoubleSolenoid Stabalizer = new DoubleSolenoid(2, 3);
    
    boolean SolenoidSwitch = false;
    boolean button1Press = false;
    boolean button6Press = false;
	
	Joystick controller1 = new Joystick(0);
	
	Talon Collectormotor = new Talon (0);
	
	CANTalon FLeft = new CANTalon (2);
	CANTalon BLeft = new CANTalon (3);
	CANTalon FRight = new CANTalon (4);
	CANTalon BRight = new CANTalon (5);
	
	
	
	DigitalInput Pvalve = new DigitalInput(1);
	
    RobotDrive CANDrive = new RobotDrive (FLeft,BLeft,FRight,BRight);
		
    CameraServer server;
    
	void stackTote() {
		int time = 0;
		while(time <= 1400) {
			time++;
			Collectormotor.set(1);
			if(time >= 500) {
				lift(false);
		    	CANDrive.mecanumDrive_Cartesian(0, -0.52, 0, 0);
			}
		}
	}
	
	void lift(boolean k) {
		if(k)
			Lift.set(DoubleSolenoid.Value.kForward);
		else
			Lift.set(DoubleSolenoid.Value.kReverse);
	}
	
	void stablize(boolean k) {
		if(k)
			Stabalizer.set(DoubleSolenoid.Value.kForward);
		else
			Stabalizer.set(DoubleSolenoid.Value.kReverse);
	}
    
   /* void stackNextMovement(int time, double x, double y, double rotation){
    	for(int i = 0; i < 50 ; i++){
    		if(myMoveQueue[i] == null){
    			myMoveQueue[i] = new Stack(time, x, y, rotation);
    		}
    	}
    }
    
    void stackNextLift(int time, boolean stabilize, boolean lift, boolean collect){
    	for(int i = 0; i < 50 ; i++){
    		if(myLiftQueue[i] == null){
    			myLiftQueue[i] = new Stack(time, stabilize, lift, collect);
    		}
    	}
    }
	
	*/
	
    
    
    
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
     CANDrive.setInvertedMotor(MotorType.kFrontLeft, false);
   	 CANDrive.setInvertedMotor(MotorType.kRearLeft, false);
   	 CANDrive.setInvertedMotor(MotorType.kFrontRight, true);
   	 CANDrive.setInvertedMotor(MotorType.kRearRight, true);
 
   	 server = CameraServer.getInstance();
   	 server.setQuality(50);
   	 server.startAutomaticCapture("cam0");
   	 
   	 
   	 
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

   }
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	  	
    	while (true){
    		
    		if (controller1.getRawButton(3)) {               //brings the lift up if the X button is pushed
           	  Stabalizer.set(DoubleSolenoid.Value.kForward);
            }
             else if (controller1.getRawButton(2)) {      //brings the lift down if the B button is pressed
           	  Stabalizer.set(DoubleSolenoid.Value.kReverse);
            }
             
           	 
           	if((Math.abs(controller1.getAxis(Joystick.AxisType.kX)) > .25 || (Math.abs(controller1.getAxis(Joystick.AxisType.kY)) > .25 || (Math.abs(controller1.getRawAxis(4))) > .25)))
                   CANDrive.mecanumDrive_Cartesian(-controller1.getAxis(Joystick.AxisType.kX)*0.45, -controller1.getAxis(Joystick.AxisType.kY)*0.45,-controller1.getRawAxis(4)*0.45, 0);
           	else
				CANDrive.mecanumDrive_Cartesian(0,0,0, 0);
           	
           	if (controller1.getRawButton(6)) { 
				Lift.set(DoubleSolenoid.Value.kForward); 
           	}
           	else if (controller1.getRawButton(5)) {
           		Lift.set(DoubleSolenoid.Value.kReverse); 
           	}
           			  
           	double triggeraxis = ((controller1.getRawAxis(3)) - (controller1.getRawAxis(2) ));
	   	 
           	if(Math.abs(triggeraxis) >= 0.2)
           		 Collectormotor.set(triggeraxis);
           	  else
            		 Collectormotor.set(0);
           	  
           	  
           	if (controller1.getRawButton(9)) { 
           		stackTote();
           	}
	  
        }
    		
    }
    	
   
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    //	 int time = 0;
        while(true) {    
        	 
			if (controller1.getRawButton(3)) {
				Stabalizer.set(DoubleSolenoid.Value.kForward);
				  
			}
			else if (controller1.getRawButton(2)) {  
				Stabalizer.set(DoubleSolenoid.Value.kReverse);
			}

			if((Math.abs(controller1.getAxis(Joystick.AxisType.kX)) > .25 || (Math.abs(controller1.getAxis(Joystick.AxisType.kY)) > .25 || (Math.abs(controller1.getRawAxis(4))) > .25)))
					CANDrive.mecanumDrive_Cartesian(-controller1.getAxis(Joystick.AxisType.kX)*0.45, -controller1.getAxis(Joystick.AxisType.kY)*0.45,-controller1.getRawAxis(4)*0.45, 0);
				 else
				 CANDrive.mecanumDrive_Cartesian(0,0,0, 0);
        	
        	 
        	double triggeraxis = ((controller1.getRawAxis(3)) - (controller1.getRawAxis(2) ));
        	 
        	 
        	 
        	if (controller1.getRawButton(6)) {
        		Lift.set(DoubleSolenoid.Value.kForward); 
        	}
        	else if (controller1.getRawButton(5)) {
        		Lift.set(DoubleSolenoid.Value.kReverse); 
        	}
        			 
        	if(Math.abs(triggeraxis) >= 0.5)
        		Collectormotor.set(triggeraxis);
        	else
         		Collectormotor.set(0);
        }
 
    }
    
 }

