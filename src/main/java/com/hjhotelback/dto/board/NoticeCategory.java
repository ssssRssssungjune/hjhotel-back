package com.hjhotelback.dto.board;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NoticeCategory {
    NOTICE, // 공지
    EVENT,  // 이벤트
    INFO;   // 정보

    @JsonCreator
    public static NoticeCategory fromString(String value) {
        for (NoticeCategory category : NoticeCategory.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown category: " + value);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
