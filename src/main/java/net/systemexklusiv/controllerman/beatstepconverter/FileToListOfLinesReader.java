package net.systemexklusiv.controllerman.beatstepconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(FileToListOfLinesReader.class);

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
//            e.printStackTrace();

            logger.error("Can not find File: " + filePath);
            logger.error(e.getMessage());

            return Collections.EMPTY_LIST;
        } finally {
        }
    }
}
