package com.ylb.service;

import com.ylb.entity.SysNotice;
import java.util.List;

public interface NoticeService {
    List<SysNotice> getList(String title);
    void publish(SysNotice notice);
    void update(SysNotice notice);
    void delete(Long noticeId);
}