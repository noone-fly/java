package com.pierre.jsontest.TestJson;

import net.sf.json.JSONObject;

/**
 * 1，安装maven在你开发机
 * 2，eclipse里面配置maven
 * 3，创建maven项目，
 * 4，在pom.xml中添加jar dependency
 */
public class App {
	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		JSONObject obj1 = new JSONObject();
		obj.put("name", "1234");
		obj.put("uid", "12345");
		obj.put("clientRequest", obj1);
		// 调用toString()方法可直接将其内容打印出来
		System.out.println(obj.toString());

	}
}
