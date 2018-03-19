package org.ekstep.genie.telemetry;

/**
 * Created on 5/16/2016.
 *
 * @author anil
 */
public interface TelemetryEvent {

    String START_EVENT = "START";
    String LOG_EVENT = "LOG";
    String END_EVENT = "END";
    String GE_LAUNCH_GAME = "GE_LAUNCH_GAME";


//    String GE_GENIE_RESUME = "GE_RESUME";
    String INTERACT_EVENT = "INTERACT";
    String INTERRUPT_EVENT = "INTERRUPT";
    String FEEDBACK_EVENT = "FEEDBACK";
    String SHARE = "SHARE";


    String IMPRESSION_EVENT = "IMPRESSION";
    String RESPONSE_EVENT = "RESPONSE";
    String SHARE_EVENT = "SHARE";
    String ERROR_EVENT = "ERROR";
    String EXDATA_EVENT = "EXDATA";

}
