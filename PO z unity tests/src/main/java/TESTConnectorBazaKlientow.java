import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TESTConnectorBazaKlientow {
    //private static final String URL = "jdbc:sqlite:C:/Dokum/PO/bazaKont.db";
    private static final String URL = "jdbc:sqlite:TESTbazaKlientow.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}