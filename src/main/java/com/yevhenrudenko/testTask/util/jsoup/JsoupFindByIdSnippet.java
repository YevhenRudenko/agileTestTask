package com.yevhenrudenko.testTask.util.jsoup;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yevhenrudenko.testTask.constants.Const.CHARSET_NAME;

public class JsoupFindByIdSnippet implements FindByIdSnippet {

    private static Logger LOGGER = LoggerFactory.getLogger(JsoupFindByIdSnippet.class);
    private String sourcePath;

    public JsoupFindByIdSnippet(String resourcePath) {
        this.sourcePath = resourcePath;
    }

    private static Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.getElementById(targetElementId));

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<String> findAttributesById(String elementId) {
        LOGGER.debug("Start finding target element by id: " + elementId);
        Optional<Element> buttonOpt = findElementById(new File(sourcePath), elementId);

        LOGGER.debug("Found element with id : " + elementId + " " + buttonOpt.orElse(null));

        Optional<List<String>> stringifiedAttributesOpt = buttonOpt.map(button ->
                button.attributes().asList().stream()
                        .map(attr -> attr.getKey() + "=" + attr.getValue())
                        .filter(attr -> !attr.contains("id"))
                        .collect(Collectors.toList())
        );

        stringifiedAttributesOpt.ifPresent(attrs -> LOGGER.info("Target element attrs: [{}]", attrs));

        return stringifiedAttributesOpt.orElse(Collections.EMPTY_LIST);
    }
}
