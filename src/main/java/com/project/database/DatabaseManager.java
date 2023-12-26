package com.project.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.project.UtilsSQLite;

public class DatabaseManager {
    public void mostrarTaula(Connection conn) {
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

    public void mostrarPersonatgesPerFaccio(Connection conn) {
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
            } else {
                mostrarResultados(rs);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Taula no existent");
        }
    }

    public void mostrarMillorAtacantPerFaccio(Connection conn) {
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
            ResultSet rs = UtilsSQLite.querySelect(conn,
                    "SELECT * FROM personatge WHERE idFaccio = " + opcionTabla + " ORDER BY atac DESC LIMIT 1;");
            mostrarResultados(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public void mostrarMillorDefensorPerFaccio(Connection conn) {
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
            ResultSet rs = UtilsSQLite.querySelect(conn,
                    "SELECT * FROM personatge WHERE idFaccio = " + opcionTabla + " ORDER BY defensa DESC LIMIT 1;");
            mostrarResultados(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public void mostrarResultados(ResultSet rs) throws SQLException {
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

    public int mostrarNomDeFaccions(Connection conn) {
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
