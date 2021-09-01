<%-- 
    Document   : menuPlatos
    Created on : 30 ago. 2021, 19:40:33
    Author     : Desktop
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@include file="componentes/header.jsp" %>
<main class="container mt-5 mb-5">
    <jsp:useBean id="platosBean" scope="session" class="com.test.bean.PlatosBean"/>
        <%
            List<ArrayList<String>> listaPlatos = (List<ArrayList<String>>) session.getAttribute("lista");
            if(request.getParameter("agregar") != null){

                Double precio = Double.valueOf(request.getParameter("precio"));
                int cantidad = Integer.valueOf(request.getParameter("cantidad"));
                
                Double subtotal = precio * cantidad;            
                ArrayList<String> platoInfo = new ArrayList<String>(); 
                platoInfo.add(request.getParameter("nombre"));
                platoInfo.add(String.valueOf(precio));
                platoInfo.add(String.valueOf(cantidad));
                platoInfo.add(String.valueOf(subtotal));
                platoInfo.add(request.getParameter("id"));

                
                if (listaPlatos == null) {
                    listaPlatos = new ArrayList<ArrayList<String>>();
                }
                listaPlatos.add(platoInfo);
                session.setAttribute("lista", listaPlatos);
            }
            
            
        %>
    <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
            <h1>Listado de Productos Para Pedir</h1>
            <a class="btn btn-success" href="listaPlatos.jsp" >Ver lista 
                <%
                    if (listaPlatos != null) {
                        out.print("" + listaPlatos.size());   
                    }else{
                        out.print("0");   
                    }
                %>
            </a>
        </div>
        <div class="card-body d-flex flex-wrap">
            <%=platosBean.listarPlatos()%>
        </div>
    </div>
        
</main>


<%@include file="componentes/footer.jsp" %>