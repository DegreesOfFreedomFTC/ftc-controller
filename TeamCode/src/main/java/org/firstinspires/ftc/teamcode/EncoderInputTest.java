package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Encoder Testing Idek")
public class EncoderInputTest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor rightDrive;
    private DcMotor leftDrive;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Started");

        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");

        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftDrive.setDirection(DcMotor.Direction.FORWARD);

        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        runtime.reset();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                rightDrive.setTargetPosition(560);
                leftDrive.setTargetPosition(560);

                rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                rightDrive.setPower(0.8);
                leftDrive.setPower(0.8);
            }

            telemetry.addData("Encoder position", "%7d :%7d", leftDrive.getCurrentPosition(), rightDrive.getCurrentPosition());
            telemetry.update();
        }
    }
}
