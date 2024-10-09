package com.rajsubhod.userservice.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoURI;

    @Value("${spring.data.mongodb.host}")
    private String mongoHOST;

    @Value("${spring.data.mongodb.port}")
    private Integer mongoPORT;

    @Value("${spring.data.mongodb.database}")
    private String mongoDATABASE;

    @Bean
    public MongoClient mongoClient() {
        if (mongoURI != null && !mongoURI.isEmpty()) {
            // Use URI if provided
            return MongoClients.create(mongoURI);
        } else {
            // Fallback to host and port
            String connectionString = String.format("mongodb://%s:%d", mongoHOST, mongoPORT);
            return MongoClients.create(connectionString);
        }
    }

    @Override
    protected String getDatabaseName() {
        return mongoDATABASE;
    }
}
