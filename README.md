# -_Java_XML_oracle_19C_programa_de_consola_con_ciclo_anidado_-

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/5f01aa18-f34a-4dbd-8948-2a65f6a91cd0" />  

```

# Java + XML + Oracle 19c

## Programa de consola con ciclo anidado

Aplicación de consola desarrollada en **Java 21** utilizando **IntelliJ IDEA**, que permite:

* Leer un archivo XML.
* Recorrer el XML mediante un **ciclo anidado**.
* Procesar la información de productos.
* Conectarse a **Oracle Database 19c** mediante JDBC.
* Insertar los datos del XML en Oracle.
* Consultar y mostrar los registros almacenados.

---

# 1. Tecnologías utilizadas

* Java 21
* IntelliJ IDEA
* Maven
* Oracle Database 19c
* JDBC
* XML DOM
* Oracle JDBC Driver `ojdbc11`

---

# 2. Estructura del proyecto

```text
JavaXmlOracle19C/
│
├── pom.xml
│
├── xml/
│   └── productos.xml
│
└── src/
    └── main/
        └── java/
            └── com/
                └── ejemplo/
                    └── xmloracle/
                        │
                        ├── ConexionOracle.java
                        ├── Producto.java
                        ├── ProductoDAO.java
                        └── Main.java
```

---

# 3. Crear la base de datos en Oracle 19c

Ingresar a Oracle 19c con SQL Developer, SQL*Plus o la herramienta que utilices.

Crear la tabla:

```sql
CREATE TABLE PRODUCTOS_XML (
    ID NUMBER PRIMARY KEY,
    NOMBRE VARCHAR2(100) NOT NULL,
    CATEGORIA VARCHAR2(100),
    PRECIO NUMBER(12,2),
    STOCK NUMBER
);
```

---

# 4. Verificar la tabla

Ejecutar:

```sql
DESC PRODUCTOS_XML;
```

Resultado esperado:

```text
Name        Null?    Type
----------- -------- ----------------
ID          NOT NULL NUMBER
NOMBRE      NOT NULL VARCHAR2(100)
CATEGORIA            VARCHAR2(100)
PRECIO               NUMBER(12,2)
STOCK                NUMBER
```

---

# 5. Limpiar los datos anteriores

Antes de realizar una nueva prueba:

```sql
DELETE FROM PRODUCTOS_XML;

COMMIT;
```

---

# 6. Crear el proyecto Maven

En IntelliJ IDEA:

```text
File
   ↓
New
   ↓
Project
   ↓
Maven
```

Utilizar:

```text
Name: JavaXmlOracle19C
Language: Java
JDK: 21
Build System: Maven
```

---

# 7. Archivo pom.xml

Crear el archivo:

```text
pom.xml
```

Código completo:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.ejemplo</groupId>
    <artifactId>JavaXmlOracle19C</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>

        <!-- Oracle JDBC Driver -->
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc11</artifactId>
            <version>23.5.0.24.07</version>
        </dependency>

    </dependencies>

</project>
```

---

# 8. Crear el archivo XML

Crear la carpeta:

```text
xml
```

Dentro crear:

```text
productos.xml
```

El siguiente XML contiene **exactamente 31 líneas**:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<productos>
    <producto>
        <id>1</id>
        <nombre>Computador</nombre>
        <categoria>Tecnologia</categoria>
        <precio>2500000</precio>
        <stock>10</stock>
    </producto>
    <producto>
        <id>2</id>
        <nombre>Teclado</nombre>
        <categoria>Accesorios</categoria>
        <precio>120000</precio>
        <stock>25</stock>
    </producto>
    <producto>
        <id>3</id>
        <nombre>Mouse</nombre>
        <categoria>Accesorios</categoria>
        <precio>80000</precio>
        <stock>30</stock>
    </producto>
    <producto>
        <id>4</id>
        <nombre>Monitor</nombre>
        <categoria>Tecnologia</categoria>
        <precio>950000</precio>
        <stock>15</stock>
    </producto>
</productos>
```

## Conteo de líneas

```text
1   <?xml version="1.0" encoding="UTF-8"?>
2   <productos>
3       <producto>
4           <id>1</id>
5           <nombre>Computador</nombre>
6           <categoria>Tecnologia</categoria>
7           <precio>2500000</precio>
8           <stock>10</stock>
9       </producto>

10      <producto>
11          <id>2</id>
12          <nombre>Teclado</nombre>
13          <categoria>Accesorios</categoria>
14          <precio>120000</precio>
15          <stock>25</stock>
16      </producto>

17      <producto>
18          <id>3</id>
19          <nombre>Mouse</nombre>
20          <categoria>Accesorios</categoria>
21          <precio>80000</precio>
22          <stock>30</stock>
23      </producto>

