package net.systemexklusiv.controllerman.beatstepconverter;

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

    public ControllerEntryItemWriter(String filePath) {
        path = Path.of(filePath);
    }

    @Nullable
        @Override
        public void write(List<? extends ControllerEntry> controllerEntries) {
            if (!controllerEntries.isEmpty()) {

                final String NL = System.lineSeparator() ;
                final String COMMA = "," ;
                final String QUOT = "\"" ;
                final String COLON = ":" ;
                final String WS = " " ;

                StringBuilder sb = new StringBuilder(controllerEntries.size() + 3);

                sb.append(ControllerEntry.PRESET_START);
                sb.append(NL);

                controllerEntries.forEach( entry -> {
                    sb.append(entry.getField() + COLON + entry.getValue() + NL);
                } );

                sb.append(ControllerEntry.PRESET_END);

                try {
                    Files.writeString(path, sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
}
