package org.ekstep.genie.model;

import org.ekstep.genie.telemetry.TelemetryEvent;
import org.ekstep.genie.util.CommonUtil;
import org.ekstep.genie.util.ExpectedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by swayangjit_gwl on 9/21/2016.
 */

public class ExpectedEventList {

    private Map<String, List<ExpectedEvent>> expectedEventMap;

    public ExpectedEventList(ExpectedEventListBuilder builder) {
        expectedEventMap = builder.expectedEventMap;
    }

    public Map<String, List<ExpectedEvent>> getExpectedEventMap() {
        return expectedEventMap;
    }

    public static class ExpectedEventListBuilder {
        private Map<String, List<ExpectedEvent>> expectedEventMap = new HashMap<>();
        private List<ExpectedEvent> expectedEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeInteractEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedCreateUserEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedCreateProfileEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeServiceApiEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeSessionEndEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeSessionStartEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeUpdateEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeFeedbackEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeLaunchGameEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeGameEndEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGenieStartEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeInterruptEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeTransferEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeResumeEventList = new ArrayList<ExpectedEvent>();
        private List<ExpectedEvent> expectedGeGenieUpdateEventList = new ArrayList<ExpectedEvent>();

        public ExpectedEventListBuilder addGEInteractEvent(String stageId, String subType, String type, String id, boolean isValueAvailable, int tbLocalContentCount, int tbTotalContentCount, int tbOffset) {
            ExpectedEvent addChildInitiateExpectedEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setStageId(stageId).
                    setSubtype(subType).
                    setType(type).
                    setEid(TelemetryEvent.GE_INTERACT).
                    setId(id).
                    setValueAvailable(isValueAvailable).
                    setTbTotalContentCount(tbTotalContentCount).
                    setTbLocalContentCount(tbLocalContentCount).
                    setTbOffset(tbOffset).build();
            expectedGeInteractEventList.add(addChildInitiateExpectedEvent);
            expectedEventMap.put(TelemetryEvent.GE_INTERACT, expectedGeInteractEventList);
            return this;
        }

        public ExpectedEventListBuilder addGEInteractEvent(String stageId, String subType, String type, String id, boolean isValueAvailable) {
            ExpectedEvent addChildInitiateExpectedEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setStageId(stageId).
                    setSubtype(subType).
                    setType(type).
                    setEid(TelemetryEvent.GE_INTERACT).
                    setId(id).
                    setValueAvailable(isValueAvailable).build();
            expectedGeInteractEventList.add(addChildInitiateExpectedEvent);
            expectedEventMap.put(TelemetryEvent.GE_INTERACT, expectedGeInteractEventList);
            return this;
        }

        public ExpectedEventListBuilder addGEInteractEvent(String stageId, String subType, String type, String id, boolean isValueAvailable, boolean isContentAvailable) {
            ExpectedEvent addChildInitiateExpectedEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setStageId(stageId).
                    setSubtype(subType).
                    setType(type).
                    setEid(TelemetryEvent.GE_INTERACT).
                    setId(id).
                    setContentAvailable(isContentAvailable).
                    setValueAvailable(isValueAvailable).build();
            expectedGeInteractEventList.add(addChildInitiateExpectedEvent);
            expectedEventMap.put(TelemetryEvent.GE_INTERACT, expectedGeInteractEventList);
            return this;
        }
        public ExpectedEventListBuilder addGEInteractEvent(String stageId, String subType, String type, String id, boolean isValueAvailable, String cDataType) {
            ExpectedEvent addChildInitiateExpectedEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setStageId(stageId).
                    setSubtype(subType).
                    setType(type).
                    setEid(TelemetryEvent.GE_INTERACT).
                    setId(id).
                    setCData(cDataType).
                    setCDataType(cDataType).
                    setValueAvailable(isValueAvailable).build();
            expectedGeInteractEventList.add(addChildInitiateExpectedEvent);
            expectedEventMap.put(TelemetryEvent.GE_INTERACT, expectedGeInteractEventList);
            return this;
        }

        public ExpectedEventListBuilder addCreateUserEvent() {
            ExpectedEvent addCreateUserExpectedEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setEid(CommonUtil.Constants.GE_CREATE_USER).build();

            expectedCreateUserEventList.add(addCreateUserExpectedEvent);
            expectedEventMap.put(CommonUtil.Constants.GE_CREATE_USER, expectedCreateUserEventList);
            return this;
        }

        public ExpectedEventListBuilder addUpdateUserEvent(String handle) {
            ExpectedEvent addCreateUserExpectedEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setEid(CommonUtil.Constants.GE_UPDATE_PROFILE).
                    setHandle(handle).build();

            expectedGeUpdateEventList.add(addCreateUserExpectedEvent);
            expectedEventMap.put(CommonUtil.Constants.GE_UPDATE_PROFILE, expectedGeUpdateEventList);
            return this;
        }

        public ExpectedEventListBuilder addCreateProfileEvent(String handle, boolean isGroupUser) {
            ExpectedEvent addCreateUserExpectedEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setEid(CommonUtil.Constants.GE_CREATE_PROFILE).
                    setHandle(handle).setIsGroupUser(isGroupUser).build();

            expectedCreateProfileEventList.add(addCreateUserExpectedEvent);
            expectedEventMap.put(CommonUtil.Constants.GE_CREATE_PROFILE, expectedCreateProfileEventList);
            return this;
        }

