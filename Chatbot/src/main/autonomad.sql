CREATE TABLE `booking` (
    `booking_id` int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `car_id` int NOT NULL,
    `pickup_date` datetime NOT NULL,
    `return_date` datetime NOT NULL,
    `total_cost` int NOT NULL,
    PRIMARY KEY (`booking_id`)
);

CREATE TABLE `user` (
    `user_id` int NOT NULL AUTO_INCREMENT,
    `first_name` varchar(50) NOT NULL,
    `second_name` varchar(50) NOT NULL,
    `email` varchar(50) NOT NULL,
    `phone` int NOT NULL,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `FAQ` (
    `question_id` int NOT NULL AUTO_INCREMENT,
    `topic_id` int NOT NULL,
    `question` varchar(150) NOT NULL,
    `answer` varchar(150) NOT NULL,
    PRIMARY KEY (`question_id`)
);

CREATE TABLE `support_ticket` (
    `ticket_id` int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `topic_id` int NOT NULL,
    `status_id` int NOT NULL,
    `ticket_date` int NOT NULL,
    PRIMARY KEY (`ticket_id`)
);

CREATE TABLE `chat_session` (
    `session_id` int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `start_time` datetime NOT NULL,
    `end_time` datetime NOT NULL,
    `status_id` int NOT NULL,
    PRIMARY KEY (`session_id`)
);

CREATE TABLE `status` (
    `status_id` int NOT NULL,
    `status` varchar(15) NOT NULL,
    PRIMARY KEY (`status_id`)
);

CREATE TABLE `chat_log` (
    `log_id` int NOT NULL AUTO_INCREMENT,
    `session_id` int NOT NULL,
    `topic_id` int NOT NULL,
    `timestamp` datetime NOT NULL,
    `sender` varchar(5) NOT NULL,
    `message` varchar(100) NOT NULL,
    PRIMARY KEY (`log_id`)
);

CREATE TABLE `review` (
    `review_id` int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `booking_id` int NOT NULL,
    `rating` int NOT NULL,
    `comment` varchar(500) NULL,
    PRIMARY KEY (`review_id`)
);

CREATE TABLE `car` (
    `car_id` int NOT NULL AUTO_INCREMENT,
    `car_make` varchar(15) NOT NULL,
    `car_model` varchar(10) NOT NULL,
    `car_type_id` int NOT NULL,
    `year` int NOT NULL,
    `registration_number` varchar(10) NOT NULL,
    `rental_price` int NOT NULL,
    PRIMARY KEY (`car_id`)
);

CREATE TABLE `car_type` (
    `car_type_id` int NOT NULL AUTO_INCREMENT,
    `car_type` varchar(25) NOT NULL,
    PRIMARY KEY (`car_type_id`)
);

CREATE TABLE `features` (
    `feature_id` int NOT NULL AUTO_INCREMENT,
    `feature` varchar(15) NOT NULL,
    PRIMARY KEY (`feature_id`)
);

CREATE TABLE `car_features` (
    `car_id` int NOT NULL,
    `feature_id` int NOT NULL,
    PRIMARY KEY (`car_id`, `feature_id`)
);

CREATE TABLE `topic` (
    `topic_id` int NOT NULL AUTO_INCREMENT,
    `topic` varchar(30) NOT NULL,
    PRIMARY KEY (`topic_id`)
);

ALTER TABLE `booking`
    ADD FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
    ADD FOREIGN KEY (`car_id`) REFERENCES `car`(`car_id`);
ALTER TABLE `FAQ`
    ADD FOREIGN KEY (`topic_id`) REFERENCES `topic`(`topic_id`);
ALTER TABLE `support_ticket`
    ADD FOREIGN KEY (`user_id`) REFERENCES `user`(user_id),
    ADD FOREIGN KEY (`topic_id`) REFERENCES `topic`(`topic_id`),
    ADD FOREIGN KEY (`status_id`) REFERENCES `status`(`status_id`);
ALTER TABLE `chat_session`
    ADD FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
    ADD FOREIGN KEY (`status_id`) REFERENCES `status`(`status_id`);
ALTER TABLE `chat_log`
    ADD FOREIGN KEY (`session_id`) REFERENCES `chat_session`(`session_id`),
    ADD FOREIGN KEY (`topic_id`) REFERENCES `topic`(`topic_id`);
ALTER TABLE `review`
    ADD FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
    ADD FOREIGN KEY (`booking_id`) REFERENCES `booking`(`booking_id`);
ALTER TABLE `car`
    ADD FOREIGN KEY (`car_type_id`) REFERENCES `car_type`(`car_type_id`);
ALTER TABLE `car_features`
    ADD FOREIGN KEY (`car_id`) REFERENCES `car`(`car_id`),
    ADD FOREIGN KEY (`feature_id`) REFERENCES `features`(`feature_id`);

SHOW ENGINE INNODB STATUS;
