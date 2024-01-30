package me.jishuna.ormtest.query;

import java.util.HashSet;
import java.util.Set;

// Bootleg version of JOOQ
public class Query {
    protected final StringBuilder builder = new StringBuilder();

    public static CreateQuery createTable(String tableName) {
        return new CreateQuery(tableName);
    }

    public static InsertQuery insert(String tableName) {
        return new InsertQuery(tableName);
    }

    public static SelectQuery select() {
        return new SelectQuery();
    }

    public static UpdateQuery update(String tableName) {
        return new UpdateQuery(tableName);
    }

    public static DeleteQuery delete(String tableName) {
        return new DeleteQuery(tableName);
    }

    protected void trim(String chars) {
        trim(chars.toCharArray());
    }

    protected void trim(char[] chars) {
        Set<Character> toRemove = new HashSet<>();
        for (char ch : chars) {
            toRemove.add(ch);
        }

        while (toRemove.contains(this.builder.charAt(this.builder.length() - 1))) {
            this.builder.deleteCharAt(this.builder.length() - 1);
        }
    }
}
