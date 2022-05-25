package com.berk2s.docman.web.models;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Error descriptions
 * These descriptions will be displayed value of error_description in a error response
 */
@Getter
public enum ErrorDesc {
    BAD_REQUEST("Bad request. Some mandatory fields might missing", 1),
    REPORT_CREATION_ERROR("Error while creating report", 2);

    private final String desc;
    private final Integer code;
    private static final Map<String, Integer> errorMap =  new HashMap<>();

    ErrorDesc(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    static {
        for (ErrorDesc errorDesc : ErrorDesc.values()) {
            errorMap.put(errorDesc.getDesc(), errorDesc.getCode());
        }
    }

    static public Integer getCodeFormDesc(String desc) {
        return errorMap.get(desc);
    }
}
