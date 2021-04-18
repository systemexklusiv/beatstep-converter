package net.systemexklusiv.controllerman.beatstepconverter;

public class Constants {

    public static class Options {
        public final static String ALL_CHANNEL = "allChannel";

        public final static String ALL_KNOB_CHANNEL = "allKnobChannel";
        public final static String ALL_PAD_CHANNEL = "allPadChannel";
        public final static String KNOB_CHANNEL_STARTING_AT = "knobChannelStartingAt";
        public final static String PAD_CHANNEL_STARTING_AT = "padChannelStartingAt";

        public final static String ALL_KNOBS_CC = "allKnobsCc";
        public final static String ALL_PADS_CC = "allPadsCc";

        public final static String KNOB_CC_STARTING_AT = "knobCcStartingAt";
        public final static String PAD_CC_STARTING_AT = "padCcStartingAt";
        public final static String PAD_NOTE_STARTING_AT = "padNoteStartingAt";

        public final static String SOURCE = "source";
        public final static String TARGET = "target";

        public static String manual() {
            return  "------------ INSTRUCTIONS ------------" + System.lineSeparator()
                    + "Application arguments must be supplied like: --OPTION=VALUE" + System.lineSeparator()
                    + "Where one option is followed by a number or a text für input and output directory" + System.lineSeparator()
                    + "example: $ beatstep-converter --allChannel=0 --preset.beatstep --target=myTagetPreset.beatstep" + System.lineSeparator()
                    + "This will set all knobs and pads to channel 1 and create a new file. The target file must be in the same directory as this application. the resulting file will be created" + System.lineSeparator()
                    + "Chain the following Options one after the other" + System.lineSeparator()
                    + ALL_CHANNEL + " 0 to 15 thus 0-based. 0 means channel 1 and 15 channel 16" + System.lineSeparator()
                    + ALL_KNOB_CHANNEL + " 0 to 15 thus 0-based. 0 means channel 1 and 15 channel 16" + System.lineSeparator()
                    + ALL_PAD_CHANNEL + " 0 to 15 thus 0-based. 0 means channel 1 and 15 channel 16" + System.lineSeparator()
                    + ALL_KNOBS_CC + " 0 to 127" + System.lineSeparator()
                    + ALL_PADS_CC + " 0 to 127" + System.lineSeparator()
                    + KNOB_CC_STARTING_AT + " 0 to 127" + System.lineSeparator()
                    + PAD_CC_STARTING_AT + " 0 to 127" + System.lineSeparator()
                    + PAD_NOTE_STARTING_AT + " 0 to 127 -> when set to e.g. 16 the 16 pads will be set to note where pad #1 is note 16, pad 2 note 17 so on" + System.lineSeparator()
                    + KNOB_CHANNEL_STARTING_AT + " 0 to 127" + System.lineSeparator()
                    + SOURCE + " MANDATORY! source file to be transformed - a path to a filename RELATIVE to this application or a filename when its next to the application" + System.lineSeparator()
                    + TARGET + " When not supplied, a auto-Filename will be created - target file to be transformed - a path to a filename or a filename when it should be created next to the application" + System.lineSeparator();
        }
    }
}
