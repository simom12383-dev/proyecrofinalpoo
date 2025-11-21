package Inventario;

import ConexionBaseDeDatos.Inventario;

public class Camisa  extends Inventario {
    //atributos

    private String Talla;
    private String TipoTela;


    //metodos


    public Camisa() {
    }


    public Camisa(int id, String nombreprodu, int cantidad, int precio, String categoria, String fechaingreso, String estado, String talla, String tipoTela) {
        super(id, nombreprodu, cantidad, precio, categoria, fechaingreso, estado);
        Talla = talla;
        TipoTela = tipoTela;
    }

    public String getTalla() {
        return Talla;
    }

    public void setTalla(String talla) {
        Talla = talla;
    }

    public String getTipoTela() {
        return TipoTela;
    }

    public void setTipoTela(String tipoTela) {
        TipoTela = tipoTela;
    }
}