package es.beatkapo.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import es.beatkapo.app.model.TipoUsuario;
import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.RegisterResponse;
import es.beatkapo.app.service.RegisterService;
import es.beatkapo.app.util.Utilidades;

public class RegisterActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText repeatPassword;
    private EditText name;
    private EditText address;
    private EditText phone;
    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeComponents();
        initializeListeners();
    }
    public void register(View v){
        if(validateFields()){
            //Crear objeto usuario
            Usuario usuario = new Usuario();
            usuario.setTipoUsuario(0);
            usuario.setEmail(email.getText().toString());
            usuario.setPassword(Utilidades.encryptPassword(password.getText().toString()));
            String nameStr = name.getText().toString();
            String addressStr = address.getText().toString();
            String phoneStr = phone.getText().toString();
            if(nameStr != null && !nameStr.isEmpty()){
                usuario.setNombre(nameStr);
            }
            if(addressStr != null && !addressStr.isEmpty()){
                usuario.setDireccion1(addressStr);
            }
            if(phoneStr != null && !phoneStr.isEmpty()) {
                usuario.setTelefono1(phoneStr);
            }
            //Llamar al servicio de registro
            RegisterService registerService = new RegisterService();
            registerService.register(usuario, response -> {
                //Si el registro es correcto, se muestra un mensaje y se cierra la actividad
                RegisterResponse registerResponse = (RegisterResponse) response;
                if(registerResponse.isError()){
                    Utilidades.showAlert(this, getString(R.string.internalErrorTitle), getString(R.string.internalError_register), getString(R.string.accept), (a,b) ->{
                        finish();
                    }, null, null);
                }else{
                    Utilidades.showAlert(this, getString(R.string.registerSuccessTitle), getString(R.string.registerSuccess), getString(R.string.accept), (a,b) ->{
                        finish();
                    }, null, null);
                }
            }, ex -> {
                //Si ocurre un error, se muestra un mensaje
                System.out.println("Error en el registro");
            });
        }else{
            Utilidades.showAlert(this, getString(R.string.registerErrorTitle), getString(R.string.registerError), getString(R.string.accept), null, null, null);
        }
    }

    private boolean validateFields() {
        if (email.getError() != null || password.getError() != null || repeatPassword.getError() != null) {
            return false;
        }
        return true;
    }

    private void initializeListeners() {
        //Listeners de los campos, al cambiar de campo se comprueba si el campo es correcto
        email.setOnFocusChangeListener((v, hasFocus) -> { //Listener para el campo email
            if (!hasFocus) {
                String emailStr = email.getText().toString();
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                    email.setError("Email no válido");
                }
            }
        });
        password.setOnFocusChangeListener((v, hasFocus) -> { //Listener para el campo password
            if (!hasFocus) {
                String passwordStr = password.getText().toString();
                if (passwordStr.length() < 8) {
                    password.setError("La contraseña debe tener al menos 8 caracteres");
                }
            }
        });
        repeatPassword.setOnFocusChangeListener((v, hasFocus) -> { //Listener para el campo repeatPassword
            if (!hasFocus) {
                String passwordStr = password.getText().toString();
                String repeatPasswordStr = repeatPassword.getText().toString();
                if (!passwordStr.equals(repeatPasswordStr)) {
                    repeatPassword.setError("Las contraseñas no coinciden");
                }
            }
        });
        phone.setOnFocusChangeListener((v, hasFocus) -> { //Listener para el campo phone
            if (!hasFocus) {
                String phoneStr = phone.getText().toString();
                if (phoneStr.length() != 9) {
                    phone.setError("El teléfono debe tener 9 dígitos");
                }
            }
        });
    }

    private void initializeComponents() {
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        repeatPassword = findViewById(R.id.repeatPassword_register);
        name = findViewById(R.id.name_register);
        address = findViewById(R.id.address1_register);
        phone = findViewById(R.id.phone1_register);
        register = findViewById(R.id.registerButton_register);
    }



}