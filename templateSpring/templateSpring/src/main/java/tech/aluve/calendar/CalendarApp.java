package tech.aluve.calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalendarApp {

	private static final Logger logger = LoggerFactory.getLogger(CalendarApp.class);
	public static void main(String[] args) {
		SpringApplication.run(CalendarApp.class, args);

		logger.debug("Debug log message");
		logger.info("Info log message");
		logger.error("Error log message");
		logger.warn("Warn log message");
		logger.trace("Trace log message");

	}

}
