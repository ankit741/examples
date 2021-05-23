package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC transaction Example-1
 */
public class JDBCTransaction {
    private static final String URL = "jdbc:mysql://localhost:3306/db1";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try {
                connection.setAutoCommit(false);
                Statement st = connection.createStatement();
                //operation-1-starts
                st.executeUpdate(
                        "insert into user(user_id,name,password,date) values('3','ankit','$2a$12$CwEEzf51ZE10DjFULErUUeMBPkVSa7dMOODrpbUtJkp61TtY95MMe','2019-04-28 20:15:15')");

                //operation -2 - starts
                Statement st1 = connection.createStatement();
                st1.executeUpdate(
                        "insert into user(user_id,name,password,date) values('4','admin','$2a$12$CwEEzf51ZE10DjFULErUUeMBPkVSa7dMOODrpbUtJkp61TtY95MMe','2019-04-28 20:15:15')");


                //operation -3 - starts - adding duplicate primary key - it will throw an error.

                Statement st2 = connection.createStatement();
                st2.executeUpdate(
                        "insert into user(user_id,name,password,date) values('3','ankit','$2a$12$CwEEzf51ZE10DjFULErUUeMBPkVSa7dMOODrpbUtJkp61TtY95MMe','2019-04-28 20:15:15')");

                // commit all operations
                connection.commit();

            } catch (SQLException ex) {
                //In case of error, transaction will be rolledback.
                if (null != connection) {
                    connection.rollback();
                }
                ex.printStackTrace();
            }

        }
    }
}
