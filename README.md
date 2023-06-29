# API asistencias 

Esta API está hecha con Spring Boot y se encarga de manejar las entradas y salidas de usuarios, almacenar esos datos en la base de datos y enviar correos al usuario sobre sus salidas y entradas.

## Aspectos a tener en cuenta para ejecutar la API
- Debe tener Java JDK +17 instalado en su máquina.
- Debe crear una BBDD con el nombre de "asistencias" en su servidor.
- Si desea usar el archivo JAR como opción, debe ejecutar el comando para iniciar la app en el CMD al mismo nivel del archivo "java -jar asistenciasAPI.jar".
- Puede cambiar la configuración de puertos de ejecución y demás en el archivo application.properties (debe tener un editor de código instalado para usar el código fuente).

## CREDENCIALES:
Estos Endpoints son para crear admins para que manejen las diferentes peticiones HTTPs de la app.
Por defecto se crea un admin con lo siguiente:

```json
{
    "username": "admin",
    "password": "@123456@"
}
```
Debe logearse para poder crear más administradores, si desea puede eliminar el admin por defecto.

Base endpoint por defecto: 
```url
http/localhost:3004
```

### Endpoints

GET: 

Funciona para obtener todos los administradores que hay registrados.
```url
/register
``` 
Ejemplo:
```json
[
    {
        "username": "admin",
        "password": "@123456@"
    },
    {
        "username": "admin2",
        "password": "@1234567@"
    },
    {
        "username": "admin3",
        "password": "@1234567@"
    }
]
```

POST:

Es para registrar un nuevo administrador desde el body.
```url
/register
```
Ejemplo:
```json
{
    "username": "String",
    "password": "String"
}
```

PUT:

Actualiza el admin con el ID de la cabecera, debe ser enviada las nuevas credenciales en el body como en la petición POST.
```url
/register/id
``` 

DELETE:

Elimina el admin con el ID dado en la cabecera.
```url
/register/id
``` 

## USUARIO:

Los siguientes endpoints sirven para insertar, actualizar, eliminar y leer usuarios.

GET:

Obtiene todos los usuarios registrados en la BBDD.
```url
/usuarios
```
Ejemplo:
```json
[
    {
        "id": 1,
        "primerNombre": "WILLIAN",
        "segundoNombre": "JESE",
        "primerApellido": "ASPRILLA",
        "segundoApellido": "COOKIE",
        "email": "correo1@gmail.com",
        "numeroIdentificacion": "2205459632"
    },
    {
        "id": 2,
        "primerNombre": "JENIFER",
        "segundoNombre": "CAMILA",
        "primerApellido": "OESTE",
        "segundoApellido": "SUR",
        "email": "correo2@gmail.com",
        "numeroIdentificacion": "2569965238"
    }
]
```

GET:

Obtiene un usuario por su ID
```url
/usuarios/id
```

POST:

Guarda un usuario en la BBDD (todos los campos son requeridos).
```url
/usuarios
```
Ejemplo:
```json
{
    "primerNombre": "WILLIAN",
    "segundoNombre": "JESE",
    "primerApellido": "ASPRILLA",
    "segundoApellido": "COOKIE",
    "email": "correo1@gmail.com",
    "numeroIdentificacion": "2205459632"
}
```

PUT:

Actualiza el usuario con el id indicado en la url (Debe mandar el usuario con el mismo formato de la peticion POST).
```url
/usuarios/id
```

DELETE:

Elimina el usuario con el id indicado en la url.
```url
/usuarios/id
``` 


## MOVIMIENTOS:

Los siguientes endpoints son para registrar las entradas y salidas de cada usuario.

GET:

Obtiene todos los movimientos.
```url
/asistencia
```
Ejemplo:
```json
[
    {
        "usuarios": {
            "primerNombre": "WILLIAN",
            "segundoNombre": "JESE",
            "primerApellido": "ASPRILLA",
            "segundoApellido": "COOKIE",
            "email": "correo1@gmail.com",
            "numeroIdentificacion": "2205459632"
        },
        "fechaHora": "2023-06-21T21:05:08.039414",
        "entrada": true
    },
    {
        "usuarios": {
            "primerNombre": "WILLIAN",
            "segundoNombre": "JESE",
            "primerApellido": "ASPRILLA",
            "segundoApellido": "COOKIE",
            "email": "correo1@gmail.com",
            "numeroIdentificacion": "2205459632"
        },
        "fechaHora": "2023-06-21T21:03:57.215438",
        "entrada": false
    }
]
```

GET:

Muestra los movimientos del usuario con el id indicado en la url.
```url
/asistencia/id
```

POST:

Registra una salida o entrada dependiendo de su ultimo registro en la BBDD, debe colocar el numero de identifiacion en la url.
```url
/asistencia/numeroDeIdentificacion
```

POST:

Envia al correo del usuario indicado en la cabecera, un email con todos sus registros de entradas y salidas.
```url
/asistencia/envmail/idUser
```

UPLOAD PHOTO (beta):

Estos endpoints son para subir y ver la foto de cada usuario (Mas adelante se va a implementar los metodos PUT y DELETE).

GET:

Muestra la foto del usuario indicado.
```url
/files/idUser
```
Ejemplo:
```json
{
    "id": 1,
    "fileName": "willian_face.png",
    "fileType": "image/png",
    "fileData": "iVBORw0KGgoAAAA..."
}
```

POST:

Guarda la foto del usuario indicado en los parametros
