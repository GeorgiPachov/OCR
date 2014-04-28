package com.fmitextrecognition.classifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import com.fmitextrecognition.characterclasses.CharacterClass;
import com.fmitextrecognition.io.Bitmap;
import com.fmitextrecognition.tools.CharMatch;

public class KNN {
	
	private List<CharacterClass> classes;
	private final int k;
	
	public KNN(int k, List<CharacterClass> classes) {
		this.classes = classes;
		this.k = k;
	}
	
	public char classify(Bitmap bitmap, Map<String, Integer> attributes) {
		PriorityQueue<CharMatch> queue = new PriorityQueue<CharMatch>();
		
		for (CharacterClass mClass : classes) {
			PriorityQueue<CharMatch> matches = mClass.getMatchesFor(bitmap);
			float dist = distance(mClass.getAttributes(), attributes);
			for (int i = 0; i < k && !matches.isEmpty(); ++i) {
				CharMatch match = matches.poll();
				queue.add(match.setMatchCoefficient(match.getMatchCoefficient() + dist));
			}
		}
		
		char mClass = queue.peek().getCharacter();
		
		Map<Character, Integer> classes = new HashMap<Character, Integer>();
		int n = k;
		while(n > 0) {
			n--;
			CharMatch cl = queue.poll();
			char key = cl.getCharacter();
			if (classes.containsKey(key)) {
				classes.put(key, classes.get(key) + 1);
			}
			else {
				classes.put(key, 1);
			}
		}
		
		int max = Integer.MIN_VALUE;
		for (Entry<Character, Integer> entry : classes.entrySet()) {
			if (entry.getValue() > max) {
				max = entry.getValue();
				mClass = entry.getKey();
			}
		}
		
		return mClass;
	}
	
	private float distance(Map<String, Integer> attr1, Map<String, Integer> attr2) {
		float dist = 0;
		for(String key : attr1.keySet()) {
			if (attr2.containsKey(key)) {
				dist += Math.pow(attr1.get(key) - attr2.get(key), 2);
			}
		}
		return (float) Math.sqrt(dist);
	}
}
