package es.beatkapo.app.model;

public class Usuario {
    private String id;
    private String nombre;
    private String apellidos;
    private String telefono1;
    private String telefono2;
    private String email;
    private String password;
    private String direccion1;
    private String direccion2;
    private int tipoUsuario;
    private String imagen;

    public Usuario(String id, String nombre, String apellidos, String telefono1, String telefono2, String email, String password, String direccion1, String direccion2, int tipoUsuario, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.email = email;
        this.password = password;
        this.direccion1 = direccion1;
        this.direccion2 = direccion2;
        this.tipoUsuario = tipoUsuario;
        this.imagen = imagen;
    }

    public Usuario() {
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean esAdministrador() {
        return this.getTipoUsuario() == 2;
    }

}
