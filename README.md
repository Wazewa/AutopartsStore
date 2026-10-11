# Autoparts Store API

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
- Каждая фича (`user/`, `product/`, ...) — самодостаточный пакет
- Внутри фичи: `controller/`, `service/`, `entity/`, `dto/`, `mapper/`, `repository/`, `exception/`
- Конфигурация в `config/`

Это позволяет легко выделить любую фичу в отдельный микросервис.

## API документация

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Конфигурация

| Переменная | Описание | По умолчанию |
|---|---|---|
| `JWT_SECRET` | Base64URL-секрет для подписи JWT (≥ 32 байта) | — (обязательно) |
| `JWT_ACCESS_TTL` | Время жизни access-токена (мс) | `900000` (15 мин) |
| `POSTGRES_DB` | Имя БД | `autopartsstore` |
| `POSTGRES_USER` | Пользователь БД | `postgres` |
| `POSTGRES_PASSWORD` | Пароль БД | — |

## Основные endpoints

| Ресурс | URL |
|--------|-----|
| Users | `/api/users` |
| Products | `/api/products` |
| Categories | `/api/categories` |
| Compatibilities | `/api/compatibilities` |
| Events | `/api/events` |
| Carts | `/api/carts` |
| Cart Items | `/api/carts/{cartId}/items` |
| Orders | `/api/orders` |
| Order Items | `/api/orders/{orderId}/items` |

## Безопасность

- **JWT-аутентификация** (HS256, access-токен 15 мин).
- **Ролевая модель**: `ROLE_ADMIN`, `ROLE_CUSTOMER`.
- **Method-level security** — `@PreAuthorize` на сервисном слое.
- **Owner-check** — клиент видит/меняет только свои ресурсы
  (`requireOwnOrder`, `requireOwnCart`).
- **Пароли** — BCrypt.
- **Публичные эндпоинты**: `/api/auth/**`, `GET /api/products/**`,
  `GET /api/categories/**`, `GET /api/compatibilities/**`,
  `POST /api/events`, Swagger.

### Как авторизоваться в Swagger

1. `POST /api/auth/login` → получить токен.
2. Кнопка **Authorize** → вставить токен **без** префикса `Bearer`.
3. Теперь защищённые эндпоинты доступны.

## Тестирование

### Swagger UI

Все endpoints доступны для тестирования прямо из браузера:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Postman

Коллекция в разработке. Примеры запросов — в Swagger UI.
