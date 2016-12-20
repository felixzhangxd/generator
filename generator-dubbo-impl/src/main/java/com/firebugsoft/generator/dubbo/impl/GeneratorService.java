package com.firebugsoft.generator.dubbo.impl;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.firebugsoft.generator.dubbo.api.IGeneratorService;
import com.firebugsoft.generator.dubbo.impl.component.SnowFlakeGenerator;

/**
 * 生成器服务
 * @author felix
 */
@Service
public class GeneratorService implements IGeneratorService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SnowFlakeGenerator twitterSnowFlake;

    @Override
    public long pk() {
        return twitterSnowFlake.next();
    }

    @Override
    public long test(long sleep) {
        logger.info("start: {}", System.currentTimeMillis());
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            logger.error("error: {}", e);
        }
        logger.info("ent: {}", System.currentTimeMillis());
        return twitterSnowFlake.next();
    }
}
