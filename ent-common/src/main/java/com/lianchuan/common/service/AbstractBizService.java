package com.lianchuan.common.service;

import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.lianchuan.common.exception.ParamException;
import com.lianchuan.common.utils.IDCardUtils;

/**
 * 业务层基础服务类
 */
public abstract class AbstractBizService extends AbstractService {

	/** 电话号码正则表达式 */
	private static final String PHONE_PATTERN = "^(14|17|13|15|18)[0-9]{9}$";

	/**
	 * 时间格式化
	 * 
	 * @param date
	 * @param patten
	 * @return
	 */
	public String format(Date date, String patten) {
		if (date == null) {
			return "";
		}
		return DateFormatUtils.format(date, patten);
	}

	/**
	 * 时间格式化<br>
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/** 检查手机电话号码格式 */
	public void checkPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			throw new ParamException("请输入手机号");
		}
		if (!phone.matches(PHONE_PATTERN)) {
			throw new ParamException("手机号格式不正确");
		}
	}

	/** 检查身份证格式 */
	public void checkIdentity(String identity) {
		checkString(identity);
		if (!IDCardUtils.isIdcard(identity)) {
			throw new ParamException("身份证格式错误");
		}
	}

	/** 检查字符串格式 */
	public void checkString(String s) {
		if (StringUtils.isBlank(s)) {
			throw new ParamException("参数不能为空");
		}
	}

	/** 检查对象是否为空 */
	public void checkObject(Object o) {
		if (o == null) {
			throw new ParamException("参数不能为空");
		}
	}

	/** 检查列表是否为空 */
	public void checkList(Collection<?> list) {
		if (CollectionUtils.isEmpty(list)) {
			throw new ParamException("参数不能为空");
		}
	}

	/** 检查字符串长度:为空时直接返回 */
	public void checkStringLength(String s, int length) {
		checkStringLength(s, length, "参数过长");
	}

	/** 检查字符串长度:为空时直接返回 */
	public void checkStringLength(String s, int length, String des) {
		if (s == null) {
			return;
		}
		if (s.length() > length) {
			throw new ParamException(des);
		}
	}

	/**
	 * 检查是否是数字可以包含小数点
	 * @param str
	 * @return
	 */
	public static boolean checkNumber(String str) {
		String reg = "^[0-9]+(.[0-9]+)?$";
		return str.matches(reg);
	}
	
    /**
     * 是否为数字的字符串
     * 
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > '9' || str.charAt(i) < '0') {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是固定范围内的数字的字符串
     * 
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static boolean isNumber(String str, int min, int max) {
        if (!isNumber(str)) {
            return false;
        }
        int number = Integer.parseInt(str);
        return number >= min && number <= max;
    }

    /**
     * 判断字符是否为整数或浮点数可以为负数. <br>
     * 
     * @param str
     *            字符
     * @return 若为整数或浮点数则返回 <code>true</code>, 否则返回 <code>false</code>
     */
    public static boolean isNumeric(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        String regex = "(\\+|-){0,1}(\\d+)([.]?)(\\d*)"; // 整数或浮点数
        return Pattern.matches(regex, str);
    }
	
	public static void main(String[] args) {
		System.out.println(checkNumber("0.211"));
		System.out.println(checkNumber("1.2"));
		System.out.println(checkNumber("1.0"));
		System.out.println(checkNumber("1."));
		System.out.println(checkNumber("99999"));
		System.out.println(checkNumber("01"));
		System.out.println(checkNumber("01.2"));
		System.out.println(checkNumber(".2"));
		System.out.println(checkNumber("99999.222222"));
	}
	
}
