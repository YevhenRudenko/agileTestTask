package com.yevhenrudenko.testTask.util.selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CSSSelectorCreator implements SelectorCreator {

    private static Logger LOGGER = LoggerFactory.getLogger(CSSSelectorCreator.class);

    @Override
    public String createJsoupSelectorFromAttribute(List<String> attrs) {
        StringBuilder selector = new StringBuilder("*");
        attrs.forEach(attr -> {
            LOGGER.debug("creating selector from the next attribute: " + attr);

            String[] split = attr.split("=");

            if(split.length != 2) {
                LOGGER.error("Invalid attribute value: " + attr);
                return;
            }

            String key = split[0];
            String value = split[1];

            switch(key){
                case "class":
                    LOGGER.trace("creating selector from the next class");
                    String[] cssClasses = value.split(" ");
                    selector.append(".").append(cssClasses[0]);
                    LOGGER.trace("created selector from the class: " + selector.toString());
                    break;
                case "href":
                    LOGGER.trace("creating selector from the href");
                    selector.append("[").append(key).append("*=").append("ok").append("]");
                    LOGGER.trace("created selector from the href:" + selector.toString());
                    break;
                case "onclick":
                    LOGGER.trace("creating selector from the onclick");
                    selector.append("[").append(key).append("*=").append("ok").append("]");
                    LOGGER.trace("created selector from the onclick:" + selector.toString());
                    break;
            }
        });
        LOGGER.debug("created complex css selector: " + selector.toString());
        return selector.toString();
    }
}
