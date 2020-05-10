package cn.max.pixiv.entity.properties;

import cn.max.pixiv.util.http.HttpClientConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.File;

/**
 * 配置实体
 *
 * @author MaxStar
 * @date 2020/5/7
 */
public class Properties {

    private String downloadPath;
    private Proxy proxy;

    public Properties() {
        this.downloadPath = System.getProperty("user.dir") + File.separator + "download" + File.separator;
        this.proxy = new Proxy("", -1);
    }

    public Properties(String downloadPath, Proxy proxy) {
        this.downloadPath = downloadPath;
        this.proxy = proxy;
    }

    public Properties(String downloadPath, String host, Integer port) {
        this.downloadPath = downloadPath;
        this.proxy = new Proxy(host, port);
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
        HttpClientConfig.rebuildHttpClient();
    }

    public void setProxy(String host, Integer port) {
        if (this.proxy == null) {
            this.proxy = new Proxy(host, port);
        } else {
            this.proxy.setHost(host);
            this.proxy.setPort(port);
        }
        HttpClientConfig.rebuildHttpClient();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String toJsonString() {
        return JSON.toJSONString(this, SerializerFeature.PrettyFormat);
    }
}
