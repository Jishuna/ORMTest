package me.jishuna.ormtest.type;

public class PrimitiveType<S, R> extends DataType<S, R> {

    public PrimitiveType(String name, Class<S> savedType, Class<R> runtimeType) {
        super(name, savedType, runtimeType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public R toRuntime(S saved) {
        if (saved == null) {
            return (R) Byte.valueOf((byte) 0);
        }

        return super.toRuntime(saved);
    }
}
