package com.fmitextrecognition.filters;

import java.awt.Color;

import com.fmitextrecognition.io.Bitmap;
import com.fmitextrecognition.io.BitmapImpl;

public class GreyscaleFilter implements BitmapFilter {

	@Override
	public Bitmap filter(Bitmap input) {
		int[][] matrix = input.cloneMatrix();
		for (int x = 0; x < matrix.length; x++){
			for (int y = 0; y < matrix[0].length; y++){
				int currentColor = matrix[x][y];
				Color color = new Color(currentColor);
				int newColor = (color.getRed() + color.getGreen() + color.getBlue())/3;
				
				//XXX: new object for every pixel, might be expensive and ineffective
				matrix[x][y]= new Color(newColor,newColor, newColor).getRGB(); 
			}
		}
		return new BitmapImpl(matrix);
	}

	@Override
	public String getFilterName() {
		return "greyscale";
	}

}
