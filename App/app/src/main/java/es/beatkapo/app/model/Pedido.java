package es.beatkapo.app.model;

import java.util.List;

public class Pedido {
    private String id;
    private String fecha;
    private String estado;
    private Usuario usuario;
    private List<LineaPedido> lineas;
    private double total;

    public Pedido(String id, String fecha, String estado, Usuario usuario, List<LineaPedido> lineas, double total) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.usuario = usuario;
        this.lineas = lineas;
        this.total = total;
    }

    public Pedido() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaPedido> lineas) {
        this.lineas = lineas;
    }

    public double getTotal() {
        double auxTotal = 0;
        for (LineaPedido linea : lineas) {
            auxTotal += linea.getTotal();
        }
        return auxTotal;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
