package me.jishuna.ormtest.query;

import me.jishuna.ormtest.column.ColumnData;

public class DeleteQuery extends Query {
    private boolean startedWhere;

    protected DeleteQuery(String tableName) {
        this.builder.append("DELETE FROM " + tableName + " ");
    }

    public DeleteQuery where(ColumnData column) {
        return where(column.getName());
    }

    public DeleteQuery where(String column) {
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
