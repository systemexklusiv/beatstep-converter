package net.systemexklusiv.controllerman.beatstepconverter.converters;

import net.systemexklusiv.controllerman.beatstepconverter.ControllerEntry;

public class ChannelConverter implements HasConverter {
    ControllerEntry.ControllerType conversionRelevantForThisControllerType;
    int targetChannel;

    public ChannelConverter(ControllerEntry.ControllerType conversionRelevantForThisControllerType, int targetChannel) {
        this.conversionRelevantForThisControllerType = conversionRelevantForThisControllerType;
        this.targetChannel = targetChannel;
    }

    @Override
    public String convert(ControllerEntry.ControllerType currentControllerType, int beatstepFeatureNum, String value) {
        if (currentControllerType.equals(conversionRelevantForThisControllerType)) {
            if (beatstepFeatureNum == ControllerEntry.CONTROL_CHANNEL) {
                value = String.valueOf(targetChannel);
            }
        }
        return value;
    }
}
