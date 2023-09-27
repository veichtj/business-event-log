package com.sap.epd.common.businesslog.config;

import com.sap.epd.common.businesslog.generator.BusinessEventGenerator;
import com.sap.epd.common.businesslog.model.ChangeKeyDto;

import java.util.Map;

@FunctionalInterface
public interface BusinessEventRegistry {
    Map<ChangeKeyDto, BusinessEventGenerator> getEventGeneratorRegistry();
}
