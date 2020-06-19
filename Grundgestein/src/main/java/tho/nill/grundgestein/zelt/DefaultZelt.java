package tho.nill.grundgestein.zelt;

import java.time.LocalDate;

public abstract class DefaultZelt implements EntityZelt {

	public String convertToString(String value) {
		return value;
	}

	public Long convertToLong(String value) {
		return Long.parseLong(value);
	}

	public double convertToDouble(String value) {
		return Double.parseDouble(value);
	}

	public LocalDate convertToLocalDate(String value) {
		return LocalDate.parse(value);
	}
}
