package com.example.springboot2demo.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: Richard.JI
 * @date: 2020-11-27 11:27
 **/
@RestController
@RequestMapping("/v1")
public class IndexController {

    @Autowired
    MeterRegistry registry;

    private Counter counter_core;
    private Counter counter_index;

    @PostConstruct
    private void init() {
        counter_core = registry.counter("app_requests_method_count_test1", "method", "IndexController.core");
        counter_index = registry.counter("app_requests_method_count_test1", "method", "IndexController.index");
    }

    @RequestMapping(value = "/index")
    public Object index() {
        try {
            counter_index.increment();
        } catch (Exception e) {
            return e;
        }
        return counter_index.count() + " index of springboot2-prometheus.";
    }

    @RequestMapping(value = "/core")
    public Object coreUrl() {
        try {
            counter_core.increment();
        } catch (Exception e) {
            return e;
        }
        return counter_core.count() + " coreUrl Monitor by Prometheus.";
    }
}
