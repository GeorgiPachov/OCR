package com.fmitextrecognition.io;

import java.awt.Dimension;
import java.io.File;

import com.fmitextrecognition.utils.Utils;
import org.apache.commons.cli.Options;

public class ConstantsAndThresholds {
	private final static String TEST_IMAGE_STRING = "/data/globe.png";

	public final static File TEST_FILE = new File(new File("").getAbsolutePath(), TEST_IMAGE_STRING);

	public final static File WRITABLE_DIRECTORY = Utils.isWindows() ? new File("C:\\Temp\\") : new File("/tmp/");

	public final static File TEST_WRITE_FILE = Utils.isWindows() ? new File(WRITABLE_DIRECTORY, "test.bmp") : new File(
			WRITABLE_DIRECTORY, "test.bmp");

	public static Dimension CHARACTER_CLASSES_DIMENSIONS = new Dimension(30, 30);

	public static File INPUT_FILE = null;
	
	public static int NUMBER_OF_THREADS = 1;

	public static boolean VERBOSE = false;

	public static final boolean DEBUG_MODE = true;

	public static final boolean USE_CACHE = false;


	public static final String OPTION_NUMBER_THREADS = "threads";

	public static final String OPTION_SAMPLE_X = "sampleX";

	public static final String OPTION_SAMPLE_Y = "sampleY";

	public static final String OPTION_FILE = "file";

	public static final String OPTION_VERBOSE = "verbose";

	public static final Options OPTIONS = createOptions();


	
	private static Options createOptions() {
		Options options = new Options();
		options.addOption(OPTION_FILE, true, "Mandatory!: path to the file");
		options.addOption(OPTION_NUMBER_THREADS, true, "Number of threads to use");
		options.addOption(OPTION_SAMPLE_X, true, "Width of the samples that will be used");
		options.addOption(OPTION_SAMPLE_Y, true, "Height of the samples that will be used");
		options.addOption(OPTION_VERBOSE, false, "Detailed verbose of recognition");
		return options;
	}
}
