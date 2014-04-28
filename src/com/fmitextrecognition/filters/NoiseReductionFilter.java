package com.fmitextrecognition.filters;

import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import com.fmitextrecognition.io.Bitmap;
import com.fmitextrecognition.io.BitmapImpl;
import com.fmitextrecognition.io.BitmapUtils;

public class NoiseReductionFilter implements BitmapFilter {

	@Override
	public Bitmap filter(Bitmap input) {
		
		float[] matrix = new float[4];
		for (int i = 0; i < 4; i++)
			matrix[i] = 1.0f/4.0f;

	    BufferedImageOp op = new ConvolveOp( new Kernel(2, 2, matrix), ConvolveOp.EDGE_ZERO_FILL, null );
	    
		return new BitmapImpl(op.filter(BitmapUtils.toBufferedImage(input), null));
	}

	@Override
	public String getFilterName() {
		return "noise_reduction";
	}

}
