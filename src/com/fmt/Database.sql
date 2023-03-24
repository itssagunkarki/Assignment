use sagun;

Drop Table If Exists Invoice;
Drop Table If Exists ItemService;
Drop Table If Exists ItemProduct;
Drop Table If Exists ItemEquipmentLease;
Drop Table If Exists ItemEquipmentPurchase;
Drop Table If Exists ItemEquipment;
Drop Table If Exists Item;
Drop Table If Exists Store;
Drop Table If Exists PersonEmail;
Drop Table If Exists Person;
Drop Table If Exists Email;
Drop Table If Exists LocalAddress;


create table LocalAddress(
    addressId int not null primary key auto_increment,
    street varchar(255) not null,
    city varchar(255) not null,
    stateName varchar(255) not null,
    zipCode varchar(16) not null,
    country varchar(16) not null
);


create table PersonEmail (
    PersonEmailId int not null primary key auto_increment,
    email varchar(255) not null
);

create table Person (
    personId int not null primary key auto_increment,
    personCode varchar(10) not null,
    lastName varchar(255) not null,
    firstName varchar(255) not null,
    addressId int not null,
    PersonEmail int not null,
    foreign key (addressId) references LocalAddress(addressId),
    foreign key (PersonEmail) references PersonEmail(PersonEmailId)
);


create table Store(
    storeId int not null primary key auto_increment,
    storeCode varchar(10) not null,
    manager int not null,
    addressId int not null,
    foreign key (addressId) references LocalAddress(addressId),
    foreign key (manager) references Person(personId),
    index storeCode_idx (storeCode)
);

create table Item (
    itemId int not null primary key auto_increment,
    itemCode varchar(10) not null,
    itemType char not null,
    itemName varchar(255) not null,
    index itemType_idx (itemType),
    index itemCode_idx (itemCode)
);

create table ItemEquipment (
    ItemequipmentId int not null primary key auto_increment,
    itemType char not null,
    ItemequipmentCode varchar(10) not null,
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
    itemCode varchar(10) not null,
    ServiceCode varchar(10) not null,
    ServiceName varchar(255) not null,
    HourlyRate double not null,
    foreign key (itemCode) references Item(itemCode)
);





create table Invoice (
    invoiceId int not null primary key auto_increment,
    invoiceCode varchar(10) not null,
    storeId int not null,
    customerId int not null,
    salesPersonId int not null,
    invoiceItem int not null,
    foreign key (storeId) references Store(storeId),
    foreign key (customerId) references Person(personId),
    foreign key (salesPersonId) references Person(personId),
    foreign key (invoiceItem) references Item(itemId),
    constraint uniquePair unique (storeId, customerId)
);
