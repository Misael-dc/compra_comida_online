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
 * @author Misael
 */
public class ClienteBean {
    //atributos
    private Connection connection;
    private PreparedStatement insertCliente;
    private PreparedStatement insertUsuario;
    private VariablesConexion variable;
    //constructores
    public ClienteBean()throws SQLException{
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
    public String registrarCliente(HttpServletRequest request){
        String mensaje="";
        if(request == null){
            return"vacio!!";
        }
        if(connection != null){
            try {
                //definiendo la consulta
                StringBuilder query = new StringBuilder();
                StringBuilder queryU = new StringBuilder();
                
                query.append(" insert into cliente ");
                query.append(" values (nextval('secuencia_1'),?,?,?,?)");
                
                queryU.append(" insert into usuario ");
                queryU.append(" values (nextval('secuencia_5'),?,?,?)");

                if(insertCliente == null){
                    insertCliente = connection.prepareStatement(query.toString());
                    insertUsuario = connection.prepareStatement(queryU.toString());
                }
                //rescatando los parametros del formulario jsp registrarCategoria
                String nombre = request.getParameter("nombre");
                String paterno = request.getParameter("paterno");
                String materno = request.getParameter("materno");
                String telefono =  request.getParameter("telefono");
                //pasando los datos a los parametros de la consulta
                insertCliente.setString(1, nombre);
                insertCliente.setString(2, paterno);
                insertCliente.setString(3, materno);
                insertCliente.setInt(4, Integer.valueOf(telefono));
                
                //ejecutando la consulta
                
                int registro = insertCliente.executeUpdate();
                
                if(registro == 1){
                    mensaje="Registro realizado con exito";
                }else{
                    mensaje="Error al insertar el registro";
                }
                
                StringBuilder query3 = new StringBuilder();
                query3.append(" select max(c.id) ");
                query3.append(" from cliente c");
                
                PreparedStatement pst1 = connection.prepareStatement(query3.toString());
                ResultSet resultadow = pst1.executeQuery();
                int id = 0;
                while(resultadow.next()){
                    id = resultadow.getInt(1);
                }   
                String correo = request.getParameter("correo");
                String pasword = request.getParameter("pasword");
                
                insertUsuario.setInt(1, id);
                insertUsuario.setString(2, correo);
                insertUsuario.setString(3, pasword);
                
                int registro2 = insertUsuario.executeUpdate();
                
                if(registro2 == 1){
                    mensaje="Registro realizado con exito";
                }else{
                    mensaje="Error al insertar el registro";
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mensaje;
    }
    
}
