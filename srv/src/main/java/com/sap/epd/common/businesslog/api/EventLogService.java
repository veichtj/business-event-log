package com.sap.epd.common.businesslog.api;

import cds.gen.businesseventlogservice.BusinessEventLog;
import cds.gen.businesseventlogservice.BusinessEventLog_;
import com.sap.cds.CdsData;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.persistence.PersistenceService;
import com.sap.cds.services.request.UserInfo;
import com.sap.epd.common.businesslog.converter.MessageConverter;
import com.sap.epd.common.businesslog.converter.MessageConverterFactory;
import com.sap.epd.common.businesslog.generator.BusinessEventGenerator;
import com.sap.epd.common.businesslog.generator.EventGeneratorFactory;
import com.sap.epd.common.businesslog.model.BusinessEvent;
import com.sap.epd.common.businesslog.model.ChangeDetectionDto;
import com.sap.epd.common.businesslog.publisher.BusinessEventPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EventLogService {

    private final PersistenceService persistenceService;
    private final MessageConverterFactory messageConverterFactory;
    private final BusinessEventPublisher businessEventPublisher;
    private final EventGeneratorFactory eventFactory;
    private final UserInfo userInfo;
    private final CdsDiffProvider cdsDiffProvider;

    public void afterDataModification(CdsData oldEntity, CdsData newEntity){
        ChangeDetectionDto detectChanges = cdsDiffProvider.detectChanges(oldEntity, newEntity);
        if(detectChanges!= null){
            List<BusinessEventGenerator> businessEventGenerators = eventFactory.getEventGeneratorForChange(detectChanges);
            List<BusinessEvent> businessEvents = businessEventGenerators.stream().map(generator -> generator.createBusinessEvent(userInfo)).collect(Collectors.toList());
            businessEventPublisher.publishEvent(businessEvents);
            this.saveBusinessEvents(businessEvents);
        }

    }

    public List<BusinessEventLog> getBusinessEventLog(String entityId, Locale locale){
        CqnSelect select = Select.from(BusinessEventLog_.class).where(b -> b.entityID().eq(entityId));
        List<BusinessEventLog> businessEventLogs = persistenceService.run(select).listOf(BusinessEventLog.class);
        businessEventLogs.forEach(businessEventLog -> {
            MessageConverter converter = messageConverterFactory.getConverterForEvent(businessEventLog);
            businessEventLog.setTitle(converter.getTitle(businessEventLog));
            businessEventLog.setMessage(converter.generateMessage(businessEventLog, locale));
            businessEventLog.setIcon(converter.getIcon(businessEventLog));
        });
        return businessEventLogs;
    }

    private void saveBusinessEvents(List<BusinessEvent> events){
        events.forEach( event -> {
            if(Boolean.TRUE.equals(event.getPersistEvent())){
                Insert insert = Insert.into(BusinessEventLog_.class).entry(event.getBusinessEventLog());
                persistenceService.run(insert);
            }
        });
    }

}
