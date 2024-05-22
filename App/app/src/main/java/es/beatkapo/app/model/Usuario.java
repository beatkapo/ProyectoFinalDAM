package es.beatkapo.app.model;
/**
 * Clase que representa un usuario en el sistema.
 */
public class Usuario {
    private String id; // Identificador único del usuario
    private String nombre; // Nombre del usuario
    private String apellidos; // Apellidos del usuario
    private String telefono1; // Teléfono principal del usuario
    private String telefono2; // Teléfono secundario del usuario
    private String email; // Correo electrónico del usuario
    private String password; // Contraseña del usuario
    private String direccion1; // Dirección principal del usuario
    private String direccion2; // Dirección secundaria del usuario
    private int userType; // Tipo de usuario (0: Usuario normal, 1: Trabajador, 2: Administrador)
    private String imagen; // Imagen de perfil del usuario

    /**
     * Constructor de la clase Usuario.
     *
     * @param id Identificador único del usuario.
     * @param nombre Nombre del usuario.
     * @param apellidos Apellidos del usuario.
     * @param telefono1 Primer número de teléfono del usuario.
     * @param telefono2 Segundo número de teléfono del usuario.
     * @param email Dirección de correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param direccion1 Dirección principal del usuario.
     * @param direccion2 Dirección secundaria del usuario.
     * @param tipoUsuario Tipo de usuario (0: Normal, 1: Moderador, 2: Administrador).
     * @param imagen URL de la imagen de perfil del usuario.
     */
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
        this.userType = tipoUsuario;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public boolean esAdministrador() {
        return this.getUserType() == 2;
    }

}
