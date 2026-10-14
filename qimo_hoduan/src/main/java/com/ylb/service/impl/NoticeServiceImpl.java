package com.ylb.service.impl;

import com.ylb.entity.SysNotice;
import com.ylb.entity.SysUser;
import com.ylb.mapper.SysNoticeMapper;
import com.ylb.mapper.SysUserMapper;
import com.ylb.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private SysNoticeMapper noticeMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @Cacheable(value = "notices", key = "#title == null ? 'all' : #title")
    public List<SysNotice> getList(String title) {
        return noticeMapper.selectList(title);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "notices", allEntries = true)
    public void publish(SysNotice notice) {
        // 获取当前登录用户
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserMapper.selectByUsername(username);
        
        notice.setPublisherId(user.getUserId());
        noticeMapper.insert(notice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "notices", allEntries = true)
    public void update(SysNotice notice) {
        noticeMapper.update(notice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "notices", allEntries = true)
    public void delete(Long noticeId) {
        noticeMapper.deleteById(noticeId);
    }
}