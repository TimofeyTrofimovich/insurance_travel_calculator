insert into classifiers (title, description)
values ('RISK_TYPE', 'travel policy risk type classifier');

insert into classifier_values (classifier_id, ic, description)
values ((select id from classifiers where title = 'RISK_TYPE'), 'TRAVEL_MEDICAL', 'travel policy medical risk'),
       ((select id from classifiers where title = 'RISK_TYPE'), 'TRAVEL_CANCELLATION',
        'travel policy trip cancellation risk'),
       ((select id from classifiers where title = 'RISK_TYPE'), 'TRAVEL_LOSS_BAGGAGE',
        'travel policy baggage lose risk');
insert into classifier_values (classifier_id, ic, description)
values ((select id from classifiers where title = 'RISK_TYPE'), 'TRAVEL_THIRD_PARTY_LIABILITY',
        'Travel policy third party liability risk type'),
       ((select id from classifiers where title = 'RISK_TYPE'), 'TRAVEL_EVACUATION',
        'Travel policy evacuation risk type'),
       ((select id from classifiers where title = 'RISK_TYPE'), 'TRAVEL_SPORT_ACTIVITIES',
        'Travel policy sport activities risk type');


insert into classifiers(title, description)
values ('COUNTRY', 'country classifier');

insert into classifier_values(classifier_id, ic, description)
VALUES ((SELECT id from classifiers where title = 'COUNTRY'), 'LATVIA', 'country: Latvia'),
       ((SELECT id from classifiers where title = 'COUNTRY'), 'SPAIN', 'country: Spain'),
       ((SELECT id from classifiers where title = 'COUNTRY'), 'JAPAN', 'country: Japan');

INSERT INTO travel_medical_country_default_day_rate(country_ic, country_default_day_rate)
VALUES ('LATVIA', 1.00),
       ('SPAIN', 2.50),
       ('JAPAN', 3.50);


INSERT INTO travel_medical_age_coefficient(age_from, age_to, coefficient)
VALUES (0, 5, 1.1),
       (6, 10, 0.7),
       (11, 17, 1.0),
       (18, 40, 1.1),
       (41, 65, 1.2),
       (65, 150, 1.5);

INSERT INTO classifiers(title, description) VALUES ('MEDICAL_RISK_LIMIT_LEVEL', 'medical risk limit level');
INSERT INTO classifier_values(classifier_id, ic, description)
VALUES ((SELECT id FROM classifiers WHERE title = 'MEDICAL_RISK_LIMIT_LEVEL'), 'LEVEL_10000', 'Medical Risk 10000 euro limit level'),
     ((SELECT id FROM classifiers WHERE title = 'MEDICAL_RISK_LIMIT_LEVEL'), 'LEVEL_15000', 'Medical Risk 15000 euro limit level'),
     ((SELECT id FROM classifiers WHERE title = 'MEDICAL_RISK_LIMIT_LEVEL'), 'LEVEL_20000', 'Medical Risk 20000 euro limit level'),
     ((SELECT id FROM classifiers WHERE title = 'MEDICAL_RISK_LIMIT_LEVEL'), 'LEVEL_50000', 'Medical Risk 50000 euro limit level');

INSERT INTO travel_medical_risk_limit_level(medical_risk_limit_level_ic, coefficient) VALUES
                                                                                       ('LEVEL_10000', 1.0),
                                                                                       ('LEVEL_15000', 1.2),
                                                                                       ('LEVEL_20000', 1.5),
                                                                                       ('LEVEL_50000', 2.0);
INSERT INTO travel_cancellation_trip_cost_coefficient(travel_cost_from, travel_cost_to, coefficient)
VALUES (0, 4999.99, 10.0),
       (5000, 9999.99, 30.0),
       (10000, 19999.99, 100.0),
       (20000, 10000000, 500.0);

INSERT INTO travel_cancellation_age_coefficient(age_from, age_to, coefficient)
VALUES (0, 9, 5.00),
       (10, 17, 10.00),
       (18, 39, 20.00),
       (40, 64, 30.00),
       (65, 150, 50.00);

