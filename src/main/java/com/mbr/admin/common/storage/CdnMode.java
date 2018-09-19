package com.mbr.admin.common.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

/**
 * 描述:系统CDN支持方式
 *
 * @outhor liuji
 * @create 2018-06-21 15:30
 */
public enum CdnMode {

    LOCAL, ALIYUN, AWS, UPYUN, OTHER;

    @JsonCreator
    public static CdnMode fromValue(final String value) {
        if (StringUtils.isEmpty(value)) {
            return LOCAL;
        }
        for (CdnMode v : values()) {
            if (StringUtils.equalsIgnoreCase(v.name(), value)) {
                return v;
            }
        }
        return OTHER;
    }
}
