package es.beatkapo.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import es.beatkapo.app.util.Utilidades;

public class TestEncryptPassword {
    @Test
    public void testEncryptPassword() {
        // Caso de prueba 1: Contraseña conocida
        String password = "mypassword";
        String expectedHash = "89e01536ac207279409d4de1e5253e01f4a1769e696db0d6062ca9b8f56767c8";
        String encryptedPassword = Utilidades.encryptPassword(password);
        assertEquals(expectedHash, encryptedPassword);

        // Caso de prueba 2: Contraseña vacía
        String emptyPassword = "";
        String expectedEmptyHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        assertEquals(expectedEmptyHash, Utilidades.encryptPassword(emptyPassword));

        // Caso de prueba 3: Contraseña nula
        String nullPassword = null;
        assertNull(Utilidades.encryptPassword(nullPassword));
    }
}
