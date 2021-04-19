package net.systemexklusiv.controllerman.beatstepconverter.converters;

import net.systemexklusiv.controllerman.beatstepconverter.ControllerEntry;

/**
 * The option etermines if the control is
 *     CONTROL_MODE_CC = "1";
 *     CONTROL_MODE_SWITCHED_CC = "8";
 *     CONTROL_MODE_NOTE = "9";
 *
 *     patch change = 10
 *     nrpm = 2
 *
 *
 */
public class OptionConverter implements HasConverter {
    ControllerEntry.ControllerType conversionRelevantForThisControllerType;
    int target;

    public OptionConverter(ControllerEntry.ControllerType conversionRelevantForThisControllerType, int target) {
        this.conversionRelevantForThisControllerType = conversionRelevantForThisControllerType;
        this.target = target;
    }

    @Override
    public String convert(ControllerEntry.ControllerType currentControllerType, int beatstepFeatureNum, String value) {
        if (currentControllerType.equals(conversionRelevantForThisControllerType)) {
            if (beatstepFeatureNum == ControllerEntry.CONTROL_MODE) {
                value = String.valueOf(target);
            }
        }
        return value;
    }
}
