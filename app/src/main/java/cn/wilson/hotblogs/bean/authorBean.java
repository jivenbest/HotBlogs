package cn.wilson.hotblogs.bean;

/**
 * Created by KingFlyer on 2015/6/30.
 */
public class authorBean {
    private String name;
    private String uri;
    private String avatar;
    private String blogapp;
    private String postcount;
    private String updated;

    public String getBlogapp() {
        return blogapp;
    }

    public void setBlogapp(String blogapp) {
        this.blogapp = blogapp;
    }

    public String getPostcount() {
        return postcount;
    }

    public void setPostcount(String postcount) {
        this.postcount = postcount;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
