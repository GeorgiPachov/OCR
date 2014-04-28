import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.fmitextrecognition.io.Bitmap;
import com.fmitextrecognition.io.BitmapUtils;
import com.fmitextrecognition.io.ConstantsAndThresholds;
import com.fmitextrecognition.tools.Logger;
import com.fmitextrecognition.tools.TextRecognizer;
import com.fmitextrecognition.tools.TimeTracker;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		Options options = ConstantsAndThresholds.OPTIONS;
		CommandLineParser parser = new PosixParser();
		try {
			parseAndSetSettings(args, options, parser);
			
		} catch (Throwable t) {
			t.printStackTrace();
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("textrecognizer.jar", ConstantsAndThresholds.OPTIONS);
			Logger.log("Please instantiate with the" + " proper arguments!");
			return;
		}

		File inputFile = ConstantsAndThresholds.INPUT_FILE;
		Bitmap ourBitmap = BitmapUtils.readImage(inputFile);

		Logger.log("Starting text recognition for file: " + inputFile.getAbsolutePath());

		TimeTracker tracker = new TimeTracker("Text recognition").start();
		Map<String, String> matchedText = new TextRecognizer().getText(ourBitmap);
		tracker.end();

		for (String method : matchedText.keySet()) {
			Logger.log("Match text with " + method + ": ");
			for (String line : matchedText.get(method).split("\n")) {
				Logger.log(line);
			}
		}
	}

	private static void parseAndSetSettings(String[] args, Options options, CommandLineParser parser) throws ParseException {
		CommandLine commandLine;
		commandLine = parser.parse(options, args);
		// mandatory!
		File file = new File(commandLine.getOptionValue(ConstantsAndThresholds.OPTION_FILE));
		int numberOfThreads = 1;
		int dimensionX = 30;
		int dimensionY = 30;
		boolean verbose = false;

		if (commandLine.hasOption(ConstantsAndThresholds.OPTION_SAMPLE_X)) {
			dimensionX = Integer.parseInt(commandLine.getOptionValue(ConstantsAndThresholds.OPTION_SAMPLE_X));
		}

		if (commandLine.hasOption(ConstantsAndThresholds.OPTION_SAMPLE_Y)) {
			dimensionY = Integer.parseInt(commandLine.getOptionValue(ConstantsAndThresholds.OPTION_SAMPLE_Y));
		}
		
		if (commandLine.hasOption(ConstantsAndThresholds.OPTION_NUMBER_THREADS)) {
			numberOfThreads = Integer.parseInt(commandLine.getOptionValue(ConstantsAndThresholds.OPTION_NUMBER_THREADS));
		}
		
		verbose = commandLine.hasOption(ConstantsAndThresholds.OPTION_VERBOSE);
			
		
		ConstantsAndThresholds.INPUT_FILE = file;
		ConstantsAndThresholds.CHARACTER_CLASSES_DIMENSIONS = new Dimension(dimensionX, dimensionY);
		ConstantsAndThresholds.NUMBER_OF_THREADS = numberOfThreads;
		ConstantsAndThresholds.VERBOSE = verbose;
	}

}
