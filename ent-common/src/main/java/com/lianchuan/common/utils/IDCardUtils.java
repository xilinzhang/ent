package com.lianchuan.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 身份证工具类
 * 
 */
public class IDCardUtils extends StringUtils {

	/** 中国公民身份证号码最小长度。 */
	public static final int CHINA_ID_MIN_LENGTH = 15;

	/** 中国公民身份证号码最大长度。 */
	public static final int CHINA_ID_MAX_LENGTH = 18;

	/** 省、直辖市代码表 */
	public static final String cityCode[] = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91" };

	/** 每位加权因子 */
	public static final int[] POWER_LIST = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/** 第18位校检码 */
	public static final int[] PARITYBIT = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
	/** 最低年限 */
	public static final int MIN = 1930;

	public static Map<Integer, String> cityCodes = new HashMap<Integer, String>();

	static {
		cityCodes.put(11, "北京");
		cityCodes.put(12, "天津");
		cityCodes.put(13, "河北");
		cityCodes.put(14, "山西");
		cityCodes.put(15, "内蒙古");
		cityCodes.put(21, "辽宁");
		cityCodes.put(22, "吉林");
		cityCodes.put(23, "黑龙江");
		cityCodes.put(31, "上海");
		cityCodes.put(32, "江苏");
		cityCodes.put(33, "浙江");
		cityCodes.put(34, "安徽");
		cityCodes.put(35, "福建");
		cityCodes.put(36, "江西");
		cityCodes.put(37, "山东");
		cityCodes.put(41, "河南");
		cityCodes.put(42, "湖北");
		cityCodes.put(43, "湖南");
		cityCodes.put(44, "广东");
		cityCodes.put(45, "广西");
		cityCodes.put(46, "海南");
		cityCodes.put(50, "重庆");
		cityCodes.put(51, "四川");
		cityCodes.put(52, "贵州");
		cityCodes.put(53, "云南");
		cityCodes.put(54, "西藏");
		cityCodes.put(61, "陕西");
		cityCodes.put(62, "甘肃");
		cityCodes.put(63, "青海");
		cityCodes.put(64, "宁夏");
		cityCodes.put(65, "新疆");
		cityCodes.put(71, "台湾");
		cityCodes.put(81, "香港");
		cityCodes.put(82, "澳门");
		cityCodes.put(91, "国外");
	}

