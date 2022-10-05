package by.itacademy.matveenko.jd2.util.validation;

public final class ValidationProvider {
 	private static final ValidationProvider instance = new ValidationProvider();
 	private final UserDataValidation userDataValidation = new UserDataValidationImpl();
	
	private ValidationProvider() {}
	
	public static ValidationProvider getInstance() {
		return instance;
	}	
	
	public UserDataValidation getUserDataValidation() {
		return userDataValidation;
	}	
}