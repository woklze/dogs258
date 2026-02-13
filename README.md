# dogs258
Перевод из адреса  координаты
http://localhost:8080/api/maps/geocode?address=Москва

Вывести список всех пород
http://localhost:8080/api/allBreeds

# старая работа
## Управление пользователями 
1. Регистрация
  ЗАПРОС:
POST /api/v1/users HTTP/1.1
Host: www.walking25/8.com
User-Agent: MyDogApp/1.0
Accept: application/json
{
  "email": "user@example.com",
  "password": "secure_password",
  "name": "Иван Иванов",
  "phone": "+79991234567"
}

  Коды ответа:
201 Created (пользователь успешно создан)
400 Bad Request (пустые поля/некорректный email)
409 Conflict (email уже зарегистрирован)
   
   ОТВЕТ:
HTTP/1.1 201 Created
Date: Sun, 25 Jan 2026 12:00:00 GMT
Content-Type: application/json
Content-Length: 89
{"id": 123, "name": "Иван"}

1. Авторизация
  ЗАПРОС:
POST /api/v1/auth/login HTTP/1.1
Host: www.walking25/8.com
User-Agent: MyDogApp/1.0
Accept: application/json
{
  "email": "user@example.com",
  "password": "secure_password",
}

  Коды ответа:
200 OK
401 — Unauthorized (неверный email/пароль)
   
   ОТВЕТ1:
HTTP/1.1 200 OK
Date: Sun, 25 Jan 2026 12:00:00 GMT
Content-Type: application/json
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMDAxLCJyb2xlIjoiY2xpZW50IiwiZXhwIjoxNzM3ODk0ODAwfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
  "token_type": "Bearer",
  "expires_in": 3600,
  "user": {
    "name": "Иван",
    "email": "ivan.ivanov@example.com",
    "role": "client",
  }
}

  ОТВЕТ2:
HTTP/1.1 401 Unauthorized
Date: Sun, 25 Jan 2Desktop 20:36:00 GMT
Content-Type: application/json
{
  "error": "Invalid credentials",
  "message": "Email or password is incorrect"
}

## Управление заказами (для владельцев) 
1. Создание заказа
ЗАПРОС: 
POST /api/v1/orders HTTP/1.1
Host: www.walking25/8.com
User-Agent: MyDogApp/1.0
Accept: application/json
{
  "dog":{"breed": "Лабрадор","size": ["большой" | "средний" | "маленький"], "photoUrl": "https://...", "specialNeeds": "энергичность"},
  "timeWindow": { "start": "17:00", "end": "18:00" },
  "frequency": "one_time" | "recurring", (одноразовый или регулярный)
  "daysOfWeek": ["Mon","Wed","Fri"], 
  "durationMinutes": 30 | 60 | 90,
  "address":{city:"Екатеринбург", "region":"Кировский", "street": "Мира 1"}, 
  "budget": 400
}

  Коды ответа: 
201 Created  (заказ создан)
400 Bad Request (пустые поля/некорректные данные)
403 Forbidden (не авторизован как владелец)

  ОТВЕТ:
HTTP/1.1 201 Created 
Date: Sun, 25 Jan 2026 12:00:00 GMT
Content-Type: application/json
Content-Length: 89
{ "orderId": 456, "dog": { ... }, "status": "open", "budget": 400, "createdAt": "2026-01-25T12:10:00Z", ...}

2. Изменение заказа 
  ЗАПРОС: 
PUT /api/v1/orders/{orderId} HTTP/1.1 
Host: www.walking25/8.com
User-Agent: MyDogApp/1.0
Accept: application/json
{
"dog":{"breed": "Лабрадор","size": ["большой" | "средний" | "маленький"], "photoUrl": "https://...", "specialNeeds": "энергичность"},
  "timeWindow": { "start": "17:00", "end": "18:00" },
  "frequency": "one_time" | "recurring", (одноразовый или регулярный)
  "daysOfWeek": ["Mon","Wed","Fri"], 
  "durationMinutes": 30 | 60 | 90,
  "address":{city:"Екатеринбург", "region":"Кировский", "street": "Мира 1"}, 
  "budget": 450
}
 (частичные обновления, можно изменить любое из полей)

Коды ответа: 
200 OK (заказ успешно обновлён) 
400 Bad Request (неверные данные)
401 Unauthorized (нет валидной авторизации)
403 Forbidden (статус, не позволяющий изменение) 
404 Not Found (заказ не найден)

ОТВЕТ: 
HTTP/1.1 200 OK
Date: Sun, 25 Jan 2026 12:00:00 GMT
Content-Type: application/json 
{ "orderId": 456, "status": "open", "budget": 450, "updatedAt": "2026-01-25T12:15:00Z" }

3. Удаление заказа 
   ЗАПРОС: 
DELETE /api/v1/orders/{orderId} HTTP/1.1 
Host: www.walking25/8.com 
User-Agent: MyDogApp/1.0
Accept: application/json

Коды ответа: 
200 OK (заказ удалён) 
401 Unauthorized (нет валидной авторизации)
403 Forbidden (принятый заказ, который нельзя удалить )
404 Not Found (заказ не найден)

  ОТВЕТ: 
