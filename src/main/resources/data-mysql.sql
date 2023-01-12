INSERT INTO role(name) VALUES ("ROLE_REDACTEUR"),("ROLE_ADMINISTRATEUR");

INSERT INTO user(email,password,role_id) VALUES ("a@a.com","azerty",NULL),("b@b.com","azerty",1),("c@c.com","azerty",2);

INSERT INTO module(name,description) VALUES ("CDA","Concepteurs développeurs d'application"), ("DWWM","Développeur Web et Web Mobile");

INSERT INTO user_module( user_id, module_id) VALUES (1,1),(1,2),(2,2);