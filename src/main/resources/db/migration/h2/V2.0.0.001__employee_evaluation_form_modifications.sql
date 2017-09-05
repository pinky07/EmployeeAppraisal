CREATE TABLE EmployeeEvaluationForm (
  id SERIAL PRIMARY KEY,
  appraisalXEvaluationFormId int,
  employeeId int,
  filledByEmployeeId int,
  mentorId int,
  createDate TIMESTAMP,
  submitDate TIMESTAMP,
  evaluationStatus VARCHAR(40),
  FOREIGN KEY (appraisalXEvaluationFormId) REFERENCES AppraisalXEvaluationForm(id)
);

INSERT INTO EmployeeEvaluationForm (id, appraisalXEvaluationFormId, employeeId, filledByEmployeeId, mentorId, createDate, submitDate, evaluationStatus)
VALUES

  -- Rubén Jiménez (id 2) - Self Evaluation
  (1, 1, 2, 2, 6, '2017-01-01 00:00:00-06', null, 'PENDING'),

  -- Rubén Jiménez (id 2) - Reference Evaluation
  (2, 2, 2, 3, 6, '2017-01-01 00:00:00-06', null, 'PENDING'),
  (3, 2, 2, 4, 6, '2017-01-01 00:00:00-06', null, 'PENDING'),
  (4, 2, 2, 5, 6, '2017-01-01 00:00:00-06', null, 'PENDING'),

  -- Rubén Jiménez (id 2) - Mentor Evaluation
  (5, 3, 2, 6, 6, '2017-01-01 00:00:00-06', null, 'PENDING'),

  -- Manuel Yépez (id 3) - Self Evaluation
  (6, 1, 3, 3, 6, '2017-01-01 00:00:00-06', null, 'PENDING'),

  -- Manuel Yépez (id 3) - Reference Evaluation
  (7, 2, 3, 2, 6, '2017-01-01 00:00:00-06', null, 'PENDING'),
  (8, 2, 3, 4, 6, '2017-01-01 00:00:00-06', null, 'PENDING'),
  (9, 2, 3, 5, 6, '2017-01-01 00:00:00-06', null, 'PENDING'),

  -- Manuel Yépez (id 3) - Mentor Evaluation
  (10, 3, 3, 6, 6, '2017-01-01 00:00:00-06', null, 'PENDING');

DROP TABLE AppraisalXEvaluationFormXEmployeeRelationship;

DROP TABLE EvaluationFormXSectionXQuestionXAnswer;

CREATE TABLE EmployeeEvaluationFormAnswer (
  id SERIAL PRIMARY KEY,
  evaluationFormXSectionXQuestionId int,
  employeeEvaluationFormId int,
  scoreValueId int,
  comment varchar(1000),
  FOREIGN KEY (evaluationFormXSectionXQuestionId) REFERENCES EvaluationFormXSectionXQuestion(id),
  FOREIGN KEY (employeeEvaluationFormId) REFERENCES EmployeeEvaluationForm(id),
  FOREIGN KEY (scoreValueId) REFERENCES ScoreValue(id)
);