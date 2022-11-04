package com.lq.sdj.pdc.secjwt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lq.sdj.pdc.secjwt.dao.BookRepository;
import com.lq.sdj.pdc.secjwt.dto.Book;

@Configuration
public class DatabaseConfig
{
    public DatabaseConfig()
    {
        super();
    }

    @Bean
    public CommandLineRunner loadData(BookRepository bookRepository)
    {
        return args -> 
        {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (bookRepository.count() != 0L)
            {
                logger.info("Données existante.");
                return;
            }

            logger.info("Génération des données");

            logger.info("... Books.");

            // title, description.
            bookRepository.save(new Book("Maverick", "Histoire d'un pilote de la Navy."));
            bookRepository.save(new Book("Thor", "Histoire d'un dieu nordique."));
            bookRepository.save(new Book("HarleyD", "Histoire d'un gars qui construit des motos."));
            bookRepository.save(new Book("JohnDoe", "Histoire d'un nobody."));
            bookRepository.save(new Book("RDemers", "Histoire d'un moine du kung-fu Shaolin."));
            bookRepository.save(new Book("Ghost-Rider", "Histoire d'un gars qui s'est fait fourré."));
            bookRepository.save(new Book("Jésus", "Histoire d'un homme qui est devenu Dieu."));
            bookRepository.save(new Book("Spartan", "Histoire d'un gars laconique."));
            bookRepository.save(new Book("Enterprise", "Histoire des aventures spaciales."));
            bookRepository.save(new Book("Moon", "Histoire d'un satellite qui voulait être une planète."));

            logger.info("Génération terminée.");
        };
    }
}