package frc.robot;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
//import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.POVButton;

public class Robot extends TimedRobot {

  // Controller Assignments //

  Joystick driverController = new Joystick(0);
  PS4Controller driverController2 = new PS4Controller(1);
  GenericHID functionController = new GenericHID(2);
  double autoStart = 0;

  POVButton povUp = new POVButton(driverController2, 0);
  //POVButton povRight = new POVButton(driverController2, 90);
  POVButton povDown = new POVButton(driverController2, 180);
  //POVButton povLeft = new POVButton(driverController2, 270);

  // Motors & Servo Assignments //

  VictorSPX driveRightB = new VictorSPX(1);
  VictorSPX driveRightA = new VictorSPX(2);
  VictorSPX driveLeftB = new VictorSPX(3);
  VictorSPX driveLeftA = new VictorSPX(4);

  //CANSparkMax slideA = new CANSparkMax(5, MotorType.kBrushed);
  //CANSparkMax slideB = new CANSparkMax(6, MotorType.kBrushed);

  CANSparkMax elevatorA = new CANSparkMax(7, MotorType.kBrushless);
  CANSparkMax elevatorB = new CANSparkMax(8, MotorType.kBrushless);

  // CANSparkMax clawMotor = new CANSparkMax(9, MotorType.kBrushless);1 

  //Servo boxServo = new Servo(9);
  
  // General Intialization //
   
  @Override
  public void robotInit() {

    CameraServer.startAutomaticCapture();
    SmartDashboard.putNumber("Joystick X value", driverController.getX());

  }
  
  // Autonomous Intialization //

  @Override
  public void autonomousInit() {

    autoStart = Timer.getFPGATimestamp();

  }

  // Autonomous Periodic //

  @Override
  public void autonomousPeriodic() {

    double autoTimeElapsed = Timer.getFPGATimestamp() - autoStart;
      
      Double autoCode = 1.0;
     
    if(autoCode == 1) {
      if(autoTimeElapsed<1.0) {

        driveLeftA.set(VictorSPXControlMode.PercentOutput, 0.5);
        driveLeftB.set(VictorSPXControlMode.PercentOutput, 0.5);
        driveRightA.set(VictorSPXControlMode.PercentOutput, -0.5);
        driveRightB.set(VictorSPXControlMode.PercentOutput, -0.5);

      }

      else {

        driveLeftA.set(VictorSPXControlMode.PercentOutput, 0);
        driveLeftB.set(VictorSPXControlMode.PercentOutput, 0);
        driveRightA.set(VictorSPXControlMode.PercentOutput, 0);
        driveRightB.set(VictorSPXControlMode.PercentOutput, 0);

      }

    } 

  }

  // Teleop Intialization //

  @Override
  public void teleopInit() {
  }

  // Autonomous Periodic //

  @Override
  public void teleopPeriodic() {

    // Driver Controller Assignments //

    //boolean crossButton = driverController2.getCrossButton();
    //boolean squareButton = driverController2.getSquareButton();
    //sboolean circleButton = driverController2.getCircleButton();
    //boolean triangleButton = driverController2.getTriangleButton();

    double slowturn = driverController.getRawAxis(2);
    double forward = driverController.getRawAxis(1);
    double turn = -driverController.getRawAxis(0);

    // Motor Controller Speed Assignments //
    
    driveLeftA.set(VictorSPXControlMode.PercentOutput, -(forward+(turn*0.65+(slowturn*-0.55))));
    driveLeftB.set(VictorSPXControlMode.PercentOutput, -(forward+(turn*0.65+(slowturn*-0.55))));
    driveRightA.set(VictorSPXControlMode.PercentOutput, forward-(turn*0.65-(slowturn*0.55)));
    driveRightB.set(VictorSPXControlMode.PercentOutput, forward-(turn*0.65-(slowturn*0.55)));

    // Elevator Controls //
    
    if (povUp.getAsBoolean()) {
      elevatorA.set(0.2);
      elevatorB.set(-0.2);
    } else if (povDown.getAsBoolean()) {
      elevatorA.set(-0.1);
      elevatorB.set(0.1);
    } else {
      elevatorA.set(0);
      elevatorB.set(0);
    }
    

   /*
   if (triangleButton) {
      slideA.set(0.2);
      slideB.set(-0.2);
    } else if (squareButton) {
      slideA.set(-0.2);
      slideB.set(0.2);
    } else {
      slideA.set(0);
      slideB.set(0);
    }
    */

    // box Servo Controls //
    /*
    if (crossButton) {
      boxServo.set(1);
    } else {
      boxServo.set(0.5);
    }
    */
  }
  // Disabled Intalization //
  
  @Override
  public void disabledInit() {

    // Disable Motors & Servos //

    driveLeftA.set(VictorSPXControlMode.PercentOutput,0);
    driveLeftB.set(VictorSPXControlMode.PercentOutput,0);
    driveRightA.set(VictorSPXControlMode.PercentOutput,0);
    driveRightB.set(VictorSPXControlMode.PercentOutput,0);

    //elevatorA.disable();
    //elevatorB.disable();

    //slideA.disable();
    //slideB.disable();

    //boxServo.setDisabled();

  }
}  
