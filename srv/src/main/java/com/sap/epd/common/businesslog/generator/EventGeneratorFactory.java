package com.sap.epd.common.businesslog.generator;

import com.sap.epd.common.businesslog.config.BusinessEventRegistry;
import com.sap.epd.common.businesslog.model.ChangeDetectionDto;
import com.sap.epd.common.businesslog.model.ChangeKeyDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventGeneratorFactory {

    private final BusinessEventRegistry registry;

    public EventGeneratorFactory(BusinessEventRegistry registry) {
        this.registry = registry;
    }

    public List<BusinessEventGenerator> getEventGeneratorForChange(ChangeDetectionDto change){
        List<BusinessEventGenerator> businessEventGenerators = new ArrayList<>();

        change.getChangedAttributes().forEach( chg -> {
            ChangeKeyDto key = ChangeKeyDto.builder().entityName(change.getEntityName()).attributeName(chg.getName()).build();
            if (registry.getEventGeneratorRegistry().containsKey(key)) {
                BusinessEventGenerator businessEventGenerator = registry.getEventGeneratorRegistry().get(key);
                businessEventGenerator.setChangedData(change.getNewEntity(), chg);
                businessEventGenerators.add(businessEventGenerator);
            }
        });
        return businessEventGenerators;
    }

    public void registerEventGenerator(ChangeKeyDto changeKey, BusinessEventGenerator businessEventGenerator){
        registry.getEventGeneratorRegistry().put(changeKey, businessEventGenerator);
    }
}
