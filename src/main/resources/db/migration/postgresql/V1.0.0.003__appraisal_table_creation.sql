CREATE TABLE Appraisal (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(500) NOT NULL,
    startDate TIMESTAMP WITH TIME ZONE NOT NULL,
    endDate TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE EvaluationForm (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL
);

CREATE TABLE EvaluationFormSection (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(500) NOT NULL
);

CREATE TABLE ScoreType (
    id SERIAL PRIMARY KEY,
    definition VARCHAR(40) NOT NULL
);

CREATE TABLE ScoreValue (
    id SERIAL PRIMARY KEY,
    scoreTypeId INT,
    value VARCHAR(40),
    description VARCHAR(500) NOT NULL,
    FOREIGN KEY (scoreTypeId) REFERENCES ScoreType(id)
);

CREATE TABLE EvaluationFormQuestion (
    id SERIAL PRIMARY KEY,
    scoreTypeId INT,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(500) NOT NULL,
    FOREIGN KEY (scoreTypeId) REFERENCES ScoreType(id)
);

CREATE TABLE AppraisalXEvaluationForm (
    id SERIAL PRIMARY KEY,
    appraisalId INT,
    evaluationFormId INT,
    FOREIGN KEY (appraisalId) REFERENCES Appraisal(id),
    FOREIGN KEY (evaluationFormId) REFERENCES EvaluationForm(id)
);

CREATE TABLE EvaluationFormXJobLevel (
    id SERIAL PRIMARY KEY,
    evaluationFormId INT,
    jobLevelId INT,
    FOREIGN KEY (evaluationFormId) REFERENCES EvaluationForm(id),
    FOREIGN KEY (jobLevelId) REFERENCES JobLevel(id)
);

CREATE TABLE AppraisalXEvaluationFormXEmployeeRelationship (
    id SERIAL PRIMARY KEY,
    appraisalXEvaluationFormId INT,
    employeeRelationshipId INT,
    evaluationStatus VARCHAR(40),
    FOREIGN KEY (appraisalXEvaluationFormId) REFERENCES AppraisalXEvaluationForm(id),
    FOREIGN KEY (employeeRelationshipId) REFERENCES EmployeeRelationship(id)
);

CREATE TABLE EvaluationFormXSectionXQuestion (
    id SERIAL PRIMARY KEY,
    evaluationFormId INT,
    evaluationFormSectionId INT,
    evaluationFormQuestionId INT,
    FOREIGN KEY (evaluationFormId) REFERENCES EvaluationForm(id),
    FOREIGN KEY (evaluationFormSectionId) REFERENCES EvaluationFormSection(id),
    FOREIGN KEY (evaluationFormQuestionId) REFERENCES EvaluationFormQuestion(id)
);

CREATE TABLE EvaluationFormXSectionXQuestionXAnswer (
    id SERIAL PRIMARY KEY,
    evaluationFormXSectionXQuestionId INT,
    appraisalXEvaluationFormXEmployeeRelationshipId INT,
    scoreValueId INT,
    comment varchar(1000),
    FOREIGN KEY (evaluationFormXSectionXQuestionId) REFERENCES EvaluationFormXSectionXQuestion(id),
    FOREIGN KEY (appraisalXEvaluationFormXEmployeeRelationshipId) REFERENCES AppraisalXEvaluationFormXEmployeeRelationship(id),
    FOREIGN KEY (scoreValueId) REFERENCES ScoreValue(id)
);
