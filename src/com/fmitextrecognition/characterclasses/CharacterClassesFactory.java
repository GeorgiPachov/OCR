package com.fmitextrecognition.characterclasses;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fmitextrecognition.tools.Logger;

public class CharacterClassesFactory {

	public static List<CharacterClass> buildAsciiCharacterClasses() {
		Logger.log("Creating character classes, please wait...");
		List<CharacterClass> returnedList = new ArrayList<CharacterClass>();
		Font[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		List<Font> fontsList = new ArrayList<Font>();
		for (int j = 0; j < allFonts.length; j++) {
			boolean fontOK = true;
			for (int i = 32; i < 127; i++) {
				if (!allFonts[j].canDisplay((char) i)) {
					fontOK = false;
					break;
				}
			}

			if (fontOK) {
				fontsList.add(allFonts[j]);
			}
		}
		System.out.println(fontsList.size() + " fonts detected on the system.");

		for (int i = 32; i < 127; i++) {
			Logger.log("Creating character class for '" + (char) i + "'...");

			float characterWeight = String.valueOf((char) i).matches("[a-zA-Z0-9]") ? 1.05f : 1;
			returnedList.add(new CharacterClass((char) i, characterWeight, fontsList));
		}

		Collections.sort(returnedList, new CharacterClassComparator());
		return returnedList;
	}

	public static List<CharacterClass> buildUtf8CharacterClasses() {
		throw new UnsupportedOperationException();
	}
}
