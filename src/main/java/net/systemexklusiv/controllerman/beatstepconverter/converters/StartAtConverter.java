package net.systemexklusiv.controllerman.beatstepconverter.converters;

import net.systemexklusiv.controllerman.beatstepconverter.ControllerEntry;
import net.systemexklusiv.controllerman.beatstepconverter.ControllerEntry.*;

public class StartAtConverter implements HasConverter {

    private int startingHere;
    ControllerType conversionRelevantForThisControllerType;
    String conversionToThisNoteOrSwitchedCC;
    boolean isInverted;
    /**
     *
     * @param startingHere The num which gets incremented each time
     * @param conversionRelevantForThisControlerType PAD or KNb which gets into account if processed
     * @param conversionToThisNoteOrSwitchedCC ControllerEntry.CONTROL_MODE_NOTE or ontrollerEntry.CONTROL_SWITCHED_CC
     * @param isInverted if true it decrements
     */
    public StartAtConverter(int startingHere, ControllerType conversionRelevantForThisControlerType, String conversionToThisNoteOrSwitchedCC, boolean isInverted) {
        this.startingHere = startingHere;
        this.conversionRelevantForThisControllerType = conversionRelevantForThisControlerType;
        this.conversionToThisNoteOrSwitchedCC = conversionToThisNoteOrSwitchedCC;
        this.isInverted = isInverted;
    }

    @Override
    public String convert(ControllerType currentControllerType, int beatstepFeatureNum, String value) {
        if (currentControllerType.equals(conversionRelevantForThisControllerType)) {
            if (beatstepFeatureNum == ControllerEntry.CONTROL_MODE) {
                // Set the pad to be midi note or swithced cc
                value = conversionToThisNoteOrSwitchedCC;
            } else if (beatstepFeatureNum == ControllerEntry.CC_NUM) {
                value = String.valueOf(getNoteNumberRoundRobin());
                incrementNoteNumRoundRobin();
            }
        }
        return value;
    }

    private int getNoteNumberRoundRobin() {
        return startingHere;
    }

    private void incrementNoteNumRoundRobin() {
        this.startingHere = isInverted ? (startingHere - 1) % 127 : (startingHere + 1) % 127;
    }
}
