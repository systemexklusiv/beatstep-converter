package net.systemexklusiv.controllerman.beatstepconverter;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class BeatstepConverterApplicationTests {

	static private String source = "src/main/resources/preset.beatstep";

	@Value("${target}")
	private String target;

	@Autowired
	HasFileToListOfLinesReader fileToListOfLinesReader;

	@Autowired
	ControllerEntryItemReader controllerEntryItemReader;

	@Test
	void whenCallingSayHello_thenReturnHello() {
		List<String> listOfLines = fileToListOfLinesReader.getContendAsListOfLines();
		Assert.assertNotNull(listOfLines);




	}

	//source=src/main/resources/preset.beatstep
	//target=src/main/resources/changed.preset.beatstep

	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {
		@Bean
		public  HasFileToListOfLinesReader fileToListOfLinesReader() {
			return new FileToListOfLinesReader(source);
		}

		@Bean
		public ControllerEntryItemReader controllerEntryItemReader(HasFileToListOfLinesReader hasFileToListOfLinesReader) {
			return new ControllerEntryItemReader(hasFileToListOfLinesReader);
		}
		
	}

}
