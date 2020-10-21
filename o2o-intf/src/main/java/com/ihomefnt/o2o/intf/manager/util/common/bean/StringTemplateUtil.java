/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年2月14日
 * Description:StringTemplateUtil.java 
 */
package com.ihomefnt.o2o.intf.manager.util.common.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串模版
 * 
 * @author chong
 */
public class StringTemplateUtil {

	private static char delimStart = '{';
	private static char delimStop = '}';
	private static String delimStr = "{}";
	private static final char ESCAPE_CHAR = '\\';

	public static String format(final String messagePattern, final Object... argArray) {
		int i = 0;
		int j;
		// use string builder for better multicore performance
		StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);

		int L;
		for (L = 0; L < argArray.length; L++) {

			j = messagePattern.indexOf(delimStr, i);

			if (j == -1) {
				// no more variables
				if (i == 0) { // this is a simple string
					return messagePattern;
					// return new FormattingTuple(messagePattern, argArray,
					// throwableCandidate);
				} else { // add the tail string which contains no variables and
							// return
					// the result.
					sbuf.append(messagePattern.substring(i, messagePattern.length()));
					// return new FormattingTuple(sbuf.toString(), argArray,
					// throwableCandidate);
					return sbuf.toString();
				}
			} else {
				if (isEscapedDelimeter(messagePattern, j)) {
					if (!isDoubleEscaped(messagePattern, j)) {
						L--; // DELIM_START was escaped, thus should not be
								// incremented
						sbuf.append(messagePattern.substring(i, j - 1));
						sbuf.append(delimStart);
						i = j + 1;
					} else {
						// The escape character preceding the delimiter start is
						// itself escaped: "abc x:\\{}"
						// we have to consume one backward slash
						sbuf.append(messagePattern.substring(i, j - 1));
						deeplyAppendParameter(sbuf, argArray[L], new HashMap<Object[], Object>());
						i = j + delimStr.length();
					}
				} else {
					// normal case
					sbuf.append(messagePattern.substring(i, j));
					deeplyAppendParameter(sbuf, argArray[L], new HashMap<Object[], Object>());
					i = j + delimStr.length();
				}
			}
		}
		// append the characters following the last {} pair.
		sbuf.append(messagePattern.substring(i, messagePattern.length()));
		// return new FormattingTuple(sbuf.toString(), argArray,
		// throwableCandidate);
		return sbuf.toString();
	}

	final static boolean isEscapedDelimeter(String messagePattern, int delimeterStartIndex) {

		if (delimeterStartIndex == 0) {
			return false;
		}
		char potentialEscape = messagePattern.charAt(delimeterStartIndex - 1);
		if (potentialEscape == ESCAPE_CHAR) {
			return true;
		} else {
			return false;
		}
	}

	final static boolean isDoubleEscaped(String messagePattern, int delimeterStartIndex) {
		if (delimeterStartIndex >= 2 && messagePattern.charAt(delimeterStartIndex - 2) == ESCAPE_CHAR) {
			return true;
		} else {
			return false;
		}
	}

	private static void deeplyAppendParameter(StringBuilder sbuf, Object o, Map<Object[], Object> seenMap) {
		if (o == null) {
			sbuf.append("null");
			return;
		}
		if (!o.getClass().isArray()) {
			safeObjectAppend(sbuf, o);
		} else {
			// check for primitive array types because they
			// unfortunately cannot be cast to Object[]
			if (o instanceof boolean[]) {
				booleanArrayAppend(sbuf, (boolean[]) o);
			} else if (o instanceof byte[]) {
				byteArrayAppend(sbuf, (byte[]) o);
			} else if (o instanceof char[]) {
				charArrayAppend(sbuf, (char[]) o);
			} else if (o instanceof short[]) {
				shortArrayAppend(sbuf, (short[]) o);
			} else if (o instanceof int[]) {
				intArrayAppend(sbuf, (int[]) o);
			} else if (o instanceof long[]) {
				longArrayAppend(sbuf, (long[]) o);
			} else if (o instanceof float[]) {
				floatArrayAppend(sbuf, (float[]) o);
			} else if (o instanceof double[]) {
				doubleArrayAppend(sbuf, (double[]) o);
			} else {
				objectArrayAppend(sbuf, (Object[]) o, seenMap);
			}
		}
	}

	private static void safeObjectAppend(StringBuilder sbuf, Object o) {
		try {
			String oAsString = o.toString();
			sbuf.append(oAsString);
		} catch (Throwable t) {
			System.err.println(
					"SLF4J: Failed toString() invocation on an object of type [" + o.getClass().getName() + "]");
			t.printStackTrace();
			sbuf.append("[FAILED toString()]");
		}

	}

	private static void intArrayAppend(StringBuilder sbuf, int[] a) {
		sbuf.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}
		sbuf.append(']');
	}

	private static void longArrayAppend(StringBuilder sbuf, long[] a) {
		sbuf.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}
		sbuf.append(']');
	}

	private static void floatArrayAppend(StringBuilder sbuf, float[] a) {
		sbuf.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}
		sbuf.append(']');
	}

	private static void doubleArrayAppend(StringBuilder sbuf, double[] a) {
		sbuf.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}
		sbuf.append(']');
	}

	private static void objectArrayAppend(StringBuilder sbuf, Object[] a, Map<Object[], Object> seenMap) {
		sbuf.append('[');
		if (!seenMap.containsKey(a)) {
			seenMap.put(a, null);
			final int len = a.length;
			for (int i = 0; i < len; i++) {
				deeplyAppendParameter(sbuf, a[i], seenMap);
				if (i != len - 1)
					sbuf.append(", ");
			}
			// allow repeats in siblings
			seenMap.remove(a);
		} else {
			sbuf.append("...");
		}
		sbuf.append(']');
	}

	private static void booleanArrayAppend(StringBuilder sbuf, boolean[] a) {
		sbuf.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}
		sbuf.append(']');
	}

	private static void byteArrayAppend(StringBuilder sbuf, byte[] a) {
		sbuf.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}
		sbuf.append(']');
	}

	private static void charArrayAppend(StringBuilder sbuf, char[] a) {
		sbuf.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}
		sbuf.append(']');
	}

	private static void shortArrayAppend(StringBuilder sbuf, short[] a) {
		sbuf.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}
		sbuf.append(']');
	}

	public static void main(String[] args) {
		
		StringTemplateUtil.setDelimStart('【');
		StringTemplateUtil.setDelimStop('】');
		StringTemplateUtil.setDelimStr("【变量】");
		
		String resultStr = StringTemplateUtil.format("测试【变量】, 【变量】, 【变量】", "参数1", "参数2", "参数3");

		System.err.println(resultStr);

		String resultStr2 = StringTemplateUtil.format(
				"尊敬的用户您好！感谢购买艾佳产品，我们已为您充值【变量】艾积分（价值【变量】人民币）。您可在艾佳生活App的“艾商城”中免费兑换礼品。数量有限，一定记得抓紧哦！（下载地址： http://t.cn/RfKqMx1 ）",
				"100", "1000", "test");

		System.err.println(resultStr2);

		String resultStr3 = StringTemplateUtil.format("测试12334343", "参数1", "参数2", "参数3");
		
		System.err.println(resultStr3);

	}

	public static char getDelimStart() {
		return delimStart;
	}

	public static void setDelimStart(char delimStart) {
		StringTemplateUtil.delimStart = delimStart;
	}

	public static char getDelimStop() {
		return delimStop;
	}

	public static void setDelimStop(char delimStop) {
		StringTemplateUtil.delimStop = delimStop;
	}

	public static String getDelimStr() {
		return delimStr;
	}

	public static void setDelimStr(String delimStr) {
		StringTemplateUtil.delimStr = delimStr;
	}

}
