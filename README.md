# EMT Labs - Extended Book Features

This project includes:

- Book listing/search with pagination, sorting, and filtering
- Projection-based book endpoints
- Database view endpoint (`book_library_view`)
- Materialized view endpoint (`book_category_stats_mv`)
- Scheduled materialized view refresh
- Event-driven rental handling with activity logging

## Swagger UI

Open:

- `http://localhost:8080/swagger-ui.html`

## New/Relevant Endpoints

- `GET /api/books` - paginated summary projection search
- `GET /api/books/details` - paginated detailed projection
- `GET /api/books/view` - read from database view
- `GET /api/books/category-stats` - read from materialized view
- `POST /api/books/{id}/rent` - rent a book and trigger events
- `GET /api/activity-logs` - paginated activity logs

## Activity Logging

Activity records are stored in `activity_log` with:

- `id`
- `book_name`
- `event_timestamp`
- `event_type`

Recorded events:

- `BOOK_RENTED`
- `BOOK_UNAVAILABLE`

## Run (Windows PowerShell)

```powershell
Set-Location "C:\Users\User\Desktop\FinkiEmtLabs"
.\mvnw.cmd spring-boot:run
```

## Try It Quickly (PowerShell)

```powershell
Invoke-RestMethod "http://localhost:8080/api/books?page=0&size=5&sortBy=NAME&direction=ASC"
Invoke-RestMethod "http://localhost:8080/api/books/details?page=0&size=5"
Invoke-RestMethod "http://localhost:8080/api/books/view"
Invoke-RestMethod "http://localhost:8080/api/books/category-stats"
Invoke-RestMethod "http://localhost:8080/api/activity-logs?page=0&size=10"
Invoke-RestMethod -Method Post "http://localhost:8080/api/books/1/rent"
```

