//package com.example.rezerwacje.conf;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataConfig {
//
//    @Bean(name = "dupa")
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("schema.sql")
//                .addScript("test-data.sql")
//                .build();
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate(@Qualifier("dupa") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//}