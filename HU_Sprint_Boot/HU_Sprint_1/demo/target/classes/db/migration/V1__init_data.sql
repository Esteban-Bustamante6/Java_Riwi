-- V1__init_data.sql — Estructura base de Eventify

CREATE TABLE venues (
                        id      BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name    VARCHAR(150) NOT NULL,
                        address VARCHAR(500) NOT NULL,
                        city    VARCHAR(100) NOT NULL
);

CREATE TABLE categories (
                            id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name        VARCHAR(100) NOT NULL UNIQUE,
                            description VARCHAR(300)
);

CREATE TABLE events (
                        id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name        VARCHAR(150) NOT NULL,
                        description VARCHAR(500) NOT NULL,
                        date        TIMESTAMP    NOT NULL,
                        capacity    INT,
                        active      BOOLEAN      NOT NULL DEFAULT TRUE,
                        venue_id    BIGINT       NOT NULL,
                        CONSTRAINT fk_event_venue FOREIGN KEY (venue_id) REFERENCES venues(id)
);


CREATE TABLE events_categories (
                                   event_id    BIGINT NOT NULL,
                                   category_id BIGINT NOT NULL,
                                   PRIMARY KEY (event_id, category_id),
                                   CONSTRAINT fk_ec_event    FOREIGN KEY (event_id)    REFERENCES events(id),
                                   CONSTRAINT fk_ec_category FOREIGN KEY (category_id) REFERENCES categories(id)
);
