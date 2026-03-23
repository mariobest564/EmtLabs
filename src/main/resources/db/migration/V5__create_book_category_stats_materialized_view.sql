CREATE MATERIALIZED VIEW IF NOT EXISTS book_category_stats_mv AS
SELECT
    b.category,
    COUNT(*)::BIGINT AS total_books,
    COALESCE(SUM(b.available_copies), 0)::BIGINT AS total_available_copies,
    COALESCE(SUM(CASE WHEN b.state <> 'GOOD' THEN 1 ELSE 0 END), 0)::BIGINT AS not_good_condition_books
FROM book b
WHERE b.deleted = FALSE
GROUP BY b.category;

CREATE UNIQUE INDEX IF NOT EXISTS ux_book_category_stats_mv_category
    ON book_category_stats_mv (category);

