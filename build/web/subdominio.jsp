<%-- 
    Document   : subdominio
    Created on : May 29, 2015, 9:59:07 AM
    Author     : Davy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Subdominio" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <title>Dados Governamentais Abertos</title>
        <meta charset="UTF-8">

        <link rel="icon" href="assets/img/favicon.png" />
        <link rel="shortcut icon" href="assets/img/favicon.png" />

        <!-- CSS -->
        <link rel="stylesheet" type="text/css" href="assets/css/style.css" media="all" />
        <link rel="stylesheet" type="text/css" href="assets/css/jquery.dataTables.min.css" media="all" />
    </head>

    <body>
        <!-- Header -->
        <header class="header_search">
            <nav>
                <ul id="menu">
                    <li><a class="active" href="index.jsp">Home</a></li>
                    <li><a href="about.jsp">Sobre</a></li>
                </ul>
            </nav>
            <form class="form_search" action="SearchSubdominio" method="post">
                <input id="valor2" name="valor" type="text" placeholder="Valor de despesa" autofocus />
                <button id="btn_sub2" type="submit">Buscar</button>
            </form>
        </header>
        <!-- EndHeader -->

        <!-- Content -->
        <section id="table_sub">
            <div>
                <h1>Ranking: Despesas Subdomínio</h1>
            </div>
            <div id="divTable" class="main clearfix">
                <table id="table_result" class="display"></table>
            </div>
        </section>
        <!-- EndContent -->

        <!-- Footer -->
        <footer>
            <p><small>&copy; 2015 Group Two. All rights reserved</small></p>
        </footer>
        <!-- EndFooter -->

        <!-- Scripts -->
        <script type="text/javascript" src="assets/js/jquery-2.1.4.min.js"></script>
        <script type="text/javascript" src="assets/js/jquery.maskMoney.js"></script>
        <script type="text/javascript" src="assets/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="assets/js/jquery-add.js"></script>
        <script>
            // Table
            <% Subdominio s = (Subdominio) request.getAttribute("subdominio_bean"); %>
            var data = <%= s.getJson() %>;
            $('#table_result').DataTable({
                data: data,
                columns: [
                    {title: 'Posição'},
                    {title: 'Descrição'},
                    {title: 'Valor'}
                ]
            });
        </script>
    </body>
</html>
