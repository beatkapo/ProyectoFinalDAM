const { initializeApp } = require('firebase/app');
const { getFirestore, collection, query, where, getDocs, addDoc } = require('firebase/firestore');
const { firebaseConfig } = require("./config");
const express = require('express');
const expressApp = express();
const PORT = 3000;

const firebaseApp = initializeApp(firebaseConfig);
const db = getFirestore(firebaseApp);

expressApp.use(express.json());
async function findUserByPhoneNumber(phoneNumber) {
    try {
        // Realiza una consulta a la colección "users" donde el campo "phoneNumber" sea igual al número proporcionado
        const q = query(collection(db, 'usuarios'), where('telefono1', '==', phoneNumber));
        // Obtiene los documentos que coinciden con la consulta
        const querySnapshot = await getDocs(q);

        // Array para almacenar los resultados
        const users = [];

        // Itera sobre los documentos encontrados y los agrega al array de usuarios
        querySnapshot.forEach((doc) => {
            users.push({ id: doc.id, data: doc.data() });
        });

        return users; // Devuelve el array de usuarios encontrados
    } catch (error) {
        console.error('Error buscando usuario por número de teléfono:', error);
        throw error;
    }
}

async function registerUser(user){
    try {
        // Añade un nuevo documento a la colección "users" con los datos del usuario
        const docRef = await addDoc(collection(db, 'usuarios'), user);
        console.log('Usuario registrado con ID:', docRef.id); // Muestra el ID del documento creado
        return docRef.id; // Devuelve el ID del documento creado
    } catch (error) {
        console.error('Error registrando usuario:', error);
        throw error;
    }
}
expressApp.get('/api', (req, res) => {
    findUserByPhoneNumber(666777888).then((users) => {
        res.json(users);
    }).catch((error) => {
        res.status(500).send('Error buscando usuario por número de teléfono: ' + error);
    });
});
expressApp.post('/api/register', (req, res) => {
    registerUser(req.body).then((userId) => {
        res.json({ userId });
    }).catch((error) => {
        res.status(500).send('Error registrando usuario: ' + error);
    });
});
expressApp.listen(3000, () => {
    console.log('Server running on port '+PORT);
});
