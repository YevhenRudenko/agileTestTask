package com.yevhenrudenko.testTask.config;

import com.yevhenrudenko.testTask.util.jsoup.JsoupCssSelectSnippet;
import com.yevhenrudenko.testTask.util.jsoup.JsoupFindByIdSnippet;
import com.yevhenrudenko.testTask.util.selector.CSSSelectorCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class JsoupConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(JsoupConfig.class);

    @Autowired
    private ApplicationArguments args;

    @Bean
    public JsoupCssSelectSnippet jsoupCssSelectSnippet(){
        LOGGER.info("create css select snippet with for the next file: " + args.getSourceArgs()[1]);
        return new JsoupCssSelectSnippet(args.getSourceArgs()[1]);
    }

    @Bean
    public JsoupFindByIdSnippet jsoupFindByIdSnippet() {
        LOGGER.info("create id select snippet with for the next file: " + args.getSourceArgs()[0]);
        return new JsoupFindByIdSnippet(args.getSourceArgs()[0]);
    }

}
