package com.sap.epd.common.businesslog.api;

import com.sap.cds.CdsData;
import com.sap.epd.common.businesslog.model.ChangeDetectionDto;
@FunctionalInterface
public interface CdsDiffProvider {
    ChangeDetectionDto detectChanges(CdsData oldEntity, CdsData newEntity);
}
