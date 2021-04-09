package net.systemexklusiv.controllerman.beatstepconverter;

public class ControllerEntry {
    String field;
    String value;

    public ControllerEntry() {
    }

    public ControllerEntry(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
