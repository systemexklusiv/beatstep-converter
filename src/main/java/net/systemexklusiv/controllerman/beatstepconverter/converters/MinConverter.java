package net.systemexklusiv.controllerman.beatstepconverter.converters;

import net.systemexklusiv.controllerman.beatstepconverter.ControllerEntry;

public class MinConverter implements HasConverter {
    ControllerEntry.ControllerType conversionRelevantForThisControllerType;
    int target;

    public MinConverter(ControllerEntry.ControllerType conversionRelevantForThisControllerType, int target) {
        this.conversionRelevantForThisControllerType = conversionRelevantForThisControllerType;
        this.target = target;
    }

    @Override
    public String convert(ControllerEntry.ControllerType currentControllerType, int beatstepFeatureNum, String value) {
        if (currentControllerType.equals(conversionRelevantForThisControllerType)) {
            if (beatstepFeatureNum == ControllerEntry.CC_MIN) {
                value = String.valueOf(target);
            }
        }
        return value;
    }
}
