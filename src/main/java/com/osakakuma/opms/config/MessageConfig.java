package com.osakakuma.opms.config;

import com.osakakuma.opms.common.util.OpmsAssert;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class MessageConfig {
    private final MessageSource messageSource;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void setupParentSource() {
        if (messageSource instanceof DelegatingMessageSource) {
            ((DelegatingMessageSource) messageSource).setParentMessageSource(jdbcMessageSource());
        }
    }

    private MessageSource jdbcMessageSource() {
        return new JdbcMessageSource(jdbcTemplate);
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource);
    }

    @RequiredArgsConstructor
    private static class JdbcMessageSource extends AbstractMessageSource {
        private static final Map<Locale, Map<String, String>> cache = new ConcurrentHashMap<>(4);
        private final JdbcTemplate jdbcTemplate;
        private volatile long lastQuery = 0L;

        @Override
        @Contract("_, _ -> new")
        protected @NotNull MessageFormat resolveCode(String code, Locale locale) {
            return new MessageFormat(resolve(code, locale), locale);
        }

        private String resolve(String code, Locale locale) {
            if (StringUtils.isEmpty(code)) return StringUtils.EMPTY;

            var raw = code.split("\\|");
            if (raw.length > 1) {
                // contain argument
                var args = raw[1].split(";");
                return resolveMessage(raw[0], args, locale);
            }

            // no argument
            return checkResolveMessage(code, locale);
        }

        private String resolveMessage(String code, String[] args, Locale locale) {
            var template = new MessageFormat(checkResolveMessage(code, locale));
            for (var i = 0; i < args.length; ++i) args[i] = checkResolveMessage(args[i], locale);
            return template.format(args);
        }

        private String checkResolveMessage(String code, Locale locale) {
            OpmsAssert.isTrue(StringUtils.isNotEmpty(code), () -> "Empty JDBC message lookup code");
            var maxCache = Duration.ofMinutes(3).toMillis();

            if (lastQuery + maxCache < System.currentTimeMillis()) {
                cache.clear();
            }

            // assume per message within 3 minutes window around 100
            return cache.computeIfAbsent(locale, k -> new ConcurrentHashMap<>(133))
                    .computeIfAbsent(code, k -> code.startsWith("#")? resolveMessage(code, locale) : code);
        }

        private String resolveMessage(String code, Locale locale) {
            OpmsAssert.isTrue(code.contains("@"), () -> "Invalid JDBC message lookup format");

            var args = code.split("@");
            var loc = locale.getLanguage();
            OpmsAssert.isTrue(args.length == 2, () -> "JDBC message lookup code incorrect number of arguments");

            var sql = """
                select val
                from message_i18n
                where key = ? and category = ? and locale = ?
            """;
            var message = jdbcTemplate.queryForObject(sql, String.class, args[0].substring(1), args[1], loc);

            OpmsAssert.isTrue(StringUtils.isNotEmpty(message), () ->
                    MessageFormat.format("No message found for key {0}, category {1} under locale {2}",
                            args[0], args[1], loc));

            this.lastQuery = System.currentTimeMillis();
            return message;
        }
    }
}
