package com.aylson.dc.sys.common;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.aylson.utils.StringUtil;

/**
 * @author ZhaoPing
 */
public class SystemConfig {
    private static Map<String, String> configMap = new HashMap<String, String>();

    /**
     * 初始系统配置
     */
    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("config/sysconfig");
            Set<String> keySet = bundle.keySet();
            for (String key : keySet) {
                configMap.put(key, bundle.getString(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 根据key获取配置内容
     * @param key
     * @return
     */
    public static String getConfigValueByKey(String key){
    	String configValue = null;
    	if(!StringUtil.isEmpty(key)){
    		configValue = configMap.get(key);
    	}
    	return configValue;
    }

    /**
     * 是否调试模式
     *
     * @return
     */
    public static boolean isDebugMode() {
        return "true".equals(configMap.get("isdebug"));
    }
    
    /**
     * 是否正式环境模式
     * @return
     */
    public static boolean isLiveMode(){
    	return "true".equals(configMap.get("islive"));
    }
    
    /**
     * 项目结算需要支付的金币
     * @return
     */
    public static Integer payGoldForPorject(){
    	Integer gold = 0;
    	String payGoldForPorject = SystemConfig.getConfigValueByKey("payGoldForPorject");
		if(StringUtil.isNotEmpty(payGoldForPorject)){
			try{
				gold = Integer.parseInt(payGoldForPorject);
			}catch(Exception e){
				gold = 0;
			}
		}
		return gold;
    }
    
    /**
     * 会员注册赠送的金币值
     * @return
     */
    public static Integer sendGoldForRegister(){
    	Integer gold = 0;
    	String payGoldForPorject = SystemConfig.getConfigValueByKey("sendGoldForRegister");
		if(StringUtil.isNotEmpty(payGoldForPorject)){
			try{
				gold = Integer.parseInt(payGoldForPorject);
			}catch(Exception e){
				gold = 0;
			}
		}
		return gold;
    }
    
   
}
