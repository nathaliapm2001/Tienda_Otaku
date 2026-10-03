package Tienda_Otaku_Paquete.datos;

import Tienda_Otaku_Paquete.modelo.Manhwa;
import Tienda_Otaku_Paquete.modelo.TiposGenero;
import Tienda_Otaku_Paquete.modelo.Autor;
import Tienda_Otaku_Paquete.modelo.Genero;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLManhwa {
    private final String URL = "jdbc:sqlite:mi_base_manhwa.db";

    private Connection connection;

    public SQLManhwa() throws SQLException {
        this.connection = DriverManager.getConnection(URL);

        crearTabla();

    }

    private void crearTabla() throws SQLException {

        String sql = """

                    CREATE TABLE IF NOT EXISTS Manhwa (
                    idObra      TEXT PRIMARY KEY,
                    titulo      TEXT NOT NULL,
                    autor       TEXT NOT NULL,
                    genero      TEXT,
                    estado      TEXT,
                    estrellas   DOUBLE,
                    stock       INTEGER,
                    capitulo    INTEGER
                ); """;

        

        Statement st = connection.createStatement();
        st.execute(sql);

        String[] inserts = {
                "INSERT OR REPLACE INTO Manhwa VALUES ('1',  'The Boxer',                        'JH',            'SHONEN-Deporte/Psicologico',  'finalizado', 4.8,  20,  123)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('2',  'Solo Leveling',                    'Chugong',       'SHONEN-Accion/Fantasia',      'finalizado', 5.0,  20,  179)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('3',  'Omniscient Reader''s Viewpoint',   'Sing Shong',    'SEINEN-Accion/Fantasia',      'activo',     4.9, 200,  200)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('4',  'Tower of God',                     'SIU',           'SHONEN-Aventura/Fantasia',    'activo',     4.7, 600,  600)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('5',  'Sweet Home',                       'Carnby Kim',    'SEINEN-Terror',               'finalizado', 4.5, 141,  141)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('6',  'Bastard',                          'Carnby Kim',    'SEINEN-Psicologico',          'finalizado', 4.6,  94,   94)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('7',  'Lookism',                          'Park Tae-jun',  'SHONEN-Drama',                'activo',     4.4, 500,  500)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('8',  'The Beginning After the End',      'TurtleMe',      'SHONEN-Isekai/Fantasia',      'activo',     4.7, 180,  180)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('9',  'Eleceed',                          'Son Jae-ho',    'SHONEN-Accion',               'activo',     4.6, 250,  250)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('10', 'Hardcore Leveling Warrior',        'Sehoon Kim',    'SHONEN-Videojuegos',          'finalizado', 4.3,  37,  318)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('11', 'True Beauty',                      'Yaongyi',       'SHONEN-Romance',              'finalizado', 4.2, 223,  223)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('12', 'Gosu',                             'Ryu Gi-woon',   'SHONEN-Artes marciales',      'finalizado', 4.5,  23,  233)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('13', 'Dice: The Cube That Changes Everything', 'Yun Hyunseok', 'SEINEN-Psicologico/Fantasia', 'finalizado', 4.1, 38, 388)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('14', 'Killstagram',                      'Ryu Jin',       'SEINEN-Terror/Thriller',      'finalizado', 4.0,  10,  100)",
                "INSERT OR REPLACE INTO Manhwa VALUES ('15', 'I Love Yoo',                       'Quimchee',      'SHONEN-Romance/Drama',        'activo',     4.3,  20,  250)"
        };

        for (String insert : inserts) {
            st.execute(insert);
        }

        st.close();
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean insertarManhwa(Manhwa manhwa) throws SQLException {
        boolean result = false;
        String sql = """
                INSERT INTO Manhwa (idObra, titulo, autor, genero, estado, estrellas, stock, capitulo) VALUES (?,?,?,?,?,?,?,?);
                """;

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, manhwa.getIdObra());
        pst.setString(2, manhwa.getTitulo());
        pst.setString(3, manhwa.getAutor().getNombre() + "," + manhwa.getAutor().getPais());
        pst.setString(4, manhwa.getGenero().getGenero() + "-" + manhwa.getGenero().getDescripcion()); 
        pst.setString(5, manhwa.getEstado());
        pst.setDouble(6, manhwa.getEstrellas());
        pst.setInt(7, manhwa.getStock());
        pst.setInt(8, manhwa.getCapitulo());

        pst.executeUpdate();

        pst.close();
        return result;

    }

    public List<Manhwa> listarManhwas() throws SQLException {

        String sql = """
                    SELECT mnhw.idObra,
                            mnhw.titulo,
                            mnhw.autor,
                            mnhw.genero,
                            mnhw.estado,
                            mnhw.estrellas,
                            mnhw.stock,
                            mnhw.capitulo
                    FROM   Manhwa mnhw

                """;

        List<Manhwa> lista = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String[] parAutor = rs.getString("autor").split(",");
                String[] parGenero = rs.getString("genero").split("-");

                Autor autor = new Autor(parAutor[0], parAutor.length > 1 ? parAutor[1] : "");
                Genero genero = new Genero(TiposGenero.valueOf(parGenero[0]), parGenero.length > 1 ? parGenero[1] : "");

                lista.add(new Manhwa(rs.getString("idObra"), rs.getString("titulo"),
                        autor, genero, rs.getString("estado"),
                        rs.getDouble("estrellas"), rs.getInt("stock"), rs.getInt("capitulo"))); 
                                                                                               
            }

        }
        return lista;
    }

}