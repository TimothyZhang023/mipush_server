package com.zts1993.mipush.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zts1993.mipush.controller.api.PushApi;
import com.zts1993.mipush.controller.response.ResponseVO;
import com.zts1993.mipush.service.MiPushApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PushController implements PushApi {


    @Autowired
    MiPushApi miPushApi;

    @HystrixCommand(
            fallbackMethod = "fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
            }
    )
    @RequestMapping(value = "/publish/simple", method = RequestMethod.POST)
    public ResponseVO<String> simple(
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "description", required = true) String description,
            @RequestParam(value = "payload", required = false, defaultValue = "") String payload
    ) {

        try {
            miPushApi.simpleMsg(title, description, payload);
            return new ResponseVO<String>(0, "success");
        } catch (Exception e) {
            return new ResponseVO<String>(-1, e.getLocalizedMessage());
        }

    }

    public ResponseVO<String> fallback(@RequestParam(value = "title", required = true) String title,
                                       @RequestParam(value = "description", required = true) String description,
                                       @RequestParam(value = "payload", required = false, defaultValue = "") String payload) {
        return new ResponseVO<String>(-1, "limited");
    }


}
