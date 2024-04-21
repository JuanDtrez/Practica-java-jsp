package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Test;

import src.model.Director;
import src.model.DirectorDAO;
import src.model.Utilidades;

public class TestDirectorDAO {
    @Test // Test para verificar si el controlador JDBC para SQLite está disponible
    public void testJDBCExist() {
        boolean Exist = false;
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Todo ha ido bien");
            Exist = true;
        } catch (ClassNotFoundException err) {
            System.out.println("SQLite JDBC no encontrado");
            err.printStackTrace();
        }

        assertTrue(Exist);
    }

    @Test // Test para verificar la creación de una conexión a una base de datos SQLite
    public void testCrearConexion() throws SQLException {
        Utilidades utils = new Utilidades();

        Connection conn = utils.getConnection("./data/pelis.sqlite");
        assertNotNull(conn);
        conn.close();

        conn = utils.getConnection("./lolailoylere/kakebo_tests.sqlite");
        assertNull(conn);
    } 


    @Test // Test para resetear el auto incremento en la tabla directores (inútil) <-- concatenado a testTraeTodos() 
    public void resetAutoIncrement() throws SQLException {
        String sql = "DELETE FROM sqlite_sequence WHERE name='directores';";
        Connection conn = new Utilidades().getConnection("./data/pelis.sqlite");
        Statement sentenciaSQL = conn.createStatement();

        sentenciaSQL.executeUpdate(sql);
        conn.close();
    }

    @Test // Test para Comprobar el funcionamiento del método dameTodos()
    public void testTraeTodos() throws SQLException {
        String sql = "DELETE FROM directores;" + "DELETE FROM sqlite_sequence WHERE name='directores';";
        Connection conn = new Utilidades().getConnection("./data/pelis.sqlite");
        Statement sentenciaSQL = conn.createStatement();

        sentenciaSQL.executeUpdate(sql);
        conn.close();

        DirectorDAO dao = new DirectorDAO("./data/pelis.sqlite");

        Director StevenSpielberg = new Director(1,"Steven Spielberg", "url_foto_spielberg", "url_web_spielberg");
        Director JamesCameron = new Director(2,"James Cameron","http://url_cameron.com", "http://web_cameron.com");
        Director PeterJackson = new Director(3, "Peter Jackson", "http://url_jackson.com", "http://web_jackson.com");
        Director JohnLasseter = new Director(4, "John Lasseter", "http://url_lasseter.com", "http://web_lasseter.com");

        dao.insertar(StevenSpielberg);
        dao.insertar(JamesCameron);
        dao.insertar(PeterJackson);
        dao.insertar(JohnLasseter);

        ArrayList<Director> directores = dao.dameTodos(); 

        assertEquals(4, directores.size());
    }

    @Test // Test para Comprobar el funcionamiento del método buscarPor(int directorId)
    public void testBuscarPorId() throws SQLException {
        DirectorDAO dao = new DirectorDAO("./data/pelis.sqlite");
        Director director = dao.buscarPor(4);

        assertEquals("John Lasseter", director.getNombre());
    }

    @Test // Test para Comprobar el funcionamiento del método buscarPor(String directorNom)
    public void testBuscarPorNombre() throws SQLException {
        DirectorDAO dao = new DirectorDAO("./data/pelis.sqlite");
        Director director = dao.buscarPor("Peter Jackson");

        assertEquals("http://url_jackson.com", director.getUrlFoto());
    }

    @Test // Test para Comprobar el funcionamiento del método borrar(int directorId)
    public void testBorrarPorId() throws SQLException {
        DirectorDAO dao = new DirectorDAO("./data/pelis.sqlite"); 
        dao.borrar(2);
    }

    @Test // Test para Comprobar el funcionamiento del método modificar(Director director)
    public void testModificar() throws SQLException {
        DirectorDAO dao = new DirectorDAO("./data/pelis.sqlite");

        Director directorModificado = new Director(1, "Steven Spielberg (Modificado)", "nueva_url_foto", "nueva_url_web");
        dao.modificar(directorModificado);

        Director directorObtenido = dao.buscarPor(1);

        assertEquals("Steven Spielberg (Modificado)", directorObtenido.getNombre());
        assertEquals("nueva_url_foto", directorObtenido.getUrlFoto());
        assertEquals("nueva_url_web", directorObtenido.getUrlWeb());
    }
}
