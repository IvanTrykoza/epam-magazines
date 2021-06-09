-- -----------------------------------------------------
-- Schema submag
-- -----------------------------------------------------

CREATE DATABASE IF NOT EXISTS submag;
USE submag;


DROP TABLE if exists category;
DROP TABLE if exists magazine;
DROP TABLE if exists receipt;
DROP TABLE if exists receipt_has_product;
DROP TABLE if exists role;
DROP TABLE if exists status;
DROP TABLE if exists user;

CREATE TABLE role (
    role_id INT AUTO_INCREMENT,
    role_name VARCHAR(45) NOT NULL UNIQUE, -- NOT NULL
    PRIMARY KEY (role_id)
);

CREATE TABLE user (
    user_id INT AUTO_INCREMENT,
    login VARCHAR(45) NOT NULL,     -- NOT NULL
    password VARCHAR(45) NOT NULL,  -- NOT NULL
    user_name VARCHAR(256) NOT NULL,     -- NOT NULL
    status TINYINT DEFAULT 1,
    role_id INT NOT NULL            -- NOT NULL
		REFERENCES role (role_id)
		ON UPDATE CASCADE
		ON DELETE RESTRICT,
    wallet DECIMAL(15 , 2 ) UNSIGNED NULL DEFAULT 0,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY pk_user_id (user_id),
    UNIQUE KEY uq_user_login (login)
);

CREATE TABLE category (
    category_id INT AUTO_INCREMENT,
    category_name VARCHAR(45) NOT NULL, 		-- NOT NULL
    PRIMARY KEY pk_category_id (category_id),
    UNIQUE KEY uq_category_name (category_name)
);

CREATE TABLE magazine (
    magazine_id INT AUTO_INCREMENT,
    magazine_name VARCHAR(256) NOT NULL,                  -- NOT NULL
    description VARCHAR(1024) NULL,
    category_id INT
		REFERENCES category (category_id)
		ON UPDATE CASCADE
		ON DELETE SET NULL,
    price DECIMAL(15 , 2 ) UNSIGNED NOT NULL,   -- NOT NULL
    PRIMARY KEY pk_magazine_id (magazine_id),
    UNIQUE KEY uq_magazine_name (magazine_name),
    CONSTRAINT ch_magazine_price CHECK (price >= 0)
);

CREATE TABLE status (
    status_id INT AUTO_INCREMENT,
    status_name VARCHAR(45) NOT NULL,          -- NOT NULL
    PRIMARY KEY pk_status_id (status_id),
    UNIQUE KEY uq_status_name (status_name)
);

CREATE TABLE receipt (
    receipt_id INT AUTO_INCREMENT,
    total_price DECIMAL(15 , 2 ) NOT NULL, -- NOT NULL
    user_id INT
		REFERENCES user (user_id)
		ON DELETE SET NULL
		ON UPDATE CASCADE,
    status_id INT NOT NULL                  -- NOT NULL
		REFERENCES status (status_id)
		ON DELETE RESTRICT
		ON UPDATE CASCADE,
	create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY pk_receipt_id (receipt_id),
    CONSTRAINT ch_total_price CHECK (total_price >= 0)
);

CREATE TABLE receipt_has_product (
    receipt_id INT
		REFERENCES receipt (receipt_id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
    magazine_id INT
		REFERENCES magazine (magazine_id)
		ON UPDATE CASCADE
		ON DELETE RESTRICT,
    price DECIMAL(15 , 2 ) UNSIGNED NOT NULL,   -- NOT NULL
    PRIMARY KEY (receipt_id , magazine_id),
    CONSTRAINT ck_receipt_has_magazine_price CHECK (price >= 0)
);

-- -----------------------------------------------------
-- Inserts
-- -----------------------------------------------------

-- roles
INSERT INTO role (role_id, role_name) VALUES(DEFAULT, 'admin');
INSERT INTO role (role_id, role_name) VALUES(DEFAULT, 'customer');

-- users
SET @textAdmin = 'admin';
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "admin", "admin", "Tom Misch", (SELECT role_id FROM role WHERE role_name = @textAdmin));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "admin1", "admin1", "John Coltrane", (SELECT role_id FROM role WHERE role_name = @textAdmin));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "admin2", "admin2", "Mal Waldron", (SELECT role_id FROM role WHERE role_name = @textAdmin));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "admin3", "admin3", "Bill Evans", (SELECT role_id FROM role WHERE role_name = @textAdmin));
SET @textCustomer = 'customer';
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer", "customer", "Ryuichi Sakamoto", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer1", "customer1", "David Bowie", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer2", "customer1", "David Gilmour", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer3", "customer1", "Eric Clapton", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer4", "customer4", "Hugh Laurie", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer5", "customer5", "Jack White", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer6", "customer6", "Jimi Hendrix", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer7", "customer7", "Pat Metheny", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer8", "customer8", "Lubomyr Melnyk", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer9", "customer9", "Mac DeMarco", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer10", "customer10", "Neil Young", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer11", "customer11", "Nicolas Jaar", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer12", "customer12", "Nils Frahm", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer13", "customer13", "Paul McCartney", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer14", "customer14", "Syd Barrett", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer15", "customer15", "Thom Yorke", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer16", "customer16", "Charles Mingus", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer17", "customer17", "Dannie Richmond", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer18", "customer18", "Keith Jarrett", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer19", "customer19", "Louis Armstrong", (SELECT role_id FROM role WHERE role_name = @textCustomer));
INSERT INTO user (user_id, login, password, user_name, role_id) VALUES(DEFAULT, "customer20", "customer20", "Miles Davis", (SELECT role_id FROM role WHERE role_name = @textCustomer));

