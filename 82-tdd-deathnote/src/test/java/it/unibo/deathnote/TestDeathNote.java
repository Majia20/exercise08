package it.unibo.deathnote;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.DeathNoteImpl;

class TestDeathNote {

    private static final int ZERO = 0;
    private static final int NEGATIVE = -1;
    private static final List<String> RULES = DeathNote.RULES;
    private static final String NAME = "human";
    private static final String OTHER_NAME = "other human";
    private static final String VOID_NAME = "";
    private static final String CAUSE = "karting accident";
    private static final String DEFAULT_CAUSE = "heart attack";
    private static final String DETAIL = "ran for too long";
    private static final String OTHER_DETAIL = "other information";
    private static final long TIME_61000 = 6100;
    private static final long TIME_100 = 100;
    private DeathNote d;

    @BeforeEach
    void setUp() {
        d = new DeathNoteImpl();
    }

    @Test 
    void zeroOrNegativeRules() {
        try {
            this.d.getRule(ZERO);
        } catch (final IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
        }
        try {
            this.d.getRule(NEGATIVE);
        } catch (final IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
        }
     }

    @Test 
    void nullRules() {
        for (final String rule: RULES) {
            assertNotNull(rule);
            assertFalse(rule.isEmpty());
        }
    }

     @Test 
     void verifyName() {
        if (!d.isNameWritten(NAME)) {
            d.writeName(NAME);
            assertTrue(d.isNameWritten(NAME));
        }
        assertFalse(d.isNameWritten(OTHER_NAME));
        d.writeName(VOID_NAME);
        assertFalse(d.isNameWritten(VOID_NAME));
     }

     @Test 
     void testDeathCause() throws InterruptedException {
        try {
            d.writeDeathCause(CAUSE);
        } catch (final IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
        d.writeName(NAME);
        assertEquals(DEFAULT_CAUSE, d.getDeathCause(NAME));
        d.writeName(OTHER_NAME);
        assertTrue(d.writeDeathCause(CAUSE));
        assertEquals(CAUSE, d.getDeathCause(OTHER_NAME));
        Thread.sleep(TIME_100);
        d.writeDeathCause(DEFAULT_CAUSE);
        assertEquals(CAUSE, d.getDeathCause(OTHER_NAME));
    }

    @Test 
    void testDeathDetail() throws InterruptedException {
        try {
            d.writeDetails(DETAIL);
        } catch (final IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
        d.writeName(NAME);
        assertTrue(d.getDeathDetails(NAME).isEmpty());
        assertTrue(d.writeDetails(DETAIL));
        assertEquals(DETAIL, d.getDeathDetails(NAME));
        d.writeName(OTHER_NAME);
        Thread.sleep(TIME_61000);
        d.writeDetails(OTHER_DETAIL);
        assertEquals("", d.getDeathDetails(OTHER_NAME));
    }

}
