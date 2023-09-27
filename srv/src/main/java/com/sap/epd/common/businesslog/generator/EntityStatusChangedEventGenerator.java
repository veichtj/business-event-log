package com.sap.epd.common.businesslog.generator;

import cds.gen.businesseventlogservice.BusinessEventLog;
import cds.gen.businesseventlogservice.EntityStatusChangedEvent;
import cds.gen.businesseventlogservice.EntityStatusChangedEventContext;
import cds.gen.businesseventlogservice.StatusChangeMessage;
import com.sap.cds.CdsData;
import com.sap.cds.services.auditlog.ChangedAttribute;
import com.sap.cds.services.request.UserInfo;
import com.sap.epd.common.businesslog.model.BusinessEvent;

import java.time.Instant;

public class EntityStatusChangedEventGenerator implements BusinessEventGenerator{
    CdsData changedEntity;
    ChangedAttribute changedAttribute;

    @Override
    public BusinessEvent createBusinessEvent(UserInfo userInfo) {
        EntityStatusChangedEventContext ctx = createCapEvent(userInfo);
        BusinessEventLog businessEventLog = createBusinessEventLog(ctx);
        BusinessEvent businessEvent = new BusinessEvent();
        businessEvent.setCapEvent(ctx);
        businessEvent.setBusinessEventLog(businessEventLog);
        businessEvent.setPersistEvent(getPersistBusinessEvent());
        return businessEvent;
    }

    private BusinessEventLog createBusinessEventLog(EntityStatusChangedEventContext ctx) {
        BusinessEventLog businessEvent = BusinessEventLog.create();
        businessEvent.setEntityID(ctx.getData().getEntityId());
        businessEvent.setEntityDisplayId(ctx.getData().getEntityDisplayId());
        businessEvent.setEntityName(ctx.getData().getEntityName());
        businessEvent.setCreatedBy(ctx.getData().getChangedBy());
        businessEvent.setCreatedOn(ctx.getData().getChangedAt());
        businessEvent.setChangeType(EntityStatusChangedEventContext.CDS_NAME);
        businessEvent.setContent(ctx.getData().getContent().toJson());
        return businessEvent;
    }

    private EntityStatusChangedEventContext createCapEvent(UserInfo userInfo) {
        EntityStatusChangedEvent event = EntityStatusChangedEvent.create();
        event.setEntityId(changedEntity.get("id").toString());
        event.setChangedBy(userInfo.getName());
        event.setChangedAt(Instant.now());
        StatusChangeMessage content = StatusChangeMessage.create();
        content.setNewStatus(changedAttribute.getNewValue());
        content.setOldStatus(changedAttribute.getOldValue());
        event.setContent(content);
        EntityStatusChangedEventContext ctx = EntityStatusChangedEventContext.create();
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
