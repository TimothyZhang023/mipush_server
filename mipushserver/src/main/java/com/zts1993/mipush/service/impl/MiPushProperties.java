package com.zts1993.mipush.service.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@NoArgsConstructor
@ConfigurationProperties("mipush")
public class MiPushProperties {


    private String appSecretKey;


    private String myPackageName;


}
