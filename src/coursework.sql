create table Person (
person_id number generated always as identity,
first_name varchar2(50),
last_name varchar2(50),
email varchar2(50),
phone_number varchar2(20)
);

-- number (1, 0) -> Simulates a boolean value, since type doesnt exist yet in Oracle SQL Developer

create type role_privilages_t as  object (
has_full_access number(1,0),
can_post number(1,0),
can_authorize_sale number(1,0)
);

create table Roles (
role_id number generated always as identity,
role_type varchar2(20),
role_privilages role_privilages_t
);

create table Person_Roles (
person_roles_id number generated always as identity,
role_id number,
person_id number
)

alter table Person_Roles
add constraint fk_person_id
foreign key(person_id)
references Person(person_id);

create type location_t as object (
latitude varchar2(10),
longtitude varchar2(10),
city varchar2(20)
);

create table Property (
property_id number generated always as identity,
price float,
square_meters number,
location location_t
);

ALTER TABLE Property
ADD (
    property_type VARCHAR2(20),
    owner_id      NUMBER
);

ALTER TABLE Property
ADD CONSTRAINT chk_property_type
CHECK (property_type IN ('garage','house','apartment'));

ALTER TABLE Property
ADD CONSTRAINT fk_property_owner
FOREIGN KEY (owner_id) REFERENCES person(person_id);

create table Garage (
property_id number
);

alter table Garage 
add constraint fk_garage_property
foreign key (property_id)
references Property(property_id);

create table House(
property_id number,
number_of_floors number,
garden_size_m2 number,
number_of_bathrooms number,
number_of_rooms number
);

alter table House
add constraint fk_house_property
foreign key (property_id)
references Property(property_id);


create table Apartment(
property_id number,
floor number,
number_of_bathrooms number,
number_of_rooms number
);

create table Preferences (
preference_id number generated always as identity,
client_id number,
preference_type varchar2(100)
);

alter table Preferences
add constraint fk_client_preferences
foreign key (client_id)
references Client(person_id);


alter table Apartment
add constraint fk_apartment_property
foreign key (property_id)
references Property(property_id);

create table Client (
person_id number,
budget number,
area_interested_in varchar2(100)
);

alter table Client
add constraint fk_client_id
foreign key (person_id)
references Person(person_id);

create table Agent (
person_id number,
salary float,
hire_date date
);

alter table Agent 
add constraint fk_agent_id
foreign key (person_id)
references Person(person_id);

create table Property_Owner (
person_id number,
property_id number
);

alter table Property_Owner
add constraint fk_owner_id
foreign key (person_id)
references Person(person_id);

alter table Property_Owner
add constraint fk_owner_property
foreign key (property_id)
references Proeprty(property_id);

create table Successful_Deals (
deal_id number generated always as identity,
property_id number,
final_price float,
agent_id number,
client_id number
);

alter table Successful_Deals
add constraint fk_client_id
foreign key (client_id)
references Client(person_id);

create table Listing (
listing_id number generated always as identity,
type_of_listing varchar2(100),
description varchar2(255),
notes varchar2(100)
);

create table Property_Images (
image_id number generated always as identity,
listing_id number,
image_data blob,
image_url varchar2(500)
);

alter table Property_Images
add constraint fk_listing_images
foreign key (listing_id)
references Listing(listing_id);


