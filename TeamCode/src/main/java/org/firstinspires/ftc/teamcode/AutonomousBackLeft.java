package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous Back Left")
public class AutonomousBackLeft extends LinearOpMode {
    private DcMotor rightDrive = null;
    private DcMotor leftDrive = null;

    private ElapsedTime runtime = new ElapsedTime();

    static final double DRIVE_COUNTS_PER_MOTOR_REV = 28;
    static final double DRIVE_GEAR_REDUCTION = 20;
    static final double WHEEL_DIAMETER_MILLIMETERS = 90;
    static final double DRIVE_COUNTS_PER_CENTIMETER = ((DRIVE_COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_MILLIMETERS * Math.PI)) * 10;
    static final double DRIVE_SPEED = 0.8;
    static final double TURN_SPEED = 0.6;

    @Override
    public void runOpMode() {
        // Initialise the motors
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");

        // Set directions
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftDrive.setDirection(DcMotor.Direction.FORWARD);

        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Starting at", "%7d :%7d",
                leftDrive.getCurrentPosition(),
                rightDrive.getCurrentPosition());
        telemetry.update();

        waitForStart();

        encoderDrive(TURN_SPEED, 29.85, -29.85, 0.5);
        encoderDrive(DRIVE_SPEED, 60.96, 60.96, 0.5);
        encoderDrive(TURN_SPEED, 29.85, -29.85, 0.5);
        encoderDrive(DRIVE_SPEED, -121.92, -121.92, 0.5);
        encoderDrive(TURN_SPEED, 29.85, -29.85, 0.5);
        encoderDrive(DRIVE_SPEED, 60.96, 60.96, 0.5);
        encoderDrive(TURN_SPEED, 14.9, -14.9, 0.5);
        encoderDrive(DRIVE_SPEED, 60.96, 60.96, 0.5);
        encoderDrive(TURN_SPEED, 14.9, -14.9, 0.5);
        encoderDrive(DRIVE_SPEED, 60.96, 60.96, 0.5);
    }

    public void encoderDrive(double speed, double leftCentimetres, double rightCentimetres, double timeoutS) {
        int newRightTarget;
        int newLeftTarget;

        if (opModeIsActive()) {
            newRightTarget = rightDrive.getCurrentPosition() + (int) (rightCentimetres * DRIVE_COUNTS_PER_CENTIMETER);
            newLeftTarget = leftDrive.getCurrentPosition() + (int) (leftCentimetres * DRIVE_COUNTS_PER_CENTIMETER);
            rightDrive.setTargetPosition(newRightTarget);
            leftDrive.setTargetPosition(newLeftTarget);

            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();

            rightDrive.setPower(Math.abs(speed));
            leftDrive.setPower(Math.abs(speed));

            while (opModeIsActive() && ((runtime.seconds() < timeoutS) || (leftDrive.isBusy() && rightDrive.isBusy()))) {
                telemetry.addData("Running to", "%7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Currently at", "%7d : %7d",
                        leftDrive.getCurrentPosition(), rightDrive.getCurrentPosition());

                telemetry.update();
            }

            rightDrive.setPower(0);
            leftDrive.setPower(0);

            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
}
