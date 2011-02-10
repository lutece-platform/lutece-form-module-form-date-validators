--
-- table structure for form_date_validators_comparator
--
CREATE TABLE form_date_validators_comparator
(
	id_comparator INT NOT NULL,
	name VARCHAR(20) NOT NULL,
	class VARCHAR(255) NOT NULL,
	PRIMARY KEY(id_comparator)
);

--
-- table structure for form_date_validators_operator
--
CREATE TABLE form_date_validators_operator
(
	id_operator INT NOT NULL,
	name VARCHAR(20) NOT NULL,
	class VARCHAR(255) NOT NULL,
	PRIMARY KEY(id_operator)
);

--
-- table structure for form_date_validators_unit
--
CREATE TABLE form_date_validators_unit
(
	id_unit INT NOT NULL,
	name VARCHAR(255) NOT NULL,
	class VARCHAR(255) NOT NULL,
	PRIMARY KEY(id_unit)
);

--
-- table structure for form_date_validators_rule
--
CREATE TABLE form_date_validators_rule
(
	id_rule INT NOT NULL,
	id_form INT NOT NULL,
	id_entry_1 INT NOT NULL,
	id_comparator INT NOT NULL,
	id_entry_2 INT DEFAULT NULL,
	date_reference DATE NULL,
	id_date_calculated INT DEFAULT NULL,
	PRIMARY KEY(id_rule)
);

--
-- table structure for form_date_validators_date_calculated
--
CREATE TABLE form_date_validators_date_calculated
(
	id_date_calculated INT NOT NULL,
	date_reference DATE NOT NULL,
	id_operator INT NOT NULL,
	number INT NOT NULL,
	id_unit INT NOT NULL,
	PRIMARY KEY(id_date_calculated)
);
