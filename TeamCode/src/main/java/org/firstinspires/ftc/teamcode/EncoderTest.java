package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Encoder Tests")
public class EncoderTest extends LinearOpMode {
    private DcMotor rightDrive = null;
    private DcMotor leftDrive = null;

    private ElapsedTime runtime = new ElapsedTime();

    static final double DRIVE_COUNTS_PER_MOTOR_REV = 28;
    static final double DRIVE_GEAR_REDUCTION = 20;
    static final double WHEEL_DIAMETER_MILLIMETERS = 90;
    static final double DRIVE_COUNTS_PER_DEGREE = (DRIVE_COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / 360;
    static final double DRIVE_COUNTS_PER_CENTIMETER = ((DRIVE_COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_MILLIMETERS * Math.PI)) * 10;
    static final double DRIVE_SPEED = 0.8;
    static final double TURN_SPEED = 0.6;

    static final double ARM_COUNTS_PER_MOTOR_REV = 4;
    static final double ARM_GEAR_REDUCTION = 72;
    static final double ARM_COUNTS_PER_DEGREE = (ARM_COUNTS_PER_MOTOR_REV * ARM_GEAR_REDUCTION) / 360;

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

        encoderDrive(TURN_SPEED, -65, 65, 5000);
    }

    private void encoderDrive(int degrees, double timeoutS) {
        int newRightTarget;
        int newLeftTarget;

        if (opModeIsActive()) {
            newRightTarget = rightDrive.getCurrentPosition() + (int) (degrees * DRIVE_COUNTS_PER_DEGREE);
            newLeftTarget = leftDrive.getCurrentPosition() + (int) (degrees * DRIVE_COUNTS_PER_DEGREE);
            rightDrive.setTargetPosition(newRightTarget);
            leftDrive.setTargetPosition(newLeftTarget);

            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();

            rightDrive.setPower(Math.abs(DRIVE_SPEED));
            leftDrive.setPower(Math.abs(DRIVE_SPEED));

            while (opModeIsActive() &&
                    ((runtime.seconds() < timeoutS) ||
                    (leftDrive.isBusy() || rightDrive.isBusy()))) {

                // Display it for the driver.
                telemetry.addData("Running to",  " %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
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

            while (opModeIsActive() && ((runtime.seconds() < timeoutS) || (leftDrive.isBusy() || rightDrive.isBusy()))) {
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
