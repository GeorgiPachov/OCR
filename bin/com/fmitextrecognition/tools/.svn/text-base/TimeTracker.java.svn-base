package com.fmitextrecognition.tools;

public class TimeTracker {

	private final String operation;
	private long startTime;
	private long endTime;

	public TimeTracker(String operation){
		this.operation = operation;
	}
	
	public TimeTracker start(){
		this.startTime = System.currentTimeMillis();
		return this;
	}
	
	public TimeTracker end(){
		this.endTime = System.currentTimeMillis();
		Logger.log(operation +" executed for " + (endTime - startTime) + " milliseconds.");
		return this;
	}
	
	
}
