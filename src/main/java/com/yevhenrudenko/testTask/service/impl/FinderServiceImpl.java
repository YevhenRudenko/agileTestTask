package com.yevhenrudenko.testTask.service.impl;

import com.yevhenrudenko.testTask.service.FinderService;
import com.yevhenrudenko.testTask.util.jsoup.CssSelectSnippet;
import com.yevhenrudenko.testTask.util.jsoup.FindByIdSnippet;
import com.yevhenrudenko.testTask.util.selector.SelectorCreator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinderServiceImpl implements FinderService {

    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FinderServiceImpl.class);

    @Autowired
    private CssSelectSnippet cssSelectSnippet;

    @Autowired
    private FindByIdSnippet findByIdSnippet;

    @Autowired
    private SelectorCreator selectorCreator;

    @Value("${targetId}")
    private String targetId;

    @Override
    public String findTargetElement() {
        List<String> attributesById = findByIdSnippet.findAttributesById(targetId);

        LOGGER.info("creating selector from the next attributes: " + attributesById);
        String jsoupSelectorFromAttribute = selectorCreator.createJsoupSelectorFromAttribute(attributesById);
        LOGGER.info("created selector with the next html tag name: " + jsoupSelectorFromAttribute);

        List<String> elementsByCssQuery = cssSelectSnippet.findElementsByCssQuery(jsoupSelectorFromAttribute);

        return null;
    }
}
