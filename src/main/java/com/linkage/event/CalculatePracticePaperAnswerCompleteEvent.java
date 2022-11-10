package com.linkage.event;

import com.linkage.domain.PracticePaperAnswerInfo;
import org.springframework.context.ApplicationEvent;

/**
 * @version 3.5.0
 * @description:  The type Calculate exam paper answer complete event.
 * Copyright (C), 2020-2021, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
public class CalculatePracticePaperAnswerCompleteEvent extends ApplicationEvent {


    private final PracticePaperAnswerInfo practicePaperAnswerInfo;

    /**
     * Instantiates a new Calculate Practice paper answer complete event.
     *
     * @param practicePaperAnswerInfo the Practice paper answer info
     */
    public CalculatePracticePaperAnswerCompleteEvent(final PracticePaperAnswerInfo practicePaperAnswerInfo) {
        super(practicePaperAnswerInfo);
        this.practicePaperAnswerInfo = practicePaperAnswerInfo;
    }

    /**
     * Gets exam paper answer info.
     *
     * @return the Practice paper answer info
     */
    public PracticePaperAnswerInfo getPracticePaperAnswerInfo() {
        return practicePaperAnswerInfo;
    }

}