24      <producto>
25          <id>4</id>
26          <nombre>Monitor</nombre>
27          <categoria>Tecnologia</categoria>
28          <precio>950000</precio>
29          <stock>15</stock>
30      </producto>
31  </productos>
```

---

# 9. Clase Producto.java

Crear:

```text
src/main/java/com/ejemplo/xmloracle/Producto.java
```

Código:

```java
package com.ejemplo.xmloracle;

public class Producto {

    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;

    public Producto() {
    }

    public Producto(int id, String nombre, String categoria,
                    double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
```

---

# 10. Clase ConexionOracle.java

Crear:

```text
src/main/java/com/ejemplo/xmloracle/ConexionOracle.java
```

Código:

```java
package com.ejemplo.xmloracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521/orcl";

    private static final String USUARIO = "system";

    private static final String PASSWORD = "TU_PASSWORD";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}
```

## Importante

Modificar:

```java
private static final String PASSWORD = "TU_PASSWORD";
```

por la contraseña real de tu usuario Oracle.

Por ejemplo:

```java
private static final String PASSWORD = "oracle";
```

La URL utilizada es:

```text
jdbc:oracle:thin:@localhost:1521/orcl
```

Si tu Oracle 19c utiliza otro servicio, modificar `orcl`.

---

# 11. Clase ProductoDAO.java

Crear:

```text
src/main/java/com/ejemplo/xmloracle/ProductoDAO.java
```

Código completo:

```java
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
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, producto.getId());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getCategoria());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());

            ps.executeUpdate();

            System.out.println(
                    "Producto insertado en Oracle: "
                            + producto.getNombre()
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al insertar producto: "
                            + e.getMessage()
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
            System.out.println("      PRODUCTOS ALMACENADOS EN ORACLE 19C");
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
                    "Error al consultar Oracle: "
                            + e.getMessage()
            );
        }
    }
}
```

---

# 12. Clase Main.java

Crear:

```text
src/main/java/com/ejemplo/xmloracle/Main.java
```

Código completo:

```java
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
        System.out.println("==============================================");
        System.out.println();

        String rutaXML = "xml/productos.xml";

        ProductoDAO productoDAO = new ProductoDAO();

        try {

            File archivoXML = new File(rutaXML);

            if (!archivoXML.exists()) {

                System.out.println(
                        "No se encontró el archivo XML:"
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
                    "Archivo XML encontrado:"
            );

            System.out.println(
                    archivoXML.getAbsolutePath()
            );

            System.out.println();

            System.out.println(
                    "Cantidad de productos: "
                            + productos.getLength()
            );

            System.out.println();

            // ==================================================
            // CICLO EXTERNO
            // Recorre cada elemento <producto>
            // ==================================================

            for (int i = 0; i < productos.getLength(); i++) {

                Node nodoProducto = productos.item(i);

                if (nodoProducto.getNodeType()
                        == Node.ELEMENT_NODE) {

                    Producto producto = new Producto();

                    NodeList datosProducto =
                            nodoProducto.getChildNodes();

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
```

---

# 13. ¿Dónde está el ciclo anidado?

El ejercicio principal está en `Main.java`.

Tenemos un ciclo externo:

```java
for (int i = 0; i < productos.getLength(); i++) {
```

Este ciclo recorre:

```text
producto 1
producto 2
producto 3
producto 4
```

Dentro de este ciclo tenemos otro ciclo:

```java
for (int j = 0;
     j < datosProducto.getLength();
     j++) {
```

Este segundo ciclo recorre:

```text
id
nombre
categoria
precio
stock
```

Por lo tanto:

```text
                    XML
                     │
              <productos>
                     │
        ┌────────────┼────────────┐
        │            │            │
    producto 1   producto 2   producto 3 ...
        │            │
        │            │
     ciclo          ciclo
     externo        externo
        │
        ├── id
        ├── nombre
        ├── categoria
        ├── precio
        └── stock
             ↑
        ciclo interno
```

---

# 14. Funcionamiento del programa

El flujo completo es:

```text
              INICIO
                 │
                 ▼
       Leer productos.xml
                 │
                 ▼
       Crear documento XML
                 │
                 ▼
       Obtener <producto>
                 │
                 ▼
       ┌─────────────────┐
       │  Ciclo externo  │
       └────────┬────────┘
                │
                ▼
        Obtener producto
                │
                ▼
       ┌─────────────────┐
       │  Ciclo interno  │
       └────────┬────────┘
                │
                ▼
       Leer datos del XML
                │
                ▼
       Crear objeto Producto
                │
                ▼
        Conectar Oracle 19c
                │
                ▼
       INSERT PRODUCTOS_XML
                │
                ▼
       Siguiente producto
                │
                ▼
       SELECT PRODUCTOS_XML
                │
                ▼
              FIN
```

---

# 15. Ejecución en IntelliJ IDEA

Abrir:

```text
Main.java
```

Y ejecutar:

```text
Run → Run 'Main'
```

También se puede presionar:

```text
Shift + F10
```

---

# 16. Resultado esperado en consola

```text
==============================================
       JAVA XML + ORACLE DATABASE 19C
==============================================

Archivo XML encontrado:
C:\...\JavaXmlOracle19C\xml\productos.xml

Cantidad de productos: 4

==============================================
PRODUCTO 1
==============================================
id: 1
nombre: Computador
categoria: Tecnologia
precio: 2500000
stock: 10

Procesando información...
Producto insertado en Oracle: Computador

==============================================
PRODUCTO 2
==============================================
id: 2
nombre: Teclado
categoria: Accesorios
precio: 120000
stock: 25

Procesando información...
Producto insertado en Oracle: Teclado

==============================================
PRODUCTO 3
==============================================
id: 3
nombre: Mouse
categoria: Accesorios
precio: 80000
stock: 30

Procesando información...
Producto insertado en Oracle: Mouse

==============================================
PRODUCTO 4
==============================================
id: 4
nombre: Monitor
categoria: Tecnologia
precio: 950000
stock: 15

Procesando información...
Producto insertado en Oracle: Monitor
```

Después aparecerá:

```text
==============================================
      PRODUCTOS ALMACENADOS EN ORACLE 19C
==============================================

ID       : 1
Nombre   : Computador
Categoria: Tecnologia
Precio   : 2500000.0
Stock    : 10
----------------------------------------------

ID       : 2
Nombre   : Teclado
Categoria: Accesorios
Precio   : 120000.0
Stock    : 25
----------------------------------------------

ID       : 3
Nombre   : Mouse
Categoria: Accesorios
Precio   : 80000.0
Stock    : 30
----------------------------------------------

ID       : 4
Nombre   : Monitor
Categoria: Tecnologia
Precio    : 950000.0
Stock    : 15
----------------------------------------------

==============================================
             PROCESO FINALIZADO
==============================================
```

---

# 17. Verificar desde Oracle

Después de ejecutar el programa:

```sql
SELECT *
FROM PRODUCTOS_XML
ORDER BY ID;
```

Resultado:

```text
ID   NOMBRE       CATEGORIA       PRECIO      STOCK
---  -----------  --------------  ----------  -----
1    Computador   Tecnologia      2500000     10
2    Teclado      Accesorios      120000      25
3    Mouse        Accesorios      80000       30
4    Monitor      Tecnologia      950000      15
```

---

# 18. Conceptos utilizados

Este proyecto permite practicar:

```text
Java
 │
 ├── Clases
 ├── Objetos
 ├── Constructores
 ├── Encapsulamiento
 ├── Métodos
 ├── Ciclo for
 ├── Ciclos anidados
 ├── switch
 ├── Manejo de excepciones
 │
 ├── XML
 │    ├── Document
 │    ├── Node
 │    └── NodeList
 │
 └── JDBC
      ├── Connection
      ├── PreparedStatement
      └── ResultSet
```

---

# 19. Concepto principal del ejercicio

El programa utiliza un **ciclo anidado** para procesar una estructura jerárquica.

El primer ciclo recorre los productos:

```java
for (int i = 0; i < productos.getLength(); i++) {
```

El segundo ciclo recorre los campos de cada producto:

```java
for (int j = 0;
     j < datosProducto.getLength();
     j++) {
```

Por ejemplo:

```text
Producto 1
   │
   ├── id
   ├── nombre
   ├── categoria
   ├── precio
   └── stock

Producto 2
   │
   ├── id
   ├── nombre
   ├── categoria
   ├── precio
   └── stock
```

Esto permite comprender cómo recorrer información XML utilizando **estructuras repetitivas anidadas**.

---

# 20. Comando SQL para comprobar cantidad de registros

```sql
SELECT COUNT(*) AS TOTAL
FROM PRODUCTOS_XML;
```

Resultado esperado:

```text
TOTAL
-----
4
```

---

# 21. Limpiar nuevamente la tabla

Para volver a realizar el ejercicio:

```sql
DELETE FROM PRODUCTOS_XML;

COMMIT;
```

Luego ejecutar nuevamente:

```text
Main.java
```

---

# 22. Resumen

El proyecto realiza el siguiente proceso:

```text
XML de 31 líneas
       │
       ▼
Java 21
       │
       ▼
Ciclo externo
       │
       ▼
Ciclo interno
       │
       ▼
Objetos Producto
       │
       ▼
JDBC
       │
       ▼
Oracle Database 19c
       │
       ▼
PRODUCTOS_XML
```

Es un ejemplo básico de integración entre **Java, XML, ciclos anidados, JDBC y Oracle 19c**, apropiado para practicar procesamiento de información estructurada desde una aplicación de consola.
