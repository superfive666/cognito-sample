package com.osakakuma.opms.config;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@MapperScan(basePackages = {"com.osakakuma.opms.**.dao", "com.osakakuma.opms.**.mapper"}, annotationClass = Mapper.class)
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            if (configuration.getDefaultStatementTimeout() == null) {
                configuration.setDefaultStatementTimeout(Duration.ofSeconds(60).toSecondsPart());
            }

            configuration.setMapUnderscoreToCamelCase(Boolean.TRUE);
            configuration.setJdbcTypeForNull(JdbcType.VARCHAR);
            // set default enum type handler if any
        };
    }
}
