package net.systemexklusiv.controllerman.beatstepconverter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class BeatstepConverterApplication implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;

	@Value("${file.input}")
	private String input;

	@Value("${file.output}")
	private String output;

	@Autowired
	@Qualifier("transformBeatstepPreset")
	private Job transformBeatstepPreset;

	public static void main(String[] args) {
		SpringApplication.run(BeatstepConverterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		JobParametersBuilder paramsBuilder = new JobParametersBuilder();
		paramsBuilder.addString("file.input", input);
		paramsBuilder.addString("file.output", output);
		jobLauncher.run(transformBeatstepPreset, paramsBuilder.toJobParameters());
	}
}
