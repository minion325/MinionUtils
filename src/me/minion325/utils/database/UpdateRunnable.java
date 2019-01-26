package me.minion325.utils.database;

import java.sql.SQLException;

public interface UpdateRunnable {

    void onUpdate() throws SQLException;

}
