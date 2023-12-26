package com.project;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.project.database.DatabaseManager;
import com.project.model.Faccio;
import com.project.model.Personatge;

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

                menu(conn);

                // Desconnectar
                UtilsSQLite.disconnect(conn);
        }

        static void initDatabase(String filePath) {
                Personatge p = new Personatge();
                p.initDatabase(filePath);

                Faccio f = new Faccio();
                f.initDatabase(filePath);
        }

        private static void menu(Connection conn) throws SQLException {
                // Crear el scanner para leer la entrada del usuario
                Scanner scanner = new Scanner(System.in);
                DatabaseManager databaseManager = new DatabaseManager();
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
                                        databaseManager.mostrarTaula(conn);
                                        break;
                                case 2:
                                        databaseManager.mostrarPersonatgesPerFaccio(conn);
                                        break;
                                case 3:
                                        databaseManager.mostrarMillorAtacantPerFaccio(conn);
                                        break;
                                case 4:
                                        databaseManager.mostrarMillorDefensorPerFaccio(conn);
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
}
