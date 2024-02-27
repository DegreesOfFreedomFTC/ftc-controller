package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Arm Encoder")
public class ArmEncoderStuff extends LinearOpMode {
    private DcMotor armLeft = null;
    private DcMotor armRight = null;

    private ElapsedTime runtime = new ElapsedTime();

    static final double ARM_COUNTS_PER_MOTOR_REV = 4;
    static final double ARM_GEAR_REDUCTION = 72;
    static final double SPOOL_DIAMETER_MILLIMETERS = 20;
    static final double ARM_COUNTS_PER_DEGREE = (ARM_COUNTS_PER_MOTOR_REV * ARM_GEAR_REDUCTION) / 360;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialised");
        telemetry.update();

        armLeft = hardwareMap.get(DcMotor.class, "armLeft");
        armRight = hardwareMap.get(DcMotor.class, "armRight");

        armLeft.setDirection(DcMotor.Direction.FORWARD);
        armRight.setDirection(DcMotor.Direction.REVERSE);
        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Right arm motor position", armRight.getCurrentPosition());
            telemetry.addData("Left arm motor position", armLeft.getCurrentPosition());
            telemetry.update();

            if (gamepad1.a) {
                armEncoderDrive(20);
            }
        }
    }

    private void armEncoderDrive(int counts) {
        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {
            newLeftTarget = armLeft.getCurrentPosition() + counts;
            newRightTarget = armRight.getCurrentPosition() + counts;

            telemetry.addData("Left Target Position", armLeft.getTargetPosition());
            telemetry.addData("Right Target Position", armRight.getTargetPosition());
            telemetry.update();

            armLeft.setTargetPosition(newLeftTarget);
            armRight.setTargetPosition(newRightTarget);

            armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            armLeft.setPower(Math.abs(0.8));
            armRight.setPower(Math.abs(0.8));

            while (opModeIsActive() &&
                    (armLeft.isBusy() && armRight.isBusy())) {
                // Do nothing. If anyone has a better idea make a PR
            }

            armLeft.setPower(0);
            armRight.setPower(0);

            armLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
