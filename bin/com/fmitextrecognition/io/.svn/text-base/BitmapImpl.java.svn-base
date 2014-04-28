package com.fmitextrecognition.io;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Using this class so we can change implementations later if the java one
 * proves to be too slow for accessing the raster.
 * 
 * @author Georgi
 * 
 */
public class BitmapImpl implements Bitmap {

	private BufferedImage image;
	private int[][] cachedMatrix;

	public BitmapImpl(BufferedImage image) {
		this.image = image;
	}

	public BitmapImpl(int[][] matrix) {
		int height = matrix[0].length;
		int width = matrix.length;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image.setRGB(i, j, matrix[i][j]);
			}
		}
	}

	@Override
	public int getPixel(int x, int y) {
		return image.getRGB(x, y);
	}

	/**
	 * Make sure to CACHE the result!
	 */
	@Override
	public int[][] cloneMatrix() {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] result = new int[width][height];

		if (cachedMatrix == null) {
			cachedMatrix = new int[width][height];
			for (int col = 0; col < width; col++) {
				for (int row = 0; row < height; row++) {
					cachedMatrix[col][row] = image.getRGB(col, row);
				}
			}

		}
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				result[col][row] = cachedMatrix[col][row];
			}
		}
		return result;
	}

	@Override
	public int getWidth() {
		return image.getWidth();
	}

	@Override
	public int getHeight() {
		return image.getHeight();
	}

	@Override
	public Bitmap rescale(int newWidth, int newHeight) {
		BufferedImage resized = new BufferedImage(newWidth, newHeight, image.getType());
		Graphics2D graphics = resized.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		graphics.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, image.getWidth(), image.getHeight(), null);
		graphics.dispose();
		return new BitmapImpl(resized);
	}

	@Override
	public Bitmap subBitmap(int x1, int y1, int x2, int y2) {
		return new BitmapImpl(image.getSubimage(x1, y1, x2 - x1, y2 - y1));
	}

	@Override
	public int[][] asMatrix() {
		if (cachedMatrix == null) {
			cachedMatrix = cloneMatrix();
		}
		return cachedMatrix;
	}
}