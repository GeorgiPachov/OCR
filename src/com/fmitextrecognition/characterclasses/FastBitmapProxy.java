package com.fmitextrecognition.characterclasses;

import java.awt.Rectangle;

import com.fmitextrecognition.io.Bitmap;
import com.fmitextrecognition.tools.Histogram;

public class FastBitmapProxy implements Raster{

	private Bitmap bitmap;
	private float scaleX;
	private float scaleY;
	private Rectangle rectangle;

	public FastBitmapProxy(Bitmap bitmap, Rectangle rect){
		this.rectangle = rect;
		this.bitmap = bitmap;
	}
	
	public void setScale(float scaleX, float scaleY){
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	@Override
	public int getPixel(int x, int y) {
		return -1;
	}


}