        public ExpectedEventListBuilder addGEServiceApiCall(int count) {
            for (int i = 0; i < count; i++) {
                ExpectedEvent addCreateUserExpectedEvent = new ExpectedEvent.ExpectedEventBuilder().
                        setEid(CommonUtil.Constants.GE_SERVICE_API_CALL).build();

                expectedGeServiceApiEventList.add(addCreateUserExpectedEvent);
            }
            expectedEventMap.put(CommonUtil.Constants.GE_SERVICE_API_CALL, expectedGeServiceApiEventList);
            return this;
        }

        public ExpectedEventListBuilder addGEInterrupt(int count) {
            for (int i = 0; i < count; i++) {
                ExpectedEvent addGEInterrupt = new ExpectedEvent.ExpectedEventBuilder().
                        setEid(TelemetryEvent.GE_INTERRUPT).build();

                expectedGeInterruptEventList.add(addGEInterrupt);
            }
            expectedEventMap.put(TelemetryEvent.GE_INTERRUPT, expectedGeInterruptEventList);
            return this;
        }

        public ExpectedEventListBuilder addGEResume(int count) {
            for (int i = 0; i < count; i++) {
                ExpectedEvent addGEInterrupt = new ExpectedEvent.ExpectedEventBuilder().
                        setEid(TelemetryEvent.GE_GENIE_RESUME).build();

                expectedGeResumeEventList.add(addGEInterrupt);
            }
            expectedEventMap.put(TelemetryEvent.GE_GENIE_RESUME, expectedGeResumeEventList);
            return this;
        }

        public ExpectedEventListBuilder addGETransfer(int count, String dataType, String direction) {
            for (int i = 0; i < count; i++) {
                ExpectedEvent addGEInterrupt = new ExpectedEvent.ExpectedEventBuilder().
                        setEid(TelemetryEvent.GE_TRANSFER).setDataTypenDirection(dataType, direction).build();

                expectedGeTransferEventList.add(addGEInterrupt);
            }
            expectedEventMap.put(TelemetryEvent.GE_TRANSFER, expectedGeTransferEventList);
            return this;
        }


        public ExpectedEventListBuilder addGESessionEndEvent() {
            ExpectedEvent addCreateUserExpectedEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setEid(CommonUtil.Constants.GE_SESSION_END).build();

            expectedGeSessionEndEventList.add(addCreateUserExpectedEvent);
            expectedEventMap.put(CommonUtil.Constants.GE_SESSION_END, expectedGeSessionEndEventList);
            return this;
        }

        public ExpectedEventListBuilder addGEFeedbackEvent(String id, String stageId, String contexttype, String comments, float rating, String type) {
            ExpectedEvent addGEFeedbackEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setEid(TelemetryEvent.GE_FEEDBACK).setFeedbackContext(id, stageId, contexttype, comments, rating, type).build();

            expectedGeFeedbackEventList.add(addGEFeedbackEvent);
            expectedEventMap.put(TelemetryEvent.GE_FEEDBACK, expectedGeFeedbackEventList);
            return this;
        }

        public ExpectedEventListBuilder addGELaunchGameEvent(String gid, String tmschm, String cDataId, String cDataType) {
            ExpectedEvent addGELaunchGameEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setGid(gid).
                    setCData(cDataId).
                    setCDataId(cDataId).
                    setCDataType(cDataType).
                    setTmschm(tmschm).build();
            expectedGeLaunchGameEventList.add(addGELaunchGameEvent);
            expectedEventMap.put(TelemetryEvent.GE_LAUNCH_GAME, expectedGeLaunchGameEventList);
            return this;
        }

        public ExpectedEventListBuilder addGELaunchGameEvent(String gid, String tmschm) {
            ExpectedEvent addGELaunchGameEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setGid(gid).
                    setTmschm(tmschm).build();
            expectedGeLaunchGameEventList.add(addGELaunchGameEvent);
            expectedEventMap.put(TelemetryEvent.GE_LAUNCH_GAME, expectedGeLaunchGameEventList);
            return this;
        }

        public ExpectedEventListBuilder addGEGameEndEvent(String gid) {
            ExpectedEvent addGEGameEndEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setGid(gid).build();
            expectedGeGameEndEventList.add(addGEGameEndEvent);
            expectedEventMap.put(TelemetryEvent.GE_GAME_END, expectedGeGameEndEventList);
            return this;
        }

        public ExpectedEventListBuilder addGEStartEvent(int noOfEvents) {
            ExpectedEvent addGEStartEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setEid(TelemetryEvent.GE_GENIE_START).build();

            if (noOfEvents > 0) {
                expectedGenieStartEventList.add(addGEStartEvent);
                expectedEventMap.put(TelemetryEvent.GE_GENIE_START, expectedGenieStartEventList);
            }
            return this;
        }

        public ExpectedEventListBuilder addGESessionStartEvent() {
            ExpectedEvent addCreateUserExpectedEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setEid(CommonUtil.Constants.GE_SESSION_START).build();

            expectedGeSessionStartEventList.add(addCreateUserExpectedEvent);
            expectedEventMap.put(CommonUtil.Constants.GE_SESSION_START, expectedGeSessionStartEventList);
            return this;
        }

        public ExpectedEventListBuilder addGEGenieUpdateEvent(String action, String utmsource, String utmmedium, String utmterm, String utmcontent, String utmcampaign) {
            ExpectedEvent addGELaunchGameEvent = new ExpectedEvent.ExpectedEventBuilder().
                    setReferer(action, utmsource, utmmedium, utmterm, utmcontent, utmcampaign)
                    .build();
            expectedGeGenieUpdateEventList.add(addGELaunchGameEvent);
            expectedEventMap.put(TelemetryEvent.GE_GENIE_UPDATE, expectedGeGenieUpdateEventList);
            return this;
        }

        public ExpectedEventList build() {
            return new ExpectedEventList(this);
        }

    }
}
