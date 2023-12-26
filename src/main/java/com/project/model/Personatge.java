package com.project.model;

import java.sql.Connection;

import com.project.UtilsSQLite;

public class Personatge {
    public void initDatabase(String filePath) {
        // Connectar (crea la BBDD si no existeix)
        Connection conn = UtilsSQLite.connect(filePath);

        // Esborrar la taula (per si existeix)
        UtilsSQLite.queryUpdate(conn, "DROP TABLE IF EXISTS personatge;");

        // Crear una nova taula
        UtilsSQLite.queryUpdate(conn, "CREATE TABLE IF NOT EXISTS personatge ("
                + "	id integer PRIMARY KEY AUTOINCREMENT,"
                + "	name text(15) NOT NULL,"
                + "	atac REAL NOT NULL,"
                + "	defensa REAL NOT NULL,"
                + " idFaccio INTEGER,"
                + "	FOREIGN KEY (idFaccio) REFERENCES faccio(id));");

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
}
