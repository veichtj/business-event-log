package com.sap.epd.common.businesslog.converter;

import cds.gen.businesseventlogservice.BusinessEventLog;

import java.util.Locale;

public class DefaultMessageConverter implements MessageConverter{
    @Override
    public String generateMessage(BusinessEventLog businessEventLog, Locale locale) {
        return businessEventLog.getContent();
    }

    @Override
    public String getIcon(BusinessEventLog businessEventLog) {
        return "sap-icon://edit";
    }

    @Override
    public String getTitle(BusinessEventLog businessEventLog) {
        return businessEventLog.getChangeType();
    }
}
