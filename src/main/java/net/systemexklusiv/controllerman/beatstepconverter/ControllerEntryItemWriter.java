package net.systemexklusiv.controllerman.beatstepconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerEntryItemWriter implements ItemWriter<ControllerEntry> {

    private Path path;

    private static final Logger logger = LoggerFactory.getLogger(ControllerEntryItemWriter.class);

    public ControllerEntryItemWriter(String filePath) {
        path = Path.of(filePath);
    }

    @Nullable
        @Override
        public void write(List<? extends ControllerEntry> controllerEntries) {
            if (!controllerEntries.isEmpty()) {

                final String NL = System.lineSeparator() ;
                final String COLON = ":" ;
                final String WS = " " ;
                final String COMMA = "," ;

                StringBuilder sb = new StringBuilder(controllerEntries.size() + 3);

                sb.append(ControllerEntry.PRESET_START);
                sb.append(NL);

                controllerEntries.forEach( entry -> {
                    sb.append(entry.getField() + COLON + entry.getValue() + COMMA + NL );
                } );

                sb.append(ControllerEntry.PRESET_END);
                try {
                    Files.writeString(path, sb.toString());

                    logger.error("Success :-) - New File has been written to: " + path);

                } catch (IOException e) {
                    logger.error("Can not write to file: " + path);
                    logger.error(e.getMessage());
                }
            } else {
                logger.info("No file has benn written, no controls have been affacted");
            }
        }
}
