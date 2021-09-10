package 객지중간과제;

public class Canceller extends Thread {
	private Movie mov;
	private String seat;
	
	public Canceller(Movie mov, String seat) {
		this.mov=mov;
		this.seat=seat;
	}
	
	public synchronized void cancel() throws InterruptedException {
		mov.buff.somoneCancelled(mov, seat);
	}
	
	public void run() {
		try {
			cancel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
