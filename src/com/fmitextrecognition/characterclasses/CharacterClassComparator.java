package com.fmitextrecognition.characterclasses;

import java.util.Comparator;

public class CharacterClassComparator implements Comparator<CharacterClass> {

	@Override
	public int compare(CharacterClass o1, CharacterClass o2) {
		return (int) (o1.getWeight() - o2.getWeight());
	}

}
