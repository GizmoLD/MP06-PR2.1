package com.project;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Aquest exemple mostra com fer una 
 * connexió a SQLite amb Java
 * 
 * A la primera crida, crea l'arxiu 
 * de base de dades hi posa dades,
 * després les modifica
 * 
 * A les següent crides ja estan
 * originalment modificades
 * (tot i que les sobreescriu cada vegada)
 */

public class Main {
        public static void main(String[] args) throws SQLException {
                String basePath = System.getProperty("user.dir") + "/data/";
                String filePath = basePath + "database.db";

                // Si no hi ha l'arxiu creat, el crea i li posa dades
                File fDatabase = new File(filePath);
                if (!fDatabase.exists()) {
                        initDatabase(filePath);
                }

                // Connectar (crea la BBDD si no existeix)
                Connection conn = UtilsSQLite.connect(filePath);

                // Llistar les taules
                ArrayList<String> taules = UtilsSQLite.listTables(conn);
                System.out.println(taules);

                menu(conn);

                // Desconnectar
                UtilsSQLite.disconnect(conn);
        }

        static void initDatabase(String filePath) {
                // Connectar (crea la BBDD si no existeix)
                Connection conn = UtilsSQLite.connect(filePath);

                // Esborrar la taula (per si existeix)
                UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS faccio;");
                UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS personatge;");

                // Crear una nova taula
                UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS faccio ("
                                + "	id integer PRIMARY KEY AUTOINCREMENT,"
                                + "	name text(15) NOT NULL,"
                                + "	resum text(500) NOT NULL);");

                UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS personatge ("
                                + "	id integer PRIMARY KEY AUTOINCREMENT,"
                                + "	name text(15) NOT NULL,"
                                + "	atac REAL NOT NULL,"
                                + "	defensa REAL NOT NULL,"
                                + "     idFaccio INTEGER,"
                                + "	FOREIGN KEY (idFaccio) REFERENCES faccio(id));");

                // Afegir elements a una taula
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO faccio (name, resum) VALUES ('Caballeros', 'Facción noble y honorable con habilidades tácticas avanzadas.');");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO faccio (name, resum) VALUES ('Vikingos', 'Facción fuerte y valiente con un enfoque en la fuerza bruta.');");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO faccio (name, resum) VALUES ('Samuráis', 'Facción disciplinada y habilidosa con técnicas de combate refinadas.');");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO faccio (name, resum) VALUES ('Wu Lin', 'Facción misteriosa y versátil con habilidades variadas.');");

                // Insertar personajes para la facción "Caballeros"
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('Berserk', 80.0, 70.0, 1);");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('Caballero2', 75.0, 65.0, 1);");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('Escudero', 40.0, 90.0, 1);");

                // Insertar personajes para la facción "Vikingos"
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('Odin', 85.0, 90.0, 2);");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('Vikingo2', 90.0, 55.0, 2);");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('Thor', 90.0, 55.0, 2);");

                // Insertar personajes para la facción "Samuráis"
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('Samurái1', 78.0, 75.0, 3);");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('Sekiro', 80.0, 99.0, 3);");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('Hatori Hanzo', 99.0, 68.0, 3);");

                // Insertar personajes para la facción "Wu Lin"
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('WuLin1', 88.0, 72.0, 4);");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('Broli', 85.0, 90.0, 4);");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO personatge (name, atac, defensa, idFaccio) VALUES ('Goku', 90.0, 70.0, 4);");

                // Desconnectar
                UtilsSQLite.disconnect(conn);
        }

        private static void menu(Connection conn) throws SQLException {
                // Crear el scanner para leer la entrada del usuario
                Scanner scanner = new Scanner(System.in);

                // Bucle del menú
                while (true) {
                        // Mostrar el menú
                        System.out.println("Menú:");
                        System.out.println("1. Mostrar una taula");
                        System.out.println("2. Mostrar personatges per facció");
                        System.out.println("3. Mostrar el millor atacant per facció");
                        System.out.println("4. Mostrar el millor defensor per facció");
                        System.out.println("5. Sortir");

                        // Pedir al usuario que ingrese una opción
                        System.out.print("Ingresa el número de opción: ");
                        int opcion = scanner.nextInt();

                        // Realizar acciones según la opción seleccionada
                        switch (opcion) {
                                case 1:
                                        mostrarTaula(conn);
                                        break;
                                case 2:
                                        mostrarPersonatgesPerFaccio(conn);
                                        break;
                                case 3:
                                        mostrarMillorAtacantPerFaccio(conn);
                                        break;
                                case 4:
                                        mostrarMillorDefensorPerFaccio(conn);
                                        break;
                                case 5:
                                        // Salir del programa
                                        System.out.println("Adéu!");
                                        return;
                                default:
                                        System.out.println("Opció no vàlida. Si us plau, tria una opció vàlida.");
                        }
                }
        }

        private static void mostrarTaula(Connection conn) {
                // Lógica para mostrar una taula
                ArrayList<String> taules = UtilsSQLite.listTables(conn);
                Scanner scanner = new Scanner(System.in);
                // Mostrar menú de tablas
                System.out.println("\nSelecciona una taula");
                for (int i = 0; i < taules.size(); i++) {
                        System.out.println((i + 1) + ". " + taules.get(i));
                }
                System.out.println((taules.size() + 1) + ". Tornar enrere");

                // Pedir al usuario que ingrese una opción
                System.out.print("Ingresa el número de opción: ");
                int opcionTabla = scanner.nextInt();

                // Validar la opción del usuario
                if (opcionTabla > 0 && opcionTabla <= taules.size()) {
                        // El usuario seleccionó una tabla
                        String tablaSeleccionada = taules.get(opcionTabla - 1);
                        System.out.println("Has seleccionat la taula: " + tablaSeleccionada);

                        ResultSet rs = UtilsSQLite.querySelect(conn, "SELECT * FROM " + tablaSeleccionada + ";");
                        try {
                                mostrarResultados(rs);
                        } catch (SQLException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                        // Aquí puedes realizar operaciones específicas con la tabla seleccionada
                } else if (opcionTabla == taules.size() + 1) {
                        // El usuario seleccionó volver atrás
                        System.out.println("Tornant enrere...");
                } else {
                        // Opción no válida
                        System.out.println("Opció no vàlida. Si us plau, tria una opció vàlida.");
                }
                System.out.println();
        }

        private static void mostrarPersonatgesPerFaccio(Connection conn) {
                // Lógica para mostrar personatges per facció
                System.out.println("Mostrant personatges per facció: ");
                Scanner scanner = new Scanner(System.in);
                String tablaSeleccionada = scanner.nextLine();
                tablaSeleccionada = tablaSeleccionada.substring(0, 1).toUpperCase() + tablaSeleccionada.substring(1);
                try {
                        ResultSet rs = UtilsSQLite.querySelect(conn,
                                        "SELECT * FROM personatge WHERE idFaccio = (SELECT id FROM faccio WHERE name = '"
                                                        + tablaSeleccionada + "' );");
                        if (!rs.next()) {
                                System.out.println("Faccion no existent.\n");
                        }else{
                                mostrarResultados(rs);
                        }
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        System.out.println("Taula no existent");
                }
        }

        private static void mostrarMillorAtacantPerFaccio(Connection conn) {
                // Lógica para mostrar una taula
                Scanner scanner = new Scanner(System.in);
                // Mostrar menú de tablas
                System.out.println("\nSelecciona una taula");

                // Pedir al usuario que ingrese una opción
                int opciones = mostrarNomDeFaccions(conn);
                System.out.print("\nIngresa el número de opción: \n");
                int opcionTabla = scanner.nextInt();

                if (opciones > opcionTabla || 0 > opciones) {
                        return;
                }

                try {
                        ResultSet rs = UtilsSQLite.querySelect(conn, "SELECT * FROM personatge WHERE idFaccio = "+opcionTabla+" ORDER BY atac DESC LIMIT 1;");
                        mostrarResultados(rs);
                } catch (Exception e) {
                        e.printStackTrace();
                }
                System.out.println();
        }

        private static void mostrarMillorDefensorPerFaccio(Connection conn) {
                // Lógica para mostrar el millor defensor per facció
                Scanner scanner = new Scanner(System.in);
                // Mostrar menú de tablas
                System.out.println("\nSelecciona una taula");

                // Pedir al usuario que ingrese una opción
                int opciones = mostrarNomDeFaccions(conn);
                System.out.print("\nIngresa el número de opción: \n");
                int opcionTabla = scanner.nextInt();

                if (opciones > opcionTabla || 0 > opciones) {
                        return;
                }

                try {
                        ResultSet rs = UtilsSQLite.querySelect(conn, "SELECT * FROM personatge WHERE idFaccio = "+opcionTabla+" ORDER BY defensa DESC LIMIT 1;");
                        mostrarResultados(rs);
                } catch (Exception e) {
                        e.printStackTrace();
                }
                System.out.println();
        }

        private static void mostrarResultados(ResultSet rs) throws SQLException {
                // Obtener información sobre las columnas
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Imprimir los nombres de las columnas
                for (int i = 1; i <= columnCount; i++) {
                        System.out.printf("%-20s", metaData.getColumnName(i));
                }
                System.out.println();

                // Imprimir los datos de cada fila
                while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                                System.out.printf("%-20s", rs.getString(i));
                        }
                        System.out.println();
                }
        }

        private static int mostrarNomDeFaccions(Connection conn) {
                try {
                        ResultSet rs = UtilsSQLite.querySelect(conn, "SELECT id,name FROM faccio");
                        ResultSet rsCount = UtilsSQLite.querySelect(conn, "SELECT count(*) FROM faccio");
                        ResultSetMetaData metaData = rsCount.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        mostrarResultados(rs);
                        return columnCount;
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return 0;
        }
}
