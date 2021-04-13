package net.systemexklusiv.controllerman.beatstepconverter;

import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileToListOfLinesReader implements HasFileToListOfLinesReader {

    private final String filePath;

    public FileToListOfLinesReader(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getContendAsListOfLines() {
        Path path = null;
        Stream<String> lineStream = null;

        try {
            FileSystemResource res = new FileSystemResource(filePath);
            path = Path.of(res.getURI());
            lineStream = Files.lines(path);
            return lineStream.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        } finally {
        }
    }
}
