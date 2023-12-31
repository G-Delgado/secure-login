# Secure-Login Project
![Gif seguridad](./giphy.gif)
## Descripción del Proyecto
Este proyecto está diseñado para proporcionar un sistema de inicio de sesión seguro que aborda las vulnerabilidades comunes de seguridad como la inyección SQL y ataques XSS. Utiliza una combinación de autenticación y autorización, implementados con Spring Security y JWT.

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
3. Utiliza el comando de Docker `docker run --name security-db -e POSTGRES_USER=secureloguser -e POSTGRES_PASSWORD=securelogpassword -p 5432:5432 -d postgres` para crear una imagen de la base de datos utilizada por el backend.
4. Inicia el servidor.

## Realización del proyecto
Este programa se realizó usando un stack de frontend con react.js + typescript, backend usando Java con Springboot y una base de datos PostgreSQL que está alojada en una imagen de Docker. Al momento de tener en cuenta lo que podría significar que un login fuera seguro, implementamos en el proyecto medidas especialmente para la autenticación y autorización de usuarios y la encriptación de contraseñas para proteger la integridad de los mismos en caso de brechas.

Adicionalmente, se aplicó sanitización de inputs para disminuir la posibilidad de ataques de inyección sql y cross-site scripting (xss) que, junto al sistema basado en roles que se implementó, permite tener distintas capas de seguridad para este login.

## Dificultades del proyecto
Las principales dificultades del proyecto se agrupan en 3 categorías:
1. Desarrollo del encriptador de contraseñas 
2. Implementación de JWT y sus pasos de seguridad
3. Aplicación de roles y sanitización de Inputs.

En cada categoría, hubo un proceso de entendimiento y de desarrollo en aspectos que no se habían tenido en cuenta en ocasiones pasadas, lo que llevó a ciertas dificultades, errores y bugs en los que el código no funcionaba como debía o no funcionaba como necesitabamos.

Estos problemas llevaron a tener en cuenta distintas opciones, de poder usar diferentes librerias y de investigar cuales eran las mejores prácticas para poder mantener la seguridad en el sistema de login.

## Conclusiones
El proyecto Secure-Login representa un esfuerzo significativo para crear un sistema de inicio de sesión seguro y robusto. A través de la implementación de tecnologías como Spring Security, JWT, y prácticas de cifrado de contraseñas, hemos logrado abordar las principales vulnerabilidades de seguridad como la inyección SQL y los ataques XSS.

Durante el desarrollo, enfrentamos desafíos significativos, particularmente en la gestión y aplicación efectiva de las prácticas de seguridad en el manejo de contraseñas y la implementación adecuada de JWT para la autenticación y autorización. Estos obstáculos nos permitieron comprender la importancia de la seguridad en cada etapa del desarrollo de aplicaciones web.

El enfoque en la sanidad de entradas, el manejo de roles y los protocolos de seguridad nos brindó una comprensión más profunda de las mejores prácticas en seguridad de aplicaciones y cómo usarlas de manera efectiva en un proyecto real.

En conclusión, Secure-Login no solo representa un sistema de inicio de sesión seguro, sino también un aprendizaje valioso sobre la importancia de la seguridad en el desarrollo de software y la aplicación práctica de técnicas de protección contra vulnerabilidades comunes.

## Generalidades Finales del Proyecto y Contribuciones
El proyecto se mantiene abierto a contribuciones externas que puedan mejorar aún más la seguridad, la eficiencia y la funcionalidad del sistema. Se alienta a cualquier persona interesada en la seguridad informática y el desarrollo de aplicaciones web a contribuir con mejoras, correcciones de errores o nuevas funcionalidades a través de solicitudes de extracción (pull requests). ¡Todas las contribuciones son bienvenidas!

No dudes en adaptar y agregar más detalles o reflexiones específicas que consideres relevantes para el proyecto.