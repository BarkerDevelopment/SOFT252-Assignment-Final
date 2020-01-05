package controllers.serialisation.strategies;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultSerialisationStrategyTest {
    private DefaultSerialisationStrategy _strategy = new DefaultSerialisationStrategy();
    private String string = "This is a test string.";
    private String fileName = "testFile";

    @Test
    @DisplayName("serialise()")
    void serialise() {
        assertDoesNotThrow(()-> _strategy.serialise(fileName, string));
    }

    @Test
    @DisplayName("deserialise()")
    void deserialise() {
        assertDoesNotThrow(() -> _strategy.deserialise(fileName));
    }
}