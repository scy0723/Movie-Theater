package �����߰�����;

public class DuplicatedIdException extends Exception{
	public DuplicatedIdException() {
		super("DuplicatedIdException");
	}
	public DuplicatedIdException(String message) {
		super(message);
	}
	public void printExcep() {
		System.out.println(this.getMessage());
	}
}
