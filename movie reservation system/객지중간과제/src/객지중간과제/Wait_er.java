package 객지중간과제;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Wait_er extends Thread{
		private Member mem;
		private Movie mov;
		private int time;
		final long startTime;
		private boolean firstTime=true;
		
		public Wait_er(Member mem, Movie mov,int time) {
			this.startTime = System.currentTimeMillis();
			this.mem = mem;
			this.mov = mov;

			this.time=time;
		}
		
		public Member getMem() {
			return mem;
		}

		public Movie getMov() {
			return mov;
		}
		public int getTime() {
			return time;
		}

		public long getStartTime() {
			return startTime;
		}

		public void setMem(Member mem) {
			this.mem = mem;
		}

		public void setMov(Movie mov) {
			this.mov = mov;
		}

		public void setTime(int time) {
			this.time = time;
		}

		public synchronized void waitAndReserve(Movie mov) throws InterruptedException, IOException {
			mov.buff.waitedAndRsv(this,firstTime,mov,time);
		}

		@Override
		public void run() {
			try {
				waitAndReserve(this.mov);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
