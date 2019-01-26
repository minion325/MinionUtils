package me.minion325.utils.database;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.minion325.utils.other.Verify;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private Connection connection;
    private Plugin plugin;
    private Map<String, PreparedStatement> statements = new HashMap<>();
    private String host, database, username;
    private int port;

    public Database(Plugin plugin, String host, int port, String database, String username, String password) {
        this.plugin = plugin;
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        if (!Verify.isNotNull(host, port, database, username, password))
            throw new NullPointerException("An argument passed to the constructor was found to be null");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String update, UpdateRunnable runnable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Database.this.connection.createStatement().executeUpdate(update);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            try {
                                runnable.onUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }.runTask(Database.this.plugin);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(this.plugin);
    }

    public void executeUpdate(String update) {
        executeUpdate(update, () ->{});
    }

    public void executeQuery(String query, QueryRunnable runnable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    ResultSet resultSet = Database.this.connection.createStatement().executeQuery(query);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            try {
                                runnable.onReceiveResults(resultSet);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }.runTask(Database.this.plugin);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(this.plugin);
    }

    public void executeSyncQuery(String sql, QueryRunnable runnable) {
        try {
            ResultSet resultSet = Database.this.connection.createStatement().executeQuery(sql);
            runnable.onReceiveResults(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeSyncUpdate(String sql) {
        try {
            Database.this.connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public int getPort() {
        return port;
    }

    public void addPreparedStatment(String name, String sql) {
        this.statements.put(name, new PreparedStatement(sql));
    }

    public PreparedStatement getPreparedStatement(String name) {
        PreparedStatement statement = this.statements.get(name);
        if (statement != null)
            return statement;
        throw new NullPointerException("No statement could be found with that name");
    }

    public void removePreparedStatement(String name) {
        this.statements.remove(name);
    }

    public class PreparedStatement {

        private java.sql.PreparedStatement statement;

        private PreparedStatement(String sql) {
            try {
                this.statement = Database.this.connection.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void executeUpdate(UpdateRunnable runnable) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        PreparedStatement.this.statement.executeUpdate();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                try {
                                    runnable.onUpdate();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.runTask(Database.this.plugin);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(Database.this.plugin);
        }

        public void executeQuery(QueryRunnable runnable) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        ResultSet resultSet = PreparedStatement.this.statement.executeQuery();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                try {
                                    runnable.onReceiveResults(resultSet);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.runTask(Database.this.plugin);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(Database.this.plugin);
        }

    }
}
