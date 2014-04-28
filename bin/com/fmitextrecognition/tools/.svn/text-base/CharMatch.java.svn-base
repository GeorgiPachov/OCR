package com.fmitextrecognition.tools;

import com.fmitextrecognition.io.Bitmap;

public class CharMatch implements Comparable<CharMatch>{
	private final char character;
	private final Bitmap matchedBitmap;
	private final Bitmap inputBitmap;
	private float matchCoefficient;

	public CharMatch(char c, Bitmap inputRescaledBitmap, Bitmap matchedBitmap, float coefficient){
		character = c;
		this.matchedBitmap = matchedBitmap;
		this.inputBitmap = inputRescaledBitmap;
		this.matchCoefficient = coefficient;
	}
	
	public char getCharacter() {
		return character;
	}

	public Bitmap getMatchedBitmap() {
		return matchedBitmap;
	}

	public float getMatchCoefficient() {
		return matchCoefficient;
	}

	public CharMatch setMatchCoefficient(float matchCoefficient) {
		this.matchCoefficient = matchCoefficient;
		return this;
	}
	
	public Bitmap getInputBitmap() {
		return inputBitmap;
	}
	
	@Override
	public int compareTo(CharMatch arg0) {
		double coeff = ((CharMatch)arg0).getMatchCoefficient();
		if(this.matchCoefficient > coeff)
			return -1;
		if(this.matchCoefficient < coeff)
			return 1;
		return 0;
	}

}
