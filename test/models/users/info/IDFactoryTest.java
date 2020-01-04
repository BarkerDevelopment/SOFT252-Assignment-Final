package models.users.info;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class IDFactoryTest {
    private final static int ITERATIONS = 1000;
    private IDFactory _idFactory;

    @BeforeEach
    void setUp() {
        _idFactory = new IDFactory(UserRole.PATIENT);
    }

    @Test
    @DisplayName("create()")
    void create() {
        ArrayList<ID> ids = new ArrayList<>();

        for (int i = 0; i < ITERATIONS; i++) ids.add(_idFactory.create(ids));

        for (ID id : ids){
            ArrayList<ID> duplicateIds = new ArrayList<>(ids.stream().filter(i -> i.getIdNumber().equals(id.getIdNumber())).collect(Collectors.toList()));
            assertEquals(1, duplicateIds.size());
        }
    }
}