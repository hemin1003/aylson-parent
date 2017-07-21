package com.fastweixin.api.config;

import com.fastweixin.api.response.GetJsApiTicketResponse;
import com.fastweixin.api.response.GetTokenResponse;
import com.fastweixin.exception.WeixinException;
import com.fastweixin.handle.ApiConfigChangeHandle;
import com.fastweixin.util.JSONUtil;
import com.fastweixin.util.NetWorkCenter;
import com.fastweixin.util.StrUtil;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * API配置类，项目中请保证其为单例
 * 实现观察者模式，用于监控token变化
 *
 * @author peiyu
 * @since 1.2
 */
public final class ApiConfig extends Observable implements Serializable {

    private static final Logger        LOG             = LoggerFactory.getLogger(ApiConfig.class);
    private final        AtomicBoolean tokenRefreshing = new AtomicBoolean(false);
    private final        AtomicBoolean jsRefreshing    = new AtomicBoolean(false);
    private final String  appid;
    private final String  secret;
    private static String  accessToken;
    private       String  jsApiTicket;
    private       boolean enableJsApi;
    private static long  jsTokenStartTime;
    private       long    weixinTokenStartTime;
    private static long  wxTokenStartTime;
    private static long  getTokenTimes;
    private static long  getTokenTimes2;

    /**
     * 构造方法一，实现同时获取access_token。不启用jsApi
     *
     * @param appid  公众号appid
     * @param secret 公众号secret
     */
    public ApiConfig(String appid, String secret) {
        this(appid, secret, false);
    }

