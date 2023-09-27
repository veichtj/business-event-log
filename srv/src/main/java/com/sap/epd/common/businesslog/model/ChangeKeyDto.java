package com.sap.epd.common.businesslog.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class ChangeKeyDto {

    String entityName;
    String attributeName;

}