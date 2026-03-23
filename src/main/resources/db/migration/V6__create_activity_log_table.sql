CREATE TABLE IF NOT EXISTS activity_log
(
    id BIGSERIAL PRIMARY KEY,
    book_name VARCHAR(255) NOT NULL,
    event_timestamp TIMESTAMP NOT NULL,
    event_type VARCHAR(50) NOT NULL
);

CREATE INDEX IF NOT EXISTS ix_activity_log_event_timestamp
    ON activity_log (event_timestamp DESC);

