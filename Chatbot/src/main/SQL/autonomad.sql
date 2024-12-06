CREATE TABLE booking (
    booking_id SERIAL NOT NULL,
    customer_id INTEGER NOT NULL,
    car_id INTEGER NOT NULL,
    pickup_date TIMESTAMP NOT NULL,
    return_date TIMESTAMP NOT NULL,
    total_cost INTEGER NOT NULL,
    PRIMARY KEY (booking_id)
);

CREATE TABLE customer (
    customer_id SERIAL NOT NULL,
    first_name varchar(50) NOT NULL,
    second_name varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    phone BIGINT NOT NULL,
    password varchar(50) NOT NULL,
    PRIMARY KEY (customer_id)
);

CREATE TABLE customer_details (
    customer_id INTEGER NOT NULL,
    DL_number varchar(50) NOT NULL,
    National_ID_number INTEGER NOT NULL,
    PRIMARY KEY (customer_id)
);

CREATE TABLE FAQ (
    question_id SERIAL NOT NULL,
    topic_id INTEGER NOT NULL,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    PRIMARY KEY (question_id)
);

CREATE TABLE support_ticket (
    ticket_id SERIAL NOT NULL,
    customer_id INTEGER NOT NULL,
    topic_id INTEGER NOT NULL,
    status_id INTEGER NOT NULL,
    ticket_date INTEGER NOT NULL,
    PRIMARY KEY (ticket_id)
);

CREATE TABLE chat_session (
    session_id SERIAL NOT NULL,
    customer_id INTEGER NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status_id INTEGER NOT NULL,
    PRIMARY KEY (session_id)
);

CREATE TABLE status (
    status_id INTEGER NOT NULL,
    status varchar(15) NOT NULL,
    PRIMARY KEY (status_id)
);

CREATE TABLE chat_log (
    log_id SERIAL NOT NULL,
    session_id INTEGER NOT NULL,
    topic_id INTEGER NOT NULL,
    log_time TIMESTAMP NOT NULL,
    sender varchar(5) NOT NULL,
    message TEXT NOT NULL,
    PRIMARY KEY (log_id)
);

CREATE TABLE review (
    review_id SERIAL NOT NULL,
    customer_id INTEGER NOT NULL,
    booking_id INTEGER NOT NULL,
    rating INTEGER NOT NULL,
    comment TEXT NULL,
    PRIMARY KEY (review_id)
);

CREATE TABLE car (
    car_id SERIAL NOT NULL,
    car_make varchar(15) NOT NULL,
    car_model varchar(10) NOT NULL,
    car_type_id INTEGER NOT NULL,
    year INTEGER NOT NULL,
    registration_number varchar(10) NOT NULL,
    rental_price INTEGER NOT NULL,
    PRIMARY KEY (car_id)
);

CREATE TABLE car_type (
    car_type_id SERIAL NOT NULL,
    car_type varchar(25) NOT NULL,
    PRIMARY KEY (car_type_id)
);

CREATE TABLE features (
    feature_id SERIAL NOT NULL,
    feature varchar(50) NOT NULL,
    PRIMARY KEY (feature_id)
);

CREATE TABLE car_features (
    car_id INTEGER NOT NULL,
    feature_id INTEGER NOT NULL,
    PRIMARY KEY (car_id, feature_id)
);

CREATE TABLE topic (
    topic_id SERIAL NOT NULL,
    topic varchar(30) NOT NULL,
    PRIMARY KEY (topic_id)
);

ALTER TABLE booking
    ADD FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    ADD FOREIGN KEY (car_id) REFERENCES car(car_id)
        ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE FAQ
    ADD FOREIGN KEY (topic_id) REFERENCES topic(topic_id)
        ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE support_ticket
    ADD FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    ADD FOREIGN KEY (topic_id) REFERENCES topic(topic_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    ADD FOREIGN KEY (status_id) REFERENCES status(status_id)
        ON UPDATE CASCADE;
ALTER TABLE chat_session
    ADD FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    ADD FOREIGN KEY (status_id) REFERENCES status(status_id)
        ON UPDATE CASCADE;
ALTER TABLE chat_log
    ADD FOREIGN KEY (session_id) REFERENCES chat_session(session_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    ADD FOREIGN KEY (topic_id) REFERENCES topic(topic_id)
        ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE review
    ADD FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    ADD FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
        ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE car
    ADD FOREIGN KEY (car_type_id) REFERENCES car_type(car_type_id)
        ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE car_features
    ADD FOREIGN KEY (car_id) REFERENCES car(car_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    ADD FOREIGN KEY (feature_id) REFERENCES features(feature_id)
        ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE customer_details
    ADD FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
        ON DELETE CASCADE ON UPDATE CASCADE;
