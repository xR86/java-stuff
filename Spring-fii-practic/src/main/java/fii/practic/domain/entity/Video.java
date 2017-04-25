package fii.practic.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Video data that will be persisted.
 */
@Document(indexName = "spider", type = "video")
public class Video {
    private String name;
    private VideoType videoType;
    private String videoSource;

    @Id
    private String url;
    private String embeded;

    //private String pictures;
    private String picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VideoType getVideoType() {
        return videoType;
    }

    public void setVideoType(VideoType videoType) {
        this.videoType = videoType;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setVideoSource(String videoSource) {
        this.videoSource = videoSource;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmbeded() {
        return embeded;
    }

    public void setEmbeded(String embeded) {
        this.embeded = embeded;
    }

//    public String getPictures() {
//        return pictures;
//    }
//    public void setPictures(String pictures) {
//        this.pictures = pictures;
//    }
//
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Video{" +
                "name='" + name + '\'' +
                ", videoType=" + videoType +
                ", videoSource=" + videoSource +
                ", url='" + url + '\'' +
                ", picture_url='" + picture + '\'' +
                '}';
    }
}