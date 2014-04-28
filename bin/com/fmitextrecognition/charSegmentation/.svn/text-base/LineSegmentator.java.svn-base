package com.fmitextrecognition.charSegmentation;

import com.fmitextrecognition.io.Bitmap;

public class LineSegmentator extends Segmentator {

	public LineSegmentator(Bitmap picture) {
		super(picture);
		nextSegment();
	}

	public int nextSegment() {
		skipSpace();
		setSegmentEnd();
		return this.segmentStart;
	}

	protected void skipSpace() {
		for (int y = segmentEnd + 1; y < picture.getHeight(); ++y) {
			for (int x = 0; x < picture.getWidth(); ++x) {
				int color = picture.getPixel(x, y);
				if (color != -1) {
					segmentStart = y;
					System.out.println("Segment start: " + segmentStart);
					return;
				}
			}
		}
		segmentStart = picture.getHeight();
	}

	protected void setSegmentEnd() {
		for (int y = segmentStart + 1; y < picture.getHeight(); ++y) {
			boolean space = true;
			for (int x = 0; x < picture.getWidth(); ++x) {
				if (picture.getPixel(x, y) != -1) {
					space = false;
				}
			}
			if (space) {
				segmentEnd = y;
				return;
			}
		}
		segmentEnd = this.picture.getHeight();
	}
}
