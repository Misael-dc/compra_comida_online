<%-- 
    Document   : listarPersonal
    Created on : 15 jun. 2021, 11:50:27
    Author     : Desktop
--%>

<%@include file="componentes/header.jsp" %>
<main class="container mt-5 mb-5">
    <jsp:useBean id="personalBean" scope="session" class="com.test.bean.PersonalBean"/>
    <div class="card">
      <div class="card-header">
        <h2>Lista Empleado</h2>
      </div>
      <div class="card-body">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Cedula</th>
                    <th>Nombre</th>
                    <th>Telefono</th>
                    <th>Placa</th>
                </tr>
            </thead>
            <tbody>
                <%=personalBean.listarPersonal()%>
            </tbody>

        </table>
      </div>
    </div>
</main>


<%@include file="componentes/footer.jsp" %>