package me.jishuna.ormtest.column;

import java.lang.reflect.Field;
import me.jishuna.ormtest.type.DataType;

public class ColumnData {
    private final String name;
    private final DataType<? super Object, ? super Object> type;
    private final Field field;

    @SuppressWarnings("unchecked")
    public ColumnData(String name, DataType<?, ?> type, Field field) {
        this.name = name;
        this.type = (DataType<? super Object, ? super Object>) type;
        this.field = field;

        field.trySetAccessible();
    }

    public String getName() {
        return this.name;
    }

    public DataType<? super Object, ? super Object> getType() {
        return this.type;
    }

    public Object getValue(Object obj) {
        try {
            return this.type.toSaved(this.field.get(obj));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setValue(Object target, Object value) {
        try {
            this.field.set(target, this.type.toRuntime(value));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
