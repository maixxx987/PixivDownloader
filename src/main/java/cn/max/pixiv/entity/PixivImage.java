package cn.max.pixiv.entity;

import java.util.List;

/**
 * @author MaxStar
 * @date 2020/4/12
 */
public class PixivImage {

    private Integer imgId;
    private String imgTitle;
    private String imgUrl;
    private String imgOriginUrl;
    private String imgComment;
    private int currNum = 0;
    private Long artistId;
    private String artistName;
    private String artistUrl;
    private List<String> tagList;

    public PixivImage() {
    }

    public PixivImage(Integer imgId) {
        this.imgId = imgId;
    }

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgOriginUrl() {
        return imgOriginUrl;
    }

    public void setImgOriginUrl(String imgOriginUrl) {
        this.imgOriginUrl = imgOriginUrl;
    }

    public int getCurrNum() {
        return currNum;
    }

    public void setCurrNum(int currNum) {
        this.currNum = currNum;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
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

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getImgComment() {
        return imgComment;
    }

    public void setImgComment(String imgComment) {
        this.imgComment = imgComment;
    }
}
