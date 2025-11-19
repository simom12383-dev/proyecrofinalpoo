package Inventario;

import ConexionBaseDeDatos.Inventario;

public class Pantalon extends Inventario {

    //metodos

    private String talla;
    private String color;


    //mmetodos


    public Pantalon() {
    }

    public Pantalon(int id, String nombreprodu, int cantidad, int precio, String categoria, String fechaingreso, String estado, String talla, String color) {
        super(id, nombreprodu, cantidad, precio, categoria, fechaingreso, estado);
        this.talla = talla;
        this.color = color;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
