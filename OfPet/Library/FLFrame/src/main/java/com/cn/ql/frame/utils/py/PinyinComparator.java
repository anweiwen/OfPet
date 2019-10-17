package com.cn.ql.frame.utils.py;


import android.text.TextUtils;
import com.cn.ql.frame.bean.LocalContacts;

import java.util.Comparator;

public class PinyinComparator implements Comparator<LocalContacts> {

	public int compare(LocalContacts o1, LocalContacts o2) {
		String o1Letters = o1.getLetters();
		String o2Letters = o2.getLetters();

		int to = o1Letters.compareTo(o2Letters);
		if (TextUtils.isEmpty(o2Letters)){
			to = 1;
		}
		if (TextUtils.isEmpty(o1Letters)){
			to = -1;
		}
		return to;
	}

}
