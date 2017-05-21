
package com.rfa.n26.transactions.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rfa.n26.transactions.cache.TransactionsCache;
import com.rfa.n26.transactions.cache.TransactionsCacheImpl;
import com.rfa.n26.transactions.holder.TransactionHolder;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Component
@Configuration
@ComponentScan(basePackages = {
        "com.rfa.n26.transactions.holder",
        "com.rfa.n26.transactions.json",
        "com.rfa.n26.transactions.resource",
        "com.rfa.n26.transactions.model.service"
})
public class SpringAppConfiguration {
    
    @Bean
    public TransactionsCache transactionsCache() {
        return new TransactionsCacheImpl();
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return mapper;
    }

    @Bean
    public BeanPostProcessor validationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
