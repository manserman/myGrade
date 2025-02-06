package priv.mansour.school.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final ZoneId DEFAULT_ZONE = ZoneId.of("Europe/Paris");

	private DateUtil() {

	}

	public static String now() {
		return formatInstant(Instant.now(), DEFAULT_FORMAT, DEFAULT_ZONE);
	}

	public static String nowUTC() {
		return formatInstant(Instant.now(), DEFAULT_FORMAT, ZoneId.of("UTC"));
	}

	public static String formatInstant(Instant instant, String pattern, ZoneId zone) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		ZonedDateTime zonedDateTime = instant.atZone(zone);
		return zonedDateTime.format(formatter);
	}
}