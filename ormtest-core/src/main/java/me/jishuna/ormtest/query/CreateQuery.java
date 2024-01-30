package me.jishuna.ormtest.query;

import me.jishuna.ormtest.column.ColumnData;

public class CreateQuery extends Query {

    protected CreateQuery(String name) {
        this.builder.append("CREATE TABLE IF NOT EXISTS " + name + " (");
    }

    public CreateQuery index() {
        this.builder.append("id INTEGER PRIMARY KEY, ");
        return this;
    }

    public CreateQuery column(ColumnData column) {
        String name = column.getName();
        String type = column.getType().getName();

        this.builder.append(name).append(" ").append(type).append(", ");
        return this;
    }

    public String build() {
        trim(", ");

        return this.builder.append(");").toString();
    }
}
