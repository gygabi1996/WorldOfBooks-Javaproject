# Create the database

CREATE DATABASE worldofbooks;

USE worldofbooks;

# Create user

CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';

GRANT ALL PRIVILEGES ON worldofbooks.* TO 'admin'@'localhost';

# Create the tables

CREATE TABLE listings (
    listing_id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    inventory_item_location_id VARCHAR(36),
    listing_price DECIMAL UNSIGNED NOT NULL,
    currency VARCHAR(3) NOT NULL,
    quantity INT UNSIGNED NOT NULL,
    listing_status_id INT UNSIGNED NOT NULL,
    marketplace_id INT UNSIGNED NOT NULL,
    upload_time VARCHAR(10) NOT NULL,
    owner_email_address VARCHAR(255) NOT NULL
)  ENGINE=INNODB;

CREATE TABLE locations (
    location_id VARCHAR(36) PRIMARY KEY,
    manager_name VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    address_primary VARCHAR(255) NOT NULL,
    address_secondary VARCHAR(255),
    country VARCHAR(255) NOT NULL,
    town VARCHAR(255) NOT NULL,
    postal_code VARCHAR(255)
)  ENGINE=INNODB;

CREATE TABLE listing_statuses (
    listing_status_id INT UNSIGNED PRIMARY KEY,
    status_name VARCHAR(255) NOT NULL
)  ENGINE=INNODB;

CREATE TABLE marketplaces (
    marketplace_id INT UNSIGNED PRIMARY KEY,
    marketplace_name VARCHAR(255) NOT NULL
)  ENGINE=INNODB;

# Add foreign key constraints

ALTER TABLE listings
ADD CONSTRAINT fk_listing_location
FOREIGN KEY (inventory_item_location_id) REFERENCES locations(location_id);

ALTER TABLE listings
ADD CONSTRAINT fk_listing_listing_status
FOREIGN KEY (listing_status_id) REFERENCES listing_statuses(listing_status_id);

ALTER TABLE listings
ADD CONSTRAINT fk_listing_marketplace
FOREIGN KEY (marketplace_id) REFERENCES marketplaces(marketplace_id);