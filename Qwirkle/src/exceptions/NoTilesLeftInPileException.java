package exceptions;

public class NoTilesLeftInPileException extends Exception {
	private static final long serialVersionUID = 1L;
	private final String msg = "Er zijn geen tegels meer in de pot";
	
	public String getMessage(){
		return msg;
	}
}
