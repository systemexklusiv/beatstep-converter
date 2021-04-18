package net.systemexklusiv.controllerman.beatstepconverter;

import org.w3c.dom.ranges.Range;

import java.time.temporal.ValueRange;

public class ControllerEntry {

    public enum ControllerType {KNOB, BIG_KNOB, PAD, STOP, PLAY, NOT_SET, NOT_PROCESSED_BY_THIS_TOOL};
    public static final String PRESET_START = "{";
    public static final String PRESET_END = "}";
    public static final String DEVICE_NAME_TUPLE = "\"device\": \"BeatStep\"";

    public static final int CONTROL_MODE = 1; // Values: 0 -> Off, 1 -> CC, 2 -> NRPM, 8 -> switched control, 9 -> Midi Note, 10 -> Patch change
    public static final String CONTROL_MODE_CC = "1";
    public static final String CONTROL_MODE_SWITCHED_CC = "8";
    public static final String CONTROL_MODE_NOTE = "9";

    public static final int CONTROL_CHANNEL = 2; // Values 0-15
    public static final int CC_NUM = 3; // Values 0-127
    public static final int CC_MIN = 4; // Values 0-127
    public static final int CC_MAX = 5; // Values 0-127
    public static final int OPTION = 6; // Values 0-> toggle, 1 -> Gate OR WHEN KNOB 0 -> absolute, 1-3 -> relative 1-3

    String field;
    String value;
    ControllerType controllerType;

    public ControllerEntry() {
    }

    public ControllerEntry(String field, String value, ControllerType controllerType) {
        this.field = field;
        this.value = value;
        this.controllerType = controllerType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ControllerEntry{" +
                "field='" + field + '\'' +
                ", value='" + value + '\'' +
                ", controllerType=" + controllerType +
                '}';
    }
}