HTTP/1.1 200 OK 
Date: Sun, 25 Jan 2026 12:20:00 GMT
Content-Type: application/json
Content-Length: 100
{ "orderId": 456, "status": "deleted", "deletedAt": "2026-01-25T12:20:00Z" }

## Лента заказов для исполнителей
1. Поик и фильтрация заказов
  ЗАПРОС: 
GET /api/v1/orders
Host: www.walking25/8.com
User-Agent: MyDogApp/1.0
Accept: application/json
{
  "area":{"city": "Екатеринбург", "region": "Кировский"},
  "timeWindow": { "start": "13:00", "end": "19:00" }
  "budget":{"priceMin": 300, "priceMax":5000},
  "daysOfWeek": ["Mon"], 
  "page": 1
}

Коды ответа: 
200 OK 
400 Bad Request (некорректные данные)
401 Unauthorized

  ОТВЕТ: 
HTTP/1.1 200 OK 
Date: Sun, 25 Jan 2026 12:00:00 GMT
Content-Type: application/json
Content-Length: 89
{ "page": 1, "size": 20, "total": 120, "orders": [ { "orderId": 456, "dog": { "breed": "Golden Retriever", "photoUrl": "https://..." }, "timeWindow": { "start": "17:00", "end": "18:00" }, "address": { {city:"Екатеринбург", "region":"Кировский", "street": "Мира 1" }, "budget": 500, "status": "open", "sellerRating": 4.9 }

2. Взять заказ 
  ЗАПРОС: 
POST /api/v1/orders/{orderId}/take
Host: www.walking25/8.com
User-Agent: MyDogApp/1.0
Accept: application/json

  Коды ответа:
200 OK (заказ взят и начинается договор)
409 Conflict (заказ уже взят другим исполнителем)
404 Not Found (заказ не найден)

  ОТВЕТ: 
HTTP/1.1 200 OK
Date: Sun, 25 Jan 2026 12:00:00 GMT
Content-Type: application/json
Content-Length: 50
 { "orderId": 456, "status": "matched", "contractId": "contract_789", "autoGeneratedOffer": { "commission": 25, "payoutToWalker": 475 } }

## Договор
1. Подписание и оплата 
  ЗАПРОС: 
POST /api/v1/contracts/{contractId}/sign 
Host: www.walking25/8.com
User-Agent: MyDogApp/1.0
Accept: application/json

Коды ответа: 
200 OK (подписи получены, платеж инициирован)
402 Payment Required (недостаточно средств)
403 Forbidden (неверная роль)

ОТВЕТ: 
HTTP/1.1 200 OK 
Date: Sun, 25 Jan 2026 12:00:00 GMT
Content-Type: application/json
Content-Length: 200
{ "contractId": "contract_789", "status": "matched", "transactionId": "txn_12345", "prepayment": 100, "budget": 500}

2. Просмотр договора 
  ЗАПРОС: 
GET /api/v1/contracts/{contractId}
Host: www.walking25/8.com
User-Agent: MyDogApp/1.0
Accept: application/json

Коды ответа: 
200 OK 
404 Not Found

  ОТВЕТ:
HTTP/1.1 200 OK 
Date: Sun, 25 Jan 2026 12:00:00 GMT
Content-Type: application/json
Content-Length: 89
{ "contractId": "contract_789", "owner": { "id": 123, "name": "Иван Иванов" }, "walker": { "id": 456, "name": "Петр Петров" }, "terms": { "startDate": "2026-02-01", "endDate": "2026-02-01", "timeWindow": { "start": "17:00", "end": "18:00" }, "durationMinutes": 60 }, "budget": 500, "prepayment": 100, "commission": 25, "status": "matched", "signature": { "owner": true, "walker": true } }

## Оплата
1. Завершение заказа и списание средств 
  ЗАПРОС: 
POST /api/v1/contracts/{contractId}/complete
Host: www.walking25/8.com
User-Agent: MyDogApp/1.0
Accept: application/json

  Коды ответа: 
200 OK (завершено, средства переведены - 5% комиссия) 
402 Payment Required (недостаточно средств) 

  ОТВЕТ: 
HTTP/1.1 200 OK 
Date: Sun, 25 Jan 2026 12:00:00 GMT
Content-Type: application/json
Content-Length: 100
{ "contractId": "contract_789", "status": "completed", "budget": 500, "prepayment": 100, "commission": 25, "payoutToWalker": 375, "payoutToWalkerTotal": 475,  "transactionId": "txn_67890" }

2. История платежей 
  ЗАПРОС: 
GET /api/v1/users/{id}/payments 
Host: www.walking25/8.com
User-Agent: MyDogApp/1.0
Accept: application/json

Коды ответа: 
200 OK
404 Not Found

  ОТВЕТ: 
HTTP/1.1 200 OK 
Date: Sun, 25 Jan 2026 12:00:00 GMT
Content-Type: application/json
Content-Length: 90
{ "payments": [ { "paymentId": "pay_1", "amount": 500, "currency": "RUB", "date": "2026-01-25T12:15:00Z", "type": "charge" }, {  "paymentId": "pay_2", "amount": 600, "currency": "RUB", "date": "2026-01-27T12:15:00Z", "type": "charge" } }

