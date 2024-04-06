-- Create schema if not exists
CREATE DATABASE IF NOT EXISTS ${schemaName};

-- Set the search path to the new schema
--SET search_path TO ${schemaName};
USE ${schemaName};
-- Create tables
CREATE TABLE IF NOT EXISTS ${schemaName}.asset (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    sl_no VARCHAR(255),
    ref_no VARCHAR(255),
    issued_date BIGINT,
    more_details VARCHAR(255),
    status BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ${schemaName}.asset_type (
    id BIGINT NOT NULL AUTO_INCREMENT,
    type VARCHAR(255),
    status BOOLEAN,
    PRIMARY KEY (id)
);
