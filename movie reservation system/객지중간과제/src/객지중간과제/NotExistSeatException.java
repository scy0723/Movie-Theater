package �����߰�����;

public class NotExistSeatException extends Exception{
	public NotExistSeatException() {
		super("NotExistSeatException");
	}
	public NotExistSeatException(String message) {
		super(message);
	}
	public void printExcep() {
		System.out.println(this.getMessage());
	}
}
