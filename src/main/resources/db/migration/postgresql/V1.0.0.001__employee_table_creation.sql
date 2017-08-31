create table JobFamily (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40),
    description VARCHAR(500)
);

create table JobLevel (
    id SERIAL PRIMARY KEY,
    jobFamilyId int,
    name VARCHAR(2),
    description VARCHAR(500),
    expertise VARCHAR(20),
    FOREIGN KEY (jobFamilyId) REFERENCES JobFamily(id)
);

create table ApplicationRole (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) UNIQUE,
    description VARCHAR(500)
);

create table Employee (
    id SERIAL PRIMARY KEY,
    jobLevelId int,
    applicationRoleId int,
    email VARCHAR(50),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    gftIdentifier VARCHAR(4) UNIQUE,
    FOREIGN KEY (jobLevelId) REFERENCES JobLevel(id) ON UPDATE CASCADE,
    FOREIGN KEY (applicationRoleId) REFERENCES ApplicationRole(id)
);

create table Relationship (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE,
    description VARCHAR(500)
);

create table EmployeeRelationship (
    id SERIAL PRIMARY KEY,
    sourceEmployeeId int,
    targetEmployeeId int,
    relationshipId int,
    startDate TIMESTAMP WITH TIME ZONE,
    endDate TIMESTAMP WITH TIME ZONE,
    FOREIGN KEY (sourceEmployeeId) REFERENCES Employee(id),
    FOREIGN KEY (targetEmployeeId) REFERENCES Employee(id),
    FOREIGN KEY (relationshipId) REFERENCES Relationship(id)
);
