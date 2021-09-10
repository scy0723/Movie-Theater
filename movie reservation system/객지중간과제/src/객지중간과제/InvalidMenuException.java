package 객지중간과제;

public class InvalidMenuException extends Exception{
	public InvalidMenuException() {
		super("default message");
	}
	public InvalidMenuException(String message) {
		super(message);
	}
	public void printExcep() {
		System.out.println(this.getMessage());
	}
}
