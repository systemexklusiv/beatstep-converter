package net.systemexklusiv.controllerman.beatstepconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.lang.Nullable;

import java.util.List;

public class ControllerEntryItemReader extends AbstractItemStreamItemReader<ControllerEntry> {

    Logger logger = LoggerFactory.getLogger(ControllerEntryItemReader.class);

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
                line = line.replaceAll("(\\s)", "");
                line = line.replaceAll(",", "");

                String[] partials = line.split(":");
                if (partials.length > 1) {
                    ControllerEntry.ControllerType controllerType = ControllerEntry.ControllerType.NOT_PROCESSED_BY_THIS_TOOL;
                    String[] fieldPartials = partials[0].split("_");
                    if (fieldPartials.length > 1) {

                        int controllId = Integer.parseInt(fieldPartials[0].replaceAll("\"*",""));
                        String controlFeatureAsString = fieldPartials[1];
                        if (controllId >= 32 && controllId <= 47 ) {
                            controllerType = ControllerEntry.ControllerType.KNOB;
                        }
                        else if (controllId == 48) {
                            controllerType = ControllerEntry.ControllerType.BIG_KNOB;
                        }
                        else if (controllId >= 112 && controllId <= 127) {
                            controllerType = ControllerEntry.ControllerType.PAD;
                        }
                        else if (controllId == 88) {
                            controllerType = ControllerEntry.ControllerType.PLAY;
                        }
                        else if (controllId == 89) {
                            controllerType = ControllerEntry.ControllerType.STOP;
                        }
                        else {
                            controllerType = ControllerEntry.ControllerType.NOT_PROCESSED_BY_THIS_TOOL;

                        }
                    }
                    ControllerEntry controllerEntry = new ControllerEntry(partials[0],  partials[1], controllerType);
                    return controllerEntry;
                }
            }
            return null;
        }
}
