package net.systemexklusiv.controllerman.beatstepconverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

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
        // @formatter:off
        return jobBuilderFactory
                .get("transformBeatstepPreset")
                .flow(step1)
                .end()
                .build();
        // @formatter:on
    }
}
