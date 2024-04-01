const { initializeApp } = require('firebase/app');
const { getFirestore, collection, query, where, getDocs, addDoc } = require('firebase/firestore');
const { firebaseConfig, secretWord } = require("./config");
const jwt = require('jsonwebtoken');

const express = require('express');
const e = require('express');
const expressApp = express();
const PORT = 3000;

const firebaseApp = initializeApp(firebaseConfig);
const db = getFirestore(firebaseApp);

expressApp.use(express.json());
//Metodos relacionados con la autenticacion
async function generateToken(user) {
    try {
        const token = jwt.sign(user, secretWord, { expiresIn: '1h' });
        return token;
    } catch (error) {
        console.error('Error generando token:', error);
        throw error;
    }
}
async function verifyToken(token) {
    try {
        const decoded = jwt.verify(token, secretWord);
        return decoded;
    } catch (error) {
        console.error('Error verificando token:', error);
        throw error;
    }
}
async function registerUser(user) {
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
async function loginUser(user) {
    try {
        // Comprueba que el usuario existe en la base de datos
        const q = query(collection(db, 'usuarios'), where('email', '==', user.email));
        const querySnapshot = await getDocs(q);
        // Comprueba que la contraseña coincide
        if (querySnapshot.empty) {
            data = { error: true, message: 'Usuario no encontrado' };
            return data;
        }
        const doc = querySnapshot.docs[0];
        const userData = doc.data();
        if (userData.password === user.password) {
            data = { error: false, token: await generateToken(userData) };
        } else {
            data = { error: true, message: 'Contraseña incorrecta' };
        }
        return data;
    } catch (error) {
        console.error('Error interno al iniciar sesión:', error);
        throw error;
    }
}
//Metodos relacionados con gestionar los productos de la base de datos
async function getProductos() {
    try {
        const q = query(collection(db, 'productos'));
        const querySnapshot = await getDocs(q);
        const products = [];
        querySnapshot.forEach((doc) => {
            products.push(doc.data());
        });
        return products;
    } catch (error) {
        console.error('Error obteniendo productos:', error);
        throw error;
    }
}
async function getIngredientes(){
    try {
        const q = query(collection(db, 'ingredientes'));
        const querySnapshot = await getDocs(q);
        const ingredients = [];
        querySnapshot.forEach((doc) => {
            ingredients.push(doc.data());
        });
        return ingredients;
    } catch (error) {
        console.error('Error obteniendo ingredientes:', error);
        throw error;
    }
}
async function getIngredienteByID(id){

    try {
        doc(db, 'ingredientes', id).get().then((doc) => {
            if (doc.exists()) {
                ingredient = doc.data();
            } else {
                console.log('No such document!');
            }
        });
        return ingredient;
    } catch (error) {
        console.error('Error obteniendo ingredientes por ID:', error);
        throw error;
    }
}
async function getCategorias(){
    try {
        const q = query(collection(db, 'categorias'));
        const querySnapshot = await getDocs(q);
        const categories = [];
        querySnapshot.forEach((doc) => {
            categories.push(doc.data());
        });
        return categories;
    } catch (error) {
        console.error('Error obteniendo categorias:', error);
        throw error;
    }

}
async function getPedidos(){
    try {
        const q = query(collection(db, 'pedidos'));
        const querySnapshot = await getDocs(q);
        const orders = [];
        querySnapshot.forEach((doc) => {
            orders.push(doc.data());
        });
        return orders;
    } catch (error) {
        console.error('Error obteniendo pedidos:', error);
        throw error;
    }
}
async function getAlergenos(){
    try {
        const q = query(collection(db, 'alergenos'));
        const querySnapshot = await getDocs(q);
        const allergens = [];
        querySnapshot.forEach((doc) => {
            allergens.push(doc.data());
        });
        return allergens;
    } catch (error) {
        console.error('Error obteniendo alergenos:', error);
        throw error;
    }
}
async function getProductoByCategoria(categoria){
    try {
        const q = query(collection(db, 'productos'), where('categoria', '==', categoria));
        const querySnapshot = await getDocs(q);
        const products = [];
        querySnapshot.forEach((doc) => {
            products.push(doc.data());
        });
        return products;
    } catch (error) {
        console.error('Error obteniendo productos por categoria:', error);
        throw error;
    }

}
async function getProductoIngredientes(producto){
    //La relacion entre productos e ingredientes se hace a traves de la coleccion "ingrediente_producto"
    try {
        const q = query(collection(db, 'ingredientes_productos'), where('idProducto', '==', producto));
        const querySnapshot = await getDocs(q);
        const ingredients = [];
        querySnapshot.forEach((doc) => {//Para cada documento se hace una consulta a la coleccion de ingredientes para obtener el ingrediente
            const ingredient = getIngredienteByID(doc.data().idIngrediente);
            ingredients.push(ingredient);
        });
        return ingredients;
    } catch (error) {
        console.error('Error obteniendo ingredientes por producto:', error);
        throw error;
    }
}
//Rutas de la API
expressApp.post('/api/register', (req, res) => {
    registerUser(req.body).then((userId) => {
        res.json({ error: false, id: userId });
    }).catch((error) => {
        res.status(500).json({ error: true, message: 'Error registrando usuario: ' + error });
    });
});
expressApp.post('/api/login', (req, res) => {
    loginUser(req.body).then((data) => {
        console.log(req.body.email + " logueado correctamente.");
        res.json(data);
    }).catch((error) => {
        res.status(500).send('Error iniciando sesión: ' + error);
    });
});
expressApp.get('/api/productos', (req, res) => {
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const products = await getProductos();
        res.json(products);
    }).catch((error) => {
        res.status(401).send('Error verificando token: ' + error);

    });
});

expressApp.listen(3000, () => {
    console.log('Server running on port ' + PORT);
});
