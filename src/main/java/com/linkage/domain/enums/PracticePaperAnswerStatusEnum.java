package com.linkage.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum PracticePaperAnswerStatusEnum {

    WaitJudge(1, "待批改"),
    Complete(2, "完成");

    int code;
    String name;

    PracticePaperAnswerStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
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


    private static final Map<Integer, PracticePaperAnswerStatusEnum> keyMap = new HashMap<>();

    static {
        for (PracticePaperAnswerStatusEnum item : PracticePaperAnswerStatusEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static PracticePaperAnswerStatusEnum fromCode(Integer code) {
        return keyMap.get(code);
    }

}
