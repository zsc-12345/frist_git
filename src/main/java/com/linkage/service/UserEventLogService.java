package com.linkage.service;

import com.linkage.viewmodel.admin.user.UserEventPageRequestVM;
import com.linkage.domain.UserEventLog;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserEventLogService extends BaseService<UserEventLog> {

    List<UserEventLog> getUserEventLogByUserId(Integer id);

    PageInfo<UserEventLog> page(UserEventPageRequestVM requestVM);

    List<Integer> selectMothCount();
}
