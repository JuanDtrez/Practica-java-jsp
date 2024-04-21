package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import src.model.Director;
import src.model.Genero;
import src.model.Pelicula;

public class TestModelo {
    @Test
    public void testCrearGenero() {
        Genero gen = Genero.ACCION;
        assertEquals(Genero.ACCION, gen);
    }

    @Test
    public void testCrearDirector(){
        Director director = new Director(1,"Steven Spielberg", "url_foto_spielberg", "url_web_spielberg");
        director.setNombre("Steven Spielberg");

        assertEquals("Steven Spielberg", director.getNombre());
    }

    @Test
    public void testCrearPelicula(){
        
        Director director = new Director(1,"Steven Spielberg", "url_foto_spielberg", "url_web_spielberg");
        Pelicula pelicula = new Pelicula("Jurassic Park", director, 1993, "http://url_caratula.com", Genero.ACCION, false);
                            

        assertEquals("Jurassic Park", pelicula.getTitulo());
        assertEquals(1993, pelicula.getAnyo());
        assertEquals("http://url_caratula.com", pelicula.getUrlCaratula());
        assertEquals(Genero.ACCION, pelicula.getGenero());
        assertEquals(false, pelicula.isEsAnimacion());
        assertEquals("Steven Spielberg", pelicula.getDirector().getNombre());
    }

    @Test
    public void testGeneroConIdsAsociados(){
        Genero Sci_Fi = Genero.CIENCIA_FICCION;
        Genero musica = Genero.MUSICAL_DANZA;
        Genero romance = Genero.ROMANCE;

        assertEquals(Sci_Fi.getId(), 7);
        assertEquals(musica.getId(), 8);
        assertEquals(romance.getId(), 13);
    }
}