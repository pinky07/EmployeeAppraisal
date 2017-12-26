-- Create Tables

CREATE TABLE JobFamily (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40),
    description VARCHAR(500)
);

CREATE TABLE JobLevel (
    id SERIAL PRIMARY KEY,
    jobFamilyId INT,
    name VARCHAR(2),
    description VARCHAR(500),
    expertise VARCHAR(20),
    FOREIGN KEY (jobFamilyId) REFERENCES JobFamily(id)
);

CREATE TABLE ApplicationRole (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) UNIQUE,
    description VARCHAR(500)
);

CREATE TABLE Employee (
    id SERIAL PRIMARY KEY,
    jobLevelId INT,
    applicationRoleId INT,
    email VARCHAR(50),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    gftIdentifier VARCHAR(4) UNIQUE,
    FOREIGN KEY (jobLevelId) REFERENCES JobLevel(id) ON UPDATE CASCADE,
    FOREIGN KEY (applicationRoleId) REFERENCES ApplicationRole(id)
);

CREATE TABLE Relationship (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE,
    description VARCHAR(500)
);

CREATE TABLE EmployeeRelationship (
    id SERIAL PRIMARY KEY,
    sourceEmployeeId INT,
    targetEmployeeId INT,
    relationshipId INT,
    startDate TIMESTAMP,
    endDate TIMESTAMP,
    comments VARCHAR(50),
    FOREIGN KEY (sourceEmployeeId) REFERENCES Employee(id),
    FOREIGN KEY (targetEmployeeId) REFERENCES Employee(id),
    FOREIGN KEY (relationshipId) REFERENCES Relationship(id)
);
