package org.guildcraft.minion325.utils.database;

import java.sql.ResultSet;

public interface QueryRunnable {

    void onReceiveResults(ResultSet resultSet);

}
