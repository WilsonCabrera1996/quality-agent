# Acta de Constitución del Proyecto: Estándares de Ingeniería (Speckit Style)

Este documento define las bases técnicas y de calidad para el desarrollo del proyecto, asegurando escalabilidad, mantenibilidad y robustez.

---

## 1. Arquitectura: Clean Architecture (Robert C. Martin)
Se implementará una arquitectura desacoplada para proteger las reglas de negocio de cambios externos.

### Implementación Técnica:
- **Capa de Dominio (`src/main/java/.../domain`):**
    - Contiene Entidades y Value Objects.
    - **Regla:** Cero dependencias externas (incluyendo frameworks como Spring o JPA).
- **Capa de Aplicación (`src/main/java/.../application`):**
    - Contiene los Puertos (Interfaces) y Casos de Uso.
    - Orquesta el flujo de datos.
- **Capa de Infraestructura (`src/main/java/.../infrastructure`):**
    - Implementación de adaptadores (Persistencia, Clientes Externos, UI/Controllers).
    - Aquí residen las anotaciones de Spring (`@Service`, `@Repository`, `@RestController`).

---

## 2. Estrategia de Pruebas: BDD (Behavior Driven Development)
El desarrollo se guiará por el comportamiento esperado, documentado en lenguaje natural.

### Implementación Técnica:
- **Herramientas:** JUnit 5 + AssertJ + Mockito + Cucumber.
- **Estructura:**
    - `src/test/resources/features/*.feature`: Definición de escenarios en Gherkin (Given/When/Then).
    - `src/test/java/.../bdd/StepDefinitions.java`: Glue code que ejecuta la lógica de prueba.
- **Niveles:**
    - **Unitarias:** Mocking de dependencias con Mockito. Cobertura de lógica de dominio.
    - **Integración:** Uso de `@SpringBootTest` con bases de datos en memoria (H2) o Testcontainers.
    - **Funcionales:** Pruebas E2E sobre los endpoints generados, validando el contrato OpenAPI.

---

## 3. Buenas Prácticas: SOLID, YAGNI, DRY
Principios fundamentales para la calidad del código.

### Aplicación Técnica:
- **SOLID:**
    - Inyección de dependencias por constructor obligatoria.
    - Uso intensivo de interfaces para desacoplar componentes.
- **YAGNI:** Prohibido crear abstracciones para "posibles" futuros casos. El código debe resolver solo el requerimiento actual.
- **DRY:** Uso de utilitarios comunes o Shared Kernel en el dominio para evitar duplicidad de lógica de validación.

---

## 4. Estrategia API First: OpenAPI & Generation
El contrato es el punto de partida y la única fuente de verdad para la comunicación.

### Implementación Técnica:
- **Contrato:** `src/main/resources/api/openapi.yaml`.
- **Automatización:** Uso del plugin `org.openapi.generator`.
- **Configuración Gradle:**
  ```gradle
  openApiGenerate {
      generatorName = "spring"
      inputSpec = "$projectDir/src/main/resources/api/openapi.yaml".toString()
      outputDir = "$buildDir/generated-sources".toString()
      apiPackage = "com.example.demo.infrastructure.api"
      modelPackage = "com.example.demo.infrastructure.api.model"
      configOptions = [
          interfaceOnly: "true",
          useSpringBoot3: "true",
          useTags: "true"
      ]
  }
  ```
- **Regla:** El código generado **no se commitea** al repositorio; se genera en tiempo de compilación.

---

## 5. Métricas de Calidad: JaCoCo
Garantía cuantitativa de la integridad del software.

### Implementación Técnica:
- **Configuración de Umbrales:**
  ```gradle
  jacocoTestCoverageVerification {
      violationRules {
          rule {
              limit {
                  minimum = 0.80 // Cobertura Global >= 80%
              }
          }
          rule {
              element = 'CLASS'
              limit {
                  counter = 'LINE'
                  value = 'COVEREDRATIO'
                  minimum = 0.80 // Cobertura por Clase > 80%
              }
          }
      }
  }
  check.dependsOn jacocoTestCoverageVerification
  ```
- **Reportes:** Generación automática de HTML y XML para análisis en SonarQube o similar.

---

## 6. Proceso de Desarrollo
1. Definir/Actualizar `openapi.yaml`.
2. Ejecutar `gradle openApiGenerate`.
3. Implementar Casos de Uso y Entidades (Domain/Application).
4. Implementar adaptadores (Infrastructure) inyectando los Puertos.
5. Crear Features de Cucumber y ejecutar pruebas hasta cumplir el coverage del 80%.
