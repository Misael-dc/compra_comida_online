<%-- 
    Document   : listaPlatos
    Created on : 31 ago. 2021, 18:30:51
    Author     : Desktop
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@include file="componentes/header.jsp" %>
<main class="container mt-5 mb-5">
    <jsp:useBean id="platosBean" scope="session" class="com.test.bean.PlatosBean"/>
        <%  
            List<ArrayList<String>> listaPlatos = (List<ArrayList<String>>) session.getAttribute("lista");
            double total = 0;
            if (listaPlatos != null) {
                for (ArrayList<String> plato1 : listaPlatos) {
                    total += Double.valueOf(plato1.get(3));
                }
            }
            
            if(request.getParameter("pedir") != null){
                platosBean.RegistrarPedido(request);
                platosBean.RegistrarFactura(listaPlatos);
            }
        %>
    <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
            <h2>Lista Pedido</h2>
            <div> 
                <%
                    if (listaPlatos != null) {
                        out.print("" + listaPlatos.size());   
                    }else{
                        out.print("0");   
                    }
                %>
            </div>
        </div>
        <div class="card-body">
          <table class="table table-striped">
              <thead>
                  <tr>
                      <th>Plato</th>
                      <th>precio</th>
                      <th>cantidad</th>
                      <th>subtotal</th>
                  </tr>
              </thead>
              <tbody>
                  <% for (ArrayList<String> plato : listaPlatos) {
                  %>
                    <tr>
                      <td><% out.print(plato.get(0));%></td>                   
                      <td><% out.print(plato.get(1));%></td>                   
                      <td><% out.print(plato.get(2));%></td>                   
                      <td><% out.print(plato.get(3));%></td>                   
                    </tr>
                  <%  
                      }
                  %>
                  <tr>
                      <td colspan="3">Total:</td>
                    <td><% out.print(total);%></td>
                  </tr>
              </tbody>
          </table>      
        </div>
        <div class="card-footer">
            <form method="POST">
                    <div class="mb-3">
                        <label>Cliente:</label>
                        <select class="form-select" id="inputGroupSelect01" name="idCliente">
                            <% out.print(platosBean.listaClientes()); %>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label>Repartidor:</label>
                        <select class="form-select" id="inputGroupSelect01" name="idRepartidor">
                            <% out.print(platosBean.listaEmpleados()); %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label>Forma de Pago</label>
                        <select class="form-select" id="inputGroupSelect01" name="formaPago">
                            <option value="payPal">payPal</option>
                            <option value="Tarjeta Credito">Tarjeta de credito</option>
                        </select>
                    </div>
                    
    
                    <button class="btn btn-primary" type="submit" name="pedir">Realizar Pedido</button>
                </form>
    
        </div>
    </div>
        
</main>


<%@include file="componentes/footer.jsp" %>