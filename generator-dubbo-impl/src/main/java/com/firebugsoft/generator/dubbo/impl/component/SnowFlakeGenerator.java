package com.firebugsoft.generator.dubbo.impl.component;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * twitter snow flake generator
 * 从左向右
 * 63位: 置0
 * 62-22位:timestamp 时间戳
 * 22-12位:workid 工作机器id
 * 11-0位:sequence 序列号
 * 使用注意：
 * 本机时间不能调整
 */
@Component
public class SnowFlakeGenerator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private long         timestamp;
    @Value(value = "${worker}")
    private long         worker;
    private long         sequence;

    public SnowFlakeGenerator() {
        this.timestamp = System.currentTimeMillis();
        this.sequence = 0L;
    }
    
    @PostConstruct
    void validate() {
        logger.info("TwitterSnowFlake's worker is {}.", worker);
        if (worker < 0x0L || 0x3FFL < worker) {
            throw new IllegalArgumentException("worker Id can't be less than 0 or greater than 1023");
        }        
    }

    public synchronized long next() {
        long now = System.currentTimeMillis();
        if (now > this.timestamp) {
            this.timestamp = now;
            this.sequence = 0L;
        } else if (now == this.timestamp) {
            this.sequence++;
            if (this.sequence > 0xFFFL) {
                this.timestamp = tilNextMillis(this.timestamp);
                this.sequence = 0L;
            }
        } else if (now < this.timestamp) {
            logger.error("Clock is moving backwards. Rejecting requests until {}.", this.timestamp);
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to create for %d milliseconds", this.timestamp - now));
        }
        return this.create(this.timestamp, this.sequence);
    }

    private long create(long timestamp, long sequence) {
        // 1451577600000: 2016-01-01 00:00:00
        return (timestamp - 1451577600000L) << 22 | worker << 12 | sequence;
    }

    private long tilNextMillis(long timestamp) {
        long now = System.currentTimeMillis();
        while (now <= timestamp) {
            now = System.currentTimeMillis();
        }
        return now;
    }
}
