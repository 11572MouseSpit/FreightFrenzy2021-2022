package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HWProfile.HWProfile;
import org.firstinspires.ftc.teamcode.Libs.DriveMecanum;

@Autonomous(name = "Red Hub Bonus", group = "Competition")

public class AutoRedHubDouble extends LinearOpMode {

    private final static HWProfile robot = new HWProfile();
    private LinearOpMode opMode = this;
    private State state = State.RUN1;
    private ElapsedTime runtime = new ElapsedTime();

    public AutoRedHubDouble() {

    }   // end of AutoRedHubDouble constructor

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
                    // Pause for alliance partner
//                    sleep(5000);

                    // strafe towards hub
                    drive.driveTime(0.8, 90, 0.6);

                    // drive towards the hu-b
                    drive.motorsOn(-0.8, -0.8, -0.8, -0.8);
//                    drive.driveTime(0.8, 180, 0.6);

//                    sleep(500);

                    // move arm into scoring position
                    robot.motorArm.setTargetPosition(robot.ARMPOSITIONHIGH - 80);
                    robot.motorArm.setPower(0.55);

                    sleep(600);
                    drive.motorsHalt();

                    sleep(900);

                    // return arm to stationary position
                    robot.motorArm.setTargetPosition(0);
                    robot.motorArm.setPower(0.4);

//                    sleep(1000);

                    // drive towards wall
                    drive.driveTime(0.9, 0, 0.5);

                    // rotate towards warehouse
                    drive.driveTurn(-90, 0.3);

                    // strafe into the wall
                    drive.driveTime(0.6, 90, 0.5);

                    // rotate towards warehouse
                    drive.driveTurn(-88, 0.3);

                    // drive towards warehouse
                    drive.driveTime(0.8, 0, 1.6);

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

                    sleep(1000);

                    // assume elements captured
                    // set the cup to an upright position
                    robot.servoIntake.setPosition(robot.INTAKECUPUP);
                    sleep(50);

                    // Lift arm up
                    robot.motorArm.setTargetPosition(robot.ARMPOSITIONMID);
                    robot.motorArm.setPower(0.4);

                    // set intake to spit out any scoring elements
                    robot.motorIntake.setPower(-1);

                    //rotate to -90
                    drive.driveTurn(-90, 0.3);

                    //strafe into the wall
                    drive.driveTime(0.5, 90, 0.5);

                    // drive to scoring position
                    drive.driveTime(0.8, 180, 1.7);

                    // turn off the intake
                    robot.motorIntake.setPower(0);

                    // turn to score
                    drive.driveTurn(0, 0.3);

                    // drive towards the hub
                    drive.driveTime(0.5, 180, 0.9);

                    // move arm into scoring position
                    robot.motorArm.setTargetPosition(robot.ARMPOSITIONHIGH - 80);
                    robot.motorArm.setPower(0.5);

                    sleep(1000);

                    // return arm to stationary position
                    robot.motorArm.setTargetPosition(0);
                    robot.motorArm.setPower(0.4);

                    // drive towards wall
                    drive.driveTime(0.8, 0, 0.6);

                    // rotate towards warehouse
                    drive.driveTurn(-90, 0.3);

                    // strafe into the wall
                    drive.driveTime(0.6, 90, 0.5);

                    // rotate towards warehouse
                    drive.driveTurn(-90, 0.2);

                    // rotate towards warehouse
                    drive.driveTurn(-90, 0.3);

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
        }   // end of if opModeIsActive()

        // End the program
        requestOpModeStop();

    }// end of runOpMode constructor

    enum State {
        TEST, PLACE_SE, RUN1, BONUS, HALT
    }   // end of enum State

}   // end of class AutoRedHubDouble