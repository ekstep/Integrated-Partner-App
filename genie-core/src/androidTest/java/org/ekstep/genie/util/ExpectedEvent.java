package org.ekstep.genie.util;

/**
 * Created by swayangjit_gwl on 9/21/2016.
 */

public class ExpectedEvent {
    private final String stageId;
    private final String subtype;
    private final String type;
    private final String eid;
    private final String query;
    private final String id;
    private final String handle;
    private final boolean isValueAvailable;
    private final boolean isContentAvailable;
    private final String gid;
    private final String tmschm;
    private final long length;
    private final boolean isGroupUser;
    private final boolean isCdataAvailable;
    private final String cDataType;
    private final String cDataId;
    private final String importDataType;
    private final String importDirection;
    private final String action;
    private final String utmsource;
    private final String utmmedium;
    private final String utmterm;
    private final String utmcontent;
    private final String utmcampaign;
    private final int tbLocalContentCount;
    private final int tbTotalContentCount;
    private final int tbOffset;
    private final String feedbackId;
    private final String feedbackStageId;
    private final String feedbackContextType;
    private final String feedbackComments;
    private final float feedbackRating;
    private final String feedbackType;

    public ExpectedEvent(ExpectedEventBuilder builder) {
        this.stageId = builder.stageId;
        this.subtype = builder.subtype;
        this.type = builder.type;
        this.eid = builder.eid;
        this.query = builder.query;
        this.id = builder.id;
        this.handle = builder.handle;
        this.isValueAvailable = builder.isValuesAvailable;
        this.isContentAvailable = builder.isContentAvailable;
        this.gid = builder.gid;
        this.tmschm = builder.tmschm;
        this.length = builder.length;
        this.isGroupUser = builder.isGroupUser;
        this.isCdataAvailable = builder.isCdataAvailable;
        this.cDataType = builder.cDataType;
        this.cDataId = builder.cDataId;
        this.importDataType = builder.importDataType;
        this.importDirection = builder.importDirection;
        this.action = builder.action;
        this.utmsource = builder.utmsource;
        this.utmmedium = builder.utmmedium;
        this.utmterm = builder.utmterm;
        this.utmcontent = builder.utmcontent;
        this.utmcampaign = builder.utmcampaign;
        this.tbLocalContentCount = builder.tbLocalContentCount;
        this.tbTotalContentCount = builder.tbTotalContentCount;
        this.tbOffset = builder.tbOffset;
        this.feedbackId = builder.feedbackId;
        this.feedbackStageId = builder.feedbackStageId;
        this.feedbackContextType = builder.feedbackContextType;
        this.feedbackComments = builder.feedbackComments;
        this.feedbackRating = builder.feedbackRating;
        this.feedbackType = builder.feedbackType;
    }

    public int getTbLocalContentCount() {
        return tbLocalContentCount;
    }

    public int getTbTotalContentCount() {
        return tbTotalContentCount;
    }

    public int getTbOffset() {
        return tbOffset;
    }

    public String getHandle() {
        return handle;
    }

    public String getStageId() {
        return stageId;
    }

    public String getSubType() {
        return subtype;
    }

    public String getType() {
        return type;
    }

    public String getEid() {
        return eid;
    }

    public String getQuery() {
        return query;
    }

    public String getId() {
        return id;
    }

    public String getGid() {
        return gid;
    }

    public String getTmschm() {
        return tmschm;
    }

    public long getLength() {
        return length;
    }

    public boolean isValuesAvailable() {
        return isValueAvailable;
    }

    public boolean isContentAvailable() {
        return isContentAvailable;
    }

    public boolean isGroupUser() {
        return isGroupUser;
    }

    public String getCdataType() {
        return cDataType;
    }

    public String getCdataId() {
        return cDataId;
    }

    public String getImportDirection() {
        return importDirection;
    }

    public String getImportDataType() {
        return importDataType;
    }


    public String getAction() {
        return action;
    }

    public String getUtmsource() {
        return utmsource;
    }

    public String getUtmmedium() {
        return utmmedium;
    }

    public String getUtmterm() {
        return utmterm;
    }

    public String getUtmcontent() {
        return utmcontent;
    }

    public String getUtmcampaign() {
        return utmcampaign;
    }

    public String getFedbackId() {
        return feedbackId;
    }

