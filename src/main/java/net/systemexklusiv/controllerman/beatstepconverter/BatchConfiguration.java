package net.systemexklusiv.controllerman.beatstepconverter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Date;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public HasFileToListOfLinesReader hasFileToListOfLinesReader(@Value("#{jobParameters['source']}") String input) {
        return new FileToListOfLinesReader(input);
    }

    @Bean
    @StepScope
    public ControllerEntryItemReader controllerEntryItemReader(HasFileToListOfLinesReader hasFileToListOfLinesReader) {
        return new ControllerEntryItemReader(hasFileToListOfLinesReader);
    }

    @Bean
    @StepScope
    public ControllerEntryItemProcessor processor(
            @Value("#{jobParameters['allChannel']}") String allChannel,
            @Value("#{jobParameters['allKnobChannel']}") String allKnobChannel,
            @Value("#{jobParameters['allPadChannel']}") String allPadChannel,
            @Value("#{jobParameters['padNoteStartingAt']}") String padNoteStartingAt,
            @Value("#{jobParameters['padNoteStartingFrom']}") String padNoteStartingFrom,
            @Value("#{jobParameters['allPadToOptionMidiNote']}") String allPadToOptionMidiNote,
            @Value("#{jobParameters['allPadToOptionMidiNote']}") String allPadToOptionSwitchedControl,
            @Value("#{jobParameters['allPadMin']}") String allPadMin,
            @Value("#{jobParameters['allPadMax']}") String allPadMax,
            @Value("#{jobParameters['allKnobMin']}") String allKnobMin,
            @Value("#{jobParameters['allKnobMax']}") String allKnobMax,
            @Value("#{jobParameters['knobCcStartingAt']}") String knobCcStartingAt,
            @Value("#{jobParameters['knobCcStartingFrom']}") String knobCcStartingFrom,
            @Value("#{jobParameters['allPadToToggle']}") String allPadToToggle,
            @Value("#{jobParameters['allPadToGate']}") String allPadToGate

                                                  ) {
        return new ControllerEntryItemProcessor(
                allChannel,
                allKnobChannel,
                allPadChannel,
                padNoteStartingAt,
                padNoteStartingFrom,
                allPadToOptionMidiNote,
                allPadToOptionSwitchedControl,
                allPadMin,
                allPadMax,
                allKnobMin,
                allKnobMax,
                knobCcStartingAt,
                knobCcStartingFrom,
                allPadToToggle,
                allPadToGate
                );
    }
    @Bean
    @StepScope
    public ControllerEntryItemWriter controllerEntryItemWriter (@Value("#{jobParameters['target']}") String output) {
        output = output != null ? output : new Date().getTime() + "_" + Constants.Options.DEFAULT_TARGET_FILENAME;
        return new ControllerEntryItemWriter(output);
    }

    @Bean
    public Step step1(ControllerEntryItemReader controllerEntryItemReader,ControllerEntryItemProcessor processor, ControllerEntryItemWriter controllerEntryItemWriter) {
        return stepBuilderFactory.get("transformBeatstepPreset")
                .<ControllerEntry, ControllerEntry> chunk(300)
                .reader(controllerEntryItemReader)
                .processor(processor)
                .writer(controllerEntryItemWriter)
                .build();
    }

    @Bean(name = "transformBeatstepPreset")
    public Job transformBeatstepPreset(Step step1) throws IOException {
        return jobBuilderFactory
                .get("transformBeatstepPreset")
                .flow(step1)
                .end()
                .build();
    }
}
