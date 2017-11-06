package org.ekstep.genie.model;

import org.ekstep.genieservices.commons.bean.CorrelationData;

import java.util.List;

/**
 * Created on 2/3/17.
 *
 * @author swayangjit
 */
public class CurrentGame {

    private String identifier;
    private String startTime;
    private String mediaType;
    private List<CorrelationData> cData;

    public CurrentGame(String identifier, String startTime, String mediaType) {
        this.identifier = identifier;
        this.startTime = startTime;
        this.mediaType = mediaType;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<CorrelationData> getcData() {
        return cData;
    }

    public void setcData(List<CorrelationData> cData) {
        this.cData = cData;
    }

    public String getMediaType() {
        return mediaType;
    }

}
