# 🤖 BIP Sucursales - Web Automation Frontend

Frontend web para ejecutar automatizaciones Selenium de BIP Sucursales de forma fácil y accesible para todo el equipo.

## ✨ Características

- ✅ Interfaz web intuitiva con botones para cada automatización
- ✅ Carga de archivos Excel desde el navegador
- ✅ Ejecución de automatizaciones Selenium mediante Maven
- ✅ Logs en tiempo real
- ✅ Diseño responsivo y moderno
- ✅ Acceso local sin requerimientos de configuración en otros equipos

## 📁 Estructura del Proyecto

```
AutomationBS/
├── pom.xml
├── README.md
├── src/
│   └── main/
│       ├── java/com/bipautomations/
│       │   ├── BipWebAutomationApplication.java
│       │   ├── controller/
│       │   │   └── AutomatizacionesController.java
│       │   └── service/
│       │       └── AutomatizacionesService.java
│       └── resources/
│           ├── application.properties
│           └── static/
│               ├── index.html
│               ├── style.css
│               └── script.js
```

## 🛠️ Requisitos Previos

- **Java 11** o superior
- **Maven 3.6+**
- Selenium WebDriver configurado
- ChromeDriver u otro driver compatible

## 📦 Instalación Rápida

### 1. Clonar el repositorio

```bash
git clone https://github.com/leobp2025/AutomationBS.git
cd AutomationBS
```

### 2. Compilar el proyecto

```bash
mvn clean install
```

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

O si compilaste:

```bash
java -jar target/bip-web-automation-1.0.0.jar
```

### 4. Acceder a la interfaz

Abre tu navegador en: **http://localhost:8080**

## ⚙️ Configuración

Edita `src/main/resources/application.properties` para personalizar:

```properties
# Puerto
server.port=8080

# Rutas de archivos Excel
autumatizaciones.excel.path=V:\\Automatizacion\\BIP\\Selenium\\Recursos
autumatizaciones.output.path=V:\\Automatizacion\\BIP\\Selenium\\Output

# Datos de BIP Sucursales
bip.sucursales.url=http://int.bipsucursales.child01.root.test:5080/
bip.usuario.default=P002331
bip.password.default=Baprosur1
```

## 🎯 Automatizaciones Disponibles

1. **Alta Cajas de Ahorro** - Carga de nuevas cajas de ahorro
2. **Alta Clientes** - Registro de nuevos clientes
3. **Alta Masiva TDBS** - Carga masiva de TDBS
4. **Alta Persona Humana** - Alta de personas naturales
5. **Alta Pasivos Pago** - Configuración de productos de pasivos
6. **OP02** - Operación OP02
7. **OP03** - Operación OP03
8. **OP1258** - Operación OP1258
9. **OP1263** - Operación OP1263
10. **OP502** - Operación OP502
11. **OP505** - Operación OP505
12. **OP5400** - Operación OP5400
13. **OP7717** - Operación OP7717

## 📝 Cómo Usar

1. Abre http://localhost:8080 en tu navegador
2. Selecciona el archivo Excel requerido con el botón de carga
3. Haz clic en el botón de la automatización deseada
4. Espera a que se complete la ejecución
5. Verifica los logs en el panel inferior derecho

## 🔗 API Endpoints

### Ejecutar Automatización

```
POST /api/automatizaciones/ejecutar/{tipo}
Content-Type: multipart/form-data

Parámetros:
- file: Archivo Excel (obligatorio)

Tipos soportados:
- ALTA_CAJAS_AHORRO
- ALTA_CLIENTES
- ALTA_MASIVA_TDBS
- ALTA_PERSONA_HUMANA
- ALTA_PASIVOS_PAGO
- OP02, OP03, OP1258, OP1263, OP502, OP505, OP5400, OP7717
```

### Obtener Estado

```
GET /api/automatizaciones/estado

Respuesta:
{
  "estado": "En línea",
  "automatizaciones": 13
}
```

## 🐛 Solución de Problemas

### Puerto 8080 en uso

Cambia el puerto en `application.properties`:

```properties
server.port=8081
```

### Errores de permisos

- Verifica que tienes acceso a las rutas configuradas
- Asegúrate que `V:\Automatizacion\BIP\Selenium\` existe

### Errores de Selenium

- Verifica que ChromeDriver está instalado y en PATH
- Comprueba que la URL de BIP es accesible
- Revisa los logs en la consola de Spring Boot

## 📊 Integración con Tests TestNG

Para integrar tus clases Selenium existentes:

1. Coloca tus clases de test en `src/test/java`
2. El servicio ejecutará automáticamente con Maven
3. Los logs aparecerán en tiempo real

**Ejemplo de clase compatible:**

```java
@Test(dataProvider = "datosDePrueba")
public void miAutomatizacion(int cantidad, String dni, String convenio) {
    // Tu código Selenium aquí
}
```

## 📄 Licencia

Propiedad de BIP Sucursales - 2026

## 👨‍💻 Desarrollo

Proyecto desarrollado con:
- **Spring Boot 2.7.14**
- **Maven**
- **Selenium 4.10.0**
- **TestNG 7.8.1**
- **Apache POI 5.2.3**

## 📞 Soporte

Para reportar problemas:
1. Revisa los logs en la consola de Spring Boot
2. Verifica el panel de logs en la interfaz web
3. Consulta el archivo `application.properties`
