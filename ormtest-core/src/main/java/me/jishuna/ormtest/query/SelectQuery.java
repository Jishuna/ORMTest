package me.jishuna.ormtest.query;

import me.jishuna.ormtest.column.ColumnData;

public class SelectQuery extends Query {
    private boolean startedWhere;

    protected SelectQuery() {
        this.builder.append("SELECT ");
    }

    public SelectQuery all() {
        this.builder.append("*");
        return this;
    }

    public SelectQuery column(ColumnData column) {
        this.builder.append(column.getName()).append(", ");
        return this;
    }

    public SelectQuery from(String tableName) {
        trim(", ");

        this.builder.append(" FROM " + tableName + " ");
        return this;
    }

    public SelectQuery where(String column) {
        if (this.startedWhere) {
            this.builder.append(" AND " + column + " = ?");
        } else {
            this.builder.append("WHERE " + column + " = ?");
            this.startedWhere = true;
        }
        return this;
    }

    public String build() {
        trim(", ");

        return this.builder.append(";").toString();
    }
}
