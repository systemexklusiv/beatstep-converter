package net.systemexklusiv.controllerman.beatstepconverter;

import net.systemexklusiv.controllerman.beatstepconverter.converters.ChannelConverter;
import net.systemexklusiv.controllerman.beatstepconverter.converters.HasConverter;
import net.systemexklusiv.controllerman.beatstepconverter.converters.OptionConverter;
import net.systemexklusiv.controllerman.beatstepconverter.converters.StartAtConverter;
import net.systemexklusiv.controllerman.beatstepconverter.ControllerEntry.ControllerType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest
class BeatstepConverterTests {


	@Test
	void Test_PadNoteStatAtConverter() {
		int startAt = 16;
		String dummyValue = "42";
		boolean isInverted = false; // counting upwards
		HasConverter startAtConverter = new StartAtConverter(startAt, ControllerType.PAD, ControllerEntry.CONTROL_MODE_NOTE, isInverted);
		// When an entry for definition of some control mode (-1) below comes in..
		String value = startAtConverter.convert(ControllerType.PAD, ControllerEntry.CONTROL_MODE, dummyValue);
		// It'll be converted to be a Midi note
		Assert.assertEquals(ControllerEntry.CONTROL_MODE_NOTE, value);

		// When an entry for definition of some CC/Note num comes in it'll set 1st to the starting value of the app parameter..
		value = startAtConverter.convert(ControllerType.PAD, ControllerEntry.CC_NUM, dummyValue);
		// It'll be converted to be a Midi note
		Assert.assertEquals(startAt, Integer.parseInt(value));

		for (int i = 0; i < 128; i++) {
			// When an entry for definition of some CC/Note num comes in it'll set 1st to the starting value of the app parameter..
			value = startAtConverter.convert(ControllerType.PAD, ControllerEntry.CC_NUM, dummyValue);
			// It'll be converted to be a Midi note + 1 of the starting value;
			Assert.assertEquals( (startAt + 1 + i ) % 127, Integer.parseInt(value));
		}
	}

	@Test
	void Test_PadNoteStatAtConverterInverted() {
		int startAt = 16;
		String dummyValue = "42";
		boolean isInverted = true; // counting downwards
		HasConverter startAtConverter = new StartAtConverter(startAt, ControllerType.PAD, ControllerEntry.CONTROL_MODE_NOTE, isInverted);

		for (int i = 0; i < 128; i++) {
			// When an entry for definition of some CC/Note num comes in it'll set 1st to the starting value of the app parameter..
			String value = startAtConverter.convert(ControllerType.PAD, ControllerEntry.CC_NUM, dummyValue);
			// It'll be converted to be a Midi note + 1 of the starting value;
			Assert.assertEquals( (startAt - i ) % 127, Integer.parseInt(value));
		}
	}

	@Test
	void Test_ChannelConverter() {
		String expected = "15";
		HasConverter padChannelConverter = new ChannelConverter(ControllerType.PAD, 15);

		String value = padChannelConverter.convert(ControllerType.PAD, ControllerEntry.CONTROL_CHANNEL, "7");

		Assert.assertEquals( expected, value);
	}

	@Test
	void Test_PAD_OptionConverter() {
		String expected = "8"; // control option switched cc
		HasConverter optionsConverter = new OptionConverter(ControllerType.PAD, Integer.valueOf(ControllerEntry.CONTROL_MODE_SWITCHED_CC));

		String value = optionsConverter.convert(ControllerType.PAD, ControllerEntry.CONTROL_MODE, ControllerEntry.CONTROL_MODE_NOTE);

		Assert.assertEquals( expected, value);
	}

	@Test
	void Test_KNOB_OptionConverter() {
		String expected = "8"; // control option switched cc
		HasConverter optionsConverter = new OptionConverter(ControllerType.KNOB, Integer.valueOf(ControllerEntry.CONTROL_MODE_SWITCHED_CC));

		String value = optionsConverter.convert(ControllerType.KNOB, ControllerEntry.CONTROL_MODE, ControllerEntry.CONTROL_MODE_NOTE);

		Assert.assertEquals( expected, value);
	}

}
