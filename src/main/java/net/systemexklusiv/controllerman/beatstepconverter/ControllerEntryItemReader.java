package net.systemexklusiv.controllerman.beatstepconverter;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerEntryItemReader extends AbstractItemStreamItemReader<ControllerEntry> {

    private List<String> linesList;

    public ControllerEntryItemReader(HasFileToListOfLinesReader hasFileToListOfLinesReader) {
        this.linesList = hasFileToListOfLinesReader.getContendAsListOfLines();
    }

    @Nullable
        @Override
        public ControllerEntry read() {
            if (!linesList.isEmpty()) {
                String line =  linesList.remove(0);
                line = line.startsWith(ControllerEntry.PRESET_START) ? linesList.remove(0) : line;
                line = line.replaceAll("\t", "");
                line = line.replaceAll(" ", "");
                line = line.replaceAll(",", "");
                String[] partials = line.split(":");
                if (partials.length > 1) {
                    Stream<String> stream = Arrays.stream(partials);
                    ControllerEntry controllerEntry = new ControllerEntry(partials[0],  partials[1]);
                    return controllerEntry;
                }
            }
            return null;
        }
}
