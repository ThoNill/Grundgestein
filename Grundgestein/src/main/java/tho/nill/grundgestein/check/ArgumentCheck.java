package tho.nill.grundgestein.check;

public class ArgumentCheck {

	private ArgumentCheck() {
		super();
	}
	
	public static void checkIllegalArgument(boolean isTrue) {
		if (!isTrue) {
			throw new IllegalArgumentException();
		}
	}
	
	public static void checkIllegalArgument(boolean isTrue,String message) {
		if (!isTrue) {
			throw new IllegalArgumentException(message);
		}
	}

}
