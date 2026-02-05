INSERT INTO Person (first_name, last_name, email, phone_number)
VALUES ('John','Doe','john.doe@email.com','1234567890');

INSERT INTO Person (first_name, last_name, email, phone_number)
VALUES ('Jane','Smith','jane.smith@email.com','0987654321');

-- Roles
INSERT INTO Roles (role_type, role_privilages)
VALUES ('Admin', role_privilages_t(1,1,1));

INSERT INTO Roles (role_type, role_privilages)
VALUES ('Agent', role_privilages_t(0,1,0));

-- Person_Roles
INSERT INTO Person_Roles (role_id, person_id)
VALUES (1,1);

INSERT INTO Person_Roles (role_id, person_id)
VALUES (2,2);

-- Properties
INSERT INTO Property (price, square_meters, location, property_type, owner_id)
VALUES (100000, 50, location_t('40.7128','-74.0060','New York'), 'apartment', 1);

INSERT INTO Property (price, square_meters, location, property_type, owner_id)
VALUES (250000, 120, location_t('34.0522','-118.2437','Los Angeles'), 'house', 2);

-- House
INSERT INTO House (property_id, number_of_floors, garden_size_m2, number_of_bathrooms, number_of_rooms)
VALUES (2, 2, 50, 2, 5);

-- Apartment
INSERT INTO Apartment (property_id, floor, number_of_bathrooms, number_of_rooms)
VALUES (1, 5, 1, 3);

-- Client
INSERT INTO Client (person_id, budget, area_interested_in)
VALUES (1, 150000, 'Manhattan');

-- Agent
INSERT INTO Agent (person_id, salary, hire_date)
VALUES (2, 3000, SYSDATE);

-- Property_Owner
INSERT INTO Property_Owner (person_id, property_id)
VALUES (1, 1);

INSERT INTO Property_Owner (person_id, property_id)
VALUES (2, 2);

-- Listing
INSERT INTO Listing (type_of_listing, description, notes)
VALUES ('Sale','Apartment for sale','Good location');

-- Property Images
INSERT INTO Property_Images (listing_id, image_url)
VALUES (1,'https://via.placeholder.com/150');

-- Preferences
INSERT INTO Preferences (client_id, preference_type)
VALUES (1,'Apartment with 2+ rooms');
