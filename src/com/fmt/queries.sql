-- 1. A query to retrieve the main attributes of each person (their code, and last/first name)
    select personCode, firstName, lastName from Person;


-- 2. A query to retrieve the major fields for every person including their address (but excluding emails)
    SELECT p.personCode, concat(p.lastName, ", ", p.firstName) as Name, a.street, a.city, a.zipCode, s.stateCode, c.countryCode
	FROM Person AS p
	LEFT JOIN Address AS a ON p.addressId = a.addressId
	LEFT JOIN State AS s ON a.stateId = s.stateId
	LEFT JOIN Country AS c ON s.countryId = c.countryId;
	

-- 3. A query to get the email addresses of a specific person
    select email as emails from Email as e
    left join PersonEmail as pe on e.emailId = pe.emailId
    left join Person as p on p.personId = pe.personId
    where p.personCode="F77P8B";

-- 4. A query to change the email address of a specific email record
    UPDATE Email e
    SET e.email = 'j.smith@gmail.com'
    WHERE e.emailId = (select emailId from Email where email="j.smith@yahoo.com");


-- 5. A query (or series of queries) to remove a specific person record
	delete from Person where 
    personId = (select distinct p.personId from Person p
    left join PersonEmail as pe on p.personId = pe.personId
    left join Email as e on pe.emailId = e.emailId
    where p.personCode = "F77P8B");
    
    delete from Invoice where customerId = (
    select distinct p.personId from Person p
    left join PersonEmail as pe on p.personId = pe.personId
    left join Email as e on pe.emailId = e.emailId
    where p.personCode = "F77P8B");
    
	select distinct p.personId from Person p
    left join PersonEmail as pe on p.personId = pe.personId
    left join Email as e on pe.emailId = e.emailId
    where p.personCode = "F77P8B";
    
    select * from InvoiceItem
    left join PurchaseEquipment on PurchaseEquipment.InvoiceItemId = InvoiceItem.InvoiceItemId;
    
    

-- 6. A query to get all the items on a specific invoice record
	select itemCode, itemName from Invoice
    left join InvoiceItem on Invoice.invoiceId = InvoiceItem.invoiceId
    left join Item on Item.itemId = InvoiceItem.itemId
    where Invoice.invoiceId = 2;

-- 7. A query to get all the items purchased by a specific person
	select itemCode, InvoiceItem.itemType, itemName from Invoice
    left join InvoiceItem on Invoice.invoiceId = InvoiceItem.invoiceId
    left join Item on InvoiceItem.itemId = Item.ItemId
    left join Person on Person.personId = Invoice.customerId
    where Person.personCode="6T49E2";

-- 8. A query to find the total number of sales made at each store
	select storeCode, count(invoiceCode) as numSales from Store 
    left join Invoice on Store.storeId = Invoice.storeId
    group by storeCode;

-- 9. A query to find the total number of sales made by each employee
	select salesPersonId, concat(Person.lastName, ", ", Person.firstName) as salesPersonName, count(invoiceCode) as numSales_by_salesPerson 
    from Store 
    left join Invoice on Invoice.storeId = Store.storeId
    left join Person on Invoice.salesPersonId = Person.personId 
    where salesPersonId is not null
    group by salesPersonId;

-- 10. A query to find the subtotal charge of all products in each invoice (hint: you can take an aggregate of a mathematical expression). Do not include taxes.
	select invoiceCode, sum(itemPrice) as sub_total_charge from Invoice
    left join InvoiceItem on Invoice.invoiceId = InvoiceItem.invoiceId
    where itemPrice is not null
    group by invoiceCode;
    SELECT count(itemId) 
	FROM Invoice 
	LEFT JOIN InvoiceItem ON Invoice.invoiceId = InvoiceItem.invoiceId
	WHERE Invoice.invoiceId = 2
	GROUP BY itemId;


-- 11. A query to detect invalid data in invoice as follows. In a single invoice, a particular product should only appear once since any number of units can be consolidated to a single record. For example, an invoice should not have two records for fertilizer; one for 10 liters and another for 25 liters. Instead, it should have a single record for 35 liters. Write a query to find any invoice that includes multiple instances of the same product. If your database design prevents such a situation, you should still write this query (but of course would never expect any results).

    select itemId, count(itemId) as numItem from Invoice 
    left join InvoiceItem on Invoice.invoiceId = InvoiceItem.invoiceId
    where Invoice.invoiceId = 1
    group by itemId
    having count(itemId) != 1;
    
-- 12. Write a query to detect a potential instance of fraud where an employee makes a sale to themselves (the same person is the sales person as well as the customer).
	select customerId, salesPersonId, managerId from Invoice
    left join Store on Invoice.invoiceId = Store.storeId
    where managerId = customerId or salesPersonId=customerId;

