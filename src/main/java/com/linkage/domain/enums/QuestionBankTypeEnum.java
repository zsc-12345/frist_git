package com.linkage.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum QuestionBankTypeEnum {

    SingleChoice(1, "单选题"),
    MultipleChoice(2, "多选题"),
    TrueFalse(3, "判断题"),
    GapFilling(4, "填空题"),
    ShortAnswer(5, "简答题");

    int code;
    String name;

    QuestionBankTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    private static final Map<Integer, QuestionBankTypeEnum> keyMap = new HashMap<>();

    static {
        for (QuestionBankTypeEnum item : QuestionBankTypeEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static QuestionBankTypeEnum fromCode(Integer code) {
        return keyMap.get(code);
    }

    public static boolean needSaveTextContent(Integer code) {
        QuestionBankTypeEnum questionTypeEnum = QuestionBankTypeEnum.fromCode(code);
        switch (questionTypeEnum) {
            case GapFilling:
            case ShortAnswer:
                return true;
            default:
                return false;
        }
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
