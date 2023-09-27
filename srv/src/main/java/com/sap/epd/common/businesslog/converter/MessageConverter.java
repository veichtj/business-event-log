package com.sap.epd.common.businesslog.converter;


import cds.gen.businesseventlogservice.BusinessEventLog;

import java.util.Locale;

public interface MessageConverter {

    String generateMessage(BusinessEventLog businessEventLog, Locale locale);

    String getIcon(BusinessEventLog businessEventLog);

    String getTitle(BusinessEventLog businessEventLog);
}
