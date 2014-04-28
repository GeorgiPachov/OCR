package com.fmitextrecognition.filters;

import com.fmitextrecognition.io.Bitmap;
import com.fmitextrecognition.io.BitmapImpl;
import com.fmitextrecognition.io.BitmapUtils;
import com.fmitextrecognition.tools.Histogram;

public class BinarizationFilter implements BitmapFilter{

	@Override
	public Bitmap filter(Bitmap input) {
		Histogram histogram = new Histogram(input);
		int threshold = getThreshold(histogram);
		int[][] matrix = input.cloneMatrix();
		
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix[0].length; j++){
				int intensity = BitmapUtils.getRed(matrix[i][j]);
				matrix[i][j] = intensity > threshold ? BitmapUtils.toColorInt(255, 255, 255) : 0; 
			}
		}
		
		return new BitmapImpl(matrix);
	}

	private int getThreshold(Histogram histogram) {
		short mostPreciseThreshold = 0;
		long maxVariance = 0;
		for (short threshold = 0; threshold < 256; threshold++){
			
			//calculate class1W and class2W
			int class1W = 0;
			for (short i = 0; i < threshold; i++){
				class1W += histogram.getNumberOfPixelsForIntensitity(i);
			}
			
			int class2W = 0;
			for (short i = threshold; i < 256; i++){
				class2W += histogram.getNumberOfPixelsForIntensitity(i);
			}
			
			//calculate mean class1 and mean class2
			int class1Mean = 0;
			for (short i = 0; i < threshold; i++){
				class1Mean += i*histogram.getNumberOfPixelsForIntensitity(i);
			}

			int class2Mean = 0;
			for (short i = threshold; i < 256; i++){
				class2Mean += i*histogram.getNumberOfPixelsForIntensitity(i);
			}
			
//			class1Mean/=(float)(class1W);
//			class2Mean/=(float)(class2W);
			
			long intraClassVariance = class1W*class2W*((class1Mean - class2Mean)*(class1Mean - class2Mean));
			if (intraClassVariance > maxVariance){
				maxVariance = intraClassVariance;
				mostPreciseThreshold = threshold;
			}
		}
		return mostPreciseThreshold;
	}

	@Override
	public String getFilterName() {
		return "binarization";
	}

}
