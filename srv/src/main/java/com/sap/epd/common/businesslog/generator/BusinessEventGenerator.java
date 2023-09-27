package com.sap.epd.common.businesslog.generator;

import com.sap.cds.CdsData;
import com.sap.cds.services.auditlog.ChangedAttribute;
import com.sap.cds.services.request.UserInfo;
import com.sap.epd.common.businesslog.model.BusinessEvent;

public interface BusinessEventGenerator {
    BusinessEvent createBusinessEvent(UserInfo userInfo);
    void setChangedData(CdsData changedEntity, ChangedAttribute changedAttribute);

    Boolean getPersistBusinessEvent();
}
