package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DirectorDAO {
    private String path;

    // Constructor que recibe la ruta de la base de datos
    public DirectorDAO(String path){
        this.path = path;
    }
    
    // Método para insertar un director en la base de datos
    public void insertar(Director direc) throws SQLException {
        String sql = """
            INSERT into directores(nombre, url_foto, url_web)
            VALUES(?,?,?);
            """;

        Connection conn = new Utilidades().getConnection(path);
        PreparedStatement sentenciaSQL = conn.prepareStatement(sql);

        sentenciaSQL.setString(1, direc.getNombre());
        sentenciaSQL.setString(2, direc.getUrlFoto());
        sentenciaSQL.setString(3, direc.getUrlWeb());

        sentenciaSQL.executeUpdate();
        conn.close();
    }

    // Método para obtener todos los directores de la base de datos
    public ArrayList<Director> dameTodos()  throws SQLException{
        ArrayList<Director> director = new ArrayList<>();
        String sql = "SELECT * FROM directores";

        Connection conn = new Utilidades().getConnection(path);
        Statement sentenciaSQL = conn.createStatement();
        ResultSet rs = sentenciaSQL.executeQuery(sql);

        while(rs.next()) {
            Director directors = new Director(
                rs.getInt("id"), 
                rs.getString("nombre"), 
                rs.getString("url_foto"), 
                rs.getString("url_web"));
            director.add(directors);
        }
        conn.close();
        return director;
    }

    // Método para buscar un director por su ID
    public Director buscarPor(int directorId) throws SQLException {
        String sql = "SELECT * FROM directores WHERE id = ?";
        Connection conn = new Utilidades().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, directorId);
        ResultSet rs = statement.executeQuery();

        Director director = null;
        if (rs.next()) {
            director = new Director();
            director.setId(rs.getInt("id"));
            director.setNombre(rs.getString("nombre"));
            director.setUrlFoto(rs.getString("url_foto"));
            director.setUrlWeb(rs.getString("url_web"));
        }

        conn.close();
        return director;
    }

    // Método para buscar un director por su nombre
    public Director buscarPor(String directorNom) throws SQLException {
        String sql = "SELECT * FROM directores WHERE nombre = ?";
        Connection conn = new Utilidades().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setString(1, directorNom);
        ResultSet rs = statement.executeQuery();

        Director director = null;
        if (rs.next()) {
            director = new Director();
            director.setId(rs.getInt("id"));
            director.setNombre(rs.getString("nombre"));
            director.setUrlFoto(rs.getString("url_foto"));
            director.setUrlWeb(rs.getString("url_web"));
        }

        conn.close();
        return director;
    }

    // Método para borrar un director por su ID
    public void borrar(int directorId) throws SQLException {
        String sql = "DELETE FROM directores WHERE id = ?";
        Connection conn = new Utilidades().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, directorId);
        statement.executeUpdate();
        conn.close();
    }

    // Método para modificar un director
    public void modificar(Director director) throws SQLException {
        String sql = """
            UPDATE directores
            SET nombre = ?, url_foto = ?, url_web = ?
            WHERE id = ?;
            """;
    
        Connection conn = new Utilidades().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setString(1, director.getNombre());
        statement.setString(2, director.getUrlFoto());
        statement.setString(3, director.getUrlWeb());
        statement.setInt(4, director.getId());
        
        statement.executeUpdate();
        conn.close();
    }
    
}
