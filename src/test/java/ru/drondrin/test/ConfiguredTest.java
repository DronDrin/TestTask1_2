package ru.drondrin.test;

import org.junit.jupiter.api.BeforeAll;
import ru.drondrin.ConfigReader;
import ru.drondrin.Main;

public abstract class ConfiguredTest {
    @BeforeAll
    protected static void setUpParameters() {
        Main.CONFIG = new ConfigReader("src/test/resources/config.properties");
    }
}
