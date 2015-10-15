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
        String cidade = "Campinas";
        String inicio = "2015-01-01";
        String fim = "2015-07-01";
        String SQL = "SELECT sum(valor_pago_acumulado), funcao FROM DAG "
                + "WHERE mes_ano BETWEEN '" + inicio + "' AND '" + fim + "' AND cidade = '" + cidade + "' "
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
            valor.append(rs.getFloat(1)/1000000);
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

    public void search2(GastoFuncao s) throws SQLException {
        PreparedStatement statement;
        String cidade_a = "Campinas";
        String cidade_b = "São José dos Campos";
        String inicio = "2015-01-01";
        String fim = "2015-07-01";
        String funcao = "SAÚDE"; //deve ser caixa alta, senão shablau!!!
        String SQL_Campinas = "select sum(valor_pago_acumulado), cidade, funcao, mes_ano from DAG "
                + "where cidade = '" + cidade_a + "' and funcao = '" + funcao + "' and mes_ano between '" + inicio + "' and '" + fim + "' group by mes_ano,cidade,funcao order by mes_ano;";
        String SQL_Sao_Jose = "select sum(valor_pago_acumulado), cidade, funcao, mes_ano from DAG "
                + "where cidade = '" + cidade_b + "' and funcao = '" + funcao + "' and mes_ano between '" + inicio + "' and '" + fim + "' group by mes_ano,cidade,funcao order by mes_ano;";
        statement = connection.prepareStatement(SQL_Campinas);
        ResultSet rs = statement.executeQuery(); // executes query

        StringBuilder valorcampinas = new StringBuilder();
        StringBuilder valorsaojose = new StringBuilder();
        StringBuilder label = new StringBuilder();
        label.append("['");
        valorcampinas.append("['");
        valorsaojose.append("['");

        int flag = 0;
        while (rs.next()) {
            if (flag == 1) {
                valorcampinas.append("', '");
                label.append("', '");
            }
            valorcampinas.append(rs.getFloat(1)/1000000);
            label.append(rs.getString(4));
            flag = 1;
        }

        statement = connection.prepareStatement(SQL_Sao_Jose);
        rs = statement.executeQuery();
        flag = 0;
        while (rs.next()) {
            if (flag == 1) {
                valorsaojose.append("', '");
            }
            valorsaojose.append(rs.getFloat(1)/1000000);
            flag = 1;
        }

        valorcampinas.append("']");
        valorsaojose.append("']");
        label.append("']");

        String vcampinas = valorcampinas.toString();
        String vsaojose = valorsaojose.toString();
        String l = label.toString();

        ArrayList<String> al = new ArrayList();
        al.add(vcampinas);
        al.add(vsaojose);
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

        return res.replaceAll("\\.", "").replaceAll(",", ".");
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
