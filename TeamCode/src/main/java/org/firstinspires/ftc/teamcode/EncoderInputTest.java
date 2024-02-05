package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Encoder Testing Idek")
public class EncoderInputTest extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor rightDrive;
    private DcMotor leftDrive;

    @Override
    public void init() {
        telemetry.addData("Status", "Started");

        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");

        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftDrive.setDirection(DcMotor.Direction.FORWARD);

        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        System.out.println("Loop function started");
        if (gamepad1.a) {
            rightDrive.setTargetPosition(560);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setPower(0.8);
        }

        telemetry.addData("Right encoder position", rightDrive.getCurrentPosition());
        telemetry.update();

        System.out.println("Loop function ended");
    }
}
