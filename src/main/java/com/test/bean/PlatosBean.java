/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.bean;

import com.test.conexion.VariablesConexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Desktop
 */
public class PlatosBean {
    private Connection connection;
    private PreparedStatement insertPersonal;
    private VariablesConexion variable;
    //constructores
    public PlatosBean()throws SQLException{
        //instanciando
        variable=new VariablesConexion();
        variable.inicioConexion();
        //obteniendo la conexion
        connection=variable.getConnection();
        System.out.println("Iniciando la conexion");
    }
   
    //metodos
     @PreDestroy
    public void cerrarConexion(){
        variable.cerrarConexion();
    }
    
    public String listarPlatos(){
        StringBuilder salidaTabla = new StringBuilder();
        StringBuilder query = new StringBuilder();
        
        query.append(" SELECT p.id, p.nom_plato, p.tipo, p.precio, p.descripcion");
        query.append(" FROM plato p ");
        try {
            PreparedStatement pst = connection.prepareStatement(query.toString());
            ResultSet resultado = pst.executeQuery();
            
            while(resultado.next()){
                salidaTabla.append("<div class=\"card border-secondary mb-3 mx-2\" style=\"max-width: 18rem; min-width: 12rem;\">");
                
                salidaTabla.append("<div class=\"card-header\">");
                salidaTabla.append(resultado.getString(2));
                salidaTabla.append("</div>");
                
                    salidaTabla.append("<div class=\"card-body text-secondary\">");
                       
                        salidaTabla.append("<p class=\"card-text\">Descripción: ");
                        salidaTabla.append(resultado.getString(5));
                        salidaTabla.append("</p>");
                        
                        salidaTabla.append("<p class=\"card-title\">Precio: ");
                        salidaTabla.append(resultado.getString(4));
                        salidaTabla.append(" Bs </p>");
                        
                        salidaTabla.append("<form method=\"post\">");
                            salidaTabla.append("<p class=\"card-text\">\n" +
"                                Cantidad: \n" +
"                                <input type=\"number\" name=\"cantidad\"  value=\"1\" min=\"1\" max=\"100\" style=\"height: 25px; width: 40px; text-align:center\">\n" +
"                            </p>");
                            salidaTabla.append("<input type=\"hidden\" name=\"precio\" value='"+ resultado.getDouble(4)+"'>");
                            
                            salidaTabla.append("<input type=\"hidden\" name=\"nombre\" value='"+ resultado.getString(2)+"'>");
                            
                            salidaTabla.append("<input type=\"hidden\" name=\"id\" value='"+resultado.getInt(1)+"'>");
                            salidaTabla.append("<button type=\"submit\" class=\"btn btn-success\" name=\"agregar\">Agregar</button>"); 
                        salidaTabla.append("</form>");
                        
                    salidaTabla.append("</div>");
                
                salidaTabla.append("</div>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error de conexion");
        }
        return salidaTabla.toString();
    }
    
    
    
    public void RegistrarPedido(HttpServletRequest request) throws ParseException{
        PreparedStatement insertPedido = null;
        if(connection != null){
            try {
                //definiendo la consulta
                LocalDate fecha = LocalDate.of(2020, Month.OCTOBER, 25);
                
                
                StringBuilder query = new StringBuilder();
                query.append(" insert into pedido ");
                query.append(" values (nextval('secuencia_2'),?,?,?, '"+ fecha.toString()+"',?)");
                
                //enviando la consulta
                if(insertPedido == null){
                    insertPedido = connection.prepareStatement(query.toString());
                }
                
                //rescatando los parametros del formulario jsp registrarCategoria
                String idCliente = request.getParameter("idCliente");
                String idRepartidor = request.getParameter("idRepartidor");
                String formaPago = request.getParameter("formaPago");
                String direccion = request.getParameter("direccion");

                //pasando los datos a los parametros de la consulta
                insertPedido.setInt(1, Integer.valueOf(idCliente));
                insertPedido.setInt(2, Integer.valueOf(idRepartidor));
                insertPedido.setString(3, formaPago);        
                insertPedido.setString(4, direccion);         
                
                //ejecutando la consulta
                int registro = insertPedido.executeUpdate();
                
                if(registro == 1){
                    String mensaje="Registro realizado con exito";
                }else{
                    String mensaje="Error al insertar el registro";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    } 
    public String RegistrarFactura(List<ArrayList<String>> lista){
        
        if(connection != null){
            try {
                //definiendo la consulta
                StringBuilder query = new StringBuilder();
                
                query.append(" SELECT * FROM pedido ORDER BY id DESC LIMIT 1;");
                PreparedStatement pst = connection.prepareStatement(query.toString());
                ResultSet resultado = pst.executeQuery();
                
                int idPedido = 0;
                while(resultado.next()){
                    idPedido = resultado.getInt(1);
                }
                
                for (ArrayList<String> datos : lista) {   
                    PreparedStatement insertFactura = null;
                    
                    StringBuilder query2 = new StringBuilder();
                    query2.append(" insert into factura ");
                    query2.append(" values ( ?,?,?,?,? )");
                       
                    int cantidad = Integer.valueOf(datos.get(2));
                    double total = Double.valueOf(datos.get(3));
                    int idPlato = Integer.valueOf(datos.get(4));
                    
                    double descuento = 0;

                    //enviando la consulta
                    
                    insertFactura = connection.prepareStatement(query2.toString());

                    
                    insertFactura.setInt(1, idPedido);
                    insertFactura.setInt(2, idPlato);
                    insertFactura.setDouble(3, descuento);
                    insertFactura.setDouble(4, total);
                    insertFactura.setInt(5, cantidad);
                    


                    //ejecutando la consulta
                    int registro = insertFactura.executeUpdate();

                    if(registro == 1){
                        System.out.println("Registrado");
                    }else{
                        System.out.println("No se ha Registrado");
                    }
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return "Pedido Realizado";
    } 
    
    
    public String listaClientes(){
        StringBuilder salidaTabla = new StringBuilder();
        StringBuilder query = new StringBuilder();
        
        query.append(" select c.id, concat(c.nombres,' ',c.paterno,' ',c.materno )");
        query.append(" from cliente c ");
        
        try {
            PreparedStatement pst = connection.prepareStatement(query.toString());
            ResultSet resultado = pst.executeQuery();
            while(resultado.next()){
                salidaTabla.append("<option value='" + resultado.getInt(1)+ "'> " + resultado.getString(2)+ "</option>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error de conexion");
        }
        return salidaTabla.toString();
    }
    
    public String listaEmpleados(){
        StringBuilder salidaTabla = new StringBuilder();
        StringBuilder query = new StringBuilder();
        
        query.append(" select p.id, concat(p.nombres,' ',p.paterno,' ',p.materno )");
        query.append(" from personal_entrega p ");
        
        try {
            PreparedStatement pst = connection.prepareStatement(query.toString());
            ResultSet resultado = pst.executeQuery();
            while(resultado.next()){
                salidaTabla.append("<option value='" + resultado.getInt(1)+ "'> " + resultado.getString(2)+ "</option>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error de conexion");
        }
        return salidaTabla.toString();
    }
}
