
-- Database schema definition

-- CUSTOMER --
CREATE TABLE `customer` (
  `id` VARCHAR(100) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `version` INT NOT NULL,
  `creation_instant` DATETIME(3) NOT NULL,
  `last_update_instant` DATETIME(3) NOT NULL,
  `deleted` BOOLEAN NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- EMPLOYEE --
CREATE TABLE `employee` (
  `id` VARCHAR(100) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `surname` VARCHAR(255) NOT NULL,
  `creation_instant` DATETIME(3) NOT NULL,
  `last_update_instant` DATETIME(3) NOT NULL,
  `deleted` BOOLEAN NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- PROJECT --
CREATE TABLE `project` (
  `id` VARCHAR(100) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  `customer_id` VARCHAR(100) NOT NULL,
  `creation_instant` DATETIME(3) NOT NULL,
  `last_update_instant` DATETIME(3) NOT NULL,
  `deleted` BOOLEAN NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_project_customer_id`
      FOREIGN KEY (`customer_id`)
      REFERENCES `customer` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `project_customer_id_idx` ON `project` (`customer_id` ASC);

CREATE TABLE `project_team_member` (
  `project_id` VARCHAR(100) NOT NULL,
  `employee_id` VARCHAR(100) NOT NULL,
  `role_id` TINYINT NOT NULL,
  PRIMARY KEY (`project_id`, `employee_id`, `role_id`),
  CONSTRAINT `fk_project_team_project_id`
        FOREIGN KEY (`project_id`)
        REFERENCES `project` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_team_employee_id`
        FOREIGN KEY (`employee_id`)
        REFERENCES `employee` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `project_team_project_id_idx` ON `project_team_member` (`project_id` ASC);
CREATE INDEX `project_team_employee_id_idx` ON `project_team_member` (`employee_id` ASC);

CREATE TABLE `project_version` (
  `major_version` INT NOT NULL,
  `minor_version` INT NOT NULL,
  `patch_version` INT NOT NULL,
  `project_id` VARCHAR(100) NOT NULL,
  `label` VARCHAR(255) NULL,
  `note` TEXT NULL,
  `status` TINYINT NOT NULL,
  `release_date` DATE NOT NULL,
  `creation_instant` DATETIME(3) NOT NULL,
  `last_update_instant` DATETIME(3) NOT NULL,
  PRIMARY KEY (`project_id`, `major_version`, `minor_version`, `patch_version`),
  CONSTRAINT `fk_project_version_project_id`
      FOREIGN KEY (`project_id`)
      REFERENCES `project` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `project_version_project_id_idx` ON `project_version` (`project_id` ASC);
