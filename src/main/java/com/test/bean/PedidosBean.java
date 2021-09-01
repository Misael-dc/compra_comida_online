/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.bean;

import com.test.conexion.VariablesConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Desktop
 */
public class PedidosBean {
   private Connection connection;
    private VariablesConexion variable;
    //constructores
    public PedidosBean()throws SQLException{
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
    
    
    public String listaPedido(HttpServletRequest request){
        String tabla = "";
        String buscar = "";
        if (request != null) {
            buscar = request.getParameter("buscar").toLowerCase();
            System.out.println("aquiiii: " + buscar + " ----");
        }
        StringBuilder salidaTabla = new StringBuilder();
        StringBuilder query = new StringBuilder();
        query.append(" SELECT concat(c.nombres,' ',c.paterno,' ',c.materno ), concat(pe.nombres,' ',pe.paterno,' ',pe.materno), p.forma_pago, p.fecha, p.direccion ");
        query.append(" FROM pedido p");
        query.append(" INNER JOIN cliente c ");
        query.append(" ON c.id = p.id_cliente ");
        query.append(" INNER JOIN personal_entrega pe ");
        query.append(" ON pe.id = p.id_repartidor ");
        if (request != null && buscar != "" ) {
            query.append(" WHERE p.fecha = '" + buscar + "' "); 
        }
        
        try{
            PreparedStatement pst = connection.prepareStatement(query.toString());
            ResultSet resultado = pst.executeQuery();
            
            while(resultado.next()){
                salidaTabla.append("<tr>");
                salidaTabla.append("<td>");
                salidaTabla.append(resultado.getString(1));
                salidaTabla.append("</td>");
                salidaTabla.append("<td>");
                salidaTabla.append(resultado.getString(2));
                salidaTabla.append("</td>");
                salidaTabla.append("<td>");
                salidaTabla.append(resultado.getString(3));
                salidaTabla.append("</td>");
                salidaTabla.append("<td>");
                salidaTabla.append(resultado.getDate(4));
                salidaTabla.append("</td>");
                salidaTabla.append("<td>");
                salidaTabla.append(resultado.getString(5));
                salidaTabla.append("</td>");
                salidaTabla.append("</tr>");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        tabla = salidaTabla.toString();
        if (tabla.equals("")) {
            tabla = "<tr><td colspan='5' align='center'>No se Encontraron los datos!! </td></tr>";
        }
        return tabla; 
    }
    
}
