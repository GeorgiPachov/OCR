package com.fmitextrecognition.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BitmapUtils {

	public static Bitmap readImage(File imageFile) throws IOException {
		return new BitmapImpl(ImageIO.read(imageFile));
	}

	public static void saveToFile(Bitmap bitmap, File file) throws IOException {
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();

		int[][] matrix = bitmap.asMatrix();
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				bufferedImage.setRGB(x, y, matrix[x][y]);
			}
		}
		ImageIO.write(bufferedImage, "bmp", file);
	}

	public static int getRed(int color) {
		return (color >> 16) & 0xFF;
	}

	public static int getBlue(int color) {
		return color & 0xFF;
	}

	public static int getGreen(int color) {
		return (color >> 8) & 0xFF;
	}

	public static int toColorInt(int red, int green, int blue) {
		return toColorInt(255, red, green, blue);
	}

	public static int toColorInt(int alpha, int red, int green, int blue) {
		return ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16)
				| ((green & 0xFF) << 8) | ((blue & 0xFF) << 0);
	}

	public static String toAsciiArt(Bitmap bitmap) {
		final StringBuilder asciiArtBuilder = new StringBuilder();
		for (int y = 0; y < bitmap.getHeight(); y++) {
			for (int x = 0; x < bitmap.getWidth(); x++) {
				asciiArtBuilder.append(bitmap.getPixel(x, y) == -1 ? " " : "#");
			}
			asciiArtBuilder.append("\n");
		}
		return asciiArtBuilder.toString();
	}
	public static BufferedImage toBufferedImage(Bitmap bitmap){
		BufferedImage image = new BufferedImage(bitmap.getWidth(), bitmap.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < bitmap.getWidth(); i++) {
			for (int j = 0; j < bitmap.getHeight(); j++) {
				image.setRGB(i, j, bitmap.getPixel(i,j));
			}
		}
		return image;
	}
}
