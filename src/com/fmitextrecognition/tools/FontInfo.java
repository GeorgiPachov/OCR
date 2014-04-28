package com.fmitextrecognition.tools;

import java.util.List;

import com.fmitextrecognition.io.Bitmap;

public class FontInfo {
	private float averageWidth;

	public FontInfo(List<Bitmap> characters){
		int totalFontWidth = 0;
		for (Bitmap bitmap : characters){
			totalFontWidth+=bitmap.getWidth();
		}
		this.averageWidth = (float) totalFontWidth/characters.size();
	}
	
	public boolean isCapital(Bitmap character){
		return character.getWidth() > averageWidth;
	}
}
