const { initializeApp } = require('firebase/app');
const { getFirestore, collection, query, where, getDocs, addDoc, getDoc, doc, setDoc } = require('firebase/firestore');
const { firebaseConfig, secretWord } = require("./config");
const jwt = require('jsonwebtoken');
const path = require('path');
const fs = require('fs');
const express = require('express');
const e = require('express');
const expressApp = express();
const PORT = 3000;

const firebaseApp = initializeApp(firebaseConfig);
const db = getFirestore(firebaseApp);

expressApp.use(express.json({limit: '10mb'}));

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
async function saveBase64Image(base64String, id) {
    try {
        // Genera un nombre de archivo único
        const filename = id + '.png';

        // Decodifica la imagen
        const data = base64String.replace(/^data:image\/\w+;base64,/, '');
        const buffer = Buffer.from(data, 'base64');

        // Define la ruta del archivo
        const filePath = path.join(__dirname, 'img', filename);

        // Guarda la imagen en el sistema de archivos
        fs.writeFileSync(filePath, buffer);

        // Devuelve el nombre del archivo
        return filename;
    } catch (error) {
        console.error('Error guardando imagen:', error);
        throw error;
    }
}
async function getProductImageInBase64(productId) {
    try {
        // Genera el nombre del archivo
        const filename = productId + '.png';

        // Define la ruta del archivo
        const filePath = path.join(__dirname, 'img', filename);

        // Lee el archivo del sistema de archivos
        const file = fs.readFileSync(filePath);

        // Convierte el archivo a base64
        const base64Image = Buffer.from(file).toString('base64');

        // Devuelve la imagen en base64
        return base64Image;
    } catch (error) {
        console.error('Error obteniendo imagen del producto:', error);
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
            const product = docSnap.data();
            product.id = docSnap.id;
            const ingredientes = await getProductoIngredientes(product.id).catch((error) => {
                console.error('Error obteniendo ingredientes del producto:', error);
            });
            product.ingredientes = ingredientes;
            const categoria = await getCategoriaByID(product.categoria).catch((error) => {
                console.error('Error obteniendo categoria del producto:', error);
            });
            product.categoria = categoria;
            
            return product;
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
            
            const alergenos = await getAlergenosIngrediente(ingredient.id); 
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
        const promises = querySnapshot.docs.map(async (doc) => {
            const pedido = doc.data();
            pedido.id = doc.id;
            pedido.lineas = await getLineasPedidoByPedidoID(pedido.id).catch((error) => {
                console.error('Error obteniendo lineas de pedido:', error);
            });
            orders.push(pedido);
        });
        await Promise.all(promises);
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
        const promises = querySnapshot.docs.map(async (doc) => {
            const pedido = doc.data();
            pedido.id = doc.id;
            pedido.lineas = await getLineasPedidoByPedidoID(pedido.id).catch((error) => {
                console.error('Error obteniendo lineas de pedido:', error);
            });
            orders.push(pedido);
        });
        await Promise.all(promises);
        return orders;
    } catch (error) {
        console.error('Error obteniendo pedidos por cliente:', error);
        throw error;
    }

}
async function getLineasPedidoByPedidoID(pedidoID){
    try {
        const q = query(collection(db, 'lineas_pedidos'), where('pedido', '==', pedidoID));
        const querySnapshot = await getDocs(q);
        const linesPromises = querySnapshot.docs.map(async (doc) => {
            const line = doc.data();
            line.id = doc.id;
            //console.log('Linea:',line);
            const product = await getProductoByID(line.producto);
            line.producto = product;
            return line;
        });
        const lines = await Promise.all(linesPromises);
        return lines;
    } catch (error) {
        console.error('Error obteniendo lineas de pedido por pedido:', error);
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
    try {
        const q = query(collection(db, 'alergenos_ingredientes'), where('idIngrediente', '==', idIngrediente));
        const querySnapshot = await getDocs(q);
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
        const docRef = doc(db, 'usuarios', id);
        const docSnap = await getDoc(docRef);
        if(docSnap.exists()){
            user = docSnap.data();
            user.id = docSnap.id;
            const data = { error: false, usuario: user };
            return data;
        }else{
            return { error: true, message: 'Usuario no encontrado' };
        }
    } catch (error) {
        console.error('Error obteniendo usuarios por ID:', error);
        throw error;
    }

}
//Rutas de la API
expressApp.post('/api/register', (req, res) => {
    // Comprobar si ya existe el usuario en la base de datos
    const user = req.body;
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
        data = { error: false, productos: products , token: token};
        res.json(data);
    }).catch((error) => {
        data = { error: true, message: 'Error verificando token: ' };
        console.log(data, token);
        res.status(401).json(data);
    });
});
expressApp.get('/api/productos/:id', (req, res) => {
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const product = await getProductoByID(req.params.id);
        const response = { error: false, producto: product };
        res.send(response);
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
    console.log('Peticion entrante /api/pedidos');
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        console.log('Token verificado');
        const response = await getUsuarioByID(decoded.id);
        const user = response.usuario;
        let orders = [];
        console.log('User:',user);
        console.log('User type:',user.userType);
        if(user.userType == 0){
            orders = await getPedidoByClienteID(decoded.id);
        }else{
            orders = await getPedidos();
        }
        const data = { error: false, pedidos: orders };
        res.json(data);
    }).catch((error) => {
        console.log('Error verificando token:',error);
        res.status(401).send({error: true, message: 'Error verificando token: ' + error});

    });
});
expressApp.get('/api/usuario', (req, res) => {
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const response = await getUsuarioByID(decoded.id);
        res.json(response);
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
        const response = await getUsuarioByID(req.params.id);
        res.json(response);
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
expressApp.post('/api/saveOrder',(req,res)=>{
    console.log('Peticion entrante /api/saveOrder');
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        console.log('Token verificado');
        const pedido = req.body;
        const lineas = pedido.lineas;
        delete pedido.lineas;
        pedido.cliente = decoded.id;
        //Guardar fecha en formato dd/mm/aaaa hh:mm
        const date = new Date();
        const day = date.getDate();
        const month = date.getMonth() + 1;
        const year = date.getFullYear();
        const hours = date.getHours();
        const minutes = date.getMinutes();
        pedido.fecha = hours + ':' + minutes+' ' +day + '/' + month + '/' + year;
        const docRef = await addDoc(collection(db, 'pedidos'), pedido).catch((error) => {
            res.status(401).send({error: true, message: 'Error añadiendo pedido: ' + error});
        });
        for(linea of lineas){
            linea.pedido = docRef.id;
            linea.producto = linea.producto.id;
            await addDoc(collection(db, 'lineas_pedidos'), linea).catch((error) => {
                res.status(401).send({error: true, message: 'Error añadiendo linea de pedido: ' + error});
            });
        }
        res.json({error: false, message: 'Pedido registrado correctamente', id: docRef.id});
    }).catch((error) => {
        console.log('Error verificando token:',error);
        res.status(401).send({error: true, message: 'Error verificando token: ' + error});
    });
});

