# API asistencias 

![portada](https://raw.githubusercontent.com/mallonflowerz/asistencias/main/asset/portada.png)

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
```url
/register
``` 
Funciona para obtener todos los administradores que hay registrados.
Ejemplo de la respuesta:
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
```url
/register
``` 
Es para registrar un nuevo administrador desde el body:
```json
{
    "username": "String",
    "password": "String"
}
```

PUT:
```url
/register/id
``` 
Actualiza el admin con el ID de la cabecera, debe ser enviada las nuevas credenciales en el body como en la petición POST.

DELETE:
```url
/register/id
``` 
Elimina el admin con el ID dado en la cabecera.
