package cn.max.pixiv.entity;

import java.io.InputStream;

/**
 * @author MaxStar
 * @date 2020/4/12
 */
public class Image {

    private long id;
    private long name;
    private String url;
    private String originUrl;
    private int currNum = 0;
    private long artistId;
    private String artistName;
    private String artistUrl;
    private InputStream simpleImage;

    public Image() {
    }

    public Image(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public int getCurrNum() {
        return currNum;
    }

    public void setCurrNum(int currNum) {
        this.currNum = currNum;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistUrl() {
        return artistUrl;
    }

    public void setArtistUrl(String artistUrl) {
        this.artistUrl = artistUrl;
    }

    public InputStream getSimpleImage() {
        return simpleImage;
    }

    public void setSimpleImage(InputStream simpleImage) {
        this.simpleImage = simpleImage;
    }
}
