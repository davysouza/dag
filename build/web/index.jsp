<%-- 
    Document   : index
    Created on : May 28, 2015, 12:10:15 PM
    Author     : Davy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <header>
            <nav>
                <ul id="menu">
                    <li><a class="active" href="index.jsp">Home</a></li>
                    <li><a href="about.jsp">Sobre</a></li>
                </ul>
            </nav>
        </header>
        <!-- EndHeader -->

        <!-- Content -->
        <section id="home">
            <div>
                <h1>Dados Governamentais Abertos</h1>
            </div>
            <div class="main clearfix">
                <div class="column">
                    <p>
                        Exibir ranking de despesas de subdomínio. Digite o valor a partir do qual
                        deve ser exibido:
                    </p>
                    <form method="post" action="SearchSubdominio">
                        <div class="search">
                            <input id="data" name="data" type="text" placeholder="mes/ano" />
                            <select id="funcao" name="funcao">
                                <option value=""></option>
                                <option value="educacao">Educação</option>
                                <option value="saude">Saúde</option>
                                <option value="ciencia">Ciência e Tecnologia</option>
                                <option value="transporte">Transporte</option>
                                <option value="saneamento">Saneamento Básico</option>
                            </select>
                            <button id="btn_sub" type="submit">Buscar</button>
                        </div>
                    </form>
                </div>
                <div class="column">
                    <p>
                        Exibir as despesas totais de cada tipo de licitação. Digite o tipo de licitação
                        desejado:
                    </p>
                    <form method="post" action="SearchLicitacao">
                        <div class="search">
                            <input id="licitacao" name="licitacao" type="text" placeholder="Tipo de Licitação" pattern="[A-Za-z\s]+$" />
                            <button id="btn_lic" type="submit">Buscar</button>
                        </div>
                    </form>
                </div>
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
    </body>
</html>