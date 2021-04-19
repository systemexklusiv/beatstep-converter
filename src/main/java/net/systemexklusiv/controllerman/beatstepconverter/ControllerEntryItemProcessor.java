package net.systemexklusiv.controllerman.beatstepconverter;

import net.systemexklusiv.controllerman.beatstepconverter.converters.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ControllerEntryItemProcessor implements ItemProcessor<ControllerEntry, ControllerEntry> {

    private static final Logger log = LoggerFactory.getLogger(ControllerEntry.class);
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

        if (padNoteStartingAt != null)
            padNoteStartAtConverter = new StartAtConverter(Integer.valueOf(padNoteStartingAt), ControllerEntry.ControllerType.PAD, ControllerEntry.CONTROL_MODE_NOTE, false);
        if (padNoteStartingFrom != null)
            padNoteStartAtConverter = new StartAtConverter(Integer.valueOf(padNoteStartingFrom), ControllerEntry.ControllerType.PAD, ControllerEntry.CONTROL_MODE_NOTE, true);


        if (knobStartingAt != null)
            knobStartAtConverter = new StartAtConverter(Integer.valueOf(knobStartingAt), ControllerEntry.ControllerType.KNOB, ControllerEntry.CONTROL_MODE_CC, false);
        if (knobStartingFrom != null)
            knobStartAtConverter = new StartAtConverter(Integer.valueOf(knobStartingFrom), ControllerEntry.ControllerType.KNOB, ControllerEntry.CONTROL_MODE_CC, true);

        if (allPadChannel != null)
            padChannelConverter = new ChannelConverter(ControllerEntry.ControllerType.PAD, Integer.valueOf(allPadChannel));
        if (allKnobChannel != null)
            knobChannelConverter = new ChannelConverter(ControllerEntry.ControllerType.KNOB, Integer.valueOf(allKnobChannel));

        if (allPadToOptionMidiNote != null) padOptionConverter = new OptionConverter(ControllerEntry.ControllerType.PAD, Integer.valueOf(ControllerEntry.CONTROL_MODE_NOTE));

        if (allPadToOptionSwitchedControl != null) padOptionConverter = new OptionConverter(ControllerEntry.ControllerType.PAD, Integer.valueOf(ControllerEntry.CONTROL_MODE_SWITCHED_CC));

        if (allPadMin != null) padMinConverter = new MinConverter(ControllerEntry.ControllerType.PAD, Integer.valueOf(allPadMin));

        if (allPadMax != null) padMaxConverter = new MaxConverter(ControllerEntry.ControllerType.PAD, Integer.valueOf(allPadMax));

        if (allKnobMin != null) knobMaxConverter = new MinConverter(ControllerEntry.ControllerType.KNOB, Integer.valueOf(allKnobMin));

        if (allKnobMax != null) knobMaxConverter = new MaxConverter(ControllerEntry.ControllerType.KNOB, Integer.valueOf(allKnobMax));
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
            if (this.padNoteStartAtConverter != null) {
                value = padNoteStartAtConverter.convert(controllerEntry.controllerType, featureNum, value);
            }

            if (this.padChannelConverter != null) {
                value = padChannelConverter.convert(controllerEntry.controllerType, featureNum, value);
            }
            if (this.knobChannelConverter != null) {
                value = knobChannelConverter.convert(controllerEntry.controllerType, featureNum, value);
            }

            if (this.padOptionConverter != null) {
                value = padOptionConverter.convert(controllerEntry.controllerType, featureNum, value);
            }
            if (this.padMinConverter != null) {
                value = padMinConverter.convert(controllerEntry.controllerType, featureNum, value);
            }

            if (this.padMaxConverter != null) {
                value = padMaxConverter.convert(controllerEntry.controllerType, featureNum, value);
            }

            if (this.knobMinConverter != null) {
                value = knobMinConverter.convert(controllerEntry.controllerType, featureNum, value);
            }

            if (this.knobMaxConverter != null) {
                value = knobMaxConverter.convert(controllerEntry.controllerType, featureNum, value);
            }

        }

        final ControllerEntry transformedEntry = new ControllerEntry(field, value, controllerEntry.controllerType);

        log.info("Converting (" + controllerEntry + ") into (" + transformedEntry + ")");

        return transformedEntry;
    }

}
