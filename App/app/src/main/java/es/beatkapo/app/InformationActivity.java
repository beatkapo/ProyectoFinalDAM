package es.beatkapo.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.Response;
import es.beatkapo.app.service.UpdateUser;
import es.beatkapo.app.util.Utilidades;

public class InformationActivity extends BaseActivity {
    EditText name, email, phone, address;
    Button save, changePassword;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_information);
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initializeComponents(){
        super.initializeComponents();
        name = findViewById(R.id.information_name_text);
        email = findViewById(R.id.information_email_text);
        phone = findViewById(R.id.information_phone_text);
        address = findViewById(R.id.information_address_text);
        email.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String emailStr = email.getText().toString();
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                    email.setError("Email no válido");
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
        loadUserInfo();
    }

    private void loadUserInfo() {
        if(user != null){
            if(user.getNombre() != null){
                name.setText(user.getNombre());
            }
            if(user.getEmail() != null){
                email.setText(user.getEmail());
            }
            if(user.getTelefono1() != null){
                phone.setText(user.getTelefono1());
            }
            if(user.getDireccion1() != null){
                address.setText(user.getDireccion1());
            }
        }
    }
    public void changePassword(View v){
        //Dialog para cambiar contraseña, debe introducir la anterior y la nueva.
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_change_password);
        Button save, cancel;
        save = dialog.findViewById(R.id.dialog_change_password_cancel);
        cancel = dialog.findViewById(R.id.dialog_change_password_cancel);
        EditText oldPassword, newPassword, newPasswordRepeat;
        oldPassword = dialog.findViewById(R.id.old_password);
        newPassword = dialog.findViewById(R.id.new_password);
        newPasswordRepeat = dialog.findViewById(R.id.new_password_confirm);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validar que las contraseñas coincidan y que la anterior sea correcta.
                String newPasswordString = newPassword.getText().toString();
                String newPasswordRepeatString = newPasswordRepeat.getText().toString();
                if(newPasswordString.equals(newPasswordRepeatString)) {
                    UpdateUser updateUser = new UpdateUser();
                    Usuario userUpdated = user;
                    userUpdated.setPassword(Utilidades.encryptPassword(newPasswordString));
                    updateUser.changePassword(userUpdated, response ->{
                        if(response!=null){
                            Response r = (Response) response;
                            if(r.getErrorCode() == 200){
                                user.setPassword(userUpdated.getPassword());
                                Utilidades.saveUser(user, context);
                                Utilidades.showAlert(activity,getString( R.string.passwordSuccessTitle), getString(R.string.success_passwordUser), getString(R.string.accept), (d,w)->{
                                    dialog.dismiss();
                                }, null, null  );
                            }else{
                                Utilidades.showAlert(activity,getString( R.string.errorPasswordTitle), getString(R.string.error_oldPasswordUser), getString(R.string.accept), null, null, null  );
                                oldPassword.setError(getString(R.string.error_oldPassword));
                            }
                        }

                    }, ex ->{
                        Utilidades.showAlert(activity,getString( R.string.internalErrorTitle), getString(R.string.internalError_updateUser), getString(R.string.accept), null, null, null  );
                    });
                }else{
                    newPassword.setError(getString(R.string.passwordsNotMatch));
                    newPasswordRepeat.setError(getString(R.string.passwordsNotMatch));
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void updateUser(View v){
        if(validateFields()){
            UpdateUser service = new UpdateUser();
            Usuario userUpdated = user;
            userUpdated.setNombre(name.getText().toString());
            userUpdated.setEmail(email.getText().toString());
            userUpdated.setTelefono1(phone.getText().toString());
            userUpdated.setDireccion1(address.getText().toString());
            service.updateUser(userUpdated, response -> {
                if(response!=null){
                    Response r = (Response) response;
                    if(r.getErrorCode() == 200){
                        user = userUpdated;
                        Utilidades.saveUser(user, context);
                        Utilidades.showAlert(activity,getString( R.string.updateSuccessTitle), getString(R.string.updateSuccess), getString(R.string.accept), null, null, null  );
                        finish();
                    }else{
                        Utilidades.showAlert(activity,getString( R.string.internalErrorTitle), getString(R.string.internalError_updateUser), getString(R.string.accept), null, null, null  );
                    }
                }
            }, ex ->{
                Utilidades.showAlert(activity,getString( R.string.internalErrorTitle), getString(R.string.internalError_updateUser), getString(R.string.accept), null, null, null  );
            });
        }
    }

    private boolean validateFields() {
        boolean isValid = true;
        if(name.getText().toString().isEmpty()){
            name.setError(getString(R.string.error_emptyField));
            isValid = false;
        }
        if(email.getText().toString().isEmpty()){
            email.setError(getString(R.string.error_emptyField));
            isValid = false;
        }
        if(phone.getText().toString().isEmpty()){
            phone.setError(getString(R.string.error_emptyField));
            isValid = false;
        }
        if(address.getText().toString().isEmpty()){
            address.setError(getString(R.string.error_emptyField));
            isValid = false;
        }
        if (email.getError() != null || phone.getError() != null) {
            isValid = false;
        }
        return isValid;
    }

}