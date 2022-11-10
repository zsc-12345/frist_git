package com.linkage.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum RoleStatusEnum {


    Enable(1, "启用"),
    Disable(2, "禁用");

    int code;
    String name;

    RoleStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<Integer, RoleStatusEnum> keyMap = new HashMap<>();

    static {
        for (RoleStatusEnum item : RoleStatusEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static RoleStatusEnum fromCode(Integer code) {
        return keyMap.get(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
