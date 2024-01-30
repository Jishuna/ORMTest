package me.jishuna.ormtest.query;

import me.jishuna.ormtest.column.ColumnData;

public class UpdateQuery extends Query {
    private boolean startedWhere;
    private boolean startedSet;

    protected UpdateQuery(String tableName) {
        this.builder.append("UPDATE " + tableName + " ");
    }

    public UpdateQuery column(ColumnData column) {
        if (this.startedSet) {
            this.builder.append(column.getName()).append(" = ?, ");
        } else {
            this.builder.append("SET ").append(column.getName()).append(" = ?, ");
            this.startedSet = true;
        }
        return this;
    }

    public UpdateQuery where(ColumnData column) {
        return where(column.getName());
    }

    public UpdateQuery where(String column) {
        if (this.startedWhere) {
            this.builder.append(" AND " + column + " = ?");
        } else {
            trim(", ");
            this.builder.append(" WHERE " + column + " = ?");
            this.startedWhere = true;
        }
        return this;
    }

    public String build() {
        trim(", ");

        return this.builder.append(";").toString();
    }
}
