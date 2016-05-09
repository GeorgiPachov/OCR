package com.fmitextrecognition.io;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fmitextrecognition.characterclasses.CharacterClassesFactory;
import com.fmitextrecognition.filters.BinarizationFilter;
import com.fmitextrecognition.filters.GreyscaleFilter;
import com.fmitextrecognition.segmentation.BitmapTextReader;
import com.fmitextrecognition.segmentation.Line;
import com.fmitextrecognition.tools.Logger;
import com.fmitextrecognition.tools.TextRecognizer;
import com.fmitextrecognition.utils.Utils;

public class AllTests {

	private Bitmap bitmap;

	@Before
	public void setUp() throws IOException {
		ConstantsAndThresholds.TEST_WRITE_FILE.delete();
		this.bitmap = BitmapUtils.readImage(ConstantsAndThresholds.TEST_FILE);
	}

	@After
	public void tearDown() {
		ConstantsAndThresholds.TEST_WRITE_FILE.delete();
	}

	@Test
	public void testImageIO() throws Exception {
		setUp();

		asserting(bitmap.getHeight() > 0);
		asserting(bitmap.getWidth() > 0);
		asserting(bitmap.cloneMatrix().length != 0);

		asserting(!ConstantsAndThresholds.TEST_WRITE_FILE.exists());
		BitmapUtils.saveToFile(bitmap, ConstantsAndThresholds.TEST_WRITE_FILE);
		asserting(ConstantsAndThresholds.TEST_WRITE_FILE.exists());

		tearDown();
	}

	@Test
	public void testBitmapTextReader() throws IOException {
		setUp();

		BinarizationFilter filter = new BinarizationFilter();
		bitmap = filter.filter(bitmap);
		final BitmapTextReader reader = new BitmapTextReader();

		List<Line> lines = reader.getCharPositions(bitmap);
		asserting(!lines.isEmpty());
		Assert.assertEquals(2, lines.size());
		Assert.assertTrue(lines.get(0).getCharacters().size() + lines.get(0).getCharacters().size() == 26);

		String text = reader.getText(bitmap, CharacterClassesFactory.buildAsciiCharacterClasses());
		asserting(text != null);
		asserting(text.length() > 0);
		Logger.log(text);
		Assert.assertEquals("ABCDEFGHIJKLM\nNOPQRSTUVWXYZ\n"

		, text);
	}

	@Test
	public void testBoundingBox() {
		final ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(-1, -1));
		points.add(new Point(0, -1));
		points.add(new Point(2, 2));
		points.add(new Point(-2, -2));

		asserting(Utils.findBoundingBox(points).x == -2);
		asserting(Utils.findBoundingBox(points).y == -2);
		asserting(Utils.findBoundingBox(points).width == 5);
		asserting(Utils.findBoundingBox(points).height == 5);
	}

	@Test
	public void testGreyscaleFilter() throws IOException {
		setUp();

		int[][] greyscaleMatrix = new GreyscaleFilter().filter(bitmap).cloneMatrix();
		for (int x = 0; x < greyscaleMatrix.length; x++) {
			for (int y = 0; y < greyscaleMatrix[0].length; y++) {
				int greyscaledPixelColor = greyscaleMatrix[x][y];
				int red = BitmapUtils.getRed(greyscaledPixelColor);
				int blue = BitmapUtils.getBlue(greyscaledPixelColor);
				int green = BitmapUtils.getGreen(greyscaledPixelColor);
				asserting(red == green && green == blue);
			}
		}

		tearDown();
	}

	@Test
	public void testBinarizationFilter() throws IOException {
		setUp();

		int[][] binariziedMatrix = new BinarizationFilter().filter(bitmap).cloneMatrix();
		for (int x = 0; x < binariziedMatrix.length; x++) {
			for (int y = 0; y < binariziedMatrix[0].length; y++) {
				int greyscaledPixelColor = binariziedMatrix[x][y];
				int red = BitmapUtils.getRed(greyscaledPixelColor);
				asserting(red == 0 || red == 255);
			}
		}

		tearDown();
	}


	private static void asserting(boolean condition) {
		if (!condition) {
			throw new AssertionError();
		}
	}

	public void testApp() throws IOException, InterruptedException, ExecutionException {
		TextRecognizer recognizer = new TextRecognizer(4);

		Bitmap bitmap = BitmapUtils.readImage(ConstantsAndThresholds.TEST_FILE);
		Logger.log(recognizer.getText(bitmap).values().toString());
	}

}
