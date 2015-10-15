package persistence;

import model.Subdominio;
import java.sql.*;
import java.text.DecimalFormat;

/**
 *
 * @author davy
 */
public class SubdominioDAO {

    private final Connection connection;

    public SubdominioDAO() throws DAOException, SQLException {
        this.connection = ConnectionFactory.getConnection();
        
        PreparedStatement stmt; 
        stmt = this.connection.prepareStatement("CREATE TEMPORARY TABLE despxsub(descricao, valor) "
                + "AS SELECT descricao, valor FROM despesa, subdominio "
                + "WHERE despesa.codigosubdominio = subdominio.codigo;");
        
        stmt.executeUpdate();
    }

    public void search(Subdominio s) throws SQLException {
        PreparedStatement statement;

        String SQL = "SELECT * FROM subdominio(" + s.getValor() + ");";

        statement = connection.prepareStatement(SQL);
        ResultSet rs = statement.executeQuery(); // executes query

        StringBuilder sb = new StringBuilder();
        long pos = 1;
        sb.append("[");
        while (rs.next()) {
            sb.append("[");
            sb.append(pos);
            sb.append(", '");
            sb.append(rs.getString(1));
            sb.append("', '");
            sb.append(maskMoney(rs.getString(2)));
            sb.append("'], ");
            pos++;
        }
        sb.append("]");
        String table = sb.toString();
        s.setJson(table);
    }
    
    public void search2(Subdominio s) throws SQLException {
        
        
    }

    private String maskMoney(String value) {
        char[] chValue = value.toCharArray();
        int tam = chValue.length;

        String res;

        if (tam == 1 || tam == 2) {
            res = value + ",00";
        } else if (chValue[tam - 2] == '.' || chValue[tam - 3] == '.') {
            if (chValue[tam - 2] == '.') {
                res = "," + chValue[tam - 1] + "0";
                int j = 0;
                for (int i = tam - 3; i >= 0; i--) {
                    if (j == 3) {
                        res = '.' + res;
                        j = 0;
                    }
                    res = chValue[i] + res;
                    j++;
                }
            } else {
                res = "," + chValue[tam - 2] + chValue[tam - 1];
                int j = 0;
                for (int i = tam - 4; i >= 0; i--) {
                    if (j == 3) {
                        res = '.' + res;
                        j = 0;
                    }
                    res = chValue[i] + res;
                    j++;
                }
            }
        } else {
            res = ",00";
            int j = 0;
            for (int i = tam - 1; i >= 0; i--) {
                if (j == 3) {
                    res = '.' + res;
                    j = 0;
                }
                res = chValue[i] + res;
                j++;
            }
        }

        return "R$ " + res;
    }

    private String maskMoneyDois(String value) {
        double v = Double.parseDouble(value);

        DecimalFormat df = new DecimalFormat("0.000");

        if (v >= 1.0E12) {
            v = v / 1.0E12;
            return "R$ " + df.format(v) + " tri";
        } else if (v >= 1.0E9) {
            v = v / 1.0E9;
            return "R$ " + df.format(v) + " bi";
        } else if (v >= 1.0E6) {
            v = v / 1.0E6;
            return "R$ " + df.format(v) + " mi";
        } else {
            return maskMoney(value);
        }
    }
}
