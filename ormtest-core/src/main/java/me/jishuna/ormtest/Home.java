package me.jishuna.ormtest;

import java.util.UUID;
import me.jishuna.ormtest.annotation.Column;
import me.jishuna.ormtest.annotation.Index;
import me.jishuna.ormtest.annotation.Table;

@Table("homes")
public class Home {

    @Index
    private int id;
    @Column("owner")
    private UUID owner;
    @Column("name")
    private String name;
    @Column("x")
    private double x;
    @Column("y")
    private double y;
    @Column("z")
    private double z;

    public Home() {
        // No args constructor for reflection
    }

    public Home(UUID owner, String name, double x, double y, double z) {
        this.owner = owner;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Home [id=" + this.id + ", owner=" + this.owner + ", name=" + this.name + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
    }
}
