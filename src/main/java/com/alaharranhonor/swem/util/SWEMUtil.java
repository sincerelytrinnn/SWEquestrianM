package com.alaharranhonor.swem.util;

public class SWEMUtil {
	public static String checkTextOverflow(String text, int maxLimit) {
		if (text.length() > maxLimit - 2) {
			return text.substring(0, maxLimit - 2) + "...";
		} else {
			return text;
		}
	}
}
