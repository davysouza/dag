<%-- 
    Document   : subdominio
    Created on : May 29, 2015, 9:59:07 AM
    Author     : Davy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.GastoFuncao" %>
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
                <div style="width:100%">
                    <div>
                        <canvas id="canvas" height="450" width="800"></canvas>
                    </div>
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
        <script src="assets/js/Chart.js"></script>

        <script>
            <% GastoFuncao s = (GastoFuncao) request.getAttribute("gastofuncao_bean");%>
            var d = <%= s.getJson()%>;
            
            var lineChartData = {
                labels: d[2
                ],
                datasets: [
                    {
                        label: "My First dataset",
                        fillColor: "rgba(220,220,220,0.2)",
                        strokeColor: "rgba(220,220,220,1)",
                        pointColor: "rgba(220,220,220,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(220,220,220,1)",
                        data: d[0]
                    },
                    {
                        label: "My Second dataset",
                        fillColor: "rgba(151,187,205,0.2)",
                        strokeColor: "rgba(151,187,205,1)",
                        pointColor: "rgba(151,187,205,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(151,187,205,1)",
                        data: d[1]
                    }
                ]

            }

            window.onload = function () {
                var ctx = document.getElementById("canvas").getContext("2d");
                window.myLine = new Chart(ctx).Line(lineChartData, {
                    responsive: true
                });
            }

        </script>
    </body>
</html>
