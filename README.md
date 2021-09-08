# AREP -TALLER CLIENTES Y SERVICIOS
Este taller presenta diferentes retos que loa ayudaran a explorar los conceptos de esquemas de nombres y de clientes y servicios. Adicionalmente, el taller le ayudará a explorar la arquitectura de las aplicaciones distribuidas sobre internet.
#CALENTAMIENTO (1H)
## Reto 1
- Escribir un servidor web que soporte múltiples solicitudes seguidas (no concurrentes). El servidor debe retornar todos los archivos solicitados, incluyendo páginas html e imágenes.
- Construir un sitio web para probar el servidor.
- Desplegar la solución en Heroku. Sin usar frameworks web como Spark o Spring, solo Java y las librerías para manejo de la red. 
## Prerrequisitos
Antes de ejecutar el proyecto es necesario instalar los siguientes programas:
* [Java](https://www.java.com/es/download/ie_manual.jsp). versión 11 o superior.
* [Maven](https://maven.apache.org/).
* [GIT](https://git-scm.com/).
* Version de compilación 1.8 o superior
## Instalación
Para descargar el proyecto primero debemos clonar el repositorio con ayuda de la consola de comandos:
```
git clone https://github.com/JCPosso/Arep-ejercicio.git
```

## Pruebas y Compilación

## Instalación
Para descargar el proyecto primero debemos clonar el repositorio con ayuda de la consola de comandos:
```
git clone https://github.com/JCPosso/Arep-ejercicio
```

## Compilacion
Para compilar el proyecto usamos Maven en el directorio raiz del proyecto  usando el siguiente comando.
```
mvn compile
mvn package
```
## Ejecución
Para ejecutar el proyecto usamos Maven en el directorio raiz del proyecto  usando el siguiente comando.
```
mvn package
```
Si desea ejecutar el programa desde línea de comandos puede usar las siguientes instrucciones:
```
java -cp target/classes;target/dependency/* org.example.HttpServer  
```

#Despliegue HEROKU
[![Heroku](src/main/resources/public/heroku.jpg)](https://heroku-app-arep.herokuapp.com/)
## Autor
[Juan Camilo Posso Guevara](https://github.com/JCPosso)
## Derechos de Autor
**©** _Juan Camilo Posso G., Escuela Colombiana de Ingeniería Julio Garavito._
## Licencia
Licencia bajo  [GNU General Public License](https://github.com/JCPosso/Arep-ejercicio/blob/master/LICENSE).