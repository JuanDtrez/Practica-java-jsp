package src.model;

public class Pelicula {
    private int id;
    private String titulo;
    private int anyo;
    private String urlCaratula;
    private boolean esAnimacion;
    private Genero genero;
    private Director director;

    public Pelicula (){}
    
    public Pelicula(String titulo, Director director, int anyo, String urlCaratula, Genero genero, boolean esAnimacion) {
        this.titulo = titulo;
        this.director = director;
        this.anyo = anyo;
        this.urlCaratula = urlCaratula;
        this.genero = genero;
        this.esAnimacion = esAnimacion;
    }    

    public Pelicula(int id, String titulo, Director director, int anyo, String urlCaratula, Genero genero, boolean esAnimacion) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anyo = anyo;
        this.urlCaratula = urlCaratula;
        this.genero = genero;
        this.esAnimacion = esAnimacion;
    }

    public Pelicula(int id, String titulo, int anyo, String urlCaratula, Genero genero, Director director) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anyo = anyo;
        this.urlCaratula = urlCaratula;
        this.genero = genero;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public int getAnyo() {
        return anyo;
    }
    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }
    public String getUrlCaratula() {
        return urlCaratula;
    }
    public void setUrlCaratula(String urlCaratula) {
        this.urlCaratula = urlCaratula;
    }
    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    public boolean isEsAnimacion() {
        return esAnimacion;
    }
    public void setEsAnimacion(boolean esAnimacion) {
        this.esAnimacion = esAnimacion;
    }
    public Director getDirector() {
        return director;
    }
    public void setDirector(Director director) {
        this.director = director;
    }
}

