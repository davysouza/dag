package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.GastoFuncao;
import persistence.DAOException;
import persistence.GastoFuncaoDAO;

/**
 *
 * @author Davy
 */
public class SearchGastoFuncao extends HttpServlet {
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        GastoFuncao s = new GastoFuncao();

        String data = request.getParameter("data");
        String funcao = request.getParameter("funcao");
        if (data.equals("")) {
            s.setData("");
        } else {
            data = data.substring(3) + "-" + data.substring(0,1) + "-01";
            s.setData(data);
        }
        s.setFuncao(funcao);
        try {
            GastoFuncaoDAO sdao = new GastoFuncaoDAO();

            sdao.search2(s);
            request.setAttribute("gastofuncao_bean", s);
            RequestDispatcher dispatcher;
            dispatcher = request.getRequestDispatcher("/gastofuncao.jsp");
            dispatcher.forward(request, response);

        } catch (DAOException | SQLException exception) {
            Logger.getLogger(SearchGastoFuncao.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