expressApp.get('/api/img', (req, res) => {
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const id = req.body.id;
        const base64Image = await getProductImageInBase64(id);
        const response = { error: false, image: base64Image} 
        res.send(response);
    }).catch((error) => {
        const response = { error: true, message: 'Error verificando token: ' + error };
        res.status(401).send(response);
    });
});
expressApp.put('/api/img', (req, res) => {
        const id = req.body.id;
        const base64Image = req.body.image;
        const filename = saveBase64Image(base64Image, id);
        const response = { error: false, message: 'Imagen guardada correctamente', filename: filename };
        res.json(response); 
});
expressApp.patch('/api/usuario', (req, res) => {
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const user = req.body.usuario;
        const docRef = doc(db, 'usuarios', decoded.id);
        await setDoc(docRef, user, { merge: true });
        res.json({ error: false, message: 'Usuario actualizado correctamente' });
    }).catch((error) => {
        const response = { error: true, message: 'Error verificando token: ' + error };
        res.status(401).send(response);
    });
});
expressApp.put('/api/usuario', (req, res) => {
    const token = req.headers.authorization;
    verifyToken(token).then(async (decoded) => {
        const user = req.body.usuario;
        const docRef = doc(db, 'usuarios', decoded.id);
        await setDoc(docRef, user);
        res.json({ error: false, message: 'Usuario actualizado correctamente' });
    }).catch((error) => {
        const response = { error: true, message: 'Error verificando token: '+ error };
        res.status(401).send(response);
    });
});


expressApp.listen(3000, () => {
    console.log('Server running on port ' + PORT);
});
