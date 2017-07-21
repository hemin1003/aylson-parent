package com.aylson.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 地图相关工具类
 * @author Administrator
 *
 */
public class MapUtil {

	//高德地图key，暂时不申请，使用的是网上的
	private static String KEY = "aa4a48297242d22d2b3fd6eddfe62217";
	private static Pattern pattern = Pattern.compile("\"location\":\"(\\d+\\.\\d+),(\\d+\\.\\d+)\"");

	/**
	 * 高德地图根据地址获取经纬图
	 * @param address
	 * @return
	 */
	public static float[] addressToGPS(String address) {
		try {

			String url = String.format("http://restapi.amap.com/v3/geocode/geo?&s=rsv3&address=%s&key=%s", address,	KEY);
			URL myURL = null;
			URLConnection httpsConn = null;
			try {
				myURL = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			InputStreamReader insr = null;
			BufferedReader br = null;
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
			if (httpsConn != null) {
				insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
				br = new BufferedReader(insr);
				String data = "";
				String line = null;
				while ((line = br.readLine()) != null) {
					data += line;
				}
				Matcher matcher = pattern.matcher(data);
				if (matcher.find() && matcher.groupCount() == 2) {
					float[] gps = new float[2];
					gps[0] = Float.valueOf(matcher.group(1));
					gps[1] = Float.valueOf(matcher.group(2));
					return gps;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public static void main(String[] args) {
		float[] data = MapUtil.addressToGPS("广东省天河区方圆E时光A座");
		System.out.println("经度:" + data[0]);
		System.out.println("纬度:" + data[1]);
	}
}
