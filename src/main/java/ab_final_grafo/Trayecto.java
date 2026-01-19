package ab_final_grafo;

/**
 * Representa una conexión entre dos direcciones con un tiempo de viaje
 */
public class Trayecto {

    private String dir1; // Nombre de la dirección origen
    private String dir2; // Nombre de la dirección destino
    private double time; // Tiempo en minutos

    // Constructor vacío para Jackson (JSON)
    public Trayecto() {}

    public Trayecto(String dir1, String dir2, double time) {
        this.dir1 = dir1;
        this.dir2 = dir2;
        this.time = time;
    }

    // Getters y Setters
    public String getDir1() {
        return dir1;
    }

    public void setDir1(String dir1) {
        this.dir1 = dir1;
    }

    public String getDir2() {
        return dir2;
    }

    public void setDir2(String dir2) {
        this.dir2 = dir2;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return dir1 + " -> " + dir2 + " (" + time + " min)";
    }
}