package com.fmitextrecognition.utils;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public class Utils {
	public static Rectangle findBoundingBox(final List<Point> allPoints) {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;

		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (final Point point : allPoints) {
			if (point.x > maxX) {
				maxX = point.x;
			}
			if (point.y > maxY) {
				maxY = point.y;
			}

			if (point.x < minX) {
				minX = point.x;
			}

			if (point.y < minY) {
				minY = point.y;
			}
		}
		return new Rectangle(minX, minY, maxX - minX + 1, maxY - minY + 1);
	}

	public static boolean contains(Rectangle rect, Point characterStart) {
		return characterStart.x >= rect.x && characterStart.x <= rect.x + rect.width
				&& characterStart.y >= rect.y && characterStart.y <= rect.y + rect.height;
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().trim().contains("windows");
	}

}
