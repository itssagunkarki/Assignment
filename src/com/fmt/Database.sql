use skarki;

drop table if exists InvoiceItem;
drop table if exists Invoice;
drop table if exists Store;
drop table if exists Person;
drop table if exists Address;
drop table if exists StateDetails;
drop table if exists Country;
drop table if exists Email;
drop table if exists ItemEquipmentLease;
drop table if exists ItemEquipmentPurchase;
drop table if exists ItemEquipment;
drop table if exists ItemService;
drop table if exists ItemProduct;
drop table if exists Item;



create table Country (
    countryId int not null primary key auto_increment,
    countryName varchar(255) not null,
    countryCode varchar(4) not null
);

create table StateDetails (
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
    foreign key (stateId) references StateDetails(stateId)
);


create table Email (
    EmailId int not null primary key auto_increment,
    email varchar(255) not null
);

create table Person (
    personId int not null primary key auto_increment,
    personCode varchar(10) not null,
    lastName varchar(255) not null,
    firstName varchar(255) not null,
    addressId int not null,
    Email int not null,
    foreign key (addressId) references Address(addressId),
    foreign key (Email) references Email(EmailId),
    index personCode_idx (personCode)
);


create table Store(
    storeId int not null primary key auto_increment,
    storeCode varchar(10) not null,
    manager int not null,
    addressId int not null,
    foreign key (addressId) references Address(addressId),
    foreign key (manager) references Person(personId),
    index storeCode_idx (storeCode)
);

create table Item (
    itemId int not null primary key auto_increment,
    itemCode varchar(10) not null unique,
    itemType char not null,
    itemName varchar(255) not null,
    index itemType_idx (itemType),
    index itemCode_idx (itemCode)
);

create table ItemEquipment (
    ItemequipmentId int not null primary key auto_increment,
    itemType char not null,
    ItemequipmentName varchar(255) not null,
    purchaseOrLease char not null,
    foreign key (itemType) references Item(itemType)
);

create table ItemEquipmentPurchase (
    ItemequipmentPurchaseId int not null primary key auto_increment,
    ItemequipmentId int not null,
    purchasePrice int not null,
    foreign key (ItemequipmentId) references ItemEquipment(ItemequipmentId)
);

create table ItemEquipmentLease (
    ItemequipmentLeaseId int not null primary key auto_increment,
    ItemequipmentId int not null,
    leasePriceMonthly int not null,
    leaseStartDate varchar(32) not null,
    leaseEndDate varchar(32) not null,
    foreign key (ItemequipmentId) references ItemEquipment(ItemequipmentId)
);


create table ItemProduct (
    ItemProductId int not null primary key auto_increment,
    itemType char not null,
    unit varchar(255) not null,
    unitPrice double not null,
    quantity int not null,
    purchaseOrLease char not null,
    foreign key (itemType) references Item(itemType)
);

create table ItemService (
    ItemServiceId int not null primary key auto_increment,
    itemType char not null,
    ServiceName varchar(255) not null,
    HourlyRate double not null,
    foreign key (itemType) references Item(itemType)
);


create table Invoice (
    invoiceId int not null primary key auto_increment,
    invoiceCode varchar(10) not null,
    storeId int not null,
    customerId int not null,
    salesPersonId int not null,
    invoiceDate varchar(32) not null,
    foreign key (storeId) references Store(storeId),
    foreign key (customerId) references Person(personId),
    foreign key (salesPersonId) references Person(personId),
    constraint uniquePair unique (storeId, customerId)
);

-- insert country
insert into Country (countryName, countryCode) values ('USA', 'US');


-- insert States
insert into StateDetails (stateName, stateCode, countryId)
values
('Alabama', 'AL', 1),
('Alaska', 'AK', 1),
('Arizona', 'AZ', 1),
('Arkansas', 'AR', 1),
('California', 'CA', 1),
('Colorado', 'CO', 1),
('Connecticut', 'CT', 1),
('Delaware', 'DE', 1),
('Florida', 'FL', 1),
('Georgia', 'GA', 1),
('Hawaii', 'HI', 1),
('Idaho', 'ID', 1),
('Illinois', 'IL', 1),
('Indiana', 'IN', 1),
('Iowa', 'IA', 1),
('Kansas', 'KS', 1),
('Kentucky', 'KY', 1),
('Louisiana', 'LA', 1),
('Maine', 'ME', 1),
('Maryland', 'MD', 1),
('Massachusetts', 'MA', 1),
('Michigan', 'MI', 1),
('Minnesota', 'MN', 1),
('Mississippi', 'MS', 1),
('Missouri', 'MO', 1),
('Montana', 'MT', 1),
('Nebraska', 'NE', 1),
('Nevada', 'NV', 1),
('New Hampshire', 'NH', 1),
('New Jersey', 'NJ', 1),
('New Mexico', 'NM', 1),
('New York', 'NY', 1),
('North Carolina', 'NC', 1),
('North Dakota', 'ND', 1),
('Ohio', 'OH', 1),
('Oklahoma', 'OK', 1),
('Oregon', 'OR', 1),
('Pennsylvania', 'PA', 1),
('Rhode Island', 'RI', 1),
('South Carolina', 'SC', 1),
('South Dakota', 'SD', 1),
('Tennessee', 'TN', 1),
('Texas', 'TX', 1),
('Utah', 'UT', 1),
('Vermont', 'VT', 1),
('Virginia', 'VA', 1),
('Washington', 'WA', 1),
('West Virginia', 'WV', 1),
('Wisconsin', 'WI', 1),
('Wyoming', 'WY', 1);


-- insert invoices 
-- INSERT INTO Invoice (invoiceCode, storeId, customerId, salesPersonId, invoiceDate)
-- VALUES
--     ('INV001', 'S25R7M', 'F77P8B', 'K65N8S', '2023-03-10'),
--     ('INV002', 'S91F2B', 'F77P8B', 'K65N8S', '2023-03-11'),
--     ('INV003', 'S63J9N', '6T49E2', 'R98M4T', '2023-03-12'),
--     ('INV004', 'S25R7M', '6T49E2', 'L77F2A', '2023-03-13'),
--     ('INV005', 'S91F2B', '6T49E2', 'K65N8S', '2023-03-14'),
--     ('INV006', 'S63J9N', '6T49E2', 'R98M4T', '2023-03-15');
