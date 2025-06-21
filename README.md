# Loan Management System
This project is a smart contract implementation using Hyperledger Fabric to manage loan records.

## Project Structure
LoanManagementSystem/
├── build.gradle
├── README.md
├── .gitignore
└── src/
     └── main/
          └── java/
               └── LoanManagementSystem/
                      ├── LoanRecord.java
                          └── LoanManagementContract.java

## Features

- Register new loan records (loan ID, borrower info, amount, interest, dates)
- Query loan details by loan ID
- Update loan amount and interest rate
- Enforces ownership and record integrity
- Runs as Hyperledger Fabric chaincode (v2.x)

---

## Prerequisites

- Java 11 or higher
- Hyperledger Fabric 2.2+
- Gradle (or use the Gradle wrapper)
- Docker & Fabric test network (for deploying)

---

## How to Run

1. **Clone the Repository**

   ```bash
   git clone https://github.com/Taniya027/loan-management-system.git
   cd loan-management-system
Build the Project

./gradlew build
Deploy Chaincode (Fabric Network Required)

Package and deploy the compiled chaincode to your Fabric peer.

NOTE: This project is written for the Java chaincode environment in Fabric.

Test Using Fabric CLI or REST Gateway

You can invoke chaincode functions like:

addNewLoan(...)

queryLoanById(...)

updateLoan(...)

Testing
JUnit 5 is used for unit testing. To run tests:

./gradlew test

Smart Contract Methods
Function Name	Description
addNewLoan()	Adds a new loan record to the ledger
queryLoanById()	Fetches a loan record using its loan ID
updateLoan()	Updates loan amount and interest rate

License
This project is licensed under the MIT License.

Author
Taniya027
