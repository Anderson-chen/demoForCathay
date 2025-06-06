CREATE TABLE currency (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    CONSTRAINT UK_currency_code UNIQUE (code)
);


INSERT INTO currency (id, code, name) VALUES
    (1, 'USD', '美元'),
    (2, 'GBP', '英鎊'),
    (3, 'EUR', '歐元');