package com.ejemplo.xmloracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO {


    public void insertar(Producto producto) {
        String sql = """    
            
                INSERT INTO PRODUCTOS_XML
            (ID, NOMBRE, CATEGORIA, PRECIO, STOCK)
            VALUES (?, ?, ?, ?, ?)
   

           """;

        try (
                Connection conexion = ConexionOracle.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql);

        ) {

            ps.setInt(1, producto.
                    getId());
            ps.setString(2,
                    producto.getNombre());
            ps.setString(3, producto.getCategoria());
            ps.setDouble
                    (4, producto.getPrecio());
            ps.

                    setInt(5, producto.

                            getStock());

            ps.executeUpdate();

            System
                    .out.println(
                            "PRODCUTO insertado en ORACLE: "
                                    + producto.getNombre
                                    ()
                    );
        } catch (SQLException e) {

            System.out.println(
                    "ERROR al insertar producto: " + e.getMessage()
            );
        }
    }
}
