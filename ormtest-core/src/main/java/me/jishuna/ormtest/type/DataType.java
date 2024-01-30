package me.jishuna.ormtest.type;

public class DataType<S, R> {
    private final String name;
    private final Class<S> savedType;
    private final Class<R> runtimeType;

    public DataType(String name, Class<S> savedType, Class<R> runtimeType) {
        this.name = name;
        this.savedType = savedType;
        this.runtimeType = runtimeType;
    }

    public S toSaved(R runtime) {
        return this.savedType.cast(runtime);
    }

    public R toRuntime(S saved) {
        return this.runtimeType.cast(saved);
    }

    public String getName() {
        return this.name;
    }

    public Class<S> getSavedType() {
        return this.savedType;
    }

    public Class<R> getRuntimeType() {
        return this.runtimeType;
    }
}
