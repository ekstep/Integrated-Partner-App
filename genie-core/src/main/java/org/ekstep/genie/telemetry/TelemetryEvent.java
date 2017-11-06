package org.ekstep.genie.telemetry;

/**
 * Created on 5/16/2016.
 *
 * @author anil
 */
public interface TelemetryEvent {

    String GE_GENIE_START = "GE_START";
    String GE_GENIE_UPDATE = "GE_UPDATE";
    String GE_GENIE_END = "GE_END";


    String GE_GENIE_RESUME = "GE_RESUME";
    String GE_INTERACT = "GE_INTERACT";
    String GE_INTERRUPT = "GE_INTERRUPT";
    String GE_FEEDBACK = "GE_FEEDBACK";
    String GE_TRANSFER = "GE_TRANSFER";

}
