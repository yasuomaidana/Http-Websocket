CREATE TABLE user_book (
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, book_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
);