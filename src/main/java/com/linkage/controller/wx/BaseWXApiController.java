package com.linkage.controller.wx;

import com.linkage.context.WxContext;
import com.linkage.domain.User;
import com.linkage.domain.UserToken;
import com.linkage.utility.ModelMapperSingle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseWXApiController {
    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    @Autowired
    private WxContext wxContext;

    protected User getCurrentUser() {
        return wxContext.getCurrentUser();
    }

    protected UserToken getUserToken() {
        return wxContext.getCurrentUserToken();
    }
}
