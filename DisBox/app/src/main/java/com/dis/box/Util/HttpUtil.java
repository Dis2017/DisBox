package com.dis.box.Util;
import okhttp3.*;

public class HttpUtil
{
	/**
	*发送OkHttp请求
	*/
	public static void sendOkHttpRequest(String addres, okhttp3.Callback callback)
	{
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			.url(addres)
			.build();
		client.newCall(request).enqueue(callback);
	}
}
