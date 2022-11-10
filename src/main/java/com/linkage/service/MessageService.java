package com.linkage.service;

import com.linkage.viewmodel.admin.message.MessagePageRequestVM;
import com.linkage.viewmodel.student.user.MessageRequestVM;
import com.linkage.domain.Message;
import com.linkage.domain.MessageUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MessageService {

    List<Message> selectMessageByIds(List<Integer> ids);

    PageInfo<MessageUser> studentPage(MessageRequestVM requestVM);

    PageInfo<Message> page(MessagePageRequestVM requestVM);

    List<MessageUser> selectByMessageIds(List<Integer> ids);

    void sendMessage(Message message, List<MessageUser> messageUsers);

    void read(Integer id);

    Integer unReadCount(Integer userId);

    Message messageDetail(Integer id);
}
