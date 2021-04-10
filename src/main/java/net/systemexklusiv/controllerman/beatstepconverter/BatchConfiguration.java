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
    public ControllerEntryItemProcessor processor(@Value("#{jobParameters['channel']}") String channel) {
        return new ControllerEntryItemProcessor(channel);
    }
    @Bean
    @StepScope
    public ControllerEntryItemWriter controllerEntryItemWriter (@Value("#{jobParameters['target']}") String output) {
        return new ControllerEntryItemWriter(output);
    }

    @Bean
    public Step step1(ControllerEntryItemReader controllerEntryItemReader,ControllerEntryItemProcessor processor, ControllerEntryItemWriter controllerEntryItemWriter) {
        return stepBuilderFactory.get("step1")
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
