drop table if exists InvoiceItem;
drop table if exists Invoice;
drop table if exists Item;
drop table if exists Store;
drop table if exists Email;
drop table if exists Person;
drop table if exists Address;
drop table if exists State;
drop table if exists Country;



create table Country (
    countryId int not null primary key auto_increment,
    countryName varchar(255) not null,
    countryCode varchar(4) not null
);

create table State (
    stateId int not null primary key auto_increment,
    stateName varchar(255) not null,
    stateCode varchar(4) not null,
    countryId int not null,
    foreign key (countryId) references Country(countryId)
);

create table Address(
    addressId int not null primary key auto_increment,
    street varchar(255) not null,
    city varchar(255) not null,
    stateId int not null,
    zipCode varchar(16) not null,
    deleted boolean not null default false,
    foreign key (stateId) references State(stateId)
);


create table Person (
    personId int not null primary key auto_increment,
    personCode varchar(10) not null unique,
    lastName varchar(255) not null,
    firstName varchar(255) not null,
    addressId int not null,
    deleted boolean not null default false,
    foreign key (addressId) references Address(addressId),
    index personCode_idx (personCode) -- index created for faster lookup
);

create table Email (
    emailId int not null primary key auto_increment,
    email varchar(255) not null,
    personId int not null,
    deleted boolean not null default false,
    foreign key (personId) references Person(personId)
);


create table Store(
    storeId int not null primary key auto_increment,
    storeCode varchar(10) not null unique,
    managerId int not null,
    addressId int not null,
    deleted boolean not null default false,
    foreign key (addressId) references Address(addressId),
    foreign key (managerId) references Person(personId),
    index storeCode_idx (storeCode)
);

create table Item (
    itemId int not null primary key auto_increment,
    itemCode varchar(10) not null unique,
    itemType char not null,
    itemName varchar(255) not null,
    unit varchar(255) null,
    unitPrice double null,
    ServiceName varchar(255) null,
    HourlyRate double null,
    deleted boolean not null default false,
    index itemCode_idx (itemCode) -- index created for faster lookup
);


create table Invoice (
    invoiceId int not null primary key auto_increment,
    invoiceCode varchar(10) not null unique,
    storeId int not null,
    customerId int not null,
    salesPersonId int not null,
    invoiceDate varchar(32) not null,
    deleted boolean not null default false,
    foreign key (storeId) references Store(storeId),
    foreign key (customerId) references Person(personId),
    foreign key (salesPersonId) references Person(personId),
    index invoiceCode_idx (invoiceCode)
);

create table InvoiceItem (
    invoiceItemId int not null primary key auto_increment,
    invoiceId int not null,
    itemId int not null,
    itemType char not null,
    itemPrice double not null,
    itemTaxes double not null,
    leasePriceMonthly int  null,
    leaseStartDate varchar(32) null,
    leaseEndDate varchar(32) null,
    purchasePrice double null,
    quantity int null,
    numHours double null,
    deleted boolean not null default false,
    foreign key (invoiceId) references Invoice(invoiceId),
    foreign key (itemId) references Item(itemId)
);


--- Inserting data 

insert into Country (countryName, countryCode)
values 
    ('United States', 'US');

insert into State (stateName, stateCode, countryId)
values 
    ('California', 'CA', 1),
    ('New York', 'NY', 1),
    ('Texas', 'TX', 1);

insert into Address (street, city, stateId, zipCode)
values 
    ('123 Main St', 'Anytown', 1, '12345'),
    ('456 1st Ave', 'Smallville', 2, '67890'),
    ('789 Elm St', 'Bigcity', 3, '54321'),
    ('12 Oak Ln', 'Anytown', 1, '12345'),
    ('345 Maple Dr', 'Smallville', 2, '67890'),
    ('678 Pine St', 'Bigcity', 3, '54321'),
    ('901 Main St', 'Anytown', 1, '12345'),
    ('234 Oak St', 'Smallville', 2, '67890'),
    ('567 Pine Ln', 'Bigcity', 3, '54321'),
    ('890 Maple Ave', 'Anytown', 1, '12345');


