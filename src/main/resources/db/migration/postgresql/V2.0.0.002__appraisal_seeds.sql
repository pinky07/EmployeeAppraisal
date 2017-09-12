
-- Delete old data

DELETE FROM EvaluationFormTemplateXSectionXQuestion;
DELETE FROM EmployeeEvaluationForm;
DELETE FROM EvaluationFormTemplateXJobLevel;
DELETE FROM AppraisalXEvaluationFormTemplate;
DELETE FROM Question;
DELETE FROM Section;
DELETE FROM ScoreValue;
DELETE FROM ScoreType;
DELETE FROM EvaluationFormTemplate;
DELETE FROM Appraisal;

-- Insert new data

INSERT INTO Appraisal (id, name, description, startDate, endDate) VALUES
    (1, 'Appraisal 2017', 'Appraisal 2017 Description', '2017-12-01 00:00:00-06', '2018-01-31 00:00:00-06'),
    (2, 'Appraisal 2016', 'Appraisal 2016 Description', '2016-12-01 00:00:00-06', '2017-01-31 00:00:00-06'),
    (3, 'Appraisal 2015', 'Appraisal 2015 Description', '2015-12-01 00:00:00-06', '2016-01-31 00:00:00-06');

INSERT INTO EvaluationFormTemplate (id, name, description) VALUES
    (1, 'General Evaluation Form', 'Evaluates all employees');

INSERT INTO ScoreType (id, definition) VALUES
    (1, 'Comment'),
    (2, '1 to 5');

INSERT INTO ScoreValue (scoreTypeId, value, description) VALUES
    (1, 'NA', 'Comment'),
    (2, '1', 'Strongly disagree'),
    (2, '2', 'Disagree'),
    (2, '3', 'Neither agree nor disagree'),
    (2, '4', 'Agree'),
    (2, '5', 'Strongly agree');

INSERT INTO Section (id, position, scoreTypeId, name, description) VALUES
    (1, 1, 2, 'Core Values', '-'),
    (2, 2, 2, 'Qualities', '-'),
    (3, 4, 1, 'Strengths', '-'),
    (4, 5, 1, 'Areas of Improvement', '-'),
    (5, 6, 1, 'Project Contributions', '-'),
    (6, 7, 1, 'Additional Contributions', '-'),
    (7, 8, 1, 'Career Goals', '-');

INSERT INTO Question (id, position, name, description) VALUES
    -- Core Values Section
    (1, 1, 'Committed', 'Engages to internal projects taking actions to finish the tasks and motivating others to join the effort. Creates value for Rule Financial by creating value for our clients. Builds long-term client relationships to deliver long-term value. Is committed to our clients.'),
    (2, 2, 'Courageous', 'Acts as a role model, always trying to do the right thing and standing up for what is right even when he or she has to stand alone. Takes responsibility for our own development. Is committed to providing growth opportunities for all. Embraces opportunities for personal development'),
    (3, 3, 'Collaborative', 'Contributes to recruitment activities such as: job fairs, interviews or any other HR/PMO/ internal project. Believes that teams achieve more than their members working alone. Celebrates the contribution of each individual to their team. Collaborates in order to succeed'),
    (4, 4, 'Caring', 'We  ensure everyone counts. We are objective in evaluating and recognizing performance. We show everyone equal respect. We treat others as we expect to be treated.'),
    (5, 5, 'Creative', 'Proposes effective solutions and present innovative ideas to improve a practice/process/dept.; looks beyond the obvious and analyzes the root cause of issues. We aspire to meet the highest standards through rigor and'),

    -- Qualities Section
    (6, 1, 'Autonomy', 'Independent, displays initiative instead of waiting to be told, is proactive, is disciplined, doesn’t need to be reminded about duties, minimal supervision is required.'),
    (7, 2, 'Coaching', 'Acts as a person who can be asked for advise and encourages professional and personal development of others.  Shows a sincere interest in employees and the solutions to their problems.'),
    (8, 3, 'Communication', 'Conveys information to others clearly, concisely, assertively and effectively (both orally and written). Effectively communicates upward, downward and horizontally.'),
    (9, 4, 'Client Focus', 'Demonstrates a client-first attitude when prioritizing work and making decisions. Builds and encourages a relationship based on trust and transparency with the client.'),
    (10, 5, 'Dependability', 'Is being able to be relied upon to complete pending tasks or to solve problems or situations.'),
    (11, 6, 'Leadership', 'Influences and creates a positive impact on the organization, coaches and helps other members grow, is a role model, is a good listener, has empathy, negotiates and makes decisions effectively.'),
    (12, 7, 'Positiveness', 'Remains optimistic and upbeat, embraces challenge positively, provides objective feedback.'),
    (13, 8, 'Problem solving', 'Displays a practical approach to solving problems, develops creative solutions and proposes multiple alternatives, effectively solves problems rather than symptoms.'),
    (14, 9, 'Time Management', 'Schedules tasks efficiently to meet deadlines, prioritizes own work, is able to handle multiple tasks effectively, drives follow-up items to completion, keeps meetings on schedule, makes effective use of discretionary time.'),
    (15, 10, 'Work Ethics', 'Behaves in a professional manner, is polite and respectful to others, is honest, is accountable of own decisions, shows respect to others beliefs and opinions.'),
    (16, 11, 'Team Work', 'Encourages collaboration within the team, shares ideas and expertise, focuses on achieving results together as a unit.'),
    (17, 12, 'Work Quality', 'Tasks and duties are performed with accuracy, quality and thoroughness. Deliverables/outputs are created with the best quality as possible and delivered on a timely manner.'),
    (18, 13, 'Efficiency', 'Executes tasks or duties meeting deadlines, KPI''s, SLA or budget requirements.'),
    (19, 14, 'Planning', 'Effectively formulates strategies, tactics, and plans to drive results. Embraces and adapts to change.'),
    (20, 15, 'Role Specific Skills', 'Understands own duties and responsibilities, has the level of proficiency required to accomplish work with the necessary tools and resources. Produces all necessary outputs according to standards and expectations.'),

    -- Strengths
    (21, 1, '-', 'What are your self strengths?'),

    -- Areas of Improvement
    (22, 1, '-', 'What are your areas of improvements?'),

    -- Project Contributions
    (23, 1, '-', 'What are your project contributions?'),

    -- Additional Contributions
    (24, 1, '-', 'What are your additional contributions?'),

    -- Career Goals
    (25, 1, '-', 'What are your career goals?');

