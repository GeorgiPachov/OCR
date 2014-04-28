package com.fmitextrecognition.tools;

import com.fmitextrecognition.io.ConstantsAndThresholds;

public class Logger {

	//Implementation could be changed later - levels, to a file etc
	public static void log(String message){
		System.out.println(message);
	}
	
	public static void logWithoutNL(String message){
		System.out.print(message);
	}
	
	public static void logDebug(String message){
		if (ConstantsAndThresholds.DEBUG_MODE){
			log(message);
		}
	}
}