insert into Person (personCode, lastName, firstName, addressId)
values 
    ('F77P8B', 'Smith', 'John', 1),
    ('6T49E2', 'Johnson', 'Jane', 2),
    ('C84A1D', 'Doe', 'Mark', 3),
    ('H22B9F', 'Wong', 'Emily', 4),
    ('K65N8S', 'Chen', 'David', 5),
    ('R98M4T', 'Singh', 'Kate', 6),
    ('L77F2A', 'Miller', 'Michael', 7),
    ('G91N7E', 'Williams', 'Susan', 8),
    ('A73R2P', 'Brown', 'Chris', 9),
    ('B49H5S', 'Davis', 'Julia', 10);


insert into Email (email, personId)
values 
    ('johnsmith@gmail.com', 1),
    ('j.smith@yahoo.com', 1),
    ('janej@gmail.com', 2),
    ('mdoe@hotmail.com', 3),
    ('mark.doe@gmail.com', 3),
    ('emilywong@gmail.com', 4),
    ('davidchen@yahoo.com', 5),
    ('katesingh@gmail.com', 6),
    ('kate.singh@yahoo.com', 6),
    ('michaelmiller@gmail.com', 7),
    ('mike.miller@yahoo.com', 7),
    ('susanw@gmail.com', 8),
    ('chrisbrown@gmail.com', 9),
    ('juliadavis@yahoo.com', 10);



insert into Store (storeCode, managerId, addressId)
values
('S63J9N', 2, 2),
('S78P4D', 3, 3),
('S91F2B', 4, 4),
('S25R7M', 5, 5);


-- -- Inserting data into the Item table
insert into Item (itemCode, itemType, itemName) values
('1d4d89', 'E', 'Tractor'),
('0ec6e9', 'E', 'Harvester'),
('740515', 'E', 'Baler'),
('3506f6', 'E', 'Backhoe'),
('649f88', 'E', 'Truck');

insert into Item (itemCode, itemType, itemName, unit, unitPrice) values
('342foa3', 'P', 'Haybale', 'bale', 500),
('3n3k4l2', 'P', 'Corn seed', 'bag', 50),
('n3453js', 'P', 'Fertilizer', 'liter', 10.25),
('l4k32j4', 'P', 'Wire fencing', 'ft', 8),
('23n4kl2', 'P', 'Top soil', 'ton', 850);

insert into Item (itemCode, itemType, itemName, HourlyRate) values
('2334b23', 'S', 'Delivery', 100),
('n43k2l3', 'S', 'Plowing', 1000),
('432kn2l', 'S', 'Tractor Driving Lessons', 25),
('8432941', 'S', 'Cattle Vaccination', 225.50);



insert into Invoice (invoiceCode, storeId, customerId, salesPersonId, invoiceDate) values 
('INV001', 4, 1, 5, '2023-03-10'),
('INV002', 3, 1, 5, '2023-03-11'),
('INV003', 1, 2, 6, '2023-03-12'),
('INV004', 4, 2, 7, '2023-03-13'),
('INV005', 3, 2, 5, '2023-03-14'),
('INV006', 1, 2, 6, '2023-03-15');


insert into InvoiceItem (invoiceId, itemid, itemType, itemPrice, itemTaxes, leasePriceMonthly, leaseStartDate, leaseEndDate) values
(3, 1,"E", 42700.000, 500.000, 3500,'2022-01-01', '2022-01-31');

insert into InvoiceItem (invoiceId, itemid, itemType, itemPrice, itemTaxes, purchasePrice) values 
(1, 2, "E",85000.000, 0.000, 85000);


insert into InvoiceItem (invoiceId, itemid, itemType, itemPrice, itemTaxes, quantity) values 
(2, 8,"P", 51.250, 3.660, 5),
(2, 9,"P", 4400.000, 314.600, 550),
(3, 9,"P", 1264.000, 90.380, 158),
(4, 8,"P", 150.000, 10.730, 3);


insert into InvoiceItem (invoiceId, itemid, itemType, itemPrice, itemTaxes, numHours) values
(1, 13, "S", 87.500, 3.020, 3.5),
(2, 12, "S", 2000.000, 69.000, 2),
(4, 12,"S", 10000.000, 345.000, 10),
(4, 14,"S", 1127.500, 38.900, 5);
