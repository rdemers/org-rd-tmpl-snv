package org.rd.tmpl.snv.config;

import org.rd.tmpl.snv.dao.BookRepository;
import org.rd.tmpl.snv.dto.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    public DatabaseConfig() {
        super();
    }

    @Bean
    public CommandLineRunner loadData(BookRepository bookRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (bookRepository.count() != 0L)
            {
                logger.info("Existing data ...");
                return;
            }

            logger.info("Data generation ...");

            logger.info("... Books.");

            // title, description.
            bookRepository.save(new Book("Maverick", "Story of a Navy pilot."));
            bookRepository.save(new Book("Thor", "Story of a Norse god."));
            bookRepository.save(new Book("HarleyD", "Story of a guy who builds motorcycles."));
            bookRepository.save(new Book("JohnDoe", "Story of a nobody."));
            bookRepository.save(new Book("RDemers", "Story of a Shaolin kung fu monk."));
            bookRepository.save(new Book("Ghost-Rider", "Story of a guy who got screwed."));
            bookRepository.save(new Book("Jésus", "Story of a man who became God."));
            bookRepository.save(new Book("Spartan", "Story of a laconic guy."));
            bookRepository.save(new Book("Enterprise", "History of a spaceship."));
            bookRepository.save(new Book("Moon", "Story of a satellite that wanted to be a planet."));
            logger.info("... Generation finished.");
        };
    }
}