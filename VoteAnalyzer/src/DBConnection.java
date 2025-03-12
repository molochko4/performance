import java.sql.*;

public class DBConnection {

    private static Connection connection;

    private static String dbName = "skillbox";
    private static String dbUser = "root";
    private static String dbPass = "13412315";

    private static StringBuilder insertQuery = new StringBuilder();

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName +
                                "?user=" + dbUser + "&password=" + dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void executeMultiInsert() throws Exception {
        String sql = "INSERT INTO voter_count(name, birthDate, `count`) " +
                "VALUES " + insertQuery.toString() +
                "ON DUPLICATE KEY UPDATE `count`=`count` + 1";
        DBConnection.getConnection().createStatement().execute(sql);
    }

    public static void countVoter(String name, String birthDay) throws Exception {
        birthDay = birthDay.replace('.', '-');

        if(insertQuery.length() > 1_000_000) {
            DBConnection.executeMultiInsert();
            insertQuery = new StringBuilder();
        }

        insertQuery.append((insertQuery.isEmpty() ? "" : ",") +
                "('" + name + "', '" + birthDay + "', 1)");
    }
}
