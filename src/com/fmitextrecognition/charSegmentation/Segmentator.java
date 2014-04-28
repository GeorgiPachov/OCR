package com.fmitextrecognition.charSegmentation;

import com.fmitextrecognition.io.Bitmap;

public abstract class Segmentator {
	protected Bitmap picture;

	protected int segmentStart;
	protected int segmentEnd;

	public Segmentator(Bitmap picture) {
		this.picture = picture;
		segmentStart = 0;
		segmentEnd = 0;
	}

	public int getSegmentStart() {
		return segmentStart;
	}

	public int getSegmentEnd() {
		return segmentEnd;
	}

	public int nextSegment() {
		skipSpace();
		setSegmentEnd();
		return this.segmentStart;
	}

	protected abstract void skipSpace();

	protected abstract void setSegmentEnd();
}
