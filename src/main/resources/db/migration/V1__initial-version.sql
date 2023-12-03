-- Users Table
CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       created_at DATETIME(6) NULL,
                       updated_at DATETIME(6) NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NULL,
                       username VARCHAR(255) NOT NULL
);

-- UserAnswer Table
CREATE TABLE user_answer (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             created_at DATETIME(6) NULL,
                             updated_at DATETIME(6) NULL,
                             answer VARCHAR(255) NULL,
                             question_id INT NULL,
                             topic VARCHAR(255) NULL,
                             author_id BIGINT NULL,
                             CONSTRAINT FK_author_id FOREIGN KEY (author_id) REFERENCES users (user_id)
);

-- Users_User_Answers Table
CREATE TABLE users_user_answers (
                                    user_user_id BIGINT NOT NULL,
                                    user_answers_id BIGINT NOT NULL,
                                    CONSTRAINT UK_user_answers_id UNIQUE (user_answers_id),
                                    CONSTRAINT FK_user_answers_id FOREIGN KEY (user_answers_id) REFERENCES user_answer (id),
                                    CONSTRAINT FK_user_user_id FOREIGN KEY (user_user_id) REFERENCES users (user_id)
);