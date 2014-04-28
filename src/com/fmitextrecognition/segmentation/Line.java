package com.fmitextrecognition.segmentation;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Line {
	private List<Rectangle> characters = new ArrayList<Rectangle>();

	public List<Rectangle> getCharacters() {
		return characters;
	}

	public void addCharacter(Rectangle character) {
		characters.add(character);
	}
}
