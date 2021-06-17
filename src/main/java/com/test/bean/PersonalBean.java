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
public class PersonalBean {
    //atributos
    private Connection connection;
    private PreparedStatement insertPersonal;
    private VariablesConexion variable;
    //constructores
    public PersonalBean()throws SQLException{
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
    public String registrarPersonal(HttpServletRequest request){
        String mensaje="";
        if(request == null){
            return"vacio!!";
        }
        if(connection != null){
            try {
                //definiendo la consulta
                StringBuilder query = new StringBuilder();
                query.append(" insert into personal_entrega ");
                query.append(" values (nextval('secuencia_3'),?,?,?,?,?,?)");
                //enviando la consulta
                if(insertPersonal==null){
                    insertPersonal=connection.prepareStatement(query.toString());
                }
                //rescatando los parametros del formulario jsp registrarCategoria
                String nombre = request.getParameter("nombre");
                String paterno = request.getParameter("paterno");
                String materno = request.getParameter("materno");
                String ci = request.getParameter("ci");
                String telefono =  request.getParameter("telefono");
                String placa = request.getParameter("placa");
                //pasando los datos a los parametros de la consulta
                insertPersonal.setString(1, ci);
                insertPersonal.setString(2, nombre);
                insertPersonal.setString(3, paterno);
                insertPersonal.setString(4, materno);
                insertPersonal.setString(5, placa);
                insertPersonal.setInt(6, Integer.valueOf(telefono));
                
                //ejecutando la consulta
                
                int registro = insertPersonal.executeUpdate();
                
                if(registro == 1){
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
    //Realizando el listado de todas las categorias que se tienen registrados
    public String listarPersonal(){
        StringBuilder salidaTabla = new StringBuilder();
        StringBuilder query = new StringBuilder();
        query.append(" select p.ci, concat(p.nombres,' ',p.paterno,' ',p.materno ), telefono, placa_vehiculo");
        query.append(" from personal_entrega p ");
        try {
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
                salidaTabla.append(resultado.getInt(3));
                salidaTabla.append("</td>");
                salidaTabla.append("<td>");
                salidaTabla.append(resultado.getString(4));
                salidaTabla.append("</td>");
                salidaTabla.append("</tr>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error de conexion");
        }
        return salidaTabla.toString();
    }
    
    
    //getter y setter

    
    
}
