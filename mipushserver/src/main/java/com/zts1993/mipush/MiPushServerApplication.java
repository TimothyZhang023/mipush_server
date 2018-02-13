package com.zts1993.mipush;

import com.zts1993.mipush.service.MiPushApi;
import com.zts1993.mipush.service.impl.MiPushProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringCloudApplication
@EnableCircuitBreaker
@EnableConfigurationProperties({MiPushProperties.class})
@Slf4j
public class MiPushServerApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MiPushServerApplication.class);
        springApplication.run(args);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Component
    public class ScheduledTasks implements InitializingBean {

        @Autowired
        @Setter
        private MiPushApi miPushApi;

        private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        @Scheduled(fixedRate = 1000 * 60 * 1000) //, initialDelay = 10
        public void reportCurrentTime() {
            Date date = new Date();
            log.info("The time is now {}", dateFormat.format(date));
            try {
                miPushApi.simpleMsg("当前时间", dateFormat.format(date), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            try {
                Date date = new Date();
                miPushApi.simpleMsg("服务启动啦", "当前时间" + dateFormat.format(date), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
