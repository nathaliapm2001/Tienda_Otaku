package Tienda_Otaku_Paquete.datos;

import Tienda_Otaku_Paquete.modelo.Manga;
import Tienda_Otaku_Paquete.modelo.TiposGenero;
import Tienda_Otaku_Paquete.modelo.Autor;
import Tienda_Otaku_Paquete.modelo.Genero;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class SQLManga {

    private final String URL = "jdbc:sqlite:mi_base_manga.db";

    private Connection connection;

    public SQLManga() throws SQLException {
        this.connection = DriverManager.getConnection(URL);

        crearTabla();

    }

    private void crearTabla() throws SQLException {

        String sql = """

                    CREATE TABLE IF NOT EXISTS Manga (
                    idObra      TEXT PRIMARY KEY,
                    titulo      TEXT NOT NULL,
                    autor       TEXT NOT NULL,
                    genero      TEXT,
                    estado      TEXT,
                    estrellas   DOUBLE,
                    stock       INTEGER,
                    volumen     INTEGER
                ); """;

        Statement st = connection.createStatement();
        st.execute(sql);

        String[] inserts = {
                "INSERT OR REPLACE INTO Manga VALUES ('1',  'Atelier of Witch Hat',            'Kamome Shirahama', 'SEINEN-Fantasia',        'activo',     4.7, 10,  16)",
                "INSERT OR REPLACE INTO Manga VALUES ('2',  'One Piece',                       'Eiichiro Oda',     'SHONEN-Aventura',        'activo',     5.0,  7, 108)",
                "INSERT OR REPLACE INTO Manga VALUES ('3',  'Oyasumi Punpun',                  'Inio Asano',       'SEINEN-Psicologico',     'finalizado', 5.0, 10,  13)",
                "INSERT OR REPLACE INTO Manga VALUES ('4',  'Mobile Suit Gundam: The Origin',  'Yoshikazu Yasuhiko','SEINEN-Mecha',          'finalizado', 4.0, 12,  12)",
                "INSERT OR REPLACE INTO Manga VALUES ('5',  'Kaoru Hana wa Rin to Saku',       'Saka Mikami',      'SHONEN-Romance',         'activo',     3.7, 10,  10)",
                "INSERT OR REPLACE INTO Manga VALUES ('6',  'Rent-A-Girlfriend',               'Reiji Miyajima',   'SHONEN-Romcom',          'activo',     2.8, 36,  36)",
                "INSERT OR REPLACE INTO Manga VALUES ('7',  'Berserk',                         'Kentaro Miura',    'SEINEN-Fantasia oscura', 'activo',     5.0, 42,  42)",
                "INSERT OR REPLACE INTO Manga VALUES ('8',  'My Hero Academia',                'Kohei Horikoshi',  'SHONEN-Accion',          'finalizado', 4.5, 42,  42)",
                "INSERT OR REPLACE INTO Manga VALUES ('9',  'Vinland Saga',                    'Makoto Yukimura',  'SEINEN-Historico',       'activo',     5.0, 28,  28)",
                "INSERT OR REPLACE INTO Manga VALUES ('10', 'Kaguya-sama: Love is War',        'Aka Akasaka',      'SEINEN-Romcom',          'finalizado', 4.5, 28,  28)",
                "INSERT OR REPLACE INTO Manga VALUES ('11', 'Pokemon Adventures',              'Hidenori Kusaka',  'SHONEN-Aventura',        'activo',     3.5, 64,  64)",
                "INSERT OR REPLACE INTO Manga VALUES ('12', 'Uzumaki',                         'Junji Ito',        'SEINEN-Terror',          'finalizado', 4.1,  3,   3)",
                "INSERT OR REPLACE INTO Manga VALUES ('13', 'Horimiya',                        'HERO/Daisuke Hagiwara','SHONEN-Romance',     'finalizado', 3.7, 16,  16)",
                "INSERT OR REPLACE INTO Manga VALUES ('14', 'Black Clover',                    'Yuki Tabata',      'SHONEN-Fantasia',        'activo',     4.6, 36,  36)",
                "INSERT OR REPLACE INTO Manga VALUES ('15', 'Bleach',                          'Tite Kubo',        'SHONEN-Accion',          'finalizado', 4.6, 74,  74)"
        };

        for (String insert : inserts) {
            st.execute(insert);
        }

        st.close();
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean insertarManga(Manga manga) throws SQLException {
        boolean result = false;
        String sql = """
                INSERT INTO Manga (idObra, titulo, autor, genero, estado, estrellas, stock, volumen) VALUES (?,?,?,?,?,?,?,?);
                """;

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, manga.getIdObra());
        pst.setString(2, manga.getTitulo());
        pst.setString(3, manga.getAutor().getNombre() + "," + manga.getAutor().getPais());
        pst.setString(4, manga.getGenero().getGenero() + "-" + manga.getGenero().getDescripcion()); 
        pst.setString(5, manga.getEstado());
        pst.setDouble(6, manga.getEstrellas());
        pst.setInt(7, manga.getStock());
        pst.setInt(8, manga.getVolumen());

        pst.executeUpdate();

        pst.close();
        return result;

    }

    public List<Manga> listarMangas() throws SQLException {

        String sql = """
                    SELECT mng.idObra,
                            mng.titulo,
                            mng.autor,
                            mng.genero,
                            mng.estado,
                            mng.estrellas,
                            mng.stock,
                            mng.volumen
                    FROM   Manga mng

                """;

        List<Manga> lista = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String[] parAutor = rs.getString("autor").split(","); 
                String[] parGenero = rs.getString("genero").split("-"); 

                Autor autor = new Autor(parAutor[0], parAutor.length > 1 ? parAutor[1] : "");
                Genero genero = new Genero(TiposGenero.valueOf(parGenero[0]), parGenero.length > 1 ? parGenero[1] : "");

                lista.add(new Manga(rs.getString("idObra"), rs.getString("titulo"),
                        autor, genero, rs.getString("estado"),
                        rs.getDouble("estrellas"), rs.getInt("stock"), rs.getInt("volumen")));
            }

        }
        return lista;
    }

}
