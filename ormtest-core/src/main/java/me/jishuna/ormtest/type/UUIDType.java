package me.jishuna.ormtest.type;

import java.util.UUID;

public class UUIDType extends DataType<String, UUID> {

    public UUIDType(String name) {
        super(name, String.class, UUID.class);
    }

    @Override
    public String toSaved(UUID runtime) {
        return runtime.toString();
    }

    @Override
    public UUID toRuntime(String saved) {
        return UUID.fromString(saved);
    }

}
