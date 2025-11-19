package Facturacion;

import ConexionBaseDeDatos.Inventario;

public class ItemFactura {
    private Inventario producto;  // aquí va inventario
    private int cantidad;
    private double subtotal;

    public ItemFactura(Inventario producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecio() * cantidad;
    }

    public Inventario getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return subtotal; }


    @Override
    public String toString() {
        return producto.getNombreprodu() + " x" + cantidad + " = $" + subtotal;
    }
}
