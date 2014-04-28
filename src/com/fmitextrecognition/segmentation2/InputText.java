package com.fmitextrecognition.segmentation2;

import java.util.ArrayList;
import java.util.List;

import com.fmitextrecognition.io.Bitmap;

public class InputText {

	private List<InputLine> lines = new ArrayList<InputLine>();

	public InputText(Bitmap bitmap) {
		SplitterState status = SplitterState.OUT;

		int lineStartY = 0;
		int lineEndY = 0;
		for (int y = 0; y < bitmap.getHeight(); y++) {
			boolean wholeLineWhite = true;
			for (int x = 0; x < bitmap.getWidth(); x++) {

				int color = bitmap.getPixel(x, y);
				wholeLineWhite &= color == -1;
				
					if (status == SplitterState.OUT) {
						if (color != -1) {
						status = SplitterState.IN;
						lineStartY = y;
						break;
					}
				}

					if (status == SplitterState.IN) {
						if (wholeLineWhite && x == bitmap.getWidth() - 1) {
						status = SplitterState.OUT;
						lineEndY = y;
						lines.add(new InputLine(bitmap.subBitmap(0, lineStartY, bitmap.getWidth(), lineEndY)));
						break;
					}
				}
			}
		}
	}

	public List<InputLine> getLines() {
		return lines;
	}

}
