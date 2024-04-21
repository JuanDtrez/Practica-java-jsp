package src.model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PeliculaDAO {
    private String path;

    // Constructor que recibe la ruta de la base de datos
    public PeliculaDAO(String path){
        this.path = path;
    }   

    // Método para insertar una pelicula en la base de datos
    public void insertar(Pelicula peli) throws SQLException {
        String sql = """
            INSERT into peliculas(titulo, id_director, anyo, url_caratula, id_genero, es_animacion)
            VALUES(?,?,?,?,?,?);
            """;

        Connection conn = new Utilidades().getConnection(path);
        PreparedStatement sentenciaSQL = conn.prepareStatement(sql);

        sentenciaSQL.setString(1, peli.getTitulo());
        sentenciaSQL.setInt(2, peli.getDirector().getId());
        sentenciaSQL.setInt(3, peli.getAnyo());
        sentenciaSQL.setString(4, peli.getUrlCaratula());
        sentenciaSQL.setInt(5, peli.getGenero().getId());
        sentenciaSQL.setString(6, peli.isEsAnimacion() ? "S" : "N");

        sentenciaSQL.executeUpdate();
        conn.close();
    }
    
    // Método para obtener todos las peliculas de la base de datos
    public ArrayList<Pelicula> dameTodos() throws SQLException {
        ArrayList<Pelicula> peliculas = new ArrayList<>();
    
        String sql = "SELECT p.id, p.titulo, p.anyo, p.url_caratula, p.id_genero, g.descripcion AS genero, p.id_director, d.nombre AS director " +
                    "FROM peliculas p " +
                    "JOIN generos g ON p.id_genero = g.id " +
                    "JOIN directores d ON p.id_director = d.id " +
                    "ORDER BY g.descripcion ASC, p.anyo DESC, p.titulo ASC;";

        Connection conn = new Utilidades().getConnection(path);
        Statement sentenciaSQL = conn.createStatement();
        ResultSet rs = sentenciaSQL.executeQuery(sql);
        
        // Crear una instancia de DirectorDAO
        DirectorDAO directorDAO = new DirectorDAO(path);
    
        while(rs.next()) {
            Pelicula pelicula = new Pelicula(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getInt("anyo"),
                    rs.getString("url_caratula"),
                    Genero.getById(rs.getInt("id_genero")),
                    directorDAO.buscarPor(rs.getInt("id_director")));            

            peliculas.add(pelicula);
        }
        conn.close();
        return peliculas;
    }
    // Método para buscar una pelicula por su ID
    public Pelicula buscarPor(int peliculaId) throws SQLException {
        String sql = "SELECT * FROM peliculas WHERE id = ?";
        Connection conn = new Utilidades().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, peliculaId);
        ResultSet rs = statement.executeQuery();

        // Crear una instancia de DirectorDAO
        DirectorDAO directorDAO = new DirectorDAO(path);

        Pelicula pelicula = null;
        if (rs.next()) {
            pelicula = new Pelicula(
                rs.getInt("id"),
                rs.getString("titulo"),
                directorDAO.buscarPor(rs.getInt("id_director")),
                rs.getInt("anyo"),
                rs.getString("url_caratula"),
                Genero.getById(rs.getInt("id_genero")),
                rs.getString("es_animacion").equals("S"));
        }

        conn.close();
        return pelicula;
    }

    // Método para buscar una pelicula por su nombre
    public Pelicula buscarPor(String peliculaNom) throws SQLException {
        String sql = "SELECT * FROM peliculas WHERE titulo = ?";
        Connection conn = new Utilidades().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setString(1, peliculaNom);
        ResultSet rs = statement.executeQuery();

        // Crear una instancia de DirectorDAO
        DirectorDAO directorDAO = new DirectorDAO(path);

        Pelicula pelicula = null;
        if (rs.next()) {
            pelicula = new Pelicula(
                rs.getInt("id"),
                rs.getString("titulo"),
                directorDAO.buscarPor(rs.getInt("id_director")),
                rs.getInt("anyo"),
                rs.getString("url_caratula"),
                Genero.getById(rs.getInt("id_genero")),
                rs.getString("es_animacion").equals("S"));
        }

        conn.close();
        return pelicula;
    }

    // Método para borrar una pelicula por su ID
    public void borrar(int peliculaId) throws SQLException {
        String sql = "DELETE FROM peliculas WHERE id = ?";
        Connection conn = new Utilidades().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, peliculaId);
        statement.executeUpdate();
        conn.close();
    }

    // Método para modificar una pelicula
    public void modificar(Pelicula pelicula) throws SQLException {
        String sql = """
            UPDATE peliculas
            SET titulo = ?, id_director = ?, anyo = ?, url_caratula = ?, id_genero = ?, es_animacion = ?
            WHERE id = ?;
            """;
    
        Connection conn = new Utilidades().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
    
        statement.setString(1, pelicula.getTitulo());
        statement.setInt(2, pelicula.getDirector().getId());
        statement.setInt(3, pelicula.getAnyo());
        statement.setString(4, pelicula.getUrlCaratula());
        statement.setInt(5, pelicula.getGenero().getId());
        statement.setString(6, pelicula.isEsAnimacion() ? "S" : "N");
        statement.setInt(7, pelicula.getId());
    
        statement.executeUpdate();
        conn.close();
    }  
    
}
