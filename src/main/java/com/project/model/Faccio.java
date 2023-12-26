package com.project.model;

import java.sql.Connection;

import com.project.UtilsSQLite;

public class Faccio {
        public void initDatabase(String filePath) {
                // Connectar (crea la BBDD si no existeix)
                Connection conn = UtilsSQLite.connect(filePath);

                // Esborrar la taula (per si existeix)
                UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS faccio;");

                // Crear una nova taula
                UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS faccio ("
                                + "	id integer PRIMARY KEY AUTOINCREMENT,"
                                + "	name text(15) NOT NULL,"
                                + "	resum text(500) NOT NULL);");

                // Afegir elements a una taula
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO faccio (name, resum) VALUES ('Caballeros', 'Facción noble y honorable con habilidades tácticas avanzadas.');");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO faccio (name, resum) VALUES ('Vikingos', 'Facción fuerte y valiente con un enfoque en la fuerza bruta.');");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO faccio (name, resum) VALUES ('Samuráis', 'Facción disciplinada y habilidosa con técnicas de combate refinadas.');");
                UtilsSQLite.queryUpdate(conn,
                                "INSERT INTO faccio (name, resum) VALUES ('Wu Lin', 'Facción misteriosa y versátil con habilidades variadas.');");

                // Desconnectar
                UtilsSQLite.disconnect(conn);
        }
}
