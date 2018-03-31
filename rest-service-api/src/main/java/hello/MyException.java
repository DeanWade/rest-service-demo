package hello;

public class MyException extends RuntimeException {

	private static final long serialVersionUID = 4376430489656417039L;
	
	public MyException() {
	}
	
	public MyException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public MyException(Exception e) {
		super(e);
	}
	
	public MyException(String msg) {
		super(msg);
	}

}
