# SuperMarket System With Scanner
[![Build and deploy Spring Boot app to Azure Web App - supermarketscanner](https://github.com/khanh-lof/SuperMarketManagement/actions/workflows/register_supermarketscanner.yml/badge.svg)](https://github.com/khanh-lof/SuperMarketManagement/actions/workflows/register_supermarketscanner.yml)

:wave: Welcome to our Software Development Project (SWP391) :wave:

## Table of Contents
- [Description](#description)
- [Preview Screenshot](#preview-screenshot)
- [Technology](#technology)
- [Functional requirements](#functional-requirements)
- [Useful Resources](#useful-resources)
- [Contributors](#contributors)

## Description
- Project Supermarket Scanner is a sales management system for supermarkets, using barcode scanner as the mainstream.
- The system allows product management, order management and employee management.
- This project started from 07-01-2023 to 10-04-2023

## Preview Screenshot

**1. Website for admininistrator**
<div align="center">
  <img src="https://github.com/loctt12345/SuperMarketManagement/blob/main/document/image/admin_management.png" alt="Homepage products management" width="25%">
  </img> &nbsp;&nbsp; <img src="https://github.com/loctt12345/SuperMarketManagement/blob/main/document/image/admin_dashboard_profit.png" alt="Dashboard profit" width="25%"></img> &nbsp;&nbsp;
  <img src="https://github.com/loctt12345/SuperMarketManagement/blob/main/document/image/admin_order_management.png" alt="Order Management" width="25%"></img> &nbsp;&nbsp;
</div>
**2. Mobile view for customer**
<div align="center">
  <img src="https://github.com/loctt12345/SuperMarketManagement/blob/main/document/image/login.png" alt="Login Page" width="25%">
  </img> &nbsp;&nbsp; <img src="https://github.com/loctt12345/SuperMarketManagement/blob/main/document/image/customer_home.png" alt="Home" width="25%"></img> &nbsp;&nbsp;
  <img src="https://github.com/loctt12345/SuperMarketManagement/blob/main/document/image/product_details.png" alt="Product Detail" width="25%"></img> &nbsp;&nbsp;
  <img src="https://github.com/loctt12345/SuperMarketManagement/blob/main/document/image/cart.png" alt="Cart" width="25%"></img> &nbsp;&nbsp;
</div>
**3. Mobile view for employees**
<div align="center">
  <img src="https://github.com/loctt12345/SuperMarketManagement/blob/main/document/image/shipper_home_scanner.png" alt="shipper home scanner" width="25%"></img> &nbsp;&nbsp; <img src="https://github.com/loctt12345/SuperMarketManagement/blob/main/document/image/storage_home_scanner.png" alt="storage man home scanner" width="25%"></img> &nbsp;&nbsp;
</div>
  
## Technology
**1. Frontend**
  - HTML, CSS, JavaScript
  - Bootstrap

**2. Backend**
  - Spring Boot

**3. Database**
  - Microsoft SQL Server - a relational model database server produced by Microsoft

**4. Other Technologies**
- RESTful API
- Google Oauth2 for Authentication Login with Google
- Using Paypal Sandbox for Payment Services
- Spring Security
- Mail Sender Service using SMTP

**5. Tool**
  - Netbean
  - Visual Studio Code 
  - Postman
  - Draw.io for ERD Diagram
  - Microsoft SQL Server Management Studio 19

## Functional requirements
:point_right: [Check out here for more details](https://github.com/loctt12345/SuperMarketManagement/blob/main/document/2023.Spring_Supermarket%20Management.docx)

**1. Customer:**
- Manage cart:
    + Add a new product by scanning the barcode.
	+ Update existing product.
    + Delete product.
- Check out.
- Update profile.
- Payment.
- View package process.

**2. Administrator:**
- Manage product:
    + Add a new product by scanning the barcode.
    + Update existing product.
    + Delete product.
- Manage employee.
- Dashboard.

**3. Storage Employee:**
- Receive orders from customers and prepare for that orders.
- Scan QR code for confirmation of the package.

**4. Delivery man:**
- Receive the package from the storage employee.
- Scan QR code for confirmation of the package has been delivered.


## Useful Resources
[Database Script](https://github.com/loctt12345/SuperMarketManagement/blob/main/document/SuperMarket.sql)

## Contributors
**1. Mentors:**
- Lecturer - Mentor: Truong Long

**2. Members:**
- [Tran Thien Loc](https://github.com/loctt12345) - SE160275 - Leader - **Frontend Developer**
- [Lo Hung Khanh](https://github.com/khanh-lof) - SE160242 - **Backend Developer**
- [Phan Nhat Tan](https://github.com/tanpnse2k2) - SE 160117 - **Backend Developer**
- [Huynh Nhat Thien Hoang](https://github.com/hoangdapoet) - SE160248 - **Frontend Developer**
- [Nguyen Chanh Nhat Minh](https://github.com/minhncn2310) - SE160473 - **Frontend Developer**
