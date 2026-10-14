package com.ylb.service.impl;

import com.ylb.mapper.SysConfigMapper;
import com.ylb.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {
    @Autowired
    private SysConfigMapper configMapper;

    @Override
    public boolean isRegisterEnabled() {
        String val = configMapper.selectValueByKey("sys.account.registerUser");
        return "true".equals(val);
    }

    @Override
    public void setRegisterEnabled(boolean enabled) {
        configMapper.updateValue("sys.account.registerUser", String.valueOf(enabled));
    }

}