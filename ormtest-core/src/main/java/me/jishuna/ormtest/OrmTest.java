package me.jishuna.ormtest;

import java.util.Map;
import java.util.UUID;
import me.jishuna.ormtest.table.Table;
import me.jishuna.ormtest.table.TableManager;

public class OrmTest {

    public static void main(String[] args) {
        TableManager manager = new TableManager();

        System.out.println("===== Create =====");
        Table<Home> table = manager.registerTable(Home.class);
        UUID id = UUID.randomUUID();

        Home home = new Home(id, "Test Home", 100, 64, -250);

        System.out.println("\n===== Insert =====");
        table.insert(home);
        table.insert(new Home(UUID.randomUUID(), "Dummy Home", 0, 10, 0));
        table.insert(new Home(UUID.randomUUID(), "Dummy Home 2", 0, 15, 0));

        System.out.println("\n===== Read All =====");
        table.readAll().forEach(System.out::println);

        System.out.println("\n===== Read Single =====");
        table.readAll(Map.of("owner", id)).forEach(System.out::println);

        home.setName("Updated Name");
        System.out.println("\n===== Update =====");
        table.update(home);

        System.out.println("\n===== Read Single (Update) =====");
        table.readAll(Map.of("owner", id)).forEach(System.out::println);

        System.out.println("\n===== Delete =====");
        table.delete(home);

        System.out.println("\n===== Read All (Delete) =====");
        table.readAll().forEach(System.out::println);
    }
}
