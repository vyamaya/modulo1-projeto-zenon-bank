create table transactions(
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             step INT NOT NULL,
                             type VARCHAR(20) NOT NULL,
                             amount DECIMAL(20,2) NOT NULL,
                             name_origin VARCHAR(50) NOT NULL,
                             old_balance_origin DECIMAL(20,2) NOT NULL,
                             new_balance_origin DECIMAL(20,2) NOT NULL,
                             name_recipient VARCHAR(50) NOT NULL,
                             old_balance_recipient DECIMAL(20,2) NOT NULL,
                             new_balance_recipient DECIMAL(20,2) NOT NULL,
                             is_fraud TINYINT(1) DEFAULT 0,
                             is_flagged_fraud TINYINT(1) DEFAULT 0
);

INSERT into transactions(step, `type`, amount, name_origin, old_balance_origin, new_balance_origin, name_recipient, old_balance_recipient, new_balance_recipient, is_fraud, is_flagged_fraud)
values(1,'PAYMENT',5000.00,'C1000001',5000.00,0.00,'M1000001',0.00,0.00,0,0);

SELECT * FROM transactions t ;