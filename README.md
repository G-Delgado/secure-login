# Secure-Login Project
## Descripción del Proyecto
Este proyecto está diseñado para proporcionar un sistema de inicio de sesión seguro que aborda las vulnerabilidades comunes de seguridad como la inyección SQL, ataques CSRF y XSS. Utiliza una combinación de autenticación y autorización, implementados con Spring Security y JWT.

El sistema proporciona una capa de seguridad robusta al validar y codificar las contraseñas, asegurando que las interacciones con la base de datos estén protegidas contra la inyección de código malicioso. Además, utiliza tokens JWT para autorizar y autenticar a los usuarios, garantizando un acceso seguro a las rutas y recursos del servidor.

## Montaje del Frontend
#### Requisitos
- Node.js y npm instalados
#### Pasos
1. Clona el repositorio del frontend.
2. Navega al directorio del proyecto y ejecuta `npm install` para instalar las dependencias.
3. Renombra el archivo `.env.example` a `.env` y configura el puerto del servidor en el que se ejecutará el frontend.
4. Utiliza el comando `npm run dev` para iniciar el frontend.
## Montaje del Backend
#### Requisitos
- Maven instalado
- Docker instalado
### Pasos:
1. Clona el repositorio del backend.
2. En el directorio raíz del proyecto, ejecuta mvn install para instalar las dependencias del proyecto.
3. Utiliza el comando de Docker `docker run --name security-db -e POSTGRES_USER=user -e POSTGRES_PASSWORD=user -p 5432:5432 -d postgres` para crear una imagen de la base de datos utilizada por el backend.
4. Inicia el servidor.
## Generalidades Finales del Proyecto y Contribuciones
Este proyecto se enfoca en proporcionar una autenticación y autorización seguras mediante la implementación de prácticas recomendadas en seguridad informática. Siéntete libre de contribuir con mejoras, soluciones a problemas o nuevas características a través de pull requests.