<%-- 
    Document   : registrarPersonal
    Created on : 15 jun. 2021, 11:50:00
    Author     : Desktop
--%>
<%@include file="componentes/header.jsp" %>
<main class="container mt-5 mb-5">
    <jsp:useBean id="personal" scope="session" class="com.test.bean.PersonalBean"/>
        <%
            if(request.getParameter("guardar") != null){
                String mensaje = personal.registrarPersonal(request);
                out.print(mensaje);
            }
        %>
    <div class="card">
      <div class="card-header">
        <h2>Registro de Personal</h2>
      </div>
      <div class="card-body">
        <form method="POST">
          <div class="mb-3">
            <label  class="form-label">Nombre(s):</label>
            <input type="text" class="form-control" aria-describedby="emailHelp" name="nombre">
          </div>
          <div class="mb-3">
            <label  class="form-label">Apellido(s):</label>
            <input type="text" class="form-control" aria-describedby="emailHelp" name="apellido">
          </div>
          <div class="mb-3">
            <label  class="form-label">CI:</label>
            <input type="text" class="form-control" aria-describedby="emailHelp" name="ci">
          </div>
          <div class="mb-3">
            <label  class="form-label">Telefono:</label>
            <input type="number" class="form-control" aria-describedby="emailHelp" name="telefono">
          </div>
          <div class="mb-3">
            <label  class="form-label">Placa Vehículo:</label>
            <input type="text" class="form-control" aria-describedby="emailHelp" name="placa">
          </div>
          <button type="submit" class="btn btn-primary" name="guardar">Guardar</button>
        </form>
      </div>
    </div>
</main>

<%@include file="componentes/footer.jsp" %>