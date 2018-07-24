package mms.util;

import java.util.TimerTask;

public class Timer {
	
	private java.util.Timer hTimer;
	private Runnable task;
	private int interval;
	private RunnableTimer taskTimer;
		
	public Timer(int i, Runnable t) {
		interval = i;
		task = t;
		taskTimer = new RunnableTimer();
	}
	
	public void start() {
		hTimer = new java.util.Timer();
		hTimer.schedule(taskTimer, interval, interval);
	}
	
	public void stop() {
		if(hTimer != null) {
			hTimer.cancel();
			hTimer = null;
		}
	}
	
	private class RunnableTimer extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(task != null) {
				task.run();
			}
		}
		
	}
}
