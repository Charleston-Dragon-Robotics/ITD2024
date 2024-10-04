package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class intake {
public CRServo intake = null;
    private LinearOpMode opmode = null;

    public intake() {

    }

    public void init(LinearOpMode opMode) {
        HardwareMap hwMap;
        opmode = opMode;
        hwMap = opMode.hardwareMap;


        intake = hwMap.crservo.get("Intake");
    }

    public void intakeIn() {
        intake.setPower(1);
    }
    public void intakeOut(){
        intake.setPower(-1);
    }
    public void intakeSTOP(){
        intake.setPower(0);
    }
}
