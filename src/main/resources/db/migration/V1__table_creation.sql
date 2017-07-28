create table JobFamily (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40),
    description VARCHAR(500)
);

create table JobLevel (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    jobFamilyId int,
    name VARCHAR(2),
    description VARCHAR(500),
    expertise VARCHAR(20),
    FOREIGN KEY (jobFamilyId) REFERENCES JobFamily(id)
);

create table ApplicationRole (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) UNIQUE,
    description VARCHAR(500)
);

create table Employee(
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    jobLevelId int,
    applicationRoleId int,
    email VARCHAR(40),
    firstName VARCHAR(20),
    lastName VARCHAR(50),
    gftIdentifier VARCHAR(4) UNIQUE,
    FOREIGN KEY (jobLevelId) REFERENCES JobLevel(id) ON UPDATE CASCADE,
    FOREIGN KEY (applicationRoleId) REFERENCES ApplicationRole(id)
);

create table Relationship (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) UNIQUE,
    description VARCHAR(500)
);

create table EmployeeRelationship (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sourceEmployeeId int,
    targetEmployeeId int,
    relationshipId int,
    startDate TIMESTAMP,
    endDate TIMESTAMP,
    FOREIGN KEY (sourceEmployeeId) REFERENCES Employee(id),
    FOREIGN KEY (targetEmployeeId) REFERENCES Employee(id),
    FOREIGN KEY (relationshipId) REFERENCES Relationship(id)
);
