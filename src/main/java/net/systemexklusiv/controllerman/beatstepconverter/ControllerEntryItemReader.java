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
                ControllerEntry controllerEntry = new ControllerEntry("dummy Field", "dummy Value");
                return controllerEntry;
            }
            return null;
        }
}
