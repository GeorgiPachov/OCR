package com.fmitextrecognition.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing the matching for a bitmap - character
 * 
 * @author Georgi
 * 
 */
public class MatchCollector {
	private List<CharMatch> matches = new ArrayList<CharMatch>();

	public void addMatch(CharMatch match) {
		matches.add(match);
	}

	private int getBestMatchIndex() {
		int maxChanceIndex = 0;
		float maxChance = 0;
		for (int i = 0; i < matches.size(); i++) {
			if (matches.get(i).getMatchCoefficient() >= maxChance) {
				maxChance = matches.get(i).getMatchCoefficient();
				maxChanceIndex = i;
			}
		}
		return maxChanceIndex;
	}

	public CharMatch getBestMatch() {
		return matches.get(getBestMatchIndex());
	}
	
	public int getNumberOfMatches(){
		return matches.size();
	}
}
