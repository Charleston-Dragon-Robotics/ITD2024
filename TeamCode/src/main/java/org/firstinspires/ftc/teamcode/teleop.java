package org.firstinspires.ftc.teamcode;

import android.graphics.Path;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drivetrain;
import org.firstinspires.ftc.teamcode.GamepadStates;

@TeleOp(name = "Teleop", group = "Teleop")
public class teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
//        ColorSensor Color;
//        TouchSensor Touch;
        DistanceSensor Distance;
//        Color = hardwareMap.get(ColorSensor.class, "SensorColor");
//        Touch = hardwareMap.get(TouchSensor.class, "Touch");
        Distance = hardwareMap.get(DistanceSensor.class, "Distance");

        double speed = .5;

        drivetrain Drive = new drivetrain();
        intake intake = new intake();

        GamepadStates newGamePad1 = new GamepadStates(gamepad1);
        GamepadStates newGamePad2 = new GamepadStates(gamepad2);

        Drive.init(this);
        intake.init(this);

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


            double DistanceFrom = Distance.getDistance(DistanceUnit.CM);
            telemetry.addData("range", String.format("% 01f cm", DistanceFrom));
            telemetry.update();
            //Adjustable bast off of distence of iteams
            if(DistanceFrom >= 7){
                Drive.servoStop();
            }  else if (DistanceFrom <=7){
                Drive.servoForward(0.1428);
            }  else if (DistanceFrom <=6){
                Drive.servoForward(0.2857);
            } else if (DistanceFrom <=5){
                Drive.servoForward(0.4285);
            }else if (DistanceFrom <=4){
                Drive.servoForward(0.5714);
            }else if (DistanceFrom <=3){
                Drive.servoForward(0.7142);
            }else if (DistanceFrom <=2) {
                Drive.servoForward(0.8571);
            }else  {
                Drive.servoForward(1);
            }


//            telemetry.addData("Red  ", Color.red());
//            telemetry.addData("Green", Color.green());
//            telemetry.addData("Blue ", Color.blue());

//            telemetry.addData("Servo power: ", +Drive.testing.getPower());
//            telemetry.addData("Button ", +Touch.getValue());
//
//            telemetry.addData("range", String.format("% 01f cm", Distance.getDistance(DistanceUnit.CM)));
//            telemetry.update();

            if (newGamePad2.dpad_down.state) {
                intake.intakeIn();
            } else if (newGamePad2.dpad_up.state) {
              intake.intakeOut();
            }else{
                intake.intakeSTOP();
            }
        }
    }
}