	/**
	 * 
	 * 身份证验证
	 * 
	 * @param s
	 *            号码内容
	 * @return 是否有效 <br>
	 *         null和"" 都是false
	 */
	public static boolean isIdcard(String s) {
		if (s == null || (s.length() != 15 && s.length() != 18))
			return false;
		final char[] cs = s.toUpperCase().toCharArray();
		// 校验位数
		int power = 0;
		for (int i = 0; i < cs.length; i++) {
			if (i == cs.length - 1 && cs[i] == 'X')
				break;// 最后一位可以 是X或x
			if (cs[i] < '0' || cs[i] > '9')
				return false;
			if (i < cs.length - 1) {
				power += (cs[i] - '0') * POWER_LIST[i];
			}
		}

		// 校验区位码
		if (!cityCodes.containsKey(Integer.valueOf(s.substring(0, 2)))) {
			return false;
		}

		// 校验年份
		String year = s.length() == 15 ? getIdcardCalendar() + s.substring(6, 8) : s.substring(6, 10);

		final int iyear = Integer.parseInt(year);
		if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR))
			return false;// 1900年的PASS，超过今年的PASS

		// 校验月份
		String month = s.length() == 15 ? s.substring(8, 10) : s.substring(10, 12);
		final int imonth = Integer.parseInt(month);
		if (imonth < 1 || imonth > 12) {
			return false;
		}

		// 校验天数
		String day = s.length() == 15 ? s.substring(10, 12) : s.substring(12, 14);
		final int iday = Integer.parseInt(day);
		if (iday < 1 || iday > 31)
			return false;

		// 校验一个合法的年月日:已经得到校验了
		/*
		 * if(!validate(iyear, imonth, iday)) return false;
		 */

		// 校验"校验码"
		if (s.length() == 15)
			return true;
		return cs[cs.length - 1] == PARITYBIT[power % 11];
	}

	private static int getIdcardCalendar() {
		GregorianCalendar curDay = new GregorianCalendar();
		int curYear = curDay.get(Calendar.YEAR);
		int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));
		return year2bit;
	}

	@Deprecated
	static boolean validate(int year, int imonth, int iday) {
		// 比如考虑闰月，大小月等
		return true;
	}

	/**
	 * 将15位身份证号码转换为18位
	 * 
	 * @param idCard
	 *            15位身份编码
	 * @return 18位身份编码
	 */
	public static String conver15CardTo18(String idCard) {
		String idCard18 = "";
		if (idCard.length() != CHINA_ID_MIN_LENGTH) {
			return idCard;
		}
		if (isNum(idCard)) {
			// 获取出生年月日
			String birthday = idCard.substring(6, 12);
			Date birthDate = null;
			try {
				birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			if (birthDate != null)
				cal.setTime(birthDate);
			// 获取出生年(完全表现形式,如：2010)
			String sYear = String.valueOf(cal.get(Calendar.YEAR));
			idCard18 = idCard.substring(0, 6) + sYear + idCard.substring(8);
			// 转换字符数组
			char[] cArr = idCard18.toCharArray();
			if (cArr != null) {
				int[] iCard = converCharToInt(cArr);
				int iSum17 = getPowerSum(iCard);
				// 获取校验位
				String sVal = getCheckCode18(iSum17);
				if (sVal.length() > 0) {
					idCard18 += sVal;
				} else {
					return idCard;
				}
			}
		} else {
			return idCard;
		}
		return idCard18;
	}

	/**
	 * 验证身份证是否合法
	 */
	public static boolean validateCard(String idCard) {
		String card = idCard.trim();
		if (validateIdCard18(card)) {
			return true;
		}
		if (validateIdCard15(card)) {
			return true;
		}
		return false;
	}

	/**
	 * 验证18位身份编码是否合法
	 * 
	 * @param idCard
	 *            身份编码
	 * @return 是否合法
	 */
	public static boolean validateIdCard18(String idCard) {
		boolean bTrue = false;
		if (idCard.length() == CHINA_ID_MAX_LENGTH) {
			// 前17位
			String code17 = idCard.substring(0, 17);
			// 第18位
			String code18 = idCard.substring(17, CHINA_ID_MAX_LENGTH);
			if (isNum(code17)) {
				char[] cArr = code17.toCharArray();
				if (cArr != null) {
					int[] iCard = converCharToInt(cArr);
					int iSum17 = getPowerSum(iCard);
					// 获取校验位
					String val = getCheckCode18(iSum17);
					if (val.length() > 0) {
						if (val.equalsIgnoreCase(code18)) {
							bTrue = true;
						}
					}
				}
			}
		}
		return bTrue;
	}

	/**
	 * 验证15位身份编码是否合法
	 * 
	 * @param idCard
	 *            身份编码
	 * @return 是否合法
	 */
	public static boolean validateIdCard15(String idCard) {
		if (idCard.length() != CHINA_ID_MIN_LENGTH) {
			return false;
		}
		if (isNum(idCard)) {
			Integer proCode= null;
			try {
				proCode = Integer.parseInt(idCard.substring(0, 2));
			} catch (Exception e) {
				return false;
			}
			if (cityCodes.get(proCode) == null) {
				return false;
			}
			String birthCode = idCard.substring(6, 12);
			Date birthDate = null;
			try {
				birthDate = new SimpleDateFormat("yy").parse(birthCode.substring(0, 2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			if (birthDate != null)
				cal.setTime(birthDate);
			if (!valiDate(cal.get(Calendar.YEAR), Integer.valueOf(birthCode.substring(2, 4)), Integer.valueOf(birthCode.substring(4, 6)))) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 验证香港身份证号码(存在Bug，部份特殊身份证无法检查)
	 * <p>
	 * 身份证前2位为英文字符，如果只出现一个英文字符则表示第一位是空格，对应数字58 前2位英文字符A-Z分别对应数字10-35
	 * 最后一位校验码为0-9的数字加上字符"A"，"A"代表10
	 * </p>
	 * <p>
	 * 将身份证号码全部转换为数字，分别对应乘9-1相加的总和，整除11则证件号码有效
	 * </p>
	 * 
	 * @param idCard
	 *            身份证号码
	 * @return 验证码是否符合
	 */
	public static boolean validateHKCard(String idCard) {
		String card = idCard.replaceAll("[\\(|\\)]", "");
		Integer sum = 0;
		if (card.length() == 9) {
			sum = (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 9 + (Integer.valueOf(card.substring(1, 2).toUpperCase().toCharArray()[0]) - 55) * 8;
			card = card.substring(1, 9);
		} else {
			sum = 522 + (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 8;
		}
		String mid = card.substring(1, 7);
		String end = card.substring(7, 8);
		char[] chars = mid.toCharArray();
		Integer iflag = 7;
		for (char c : chars) {
			sum = sum + Integer.valueOf(c + "") * iflag;
			iflag--;
		}
		if (end.toUpperCase().equals("A")) {
			sum = sum + 10;
		} else {
			sum = sum + Integer.valueOf(end);
		}
		return (sum % 11 == 0) ? true : false;
	}

	/**
	 * 将字符数组转换成数字数组
	 * 
	 * @param ca
	 *            字符数组
	 * @return 数字数组
	 */
	public static int[] converCharToInt(char[] ca) {
		int len = ca.length;
		int[] iArr = new int[len];
		try {
			for (int i = 0; i < len; i++) {
				iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return iArr;
	}

	/**
	 * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
	 * 
	 * @param iArr
	 * @return 身份证编码。
	 */
	public static int getPowerSum(int[] iArr) {
		int iSum = 0;
		if (POWER_LIST.length == iArr.length) {
			for (int i = 0; i < iArr.length; i++) {
				for (int j = 0; j < POWER_LIST.length; j++) {
					if (i == j) {
						iSum = iSum + iArr[i] * POWER_LIST[j];
					}
				}
			}
		}
		return iSum;
	}

	/**
	 * 将power和值与11取模获得余数进行校验码判断
	 * 
	 * @param iSum
	 * @return 校验位
	 */
	public static String getCheckCode18(int iSum) {
		String sCode = "";
		switch (iSum % 11) {
		case 10:
			sCode = "2";
			break;
		case 9:
			sCode = "3";
			break;
		case 8:
			sCode = "4";
			break;
		case 7:
			sCode = "5";
			break;
		case 6:
			sCode = "6";
			break;
		case 5:
			sCode = "7";
			break;
		case 4:
			sCode = "8";
			break;
		case 3:
			sCode = "9";
			break;
		case 2:
			sCode = "X";
			break;
		case 1:
			sCode = "0";
			break;
		case 0:
			sCode = "1";
			break;
		}
		return sCode;
	}

	/**
	 * 根据身份编号获取年龄
	 * 
	 * @param idCard
	 *            身份编号
	 * @return 年龄
	 */
	public static int getAgeByIdCard(String idCard) {
		return getAgeByIdCard(idCard, new Date());
	}

	/**
	 * 根据身份编号获取年龄
	 * 
	 * @param idCard
	 *            身份编号
	 * @param now
	 *            计算年龄的时间
	 * @return 年龄
	 */
	public static int getAgeByIdCard(String idCard, Date now) {
		if (StringUtils.isBlank(idCard)) {
			return 0;
		}
		int iAge = 0;
		if (idCard.length() == CHINA_ID_MIN_LENGTH) {
			idCard = conver15CardTo18(idCard);
		}
		SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
		SimpleDateFormat format_M = new SimpleDateFormat("MM");
		SimpleDateFormat format_d = new SimpleDateFormat("dd");

		String birth_year = idCard.substring(6, 10);
		String birth_month = idCard.substring(10, 12);
		String birth_day = idCard.substring(12, 14);

		Date nowdate = now;
		String now_year = format_y.format(nowdate);
		String now_month = format_M.format(nowdate);
		String now_day = format_d.format(nowdate);

		iAge = Integer.parseInt(now_year) - Integer.parseInt(birth_year) - 1;
		if ((now_month.compareTo(birth_month) > 0) || (now_month.compareTo(birth_month) == 0 && now_day.compareTo(birth_day) >= 0)) {
			iAge++;
		}
		if (iAge < 0) {
			iAge = 0;
		}
		return iAge;
	}

	/**
	 * 根据身份编号获取18岁生日
	 * 
	 * @param idCard
	 * @return
	 */
	public static Date get18BirthByIdCard(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return null;
		}
		Integer len = idCard.length();
		if (len < CHINA_ID_MIN_LENGTH) {
			return null;
		} else if (len == CHINA_ID_MIN_LENGTH) {
			idCard = conver15CardTo18(idCard);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			String year = idCard.substring(6, 10);
			int year18 = Integer.valueOf(year) + 18;
			String month = idCard.substring(10, 12);
			String day = idCard.substring(12, 14);
			System.out.println(year18 + month + day);
			return sdf.parse(year18 + month + day);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 根据身份编号获取生日
	 * 
	 * @param idCard
	 *            身份编号
	 * @return 生日(yyyyMMdd)
	 */
	public static String getBirthByIdCard(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return null;
		}
		Integer len = idCard.length();
		if (len < CHINA_ID_MIN_LENGTH) {
			return null;
		} else if (len == CHINA_ID_MIN_LENGTH) {
			idCard = conver15CardTo18(idCard);
		}
		return idCard.substring(6, 14);
	}

	/**
	 * 根据身份编号获取生日年
	 * 
	 * @param idCard
	 *            身份编号
	 * @return 生日(yyyy)
	 */
	public static Short getYearByIdCard(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return null;
		}
		Integer len = idCard.length();
		if (len < CHINA_ID_MIN_LENGTH) {
			return null;
		} else if (len == CHINA_ID_MIN_LENGTH) {
			idCard = conver15CardTo18(idCard);
		}
		return Short.valueOf(idCard.substring(6, 10));
	}

	/**
	 * 根据身份编号获取生日月
	 * 
	 * @param idCard
	 *            身份编号
	 * @return 生日(MM)
	 */
	public static Short getMonthByIdCard(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return null;
		}
		Integer len = idCard.length();
		if (len < CHINA_ID_MIN_LENGTH) {
			return null;
		} else if (len == CHINA_ID_MIN_LENGTH) {
			idCard = conver15CardTo18(idCard);
		}
		return Short.valueOf(idCard.substring(10, 12));
	}

	/**
	 * 根据身份编号获取生日天
	 * 
	 * @param idCard
	 *            身份编号
	 * @return 生日(dd)
	 */
	public static Short getDateByIdCard(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return null;
		}
		Integer len = idCard.length();
		if (len < CHINA_ID_MIN_LENGTH) {
			return null;
		} else if (len == CHINA_ID_MIN_LENGTH) {
			idCard = conver15CardTo18(idCard);
		}
		return Short.valueOf(idCard.substring(12, 14));
	}
	
	/**
	 * 根据身份编号获取性别
	 * 
	 * @param idCard
	 *            身份编号
	 * @return 性别(0，1，2)
	 */
	public static int getGenderByIdCard2(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return 0;
		}
		int gender = 0;
		if (idCard.length() == CHINA_ID_MIN_LENGTH) {
			idCard = conver15CardTo18(idCard);
		}
		String sCardNum = idCard.substring(16, 17);
		if (Integer.parseInt(sCardNum) % 2 != 0) {
			gender = 1;
		} else {
			gender = 2;
		}
		return gender;
	}

	/**
	 * 根据身份编号获取性别
	 * 
	 * @param idCard
	 *            身份编号
	 * @return 性别(男，女，未知)
	 */
	public static String getGenderByIdCard(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return "未知";
		}
		String gender = "未知";
		if (idCard.length() == CHINA_ID_MIN_LENGTH) {
			idCard = conver15CardTo18(idCard);
		}
		String sCardNum = idCard.substring(16, 17);
		if (Integer.parseInt(sCardNum) % 2 != 0) {
			gender = "男";
		} else {
			gender = "女";
		}
		return gender;
	}

	/**
	 * 根据身份编号获取性别
	 * 
	 * @param idCard
	 *            身份编号
	 * @return 性别(0，1，2)
	 */
	public static int getGenderByIdCardCode(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return 0;
		}
		int gender = 0;
		if (idCard.length() == CHINA_ID_MIN_LENGTH) {
			idCard = conver15CardTo18(idCard);
		}
		String sCardNum = idCard.substring(16, 17);
		if (Integer.parseInt(sCardNum) % 2 != 0) {
			gender = 1;
		} else {
			gender = 2;
		}
		return gender;
	}

	/**
	 * 根据身份编号获取户籍省份
	 * 
	 * @param idCard
	 *            身份编码
	 * @return 省级编码。
	 */
	public static String getProvinceByIdCard(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return null;
		}
		int len = idCard.length();
		String sProvince = null;
		Integer sProvinNum= null;

		if (len == CHINA_ID_MIN_LENGTH || len == CHINA_ID_MAX_LENGTH) {
			try {
				sProvinNum = Integer.parseInt(idCard.substring(0, 2));
			} catch (Exception e) {
				return sProvince;
			}
		}
		sProvince = cityCodes.get(sProvinNum);
		return sProvince;
	}

	/**
	 * 数字验证
	 * 
	 * @param val
	 * @return 提取的数字。
	 */
	public static boolean isNum(String val) {
		return val == null || "".equals(val) ? false : val.matches("^[0-9]*{1}");
	}

	/**
	 * 验证小于当前日期 是否有效
	 * 
	 * @param iYear
	 *            待验证日期(年)
	 * @param iMonth
	 *            待验证日期(月 1-12)
	 * @param iDate
	 *            待验证日期(日)
	 * @return 是否有效
	 */
	public static boolean valiDate(int iYear, int iMonth, int iDate) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int datePerMonth;
		if (iYear < MIN || iYear >= year) {
			return false;
		}
		if (iMonth < 1 || iMonth > 12) {
			return false;
		}
		switch (iMonth) {
		case 4:
		case 6:
		case 9:
		case 11:
			datePerMonth = 30;
			break;
		case 2:
			boolean dm = ((iYear % 4 == 0 && iYear % 100 != 0) || (iYear % 400 == 0)) && (iYear > MIN && iYear < year);
			datePerMonth = dm ? 29 : 28;
			break;
		default:
			datePerMonth = 31;
		}
		return (iDate >= 1) && (iDate <= datePerMonth);
	}

	public static void main(String[] args) {

		String[] idCard = { "140107820815121", "124578200402284571", "510521195402201748", "510522200402306644" };
		for (String sfz : idCard) {
			System.out.println(isIdcard(sfz));
		}

		System.out.println(conver15CardTo18("140107820815121").toUpperCase());

	}
}
