package �����߰�����;

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
