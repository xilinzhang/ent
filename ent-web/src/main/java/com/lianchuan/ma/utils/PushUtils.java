package com.lianchuan.ma.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class PushUtils {

	public static Map<String, Object> sort(JSONObject jsonObject) {
		List<String> keys = new ArrayList<String>(jsonObject.keySet());
		List<String> keys1 = null;
		Map<String, Object> map = new TreeMap<>();
		Map<String, Object> map1 = new TreeMap<>();
		JSONObject js = null;
		Object jn = null;
		String va = "";
		for (int i = 0; i < keys.size(); i++) {
			String k = (String) keys.get(i);
			jn = (Object) jsonObject.get(k);
			if (jn instanceof JSONObject) {
				js = (JSONObject) jn;
				map1 = new TreeMap<String, Object>();
				keys1 = new ArrayList<String>(js.keySet());
				for (int a = 0; a < keys1.size(); a++) {
					String k1 = (String) keys1.get(a);
					va = js.getString(k1);
					map1.put(k1, va);
				}
				map.put(k, map1);
			} else {
				va = (String) jn;
				map.put(k, va);
			}
		}
		return map;
	}

	public static String createLinkString(Map<String, String> params, boolean encode) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		String charset = "UTF-8";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (encode) {
				try {
					value = URLEncoder.encode(URLEncoder.encode(value, charset), charset);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if (i == keys.size() - 1) {
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("c", "1");
		map.put("a", "2");
		map.put("b", "3");
		String sortData = JSON.toJSONString(map, false);
		System.out.println(sortData);
		String content = createLinkString(map, false);
		System.out.println(content);
	}
}
