package net.systemexklusiv.controllerman.beatstepconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class BeatstepConverterApplication implements ApplicationRunner {

	@Autowired
	private JobLauncher jobLauncher;

	@Value("${source}")
	private String source;

	@Value("${target}")
	private String target;

	@Autowired
	@Qualifier("transformBeatstepPreset")
	private Job transformBeatstepPreset;

	private static final Logger logger = LoggerFactory.getLogger(BeatstepConverterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BeatstepConverterApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		JobParametersBuilder paramsBuilder = new JobParametersBuilder();

		paramsBuilder.addString("source", source);
		paramsBuilder.addString("target", target);

		if (args.containsOption("channel")) {
			paramsBuilder.addString("channel", args.getOptionValues("channel").get(0));
		}
		if (args.containsOption("source")) {
			paramsBuilder.addString("source", args.getOptionValues("source").get(0));
		}
		if (args.containsOption("target")) {
			paramsBuilder.addString("target", args.getOptionValues("target").get(0));
		}
		logger.info("Your application started with option names : {}", args.getSourceArgs());

		jobLauncher.run(transformBeatstepPreset, paramsBuilder.toJobParameters());
	}
}
