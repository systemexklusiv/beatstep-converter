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

import static net.systemexklusiv.controllerman.beatstepconverter.Constants.*;

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

//		paramsBuilder.addString("source", source);
//		paramsBuilder.addString("target", target);

		if (args.containsOption(Options.ALL_CHANNEL)) {
			int channel = Integer.valueOf(args.getOptionValues(Options.ALL_CHANNEL).get(0));
			checkChannel(channel);
			paramsBuilder.addString(Options.ALL_CHANNEL, args.getOptionValues(Options.ALL_CHANNEL).get(0));
		}

		if (args.containsOption(Options.ALL_KNOB_CHANNEL)) {
			int channel = Integer.valueOf(args.getOptionValues(Options.ALL_KNOB_CHANNEL).get(0));
			checkCcAndNoteRange(channel);
			paramsBuilder.addString(Options.ALL_KNOB_CHANNEL, args.getOptionValues(Options.ALL_KNOB_CHANNEL).get(0));
		}

		if (args.containsOption(Options.ALL_PAD_CHANNEL)) {
			int channel = Integer.valueOf(args.getOptionValues(Options.ALL_PAD_CHANNEL).get(0));
			checkCcAndNoteRange(channel);
			paramsBuilder.addString(Options.ALL_PAD_CHANNEL, args.getOptionValues(Options.ALL_PAD_CHANNEL).get(0));
		}

		if (args.containsOption(Options.PAD_NOTE_STARTING_AT)) {
			int channel = Integer.valueOf(args.getOptionValues(Options.PAD_NOTE_STARTING_AT).get(0));
			checkCcAndNoteRange(channel);
			paramsBuilder.addString(Options.PAD_NOTE_STARTING_AT, args.getOptionValues(Options.PAD_NOTE_STARTING_AT).get(0));
		}
		if (args.containsOption(Options.SOURCE)) {
			paramsBuilder.addString(Options.SOURCE, args.getOptionValues(Options.SOURCE).get(0));
		}
		if (args.containsOption(Options.TARGET)) {
			paramsBuilder.addString(Options.TARGET, args.getOptionValues(Options.TARGET).get(0));
		}

		if (paramsBuilder.toJobParameters().getParameters().size() != args.getSourceArgs().length) {
			logger.error ("Not all Options you supplied where recognized. please check the spelling of the options you supplied! These are your options: {}", args.getSourceArgs());
			logger.error ("These Options have been recognized : {}", paramsBuilder.toJobParameters().getParameters());
			printInstructions();
//			System.exit(1);
		}
		logger.info("Your application started with option names : {}", args.getSourceArgs());
		jobLauncher.run(transformBeatstepPreset, paramsBuilder.toJobParameters());
	}

	private void checkChannel(int channel) {
		if (channel < 0  ||  channel > 15 ) {
			logger.error("Channel must be in range 0 to 15! You supplied: " + channel);
			printInstructions();
			System.exit(1);
		}
	}

	private void checkCcAndNoteRange(int value) {
		if (value < 0  ||  value > 127 ) {
			logger.error("CC and Note must be in range 0 to 127! You supplied: " + value);
			printInstructions();
			System.exit(1);
		}
	}

	private void printInstructions() {
		logger.info(Options.manual());
	}

}
