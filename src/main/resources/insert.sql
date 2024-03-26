-- Questo script inserisce nel database una serie di entry generate direttamente tramite il sistema e che hanno
-- lo scopo di simulare una possibile situazione reale di un sistema già avviato e in funzione.

-- Riempimento Tabella roles

INSERT INTO crazycarddb.roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO crazycarddb.roles (id, name) VALUES (2, 'ROLE_MERCHANT');
INSERT INTO crazycarddb.roles (id, name) VALUES (3, 'ROLE_CUSTOMER');


-- Riempimento Tabella stores

INSERT INTO crazycarddb.stores (id, name, locality, authorized) VALUES (1, 'Market SPA', 'Palermo', true);
INSERT INTO crazycarddb.stores (id, name, locality, authorized) VALUES (2, 'Spesa Facile SRL', 'Catania', true);
INSERT INTO crazycarddb.stores (id, name, locality, authorized) VALUES (9, 'Tutto Fresco SRLS', 'Trento', true);
INSERT INTO crazycarddb.stores (id, name, locality, authorized) VALUES (10, 'Spesa Difficile SNC', 'Messina', false);


-- Riempimento Tabella users

INSERT INTO crazycarddb.users (id, email, name, password, store_id) VALUES (1, 'domyg98@gmail.com', 'Domenico Giosue', '$2a$10$tj/o8wJ7bw8uVeFBnVT5EO5fA5RVE/CJubpKgSLO8bbPNZEK7zZj.', 9);
INSERT INTO crazycarddb.users (id, email, name, password, store_id) VALUES (32, 'giulio.verdi@gmail.com', 'Giulio Verdi', '$2a$10$VUkC0LcWeD3JyzBjkUi3QeEQMsiNeexqA4wr11d97b0IIeNnH6SzG', null);
INSERT INTO crazycarddb.users (id, email, name, password, store_id) VALUES (34, 'alfonso.grigi@gmail.com', 'Alfonso Grigi', '$2a$10$rm8x/OwT6p5KF983TpjWWeBe9Va20ClfslmkAsj0XQ2/YcDyqFEk2', 1);
INSERT INTO crazycarddb.users (id, email, name, password, store_id) VALUES (35, 'ferdinando.arancioni@gmail.com', 'Ferdinando Arancioni', '$2a$10$ZQImDCHZ67BRY.bt2oBMr.A0xmNznT7/rkW4XrXij3/AsP47HWtgi', null);
INSERT INTO crazycarddb.users (id, email, name, password, store_id) VALUES (36, 'simone.bianchi@gmail.com', 'Simone Bianchi', '$2a$10$3Yh1Psxz2GaE57vzMQuACOD2DgZLE0d1UlTO8j.XblI8S8mwJ.Mvm', 2);
INSERT INTO crazycarddb.users (id, email, name, password, store_id) VALUES (37, 'marco.rossi@gmail.com', 'Marco Rossi', '$2a$10$AB0/9SAXpckXwq02MdXv3u4jk2SU7cdOOvLoHpuJE4uIdLkiXkFry', null);
INSERT INTO crazycarddb.users (id, email, name, password, store_id) VALUES (38, 'federico.bianchi@gmail.com', 'Federico Bianchi', '$2a$10$a4E23eiLY3GeFvIbdHVMSOlmqJz5X1tRfEpl8dEVyLAMgLj4Re9MC', 10);


-- Riempimento Tabella users_roles

INSERT INTO crazycarddb.users_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO crazycarddb.users_roles (user_id, role_id) VALUES (1, 3);
INSERT INTO crazycarddb.users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO crazycarddb.users_roles (user_id, role_id) VALUES (32, 1);
INSERT INTO crazycarddb.users_roles (user_id, role_id) VALUES (34, 2);
INSERT INTO crazycarddb.users_roles (user_id, role_id) VALUES (35, 3);
INSERT INTO crazycarddb.users_roles (user_id, role_id) VALUES (37, 3);
INSERT INTO crazycarddb.users_roles (user_id, role_id) VALUES (38, 2);
INSERT INTO crazycarddb.users_roles (user_id, role_id) VALUES (36, 2);


-- Riempimento Tabella cards

INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (1, 4, '1234567891234567', '123456', true, 35);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (12, 255.01, '4731360632944144', '694755', false, 35);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (39, 100, '2253706934833458', '350378', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (46, 100, '8587446601860438', '312221', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (47, 100, '3740990745828830', '157465', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (48, 100, '0865741454881064', '780152', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (49, 100, '2760878522699467', '011455', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (50, 127.5, '0040438998237854', '360700', true, 37);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (51, 200, '1661542424230439', '102277', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (83, 100, '2232173374173732', '980255', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (90, 100, '7457360129346496', '931465', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (105, 414.72, '8111822888151092', '882644', true, 37);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (106, 500, '2087667807696534', '045164', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (107, 500, '1642687867141098', '588835', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (108, 500, '9393884007544798', '328219', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (109, 500, '5724039417050114', '848764', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (110, 1000, '2524854586261174', '971955', true, null);
INSERT INTO crazycarddb.cards (id, balance, number, pin, state, owner_id) VALUES (111, 1000, '1355492080282158', '695330', true, null);


-- Riempimento Tabella transactions

INSERT INTO crazycarddb.transactions (id, amount, date, card_id, store_id) VALUES (7, -17.5, '2023-07-11', 1, 1);
INSERT INTO crazycarddb.transactions (id, amount, date, card_id, store_id) VALUES (8, 0.5, '2023-07-11', 1, 1);
INSERT INTO crazycarddb.transactions (id, amount, date, card_id, store_id) VALUES (9, -32.67, '2023-07-11', 12, 1);
INSERT INTO crazycarddb.transactions (id, amount, date, card_id, store_id) VALUES (10, -87.85, '2023-07-11', 105, 9);
INSERT INTO crazycarddb.transactions (id, amount, date, card_id, store_id) VALUES (11, -12.43, '2023-07-11', 105, 9);
INSERT INTO crazycarddb.transactions (id, amount, date, card_id, store_id) VALUES (12, 15, '2023-07-11', 105, 9);
INSERT INTO crazycarddb.transactions (id, amount, date, card_id, store_id) VALUES (13, -72.5, '2023-07-11', 50, 9);
INSERT INTO crazycarddb.transactions (id, amount, date, card_id, store_id) VALUES (14, -79, '2023-07-11', 1, 9);
INSERT INTO crazycarddb.transactions (id, amount, date, card_id, store_id) VALUES (15, -12.32, '2023-07-11', 12, 9);





