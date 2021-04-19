package net.systemexklusiv.controllerman.beatstepconverter.converters;

import net.systemexklusiv.controllerman.beatstepconverter.ControllerEntry;
import net.systemexklusiv.controllerman.beatstepconverter.ControllerEntry.ControllerType;

public interface HasConverter {
    /**
     * @param currentControllerType pass in the actual to check if to be conevrted or not
     * @param beatstepFeatureNum pass in the feature num (1-6) in oder to check if it is the right tuple for the conversion
     * @param value pass in the actual value of the tuple, pass it through if no conversion is to be done
     * @return the converted value, eg the new midichannel or CC/Note number
     */
    String convert(ControllerType currentControllerType, int beatstepFeatureNum, String value);

}
