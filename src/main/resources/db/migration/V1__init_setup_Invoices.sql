CREATE TABLE invoices (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       dateOfIssueOrReceipt DATE NOT NULL,
                       amount DECIMAL(12,2) NOT NULL
);