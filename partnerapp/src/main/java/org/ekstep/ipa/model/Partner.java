package org.ekstep.ipa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Partner {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("public_key")
    @Expose
    private String publicKey;
    @SerializedName("channel")
    @Expose
    private List<String> channel = null;
    @SerializedName("audience")
    @Expose
    private List<String> audience = null;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("tag")
    @Expose
    private List<Tag> tag = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public List<String> getChannel() {
        return channel;
    }

    public void setChannel(List<String> channel) {
        this.channel = channel;
    }

    public List<String> getAudience() {
        return audience;
    }

    public void setAudience(List<String> audience) {
        this.audience = audience;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Tag> getTag() {
        return tag;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

}
