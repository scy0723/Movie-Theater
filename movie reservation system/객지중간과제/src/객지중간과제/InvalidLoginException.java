package �����߰�����;

public class InvalidLoginException extends Exception{
	public InvalidLoginException() {
		super("InvalidLoginException");
	}
	public InvalidLoginException(String message) {
		super(message);
	}
	public void printExcep() {
		System.out.println(this.getMessage());
	}
}
