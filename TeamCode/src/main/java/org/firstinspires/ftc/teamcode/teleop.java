package org.firstinspires.ftc.teamcode;

import android.graphics.Path;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.drivetrain;
import org.firstinspires.ftc.teamcode.GamepadStates;

@TeleOp(name = "Teleop", group = "Teleop")
public class teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        TouchSensor Touch;
        Touch = hardwareMap.get(TouchSensor.class, "Touch");

        double speed = .5;

        drivetrain Drive = new drivetrain();

        GamepadStates newGamePad1 = new GamepadStates(gamepad1);
        GamepadStates newGamePad2 = new GamepadStates(gamepad2);

        Drive.init(this);

        waitForStart();

        while (opModeIsActive()) {
            newGamePad1.updateState();
            newGamePad2.updateState();

            if (gamepad1.left_stick_x > .4) {
                Drive.strafeRight(speed);
            } else if (gamepad1.left_stick_x < -.4) {
                Drive.strafeLeft(speed);
            } else if (gamepad1.left_stick_y < -.4) {
                Drive.forward(speed);
            } else if (gamepad1.left_stick_y > .4) {
                Drive.backward(speed);
            } else if (gamepad1.right_stick_x > .4) {
                Drive.turnRight(speed);
            } else if (gamepad1.right_stick_x < -.4) {
                Drive.turnLeft(speed);
            }

            if (newGamePad1.right_bumper.released) {
                if (speed < 1) {
                    speed += .1;
                }
            } else if (newGamePad1.left_bumper.released) {
                if (speed > 0) {
                    speed -= .1;
                }
            }
            //Servo Movement

//            if (gamepad2.a) {
//                Drive.servoForward();
//            } else if (gamepad2.b) {
//                Drive.servoRevers();
//            } else {
//                Drive.servoStop();
//            }


            if (!Touch.isPressed()) {
                Drive.servoForward();
            } else {
                Drive.servoStop();
            }

            telemetry.addData("Servo power: ", +Drive.testing.getPower());
            telemetry.addData("Button ", +Touch.getValue());
            telemetry.update();
        }
    }
}