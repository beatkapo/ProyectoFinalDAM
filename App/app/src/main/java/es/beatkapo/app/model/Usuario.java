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

    /**
     * Constructor vacío para inicializar un objeto Usuario sin parámetros.
     */
    public Usuario() {
    }

    /**
     * Obtiene la URL de la imagen de perfil del usuario.
     * @return URL de la imagen de perfil del usuario.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la URL de la imagen de perfil del usuario.
     * @param imagen URL de la imagen de perfil del usuario.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el identificador único del usuario.
     * @return Identificador único del usuario.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único del usuario.
     * @param id Identificador único del usuario.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre Nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario.
     * @return Apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     * @param apellidos Apellidos del usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el primer número de teléfono del usuario.
     * @return Primer número de teléfono del usuario.
     */
    public String getTelefono1() {
        return telefono1;
    }

    /**
     * Establece el primer número de teléfono del usuario.
     * @param telefono1 Primer número de teléfono del usuario.
     */
    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    /**
     * Obtiene el segundo número de teléfono del usuario.
     * @return Segundo número de teléfono del usuario.
     */
    public String getTelefono2() {
        return telefono2;
    }

    /**
     * Establece el segundo número de teléfono del usuario.
     * @param telefono2 Segundo número de teléfono del usuario.
     */
    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return Correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     * @param email Correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return Contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * @param password Contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene la dirección principal del usuario.
     * @return Dirección principal del usuario.
     */
    public String getDireccion1() {
        return direccion1;
    }

    /**
     * Establece la dirección principal del usuario.
     * @param direccion1 Dirección principal del usuario.
     */
    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    /**
     * Obtiene la dirección secundaria del usuario.
     * @return Dirección secundaria del usuario.
     */
    public String getDireccion2() {
        return direccion2;
    }

    /**
     * Establece la dirección secundaria del usuario.
     * @param direccion2 Dirección secundaria del usuario.
     */
    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    /**
     * Obtiene el tipo de usuario.
     * @return Tipo de usuario (0: Usuario normal, 1: Trabajador, 2: Administrador).
     */
    public int getUserType() {
        return userType;
    }

    /**
     * Establece el tipo de usuario.
     * @param userType Tipo de usuario (0: Usuario normal, 1: Trabajador, 2: Administrador).
     */
    public void setUserType(int userType) {
        this.userType = userType;
    }

    /**
     * Verifica si el usuario es administrador.
     * @return true si el usuario es administrador, false en caso contrario.
     */
    public boolean esAdministrador() {
        return this.getUserType() == 2;
    }
}
