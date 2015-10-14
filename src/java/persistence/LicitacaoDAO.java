package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import model.Licitacao;

/**
 *
 * @author Davy
 */
public class LicitacaoDAO {

    private final Connection connection;

    public LicitacaoDAO() throws DAOException {
        this.connection = ConnectionFactory.getConnection();
    }

    public void search(Licitacao l) throws SQLException {
        PreparedStatement statement;

        String SQL = "SELECT * FROM total_tipolic('%" + l.getDescricao() + "%');";

        statement = connection.prepareStatement(SQL);
        ResultSet rs = statement.executeQuery(); // executes query

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (rs.next()) {
            sb.append("['");
            sb.append(rs.getString(1));
            sb.append("', '");
            sb.append(maskMoneyDois(rs.getString(2)));
            sb.append("'], ");
        }
        sb.append("]");
        String table = sb.toString();
        l.setJson(table);
    }

    private String maskMoney(String value) {
        char[] chValue = value.toCharArray();
        int tam = chValue.length;

        String res = "";

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

        DecimalFormat df = new DecimalFormat("0.0");

        if (v >= 1.0E12) {
            v = v / 1.0E12;
            return "R$ " + df.format(v) + " trilhões";
        } else if (v >= 1.0E9) {
            v = v / 1.0E9;
            return "R$ " + df.format(v) + " bilhões";
        } else if (v >= 1.0E6) {
            v = v / 1.0E6;
            return "R$ " + df.format(v) + " milhões";
        } else if (v >= 99E3) {
            v = v / 1.0E3;
            return "R$ " + df.format(v) + " mil";
        } else {
            return maskMoney(value);
        }
    }
}
