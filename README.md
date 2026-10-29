# ReporteService

API del examen de Juan Daniel Medina Garces. Java 21, Spring Boot 4.1.1, Gradle, JPA y PostgreSQL. Estructura basada en la API Pedido de clase.

## Capas

`controller` recibe HTTP y valida → `service` aplica reglas y transacciones → `repository` accede a JPA → `entity` representa la tabla. Los DTO delimitan los datos de entrada y el contrato de paginación. La fecha se genera en hora de Lima cuando no se envía; el estado inicial siempre es 1, aunque el cliente intente establecerlo.

## Ejecutar

Con JDK 21: `./gradlew.bat bootRun` en Windows o `./gradlew bootRun` en Linux. Perfil local: H2 persistente en `data/`, solo para desarrollo rápido.

Para PostgreSQL, cree una base vacía y configure en PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE='prod'
$env:DB_URL='jdbc:postgresql://localhost:5432/reportes'
$env:DB_USERNAME='su_usuario'
$env:DB_PASSWORD='su_contraseña'
./gradlew.bat bootRun
```

También puede usar Docker: defina `DB_PASSWORD` y ejecute `docker compose up --build`. Hibernate crea/actualiza la tabla. No publique credenciales.

## Contrato HTTP

| Método | Ruta | Resultado |
|---|---|---|
| POST | `/reportes/` | 201, Location y objeto creado con estado 1 |
| GET | `/reportes/?offset=0&limit=5` | 200, página de reportes |
| GET | `/reportes/{id}` | 200 o 404 |
| GET | `/reportes/ciudadano/{dni}` | 200, arreglo; vacío si no hay coincidencias |
| PUT | `/reportes/{id}` | 200, estado 0; no requiere cuerpo |

Ejemplo POST:

```json
{"dni":"12345678","tipo":"DOMICILIARIA","direccion":"Av. Los Parques 123, Chiclayo","referencia":"Frente al parque"}
```

`fecha` es opcional en la solicitud, con formato `2026-10-29T10:30:00`. En la base siempre es obligatoria. `referencia` puede ser nula. DNI de exactamente 8 dígitos; tipo hasta 30 caracteres; dirección hasta 200; referencia hasta 255. DNI se mantiene como texto para conservar ceros iniciales.

Respuesta paginada: `content`, `totalElements`, `offset`, `limit`, `last`. **offset indica registros omitidos**, no número de página: 0, 5, 10. Orden descendente por ID. `limit` entre 1 y 100, offset no negativo. Un registro nuevo puede mover los límites entre páginas; la app evita duplicados y permite actualizar la lista. PUT es idempotente: atender de nuevo mantiene estado 0.

Los errores de validación devuelven 400 y `message`; un ID inexistente devuelve 404. Se aceptan `/reportes` y `/reportes/`.

## Pruebas

`./gradlew.bat test bootJar` verifica servicio, persistencia y paginación. Para probar HTTP real: `python ../tools/verify_api.py http://localhost:8080`. Este script pertenece al paquete de entrega, no es necesario para ejecutar la API.

Importe `postman/Reportes.postman_collection.json` y `postman/Local.postman_environment.json` en Postman. Ejecute en orden: registrar, listar, detalle, buscar, atender. La colección guarda el ID creado automáticamente. Cambie `baseUrl` para Render, sin barra final.

## Render

1. Suba esta carpeta a su propio repositorio GitHub con el Dockerfile y `render.yaml` en la raíz.
2. En Render cree un Blueprint desde ese repositorio. Seleccione los recursos gratuitos indicados y compruebe disponibilidad en su cuenta.
3. El Blueprint enlaza host, base, usuario y contraseña mediante referencias a la base. El perfil `prod` construye el JDBC desde esas variables; no copie una URL `postgresql://` directamente como JDBC.
4. Espere el despliegue y pruebe `https://SU-SERVICIO.onrender.com/reportes/?offset=0&limit=5`.
5. Ejecute la colección Postman sobre la URL pública y reconstruya la app con esa misma URL.

Configuración manual alternativa: Web Service, runtime Docker, `SPRING_PROFILES_ACTIVE=prod`, `DB_URL=jdbc:postgresql://HOST:5432/BASE?sslmode=require`, `DB_USERNAME`, `DB_PASSWORD`. El puerto usa `PORT` de Render.

Referencias: https://render.com/docs/blueprint-spec y https://render.com/docs/postgresql-creating-connecting . Revise las condiciones vigentes del plan gratuito en su cuenta.
