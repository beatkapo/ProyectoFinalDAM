const { initializeApp } = require('firebase/app');
const { getFirestore, collection, query, where, getDocs, addDoc, getDoc, doc } = require('firebase/firestore');
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
        const decoded = jwt.verify(token.substring(7), secretWord);
        return decoded;
    } catch (error) {
        const data = { error: true, message: 'Token inválido' };
        throw data;
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
            data = { error: true , errorCode: 0 , message: 'Usuario no encontrado' };
            return data;
        }
        const doc = querySnapshot.docs[0];
        const userData = doc.data();
        userData.id = doc.id;
        if (userData.password === user.password) {
            console.log('Usuario con id '+userData.id+' logueado:', userData.email);
            data = { error: false, token: await generateToken(userData) };
        } else {
            data = { error: true, errorCode: 1 ,message: 'Contraseña incorrecta' };
        }
        return data;
    } catch (error) {
        console.error('Error interno al iniciar sesión:', error);
        throw error;
    }
}
//Metodos relacionados con gestionar los productos y pedidos de la base de datos
async function getProductos() {
    try {
        const q = query(collection(db, 'productos'));
        const querySnapshot = await getDocs(q);
        
        const productsPromises = querySnapshot.docs.map(async (doc) => {
            const product = doc.data();
            product.id = doc.id;
            const ingredientes = await getProductoIngredientes(product.id);
            product.ingredientes = ingredientes;
            const categoria = await getCategoriaByID(product.categoria);
            product.categoria = categoria;
            return product;
        });

        const products = await Promise.all(productsPromises);

        return products;
    } catch (error) {
        console.error('Error obteniendo productos:', error);
        throw error;
    }
}
async function getProductoByID(id) {
    // Obtener producto por id
    try {
        const docRef = doc(db, 'productos', id);
        const docSnap = await getDoc(docRef);
        if(docSnap.exists()){
            product = docSnap.data();
            product.id = docSnap.id;
            const ingredientes = await getProductoIngredientes(product.id);
            product.ingredientes = ingredientes;
            const categoria = await getCategoriaByID(product.categoria);
            product.categoria = categoria;
            const response = { error: false, producto: product };
            return response;
        }else{
            const response = { error: true, message: 'Producto no encontrado' };
            return response;
        }
    } catch (error) {
        console.error('Error obteniendo producto por ID:', error);
        throw error;
    }

}
async function getIngredientes(){
    try {
        const q = query(collection(db, 'ingredientes'));
        const querySnapshot = await getDocs(q);
        const ingredients = [];
        querySnapshot.forEach((doc) => {
            data = doc.data();
            data.id = doc.id;
            ingredients.push(data);
        });
        return ingredients;
    } catch (error) {
        console.error('Error obteniendo ingredientes:', error);
        throw error;
    }
}
async function getIngredienteByID(id){

    try {
        const docRef = doc(db, 'ingredientes', id);
        const docSnap = await getDoc(docRef);
        if(docSnap.exists()){
            let ingredient = {...docSnap.data()}
            ingredient.id = docSnap.id;
            console.log('ID ingrediente antes de alergenos',ingredient.id);
            console.log('Antes de treure alergenos:',ingredient);
            
            const alergenos = await getAlergenosIngrediente(ingredient.id); 
            console.log('ID ingrediente despues de alergenos',ingredient.id);
            console.log('Despres de treure alergenos:',ingredient);
            console.log('Alergenos:',alergenos);
            ingredient.alergenos = alergenos;
            return ingredient;
        }else{
            return { error: true, message: 'Ingrediente no encontrado' };
        }

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
            data = doc.data();
            data.id = doc.id;
            categories.push(data);
        });
        return categories;
    } catch (error) {
        console.error('Error obteniendo categorias:', error);
        throw error;
    }

}
async function getCategoriaByID(id){
    try {
        const docRef = doc(db, 'categorias', id);
        const docSnap = await getDoc(docRef);
        if(docSnap.exists()){
            category = docSnap.data();
            category.id = docSnap.id;
            return category;
        }else{
            return { error: true, message: 'Categoria no encontrada' };
        }
    } catch (error) {
        console.error('Error obteniendo categorias por ID:', error);
        throw error;
    }
}
async function getPedidos(){
    try {
        const q = query(collection(db, 'pedidos'));
        const querySnapshot = await getDocs(q);
        const orders = [];
        querySnapshot.forEach((doc) => {
            data = doc.data();
            data.id = doc.id;
            orders.push(data);
        });
        return orders;
    } catch (error) {
        console.error('Error obteniendo pedidos:', error);
        throw error;
    }
}
async function getPedidoByClienteID(clienteID){
    try {
        const q = query(collection(db, 'pedidos'), where('cliente', '==', clienteID));
        const querySnapshot = await getDocs(q);
        const orders = [];
        querySnapshot.forEach((doc) => {
            data = doc.data();
            data.id = doc.id;
            orders.push(data);
        });
        return orders;
    } catch (error) {
        console.error('Error obteniendo pedidos por cliente:', error);
        throw error;
    }

}
async function getAlergenos(){
    try {
        const q = query(collection(db, 'alergenos'));
        const querySnapshot = await getDocs(q);
        const allergens = [];
        querySnapshot.forEach((doc) => {
            data = doc.data();
            data.id = doc.id;
            allergens.push(data);
        });
        return allergens;
    } catch (error) {
        console.error('Error obteniendo alergenos:', error);
        throw error;
    }
}
async function getAlergenoByID(id){
    try {
        const docRef = doc(db, 'alergenos', id);
        const docSnap = await getDoc(docRef);
        if(docSnap.exists()){
            allergen = docSnap.data();
            allergen.id = docSnap.id;
            return allergen;
        }else{
            return { error: true, message: 'Alergeno no encontrado' };
        }
    } catch (error) {
        console.error('Error obteniendo alergenos por ID:', error);
        throw error;
    }

}
async function getProductosByCategoria(categoria){
    try {
        const q = query(collection(db, 'productos'), where('categoria', '==', categoria));
        const querySnapshot = await getDocs(q);
        const products = [];
        querySnapshot.forEach((doc) => {
            data = doc.data();
            data.id = doc.id;
            products.push(data);
        });
        return products;
    } catch (error) {
        console.error('Error obteniendo productos por categoria:', error);
        throw error;
    }

}
async function getProductoIngredientes(idProducto){
    //La relacion entre productos e ingredientes se hace a traves de la coleccion "ingrediente_producto"
    try {
        const q = query(collection(db, 'ingredientes_productos'), where('idProducto', '==', idProducto));
        const querySnapshot = await getDocs(q);
        const ingredientsPromises = querySnapshot.docs.map(async (doc) => {
            const relacion = {...doc.data()}
            console.log('Relacion: ',relacion);
            return await getIngredienteByID(relacion.idIngrediente);
        });
        const ingredients = await Promise.all(ingredientsPromises);
        
        return ingredients;
    } catch (error) {
        console.error('Error obteniendo ingredientes por producto:', error);
        throw error;
    }
}
async function getAlergenosIngrediente(idIngrediente){
    //La relacion entre ingredientes y alergenos se hace a traves de la coleccion "ingrediente_alergeno"
    console.log('ID del ingrediente: ',idIngrediente)
    try {
        const q = query(collection(db, 'alergenos_ingredientes'), where('idIngrediente', '==', idIngrediente));
        const querySnapshot = await getDocs(q);
        console.log('QuerySnapshot:',querySnapshot.docs);
        const alergenosPromises = querySnapshot.docs.map(async (doc) => {
            const relacion = doc.data();
            return getAlergenoByID(relacion.idAlergeno);
        });
        const alergenos = await Promise.all(alergenosPromises);
        return alergenos;
    } catch (error) {
        console.error('Error obteniendo alergenos por ingrediente:', error);
        throw error;
    }

}
async function getUsuarios(){
    try {
        const q = query(collection(db, 'usuarios'));
        const querySnapshot = await getDocs(q);
        const users = [];
        querySnapshot.forEach((doc) => {
            data = doc.data();
            data.id = doc.id;
            users.push(data);
        });
        return users;
    } catch (error) {
        console.error('Error obteniendo usuarios:', error);
        throw error;
    }
}
async function getUsuarioByID(id){
    try {
        doc(db, 'usuarios', id).get().then((doc) => {
            if (doc.exists()) {
                user = doc.data();
                user.id = doc.id;
            } else {
                console.log('No such document!');
            }
        });
        return user;
    } catch (error) {
        console.error('Error obteniendo usuarios por ID:', error);
        throw error;
    }

}
//Rutas de la API
expressApp.post('/api/register', (req, res) => {
    // Comprobar si ya existe el usuario en la base de datos
    const user = req.body;
    console.log(user);
    const q = query(collection(db, 'usuarios'), where('email', '==', user.email));
    getDocs(q).then((querySnapshot) => {
        if (querySnapshot.empty) {
            registerUser(user).then((data) => {
                console.log(user.email + " registrado correctamente.");
                response = { error: false, message: 'Usuario registrado correctamente', id: data };
                res.json(response);
            }).catch((error) => {
                res.status(500).send('Error registrando usuario: ' + error);
            });
        } else {
            data = { error: true, message: 'El usuario ya existe' };
            res.status(400).json(data);
        }
    }).catch((error) => {
        res.status(500).send('Error comprobando usuario: ' + error);
    });

    
});
expressApp.post('/api/login', (req, res) => {
    console.log("Peticion de login.");
    loginUser(req.body).then((data) => {
        res.json(data);
    }).catch((error) => {
        res.status(500).send('Error iniciando sesión: ' + error);
    });
});
expressApp.get('/api/productos', (req, res) => {
    console.log('Peticion entrante /api/productos');
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const products = await getProductos();
        data = { error: false, productos: products };
        res.json(data);
    }).catch((error) => {
        data = { error: true, message: 'Error verificando token: ' + error };
        res.status(401).json(data);

    });
});
expressApp.get('/api/productos/:id', (req, res) => {
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const response = await getProductoByID(req.params.id);
        res.json(response);
    }).catch((error) => {
        res.status(401).send('Error verificando token: ' + error);

    });
});
expressApp.get('/api/ingredientes', (req, res) => {
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const ingredients = await getIngredientes();
        res.json(ingredients);
    }).catch((error) => {
        res.status(401).send('Error verificando token: ' + error);

    });
});
expressApp.get('/api/ingredientes/:id', (req, res) => {
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const ingredient = await getIngredienteByID(req.params.id);
        res.json(ingredient);
    }).catch((error) => {
        res.status(401).send('Error verificando token: ' + error);

    });
});
expressApp.get('/api/categorias', (req, res) => {
    console.log('Peticion entrante /api/categorias');
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const categories = await getCategorias();
        data = { error: false, categorias: categories };
        res.json(data);
    }).catch((error) => {
        data = { error: true, message: 'Error verificando token: ' + error };
        res.status(401).json(data);

    });
});
expressApp.get('/api/categorias/:id/productos', (req, res) => {
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const products = await getProductosByCategoria(req.params.id);
        data = { error: false, products: products };
        res.json(data);
    }).catch((error) => {
        data = { error: true, message: 'Error verificando token: ' + error };
        res.status(401).json(data);
    });
});
expressApp.get('/api/pedidos', (req, res) => {
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const user = decoded.user;
        let orders = [];
        if(user.userType = 0){
            orders = await getPedidoByClienteID(user.id);
        }else{
            orders = await getPedidos();
        }
        res.json(orders);
    }).catch((error) => {
        res.status(401).send('Error verificando token: ' + error);

    });
});
expressApp.get('/api/usuarios',(req,res)=>{
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const users = await getUsuarios();
        res.json(users);
    }).catch((error) => {
        res.status(401).send('Error verificando token: ' + error);

    });
});
expressApp.get('/api/usuarios/:id',(req,res)=>{
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const user = await getUsuarioByID(req.params.id);
        res.json(user);
    }).catch((error) => {
        res.status(401).send('Error verificando token: ' + error);

    });
});
expressApp.get('/api/usuarios/:id/pedidos',(req,res)=>{
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const orders = await getPedidoByClienteID(req.params.id);
        res.json(orders);
    }).catch((error) => {
        res.status(401).send('Error verificando token: ' + error);

    });
});

expressApp.listen(3000, () => {
    console.log('Server running on port ' + PORT);
});
