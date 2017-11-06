package org.ekstep.genie.model;

import java.io.Serializable;

/**
 * This represents a part of the DownloadQueue section, which contains the downloading lessons inside that particular unit
 * <p>
 *
 * @author Indraja Machani
 */

public class DownloadQueueItem implements Serializable {
    private String identifier;
    private String name;
    private String size;
    private String parentIdentifier;
    private String parentName;
    private String progress;
    private int childCount;

    public DownloadQueueItem(String identifier, String name, String size, String parentIdentifier, String parentName, int childCount) {
        this.identifier = identifier;
        this.name = name;
        this.size = size;
        this.parentIdentifier = parentIdentifier;
        this.parentName = parentName;
        this.childCount = childCount;
    }

    public DownloadQueueItem(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getParentIdentifier() {
        return parentIdentifier;
    }

    public void setParentIdentifier(String parentIdentifier) {
        this.parentIdentifier = parentIdentifier;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DownloadQueueItem that = (DownloadQueueItem) o;
        return identifier != null ? identifier.equals(that.identifier) : that.identifier == null;
    }

    @Override
    public int hashCode() {
        return identifier != null ? identifier.hashCode() : 0;
    }

}