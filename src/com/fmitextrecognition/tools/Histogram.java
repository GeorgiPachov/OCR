package com.fmitextrecognition.tools;

import com.fmitextrecognition.io.Bitmap;
import com.fmitextrecognition.io.BitmapUtils;

public class Histogram {
	private int[] intensityLevels = new int[256];
	public Histogram(Bitmap bitmap){
		for (int i = 0; i < bitmap.getWidth(); i++){
			for (int j = 0; j < bitmap.getHeight(); j++){
				//Assuming image has been greyscaled, intensity = (r + g + b)/3
				//In greyscaled, they area all equal, so... just red
				int intensity = BitmapUtils.getRed(bitmap.getPixel(i, j));
				intensityLevels[intensity]++;
			}
		}
	}
	/**
	 * @param intensity between 0 and 255
	 * @return numberOfPixels in the bitmap given 
	 * than have this ammount of intensity
	 */
	public int getNumberOfPixelsForIntensitity(int intensity){
		return intensityLevels[intensity];
	}
}
