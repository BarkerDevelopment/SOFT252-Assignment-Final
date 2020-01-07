package controllers.serialisation.strategies;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultSerialisationStrategyTest {
    private DefaultSerialisationStrategy _strategy = new DefaultSerialisationStrategy();
    private String _fileLocation = "test-resources";
    private String _fileName = "testFile";
    private String _string = "This is a test string.";

    @Test
    @DisplayName("serialise()")
    void serialise() {
        assertDoesNotThrow(()-> _strategy.serialise(_fileLocation, _fileName, _string));
    }

    @Test
    @DisplayName("deserialise()")
    void deserialise() {
        assertDoesNotThrow(() -> _strategy.deserialise(_fileLocation, _fileName));
    }
}