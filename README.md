# API asistencias 

Esta API está hecha con Spring Boot y se encarga de manejar las entradas y salidas de usuarios, almacenar esos datos en la base de datos y enviar correos al usuario sobre sus salidas y entradas.

## Aspectos a tener en cuenta para ejecutar la API
- Debe tener Java JDK +17 instalado en su máquina.
- Si desea generar un JAR del proyecto puede mirar este video para hacerlo: https://www.youtube.com/watch?v=aHhdZIvnc7o&ab_channel=HAH-Tech
- **IMPORTANTE:** Para poder acceder a la API desde su Frontend debe cambiar el valor y poner su URL en la variable **asistencias.url** en el [application.properties](https://github.com/mallonflowerz/asistencias/blob/main/src/main/resources/application.properties)
- Puede cambiar la configuración de puertos de ejecución y demás en el archivo [application.properties](https://github.com/mallonflowerz/asistencias/blob/main/src/main/resources/application.properties) (debe tener un editor de código instalado para usar el código fuente).
```properties
server.port=3004 # Puede cambiar el puerto al que desee

# configuracion de la bbdd MySQL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# puede cambiar el puerto "3306" al que use su equipo y el nombre de la BBDD "asistencias" al que desee (recuerde crear la BBDD antes de ejcutar la API)
spring.datasource.url=jdbc:mysql://localhost:3306/asistencias?useSSL=false&serverTimezone=America/Bogota&allowPublicKeyRetrieval=true
spring.datasource.username=root # Modifique su username 
spring.datasource.password=tu_password # Coloque su password
spring.jpa.hibernate.ddl-auto=update 
```

## CREDENCIALES:
Estos Endpoints son para crear admins para que manejen las diferentes peticiones HTTPs de la app.
Por defecto se crea un admin con lo siguiente:

```json
{
    "username": "admin",
    "password": "@123456@"
}
```
Debe logearse para poder crear más administradores en http/localhost:3004/login (POST), si desea puede eliminar el admin por defecto.

Base endpoint por defecto: 
```url
http/localhost:3004
```

### Endpoints

**GET**: 

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

**POST**:

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

**PUT**:

Actualiza el admin con el ID de la cabecera, debe ser enviada las nuevas credenciales en el body como en la petición POST.
```url
/register/id
``` 

**DELETE**:

Elimina el admin con el ID dado en la cabecera.
```url
/register/id
``` 

## USUARIO:

Los siguientes endpoints sirven para insertar, actualizar, eliminar y leer usuarios.

**GET**:

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

**GET**:

Obtiene un usuario por su ID
```url
/usuarios/id
```

**POST**:

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

**PUT**:

Actualiza el usuario con el id indicado en la url (Debe mandar el usuario con el mismo formato de la peticion POST).
```url
/usuarios/id
```

**DELETE**:

Elimina el usuario con el id indicado en la url.
```url
/usuarios/id
``` 


## MOVIMIENTOS:

Los siguientes endpoints son para registrar las entradas y salidas de cada usuario.

**GET**:

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

**GET**:

Muestra los movimientos del usuario con el id indicado en la url.
```url
/asistencia/id
```

**POST**:

Registra una salida o entrada dependiendo de su ultimo registro en la BBDD, debe colocar el numero de identifiacion en la url.
```url
/asistencia/numeroDeIdentificacion
```

**POST**:

Envia al correo del usuario indicado en la cabecera, un email con todos sus registros de entradas y salidas.
```url
/asistencia/envmail/idUser
```

Para poder usar el envio de correos electronicos debe configurar en el archivo [application.properties](https://github.com/mallonflowerz/asistencias/blob/main/src/main/resources/application.properties) lo siguiente:
```properties
# configuracion para envios de emails
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=su_correo@gmail.com # indicar el correo con el cual quiere enviar todos los correos electronicos a los usuarios
spring.mail.password=password # colocar la contraseña generada para la aplicacion, el nombre debe ser "asistencias"
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
Si no sabe como generar una contraseña para la aplicacion puede mirar este video que lo explica: 

https://www.youtube.com/watch?v=Q74nxFBCHCI&ab_channel=Consultor%C3%ADaINSICON

## UPLOAD PHOTO:

Estos endpoints son para subir, ver, actualizar y eliminar la foto de cada usuario.

**GET**:

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

**POST**:

Guarda la foto del usuario indicado en los parametros.
```url
/files
```
La forma de consumir esta peticion varia dependiendo del lenguaje o framework front-end que quiera usar, pero casi todos tienen la misma logica y es la siguiente.

![params](https://raw.githubusercontent.com/mallonflowerz/asistencias/main/asset/param.PNG)

La primera y segunda key es obligatoria y deben escribirse exactamente como se indica en a imagen, en el **userId** debe indicar el id del usuario al cual desea subir su foto.

Para configurar el peso de las foto puede irse al archivo [application.properties](https://github.com/mallonflowerz/asistencias/blob/main/src/main/resources/application.properties) y configurar lo siguiente:
```properties
# files config
spring.servlet.multipart.max-file-size=5MB # indique el tamaño maximo que desee
spring.servlet.multipart.max-request-size=5MB # lo mismo aqui
```

**PUT:**

Actualizar la foto de un usuario indicado en los parametros.

Debe ser enviado de la misma forma que la peticion POST, debe indicar el **userId** del usuario a actualizar.
```url
/files
```

**DELETE:**

Elimina la foto del usuario por medio del id indicado en la url.
```url
/files/idUser
```

