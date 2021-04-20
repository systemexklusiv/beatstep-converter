package net.systemexklusiv.controllerman.beatstepconverter.converters;

import net.systemexklusiv.controllerman.beatstepconverter.Constants;
import net.systemexklusiv.controllerman.beatstepconverter.ControllerEntry;

public class SingleConverter implements HasConverter {
    ControllerEntry.ControllerType conversionRelevantForThisControllerType;
    int targetField; // CONTROL_MODE, CHANNEL, CC_MAX CC_MIN..
    int target;

    public SingleConverter(ControllerEntry.ControllerType conversionRelevantForThisControllerType, int targetField ,int targetValue) {
        this.conversionRelevantForThisControllerType = conversionRelevantForThisControllerType;
        this.targetField = targetField;
        this.target = targetValue;
    }

    @Override
    public String convert(ControllerEntry.ControllerType currentControllerType, int beatstepFeatureNum, String value) {
        if (currentControllerType.equals(conversionRelevantForThisControllerType)) {
            if (beatstepFeatureNum == targetField) {
                value = String.valueOf(target);
            }
        }
        return value;
    }
}
