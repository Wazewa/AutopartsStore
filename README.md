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
git clone <url>
```

# Создать .env из шаблона
cp .env.example .env

# Запустить
```bash
docker compose up -d --build
```

Приложение будет доступно на http://localhost:8080

### Локально

# Собрать JAR
```bash
mvn clean package -DskipTests
```

# Запустить с профилем dev
```bash
java -jar target/*.jar --spring.profiles.active=dev
```

## API документация

Swagger UI: http://localhost:8080/swagger-ui.html

## Основные endpoints

| Ресурс | URL |
|--------|-----|
| Admins | /api/admins |
| Customers | /api/customers |
| Products | /api/products |
| Categories | /api/categories |
| Compatibilities | /api/compatibilities |
| Events | /api/events |
| Carts | /api/carts |
| Cart Items | /api/carts/{cartId}/items |
| Orders | /api/orders |
| Order Items | /api/orders/{orderId}/items |

## Тестирование

### Swagger UI

Все endpoints доступны для тестирования прямо из браузера:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Postman

Postman коллекция: `postman/Automagazine.postman_collection.json` (в разработке)
