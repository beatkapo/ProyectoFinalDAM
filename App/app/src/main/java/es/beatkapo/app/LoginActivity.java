package es.beatkapo.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.LoginResponse;
import es.beatkapo.app.service.LoginService;
import es.beatkapo.app.util.ServiceUtils;
import es.beatkapo.app.util.Utilidades;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private Button register;
    private TextView forgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeComponents();
    }

    private void initializeComponents() {
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton_login);
        forgotPassword = findViewById(R.id.forgotPassword);
    }

    public void login(View v){
        Context context = this;
        Activity activity = this;
        if(validateFields()){
            LoginService loginService = new LoginService();
            Usuario usuario = new Usuario();
            usuario.setEmail(email.getText().toString());
            String encryptedPassword = Utilidades.encryptPassword(password.getText().toString());
            usuario.setPassword(encryptedPassword);
            loginService.login(usuario, response -> {
                LoginResponse loginResponse = (LoginResponse) response;
                if(loginResponse.isError()){
                    if(loginResponse.getErrorCode() == 0) {
                        Utilidades.showAlert(activity, getString(R.string.registerQuestionTitle), getString(R.string.registerQuestionMessage), getString(R.string.accept), (a,e) -> {
                            Intent intent = new Intent(context, RegisterActivity.class);
                            startActivity(intent);
                        }, getString(R.string.cancel), null);
                    }else if(loginResponse.getErrorCode() == 1) {
                        activity.runOnUiThread(() -> {
                            password.clearFocus();
                            password.setError(getString(R.string.password_incorrect));
                            password.requestFocus();
                        });
                    }
                }else{

                    //Guardar token en SharedPreferences
                    String token = loginResponse.getToken();
                    SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token);
                    editor.apply();
                    //Aplicar token en ServiceUtils
                    ServiceUtils.setToken(token);
                    //Ir a la pantalla principal
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);

                    finish();
                }
            }, ex -> {
                Utilidades.showAlert(this, getString(R.string.internalErrorTitle), getString(R.string.internalError_login), getString(R.string.accept), null, null, null);
                System.out.println("Error en el login");
            });
        }
    }
    public void register_login(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public boolean validateFields(){
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        if(emailStr.isEmpty()){
            email.setError(getString(R.string.email_required));
            return false;
        }else{//Validar que el email sea un email, que cumpla con el formato
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){//Android tiene un patrón para validar emails
                email.setError(getString(R.string.email_invalid));
                return false;
            }
        }
        if(passwordStr.isEmpty()){
            password.setError(getString(R.string.password_required));
            return false;
        }

        return true;
    }

}