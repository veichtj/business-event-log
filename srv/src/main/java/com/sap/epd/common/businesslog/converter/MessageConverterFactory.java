package com.sap.epd.common.businesslog.converter;

import cds.gen.businesseventlogservice.BusinessEventLog;
import org.springframework.stereotype.Component;

@Component
public class MessageConverterFactory {

    public MessageConverter getConverterForEvent(BusinessEventLog businessEventLog){
        return new DefaultMessageConverter();
    }
}
