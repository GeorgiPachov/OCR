package com.fmitextrecognition.characterclasses;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import com.fmitextrecognition.io.Bitmap;
import com.fmitextrecognition.io.BitmapImpl;
import com.fmitextrecognition.io.BitmapUtils;
import com.fmitextrecognition.io.ConstantsAndThresholds;
import com.fmitextrecognition.tools.CharMatch;
import com.fmitextrecognition.tools.Logger;

public class CharacterClass {

	private final char character;
	private List<Bitmap> classInstances = new ArrayList<Bitmap>();
	private final float classWeight;
	// Attributes other then matched pixels may be added for classification.
	private final HashMap<String, Integer> otherAttributes;

	public CharacterClass(char c, float weight, List<Font> fontsToUse ) {
		this.character = c;
		classInstances = buildInstaces(fontsToUse);
		classWeight = weight;
		otherAttributes = new HashMap<String, Integer>();
	}

	public CharacterClass setAttribute(String name, Integer value) {
		otherAttributes.put(name, value);
		return this;
	}

	public HashMap<String, Integer> getAttributes() {
		return otherAttributes;
	}

	private List<Bitmap> buildInstaces(List<Font> allFonstFonts) {
		final int width = ConstantsAndThresholds.CHARACTER_CLASSES_DIMENSIONS.width;
		final int height = ConstantsAndThresholds.CHARACTER_CLASSES_DIMENSIONS.height;

		List<Bitmap> returnedList = new ArrayList<Bitmap>();

		for (int i = 0; i < allFonstFonts.size(); i++) {
			Font currentFont = allFonstFonts.get(i);

			if (currentFont.canDisplay(character)) {
				Bitmap instance = null;
				if (ConstantsAndThresholds.USE_CACHE) {
					instance = loadCharacterBitmap(currentFont);
				}

				if (instance == null) {
					instance = new BitmapImpl(buildCharacterBitmap(width, height, currentFont));
				}

				if (ConstantsAndThresholds.USE_CACHE /*
													 * ||
													 * ConstantsAndThresholds.
													 * DEBUG_MODE
													 */) {
					try {
						BitmapUtils.saveToFile(instance, getCharacterBitmapFilepath(currentFont));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				returnedList.add(instance);
			}

		}
		return returnedList;
	}

	private File getCharacterBitmapFilepath(Font font) {
		return new File(ConstantsAndThresholds.WRITABLE_DIRECTORY, character + font.getFontName() + ".bmp");
	}

	private BufferedImage buildCharacterBitmap(final int width, final int height, Font currentFont) {
		final String textToDraw = String.valueOf(character);

		BufferedImage backingImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphicsContext = backingImage.createGraphics();

		currentFont = currentFont.deriveFont(getOptimalFontSize(currentFont, width, height, graphicsContext));

		graphicsContext.setFont(currentFont);

		// Disable, we want them clear and sharp!!
		graphicsContext.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		graphicsContext.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		graphicsContext.setBackground(Color.white);
		graphicsContext.clearRect(0, 0, width, height);

		Color drawingColor = Color.black;

		graphicsContext.setColor(drawingColor);

		FontMetrics fm = graphicsContext.getFontMetrics();

		graphicsContext.drawString(textToDraw, 0, height - fm.getMaxDescent() - 1);

		Rectangle2D boundingBox = getBoundingBox(backingImage);
		graphicsContext.clearRect(0, 0, width, height);

		final int tx = (int) -boundingBox.getMinX();
		final int ty = (int) -boundingBox.getMinY();

//		double sx = width / boundingBox.getWidth();
//		double sy = height / boundingBox.getHeight();
		// graphicsContext.scale(sx, sy);
		graphicsContext.translate(tx, ty);
		graphicsContext.drawString(textToDraw, 0, (int) fm.getMaxAscent());
		return backingImage.getSubimage(0, 0, (int) boundingBox.getWidth(), (int) boundingBox.getHeight());
		// return backingImage;
	}

	private Bitmap loadCharacterBitmap(Font currentFont) {
		try {
			return BitmapUtils.readImage(getCharacterBitmapFilepath(currentFont));
		} catch (Exception e) {
			Logger.log("Attempt to load character class for " + character + "from font" + currentFont.getFontName() + "from file"
					+ getCharacterBitmapFilepath(currentFont) + " has failed, probably cache has been removed.");
		}
		return null;
	}

	// TODO: Use binary search to optimize character class building
	private float getOptimalFontSize(Font currentFont, int rasterWidth, int rasterHeight, Graphics2D graphicsContext) {
		FontMetrics fontMetrics = graphicsContext.getFontMetrics(currentFont);
		while (getInterval(fontMetrics, rasterWidth, rasterHeight) > 0) {
			currentFont = currentFont.deriveFont((float) currentFont.getSize() + 1);
			fontMetrics = graphicsContext.getFontMetrics(currentFont);
		}
		return currentFont.getSize() - 1;
	}

	private int getInterval(FontMetrics metrics, int rasterWidth, int rasterHeight) {
		return Math.min(rasterHeight - getFontHeight(metrics), rasterWidth - metrics.charWidth(character));
	}

	private static int getFontHeight(FontMetrics metrics) {
		return metrics.getAscent() + metrics.getDescent();
	}

	private Rectangle2D getBoundingBox(BufferedImage backingImage) {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (int i = 0; i < backingImage.getHeight(); i++) {
			for (int j = 0; j < backingImage.getWidth(); j++) {
				if (backingImage.getRGB(j, i) == -16777216) { // minimum
																// intensity on
					// the blue channel!
					if (i < minY) {
						minY = i;
					}
					if (i > maxY) {
						maxY = i;
					}

					if (j < minX) {
						minX = j;
					}

					if (j > maxX) {
						maxX = j;
					}
				}
			}
		}
		return new Rectangle(minX, minY, maxX - minX + 1, maxY - minY + 1);
	}

	/**
	 * 
	 * @param bitmap
	 * @return a priority queue of CharMatch for the bitmap. A heuristic can be
	 *         used for this.
	 */
	public PriorityQueue<CharMatch> getMatchesFor(Bitmap bitmap) {

		PriorityQueue<CharMatch> matches = new PriorityQueue<CharMatch>();

		float aspectRatio = (float) bitmap.getWidth() / bitmap.getHeight();
		for (Bitmap instanceBitmap : classInstances) {
			float instanceAspectRatio = (float) instanceBitmap.getWidth() / instanceBitmap.getHeight();
			float difference = Math.abs(aspectRatio - instanceAspectRatio);

			if (difference < 0.6f) {
				int inputPixels = bitmap.getHeight() * bitmap.getWidth();
				int instancePixels = instanceBitmap.getHeight() * instanceBitmap.getWidth();
				Bitmap rescaledInput = null;
				if (instancePixels > inputPixels) {
					rescaledInput = bitmap.rescale(instanceBitmap.getWidth(), instanceBitmap.getHeight());
				} else{
					rescaledInput = bitmap;
					instanceBitmap = instanceBitmap.rescale(bitmap.getWidth(), bitmap.getHeight());
				}

				int totalPixels = rescaledInput.getHeight() * rescaledInput.getWidth();
				
				int matchedPixels = 0;
				int[][] instance = instanceBitmap.asMatrix();
				int[][] input = rescaledInput.asMatrix();
				final int instanceWidth = instanceBitmap.getWidth();
				final int instanceHeight = instanceBitmap.getHeight();
				for (int i = 0; i < instanceWidth; i++) {
					for (int j = 0; j < instanceHeight; j++) {

						if (BitmapUtils.getRed(instance[i][j]) == 0) {
							matchedPixels += instance[i][j] == input[i][j] ? 2 : 0;
						}

						else {
							matchedPixels += instance[i][j] == input[i][j] ? 1 : 0;
						}

					}
				}
				float matchCoefficient = ((float) matchedPixels / (totalPixels)) * classWeight;
				matches.add(new CharMatch(character, rescaledInput, instanceBitmap, matchCoefficient));
			}

		}

		return matches;
	}

	public char getRepresentingSymbol() {
		return character;
	}

	public float getWeight() {
		return classWeight;
	}

	public boolean isCapital() {
		return ((int) character) <= 90 && ((int) character) >= 65;
	}

}