INSERT INTO AppraisalXEvaluationFormTemplate (id, appraisalId, evaluationFormTemplateId) VALUES
    (1, 1, 1), -- Appraisal 2017
    (2, 2, 1), -- Appraisal 2016
    (3, 3, 1); -- Appraisal 2015

INSERT INTO EmployeeEvaluationForm (appraisalXEvaluationFormTemplateId, employeeId, filledByEmployeeId, mentorId, createDate, submitDate) VALUES
    -- Rubén Jiménez (id 2) - Self Evaluation
    (1, 2, 2, 6, '2017-01-01 00:00:00-06', null),

    -- Rubén Jiménez (id 2) - Reference Evaluation
    (1, 2, 3, 6, '2017-01-01 00:00:00-06', null),
    (1, 2, 4, 6, '2017-01-01 00:00:00-06', null),
    (1, 2, 5, 6, '2017-01-01 00:00:00-06', null),

    -- Rubén Jiménez (id 2) - Mentor Evaluation
    (1, 2, 6, 6, '2017-01-01 00:00:00-06', null),

    -- Manuel Yépez (id 3) - Self Evaluation
    (1, 3, 3, 6, '2017-01-01 00:00:00-06', null),

    -- Manuel Yépez (id 3) - Reference Evaluation
    (1, 3, 2, 6, '2017-01-01 00:00:00-06', null),
    (1, 3, 4, 6, '2017-01-01 00:00:00-06', null),
    (1, 3, 5, 6, '2017-01-01 00:00:00-06', null),

    -- Manuel Yépez (id 3) - Mentor Evaluation
    (1, 3, 6, 6, '2017-01-01 00:00:00-06', null);

INSERT INTO EvaluationFormTemplateXSectionXQuestion (evaluationFormTemplateId, sectionId, questionId) VALUES
    -- Core Values Section
    (1, 1, 1),
    (1, 1, 2),
    (1, 1, 3),
    (1, 1, 4),
    (1, 1, 5),
    -- Qualities Section
    (1, 2, 6),
    (1, 2, 7),
    (1, 2, 8),
    (1, 2, 9),
    (1, 2, 10),
    (1, 2, 11),
    (1, 2, 12),
    (1, 2, 13),
    (1, 2, 14),
    (1, 2, 15),
    (1, 2, 16),
    (1, 2, 17),
    (1, 2, 18),
    (1, 2, 19),
    (1, 2, 20),
    -- Strengths
    (1, 3, 21),
    -- Areas of improvement
    (1, 4, 22),
    -- Project Contributions
    (1, 5, 23),
    -- Additional Contributions
    (1, 6, 24),
    -- Career Goals
    (1, 7, 25);

-- Reset serial ids

SELECT pg_catalog.setval(pg_get_serial_sequence('Appraisal', 'id'), MAX(id)) FROM Appraisal;
SELECT pg_catalog.setval(pg_get_serial_sequence('EvaluationFormTemplate', 'id'), MAX(id)) FROM EvaluationFormTemplate;
SELECT pg_catalog.setval(pg_get_serial_sequence('Section', 'id'), MAX(id)) FROM Section;
SELECT pg_catalog.setval(pg_get_serial_sequence('Question', 'id'), MAX(id)) FROM Question;
SELECT pg_catalog.setval(pg_get_serial_sequence('ScoreType', 'id'), MAX(id)) FROM ScoreType;
SELECT pg_catalog.setval(pg_get_serial_sequence('ScoreValue', 'id'), MAX(id)) FROM ScoreValue;
SELECT pg_catalog.setval(pg_get_serial_sequence('AppraisalXEvaluationFormTemplate', 'id'), MAX(id)) FROM AppraisalXEvaluationFormTemplate;
SELECT pg_catalog.setval(pg_get_serial_sequence('EvaluationFormTemplateXJobLevel', 'id'), MAX(id)) FROM EvaluationFormTemplateXJobLevel;
SELECT pg_catalog.setval(pg_get_serial_sequence('EmployeeEvaluationForm', 'id'), MAX(id)) FROM EmployeeEvaluationForm;
SELECT pg_catalog.setval(pg_get_serial_sequence('EvaluationFormTemplateXSectionXQuestion', 'id'), MAX(id)) FROM EvaluationFormTemplateXSectionXQuestion;
