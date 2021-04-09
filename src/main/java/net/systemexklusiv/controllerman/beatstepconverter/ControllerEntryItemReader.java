package net.systemexklusiv.controllerman.beatstepconverter;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerEntryItemReader implements ItemReader<ControllerEntry> {

    private List<String> linesList;

    public ControllerEntryItemReader(String filePath) {

        Path path = null;
        Stream<String> lineStream = null;

        try {
            path = Paths.get(getClass().getClassLoader()
                    .getResource(filePath).toURI());
            lineStream = Files.lines(path);
            linesList = lineStream.collect(Collectors.toList());
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        } finally {
            lineStream.close();
        }
//        String data = lineStream.collect(Collectors.joining("\n"));
    }

    @Nullable
        @Override
        public ControllerEntry read() {
            if (!linesList.isEmpty()) {
                String line =  linesList.remove(0);
                line = line.startsWith(ControllerEntry.PRESET_START) ? linesList.remove(0) : line;
                line = line.replaceAll("\t", "");
                String[] partials = line.split(":");
                if (partials.length > 1) {
                    Stream<String> stream = Arrays.stream(partials);
                    partials = stream.map(str -> str.replaceAll("\\s","")).toArray(size -> new String[size]);
                    ControllerEntry controllerEntry = new ControllerEntry(partials[0],  partials[1]);
                    return controllerEntry;
                }
            }
            return null;
        }
}
