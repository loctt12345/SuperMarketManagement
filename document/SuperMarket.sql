
/*drop database SuperMarket

create database SuperMarket*/
use SuperMarket

create table [Customer] (
	UserID varchar(100),
	username varchar(50) not null unique,
	[password] varchar(100),
	Full_Name nvarchar(255),
	Phone varchar(20),
	Email varchar(100) unique,
	[Address] nvarchar(255),
	auth_provider varchar(20),
	reset_password_token varchar(45),
	verification_code varchar(45),
	[Status] bit not null,
	PRIMARY KEY (UserID)
)
create table [Role](
	RoleID varchar(100),
	Role_Name nvarchar(255),
	PRIMARY KEY (RoleID)
)


create table Employee (
	EmployeeID varchar(100),
	RoleID varchar(100),
	username varchar(50),
	[password] varchar(100),
	Full_Name nvarchar(255),
	Phone varchar(20),
	Email varchar(255) unique,
	[Address] nvarchar(255),
	Salary [float],
	[Status] BIT not null,
	PRIMARY KEY (EmployeeID),
	FOREIGN KEY (RoleID) REFERENCES ROLE(RoleID)
)


create table Order_Status(
	StatusID int,
	Status_Name nvarchar(255),
	PRIMARY KEY (StatusID)
)

create table [Primary_Order](
	OrderID varchar(100),
	UserID varchar(100),
	StatusID int,
	[Time] DateTime,
	Shipping_Address nvarchar(500),
	total [float],
	Phone varchar(20),
	Email varchar(100),
	PRIMARY KEY (OrderID),
	FOREIGN KEY (UserID) REFERENCES [Customer](UserID),
	FOREIGN KEY (StatusID) REFERENCES Order_Status(StatusID)
)

create table Employee_Status(
	OrderID varchar(100),
	EmployeeID varchar(100),
	[Status] BIT,
	FOREIGN KEY (OrderID) REFERENCES [Primary_Order](OrderID ),
	FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID),
	PRIMARY KEY (OrderID, EmployeeID)
)

create table Product_Details(
	ProductID varchar(100),
	[Name] nvarchar(255),
	[Description] nvarchar(max),
	Category nvarchar(255),
	[Image] nvarchar(1000),
	Sell_Price [float],
	[Status] BIT,
 	PRIMARY KEY (ProductID)
	
)


create table Product_Manager(
	ProductID varchar(100),
	Total_Imported_Number int,
	Total_Sold_Number int,
	FOREIGN KEY (ProductID) REFERENCES Product_Details(ProductID),
	PRIMARY KEY (ProductID)
)


create table Order_Details(
	OrderDetailsID varchar(100) PRIMARY KEY,
	OrderID varchar(100),
	ProductID varchar(100),
	Sold_Number int,
	Amount float,
	FOREIGN KEY (OrderID) REFERENCES [Primary_Order](OrderID),
	FOREIGN KEY (ProductID) REFERENCES Product_Details(ProductID),
)


create table [Notification](
	NotificationID varchar(100),
	[Description] nvarchar(1000),
	Time_Start DateTime,
	Time_End DateTime,
	ID_Person varchar(30)
)

create table Import_Bill(
	ImportID varchar(100),
	Date_Imported DateTime,
	Bussiness_Partner nvarchar(1000),
	PRIMARY KEY(ImportID)
)


create table Product_Import_Details(
	ProductID varchar(100),
	ImportID varchar(100),
	Date_Exp DateTime,
	Imported_Number int,
	Imported_Price [float]
	FOREIGN KEY (ProductID) REFERENCES Product_Details(ProductID),
	FOREIGN KEY (ImportID) REFERENCES Import_Bill(ImportID),
	PRIMARY KEY(ProductID)
)
GO
CREATE SEQUENCE [dbo].[hibernate_sequence] 
 AS [bigint]
 START WITH 10000000
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
CREATE TABLE Product_Recommendation (
	CommentID varchar(100),
	Comment nvarchar(max),
	ProductID varchar(100),
	[Status] bit,
)