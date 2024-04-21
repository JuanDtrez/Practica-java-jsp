package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import src.model.Director;
import src.model.Genero;
import src.model.Pelicula;
import src.model.PeliculaDAO;
import src.model.Utilidades;

public class TestPeliculaDAO {
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
    
    @Test // Test para Comprobar el funcionamiento del método dameTodos()
    public void testTraeTodos() throws SQLException {
        String sql = "DELETE FROM peliculas; DELETE FROM sqlite_sequence WHERE name='peliculas';";
        Connection conn = new Utilidades().getConnection("./data/pelis.sqlite");
        conn.createStatement().executeUpdate(sql);
        conn.close();

        PeliculaDAO dao = new PeliculaDAO("./data/pelis.sqlite");

        Director stevenSpielberg = new Director(1,"Steven Spielberg", "url_foto_spielberg", "url_web_spielberg");
        Director jamesCameron = new Director(2,"James Cameron","http://url_cameron.com", "http://web_cameron.com");
        Director peterJackson = new Director(3, "Peter Jackson", "http://url_jackson.com", "http://web_jackson.com");
        Director johnLasseter = new Director(4, "John Lasseter", "http://url_lasseter.com", "http://web_lasseter.com");

        Pelicula peli1 = new Pelicula("Jurassic Park", stevenSpielberg, 1993, "http://url_caratula.com", Genero.ACCION, false);
        Pelicula peli2 = new Pelicula("Titanic", jamesCameron, 1997, "http://url_caratula2.com", Genero.DRAMA, false);
        Pelicula peli3 = new Pelicula("The Lord of the Rings: The Fellowship of the Ring", peterJackson, 2001, "http://url_caratula3.com", Genero.AVENTURA, false);
        Pelicula peliAnimacion = new Pelicula("Toy Story", johnLasseter, 1995, "http://url_caratula_animacion.com", Genero.FANTASIA, true);

        dao.insertar(peliAnimacion);
        dao.insertar(peli1);
        dao.insertar(peli2);
        dao.insertar(peli3);

        ArrayList<Pelicula> peliculas = dao.dameTodos(); 

        assertEquals(4, peliculas.size());

        assertEquals("Titanic", peliculas.get(2).getTitulo());
        assertEquals("The Lord of the Rings: The Fellowship of the Ring", peliculas.get(1).getTitulo());
        assertEquals("Toy Story", peliculas.get(3).getTitulo());
        assertEquals("Jurassic Park", peliculas.get(0).getTitulo());

        assertEquals(Genero.DRAMA, peliculas.get(2).getGenero());
        assertEquals(Genero.AVENTURA, peliculas.get(1).getGenero());
        assertEquals(Genero.FANTASIA, peliculas.get(3).getGenero());
        assertEquals(Genero.ACCION, peliculas.get(0).getGenero());
        
        assertEquals("James Cameron",peliculas.get(2).getDirector().getNombre());
        assertEquals("Peter Jackson",peliculas.get(1).getDirector().getNombre());
        assertEquals("John Lasseter",peliculas.get(3).getDirector().getNombre());
        assertEquals("Steven Spielberg",peliculas.get(0).getDirector().getNombre());
    }

    @Test // Test para Comprobar el funcionamiento del método buscarPor(int peliculaId)
    public void testBuscarPorId() throws SQLException {
        PeliculaDAO dao = new PeliculaDAO("./data/pelis.sqlite");
        Pelicula pelicula = dao.buscarPor(2);

        assertEquals("Jurassic Park", pelicula.getTitulo());
    }
    
    @Test // Test para Comprobar el funcionamiento del método buscarPor(String peliculaNom)
    public void testBuscarPorNomre() throws SQLException {
        PeliculaDAO dao = new PeliculaDAO("./data/pelis.sqlite");
        Pelicula pelicula = dao.buscarPor("Jurassic Park");

        assertEquals(1, pelicula.getGenero().getId());
    }

    @Test // Test para Comprobar el funcionamiento del método borrar(int peliculaId)
    public void testBorrarPorId() throws SQLException {
        PeliculaDAO dao = new PeliculaDAO("./data/pelis.sqlite"); 
        dao.borrar(4);
    }

    @Test // Test para verificar la funcionalidad del método modificar(Pelicula pelicula)
    public void testModificar() throws SQLException {
        PeliculaDAO dao = new PeliculaDAO("./data/pelis.sqlite");
    
        Pelicula peliculaExistente = dao.buscarPor(2);
    
        peliculaExistente.setTitulo("Jurassic Park (Modificado)");
        peliculaExistente.setAnyo(9999);
        peliculaExistente.setUrlCaratula("http://url_nueva.com");
        peliculaExistente.setGenero(Genero.DOCUMENTAL);
        peliculaExistente.setEsAnimacion(false);
    
        dao.modificar(peliculaExistente);
    
        Pelicula peliculaModificada = dao.buscarPor(2);
    
        assertEquals("Jurassic Park (Modificado)", peliculaModificada.getTitulo());
        assertEquals(9999, peliculaModificada.getAnyo());
        assertEquals("http://url_nueva.com", peliculaModificada.getUrlCaratula());
        assertEquals(Genero.DOCUMENTAL, peliculaModificada.getGenero());
        assertEquals(false, peliculaModificada.isEsAnimacion());
    }
    
    
    
}