package com.firebugsoft.generator.dubbo.impl;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.firebugsoft.generator.dubbo.api.IGeneratorService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class GeneratorServiceTest {
    @Resource
    private IGeneratorService generatorService;

    @Test
    public void pk() {
//    	Assert.assertTrue(generatorService.pk() > 0);
        System.out.println(System.currentTimeMillis());
        try{
            Assert.assertTrue(generatorService.test(15000L) > 0);
        }catch (Exception e) {
            System.out.println("error");
        }
        System.out.println(System.currentTimeMillis());
    }
}
