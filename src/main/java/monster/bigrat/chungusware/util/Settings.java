package monster.bigrat.chungusware.util;

import java.util.HashMap;

public class Settings {
    private final HashMap<String, Object> settings;

    public Settings() {
        this.settings = new HashMap<>();
    }

    public static String getDatatypeSymbol(Object n) {
        if (n instanceof Integer) {
            return "[i]";
        } else if (n instanceof String) {
            return "[S]";
        } else if (n instanceof Float) {
            return "[f]";
        } else if (n instanceof Boolean) {
            return "[!]";
        } else return "[O]";
    }

    public void add(String k, Object o) {
        settings.putIfAbsent(k, o);
    }

    public Object get(String k) {
        return settings.get(k);
    }

    public void set(String k, Object o) {
        settings.put(k, o);
    }

    // returns false if null
    public boolean getBool(String k) {
        return settings.get(k) instanceof Boolean && (boolean) settings.get(k);
    }

    //return 0 if null
    public int getInt(String k) {
        return settings.get(k) instanceof Integer ? (int) settings.get(k) : 0;
    }

    @Override
    public String toString() {
        return String.join(", ", this.settings.keySet().stream().map(m -> m + getDatatypeSymbol(this.settings.get(m))).toArray(String[]::new));
    }
}
