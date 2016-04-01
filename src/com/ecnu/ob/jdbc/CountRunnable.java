package com.ecnu.ob.jdbc;

public class CountRunnable implements Runnable {
	
	private boolean isStop = false;

	@Override
	public void run() {
		int i = 1;
		System.out.print("Wait: ");
		while(!isStop) {
			System.out.print(i);
			if(!isStop) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				for(int j = 0; j < String.valueOf(i).length(); j++) {
					System.out.print("\b");
				}
				i++;
			}
		}
		System.out.println(i - 1);
	}
	
	public void stop() {
		isStop = true;
	}

}
