package com.yevhenrudenko.testTask.util.jsoup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yevhenrudenko.testTask.constants.Const.CHARSET_NAME;

public class JsoupCssSelectSnippet implements CssSelectSnippet{

    private static Logger LOGGER = LoggerFactory.getLogger(JsoupCssSelectSnippet.class);
    private String destinationPath;

    public JsoupCssSelectSnippet(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    private static Optional<Elements> findElementsByQuery(File htmlFile, String cssQuery) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.select(cssQuery));

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<String> findElementsByCssQuery(String cssQuery) {
        List<String> paths = new ArrayList<>();
        LOGGER.debug("Start finding target element by cssQuery: " + cssQuery);
        Optional<Elements> elementsOpt = findElementsByQuery(new File(destinationPath), cssQuery);

        LOGGER.debug("Found element with cssQuery : " + cssQuery + " " + elementsOpt.orElse(null));
        Optional<List<String>> elementsAttrsOpts = elementsOpt.map(buttons ->
                {
                    buttons.iterator().forEachRemaining(button -> {
                        String pathInDocument = getPathInDocument(button);
                                paths.add(pathInDocument);
                            });
                    return paths;
                }
        );

        elementsAttrsOpts.ifPresent(attrsList ->
                attrsList.forEach(attrs ->
                        LOGGER.info("Target element paths: [{}]", paths)
                )
        );

        return paths;
    }

    private String getPathInDocument(Element element) {
        List<String> path = new ArrayList<>();
        path.add(element.tagName());
        while(element.parent() != null){
            path.add(element.parent().tagName());
            element = element.parent();
        }
        Collections.reverse(path);
        return path.stream().reduce((parent1, parent2) -> parent1.concat("->").concat(parent2)).orElse(null);
    }

}
