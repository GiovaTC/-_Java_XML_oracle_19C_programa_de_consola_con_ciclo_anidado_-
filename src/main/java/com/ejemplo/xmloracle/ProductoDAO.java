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

    public void listarProductos() {

        String sql = """
                SELECT ID,
                    NOMBRE,
                    CATEGORIA,
                    PRECIO,
                    STOCK
                FROM PRODUCTOS_XML
                ORDER BY ID
                """;
        try (
                Connection conexion = ConexionOracle.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("    PRODUCTOS ALMACENADOS EN ORACLE!    ");
            System.out.println("==============================================");

            while (rs.next()) {

                System.out.println(
                        "ID       : " + rs.getInt("ID")
                );

                System.out.println(
                        "Nombre   : " + rs.getString("NOMBRE")
                );

                System.out.println(
                        "Categoria: " + rs.getString("CATEGORIA")
                );

                System.out.println(
                        "Precio   : " + rs.getDouble("PRECIO")
                );

                System.out.println(
                        "Stock    : " + rs.getInt("STOCK")
                );

                System.out.println("----------------------------------------------");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al consultar ORACLE: " + e.getMessage()
            );
        }
    }
}