    public String getFeedbackStageId() {
        return feedbackStageId;
    }

    public String getFeedbackContextType() {
        return feedbackContextType;
    }

    public String getFeedbackComments() {
        return feedbackComments;
    }

    public float getFeedbackRating() {
        return feedbackRating;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public static class ExpectedEventBuilder {
        private String stageId;
        private String subtype;
        private String type;
        private String eid;
        private String query;
        private String id;
        private String handle;
        private boolean isValuesAvailable;
        private boolean isContentAvailable;
        private String gid;
        private String tmschm;
        private long length;
        private boolean isGroupUser;
        private boolean isCdataAvailable;
        private String cDataType;
        private String cDataId;
        private String importDataType;
        private String importDirection;
        private String action;
        private String utmsource;
        private String utmmedium;
        private String utmterm;
        private String utmcontent;
        private String utmcampaign;
        private int tbLocalContentCount;
        private int tbTotalContentCount;
        private int tbOffset;
        private String feedbackId;
        private String feedbackStageId;
        private String feedbackContextType;
        private String feedbackComments;
        private float feedbackRating;
        private String feedbackType;

        public ExpectedEventBuilder setTbLocalContentCount(int tbLocalContentCount) {
            this.tbLocalContentCount = tbLocalContentCount;
            return this;
        }

        public ExpectedEventBuilder setTbTotalContentCount(int tbTotalContentCount) {
            this.tbTotalContentCount = tbTotalContentCount;
            return this;
        }

        public ExpectedEventBuilder setTbOffset(int tbOffset) {
            this.tbOffset = tbOffset;
            return this;
        }

        public ExpectedEventBuilder setStageId(String stageId) {
            this.stageId = stageId;
            return this;
        }

        public ExpectedEventBuilder setSubtype(String subtype) {
            this.subtype = subtype;
            return this;
        }

        public ExpectedEventBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public ExpectedEventBuilder setEid(String eid) {
            this.eid = eid;
            return this;
        }

        public ExpectedEventBuilder setHandle(String handle) {
            this.handle = handle;
            return this;
        }

        public ExpectedEventBuilder setIsGroupUser(boolean isGroupUser) {
            this.isGroupUser = isGroupUser;
            return this;
        }

        public ExpectedEventBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public ExpectedEventBuilder setValueAvailable(boolean isValuesAvailable) {
            this.isValuesAvailable = isValuesAvailable;
            return this;
        }

        public ExpectedEventBuilder setContentAvailable(boolean isContentAvailable) {
            this.isContentAvailable = isContentAvailable;
            return this;
        }

        public ExpectedEventBuilder setGid(String gid) {
            this.gid = gid;
            return this;
        }

        public ExpectedEventBuilder setTmschm(String tmschm) {
            this.tmschm = tmschm;
            return this;
        }

        public ExpectedEventBuilder setLength(long length) {
            this.length = length;
            return this;
        }

        public ExpectedEventBuilder setCData(String cDataType) {
            this.cDataType = cDataType;
            return this;
        }

        public ExpectedEventBuilder setCDataId(String cDataId) {
            this.cDataId = cDataId;
            return this;
        }

        public ExpectedEventBuilder setCDataType(String cDataType) {
            this.cDataType = cDataType;
            return this;
        }

        public ExpectedEventBuilder setDataTypenDirection(String dataType, String direction) {
            this.importDataType = dataType;
            this.importDirection = direction;
            return this;
        }

        public ExpectedEventBuilder setReferer(String action, String utmsource, String utmmedium, String utmterm, String utmcontent, String utmcampaign) {
            this.action = action;
            this.utmsource = utmsource;
            this.utmmedium = utmmedium;
            this.utmterm = utmterm;
            this.utmcontent = utmcontent;
            this.utmcampaign = utmcampaign;
            return this;
        }

        public ExpectedEventBuilder setFeedbackContext(String id, String stageId, String contextType, String comments, float rating, String type) {
            this.feedbackId = id;
            this.feedbackStageId = stageId;
            this.feedbackContextType = contextType;
            this.feedbackComments = comments;
            this.feedbackRating = rating;
            this.feedbackType = type;
            return this;
        }

        public ExpectedEvent build() {
            return new ExpectedEvent(this);
        }

    }
}
