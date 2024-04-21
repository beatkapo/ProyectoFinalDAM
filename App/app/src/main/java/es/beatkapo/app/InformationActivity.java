package es.beatkapo.app;

import android.os.Bundle;
import android.widget.EditText;

public class InformationActivity extends BaseActivity {
    EditText name, email, phone, address;
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

}