-- categories
INSERT INTO category (category_id, category_name) VALUES(1, "Sport");
INSERT INTO category (category_id, category_name) VALUES(2, "Science");
INSERT INTO category (category_id, category_name) VALUES(3, "News");
INSERT INTO category (category_id, category_name) VALUES(4, "Travels");
INSERT INTO category (category_id, category_name) VALUES(5, "History");

-- magazines
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Coach #3, March 2021", "Stanislav Lindover", 1, 5);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Football. Ukraine #34, May 2021", "Chelsea smashes Real Madrid and surprises: why is it so easy?", 1, 9);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Sportreview #13, April 2020", "The chase continues", 1, 8);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "The Goal #14, April 2020", "Team and fans: love continues!", 1, 5);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Big sport #10, October 2018", "Khabib VS Conor: Showdown in Vegas", 1, 6);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Science and technology #5, May 2021", "Space nuclear tug", 2, 6);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Quantum #2, February 2021", "Tasks M2638-M2641", 2, 10);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Young polymath #1, January 2021", "Desert architecture", 2, 11);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "National Geographic #6, June 2021", "Gladiators fight club of ancient Rome", 2, 8);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Total Escape #50, 2021", "50 places of our planet", 4, 8);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "L'Officiel Voyage #29, December 2019", "Style India", 4, 7);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "GQ. Travel #10, October 2019", "Saint Petersburg is a city with a masculine character", 4, 10);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Historian #4, April 2021", "Gagarin''s flight", 5, 10);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Your Privy Counselor #8, November 2019", "Love talk show of Catherine II", 5, 4);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Living history. Appendix #4, May 2017", "Occupation: how they survived under German rule", 5, 8);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Time #4, April 2021", "Time is a weekly USA based news magazine also has European edition", 3, 8);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Reason #4, April 2021", "Magazine published 11 times annually by the Reason Foundation", 3, 25);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "The week #4, April 2021", "Magazine features include national and international affairs, business, lifestyles, society, politics, and business.", 3, 15);
-- magazines-new
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Sports Science Bulletin #1, March 2021", "The journal 'Sports Science Bulletin' publishes scientific reports on the theory and methodology of elite sports", 1, 17);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Moto #8, January 2020", "This magazine is not just for those who ride on two wheels or want to learn more about the world of motorcycles.", 1, 12);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "World of spearfishing #2, November 2020", "Hunting without a gun, or On the benefits of politeness, magic hemp and pacifism. Far East trophies. Latvian pike perch, spring and large", 1, 8);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Tarpon #2 January 2019", "Islands, countries and cities open from the pages of the magazine.", 1, 24);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Football. Hockey 52, Apri 2020", "The magazine for active fans and all those who are interested in football.", 1, 18);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Boxing. Almanac 2020", "Illustrated edition about boxing and kickboxing. ", 1, 16);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Science and life #01 , December 2021", "Science and technology news, articles on history, biology, physics, medicine, art history", 2, 7.5);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Gangut. #115 May 2020", "The history of shipbuilding is represented by an article about timber harvesting and their transportation", 2, 13);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Logos #2 December 2021", "Alexander Markov. Contagiousness as a problem of social epistemology", 2, 4.6);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Aviation history #37, October 2020", "I-16 fighters in the 'Winter War' 1939-1940", 2, 20);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "First-hand science. #4 May 2020", "A powerful modern method for the study of complex chemicals", 2, 7.8);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "And why? #11 , October 2019", "A magazine for boys, girls and their parents about science, technology, nature", 2, 10);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Young technician #05 May 2019", "Scientific and technical, popular science magazine for teenagers", 2, 1.5);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "Aerospace Review #6, June 2020", "coverage of all aspects of activities in the development, production and operation of aviation and space technology", 2, 7.2);
INSERT INTO magazine (magazine_id, magazine_name, description, category_id, price) VALUES(DEFAULT, "The world #4, April 2020", "scientific and technical journal of metrological profile", 2, 4);



-- status
INSERT INTO status (status_id, status_name) VALUES(DEFAULT, "new");
INSERT INTO status (status_id, status_name) VALUES(DEFAULT, "paid");
INSERT INTO status (status_id, status_name) VALUES(DEFAULT, "closed");
