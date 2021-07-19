
-- Local dev database data

INSERT INTO `customer`
	(`id`, `name`, 	  `creation_instant`,     `last_update_instant`, `deleted`)
VALUES
	('1',		'Customer1',	'2021-01-01 00:00:00',  '2021-01-01 00:00:00',  0),
	('2',		'Customer2',	'2021-01-02 00:00:00',  '2021-01-02 00:00:00',  0),
	('3',		'Customer3',	'2021-01-03 00:00:00',  '2021-01-03 00:00:00',  0);

INSERT INTO `employee`
  (`id`,  `name`,       `surname`,      `creation_instant`,       `last_update_instant`,    `deleted`)
VALUES
  ('1',     'Employee1',   'Surname1',    '2021-01-01 00:00:00',    '2021-01-01 00:00:00',    0),
  ('2',     'Employee2',   'Surname2',    '2021-01-02 00:00:00',    '2021-01-02 00:00:00',    0),
  ('3',     'Employee3',   'Surname3',    '2021-01-03 00:00:00',    '2021-01-03 00:00:00',    0),
  ('4',     'Employee4',   'Surname4',    '2021-01-04 00:00:00',    '2021-01-04 00:00:00',    0),
  ('5',     'Employee5',   'Surname4',    '2021-01-05 00:00:00',    '2021-01-05 00:00:00',    0);

INSERT INTO `project`
  (`id`,  `name`,       `description`,    `customer_id`,  `creation_instant`,       `last_update_instant`,    `deleted`)
VALUES
  ('1',     'Project1',   'description1',   '1',           '2021-01-01 00:00:00',    '2021-01-01 00:00:00',    0);


INSERT INTO `project_team_member`
  (`project_id`,  `employee_id`,    `role_id`)
VALUES
  ('1',             '1',             1),
  ('1',             '2',             2),
  ('1',             '3',             3),
  ('1',             '4',             3);

INSERT INTO `project_version`
(`major_version` ,  `minor_version`,  `patch_version`,  `project_id`, `label`,  `note`, `status`, `release_date`,    `creation_instant`,       `last_update_instant`)
VALUES
  ('1',               '0',               '0',               '1',         NULL,     NULL,   1,       '2021-01-01',    '2021-01-01 00:00:00',     '2021-01-01 00:00:00'),
  ('1',               '0',               '1',               '1',         'label',  NULL,   1,       '2021-01-02',    '2021-01-02 00:00:00',     '2021-01-02 00:00:00'),
  ('1',               '1',               '0',               '1',         NULL,     'note', 1,       '2021-01-03',    '2021-01-03 00:00:00',     '2021-01-03 00:00:00'),
  ('1',               '1',               '1',               '1',         NULL,     NULL,   2,       '2021-01-04',    '2021-01-04 00:00:00',     '2021-01-04 00:00:00');

