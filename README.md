# Automagazine API

REST API для интернет-магазина автозапчастей.

## Стек технологий

- Java 25
- Spring Boot 4.1.1
- PostgreSQL 18
- Flyway
- MapStruct
- Lombok
- Springdoc OpenAPI (Swagger)
- Docker + Docker Compose

## Как запустить

### Через Docker Compose (рекомендуется)

```bash
# Клонировать репозиторий
git clone https://github.com/Wazewa/Automagazine.git
cd Automagazine

# Создать .env из шаблона
cp .env.example .env

# Запустить
docker compose up -d --build
```

Приложение будет доступно на [http://localhost:8080](http://localhost:8080).
При первом запуске Flyway автоматически применит миграции и создаст схему БД.

### Локально

```bash
# Собрать JAR
mvn clean package -DskipTests

# Запустить с профилем dev
java -jar target/*.jar --spring.profiles.active=dev
```

## Архитектура

Проект построен по feature-based архитектуре:
- Каждая фича (`admin/`, `customer/`, `product/`, ...) — самодостаточный пакет
- Внутри фичи: `controller/`, `service/`, `entity/`, `dto/`, `mapper/`, `repository/`, `exception/`
- Конфигурация в `config/`

Это позволяет легко выделить любую фичу в отдельный микросервис.

## API документация

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Основные endpoints

| Ресурс | URL |
|--------|-----|
| Admins | `/api/admins` |
| Customers | `/api/customers` |
| Products | `/api/products` |
| Categories | `/api/categories` |
| Compatibilities | `/api/compatibilities` |
| Events | `/api/events` |
| Carts | `/api/carts` |
| Cart Items | `/api/carts/{cartId}/items` |
| Orders | `/api/orders` |
| Order Items | `/api/orders/{orderId}/items` |

## Тестирование

### Swagger UI

Все endpoints доступны для тестирования прямо из браузера:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Postman

Postman коллекция: `postman/Automagazine.postman_collection.json` (в разработке)
