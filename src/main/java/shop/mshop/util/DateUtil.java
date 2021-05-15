package shop.mshop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	// 1일을 second로 환산한 값
	public static final long ONE_DAY_SEC = 24 * 60 * 60;

	// 1일을 millisecond로 환산한 값
	public static final long ONE_DAY_MS = ONE_DAY_SEC * 1000;

	// 기본 날짜 포맷 (yyyy-MM-dd HH:mm:ss)
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// yyyyMMddHHmmss
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	// 날짜 포맷
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	// yyyyMMdd
	public static final String DATE_FORMAT_WITHOUT_DASH = "yyyyMMdd";

	// yyyy-MM-dd HH:mm
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

	// yyyyMMddHHmm
	public static final String DATE_TIME_FORMAT_WITHOUT_DASH = "yyyyMMddHHmm";

	// HHmmss
	public static final String TIME_FORMAT_WITHOUT_DASH = "HHmmss";

	// 기본 날짜 포맷 길이
	public static final int DEFAUT_DATE_FORMAT_LEN = DEFAULT_DATE_FORMAT.length();

	// 날짜 포맷 길이
	public static final int DATE_FORMAT_LEN = DATE_FORMAT.length();

	public static String getCurrentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public static String getCurrentDateAsString() {
		return getCurrentDateAsString(DEFAULT_DATE_FORMAT);
	}

	public static String getCurrentDateAsString(String format) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 문자열을 날짜로 변환한다.
	 *
	 * @param dateString 날짜 문자열
	 * @return 변환된 날짜
	 */
	public static LocalDate stringToDate(String dateString) {
		return stringToDate(dateString, null);
	}

	/**
	 * 문자열을 날짜로 변환한다.
	 *
	 * @param dateString 날짜 문자열
	 * @param format     변환할 포맷
	 * @return 변환된 날짜
	 */
	public static LocalDate stringToDate(String dateString, String format) {
		if (dateString == null)
			return null;

		String newFormat = makeFormat(dateString, format);

		System.out.println("newFormat: " + newFormat);

		return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(newFormat));
	}

	private static String makeFormat(String dateString, String format) {
		String newFormat;

		if (format != null) {
			newFormat = format;
		} else {
			int strLen = dateString.length();
			if (strLen == DATE_FORMAT_LEN) {
				newFormat = DATE_FORMAT;
			} else {
				newFormat = DATE_FORMAT_WITHOUT_DASH;
			}
		}

		return newFormat;
	}

	/**
	 * 날짜를 문자열로 변환한다.
	 *
	 * @param date   날짜
	 * @param format 포맷
	 * @return 문자열 날짜
	 */
	public static String dateToString(LocalDateTime date, String format) {
		if (format == null) {
			format = DEFAULT_DATE_FORMAT;
		}

		return date.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 두 날짜의 차이(일수)를 구한다. <br>
	 * lastDate - firstDate의 일수
	 *
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static long diffDate(LocalDate firstDate, LocalDate lastDate) {
		long days = ChronoUnit.DAYS.between(firstDate, lastDate);

		return days;
	}

	public static long diffDateTime(LocalDateTime firstDateTime, LocalDateTime lastDateTime) {
		long days = ChronoUnit.DAYS.between(firstDateTime, lastDateTime);

		return days;
	}

	/**
	 * Unix epoch seconds 를 date로 변환한다.
	 *
	 * @param long timestamp ex) 1522313221
	 * @return
	 */
	public static String getTimestampToDate(long timestamp) {
		try {
			Date date = new Date(timestamp * 1000L);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
			String formattedDate = sdf.format(date);
			return formattedDate;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * date를 unix ephoch seconds로 변환한다.
	 *
	 * @param String date (yyyy-mm-dd HH:mm:ss)
	 * @return
	 */
	public static String getDateToTimestamp(String dateStr) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(dateStr);
			long timeStamp = date.getTime();
			String unixTime = Long.toString(timeStamp / 1000);
			return unixTime;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 현재날짜 -standardDay 일자보다 체크날짜가 이후날짜인지 확인
	 *
	 * @param
	 * @return
	 * @throws ParseException
	 */
	public static boolean standardLateChk(String checkDt, int standardDay) throws ParseException {
		SimpleDateFormat transFormats = new SimpleDateFormat("yyyy-MM-dd");
		standardDay = standardDay * -1; // 정수 음수변환
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, standardDay);
		int compare = day.getTime().compareTo(transFormats.parse(checkDt));
		if (compare < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 현재시간에 지정분을 추가한다.
	 *
	 * @param long plusMinutes ex) 30 @return @throws
	 */
	public static String getDatePlusTime(long plusMinutes) {
		LocalDateTime now = LocalDateTime.now();
		return now.plusMinutes(plusMinutes).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 일시에 하이픈, 콜론을 추가한다.
	 *
	 * @param String yyyyMMddHHmmss ex) 20200101120030
	 * @return String ex)2020-01-01 12:00:30
	 */
	public static String getFormatDateTime(String yyyyMMddHHmmss) {
		String fdt = yyyyMMddHHmmss.substring(0, 4) + "-" + yyyyMMddHHmmss.substring(4, 6) + "-" + yyyyMMddHHmmss.substring(6, 8) + " "
				+ yyyyMMddHHmmss.substring(8, 10) + ":" + yyyyMMddHHmmss.substring(10, 12) + ":" + yyyyMMddHHmmss.substring(12, 14);

		return fdt;
	}
	
	/**
	 * 일시에 하이픈, 콜론을 추가한다.
	 *
	 * @param String yyyy-MM-dd HH:mm:ss ex) 2020-01-01 12:00:30
	 * @return String ex)20200101120030
	 */
	public static String removeHyphonDateTime(String yyyyMMddHHmmss) {
		return yyyyMMddHHmmss.replace("-", "").replace(" ", "").replace(":", "").trim();
	}

	public static boolean isVaildlDateFormat(String dateStr, boolean nullOk) {
		//
		if (nullOk == true && dateStr == null) {
			return true;
		}

		try {
			LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT_WITHOUT_DASH));
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static boolean isVaildDateTimeFormat(String dateStr, boolean nullOk) {
		if (nullOk == true && dateStr == null) {
			return true;
		}

		try {
			int strLen = dateStr.length();
			if (strLen == DEFAUT_DATE_FORMAT_LEN) {
				LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
			} else {
				LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS));
			}

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		System.out.println("getCurrentTime: " + getCurrentTime());
		System.out.println("getCurrentDateAsString1: " + getCurrentDateAsString());
		System.out.println("getCurrentDateAsString2: " + getCurrentDateAsString(DEFAULT_DATE_FORMAT));
		System.out.println("getCurrentDateAsString3: " + getCurrentDateAsString(YYYYMMDDHHMMSS));
		System.out.println("getCurrentDateAsString4: " + getCurrentDateAsString(DATE_FORMAT));
		System.out.println("getCurrentDateAsString5: " + getCurrentDateAsString(DATE_FORMAT_WITHOUT_DASH));
		System.out.println("getCurrentDateAsString7: " + getCurrentDateAsString(DATE_TIME_FORMAT));
		System.out.println("getCurrentDateAsString8: " + getCurrentDateAsString(DATE_TIME_FORMAT_WITHOUT_DASH));
		System.out.println("getCurrentDateAsString9: " + getCurrentDateAsString(TIME_FORMAT_WITHOUT_DASH));

		LocalDateTime dateTime = LocalDateTime.now();
		System.out.println("dateToString1: " + dateToString(dateTime, DEFAULT_DATE_FORMAT));
		System.out.println("dateToString2: " + dateToString(dateTime, YYYYMMDDHHMMSS));
		System.out.println("dateToString3: " + dateToString(dateTime, DATE_FORMAT));
		System.out.println("dateToString4: " + dateToString(dateTime, DATE_FORMAT_WITHOUT_DASH));
		System.out.println("dateToString5: " + dateToString(dateTime, DATE_TIME_FORMAT));
		System.out.println("dateToString6: " + dateToString(dateTime, DATE_TIME_FORMAT_WITHOUT_DASH));
		System.out.println("dateToString7: " + dateToString(dateTime, TIME_FORMAT_WITHOUT_DASH));

		System.out.println("stringToDate1: " + stringToDate("2021-01-19"));
		System.out.println("stringToDate2: " + stringToDate("20210119"));

		LocalDate firstDate = DateUtil.stringToDate("2020-01-01", DATE_FORMAT);
		LocalDate lastDate = LocalDate.now();
		System.out.println("diffDate1: " + diffDate(firstDate, lastDate));

		LocalDateTime firstDateTime = LocalDateTime.parse("2020-01-01T12:34:56", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		LocalDateTime lastDateTime = LocalDateTime.now();
		System.out.println("diffDate2: " + diffDateTime(firstDateTime, lastDateTime));

		System.out.println("isVaildlDateFormat: " + isVaildlDateFormat("20210119", false));
		System.out.println("isVaildDateTimeFormat1: " + isVaildDateTimeFormat("2021-01-21 18:14:20", false));
		System.out.println("isVaildDateTimeFormat2: " + isVaildDateTimeFormat("20210121181420", false));
	}
}
