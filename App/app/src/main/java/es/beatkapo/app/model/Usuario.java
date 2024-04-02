package es.beatkapo.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private TipoUsuario tipoUsuario;

}
