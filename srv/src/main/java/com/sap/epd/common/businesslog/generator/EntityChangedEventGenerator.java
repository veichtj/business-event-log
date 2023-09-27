package com.sap.epd.common.businesslog.generator;

import cds.gen.businesseventlogservice.BusinessEventLog;
import cds.gen.businesseventlogservice.EntityChangedEvent;
import cds.gen.businesseventlogservice.EntityChangedEventContext;
import com.sap.cds.CdsData;
import com.sap.cds.services.auditlog.ChangedAttribute;
import com.sap.cds.services.request.UserInfo;
import com.sap.epd.common.businesslog.model.BusinessEvent;

import java.time.Instant;

public class EntityChangedEventGenerator implements BusinessEventGenerator{
    CdsData changedEntity;
    ChangedAttribute changedAttribute;

    @Override
    public BusinessEvent createBusinessEvent(UserInfo userInfo) {
        EntityChangedEventContext ctx = createCapEvent(userInfo);
        BusinessEventLog businessEventLog = createBusinessEventLog(ctx);
        BusinessEvent businessEvent = new BusinessEvent();
        businessEvent.setCapEvent(ctx);
        businessEvent.setBusinessEventLog(businessEventLog);
        businessEvent.setPersistEvent(getPersistBusinessEvent());
        return businessEvent;
    }

    private BusinessEventLog createBusinessEventLog(EntityChangedEventContext ctx) {
        BusinessEventLog businessEvent = BusinessEventLog.create();
        businessEvent.setEntityID(ctx.getData().getEntityId());
        businessEvent.setEntityDisplayId(ctx.getData().getEntityDisplayId());
        businessEvent.setEntityName(ctx.getData().getEntityName());
        businessEvent.setCreatedBy(ctx.getData().getChangedBy());
        businessEvent.setCreatedOn(ctx.getData().getChangedAt());
        businessEvent.setChangeType(EntityChangedEventContext.CDS_NAME);
        businessEvent.setContent(ctx.getData().getChangePayload());
        return businessEvent;
    }


    private EntityChangedEventContext createCapEvent(UserInfo userInfo) {
        EntityChangedEvent event = EntityChangedEvent.create();
        String id = changedEntity.get("id") != null ? changedEntity.get("id").toString() :
                changedEntity.get("ID").toString();
        event.setEntityId(id);
        event.setChangedBy(userInfo.getName());
        event.setChangedAt(Instant.now());
        event.setChangePayload(changedAttribute.toJson());
        EntityChangedEventContext ctx = EntityChangedEventContext.create();
        ctx.setData(event);
        return ctx;
    }

    @Override
    public void setChangedData(CdsData changedEntity, ChangedAttribute changedAttribute) {
        this.changedEntity = changedEntity;
        this.changedAttribute = changedAttribute;

    }

    @Override
    public Boolean getPersistBusinessEvent() {
        return true;
    }
}
