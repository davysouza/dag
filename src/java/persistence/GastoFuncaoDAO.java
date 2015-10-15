package persistence;

import model.GastoFuncao;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author davy
 */
public class GastoFuncaoDAO {

    private final Connection connection;

    public GastoFuncaoDAO() throws DAOException, SQLException {
        this.connection = ConnectionFactory.getConnection();
    }

    public void search(GastoFuncao s) throws SQLException {
        PreparedStatement statement;

        String SQL = "SELECT sum(valor_pago_acumulado), funcao FROM DAG "
                + "WHERE mes_ano BETWEEN '2015-01-01' AND '2015-07-01' AND cidade = 'Campinas' "
                + "GROUP BY funcao;";

        statement = connection.prepareStatement(SQL);
        ResultSet rs = statement.executeQuery(); // executes query

        StringBuilder valor = new StringBuilder();
        StringBuilder label = new StringBuilder();

        label.append("['");
        valor.append("['");
        int cont = 0;
        while (rs.next()) {
            if (cont > 0) {
                valor.append("', '");
                label.append("', '");
            }
//            float val = rs.getFloat(1);
//            valor.append(val).append("");
            valor.append(rs.getString(1));
            label.append(rs.getString(2));
            cont++;
        }
        valor.append("']");
        label.append("']");
        String v = valor.toString();
        String l = label.toString();

        ArrayList<String> al = new ArrayList();
        al.add(v);
        al.add(l);
        s.setJson(al);
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
