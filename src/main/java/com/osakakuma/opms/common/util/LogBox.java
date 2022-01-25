package com.osakakuma.opms.common.util;

import com.osakakuma.opms.common.entity.AuditLog;
import com.osakakuma.opms.common.entity.LogAction;
import com.osakakuma.opms.common.entity.LogModule;
import com.osakakuma.opms.config.model.CognitoUser;
import lombok.RequiredArgsConstructor;
import org.apache.tika.utils.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
public class LogBox {
    private static final TransactionDefinition DEFAULT_TRANSACTION_DEFINITION = new DefaultTransactionDefinition();
    public static final String LOG_TITLE_SUFFIX = "@logTitle";
    public static final String LOG_DESCRIPTION_SUFFIX = "@logDescription";
    public static final String LOG_CONTENT_SUFFIX = "@logContent";
    public static final String LOG_ACTION_SUFFIX = "@logAction";
    public static final String LOG_MODULE_SUFFIX = "@logModule";

    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager platformTransactionManager;
    private final CognitoUser cognitoUser;
    private final LogModule logModule;

    // storing logs to be batched inserted into the database
    private final List<AuditLog> logs = new ArrayList<>(10);

    /**
     * This is a shortcut method that creates a log entry with title, description, value with the same category suffix.
     *
     * @param key Common key for all title, description, content
     * @param desc The list of arguments for description text
     * @param before List of arguments for before value (null if not exists)
     * @param after List of arguments for after value (null if not exists)
     */
    public void log (String key, Collection<String> desc, Collection<String> before, Collection<String> after) {
        var title = "#" + key + LOG_TITLE_SUFFIX;
        var description = "#" + key + LOG_DESCRIPTION_SUFFIX + getArgString(desc);
        var valBefore = "#" + Optional.ofNullable(before)
                .map(b -> key + LOG_CONTENT_SUFFIX + getArgString(b))
                .orElse(null);
        var valAfter = "#" + Optional.ofNullable(after)
                .map(a -> key + LOG_CONTENT_SUFFIX + getArgString(a))
                .orElse(null);

        log(title, description, valBefore, valAfter);
    }

    private String getArgString(Collection<String> args) {
        if (CollectionUtils.isEmpty(args)) return StringUtils.EMPTY;

        return "|" + String.join(";", args);
    }

    /**
     * Log relevant audit logs into the database.
     * For all parameters listed that this function accepts, they are translatable via message_i18n.
     * For the format of the message translatable:
     * <ul>
     *     <li>message starts with "#" will be looked up with the format of "#key@category"</li>
     *     <li>message that does not start with "#" will return the same</li>
     *     <li>message that has "|" separated arguments "#key@category|#arg1@cat1;arg2</li>
     * </ul>
     *
     * @param title The log title (can be considered as a brief summary of the log entry).
     *              Require to use the <pre>{@code LogBox.LOG_TITLE_SUFFIX}</pre> as the suffix
     *              if there is no additional inner parameters to be translated
     * @param description The log description which is a longer version of the log details directly displayable.
     *                    Require to use the <pre>{@code LogBox.LOG_DESCRIPTION_SUFFIX}</pre> as the suffix
     *                    if there is no additional inner parameters to be translated
     * @param before The before value recorded for the specific log entry,
     *               can be null (but before and after cannot be both null
     * @param after The after value recorded for the specific log entry,
     *              can be null (but before and after cannot be both null)
     * @see org.springframework.context.support.MessageSourceAccessor
     */
    public void log(String title, String description, String before, String after) {
        var action = getAction(before, after);
        logs.add(AuditLog.builder()
                        .username(cognitoUser.username())
                        .logTime(Instant.now())
                        .logAction(action)
                        .logModule(logModule)
                        .logTitle(title)
                        .logDescription(description)
                        .valBefore(before)
                        .valAfter(after)
                .build());
    }

    /**
     * After logging is completed, call this method to persist the logs into the database.
     * This method will directly commit the logs into the DB without rolling back.
     * As such, only invoke this method at the end of the logging or step logging.
     * After commit is invoked the in-memory persisted logs will be cleared for next step logging if any
     */
    public void commit () {
        // if there is no logs, nothing to commit
        if (CollectionUtils.isEmpty(logs)) return;

        var transaction = platformTransactionManager.getTransaction(DEFAULT_TRANSACTION_DEFINITION);
        var sql = """
                insert into audit_log (username, log_time, log_action, log_module, log_title, log_description, val_before, val_after)
                values (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.batchUpdate(sql, logs, logs.size(), (ps, l) -> {
            ps.setString(1, l.getUsername());
            ps.setTimestamp(2, Timestamp.from(l.getLogTime()));
            ps.setString(3, l.getLogAction().name());
            ps.setString(4, l.getLogModule().name());
            ps.setString(5, l.getLogTitle());
            ps.setString(6, l.getLogDescription());
            ps.setString(7, l.getValBefore());
            ps.setString(8, l.getValAfter());
        });

        // direct committing the logs into the audit log table, assuming the audit log transaction will not fail logging
        platformTransactionManager.commit(transaction);

        // clear logs buffer once the logs has been committed into the database
        logs.clear();
    }

    private LogAction getAction(String before, String after) {
        OpmsAssert.isTrue(Objects.nonNull(before) || Objects.nonNull(after),
                () -> "Invalid log value because before/after values cannot be both null");

        if (Objects.isNull(before)) return LogAction.CREATE;
        if (Objects.isNull(after)) return LogAction.DELETE;

        return LogAction.UPDATE;
    }
}
