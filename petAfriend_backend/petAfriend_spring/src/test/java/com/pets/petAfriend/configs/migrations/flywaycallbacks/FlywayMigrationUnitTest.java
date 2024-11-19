package com.pets.petAfriend.configs.migrations.flywaycallbacks;

import com.pets.petAfriend.configs.migrations.FlywayCallbackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;

@Slf4j
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = FlywayCallbackTestConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        FlywayTestExecutionListener.class })
@SpringBootTest
@TestPropertySource(value = {"file:src/test/resources/application-test.properties"}, encoding = "UTF-8")
public class FlywayMigrationUnitTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void migrateWithNoCallbacks() {
        logTestBoundary("migrateWithNoCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("filesystem:src/test/resources/db/migration/")
                .load();
        flyway.migrate();
        Assertions.assertThat(flyway).isInstanceOf(Flyway.class);
    }

    @Test
    public void migrateWithJavaCallbacks() {
        logTestBoundary("migrateWithJavaCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("filesystem:src/test/resources/db/migration/")
                .callbacks(new ExampleFlywayCallback())
                .load();
        flyway.migrate();
        Assertions.assertThat(flyway).isInstanceOf(Flyway.class);
    }

    @Test
    public void migrateWithSqlCallbacks() {
        logTestBoundary("migrateWithSqlCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("filesystem:src/test/resources/db/migration/", "filesystem:src/test/resources/db/callbacks")
                .load();
        flyway.migrate();
        Assertions.assertThat(flyway).isInstanceOf(Flyway.class);
    }

    @Test
    public void migrateWithSqlAndJavaCallbacks() {
        logTestBoundary("migrateWithSqlAndJavaCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("filesystem:src/test/resources/db/migration/", "filesystem:src/test/resources/db/callbacks")
                .callbacks(new ExampleFlywayCallback())
                .load();
        flyway.migrate();
        Assertions.assertThat(flyway).isInstanceOf(Flyway.class);
    }

    private void logTestBoundary(String testName) {
        System.out.println("\n");
        log.info("> {}", testName);
    }

}
