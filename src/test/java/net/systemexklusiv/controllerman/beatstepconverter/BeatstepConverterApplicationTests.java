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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@RunWith(SpringRunner.class)
//@SpringBootTest
class BeatstepConverterApplicationTests {

//	static private String source = "src/main/resources/preset.beatstep";
//
//	@Value("${target}")
//	private String target;
//
//	@Autowired
//	HasFileToListOfLinesReader fileToListOfLinesReader;

//	@Test
//	void tesr1() {
//		List<String> listOfLines = fileToListOfLinesReader.getContendAsListOfLines();
//		Assert.assertNotNull(listOfLines);
//
//		ControllerEntryItemReader controllerEntryItemReader = new ControllerEntryItemReader(fileToListOfLinesReader);
//
//		while (controllerEntryItemReader.read() != null) {
//
//		}
//
//
//	}

	@Test
	void Test_Itemreader_Pad() {
		TestLinesReader linesReader = new TestLinesReader("        \"112_1\": 8,  ");

		ControllerEntryItemReader controllerEntryItemReader = new ControllerEntryItemReader(linesReader);
		ControllerEntry ce = controllerEntryItemReader.read();

		Assert.assertEquals("\"112_1\"", ce.field);
		Assert.assertEquals("8", ce.value);
		Assert.assertEquals(ControllerEntry.ControllerType.PAD, ce.controllerType);
	}

	@Test
	void Test_Itemreader_Knob() {
		TestLinesReader linesReader = new TestLinesReader(" \"33_6\":1,, ");

		ControllerEntryItemReader controllerEntryItemReader = new ControllerEntryItemReader(linesReader);
		ControllerEntry ce = controllerEntryItemReader.read();

		Assert.assertEquals("\"127_6\"", ce.field);
		Assert.assertEquals("1", ce.value);
		Assert.assertEquals(ControllerEntry.ControllerType.KNOB, ce.controllerType);
	}

	@Test
	void Test_Itemreader_BigKnob() {
		TestLinesReader linesReader = new TestLinesReader(" \"48_6\":1,, ");

		ControllerEntryItemReader controllerEntryItemReader = new ControllerEntryItemReader(linesReader);
		ControllerEntry ce = controllerEntryItemReader.read();

		Assert.assertEquals("\"127_6\"", ce.field);
		Assert.assertEquals("1", ce.value);
		Assert.assertEquals(ControllerEntry.ControllerType.KNOB, ce.controllerType);
	}

	class TestLinesReader implements HasFileToListOfLinesReader {

		public List<String> lines = new ArrayList<>();
		public TestLinesReader(String content) {
			lines.add(content);
		}

		@Override
		public List<String> getContendAsListOfLines() {
			return lines;
		}
	}



}
