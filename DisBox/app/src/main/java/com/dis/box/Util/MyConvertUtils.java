package com.dis.box.Util;
import java.math.*;

public class MyConvertUtils
{
	public static float BToMB(long b)
	{
		BigDecimal bd = new BigDecimal((float)b/1024/1024);
		return bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}
}
