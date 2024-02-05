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

    static final double COUNTS_PER_MOTOR_REV = 28;

    static final double DRIVE_GEAR_REDUCTION = 20;

    static final double WHEEL_DIAMETER_MILLIMETERS = 90;

    static final double COUNTS_PER_DEGREE = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION * 4) / 360;

    static final double COUNTS_PER_CENTIMETER = ((COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION * 4) / (WHEEL_DIAMETER_MILLIMETERS * Math.PI)) * 10;

    static final double DRIVE_SPEED = 0.6;

    @Override
    public void runOpMode() {
        // Initialise the motors
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");

        // Set directions
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        leftDrive.setDirection(DcMotor.Direction.REVERSE);

        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Starting at", "%7d :%7d",
                leftDrive.getCurrentPosition(),
                rightDrive.getCurrentPosition());
        telemetry.update();

        waitForStart();

        encoderDrive(360, 5);
        encoderDrive(180, 5);
        encoderDrive(90, 5);
        encoderDrive(45, 5);
    }

    private void encoderDrive(int degrees, double timeoutS) {
        int newRightTarget;
        int newLeftTarget;

        if (opModeIsActive()) {
            newRightTarget = rightDrive.getCurrentPosition() + (int) (degrees * COUNTS_PER_DEGREE);
            newLeftTarget = leftDrive.getCurrentPosition() + (int) (degrees * COUNTS_PER_DEGREE);
            rightDrive.setTargetPosition(newRightTarget);
            leftDrive.setTargetPosition(newLeftTarget);

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
            newRightTarget = rightDrive.getCurrentPosition() + (int) (rightCentimetres * COUNTS_PER_CENTIMETER);
            newLeftTarget = leftDrive.getCurrentPosition() + (int) (leftCentimetres * COUNTS_PER_CENTIMETER);
            rightDrive.setTargetPosition(newRightTarget);
            leftDrive.setTargetPosition(newLeftTarget);

            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();

            rightDrive.setPower(Math.abs(speed));
            leftDrive.setPower(Math.abs(speed));

            while (opModeIsActive() && ((runtime.seconds() < timeoutS) || (leftDrive.isBusy() || rightDrive.isBusy()))) {
                telemetry.addData("Running to", "%7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Curretnly at", "%7d : %7d",
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
