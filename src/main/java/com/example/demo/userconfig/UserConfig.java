package com.example.demo.userconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    CustomMetadataConfig.class,
    CustomMessageSourceConfig.class
})
@Configuration
public class UserConfig {
}
