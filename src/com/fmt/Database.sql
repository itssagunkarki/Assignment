use skarki;

drop table if exists LeaseEquipment;


drop table if exists PurchaseEquipment;
drop table if exists Equipment;
drop table if exists ServiceUsage;

drop table if exists Service;
drop table if exists PurchaseProduct;
drop table if exists InvoiceItem;
drop table if exists Invoice;
drop table if exists Store;
drop table if exists PersonEmail;
drop table if exists Person;
drop table if exists Address;
drop table if exists State;
drop table if exists Country;
drop table if exists Email;


drop table if exists Product;
drop table if exists Item;



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
    foreign key (stateId) references State(stateId)
);


create table Email (
    emailId int not null primary key auto_increment,
    email varchar(255) not null
);

create table Person (
    personId int not null primary key auto_increment,
    personCode varchar(10) not null unique,
    lastName varchar(255) not null,
    firstName varchar(255) not null,
    addressId int not null,
    foreign key (addressId) references Address(addressId),
    index personCode_idx (personCode)
);

create table PersonEmail (
    personEmailId int not null primary key auto_increment,
    personId int not null,
    emailId int not null,
    foreign key (personId) references Person(personId),
    foreign key (emailId) references Email(emailId)
);

create table Store(
    storeId int not null primary key auto_increment,
    storeCode varchar(10) not null,
    managerId int not null,
    addressId int not null,
    foreign key (addressId) references Address(addressId),
    foreign key (managerId) references Person(personId),
    index storeCode_idx (storeCode)
);

create table Item (
    itemId int not null primary key auto_increment,
    itemCode varchar(10) not null unique,
    itemType char not null,
    itemName varchar(255) not null,
    index itemCode_idx (itemCode)
);

create table Equipment (
    equipmentId int not null primary key auto_increment,
    itemId int not null,
    itemType char not null,
    foreign key (itemId) references Item(itemId)
);

create table Product (
    productId int not null primary key auto_increment,
    itemId int not null,
    itemType char not null,
    unit varchar(255) not null,
    unitPrice double not null,
    foreign key (itemId) references Item(itemId)
);


create table Service (
    serviceId int not null primary key auto_increment,
    itemId int not null,
    itemType char not null,
    ServiceName varchar(255) not null,
    HourlyRate double not null,
    foreign key (itemId) references Item(itemId)
);


create table Invoice (
    invoiceId int not null primary key auto_increment,
    invoiceCode varchar(10) not null unique,
    storeId int not null,
    customerId int not null,
    salesPersonId int not null,
    invoiceDate varchar(32) not null,
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
    foreign key (invoiceId) references Invoice(invoiceId),
    foreign key (itemId) references Item(itemId)
);

create table LeaseEquipment (
    equipmentLeaseId int not null primary key auto_increment,
    invoiceItemId int not null,
    leasePriceMonthly int not null,
    leaseStartDate varchar(32) not null,
    leaseEndDate varchar(32) not null,
    foreign key (invoiceItemId) references InvoiceItem(invoiceItemId)   
);

create table PurchaseEquipment (
    equipmentPurchaseId int not null primary key auto_increment,
    invoiceItemId int not null,
    purchasePrice double not null,
    foreign key (invoiceItemId) references InvoiceItem(invoiceItemId)    
);

create table PurchaseProduct(
    purchaseProductId int not null primary key auto_increment,
    invoiceItemId int not null,
    quantity int not null,
    foreign key (invoiceItemId) references InvoiceItem(invoiceItemId)    
);

create table ServiceUsage (
    serviceUsageId int not null primary key auto_increment,
    invoiceItemId int not null,
    numHours double not null,
	foreign key (invoiceItemId) references InvoiceItem(invoiceItemId)

    
);

INSERT INTO Country (countryName, countryCode)
VALUES 
    ('United States', 'US');

INSERT INTO State (stateName, stateCode, countryId)
VALUES 
    ('California', 'CA', 1),
    ('New York', 'NY', 1),
    ('Texas', 'TX', 1);

INSERT INTO Address (street, city, stateId, zipCode)
VALUES 
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

