
-- Rename tables
ALTER TABLE Relationship RENAME TO RelationshipType;
ALTER TABLE EvaluationFormXJobLevel RENAME TO EvaluationFormTemplateXJobLevel;
ALTER TABLE AppraisalXEvaluationFormXEmployeeRelationship RENAME TO EmployeeEvaluationForm;
ALTER TABLE EvaluationFormXSectionXQuestionXAnswer RENAME TO EmployeeEvaluationFormAnswer;
ALTER TABLE AppraisalXEvaluationForm RENAME TO AppraisalXEvaluationFormTemplate;
ALTER TABLE EvaluationForm RENAME TO EvaluationFormTemplate;
ALTER TABLE EvaluationFormSection RENAME TO Section;
ALTER TABLE EvaluationFormQuestion RENAME TO Question;
ALTER TABLE EvaluationFormXSectionXQuestion RENAME TO EvaluationFormTemplateXSectionXQuestion;

-- Rename columns
ALTER TABLE EmployeeRelationship RENAME COLUMN relationshipId TO relationshipTypeId;
ALTER TABLE EvaluationFormTemplateXJobLevel RENAME COLUMN evaluationFormId TO evaluationFormTemplateId;
ALTER TABLE EmployeeEvaluationForm RENAME COLUMN appraisalXEvaluationFormId TO appraisalXEvaluationFormTemplateId;
ALTER TABLE EmployeeEvaluationFormAnswer RENAME COLUMN appraisalXEvaluationFormXEmployeeRelationshipId TO employeeEvaluationFormId;
ALTER TABLE EmployeeEvaluationFormAnswer RENAME COLUMN evaluationFormXSectionXQuestionId TO evaluationFormTemplateXSectionXQuestion;
ALTER TABLE AppraisalXEvaluationFormTemplate RENAME COLUMN evaluationFormId TO evaluationFormTemplateId;
ALTER TABLE EvaluationFormTemplateXSectionXQuestion RENAME COLUMN evaluationFormId TO evaluationFormTemplateId;
ALTER TABLE EvaluationFormTemplateXSectionXQuestion RENAME COLUMN evaluationFormSectionId TO sectionId;
ALTER TABLE EvaluationFormTemplateXSectionXQuestion RENAME COLUMN evaluationFormQuestionId TO questionId;

-- Add columns

ALTER TABLE EmployeeEvaluationForm ADD COLUMN employeeId INTEGER;
ALTER TABLE EmployeeEvaluationForm ADD COLUMN filledByEmployeeId INTEGER;
ALTER TABLE EmployeeEvaluationForm ADD COLUMN mentorId INTEGER;
ALTER TABLE EmployeeEvaluationForm ADD COLUMN createDate TIMESTAMP WITH TIME ZONE;
ALTER TABLE EmployeeEvaluationForm ADD COLUMN submitDate TIMESTAMP WITH TIME ZONE;
ALTER TABLE EmployeeEvaluationForm ADD COLUMN  comments VARCHAR(50);
ALTER TABLE Section ADD COLUMN scoreTypeId INTEGER;
ALTER TABLE Section ADD COLUMN position INTEGER;
ALTER TABLE Question ADD COLUMN position INTEGER;

-- Add foreign key constraints
ALTER TABLE EmployeeEvaluationForm ADD CONSTRAINT EmployeeEvaluationForm_employeeId_fkey FOREIGN KEY (employeeId) REFERENCES Employee(id);
ALTER TABLE EmployeeEvaluationForm ADD CONSTRAINT EmployeeEvaluationForm_filledByEmployeeId_fkey FOREIGN KEY (filledByEmployeeId) REFERENCES Employee(id);
ALTER TABLE EmployeeEvaluationForm ADD CONSTRAINT EmployeeEvaluationForm_mentorId_fkey FOREIGN KEY (mentorId) REFERENCES Employee(id);
ALTER TABLE Section ADD CONSTRAINT Section_scoreTypeId_fkey FOREIGN KEY (scoreTypeId) REFERENCES ScoreType(id);

-- Drop columns
ALTER TABLE EmployeeEvaluationForm DROP COLUMN employeeRelationshipId RESTRICT;
ALTER TABLE EmployeeEvaluationForm DROP COLUMN evaluationStatus RESTRICT;
ALTER TABLE Question DROP COLUMN scoreTypeId RESTRICT;
