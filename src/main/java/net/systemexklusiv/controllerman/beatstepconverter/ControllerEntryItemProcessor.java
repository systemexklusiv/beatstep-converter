package net.systemexklusiv.controllerman.beatstepconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ControllerEntryItemProcessor implements ItemProcessor<ControllerEntry, ControllerEntry> {

    private static final Logger log = LoggerFactory.getLogger(ControllerEntry.class);
    @Override
    public ControllerEntry process(final ControllerEntry controllerEntry) throws Exception {
        final String field = controllerEntry.getField();
        final String value = controllerEntry.getValue();

        final ControllerEntry transformedEntry = new ControllerEntry(field, value);

        log.info("Converting (" + controllerEntry + ") into (" + transformedEntry + ")");

        return transformedEntry;
    }
}
