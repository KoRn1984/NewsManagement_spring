package by.itacademy.matveenko.jd2.util.validation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import by.itacademy.matveenko.jd2.bean.User;

public class NewsDataValidation {
	private final Map<InputNewsDataValidation, String> dataValid;

	private NewsDataValidation(ValidBuilder validBuilder) {
		this.dataValid = validBuilder.dataValid;
	}

	public Map<InputNewsDataValidation, String> getDataValid() {
		return dataValid;
	}

	public static class ValidBuilder {
		private Map<InputNewsDataValidation, String> dataValid = new HashMap<>();

		private static final String TITLE_EMPTY = "Title is empty!";
		private static final String TITLE_LENGTH = "Title length is out of range!";
		private static final int MAX_TITLE = 100;
		private static final int MIN_TITLE = 5;

		public ValidBuilder titleValid(String title) {

			if (isEmptyString(title)) {
				dataValid.put(InputNewsDataValidation.TITLE_ERROR, TITLE_EMPTY);
				return this;
			}
			if (title.length() < MIN_TITLE || title.length() > MAX_TITLE) {
				dataValid.put(InputNewsDataValidation.TITLE_ERROR, TITLE_LENGTH);
			}
			return this;
		}

		private static final String BRIEF_EMPTY = "Brief is empty!";
		private static final String BRIEF_LENGTH = "Brief length is out of range!";
		private static final int MAX_BRIEF = 500;
		private static final int MIN_BRIEF = 15;

		public ValidBuilder briefValid(String brief) {
			if (isEmptyString(brief)) {
				dataValid.put(InputNewsDataValidation.BRIEF_ERROR, BRIEF_EMPTY);
				return this;
			}
			if (brief.length() < MIN_BRIEF || brief.length() > MAX_BRIEF) {
				dataValid.put(InputNewsDataValidation.BRIEF_ERROR, BRIEF_LENGTH);
			}
			return this;
		}

		private final static String CONTENT_EMPTY = "Content is empty!";
		private final static String CONTENT_LENGTH = "Content length is out of range!";
		private final static int MAX_CONTENT = 5000;
		private final static int MIN_CONTENT = 30;

		public ValidBuilder contentValid(String content) {

			if (isEmptyString(content)) {
				dataValid.put(InputNewsDataValidation.CONTENT_ERROR, CONTENT_EMPTY);
				return this;
			}
			if (content.length() < MIN_CONTENT || content.length() > MAX_CONTENT) {
				dataValid.put(InputNewsDataValidation.CONTENT_ERROR, CONTENT_LENGTH);
			}
			return this;
		}

		public ValidBuilder dateValid(LocalDate date) {
			return this;
		}

		public ValidBuilder authorValid(User author) {
			return this;
		}

		public NewsDataValidation build() {
			return new NewsDataValidation(this);
		}

		private static boolean isEmptyString(String string) {
			if (string.isEmpty() || string == null) {
				return true;
			}
			return false;
		}
	}
}