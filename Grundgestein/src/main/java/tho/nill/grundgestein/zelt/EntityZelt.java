package tho.nill.grundgestein.zelt;

import java.time.LocalDate;

public interface EntityZelt {
	void create();

	void save();

	default void skip(String value) {
	}
	
	

	default String convertToString(String value) {
		return value;
	}

	default Long convertToLong(String value) {
		return Long.parseLong(value);
	}

	default double convertToDouble(String value) {
		return Double.parseDouble(value);
	}

	default LocalDate convertToLocalDate(String value) {
		return LocalDate.parse(value);
	}
	
	


}
