package src.model;

public enum Genero {
    ACCION(1),
    AVENTURA(2),
    COMEDIA(3),
    DRAMA(4),
    FANTASIA(5),
    TERROR(6),
    CIENCIA_FICCION(7),
    MUSICAL_DANZA(8),
    SUSPENSE(9),
    WESTERN(10),
    DOCUMENTAL(11),
    BIOGRAFICO(12),
    ROMANCE(13);

    private int id;

    Genero(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Genero getById(int id) {
        for (Genero genero : values()) {
            if (genero.getId() == id) {
                return genero;
            }
        }
        throw new IllegalArgumentException("No existe ningún género con el ID proporcionado");
    }
}