INSERT INTO Email (email)
VALUES 
    ('johnsmith@gmail.com'),
    ('j.smith@yahoo.com'),
    ('janej@gmail.com'),
    ('mdoe@hotmail.com'),
    ('mark.doe@gmail.com'),
    ('emilywong@gmail.com'),
    ('davidchen@yahoo.com'),
    ('katesingh@gmail.com'),
    ('kate.singh@yahoo.com'),
    ('michaelmiller@gmail.com'),
    ('mike.miller@yahoo.com'),
    ('susanw@gmail.com'),
    ('chrisbrown@gmail.com'),
    ('juliadavis@yahoo.com');


INSERT INTO Person (personCode, lastName, firstName, addressId)
VALUES 
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


insert into PersonEmail (personId, emailId)
values
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 4),
    (3, 5),
    (4, 6),
    (5, 7),
    (6, 8),
    (6, 9),
    (7, 10),
    (7, 11),
    (8, 12),
    (9, 13),
    (10, 14);


INSERT INTO Store (storeCode, managerId, addressId)
VALUES
('S63J9N', 2, 2),
('S78P4D', 3, 3),
('S91F2B', 4, 4),
('S25R7M', 5, 5);


-- Inserting data into the Item table
INSERT INTO Item (itemCode, itemType, itemName) VALUES
('1d4d89', 'E', 'Tractor'),
('0ec6e9', 'E', 'Harvester'),
('740515', 'E', 'Baler'),
('3506f6', 'E', 'Backhoe'),
('649f88', 'E', 'Truck'),
('342foa3', 'P', 'Haybale'),
('3n3k4l2', 'P', 'Corn seed'),
('n3453js', 'P', 'Fertilizer'),
('l4k32j4', 'P', 'Wire fencing'),
('23n4kl2', 'P', 'Top soil'),
('2334b23', 'S', 'Delivery'),
('n43k2l3', 'S', 'Plowing'),
('432kn2l', 'S', 'Tractor Driving Lessons'),
('8432941', 'S', 'Cattle Vaccination');

insert into Equipment (itemId, itemType) values 
(1, 'E'),
(2, 'E'),
(3, 'E'),
(4, 'E'),
(5, 'E');


insert into Product (itemId, itemType, unit, unitPrice) values 
(6, 'P', 'bale', 500),
(7, 'P', 'bag', 50),
(8, 'P', 'liter', 10.25),
(9, 'P', 'ft', 8),
(10, 'P', 'ton', 850);


insert into Service (itemId, itemType, ServiceName, HourlyRate) values 
(11, 'S', 'Delivery', 100),
(12, 'S', 'Plowing', 1000),
(13, 'S', 'Tractor Driving Lessons', 25),
(14, 'S', 'Cattle Vaccination', 225.50);


insert into Invoice (invoiceCode, storeId, customerId, salesPersonId, invoiceDate) values 
('INV001', 4, 1, 5, '2023-03-10'),
('INV002', 3, 1, 5, '2023-03-11'),
('INV003', 1, 2, 6, '2023-03-12'),
('INV004', 4, 2, 7, '2023-03-13'),
('INV005', 3, 2, 5, '2023-03-14'),
('INV006', 1, 2, 6, '2023-03-15');


insert into InvoiceItem (invoiceId, itemid, itemType, itemPrice, itemTaxes) values 
(1, 13, "S", 87.500, 3.020),
(1, 2, "E",85000.000, 0.000),
(2, 12, "S", 2000.000, 69.000),
(2, 8,"P", 51.250, 3.660),
(2, 9,"P", 4400.000, 314.600),
(3, 9,"P", 1264.000, 90.380),
(3, 1,"E", 42700.000, 500.000),
(4, 8,"P", 150.000, 10.730),
(4, 12,"S", 10000.000, 345.000),
(4, 14,"S", 1127.500, 38.900);


insert into LeaseEquipment (invoiceItemId, leasePriceMonthly, leaseStartDate, leaseEndDate) values 
(7, 3500,'2022-01-01', '2022-01-31');


insert into PurchaseEquipment (invoiceItemId, purchasePrice) values 
(2,  85000);


insert into PurchaseProduct (invoiceItemId,  quantity) values 
(4, 5),
(5, 550),
(6, 158),
(8, 3);


insert into ServiceUsage (invoiceItemId, numHours) values 
(1, 3.5),
(3, 2),
(9, 10),
(10, 5);