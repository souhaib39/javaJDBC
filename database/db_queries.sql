USE Banking_System_Project;


ALTER TABLE accounts
    ADD CONSTRAINT fk_email FOREIGN KEY (email) REFERENCES user(email);
