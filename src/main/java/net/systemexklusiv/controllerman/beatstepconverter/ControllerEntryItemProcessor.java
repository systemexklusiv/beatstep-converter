package net.systemexklusiv.controllerman.beatstepconverter;

import net.systemexklusiv.controllerman.beatstepconverter.converters.ChannelConverter;
import net.systemexklusiv.controllerman.beatstepconverter.converters.HasConverter;
import net.systemexklusiv.controllerman.beatstepconverter.converters.StartAtConverter;
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
    HasConverter padChannelConverter;
    HasConverter knobChannelConverter;

    public ControllerEntryItemProcessor(String allChannel, String allKnobChannel, String allPadChannel, String padNoteStartingAt, String padNoteStartingFrom) {
        this.allChannel = allChannel;

        if (padNoteStartingAt != null) padNoteStartAtConverter = new StartAtConverter(Integer.valueOf(padNoteStartingAt), ControllerEntry.ControllerType.PAD, ControllerEntry.CONTROL_MODE_NOTE, false);
        if (padNoteStartingFrom != null) padNoteStartAtConverter = new StartAtConverter(Integer.valueOf(padNoteStartingFrom), ControllerEntry.ControllerType.PAD, ControllerEntry.CONTROL_MODE_NOTE, true);

        if (allPadChannel != null) padChannelConverter = new ChannelConverter(ControllerEntry.ControllerType.PAD, Integer.valueOf(allPadChannel));
        if (allKnobChannel != null) knobChannelConverter = new ChannelConverter(ControllerEntry.ControllerType.KNOB, Integer.valueOf(allKnobChannel));
        //        switchedCcStartAtConverter = new StartAtConverter(Integer.valueOf(padNoteStartingAt), ControllerEntry.ControllerType.PAD, ControllerEntry.CONTROL_MODE_NOTE);
    }

    @Override
    public ControllerEntry process(final ControllerEntry controllerEntry) throws Exception {

         String field = controllerEntry.getField();
         String value = controllerEntry.getValue();

        String[] fieldPartials = field.split("_");
        if (fieldPartials.length > 1) {
            String controlIdAsString = fieldPartials[0];
            String controlFeatureAsString = fieldPartials[1];

           int featureNum = Integer.valueOf(controlFeatureAsString.replaceAll("\"",""));

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


        }

        final ControllerEntry transformedEntry = new ControllerEntry(field, value, controllerEntry.controllerType);

        log.info("Converting (" + controllerEntry + ") into (" + transformedEntry + ")");

        return transformedEntry;
    }

}
