package me.jishuna.ormtest.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import me.jishuna.ormtest.ConnectionPool;
import me.jishuna.ormtest.column.ColumnData;
import me.jishuna.ormtest.query.CreateQuery;
import me.jishuna.ormtest.query.DeleteQuery;
import me.jishuna.ormtest.query.InsertQuery;
import me.jishuna.ormtest.query.Query;
import me.jishuna.ormtest.query.SelectQuery;
import me.jishuna.ormtest.query.UpdateQuery;
import me.jishuna.ormtest.type.DataType;
import me.jishuna.ormtest.type.DataTypes;

public class Table<T> {
    private final Class<T> clazz;
    private final String name;
    private final ColumnData index;
    private final Map<String, ColumnData> columns;
    private final ConnectionPool pool;

    public Table(String name, ColumnData index, Map<String, ColumnData> columns, Class<T> clazz, ConnectionPool pool) {
        this.name = name;
        this.index = index;
        this.columns = columns;
        this.clazz = clazz;
        this.pool = pool;
    }

    public String getName() {
        return this.name;
    }

    public Map<String, ColumnData> getColumns() {
        return this.columns;
    }

    // TODO delegate all of this depending on backend (And make it less ugly)
    public boolean createTable() {
        CreateQuery query = Query.createTable(this.name).index();
        for (ColumnData column : this.columns.values()) {
            query.column(column);
        }

        String queryString = query.build();
        System.out.println(queryString);

        try (Connection connection = this.pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean insert(T object) {
        InsertQuery query = Query.insert(this.name);
        for (ColumnData column : this.columns.values()) {
            query.column(column);
        }

        String queryString = query.build();
        System.out.println(queryString);

        try (Connection connection = this.pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryString)) {
            int index = 1;
            for (ColumnData column : this.columns.values()) {
                statement.setObject(index++, column.getValue(object));
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.index.setValue(object, resultSet.getObject(this.index.getName()));
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(T object) {
        UpdateQuery query = Query.update(this.name);
        for (ColumnData column : this.columns.values()) {
            query.column(column);
        }

        String queryString = query.where(this.index).build();
        System.out.println(queryString);

        try (Connection connection = this.pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryString)) {

            int index = 1;
            for (ColumnData column : this.columns.values()) {
                statement.setObject(index++, column.getValue(object));
            }
            statement.setObject(index++, this.index.getValue(object));
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean delete(T object) {
        DeleteQuery query = Query.delete(this.name).where(this.index);

        String queryString = query.build();
        System.out.println(queryString);

        try (Connection connection = this.pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryString)) {

            statement.setObject(1, this.index.getValue(object));
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<T> readAll(Map<String, Object> conditions) {
        SelectQuery query = Query.select().all().from(this.name);

        for (String column : conditions.keySet()) {
            query.where(column);
        }

        String queryString = query.build();
        System.out.println(queryString);

        List<T> values = new ArrayList<>();

        try (Connection connection = this.pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryString)) {
            int index = 1;
            for (Object value : conditions.values()) {
                @SuppressWarnings("unchecked")
                DataType<?, ? super Object> type = (DataType<?, ? super Object>) DataTypes.getType(value.getClass());
                if (type != null) {
                    value = type.toSaved(value);
                }

                statement.setObject(index++, value);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T instance = this.clazz.getDeclaredConstructor().newInstance();
                this.index.setValue(instance, resultSet.getObject(this.index.getName()));

                for (ColumnData column : this.columns.values()) {
                    column.setValue(instance, resultSet.getObject(column.getName()));
                }

                values.add(instance);
            }
        } catch (SQLException | ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
        return values;
    }

    public List<T> readAll() {
        return readAll(Collections.emptyMap());
    }
}
