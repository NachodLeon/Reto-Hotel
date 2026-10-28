# HotelApp - Sistema de Reservas de Hotel

**HotelApp** es una aplicación de escritorio desarrollada en **Java 21** utilizando **JavaFX** y gestionada con **Maven**. La aplicación permite cargar, visualizar y consultar el estado de disponibilidad de habitaciones de un hotel mediante el procesamiento de un archivo de datos local en formato XML.

## Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de contar con las siguientes herramientas instaladas en tu sistema:

* **JDK 21** o superior (p. ej., Eclipse Temurin, OpenJDK, Oracle JDK).

* **Apache Maven** (versión 3.8+ recomendada) o la extensión de Maven para tu IDE.

* **Visual Studio Code** con la extensión **Extension Pack for Java** (de Microsoft).

## Estructura del Proyecto

El proyecto sigue la estructura estándar de un proyecto Maven:

```
HotelApp/
├── .vscode/
│   └── settings.json             # Configuración opcional para VSCode
├── src/
│   └── main/
│       ├── java/
│       │   └── hotelapp/
│       │       ├── Main.java         # Clase principal (Punto de entrada JavaFX)
│       │       └── Controlador.java  # Lógica de la interfaz y procesamiento XML
│       └── resources/
│           └── hotelapp/
│               ├── vista.fxml        # Interfaz gráfica (diseño FXML)
│               └── reservas.xml      # Base de datos local en formato XML
├── pom.xml                       # Configuración de dependencias y plugins Maven
└── README.md                     # Documentación del proyecto

```

## ⚙️ Arquitectura y Funcionamiento

### 1. **Carga y Parsing de XML Seguro (`Controlador.java`)**

* Al iniciar la vista, el método `initialize()` ejecuta de forma automática la lectura del archivo `reservas.xml` ubicado en la carpeta de recursos (`/resources/hotelapp/`).

* Se utiliza el analizador sintáctico DOM (`DocumentBuilderFactory`). Por seguridad, está configurado para **deshabilitar procesamiento DOCTYPE e inclusiones externas (XInclude)**, protegiendo la aplicación contra ataques de entidades externas XML (XXE).

* Los elementos `<habitacion>` son parseados y almacenados en memoria dentro de una colección ordenada (`LinkedHashMap<String, Habitacion>`).

### 2. **Criterios de Validación**

* Si una habitación no especifica el elemento `<numero>`, se omite.

* El atributo `<estado>` solo acepta los valores `disponible` o `ocupada` (sin importar mayúsculas/minúsculas). Cualquier otro estado queda descartado.

### 3. **Interfaz de Usuario (`vista.fxml` & `Controlador.java`)**

* **Listado General:** Al arrancar, el área de texto principal muestra todas las habitaciones registradas con su número, tipo, precio y estado actual.

* **Búsqueda por Número:** Permite ingresar un número de habitación (ejemplo: `101`).

  * Si existe y está **disponible**, muestra una alerta verde/texto descriptivo con su tipo y precio.

  * Si existe y está **ocupada**, notifica que no está disponible.

  * Si el número no coincide con ningún registro, informa que la habitación no existe.

## Cómo Ejecutar la Aplicación

### Desde la Terminal de VSCode

1. Abre la carpeta del proyecto en VSCode (`Archivo > Abrir carpeta...`).

2. Abre la terminal integrada de VSCode (`Ctrl + ~` o `Cmd + ~`).

3. Ejecuta el comando del plugin de JavaFX en Maven:

```
mvn javafx:run

```

### Compilar y Empaquetar

Para verificar que no existan errores de compilación y empaquetar el proyecto:

```
mvn clean compile

```

## Formato del Archivo `reservas.xml`

El archivo de datos debe ubicarse en `src/main/resources/hotelapp/reservas.xml` y seguir la siguiente estructura:

```
<?xml version="1.0" encoding="UTF-8"?>
<reservas>
    <habitacion>
        <numero>101</numero>
        <tipo>Individual</tipo>
        <precio>45.00</precio>
        <estado>disponible</estado>
    </habitacion>
    <habitacion>
        <numero>102</numero>
        <tipo>Doble</tipo>
        <precio>70.00</precio>
        <estado>ocupada</estado>
    </habitacion>
</reservas>

```