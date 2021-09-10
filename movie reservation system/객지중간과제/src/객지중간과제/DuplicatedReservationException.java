package 객지중간과제;

public class DuplicatedReservationException extends Exception{
	public DuplicatedReservationException(){
		super("DuplicatedReservationException");
	}
	public DuplicatedReservationException(String message) {
		super(message);
	}
	public void printExcep() {
		System.out.println(this.getMessage());
	}
}
