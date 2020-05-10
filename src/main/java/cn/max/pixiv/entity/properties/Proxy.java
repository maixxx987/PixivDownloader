package cn.max.pixiv.entity.properties;

/**
 * 代理
 *
 * @author MaxStar
 * @date 2020/5/9
 */
public class Proxy {

    private String host;
    private Integer port;

    public Proxy(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
