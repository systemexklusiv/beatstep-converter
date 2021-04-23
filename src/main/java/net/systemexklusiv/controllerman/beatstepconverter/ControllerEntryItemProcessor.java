package net.systemexklusiv.controllerman.beatstepconverter;

import net.systemexklusiv.controllerman.beatstepconverter.converters.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class ControllerEntryItemProcessor implements ItemProcessor<ControllerEntry, ControllerEntry> {

    private static final Logger log = LoggerFactory.getLogger(ControllerEntryItemProcessor.class);
    private final String allChannel;
    //    private final String knobChannelStartingAt;
//    private final String padChannelStartingAt;
//    private final String allKnobsCc;
//    private final String allPadsCc;
//    private final String knobCCStartingAt;
//    private final String padCcStartingAt;

    HasConverter padNoteStartAtConverter;

    HasConverter knobStartAtConverter;

    HasConverter knobChannelConverter;
    HasConverter padChannelConverter;
    HasConverter padOptionConverter;
    HasConverter padMinConverter;
    HasConverter padMaxConverter;
    HasConverter knobMinConverter;
    HasConverter knobMaxConverter;

    List<HasConverter> converters = new ArrayList<>();

    public ControllerEntryItemProcessor(
            String allChannel
            , String allKnobChannel
            , String allPadChannel
            , String padNoteStartingAt
            , String padNoteStartingFrom
            , String allPadToOptionMidiNote
            , String allPadToOptionSwitchedControl
            , String allPadMin
            , String allPadMax
            , String allKnobMin
            , String allKnobMax
            , String knobStartingAt
            , String knobStartingFrom
    ) {

        this.allChannel = allChannel;

        if (padNoteStartingAt != null) {
            padNoteStartAtConverter = new StartAtConverter(Integer.valueOf(padNoteStartingAt), ControllerEntry.ControllerType.PAD, ControllerEntry.CONTROL_MODE_NOTE, false);
            converters.add(padNoteStartAtConverter);
        } else if (padNoteStartingFrom != null) {
            padNoteStartAtConverter = new StartAtConverter(Integer.valueOf(padNoteStartingFrom), ControllerEntry.ControllerType.PAD, ControllerEntry.CONTROL_MODE_NOTE, true);
            converters.add(padNoteStartAtConverter);
        }


        if (knobStartingAt != null) {
            knobStartAtConverter = new StartAtConverter(Integer.valueOf(knobStartingAt), ControllerEntry.ControllerType.KNOB, ControllerEntry.CONTROL_MODE_CC, false);
            converters.add(knobStartAtConverter);
        } else if (knobStartingFrom != null) {
            knobStartAtConverter = new StartAtConverter(Integer.valueOf(knobStartingFrom), ControllerEntry.ControllerType.KNOB, ControllerEntry.CONTROL_MODE_CC, true);
            converters.add(knobStartAtConverter);
        }

        if (allPadChannel != null)
            converters.add(padChannelConverter = new SingleConverter(ControllerEntry.ControllerType.PAD, ControllerEntry.CONTROL_CHANNEL, Integer.valueOf(allPadChannel)));

        if (allKnobChannel != null)
            converters.add(knobChannelConverter = new SingleConverter(ControllerEntry.ControllerType.KNOB, ControllerEntry.CONTROL_CHANNEL, Integer.valueOf(allKnobChannel)));

        if (allPadToOptionMidiNote != null)
            converters.add(padOptionConverter = new SingleConverter(ControllerEntry.ControllerType.PAD, ControllerEntry.CONTROL_MODE, Integer.valueOf(ControllerEntry.CONTROL_MODE_NOTE)));

        if (allPadToOptionSwitchedControl != null)
            converters.add(padOptionConverter = new SingleConverter(ControllerEntry.ControllerType.PAD, ControllerEntry.CONTROL_MODE, Integer.valueOf(ControllerEntry.CONTROL_MODE_SWITCHED_CC)));

        if (allPadMin != null)
            converters.add(padMinConverter = new SingleConverter(ControllerEntry.ControllerType.PAD, ControllerEntry.CC_MIN, Integer.valueOf(allPadMin)));

        if (allPadMax != null)
            converters.add(padMaxConverter = new SingleConverter(ControllerEntry.ControllerType.PAD, ControllerEntry.CC_MAX, Integer.valueOf(allPadMax)));

        if (allKnobMin != null)
            converters.add(knobMinConverter = new SingleConverter(ControllerEntry.ControllerType.KNOB, ControllerEntry.CC_MIN, Integer.valueOf(allKnobMin)));

        if (allKnobMax != null)
            converters.add(knobMaxConverter = new SingleConverter(ControllerEntry.ControllerType.KNOB, ControllerEntry.CC_MAX, Integer.valueOf(allKnobMax)));
    }

    @Override
    public ControllerEntry process(final ControllerEntry controllerEntry) throws Exception {

        String field = controllerEntry.getField();
        String value = controllerEntry.getValue();

        String[] fieldPartials = field.split("_");
        if (fieldPartials.length > 1) {
            String controlIdAsString = fieldPartials[0];
            String controlFeatureAsString = fieldPartials[1];

            int featureNum = Integer.valueOf(controlFeatureAsString.replaceAll("\"", ""));

            if (this.allChannel != null) {
                if (featureNum == ControllerEntry.CONTROL_CHANNEL) {
                    value = allChannel;
                }
            }

            for (HasConverter converter : converters
            ) {
                value = converter.convert(controllerEntry.controllerType, featureNum, value);
            }

        }

        final ControllerEntry transformedEntry = new ControllerEntry(field, value, controllerEntry.controllerType);

        log.info("Converting (" + controllerEntry + ") into (" + transformedEntry + ")");

        return transformedEntry;
    }

}
