package me.jishuna.ormtest.query;

import me.jishuna.ormtest.column.ColumnData;

public class InsertQuery extends Query {
    private int columns;

    protected InsertQuery(String name) {
        this.builder.append("INSERT OR IGNORE INTO " + name + "(");
    }

    public InsertQuery column(ColumnData column) {
        this.builder.append(column.getName()).append(",");
        this.columns++;
        return this;
    }

    public String build() {
        trim(", ");

        this.builder.append(") VALUES (");
        this.builder.append("?,".repeat(this.columns));
        trim(", ");

        return this.builder.append(") RETURNING id;").toString();
    }
}
