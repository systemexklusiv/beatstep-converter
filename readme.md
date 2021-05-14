
        /
    ---/__-----__-----__---_/_----__---_/_-----__-------__----------__-----__-----__-----------__----)__---_/_-----__----)__-
      /   )  /___)  /   )  /     (_ `  /     /___)    /   ) ===   /   '  /   )  /   )  | /   /___)  /   )  /     /___)  /   )
    _(___/__(___ __(___(__(_ ___(__)__(_ ___(___ ____/___/_______(___ __(___/__/___/___|/___(___ __/______(_ ___(___ __/_____
                                                /
                                               /
                                              /


# beatstep-converter
<p>Source code here: https://github.com/systemexklusiv

### [ INSTRUCTIONS ]


I wrote this app because the UI editor of the beatstep is anoying me with too many repeating tasks.. so I descied instead of spending hours of clicking and scrolling in writing this tool :-D
Application arguments must be supplied like: `--OPTION=VALUE`
Where one option is followed by one number or one text for input and output directory or nothing (than the equals is omitted) as explained in the list of arguments below
    
Note that each option starts with a double minus followed by the name (better copy paste it!) and an equals symbol and a digit or a word or nothing

example: `$ beatstep-converter --allChannel=0 --allPadToOptionSwitchedControl --source=source-preset.beatstep --target=target-preset.beatstep`

This will take the original file called source-preset.beatstep set all knobs and pads to channel 1, sets all pads to option switchedCC (instead of the default midi note)
and create a new file named target-preset.beatstep. the resulting file will be created next to the app.

You can use the beatstep-converter miltiple time so you don't have to make all adjustments in one call, because the rest of assingments got unaltered piped through.
F.i. in one call you can just change the channel, in another call the midi notes of the pads in another call somthing else. You can always load in the results in between and check if its to your satisfactory :-)

**ALWAYS BACKUP YOU WORK - ALWAYS MAKE A COPY SOMEWHERE - I TAKE NO RISK OF DATA LOSS AND OTHER PROBLEMS WHICH MAY ARISE**

### Chain the following Options one after the other:

    --source=myNewFilename.beatstep !!MANDATORY!! source file to be transformed - a path to a filename RELATIVE to this application or a filename when its next to the application </p>
    --target When not supplied, a auto-Filename will be created like TIMESTAMPcreatedByBeatstepConverter.beatstep as the result of the conversion. The file will be created next to the application </p>
    --allChannel=0 to 15 -> Sets the midichannel for all knobs and pads. thus 0-based. 0 means channel 1 and 15 channel 16 </p>
    --allKnobChannel=0 to 15 thus 0-based -> Sets the midichannel for all knobs. 0 means channel 1 and 15 channel 16 </p>
    --allPadChannel= 0 to 15 thus 0-based -> Sets the midichannel for all Pads. 0 means channel 1 and 15 channel 16 </p>
    --padNoteStartingAt=0 to 127 -> when set to e.g. 16 the 16 pads will be set to note where pad #1 is note 16, pad 2 note 17 so on </p>
    --padNoteStartingFrom=0 to 127 -> when set to e.g. 16 the 16 pads will be set to note where pad #1 is note 16, pad 2 note 15, pad 3 note 14 so on     --allPadToOptionMidiNote followed by no = and further params -> sets all pads to midi note
    --allPadToOptionSwitchedControl followed by no = and further params -> sets all pads to switched control
    --padNoteStartingAt=0 to 127 -> when set to e.g. 16 the 16 pads will be set to note where pad #1 is note 16, pad 2 note 17 so on
    --knobCcStartingAt=0 to 127 -> when set to e.g. 16 the 16 knobs will be set to cc where knobs #1 is cc 16, knobs 2 cc 17 so on
    --knobCcStartingFrom=0 to 127 -> when set to e.g. 16 the 16 knobs will be set to cc where knobs #1 is cc 16, knobs 2 cc 15, knobs 3 cc 14 so on
    --allKnobMin=0 to 127 -> sets the min for all knobs together
    --allKnobMax=0 to 127 -> sets the max for all knobs together
    --allPadMin=0 to 127 -> sets the min for all pads together
    --allPadMax=0 to 127 -> sets the max for all pads together


### Ex: Octatrack Track Sample Pitch from +0 semi to 12, assuming there is a beatstep.preset which is called `source-preset.beatstep` next to the app:

    --allChannel=10
    --source=source-preset.beatstep
    --target=OT_0to12Pitch_startAt84.beatstep
    --padNoteStartingAt=84

*** Have Fun and share your results :-) ***
