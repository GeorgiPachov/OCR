package com.fmitextrecognition.segmentation2;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.fmitextrecognition.io.Bitmap;

public class InputLine {

	private final List<InputCharacter> characters = new ArrayList<InputCharacter>();

	public InputLine(Bitmap bitmap) {
		int startX = getStartX(bitmap);
		int endX = getEndX(bitmap);

		SplitterState splitterState = SplitterState.OUT;
		int charStartX = -1;
		int charEndX = -1;

		int totalWhitespacesSum = 0;

		for (int x = startX; x < endX; x++) {
			boolean wholeLineWhite = true;
			for (int y = 0; y < bitmap.getHeight(); y++) {
				int color = bitmap.getPixel(x, y);
				wholeLineWhite &= color == -1;
				if (color != -1) {
					if (splitterState == SplitterState.OUT) {
						splitterState = SplitterState.IN;
						charStartX = x;
						if (charEndX != -1 && charStartX != -1) {
							totalWhitespacesSum += charStartX - charEndX;
						}

						break;
					}
				}
				if (wholeLineWhite && y == bitmap.getHeight() - 1) {
					if (splitterState == SplitterState.IN) {

						splitterState = SplitterState.OUT;
						charEndX = x;

						Bitmap subBitmap = cutDownWSY(bitmap.subBitmap(charStartX, 0, charEndX, bitmap.getHeight()));
						Rectangle position = new Rectangle(charStartX, 0, charEndX - charStartX, bitmap.getHeight());

						characters.add(new InputCharacter(subBitmap, position));

						break;
					}

				}
			}
		}

		if (characters.size() > 0) {
			float spacesAvg = (float) totalWhitespacesSum / (characters.size() - 1);
			InputCharacter last = characters.get(0);
			for (int i = 1; i < characters.size(); i++) {
				InputCharacter currentCharacter = characters.get(i);
				int distance = (int) (currentCharacter.getPosition().getMinX() - last.getPosition().getMaxX());
				if (distance > spacesAvg) {
					characters.add(i, new InputCharacter(null, null));
					i++;
				}
				last = characters.get(i);
			}
		}
	}

	private Bitmap cutDownWSY(Bitmap subBitmap) {

		int startY = -1;
		int endY = -1;
		boolean doBreak = false;
		for (int y = 0; y < subBitmap.getHeight(); y++) {
			for (int x = 0; x < subBitmap.getWidth(); x++) {
				int color = subBitmap.getPixel(x, y);
				if (color != -1) {
					startY = y;

					doBreak = true;
					break;
				}

			}
			if (doBreak) {
				break;
			}
		}

		for (int y = subBitmap.getHeight() - 1; y >= startY; y--) {
			for (int x = 0; x < subBitmap.getWidth(); x++) {
				int color = subBitmap.getPixel(x, y);

				if (color != -1) {
					endY = y;
					return subBitmap.subBitmap(0, startY, subBitmap.getWidth(), endY + 1);
				}
			}
		}
		return subBitmap;
	}

	private int getEndX(Bitmap bitmap) {
		int endX = -1;
		boolean doBreak = false;
		for (int x = bitmap.getWidth() - 1; x >= 0; x--) {
			for (int y = 0; y < bitmap.getHeight(); y++) {
				int color = bitmap.getPixel(x, y);
				if (color != -1) {
					endX = x;
					doBreak = true;
					break;
				}
			}
			if (doBreak) {
				break;
			}
		}
		return endX;
	}

	private int getStartX(Bitmap bitmap) {
		int startX = -1;
		boolean doBreak = false;
		for (int x = 0; x < bitmap.getWidth(); x++) {
			for (int y = 0; y < bitmap.getHeight(); y++) {
				int color = bitmap.getPixel(x, y);
				if (color != -1) {
					startX = x;
					doBreak = true;
					break;
				}
			}
			if (doBreak) {
				break;
			}
		}
		return startX;
	}

	public List<InputCharacter> getCharacters() {
		return characters;
	}
}
