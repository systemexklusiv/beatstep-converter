package net.systemexklusiv.controllerman.beatstepconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ControllerEntryItemProcessor implements ItemProcessor<ControllerEntry, ControllerEntry> {

    private static final Logger log = LoggerFactory.getLogger(ControllerEntry.class);
    private final String channel;

    public ControllerEntryItemProcessor(String channel) {
        this.channel =  channel;
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

            controlIdAsString.replaceAll("\"","");
            controlIdAsString.replaceAll(" ","");

           int controlAddress = Integer.valueOf(controlIdAsString.replaceAll("\"",""));
           int featureNum = Integer.valueOf(controlFeatureAsString.replaceAll("\"",""));

           final String newChannel = channel != null ? channel : value;

           if (featureNum == ControllerEntry.CONTROL_CHANNEL) {
               value = newChannel;
           }
        }

        final ControllerEntry transformedEntry = new ControllerEntry(field, value);

        log.info("Converting (" + controllerEntry + ") into (" + transformedEntry + ")");

        return transformedEntry;
    }
}