    /**
     * 构造方法二，实现同时获取access_token，启用jsApi
     *
     * @param appid       公众号appid
     * @param secret      公众号secret
     * @param enableJsApi 是否启动js api
     */
    public ApiConfig(String appid, String secret, boolean enableJsApi) {
        this.appid = appid;
        this.secret = secret;
        this.enableJsApi = enableJsApi;
        long now = System.currentTimeMillis();
        long time = now - ApiConfig.wxTokenStartTime;
        System.out.println("==========================ApiConfig.wxTokenStartTime:"+ApiConfig.wxTokenStartTime+"=========================================");
        if(ApiConfig.wxTokenStartTime == 0){//第一次为0
        	initToken(now);
        }else{
        	 try {
                 //官方给出的超时时间是7200秒，这里用7100秒来做，防止出现已经过期的情况
                 if (time > 7000000 && tokenRefreshing.compareAndSet(false, true)) {
                     LOG.debug("准备刷新token.............");
                     initToken(now);
                 }
             } finally {
                 tokenRefreshing.set(false);
             }
        }
        if (enableJsApi) initJSToken(now);
    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getAccessToken() {
        long now = System.currentTimeMillis();
//        long time = now - this.weixinTokenStartTime;
        long time = now - ApiConfig.wxTokenStartTime;
        try {
            //官方给出的超时时间是7200秒，这里用7100秒来做，防止出现已经过期的情况
        	System.out.println("==============getAccessToken================"+(ApiConfig.getTokenTimes2+1)+"===============================================");
            if (time > 7000000 && tokenRefreshing.compareAndSet(false, true)) {
                LOG.debug("准备刷新token.............");
                initToken(now);
            }
        } finally {
            tokenRefreshing.set(false);
        }
        return accessToken;
    }

    public String getJsApiTicket() {
        if (enableJsApi) {
            long now = System.currentTimeMillis();
            try {
                //官方给出的超时时间是7200秒，这里用7100秒来做，防止出现已经过期的情况
                if (now - ApiConfig.jsTokenStartTime > 7000000 && jsRefreshing.compareAndSet(false, true)) {
                    getAccessToken();
                    initJSToken(now);
                    jsRefreshing.set(false);
                }
            } finally {
                jsRefreshing.set(false);
            }
        } else {
            jsApiTicket = null;
        }
        return jsApiTicket;
    }

    public boolean isEnableJsApi() {
        return enableJsApi;
    }

    public void setEnableJsApi(boolean enableJsApi) {
        this.enableJsApi = enableJsApi;
        if (!enableJsApi)
            this.jsApiTicket = null;
    }

    /**
     * 添加配置变化监听器
     *
     * @param handle 监听器
     */
    public void addHandle(final ApiConfigChangeHandle handle) {
        super.addObserver(handle);
    }

    /**
     * 移除配置变化监听器
     *
     * @param handle 监听器
     */
    public void removeHandle(final ApiConfigChangeHandle handle) {
        super.deleteObserver(handle);
    }

    /**
     * 移除所有配置变化监听器
     */
    public void removeAllHandle() {
        super.deleteObservers();
    }

    /**
     * 初始化微信配置，即第一次获取access_token
     *
     * @param refreshTime 刷新时间
     */
    private void initToken(final long refreshTime) {
    	System.out.println("=======================获取token次数=====ApiConfig.getTokenTimes================"+(ApiConfig.getTokenTimes+1)+"==================");
        LOG.debug("开始初始化access_token........");
        //记住原本的时间，用于出错回滚
        final long oldTime = this.weixinTokenStartTime;
        this.weixinTokenStartTime = refreshTime;
        ApiConfig.wxTokenStartTime = refreshTime;
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + this.appid + "&secret=" + this.secret;
        NetWorkCenter.get(url, null, new NetWorkCenter.ResponseCallback() {
            @Override
            public void onResponse(int resultCode, String resultJson) {
            	System.out.println("========================================>>>>>>>>resultJson:" + resultJson);
                if (HttpStatus.SC_OK == resultCode) {
                    GetTokenResponse response = JSONUtil.toBean(resultJson, GetTokenResponse.class);
                    LOG.debug("获取access_token:{}", response.getAccessToken());
                    if (null == response.getAccessToken()) {
                        //刷新时间回滚
                        weixinTokenStartTime = oldTime;
                        wxTokenStartTime = oldTime;
                        throw new WeixinException("微信公众号token获取出错，错误信息:" + response.getErrcode() + "," + response.getErrmsg());
                    }
                    ApiConfig.getTokenTimes++;
                    ApiConfig.getTokenTimes2++;
                    accessToken = response.getAccessToken();
                    //设置通知点
                    setChanged();
                    notifyObservers(new ConfigChangeNotice(appid, ChangeType.ACCESS_TOKEN, accessToken));
                }
            }
        });
    }

    /**
     * 初始化微信JS-SDK，获取JS-SDK token
     *
     * @param refreshTime 刷新时间
     */
    private void initJSToken(final long refreshTime) {
        LOG.debug("初始化 jsapi_ticket........");
        //记住原本的时间，用于出错回滚
        final long oldTime = ApiConfig.jsTokenStartTime;
        ApiConfig.jsTokenStartTime = refreshTime;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
        System.out.println(url);
        NetWorkCenter.get(url, null, new NetWorkCenter.ResponseCallback() {
            @Override
            public void onResponse(int resultCode, String resultJson) {
                if (HttpStatus.SC_OK == resultCode) {
                    GetJsApiTicketResponse response = JSONUtil.toBean(resultJson, GetJsApiTicketResponse.class);
                    LOG.debug("获取jsapi_ticket:{}", response.getTicket());
                    if (StrUtil.isBlank(response.getTicket())) {
                        //刷新时间回滚
                    	ApiConfig.jsTokenStartTime = oldTime;
                        throw new WeixinException("微信公众号jsToken获取出错，错误信息:" + response.getErrcode() + "," + response.getErrmsg());
                    }
                    jsApiTicket = response.getTicket();
                    //设置通知点
                    setChanged();
                    notifyObservers(new ConfigChangeNotice(appid, ChangeType.JS_TOKEN, jsApiTicket));
                }
            }
        });
    }
    
    
    /**
     * 校验AccessToken是否合法有效
     * @return
     */
    public  boolean validAccessToken() {
        long now = System.currentTimeMillis();
        long time = now - this.weixinTokenStartTime;
         //官方给出的超时时间是7200秒，这里用7100秒来做，防止出现已经过期的情况
        if (time > 7000000 && tokenRefreshing.compareAndSet(false, true)) {
            return false;
        }else{
        	return true;
        }
    }
    
    public static void main(String[] args) {
    	 String appid = "wx1d6c9a24fac977e7"; //  "wx9cd4da4ce736b685";// 
         String secret = "d13948eccb889bd083e9b867e8b4c1df"; //  "83f7a067445874fd38ecaed01741e5b1"; // 
         for(int i = 0; i<3; i++){
        	 ApiConfig config = new ApiConfig(appid, secret);
         }
	}
}
