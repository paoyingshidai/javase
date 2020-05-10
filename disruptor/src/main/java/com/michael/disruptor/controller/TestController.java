package com.michael.disruptor.controller;

import com.michael.disruptor.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟在高并发的场景下对数据库进行操作是否会造成并发问题
 */
@RestController
public class TestController {

    @Autowired
    private LockService lockService;

    @RequestMapping("getCount")
    public String getCount() {
        return lockService.getCount().toString();
    }

    @RequestMapping("addLock")
    public void addLock() {
        lockService.addThroughLock();
    }

    @RequestMapping("noLock")
    public void noLock() {
        lockService.noLock();
    }

    @RequestMapping("addDisruptor")
    public void addDisruptor() {
        lockService.addThroughDisruptor();
    }

    @RequestMapping("addUsingTranslate")
    public void addUsingTranslate() {
        lockService.addUsingTranslate();
    }

}
