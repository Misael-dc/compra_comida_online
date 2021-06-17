<%-- 
    Document   : registrarCliente
    Created on : 15 jun. 2021, 11:50:16
    Author     : Desktop
--%>

<%@include file="componentes/header.jsp" %>
<main class="container mt-5 mb-5">
    <jsp:useBean id="cliente" scope="session" class="com.test.bean.ClienteBean"/>
        <%
            if(request.getParameter("guardar") != null){
                String mensaje = cliente.registrarCliente(request);
                out.print(mensaje);
            }
        %>
    <div class="card">
      <div class="card-header">
        <h2>Registro Cliente</h2>
      </div>
      <div class="card-body">
        <form>
            <div class="mb-3">
              <label for="exampleInputEmail1" class="form-label">Nombre(s):</label>
              <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="nombre">
            </div>
            <div class="mb-3">
              <label for="exampleInputEmail1" class="form-label">Paterno:</label>
              <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="paterno">
            </div>
            <div class="mb-3">
              <label for="exampleInputEmail1" class="form-label">Materno:</label>
              <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="materno">
            </div>
            <div class="mb-3">
              <label for="exampleInputEmail1" class="form-label">Correo:</label>
              <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="correo">
            </div>
            <div class="mb-3">
              <label for="exampleInputEmail1" class="form-label">Contraseña:</label>
              <input type="password" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="pasword">
            </div>
            <div class="mb-3">
              <label for="exampleInputEmail1" class="form-label">Telefono:</label>
              <input type="number" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="telefono">
            </div>
            <button type="submit" class="btn btn-primary" name="guardar">Guardar</button>
          </form>
      </div>
    </div>
</main>
<%@include file="componentes/footer.jsp" %>
