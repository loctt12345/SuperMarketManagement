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

<!-- **1. Website for admin and manager**

**2. Android mobile application for customer**
<div align="center">
  <img src="https://raw.githubusercontent.com/fptu-team-404-not-found/co_tam_houseworker_mobile/main/document/imgs/customer_mobile_login.png" alt="Customer Mobile Login" width="25%"></img> &nbsp;&nbsp; <img src="https://raw.githubusercontent.com/fptu-team-404-not-found/co_tam_houseworker_mobile/main/document/imgs/customer_mobile_home.png" alt="Customer Mobile Home" width="25%"></img> &nbsp;&nbsp; <img src="https://raw.githubusercontent.com/fptu-team-404-not-found/co_tam_houseworker_mobile/main/document/imgs/customer_mobile_order_history.png" alt="Customer Mobile Order History" width="25%"></img> &nbsp;&nbsp; <img src="https://raw.githubusercontent.com/fptu-team-404-not-found/co_tam_houseworker_mobile/main/document/imgs/customer_mobile_order_rating.png" alt="Customer Mobile Order Rating" width="25%"></img>
</div>

**3. Android mobile application for houseworker**
<div align="center">
  <img src="https://raw.githubusercontent.com/fptu-team-404-not-found/co_tam_houseworker_mobile/main/document/imgs/houseworker_mobile_login.png" alt="Houseworker Mobile Login" width="25%"></img> &nbsp;&nbsp; <img src="https://raw.githubusercontent.com/fptu-team-404-not-found/co_tam_houseworker_mobile/main/document/imgs/houseworker_mobile_home.png" alt="Houseworker Mobile Home" width="25%"></img> &nbsp;&nbsp; <img src="https://raw.githubusercontent.com/fptu-team-404-not-found/co_tam_houseworker_mobile/main/document/imgs/houseworker_mobile_order_receiving.png" alt="Houseworker Mobile Order Receiving" width="25%"></img> 
</div> -->
  
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
:point_right: [Check out here for more details]()

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
[Database Script]()

## Contributors
**1. Mentors:**
- Lecturer - Mentor: Truong Long

**2. Members:**
- [Tran Thien Loc](https://github.com/loctt12345) - SE160275 - Leader - **Frontend Developer**
- [Lo Hung Khanh](https://github.com/khanh-lof) - SE160242 - **Backend Developer**
- [Phan Nhat Tan](https://github.com/tanpnse2k2) - SE 160117 - **Backend Developer**
- [Huynh Nhat Thien Hoang](https://github.com/hoangdapoet) - SE160248 - **Frontend Developer**
- [Nguyen Chanh Nhat Minh](https://github.com/minhncn2310) - SE160473 - **Frontend Developer**