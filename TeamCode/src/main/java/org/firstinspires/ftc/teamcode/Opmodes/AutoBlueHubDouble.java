package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HWProfile.HWProfile;
import org.firstinspires.ftc.teamcode.Libs.DriveMecanum;

@Autonomous(name = "Blue Hub Bonus", group = "Competition")
public class AutoBlueHubDouble extends LinearOpMode {

    private final static HWProfile robot = new HWProfile();
    private LinearOpMode opMode = this;
    private State state = State.RUN1;
    private ElapsedTime runtime = new ElapsedTime();

    public AutoBlueHubDouble() {

    }   // end of TestAuto constructor

    public void runOpMode() {
        telemetry.addData("Robot State = ", "READY");
        telemetry.update();

        /*
         * Setup the initial state of the robot
         */
        robot.init(hardwareMap);

        /*
         * Initialize the drive class
         */
        DriveMecanum drive = new DriveMecanum(robot, opMode);

        robot.servoIntake.setPosition(robot.INTAKECUPUP);
        telemetry.addData("Z Value = ", drive.getZAngle());
        telemetry.addData("Robot state = ", "INITIALIZED");
        telemetry.update();

        waitForStart();

        // reset the runtime clock
        runtime.reset();

        while(opModeIsActive()) {
            switch (state) {
                case TEST:

                    break;

                case RUN1:
                    // strafe away from the wall
//                    sleep(5000);

                    // strafe to scoring position
                    drive.driveTime(0.5, -90, 1);

                    // drive towards the alliance hub
                    drive.driveTime(.5, 180, 0.9);

                    // score the shipping element into the alliance hub
                    robot.motorArm.setTargetPosition(robot.ARMPOSITIONHIGH - 80);
                    robot.motorArm.setPower(0.4);

                    sleep(2000);

                    // return the arm to ready position
                    robot.motorArm.setTargetPosition(-10);
                    robot.motorArm.setPower(0.4);

                    sleep(1000);

                    // drive towards the outside wall
                    drive.driveTime(0.5, 0, .9);

                    // turn towards the warehouse
                    drive.driveTurn(90, 0.3);

                    // strafe into the wall
                    drive.driveTime(.5, -90, 1);

                    // drive towards the warehouse
                    drive.driveTime(.5, 0, 2.3);

                    // strafe from the wall to make room for another bot to park
                    drive.driveTime(0.5, 90, 1.2);

                    // lower the cup to intake more elements
                    robot.servoIntake.setPosition(robot.INTAKECUPDOWN);

                    if(opModeIsActive() && runtime.time() < 20) {
                        state = State.BONUS;
                    } else {
                        state = State.HALT;
                    }

                    break;

                case BONUS:
                    // turn on intake
                    robot.motorIntake.setPower(1);

                    // drive into the elements
                    drive.driveTime(0.4, 0, 1);

//                    sleep(1000);

                    // assume elements captured
                    // set the cup to an upright position
                    robot.servoIntake.setPosition(robot.INTAKECUPUP);
                    sleep(50);

                    // Lift arm up
                    robot.motorArm.setTargetPosition(robot.ARMPOSITIONMID);
                    robot.motorArm.setPower(0.4);

                    // set intake to spit out any scoring elements
                    robot.motorIntake.setPower(-1);

                    // straighten robot out
                    drive.driveTurn(90, 0.3);

                    //strafe into the wall
                    drive.driveTime(0.5, -90, 0.5);

                    // drive to scoring position
                    drive.driveTime(0.8, 180, 2);

                    // turn off the intake
                    robot.motorIntake.setPower(0);

                    // turn to score
                    drive.driveTurn(0, 0.3);

                    // drive towards the hub
                    drive.driveTime(0.5, 180, 0.9);

                    // move arm into scoring position
                    robot.motorArm.setTargetPosition(robot.ARMPOSITIONHIGH - 80);
                    robot.motorArm.setPower(0.4);

                    sleep(1000);

                    // return arm to stationary position
                    robot.motorArm.setTargetPosition(0);
                    robot.motorArm.setPower(0.4);

                    // drive towards wall
                    drive.driveTime(0.5, 0, 0.7);

                    // rotate towards warehouse
                    drive.driveTurn(90, 0.3);

                    // strafe into the wall
                    drive.driveTime(0.5, -90, 1);

                    // drive towards warehouse
                    drive.driveTime(0.8, 0, 1.5);

                    // lower the cup to intake more elements
                    robot.servoIntake.setPosition(robot.INTAKECUPDOWN);

                    if(runtime.time() > 25) {
                        state = State.HALT;
                    }
                    break;

                case HALT:

                    // Stop all motors
                    drive.motorsHalt();

                    // End the program
                    requestOpModeStop();

                    break;
            }   // end of the switch state
        }   // end of while opModeIsActive()

        // End the program
        requestOpModeStop();

    }// end of runOpMode constructor

    enum State {
        TEST, PLACE_SE, RUN1, BONUS, HALT
    }   // end of enum State

}   // end of class AutoBlueStorage