package com.fmitextrecognition.segmentation;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.fmitextrecognition.characterclasses.CharacterClass;
import com.fmitextrecognition.io.Bitmap;
import com.fmitextrecognition.io.BitmapUtils;
import com.fmitextrecognition.segmentation2.InputLine;
import com.fmitextrecognition.tools.CharMatch;
import com.fmitextrecognition.tools.Logger;
import com.fmitextrecognition.utils.Utils;

public class BitmapTextReader {

	private int[][] matrix;

	public String getText(Bitmap b, List<CharacterClass> classes) {
		List<Line> lines = getCharPositions(b);

		final StringBuilder result = new StringBuilder();
		for (Line l : lines) {
			for (Rectangle r : l.getCharacters()) {
				Bitmap subBitmap = b.subBitmap(r.x, r.y, r.x + r.width, r.y + r.height);

				final ArrayList<CharMatch> matches = new ArrayList<CharMatch>();
				for (CharacterClass clazz : classes) {
					CharMatch match = clazz.getMatchesFor(subBitmap).peek();
					if (match != null) {
						matches.add(match);
					}
				}
				
				Collections.sort(matches);
				Logger.log(BitmapUtils.toAsciiArt(subBitmap) + " =>>>>>>>>>>>>> recognized as "
						+ matches.get(0).getCharacter());
				result.append(matches.get(0).getCharacter());
			}
			result.append("\n");
		}

		return result.toString();
	}

	public List<Line> getCharPositions(Bitmap b) {
		this.matrix = b.asMatrix();

		final ArrayList<Line> result = new ArrayList<Line>();

		for (int y = 0; y < matrix[0].length; y++) {
			Line line = null;
			for (int x = 0; x < matrix.length; x++) {
				int pixel = matrix[x][y];
				if (isConsideredPartOfCharacter(pixel)) {
					Point characterStart = new Point(x, y);

					// skip if already in result
					boolean skip = false;
					for (Line l : result) {
						for (Rectangle rect : l.getCharacters()) {
							if (rect.contains(characterStart)) {
								skip = true;
								break;
							}
						}
					}

					if (skip) {
						continue;
					}

					final List<Point> allPoints = findAllPoints(characterStart);
					final Rectangle boundingBox = Utils.findBoundingBox(allPoints);

					
					final boolean firstFoundCharacterOnLine = line == null;
					if (line == null) {
						line = new Line();
					}
					
					//rollback to first character on row
					if (firstFoundCharacterOnLine){
						x = 0; 
						y = (int) boundingBox.getCenterY();
						continue;
					}
					line.addCharacter(boundingBox);

					// little optimization
					x = (int) boundingBox.getMaxX();
					y = (int) boundingBox.getCenterY();

				}
			}
			if (line != null) {
				result.add(line);
			}
		}

		return result;

	}

	private List<Point> findAllPoints(Point characterStart) {
		final List<Point> characterPoints = new ArrayList<Point>();
		Queue<Point> queue = new LinkedList<Point>();
		final HashSet<Point> visited = new HashSet<Point>();
		queue.add(characterStart);

		while (!queue.isEmpty()) {
			Point current = queue.poll();
			visited.add(current);

			if (isConsideredPartOfCharacter(current)) {
				characterPoints.add(current);

				for (Point neighbour : getNeighbours(current)) {
					if (!visited.contains(neighbour) && !queue.contains(neighbour)
							&& isConsideredPartOfCharacter(neighbour)) {
						queue.add(neighbour);
					}
				}
			}
		}

		return characterPoints;
	}

	private boolean isConsideredPartOfCharacter(Point current) {
		return isConsideredPartOfCharacter(matrix[current.x][current.y]);
	}

	private List<Point> getNeighbours(Point current) {
		ArrayList<Point> points = new ArrayList<Point>();
		if (current.x < matrix.length - 1) {
			points.add(new Point(current.x + 1, current.y));
		}

		if (current.y < matrix[0].length) {
			points.add(new Point(current.x, current.y + 1));
		}
		if (current.x < matrix.length - 1 && current.y < matrix[0].length - 1) {
			points.add(new Point(current.x + 1, current.y + 1));
		}

		if (current.x > 0 && current.y > 0) {
			points.add(new Point(current.x - 1, current.y - 1));
		}
		if (current.x > 0) {
			points.add(new Point(current.x - 1, current.y));
		}
		if (current.y > 0) {
			points.add(new Point(current.x, current.y - 1));
		}

		if (current.x > 0 && current.y < matrix[0].length - 1) {
			points.add(new Point(current.x - 1, current.y + 1));
		}
		if (current.x < matrix[0].length - 1 && current.y > 0) {
			points.add(new Point(current.x + 1, current.y - 1));
		}

		return points;
	}

	private boolean isConsideredPartOfCharacter(int pixel) {

		return BitmapUtils.getBlue(pixel) == 0 && BitmapUtils.getRed(pixel) == 0 && BitmapUtils.getGreen(pixel) == 0;
	}
}
