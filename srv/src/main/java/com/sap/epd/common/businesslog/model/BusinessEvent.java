package com.sap.epd.common.businesslog.model;

import cds.gen.businesseventlogservice.BusinessEventLog;
import com.sap.cds.services.EventContext;
import lombok.Data;

@Data
public class BusinessEvent {

    EventContext capEvent;
    BusinessEventLog businessEventLog;
    Boolean persistEvent;
    Boolean publishEvent;
}
