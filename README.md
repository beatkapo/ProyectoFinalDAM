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
## Cliente
El cliente deberá enviar y recibir peticiones al servidor en formato JSON
Habrá un sistema de admin que solo se verá si el usuario consta como administrador. 
## Base de datos
Usaré Firebase Realtime database por su simplicidad y extensa documentación.
