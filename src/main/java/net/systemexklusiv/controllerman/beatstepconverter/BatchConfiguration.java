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
    public JsonItemReader<ControllerEntry> jsonItemReader(@Value("#{jobParameters['file.input']}") String input) {
        final ObjectMapper mapper = new ObjectMapper();

        final JacksonJsonObjectReader<ControllerEntry> jsonObjectReader = new JacksonJsonObjectReader<>(
                ControllerEntry.class);
        jsonObjectReader.setMapper(mapper);

        Resource cpr = new ClassPathResource(input);
        return new JsonItemReaderBuilder<ControllerEntry>()
                .jsonObjectReader(jsonObjectReader)
                .resource(cpr)
                .name("myReader")
                .build();
    }

    @Bean
    @StepScope
    public ControllerEntryItemReader controllerEntryItemReader(@Value("#{jobParameters['file.input']}") String input) {
        return new ControllerEntryItemReader(input);
    }

    @Bean
    public ControllerEntryItemProcessor processor() {
        return new ControllerEntryItemProcessor();
    }

    @Bean
    @StepScope
    public JsonFileItemWriter<ControllerEntry> jsonItemWriter(
            @Value("#{jobParameters['file.output']}") String output) throws IOException {
        JsonFileItemWriterBuilder<ControllerEntry> builder = new JsonFileItemWriterBuilder<>();
        JacksonJsonObjectMarshaller<ControllerEntry> marshaller = new JacksonJsonObjectMarshaller<>();
        return builder
                .name("controllerEntryItemWriter")
                .jsonObjectMarshaller(marshaller)
                .resource(new FileSystemResource(output))
                .build();
    }


    @Bean
    public Step step1(ControllerEntryItemReader controllerEntryItemReader,JsonFileItemWriter<ControllerEntry> jsonItemWriter) {
        return stepBuilderFactory.get("step1")
                .<ControllerEntry, ControllerEntry> chunk(10)
                .reader(controllerEntryItemReader)
                .processor(processor())
                .writer(jsonItemWriter)
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
