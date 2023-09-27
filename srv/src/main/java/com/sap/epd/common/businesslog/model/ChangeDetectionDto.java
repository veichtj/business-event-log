package com.sap.epd.common.businesslog.model;

import com.sap.cds.CdsData;
import com.sap.cds.services.auditlog.Action;
import com.sap.cds.services.auditlog.ChangedAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class ChangeDetectionDto {

    String entityId;
    String entityName;
    CdsData newEntity;
    Action action;
    Collection<ChangedAttribute> changedAttributes;

}
