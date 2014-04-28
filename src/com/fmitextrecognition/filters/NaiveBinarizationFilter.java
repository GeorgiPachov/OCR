package com.fmitextrecognition.filters;

import java.awt.Color;

import com.fmitextrecognition.io.Bitmap;
import com.fmitextrecognition.io.BitmapImpl;
import com.fmitextrecognition.io.BitmapUtils;

public class NaiveBinarizationFilter implements BitmapFilter {

	@Override
	public Bitmap filter(Bitmap input) {
		int[][] matrix = input.cloneMatrix();

		int maxIntensity = Integer.MIN_VALUE;
		int minIntesity = Integer.MAX_VALUE;
		for (int i = 0; i < input.getWidth(); i++) {
			for (int j = 0; j < input.getHeight(); j++) {
				int intensity = BitmapUtils.getRed(matrix[i][j]);
				if (intensity > maxIntensity) {
					maxIntensity = intensity;
				}
				if (intensity < minIntesity) {
					minIntesity = intensity;
				}
			}
		}

		for (int i = 0; i < input.getWidth(); i++) {
			for (int j = 0; j < input.getHeight(); j++) {
				int localThreshhold = (maxIntensity + minIntesity) / 2;
				if (BitmapUtils.getRed(matrix[i][j]) > localThreshhold) {
					matrix[i][j] = Color.white.getRGB();
				} else
					matrix[i][j] = 0;
			}
		}
		return new BitmapImpl(matrix);
	}

	@Override
	public String getFilterName() {
		return "naive_binarization_filter";
	}

}
