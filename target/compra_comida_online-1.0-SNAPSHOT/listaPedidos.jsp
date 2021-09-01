<%-- 
    Document   : listaPedidos
    Created on : 1 sept. 2021, 17:59:30
    Author     : Desktop
--%>

<%@page import="java.util.List"%>
<%@include file="componentes/header.jsp" %>
<main class="container mt-5 mb-5">
    <jsp:useBean id="pedidosBean" scope="session" class="com.test.bean.PedidosBean"/>
      
    <div class="card">
      <div class="card-header">
        <h2>Lista Empleado</h2>
      </div>
      <div class="card-body">
        <form class="d-flex justify-content-between align-items-center" >
            <label for="formFileSm" class="form-label">Fecha: </label>
            <input class="form-control me-2" type="date" placeholder="Search" aria-label="Search" name="buscar" style="width:80%">
            <button class="btn btn-outline-success" type="submit" name="btnBuscar">Buscar</button>
         </form>
       </div>  
      <div class="card-body">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Cliente</th>
                    <th>Repartidor</th>
                    <th>Forma Pago</th>
                    <th>Fecha</th>
                    <th>Direccion</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (request.getParameter("btnBuscar") != null) {
                        String datos = pedidosBean.listaPedido(request); 
                        out.print(datos);
                    }else{
                        out.print( pedidosBean.listaPedido(null) );  
                    }  
                %>
            </tbody>

        </table>
      </div>
    </div>
</main>


<%@include file="componentes/footer.jsp" %>
