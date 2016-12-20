package com.firebugsoft.generator.dubbo.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.firebugsoft.generator.dubbo.api.IGeneratorService;
import com.firebugsoft.generator.dubbo.impl.component.SnowFlakeGenerator;

/**
 * 生成器服务
 * @author felix
 */
@Service
public class GeneratorService implements IGeneratorService {
    @Resource
    private SnowFlakeGenerator twitterSnowFlake;

    @Override
    public long pk() {
        return twitterSnowFlake.next();
    }
}
