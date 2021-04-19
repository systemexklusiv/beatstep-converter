package net.systemexklusiv.controllerman.beatstepconverter;

import java.util.Date;

public class Constants {

    public static class Options {
        public final static String ALL_CHANNEL = "allChannel";

        public final static String ALL_KNOB_CHANNEL = "allKnobChannel";
        public final static String ALL_PAD_CHANNEL = "allPadChannel";
        public final static String KNOB_CHANNEL_STARTING_AT = "knobChannelStartingAt";
//        public final static String PAD_CHANNEL_STARTING_AT = "padChannelStartingAt";

        public final static String ALL_KNOBS_CC = "allKnobsCc";
        public final static String ALL_PADS_CC = "allPadsCc";

        public final static String KNOB_CC_STARTING_AT = "knobCcStartingAt";
        public final static String KNOB_CC_STARTING_FROM = "knobCcStartingFrom";
        public final static String PAD_NOTE_STARTING_AT = "padNoteStartingAt";
        public final static String PAD_NOTE_STARTING_FROM = "padNoteStartingFrom";


        public final static String SOURCE = "source";
        public final static String TARGET = "target";
        public static final String ALL_KNOB_MIN = "allKnobMin";
        public static final String ALL_KNOB_MAX = "allKnobMax";
        public static final String ALL_PAD_TO_OPTION_MIDI_NOTE = "allPadToOptionMidiNote";
        public static final String ALL_PAD_TO_OPTION_SWITCHED_CONTROL = "allPadToOptionSwitchedControl";
        public static final String ALL_PAD_MIN = "allPadMin";
        public static final String ALL_PAD_MAX = "allPadMax";

        private static final String PREFIX = "--";
        public static final String DEFAULT_TARGET_FILENAME = "createdByBeatstepConverter.beatstep";
        private static final String MANUAL = System.lineSeparator() + "------------ INSTRUCTIONS ------------" + System.lineSeparator()
                + System.lineSeparator()
                + "beatstep-converter by David Rival. Source code here: https://github.com/systemexklusiv" + System.lineSeparator()
                + System.lineSeparator()
                + "I wrote this app because the UI editor of the beatstep is anoying me with too many repeating tasks.. so I descied instead of spending hours of clicking and scrolling in writing this tool :-D" + System.lineSeparator()
                + "Application arguments must be supplied like: --OPTION=VALUE" + System.lineSeparator()
                + "Where one option is followed by one number or one text for input and output directory or nothing (than the equals is omitted) as explained in the list of arguments below" + System.lineSeparator()
                + "Note that each option starts with a double minus followed by the name (better copy paste it!) and an equals symbol and ohne digit or text or nothing" + System.lineSeparator()
                + "example: $ beatstep-converter --allChannel=0 --allPadToOptionSwitchedControl --source=source-source-preset.beatstep --target=target-source-preset.beatstep " + System.lineSeparator()
                + "This will take the original file called source-source-preset.beatstep set all knobs and pads to channel 1, sets all pads to option switchedCC (instead of the default midi note)" + System.lineSeparator() +
                "and create a new file named target-source-preset.beatstep. the resulting file will be created" + System.lineSeparator()
                + System.lineSeparator()
                + "You can use the beatstep-converter miltiple time so you don't have to make all adjustments in one call, because the rest of assingments got unaltered piped through." + System.lineSeparator() +
                " F.i. in one call you can just change the channel, in another call the midi notes of the pads in another call somthing else. You can always load in the results in between and check if its to your satisfactory :-)" + System.lineSeparator()
                + System.lineSeparator()
                + "ALWAYS BACKUP YOU WORK - ALWAYS MAKE A COPY SOMEWHERE - I TAKE NO RISK OF DATA LOSS AND OTHER PROBLEMS WHICH MAY ARISE"
                + System.lineSeparator()
                + System.lineSeparator()
                + "Chain the following Options one after the other" + System.lineSeparator()
                +  PREFIX + SOURCE + "=myNewFilename.beatstep !!MANDATORY!! source file to be transformed - a path to a filename RELATIVE to this application or a filename when its next to the application" + System.lineSeparator()
                +  PREFIX + TARGET + " When not supplied, a auto-Filename will be created like <TIMESTAMP>" + DEFAULT_TARGET_FILENAME +" as the result of the conversion. The file will be created next to the application" + System.lineSeparator()
                + PREFIX + ALL_CHANNEL + "=0 to 15 -> Sets the midichannel for all knobs and pads. thus 0-based. 0 means channel 1 and 15 channel 16" + System.lineSeparator()
                + PREFIX +  ALL_KNOB_CHANNEL + "=0 to 15 thus 0-based -> Sets the midichannel for all knobs. 0 means channel 1 and 15 channel 16" + System.lineSeparator()
                + PREFIX +  ALL_PAD_CHANNEL + "= 0 to 15 thus 0-based -> Sets the midichannel for all Pads. 0 means channel 1 and 15 channel 16" + System.lineSeparator()
                + PREFIX +  PAD_NOTE_STARTING_AT + "=0 to 127 -> when set to e.g. 16 the 16 pads will be set to note where pad #1 is note 16, pad 2 note 17 so on" + System.lineSeparator()
                + PREFIX +  PAD_NOTE_STARTING_FROM + "=0 to 127 -> when set to e.g. 16 the 16 pads will be set to note where pad #1 is note 16, pad 2 note 15, pad 3 note 14 so on" + System.lineSeparator()
                + PREFIX +  ALL_PAD_TO_OPTION_MIDI_NOTE + " followed by no = and further params -> sets all pads to midi note" + System.lineSeparator()
                + PREFIX + ALL_PAD_TO_OPTION_SWITCHED_CONTROL + " followed by no = and further params -> sets all pads to switched control" + System.lineSeparator()
                + PREFIX +  PAD_NOTE_STARTING_AT + "=0 to 127 -> when set to e.g. 16 the 16 pads will be set to note where pad #1 is note 16, pad 2 note 17 so on" + System.lineSeparator()
                +  PREFIX + KNOB_CC_STARTING_AT + "=0 to 127 -> when set to e.g. 16 the 16 knobs will be set to cc where knobs #1 is cc 16, knobs 2 cc 17 so on" + System.lineSeparator()
                +  PREFIX + KNOB_CC_STARTING_FROM + "=0 to 127 -> when set to e.g. 16 the 16 knobs will be set to cc where knobs #1 is cc 16, knobs 2 cc 15, knobs 3 cc 14 so on" + System.lineSeparator()
                +  PREFIX + ALL_KNOB_MIN + "=0 to 127 -> sets the min for all knobs together" + System.lineSeparator()
                +  PREFIX + ALL_KNOB_MAX + "=0 to 127 -> sets the max for all knobs together" + System.lineSeparator()
                +  PREFIX + ALL_PAD_MIN + "=0 to 127 -> sets the min for all pads together" + System.lineSeparator()
                +  PREFIX + ALL_PAD_MAX + "=0 to 127 -> sets the max for all pads together" + System.lineSeparator()
                + System.lineSeparator() + System.lineSeparator()
                + "Have Fun and share your results :-)";;

        public static String manual() {
            return   MANUAL;

        }
    }
}
