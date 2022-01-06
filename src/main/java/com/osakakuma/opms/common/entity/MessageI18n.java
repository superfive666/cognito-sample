package com.osakakuma.opms.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageI18n {
    private String locale;
    private String key;
    private String val;
    private String category;
}
