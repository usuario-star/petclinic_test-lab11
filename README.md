# Spring Boot : Application PetClinic

## Features

### 1. Unit Test --> Tag v1.0.0

mvn test -Dspring.profiles.active=h2
mvn test -Dspring.profiles.active=mysql

## Equipo ERROR 404 - Laboratorio 11

| Integrante | Rama Git | Casos / Pruebas Implementadas |
|---|---|---|
| Jerardo Cutipa | `feature/caso2-owner-tests` | Caso 2: `OwnerServiceTest` (CRUD, búsquedas y casos de error) |
| Max Muñoz | `feature/caso1-vet-tests` | Caso 1: `VetServiceTest` (CRUD, búsquedas y activación/desactivación) |
| David Rojas | `feature/caso3-caso5-specialty-type-tests` | Casos 3 y 5: `SpecialtyServiceTest` y `TypeServiceTest` |
| Julio Perez | `feature/caso4-visit-tests` | Caso 4: `VisitServiceTest` (CRUD, relaciones, rangos de fecha y costos) |
| Jerardo Cutipa | `feature/caso6-vet-specialty-tests` | Caso 6: `VetSpecialtyServiceTest` (asignación, búsquedas, especialidad principal y eliminación) |

### Conflictos resueltos
- Conflicto en `README.md` al integrar `feature/caso3-caso5-specialty-type-tests` en `main` (colisión en la tabla de integrantes con `feature/caso1-vet-tests`), resuelto conservando los aportes de ambos integrantes.
