package me.jishuna.ormtest.table;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import me.jishuna.ormtest.ConnectionPool;
import me.jishuna.ormtest.annotation.Column;
import me.jishuna.ormtest.annotation.Index;
import me.jishuna.ormtest.column.ColumnData;
import me.jishuna.ormtest.type.DataTypes;
import me.jishuna.ormtest.type.DataType;

public class TableManager {
    private final Map<Class<?>, Table<?>> tableData = new HashMap<>();
    private final ConnectionPool pool = ConnectionPool.createSQLite("test", new File("test"));

    public <T> Table<T> registerTable(Class<T> tableClass) {
        String name = tableClass.getAnnotation(me.jishuna.ormtest.annotation.Table.class).value();
        ColumnData index = null;
        Map<String, ColumnData> columns = new HashMap<>();

        for (Field field : tableClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Index.class)) {
                index = new ColumnData("id", DataTypes.INTEGER, field);
                continue;
            }

            ColumnData data = readColumn(field);
            if (data != null) {
                columns.put(data.getName(), data);
            }
        }

        Table<T> table = new Table<>(name, index, columns, tableClass, this.pool);
        this.tableData.put(tableClass, table);

        table.createTable();
        return table;
    }

    private ColumnData readColumn(Field field) {
        Column column = field.getDeclaredAnnotation(Column.class);
        if (column == null) {
            return null;
        }

        DataType<?, ?> type = DataTypes.getType(field.getType());
        if (type == null) {
            return null;
        }

        return new ColumnData(column.value(), type, field);
    }
}
