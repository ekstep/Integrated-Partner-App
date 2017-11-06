package org.ekstep.genie.model;

/**
 * Created by swayangjit on 1/10/17.
 */

public class SelectedContent{

    private String identifier;
    private long size;
    private boolean isChild;

    public int getRefCount() {
        return refCount;
    }

    public void setRefCount(int refCount) {
        this.refCount = refCount;
    }

    private int refCount;

    public SelectedContent(String identifier) {
        this.identifier = identifier;
    }

    public SelectedContent(String identifier, long size, boolean isChild) {
        this.identifier = identifier;
        this.size = size;
        this.isChild = isChild;
    }

    public String getIdentifier() {
        return identifier;
    }

    public long getSize() {
        return size;
    }

    public boolean isChild() {
        return isChild;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectedContent that = (SelectedContent) o;
        return identifier != null ? identifier.equals(that.identifier) : that.identifier == null;
    }

    @Override
    public int hashCode() {
        return identifier != null ? identifier.hashCode() : 0;
    }

}
