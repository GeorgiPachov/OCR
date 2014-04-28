package com.fmitextrecognition.charSegmentation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fmitextrecognition.io.Bitmap;
import com.fmitextrecognition.io.BitmapImpl;

public class CharSegmentator extends Segmentator implements Iterable<Bitmap> {

	private LineSegmentator line;
	private int whiteSpaceLength;
	private boolean lineChanged;

	public boolean isLineChanged() {
		return lineChanged;
	}

	public int getWhiteSpaceLength() {
		return whiteSpaceLength;
	}

	public CharSegmentator(Bitmap picture) {
		super(picture);
		line = new LineSegmentator(picture);
		whiteSpaceLength = 0;
		lineChanged = false;
		nextSegment();
	}

	@SuppressWarnings("unused")
	@Override
	protected void setSegmentEnd() {
		for (int x = segmentStart + 1, i = 0; x < picture.getWidth(); ++x, ++i) {
			boolean space = true;
			for (int y = line.getSegmentStart(), j = 0; y < line
					.getSegmentEnd(); ++y, ++j) {
				int color = picture.getPixel(x, y);
				if (color != -1) {
					space = false;
				}
			}
			if (space) {
				segmentEnd = x;
				return;
			}
		}
		segmentEnd = picture.getWidth();

	}

	@Override
	protected void skipSpace() {
		lineChanged = false;
		do {
			whiteSpaceLength = 0;
			for (int x = segmentEnd + 1; x < picture.getWidth(); ++x) {
				for (int y = line.getSegmentStart(); y < line.getSegmentEnd(); ++y) {
					int color = picture.getPixel(x, y);
					if (color != -1) {
						segmentStart = x;
						return;
					}
				}
				whiteSpaceLength++;
			}
			segmentEnd = segmentStart = 0;
			line.nextSegment();
			lineChanged = true;
		} while (line.getSegmentStart() < picture.getHeight());
		segmentStart = picture.getWidth();
	}

	public Bitmap character() {
		int[][] subPic = new int[this.segmentEnd - this.segmentStart][this.line
				.getSegmentEnd()
				- this.line.getSegmentStart()];
		for (int i = this.segmentStart, x = 0; i < this.segmentEnd; ++i, ++x) {
			for (int j = this.line.getSegmentStart(), y = 0; j < line
					.getSegmentEnd(); ++j, ++y) {
				subPic[x][y] = picture.getPixel(i, j);
			}
		}
		//return new BitmapImpl(subPic);
		return cutUpDownWS(subPic);
	}
	
	//remove cut white spaces above and under the character.
	private Bitmap cutUpDownWS(int[][] pic) {
		LineSegmentator ls = new LineSegmentator(new BitmapImpl(pic));
		int h = ls.getSegmentEnd() - ls.getSegmentStart();
		int[][] result = new int[pic.length][h];
		for (int i = 0; i < pic.length; ++i) {
			for (int j = 0; j < h; ++j) {
				result[i][j] = pic[i][j + ls.getSegmentStart()];
			}
		}
		return new BitmapImpl(result);
	}

	public boolean hasNext() {
		return line.getSegmentStart() < picture.getHeight();
	}
	
	public List<Bitmap> asList(){
		List<Bitmap> list = new ArrayList<Bitmap>();
		CharSegmentator clone = clone();
		while (clone.hasNext()){
			list.add(clone.character());
			clone.nextSegment();
		}
		return list;
	}

	@Override
	public Iterator<Bitmap> iterator() {
		return asList().iterator();
	}
	
	public CharSegmentator clone(){
		return new CharSegmentator(picture);
	}
}
