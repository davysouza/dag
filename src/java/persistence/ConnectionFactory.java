package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davy
 */
public class ConnectionFactory {

    public static Connection getConnection() throws DAOException {
        try {
            Class.forName("org.postgresql.Driver").newInstance();

            String connection = "jdbc:postgresql://localhost/DAG projeto";
            String user = "postgres";
            String password = "25041993";

            Connection conn = DriverManager.getConnection(connection, user, password);            
            return conn;

        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception.fillInStackTrace());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs)
            throws DAOException {
        close(conn, ps, rs);
    }

    public static void closeConnection(Connection conn, PreparedStatement ps)
            throws DAOException {
        close(conn, ps, null);
    }

    public static void closeConnection(Connection conn)
            throws DAOException {
        close(conn, null, null);
    }

    private static void close(Connection conn, PreparedStatement ps, ResultSet rs)
            throws DAOException {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception.fillInStackTrace());
        }
    }
}
