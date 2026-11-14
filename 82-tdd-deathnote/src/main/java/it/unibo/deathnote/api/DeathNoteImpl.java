package it.unibo.deathnote.api;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * DeathNoteImpl.
 * The class implement the interface DeathNote .
 */
public class DeathNoteImpl implements DeathNote {

    private static final int SHIFT = 1;
    private static final int MAX_TIME_FOR_CAUSE = 40;
    private static final int MAX_TIME_FOR_DETAIL = 6040;
    private static final String DEFAULT_CAUSE = "heart attack";
    private final List<Human> writtenHuman;

    /**
     * initialize the arrayList tha will contain the information about each human.
     */
    public DeathNoteImpl() { 
        this.writtenHuman = new ArrayList<>();
    }

    @Override
    public final String getRule(final int ruleNumber) {
        if (ruleNumber < 0 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("no zero or negative");
        }
        return RULES.get(ruleNumber + SHIFT);
    }

    @Override
    public final void writeName(final String name) {
        Objects.requireNonNull(name);
        if (!name.isEmpty()) {
            this.writtenHuman.add(new Human(name));
        }
    }

    @Override
    public final boolean writeDeathCause(final String cause) {
        if (cause == null || this.writtenHuman.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return this.writtenHuman.getLast().addCause(cause);
    }

    @Override
    public final boolean writeDetails(final String details) {
        if (details == null || this.writtenHuman.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return this.writtenHuman.getLast().addDetail(details);
    }

    @Override
    public final String getDeathCause(final String name) {
        for (final Human human : this.writtenHuman) {
            if (human.getName().equals(name)) {
                return human.getCause(name);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public final String getDeathDetails(final String name) {
        for (final Human human : this.writtenHuman) {
            if (human.getName().equals(name)) {
                return human.getDetail(name);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public final boolean isNameWritten(final String name) {
        if (name == null) {
            return false;
        }
        for (final Human human : this.writtenHuman) {
            if (human.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static class Human {
        private final long time;
        private final String name;
        private final Map<String, String> cause;
        private final Map<String, String> detail;

        Human(final String name) {
            this.time = System.currentTimeMillis();
            this.name = name;
            this.cause = new HashMap<>();
            this.cause.put(name, DEFAULT_CAUSE);
            this.detail = new HashMap<>();
            this.detail.put(name, "");
        }

        boolean addCause(final String newCause) {
            final long currentTime = System.currentTimeMillis();
            if (currentTime - this.time > MAX_TIME_FOR_CAUSE) {
                return false;
            } else {
                this.cause.remove(name);
                this.cause.put(name, newCause);
                return true;
            }
        }

        boolean addDetail(final String newDetails) {
            final long currentTime = System.currentTimeMillis();
            if ((currentTime - this.time) > MAX_TIME_FOR_DETAIL) {
                return false;
            } else {
                this.detail.remove(name);
                this.detail.put(name, newDetails);
                return true;
            }
        }

        String getCause(final String newName) {
            return this.cause.get(newName);
        }

        String getDetail(final String newName) {
            return this.detail.get(newName);
        }

        String getName() {
            return this.name;
        }
    }

}
