DROP TABLE singleHouse;
DROP TABLE apartmentComplex;
DROP TABLE vacationRental;
DROP TABLE tenant;

CREATE TABLE tenant (
   tenantID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
   firstName VARCHAR(30) NOT NULL,
   lastName VARCHAR(30) NOT NULL,
   phoneNumber VARCHAR(30) NOT NULL,
   email VARCHAR(30) NOT NULL,
   PRIMARY KEY (tenantID)
);

CREATE TABLE singleHouse (
   houseID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
   propertyType CHAR(1) NOT NULL,
   numBeds INT NOT NULL,
   numBaths INT NOT NULL,
   garage BOOLEAN NOT NULL,
   frontYard BOOLEAN NOT NULL,
   backYard BOOLEAN NOT NULL,
   leaseLength INT NOT NULL,
   monthlyRent INT NOT NULL,
   maxTenants INT NOT NULL,
   tenantID INT,
   leaseStartDate DATE,
   leaseEndDate DATE,
   rentDueDate DATE,
   PRIMARY KEY (houseID),
   FOREIGN KEY (tenantID) REFERENCES tenant (tenantID)
);

CREATE TABLE apartmentComplex (
   apartmentID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
   propertyType CHAR(1) NOT NULL,
   unitNumber INT NOT NULL,
   numBeds INT NOT NULL,
   numBaths INT NOT NULL,
   commonArea BOOLEAN NOT NULL,
   utilityRoom BOOLEAN NOT NULL,
   fitnessCenter BOOLEAN NOT NULL,
   reservedParking BOOLEAN NOT NULL,
   leaseLength INT NOT NULL,
   monthlyRent INT NOT NULL,
   maxTenants INT NOT NULL,
   tenantID INT,
   leaseStartDate DATE,
   leaseEndDate DATE,
   rentDueDate DATE,
   PRIMARY KEY (apartmentID),
   FOREIGN KEY (tenantID) REFERENCES tenant (tenantID)
);

CREATE TABLE vacationRental (
   unitID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
   propertyType CHAR(1) NOT NULL,
   unitDescription VARCHAR(200) NOT NULL,
   leaseLength INT NOT NULL,
   leaseUnit VARCHAR(30) NOT NULL,
   dailyRent INT NOT NULL,
   weeklyRent INT NOT NULL,
   monthlyRent INT NOT NULL,
   maxTenants INT NOT NULL,
   tenantID INT,
   leaseStartDate DATE,
   leaseEndDate DATE,
   rentDueDate DATE,
   PRIMARY KEY (unitID),
   FOREIGN KEY (tenantID) REFERENCES tenant (tenantID)
);
