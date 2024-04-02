# ProyectoFinalDAM
Aquí queda registrado el proceso de mi proyecto final para el módulo superior de desarrollo de aplicaciones. Usaré este archivo para anotar ideas durante el proceso.

## Idea principal

Aplicación cliente-servidor para gestionar pedidos de un restaurante.
La base de datos almacena:
 - Usuarios
 - Productos
 - Ingredientes
 - Categorías
 - Pedidos

## Servidor
API Express que se encargará de realizar las consultas a la base de datos.
### Endpoints
#### POST /api/register
Registra un usuario en la base de datos.
Recibe los datos del usuario en formato JSON
Devuelve la cadena de identificacion del documento.
#### POST /api/login
Inicia la sesion de un usuario.
Recibe las credenciales del usuario y comprueba que existen en la base de datos.
Devuelve un token para mantener la sesion iniciada.
#### GET /api/productos
Devuelve todos los productos
Se necesita estar logueado.
#### GET /api/productos/:id
Devuelve el producto con id pasada por parametro.
Se necesita estar logueado.
#### GET /api/ingredientes
Devuelve todos los ingredientes del restaurante
Se necesita estar logueado
#### GET /api/ingredientes/:id
Devuelve un ingrediente dada una cadena de identificacion.
Se necesita esar logueado
#### GET /api/categorias
Devuelve todas las categorías de la carta.
Se necesita estar logueado.
#### GET /api/categorias/:id/ingredientes **(Por implementar)**
Devuelve los ingredientes de una categoria
Se necesita estar logueado
#### GET /api/categorias/:id/productos
Devuelve los productos de una categoría.
Se necesita estar logueado.
#### GET /api/pedidos
Devuelve los pedidos del usuario, en caso de ser trabajador o administrador, devuelve todos los pedidos.
#### GET /api/usuarios
Devuelve todos los usuarios
Se necesita ser trabajador o administrador y estar logueado. **Todavia sin implementar roles, responde a cualquier usuario**
#### GET /api/usuarios/:id
Devuelve el usuario con la cadena de identificacion pasada en parametro.
Se necesita ser trabajador o administrador y estar logueado. **Todavia sin implementar roles, responde a cualquier usuario**
#### GET /api/usuarios/:id/pedidos
Devuelve todos los pedidos que ha realizado un usuario.
Se necesita ser trabajador o administrador y estar logueado. **Todavia sin implementar roles, responde a cualquier usuario**

## Cliente
El cliente deberá enviar y recibir peticiones al servidor en formato JSON
Habrá un sistema de admin que solo se verá si el usuario consta como administrador. 
## Base de datos
Usaré Firebase Realtime database por su simplicidad y extensa documentación.
## Herramientas
(02/04/2024)
**importar_excel_firestore.py** ha sido una idea que se me ha ocurrido al ver lo tedioso que era importar un par de datos a cada colección de la base de datos. Este script Python abre una ventana que deja seleccionar un fichero con extension "xlsx", automaticamente lo procesa y e importa los datos en Firestore.
Toma el nombre de la hoja para referenciar el nombre de la colección, por lo que puedes tener toda una base de datos en un archivo e importarla.
La primera fila se refiere al nombre de los campos, las siguientes representan los datos del documento.
Cualquier fallo lo notifica en un label, en caso de exito también y se cierra a los pocos segundos.
