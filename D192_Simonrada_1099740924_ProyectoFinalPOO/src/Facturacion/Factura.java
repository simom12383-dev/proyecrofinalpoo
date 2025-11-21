package Facturacion;
import ConexionBaseDeDatos.Inventario;

import java.util.ArrayList;
import java.util.List;

public class Factura {
    private int idFactura;
    private String fecha;
    private List<ItemFactura> items;   // Composición: items NO existen sin la factura

    public Factura(int idFactura, String fecha) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.items = new ArrayList<>();
    }

    // Agregación -> Factura usa Inventario para crear items
    public void agregarItem(Inventario producto, int cantidad) {
        ItemFactura item = new ItemFactura(producto, cantidad);
        items.add(item);
    }

    public int calcularTotal() {
        int total = 0;
        for (ItemFactura item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void mostrarFactura() {
        System.out.println("====================================");
        System.out.println("FACTURA #" + idFactura);
        System.out.println("Fecha: " + fecha);
        System.out.println("Items:");

        for (ItemFactura item : items) {
            System.out.println(item);
        }

        System.out.println("------------------------------------");
        System.out.println("TOTAL A PAGAR: " + calcularTotal());
        System.out.println("====================================");
    }
}