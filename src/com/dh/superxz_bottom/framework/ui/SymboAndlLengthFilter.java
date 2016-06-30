package com.dh.superxz_bottom.framework.ui;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

public class SymboAndlLengthFilter implements InputFilter {

	private int max;

	public SymboAndlLengthFilter(int max) {
		this.max = max;
	}

	public static int getCharacterNum(final String content) {
		if (null == content || "".equals(content)) {
			return 0;
		} else {
			return (content.length() + getChineseNum(content));
		}
	}

	public static boolean isPropertyUserName(String str) {
		if (!TextUtils.isEmpty(str)) {
			for (char c : str.toCharArray()) {
//				boolean isChinese = (c >= 0x0391 && c <= 0xFFE5);
				boolean isChinese=(c >= 0x4e00)&&(c <= 0x9fbb);
				boolean isEnglish = (c >= 0x0000 && c <= 0x00FF);
				if (!(isChinese || (isEnglish && ((c >= '0' && c <= '9')
						|| (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
						|| c == '.' || c == '@')))) {
					return false;
				}
			}
		}
		return true;
	}

	private static int getChineseNum(String s) {
		int num = 0;
		char[] myChar = s.toCharArray();
		for (int i = 0; i < myChar.length; i++) {
			if ((char) (byte) myChar[i] != myChar[i]) {
				num++;
			}
		}
		return num;
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		if (
//				!isPropertyUserName(source.toString())||
				getCharacterNum(dest.toString())
				+ getCharacterNum(source.toString()) > max) {
			return "";
		} else {
			return null;
		}
	}
}
