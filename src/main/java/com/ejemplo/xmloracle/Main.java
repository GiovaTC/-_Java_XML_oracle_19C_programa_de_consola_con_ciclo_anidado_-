package com.ejemplo.xmloracle;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("       JAVA XML + ORACLE DATABASE 19C");
        System.out.println();

        String rutaXML = "xml/productos.xml";

        ProductoDAO productoDAO = new ProductoDAO();

        try {
            File archivoXML = new File(rutaXML);

            if (!archivoXML.exists()) {

                System.out.println(
                        "NO se encontro el archivo XML:"
                );
                System.out.println(
                        archivoXML.getAbsolutePath()
                );

                return;
            }

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            Document documento =
                    builder.parse(archivoXML);

            documento.getDocumentElement().normalize();

            NodeList productos =
                    documento.getElementsByTagName("producto");

            System.out.println(
                    archivoXML.getAbsolutePath()
            );

            System.out.println(
                    "CANTIDAD de productos: " +
                            productos.getLength()
            );

            System.out.println();

            // ==================================================
            // CICLO EXTERNO
            // Recorre cada elemento <producto>
            // ==================================================

            for ( int i = 0; i < productos.getLength(); i++) {
                Node nodoProducto = productos.item(i);

                if (nodoProducto.getNodeType() == Node.ELEMENT_NODE) {

                    Producto producto = new Producto();

                    NodeList datosProducto = nodoProducto.getChildNodes();

                    System.out.println(
                            "=============================================="
                    );

                    System.out.println(
                            "PRODUCTO " + (i + 1)
                    );

                    System.out.println(
                            "=============================================="
                    );

                    // ==================================================
                    // CICLO INTERNO
                    // Recorre los elementos hijos del producto
                    // ==================================================

                    for (int j = 0;
                         j < datosProducto.getLength();
                         j++) {

                        Node dato = datosProducto.item(j);

                        if (dato.getNodeType()
                                == Node.ELEMENT_NODE) {

                            String nombreNodo =
                                    dato.getNodeName();

                            String valor =
                                    dato.getTextContent().trim();

                            System.out.println(
                                    nombreNodo + ": " + valor
                            );

                            switch (nombreNodo) {

                                case "id":
                                    producto.setId(
                                            Integer.parseInt(valor)
                                    );
                                    break;

                                case "nombre":
                                    producto.setNombre(valor);
                                    break;

                                case "categoria":
                                    producto.setCategoria(valor);
                                    break;

                                case "precio":
                                    producto.setPrecio(
                                            Double.parseDouble(valor)
                                    );
                                    break;

                                case "stock":
                                    producto.setStock(
                                            Integer.parseInt(valor)
                                    );
                                    break;

                                default:
                                    break;
                            }
                        }
                    }

                    System.out.println();

                    System.out.println(
                            "Procesando información..."
                    );

                    productoDAO.insertar(producto);

                    System.out.println();
                }
            }

            // ==================================================
            // CONSULTAR INFORMACIÓN EN ORACLE
            // ==================================================

            productoDAO.listarProductos();

            System.out.println();
            System.out.println(
                    "=============================================="
            );

            System.out.println(
                    "             PROCESO FINALIZADO"
            );

            System.out.println(
                    "=============================================="
            );

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Se produjo un error:"
            );

            System.out.println(
                    e.getMessage()
            );

            e.printStackTrace();
        }
    }
}