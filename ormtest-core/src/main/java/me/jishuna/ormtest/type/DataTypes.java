package me.jishuna.ormtest.type;

import com.google.common.primitives.Primitives;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataTypes {
    private static final Map<Class<?>, DataType<?, ?>> TYPES = new HashMap<>();

    public static final DataType<Byte, Byte> BYTE = register(new PrimitiveType<>("TINYINT", Byte.class, Byte.class));
    public static final DataType<Short, Short> SHORT = register(new PrimitiveType<>("SMALLINT", Short.class, Short.class));
    public static final DataType<Integer, Integer> INTEGER = register(new PrimitiveType<>("INTEGER", Integer.class, Integer.class));

    public static final DataType<Long, Long> LONG = register(new PrimitiveType<>("BIGINT", Long.class, Long.class));
    public static final DataType<Float, Float> FLOAT = register(new PrimitiveType<>("FLOAT", Float.class, Float.class));
    public static final DataType<Double, Double> DOUBLE = register(new PrimitiveType<>("DOUBLE", Double.class, Double.class));

    public static final DataType<String, String> STRING = register(new DataType<>("VARCHAR", String.class, String.class));

    public static final DataType<String, UUID> UUID = register(new UUIDType("CHAR(36)"));

    @SuppressWarnings("unchecked")
    public static <R> DataType<?, R> getType(Class<R> runtime) {
        return (DataType<?, R>) TYPES.get(runtime);
    }

    private static <S, R> DataType<S, R> register(DataType<S, R> type) {
        TYPES.put(type.getRuntimeType(), type);

        if (Primitives.isWrapperType(type.getRuntimeType())) {
            TYPES.put(Primitives.unwrap(type.getRuntimeType()), type);
        }

        return type;
    }
}
