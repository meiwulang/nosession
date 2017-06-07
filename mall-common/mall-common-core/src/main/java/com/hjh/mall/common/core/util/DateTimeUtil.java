package com.hjh.mall.common.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.StringUtil;

public class DateTimeUtil {

	public static final int STANDARD_DAYS_OF_YEAR = 365;

	public static final int STANDARD_DAYS_OF_WEEK = 7;

	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";

	public static final String FORMAT_MD_CHINESE = "M月d日";

	public static final String FORMAT_HHMMSS = "HH:mm:ss";

	public static final String FORMAT_YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String FORMAT_YYYYMMDDHHMMSS_NO_BREAK = "yyyyMMddHHmmss";

	public static final String FORMAT_YYYYMMDD_NO_BREAK = "yyyyMMdd";

	public static final String FORMAT_HHMMSS_NO_BREAK = "HHmmss";

	public static final String FORMAT_YYYYMMDDHHMMSSSSS_NO_BREAK = "yyyyMMddHHmmssSSS";

	public static final String FORMAT_DEFAULT = FORMAT_YYYYMMDDHHMMSS;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DateTimeUtil.class);

	private static final String ERR_MSG_INVALID_FORMAT = "Invalid format.";

	private static final String ERR_MSG_PARSE_FAILED = "Parse date failed.";

	public static Date toDate(String dateString, String format)
			throws ParseException {
		return new SimpleDateFormat(format).parse(dateString);
	}

	public static Date toDateOrNull(String dateString, String format) {
		if (StringUtil.isBlank(dateString)) {
			return null;
		}
		try {
			return new SimpleDateFormat(format).parse(dateString);
		} catch (IllegalArgumentException e) {
			LOGGER.error(ERR_MSG_INVALID_FORMAT, e);
			return null;
		} catch (ParseException e) {
			LOGGER.error(ERR_MSG_PARSE_FAILED, e);
			return null;
		}
	}

	public static Date toDate(String dateString) throws ParseException {
		return toDate(dateString, FORMAT_DEFAULT);
	}

	public static String toTimeString(Integer time) {
		String timeString = time.toString();
		Integer time_length = timeString.length();
		for (int i = 0; i < 6 - time_length; i++) {
			timeString = "0" + timeString;
		}
		return timeString;
	}

	public static Date toDateOrNull(String dateString) {
		return toDateOrNull(dateString, FORMAT_DEFAULT);
	}

	public static String toString(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static String toStringOrNull(Date date, String format) {
		if (null == date) {
			return null;
		}
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (IllegalArgumentException e) {
			LOGGER.error(ERR_MSG_INVALID_FORMAT, e);
			return null;
		}
	}

	public static String toStringOrEmpty(Date date, String format) {
		if (null == date) {
			return "";
		}
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (IllegalArgumentException e) {
			LOGGER.error(ERR_MSG_INVALID_FORMAT, e);
			return "";
		}
	}

	public static String toString(Date date) {
		return toString(date, FORMAT_DEFAULT);
	}

	public static String toStringOrNull(Date date) {
		return toStringOrNull(date, FORMAT_DEFAULT);
	}

	public static String transDateFormat(String dateString, String fromFormat,
			String toFormat) {
		if (!StringUtil.isBlank(dateString)) {
			Date date = toDateOrNull(dateString, fromFormat);
			if (null != date) {
				return toStringOrNull(date, toFormat);
			}
		}
		return dateString;
	}

	public static String getCurrentDateString() {
		return toString(new Date(), FORMAT_DEFAULT);
	}

	public static String getCurrentDateString(String format) {
		return toString(new Date(), format);
	}

	public static Calendar toCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Calendar toCalendar(Date date, Locale locale) {
		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(date);
		return calendar;
	}

	public static Calendar toCalendar(Date date, TimeZone timeZone) {
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTime(date);
		return calendar;
	}

	public static Calendar toCalendar(Date date, TimeZone timeZone,
			Locale locale) {
		Calendar calendar = Calendar.getInstance(timeZone, locale);
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 计算一年有多少天
	 * 
	 * @param startDate
	 *            开始时间
	 * @return 一年的天数
	 */
	public static int getDaysOfYear(Date startDate) {
		// 按开始时间创建日历
		Calendar calendar = toCalendar(startDate);
		// 获取开始时间在该年是第几天
		int startDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		// 往后翻一年
		calendar.add(Calendar.YEAR, 1);
		// 获取结束时间在该年（开始时间的下一年）是第几天
		int endDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		// 一年的天数 = 365天 + （一年中第几天的偏差）
		int yearDays = STANDARD_DAYS_OF_YEAR + (endDayOfYear - startDayOfYear);
		// 返回结果
		return yearDays;
	}

	/**
	 * 根据基准日历获取日历
	 * 
	 * @param baseCalendar
	 *            基准日历
	 * @param calendarField
	 *            日历字段，使用{@link Calendar}的<code>field</code>常量表示
	 * @param diff
	 *            时间差，以日历字段为单位，正数向更晚时间，负数向更早时间
	 * @return
	 */
	public static Calendar getCalendarFromBase(Calendar baseCalendar,
			int calendarField, int diff) {
		baseCalendar.add(calendarField, diff);
		return baseCalendar;
	}

	/**
	 * 根据基准日历获取时间
	 * 
	 * @param baseCalendar
	 *            基准日历
	 * @param calendarField
	 *            日历字段，使用{@link Calendar}的<code>field</code>常量表示
	 * @param diff
	 *            时间差，以日历字段为单位，正数向更晚时间，负数向更早时间
	 * @return
	 */
	public static Date getDateFromBase(Calendar baseCalendar, int calendarField,
			int diff) {
		return getCalendarFromBase(baseCalendar, calendarField, diff).getTime();
	}

	/**
	 * 根据基准时间获取日历
	 * 
	 * @param baseDate
	 *            基准时间
	 * @param calendarField
	 *            日历字段，使用{@link Calendar}的<code>field</code>常量表示
	 * @param diff
	 *            时间差，以日历字段为单位，正数向更晚时间，负数向更早时间
	 * @return
	 */
	public static Calendar getCalendarFromBase(Date baseDate, int calendarField,
			int diff) {
		// 按开始时间创建日历
		Calendar calendar = toCalendar(baseDate);
		calendar.add(calendarField, diff);
		return calendar;
	}

	/**
	 * 根据基准时间获取时间
	 * 
	 * @param baseDate
	 *            基准时间
	 * @param calendarField
	 *            日历字段，使用{@link Calendar}的<code>field</code>常量表示
	 * @param diff
	 *            时间差，以日历字段为单位，正数向更晚时间，负数向更早时间
	 * @return
	 */
	public static Date getDateFromBase(Date baseDate, int calendarField,
			int diff) {
		return getCalendarFromBase(baseDate, calendarField, diff).getTime();
	}

	/**
	 * 调整日历，将指定字段后的精度清除
	 * 
	 * @param calendar
	 *            原始日历
	 * @param field
	 *            精确到该精度，使用{@link Calendar}的<code>field</code>常量表示
	 * @return 清除精度后的日历
	 */
	public static Calendar trimToAccuracy(Calendar calendar, int field) {
		switch (field) {
			case Calendar.YEAR :
				calendar.set(Calendar.MONTH, 0);
			case Calendar.MONTH :
				calendar.set(Calendar.DAY_OF_MONTH, 1);
			case Calendar.DAY_OF_MONTH :
			case Calendar.DAY_OF_WEEK :
			case Calendar.DAY_OF_YEAR :
			case Calendar.DAY_OF_WEEK_IN_MONTH :
				calendar.set(Calendar.HOUR_OF_DAY, 0);
			case Calendar.HOUR_OF_DAY :
				calendar.set(Calendar.MINUTE, 0);
			case Calendar.MINUTE :
				calendar.set(Calendar.SECOND, 0);
			case Calendar.SECOND :
				calendar.set(Calendar.MILLISECOND, 0);
			case Calendar.MILLISECOND :
				break;
			case Calendar.WEEK_OF_YEAR :
			case Calendar.WEEK_OF_MONTH :
				calendar.set(Calendar.DAY_OF_WEEK, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				break;
			default :
				break;
		}
		return calendar;
	}

	/**
	 * 调整时间，将指定字段后的精度清除
	 * 
	 * @param date
	 *            原始时间
	 * @param field
	 *            精确到该精度，使用{@link Calendar}的<code>field</code>常量表示
	 * @return 清除精度后的时间
	 */
	public static Calendar trimToAccuracy(Date date, int field) {
		return trimToAccuracy(toCalendar(date), field);
	}

	/**
	 * 根据基准日历获取时间
	 * 
	 * @param baseCalendar
	 *            基准日历
	 * @param baseAccuracy
	 *            基准日历精度，日历字段，使用{@link Calendar}的<code>field</code>常量表示
	 * @param rollUnit
	 *            滚动单位，日历字段，使用{@link Calendar}的<code>field</code>常量表示
	 * @param diff
	 *            时间差，以日历字段为单位，正数向更晚时间，负数向更早时间
	 * @return
	 */
	public static Calendar getCalendarFromBase(Calendar baseCalendar,
			int baseAccuracy, int rollUnit, int diff) {
		return getCalendarFromBase(trimToAccuracy(baseCalendar, baseAccuracy),
				rollUnit, diff);
	}

	/**
	 * 根据基准时间获取时间
	 * 
	 * @param baseDate
	 *            基准时间
	 * @param baseAccuracy
	 *            基准时间精度，日历字段，使用{@link Calendar}的<code>field</code>常量表示
	 * @param rollUnit
	 *            滚动单位，日历字段，使用{@link Calendar}的<code>field</code>常量表示
	 * @param diff
	 *            时间差，以日历字段为单位，正数向更晚时间，负数向更早时间
	 * @return
	 */
	public static Date getDateFromBase(Date baseDate, int baseAccuracy,
			int rollUnit, int diff) {
		return getCalendarFromBase(trimToAccuracy(baseDate, baseAccuracy),
				rollUnit, diff).getTime();
	}

	/**
	 * 获取基准时间前的一个时间单位的时间，如“前一秒”<br />
	 * 昨天的最后一秒可以这样获取：
	 * <code>getDateFrontOfBase(new Date(), Calendar.DAY_OF_YEAR, Calendar.SECOND)</code>
	 * 
	 * @param baseDate
	 *            基准时间
	 * @param baseAccuracy
	 *            基准时间精度，日历字段，使用{@link Calendar}的<code>field</code>常量表示
	 * @param rollUnit
	 *            滚动单位，日历字段，使用{@link Calendar}的<code>field</code>常量表示
	 * @return
	 */
	public static Date getDateFrontOfBase(Date baseDate, int baseAccuracy,
			int rollUnit) {
		return getDateFromBase(baseDate, baseAccuracy, rollUnit, -1);
	}

	/**
	 * 获取指定时间当前的开始时间（00:00:00）。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartOfDay(Date date) {
		return trimToAccuracy(date, Calendar.DAY_OF_YEAR).getTime();
	}

	/**
	 * 获取指定时间当前的结束时间（23:59:59）。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndOfDay(Date date) {
		Calendar calendar = toCalendar(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取两个日期的差值
	 * 
	 * @param start
	 * @param end
	 * @param firstDayCalc
	 *            第一天是否算进去
	 * @return
	 */
	public static Integer getDateDiffer(Date start, Date end,
			boolean firstDayCalc) {
		long diff = end.getTime() - start.getTime();
		try {
			int r = (int) (diff / (1000 * 60 * 60 * 24));
			return firstDayCalc ? r + 1 : r;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * date的下一天
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期
	 */
	public static Date getNextDate(Date date) {
		Calendar calendar = toCalendar(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
	}

	/**
	 * 获得两个时间的差值精确到秒 建议不要取时间相差太久
	 * 
	 * @param start
	 * @param end
	 * @return 相差秒
	 */
	public static Integer getTimeDiff(Date start, Date end) {
		Calendar cr1 = Calendar.getInstance();
		Calendar cr2 = Calendar.getInstance();
		cr1.setTime(start);
		cr2.setTime(end);
		int r = (int) ((cr1.getTimeInMillis() - cr2.getTimeInMillis()) / 1000);
		return r;

	}

	/**
	 * 获得本周的周一和周日的日期
	 * 
	 * @return
	 */

	/**
	 * 获得N天后的日期
	 * 
	 * @return
	 */
	public static String getDayByN(int n, String format) {
		// 获取当前月第一天：
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, n);
		return toStringOrEmpty(cal.getTime(), format);
	}

	public static String getDayYYYYMMDDByN(int n) {
		return getDayByN(n, FORMAT_YYYYMMDD_NO_BREAK);
	}
}
