package by.itacademy.matveenko.jd2.util.validation;

import java.util.regex.Pattern;

import by.itacademy.matveenko.jd2.bean.User;
import by.itacademy.matveenko.jd2.service.ServiceException;

public class UserDataValidationImpl implements UserDataValidation {
	private final static String NAME_PATTERN = "^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})$";
	private final static String SURNAME_PATTERN = "^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})$";
	private final static String EMAIL_PATTERN = "([A-zА-я])+([0-9\\-_\\+\\.])*([A-zА-я0-9\\-_\\+\\.])*@([A-zА-я])+([0-9\\-_\\+\\.])*([A-zА-я0-9\\-_\\+\\.])*[\\.]([A-zА-я])+";
	private final static String LOGIN_PATTERN = "^[A-Za-z]([.A-Za-z0-9-]{1,18})([A-Za-z0-9])$";
	private final static String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^\\w\\s]).{6,}";
	
	@Override
	public boolean checkAuthDataLogination(String login, String password) {
		if (password.isEmpty() || login.isEmpty()) {
            return false;
        }
        if (Pattern.matches(LOGIN_PATTERN, login) && Pattern.matches(PASSWORD_PATTERN, password)) {
            return true;
        }
        return false;
    }

	@Override
	public boolean checkAuthDataRegistration(User user) throws ServiceException {
		String login = user.getLogin();
		String password = user.getPassword();
		String userName = user.getUserName();
		String userSurname = user.getUserSurname();		
		String email = user.getEmail();
			
		if (password.isEmpty() || login.isEmpty() || userName.isEmpty() || userSurname.isEmpty() || email.isEmpty()) {
            return false;
        }
        if (Pattern.matches(LOGIN_PATTERN, login)
                && Pattern.matches(PASSWORD_PATTERN, password)
                && Pattern.matches(NAME_PATTERN, userName)
                && Pattern.matches(SURNAME_PATTERN, userSurname)
                && Pattern.matches(EMAIL_PATTERN, email)) {
            return true;
        }
        return false;
    }
}