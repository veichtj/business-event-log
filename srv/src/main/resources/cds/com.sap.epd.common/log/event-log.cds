using {
    cuid,
    managed,
    User
} from '@sap/cds/common';
service BusinessEventLogService {

    @cds.autoexpose
    entity BusinessEventLog : cuid {
      @Core.Immutable
      entityID            : UUID;

      @Core.Immutable
      entityName          : String(100) enum { Baselines };

      @Core.Immutable
      entityDisplayId     : String(100);

      @Core.Immutable
      rootEntityID        : UUID;

      @Core.Immutable
      changeType          : String(100);

      createdBy           : String(255);

      createdOn           : Timestamp;

      @Core.Immutable
      content             : String;

      virtual icon        : String(255);
      virtual title       : String(255);
      virtual message     : String;

}

    event EntityStatusChangedEvent : businessEvent {
       content : statusChangeMessage;
}

    event EntityChangedEvent : businessEvent {
        changePayload : String;
}

    event EntityCreatedEvent : businessEvent {
      changePayload : String;
}

    aspect businessEvent {
        id         : UUID;
        entityId   : UUID;
        entityDisplayId  : String(255);
        entityName : String(255);
        changedBy  : String(255);
        changedAt  : Timestamp;
}

    type statusChangeMessage {
        oldStatus   : String;
        newStatus   : String;
};
}