package dio.digitalinovation.services.exceptions;

public class ResourseNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ResourseNotFoundException(String msg) {
		super(msg);
	}

}
