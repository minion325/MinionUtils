package me.minion325.utils.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryRunnable {

    void onReceiveResults(ResultSet resultSet) throws SQLException;

}
