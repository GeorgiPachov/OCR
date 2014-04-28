package com.fmitextrecognition.charSegmentation;

import java.util.Arrays;

public class WhiteSpace {
	
	private int middle;
	
	public WhiteSpace(Integer[] statistics) {
		Arrays.sort(statistics);
		middle = (statistics.length > 1) ? (statistics[0] + statistics[statistics.length - 2]) / 2
				: 1;
	}
	
	public String isWhiteSpace(int dist) {
		if (dist < middle) {
			return "";
		} else {
			return " ";
		}
	}
}
