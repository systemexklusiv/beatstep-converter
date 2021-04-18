package net.systemexklusiv.controllerman.beatstepconverter.converters;

import net.systemexklusiv.controllerman.beatstepconverter.ControllerEntry;
import net.systemexklusiv.controllerman.beatstepconverter.ControllerEntry.ControllerType;

public interface HasConverter {
    /**
     * @param currentControllerType pass in the actual to check if to be conerted or not
     * @param beatstepFeatureNum pass in the feature num (1-6) in oder to check if it is the right uple for the conversion
     * @param value pass in the actual value of the tuple, pass it throughnif no conversion is to be done
     * @return
     */
    String convert(ControllerType currentControllerType, int beatstepFeatureNum, String value);

}
