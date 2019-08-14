package com.yevhenrudenko.testTask;

import com.yevhenrudenko.testTask.service.FinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestTaskApplication implements CommandLineRunner {

	private static Logger LOGGER = LoggerFactory
			.getLogger(TestTaskApplication.class);

	@Autowired
    private FinderService finderService;

	public static void main(String[] args) {
		if(args.length != 2){
            LOGGER.error("Incorrect input parameters, should be source and destination files");
		    System.exit(0);
        }
	    SpringApplication.run(TestTaskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        finderService.findTargetElement();
	}

    private int exitFromApp() {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(TestTaskApplication.class)
                .web(WebApplicationType.NONE).run();
        return SpringApplication.exit(ctx, (ExitCodeGenerator) () -> 0);
    }
}
