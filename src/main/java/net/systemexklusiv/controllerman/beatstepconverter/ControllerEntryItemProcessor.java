package net.systemexklusiv.controllerman.beatstepconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ControllerEntryItemProcessor implements ItemProcessor<ControllerEntry, ControllerEntry> {

    private static final Logger log = LoggerFactory.getLogger(ControllerEntry.class);
    private final String allChannel;
    private final String allKnobChannel;
    private final String allPadChannel;
    private final String padNoteStartingAt;
//    private final String knobChannelStartingAt;
//    private final String padChannelStartingAt;

//    private final String allKnobsCc;
//    private final String allPadsCc;
//    private final String knobCCStartingAt;
//    private final String padCcStartingAt;

    private int lastNoteNumber = -1;

    public ControllerEntryItemProcessor(String allChannel, String allKnobChannel, String allPadChannel, String padNoteStartingAt) {
        this.allChannel = allChannel;
        this.allKnobChannel = allKnobChannel;
        this.allPadChannel = allPadChannel;
        this.padNoteStartingAt = padNoteStartingAt;
        lastNoteNumber = Integer.valueOf(padNoteStartingAt);
    }

    @Override
    public ControllerEntry process(final ControllerEntry controllerEntry) throws Exception {
        final String COMMA = "," ;
        final String QUOT = "\"" ;

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

            if (this.padNoteStartingAt != null) {
                if (featureNum == ControllerEntry.CONTROL_MODE) {
                        // Set the pad to be midi note
                        if (controllerEntry.controllerType.equals(ControllerEntry.ControllerType.PAD)) {
                            value = ControllerEntry.CONTROL_MODE_NOTE;
                        }
                    } else
                if (featureNum == ControllerEntry.CC_NUM) {
                    value = String.valueOf(getNoteNumberRoundRobin());
                    incrementNoteNumRoundRobin();
                }
           }

        }

        final ControllerEntry transformedEntry = new ControllerEntry(field, value, controllerEntry.controllerType);

        log.info("Converting (" + controllerEntry + ") into (" + transformedEntry + ")");

        return transformedEntry;
    }

    private int getNoteNumberRoundRobin() {
        return lastNoteNumber;
    }

    private void incrementNoteNumRoundRobin() {
        this.lastNoteNumber = (lastNoteNumber + 1) % 127;
    }
}
