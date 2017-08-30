create table Appraisal (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(500) NOT NULL,
    startDate TIMESTAMP NOT NULL,
    endDate TIMESTAMP NOT NULL
);

create table EvaluationForm (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL
);

create table EvaluationFormSection (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(500) NOT NULL
);

create table ScoreType (
    id SERIAL PRIMARY KEY,
    definition VARCHAR(40) NOT NULL
);

create table ScoreValue (
    id SERIAL PRIMARY KEY,
    scoreTypeId int,
    value VARCHAR(40),
    description VARCHAR(500) NOT NULL,
    FOREIGN KEY (scoreTypeId) REFERENCES ScoreType(id)
);

create table EvaluationFormQuestion (
    id SERIAL PRIMARY KEY,
    scoreTypeId int,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(500) NOT NULL,
    FOREIGN KEY (scoreTypeId) REFERENCES ScoreType(id)
);

create table AppraisalXEvaluationForm (
    id SERIAL PRIMARY KEY,
    appraisalId int,
    evaluationFormId int,
    FOREIGN KEY (appraisalId) REFERENCES Appraisal(id),
    FOREIGN KEY (evaluationFormId) REFERENCES EvaluationForm(id)
);

create table EvaluationFormXJobLevel (
    id SERIAL PRIMARY KEY,
    evaluationFormId int,
    jobLevelId int,
    FOREIGN KEY (evaluationFormId) REFERENCES EvaluationForm(id),
    FOREIGN KEY (jobLevelId) REFERENCES JobLevel(id)
);

create table AppraisalXEvaluationFormXEmployeeRelationship (
    id SERIAL PRIMARY KEY,
    appraisalXEvaluationFormId int,
    employeeRelationshipId int,
    evaluationStatus VARCHAR(40),
    FOREIGN KEY (appraisalXEvaluationFormId) REFERENCES AppraisalXEvaluationForm(id),
    FOREIGN KEY (employeeRelationshipId) REFERENCES EmployeeRelationship(id)
);

create table EvaluationFormXSectionXQuestion (
    id SERIAL PRIMARY KEY,
    evaluationFormId int,
    evaluationFormSectionId int,
    evaluationFormQuestionId int,
    FOREIGN KEY (evaluationFormId) REFERENCES EvaluationForm(id),
    FOREIGN KEY (evaluationFormSectionId) REFERENCES EvaluationFormSection(id),
    FOREIGN KEY (evaluationFormQuestionId) REFERENCES EvaluationFormQuestion(id)
);

create table EvaluationFormXSectionXQuestionXAnswer (
    id SERIAL PRIMARY KEY,
    evaluationFormXSectionXQuestionId int,
    appraisalXEvaluationFormXEmployeeRelationshipId int,
    scoreValueId int,
    comment varchar(1000),
    FOREIGN KEY (evaluationFormXSectionXQuestionId) REFERENCES EvaluationFormXSectionXQuestion(id),
    FOREIGN KEY (appraisalXEvaluationFormXEmployeeRelationshipId) REFERENCES AppraisalXEvaluationFormXEmployeeRelationship(id),
    FOREIGN KEY (scoreValueId) REFERENCES ScoreValue(id)
);
