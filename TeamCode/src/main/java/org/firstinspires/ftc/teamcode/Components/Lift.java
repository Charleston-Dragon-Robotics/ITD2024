package org.firstinspires.ftc.teamcode.Components;


import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.LOGGER;

import org.firstinspires.ftc.teamcode.Components.RFModules.Devices.RFMotor;
import org.firstinspires.ftc.teamcode.Components.RFModules.System.RFLogger;

/**
 * William
 */
public class Lift extends RFMotor {
    private double lastPower = 0.0;
    private double target = 0.0;
    /**
     * Constructor
     */
    public Lift() {
        super("liftMotor", true);
    }

    /**
     * Stores different states of lift.
     */
    public enum LiftPositionStates {
        HIGH_SET_LINE(3000, false),
        MID_SET_LINE(2000, false),
        LOW_SET_LINE(1000, false),
        AT_ZERO(0, true);

        double position;
        boolean state;

        LiftPositionStates(double p_position, boolean p_state) {
            this.position = p_position;
            this.state = p_state;
        }
        void setStateTrue() {
            for(int i = 0; i < LiftPositionStates.values().length; i++){
                LiftPositionStates.values()[i].state=false;
            }
            this.state=true;
        }
        public double getPosition(){
            return position;
        }
    }

    public enum LiftMovingStates {
        AT_ZERO(true),
        LOW(false),
        MID(false),
        HIGH(false),
        LIFTING(false),
        LOWERING(false);

        boolean state;

        LiftMovingStates(boolean p_state) {
            this.state = p_state;
        }
        void setState(boolean p_state) {
            this.state = p_state;
        }
    }

    /**
     * Depending on which state the lift is currently in, checks whether the state can be
     * transitioned to the next state, then changes state values.
     * Logs which state(s)' values have been changed and to what.
     * Logs to RFMotor & general logs.
     * Logs to finest level.
     * Updates LiftMovingStates and LiftPositionStates state machines.
     */
    public void update() {
        //enum.values()[]
    }

    /**
     * Sets target position for lift.
     * @param p_target target position for lift to run to
     * Logs what position the target position has been set to.
     * Logs to RFMotor & general logs.
     * Logs to finest level.
     * Updates LiftMovingStates state machine.
     */
    public void setPosition(double p_target) {
        super.setPosition(p_target, 0);
        if(target!=p_target) {
            LOGGER.setLogLevel(RFLogger.Severity.INFO);
            LOGGER.log("Lift position set to: " + p_target);
            target=p_target;
        }
    }
    public void setPosition(LiftPositionStates p_state) {
        super.setPosition(p_state.position, 0);
        if(target!=p_state.position) {
            LOGGER.setLogLevel(RFLogger.Severity.INFO);
            LOGGER.log("Lift position set to: " + p_state.position);
            target=p_state.position;
        }
    }

    /**
     * Manually extend/retract slides
     * @param p_power How fast the user wants to move the slides and in what direction
     * Logs that the lift is currently being manually extended.
     * Logs to RFMotor & general logs.
     * Logs to finest level.
     * Updates LiftMovingStates state machine.
     */
    public void manualExtend(double p_power) {
        super.setPower(p_power);
        if(p_power!=lastPower) {
            LOGGER.setLogLevel(RFLogger.Severity.INFO);
            LOGGER.log("Lift power set to: " + p_power);
            lastPower=p_power;
        }
    }

    /**
     * Iterate to the next highest set line lift height.
     * Logs what set line lift target height the lift has been set to run to.
     * Logs to RFMotor & general logs.
     * Logs to finest level.
     * Updates LiftMovingStates state machine.
     */
    public void iterateUp() {
        int ordinal = 0;
        for (int i = 0; i < LiftPositionStates.values().length; i++) {
            if (LiftPositionStates.values()[i].state) {
                ordinal = i;
            }
        }
        ordinal = (ordinal + 1) % 4;
        setPosition(LiftPositionStates.values()[ordinal]);
        LOGGER.setLogLevel(RFLogger.Severity.INFO);
        LOGGER.log("Lift state iterated up to state: " + LiftPositionStates.values()[ordinal]);
    }

    /**
     * Iterate to the next lowest set line lift height.
     * Logs what set line lift target height the lift has been set to run to.
     * Logs to RFMotor & general logs.
     * Logs to finest level.
     * Updates LiftMovingStates state machine.
     */
    public void iterateDown() {
        int ordinal = 0;
        for (int i = 0; i < LiftPositionStates.values().length; i++) {
            if (LiftPositionStates.values()[i].state) {
                ordinal = i;
            }
        }
        ordinal = (ordinal - 1) % 4;
        setPosition(LiftPositionStates.values()[ordinal]);
        LOGGER.setLogLevel(RFLogger.Severity.INFO);
        LOGGER.log("Lift state iterated down to state: " + LiftPositionStates.values()[ordinal]);
    }
}