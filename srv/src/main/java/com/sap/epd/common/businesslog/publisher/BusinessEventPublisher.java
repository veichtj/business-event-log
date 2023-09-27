package com.sap.epd.common.businesslog.publisher;


import com.sap.cds.services.cds.CqnService;
import com.sap.epd.common.businesslog.model.BusinessEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BusinessEventPublisher {
    @Setter
    private CqnService cqnService;
    public void publishEvent(List<BusinessEvent> businessEvents){
        if(cqnService != null){
            businessEvents.forEach( event-> cqnService.emit(event.getCapEvent()));
        }
    }
}
