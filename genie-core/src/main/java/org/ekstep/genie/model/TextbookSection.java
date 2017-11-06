package org.ekstep.genie.model;

import org.ekstep.genieservices.commons.bean.Content;

import java.util.List;

/**
 * This represents a part of the textbook, which contains Chapter number and the lessons inside that particular unit
 * <p>
 * Created by shriharsh on 28/2/17.
 */

public class TextbookSection {

    private String sectionName;
    private Content currentSectionContentDetails;
    private List<Content> sectionContents;
    private List<String> sectionContentIdentifiers;

    public List<String> getSectionContentIdentifiers() {
        return sectionContentIdentifiers;
    }

    public void setSectionContentIdentifiers(List<String> sectionContentIdentifiers) {
        this.sectionContentIdentifiers = sectionContentIdentifiers;
    }

    public Content getCurrentSectionContentDetails() {
        return currentSectionContentDetails;
    }

    public void setCurrentSectionContentDetails(Content currentSectionGameDetails) {
        this.currentSectionContentDetails = currentSectionGameDetails;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<Content> getSectionContents() {
        return sectionContents;
    }

    public void setSectionContents(List<Content> sectionContents) {
        this.sectionContents = sectionContents;
    }

}
