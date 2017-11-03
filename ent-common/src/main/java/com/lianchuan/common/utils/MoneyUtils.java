package com.lianchuan.common.utils;

public class MoneyUtils {

	/**
	 * 将分转换为元<br>
	 * 例如:<br>
	 * 123--->1.23<br>
	 * 1--->0.01<br>
	 * 100--->1.00
	 * 
	 * @param amount
	 *            单位:分
	 * @return
	 */
	public static String getMoney(long amount) {
		String symbol = "";
		if (amount < 0) {
			symbol = "-";
			amount = -amount;
		}
		String money = "";
		if (amount < 10) {
			money = "0.0" + amount;
		} else if (amount < 100) {
			money = "0." + amount;
		} else {
			money = amount + "";
			money = money.substring(0, money.length() - 2) + "." + money.substring(money.length() - 2, money.length());
		}
		return symbol + money;
	}

	/**
	 * 将分转换成元 123->1.23 1->0.01 200->2
	 * 
	 * @param amount
	 * @return
	 */
	public static String formatMoney(int amount) {
		String symbol = "";
		if (amount < 0) {
			symbol = "-";
			amount = -amount;
		}
		int yuan = amount / 100;
		int fen = amount - 100 * yuan;
		if (fen == 0) {
			return String.valueOf(yuan);
		} else if (fen < 10) {
			return String.format("%d.0%d", yuan, fen);
		}
		return symbol + String.format("%d.%d", yuan, fen);
	}
}
