
-- Fix: After inserting seeds, PostgreSQL requires the next Id to be set "manually". Otherwise it would start at 1
-- Only do this for tables to which you inserted values!

-- Employee Tables
SELECT pg_catalog.setval(pg_get_serial_sequence('JobFamily', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('JobLevel', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('ApplicationRole', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('Employee', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('Relationship', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('EmployeeRelationship', 'id'), MAX(id)) FROM table_name;

-- Appraisal Tables
SELECT pg_catalog.setval(pg_get_serial_sequence('Appraisal', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('EvaluationForm', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('EvaluationFormSection', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('ScoreType', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('ScoreValue', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('EvaluationFormQuestion', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('AppraisalXEvaluationForm', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('EvaluationFormXJobLevel', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('AppraisalXEvaluationFormXEmployeeRelationship', 'id'), MAX(id)) FROM table_name;
SELECT pg_catalog.setval(pg_get_serial_sequence('EvaluationFormXSectionXQuestion', 'id'), MAX(id)) FROM table_name;
-- SELECT pg_catalog.setval(pg_get_serial_sequence('EvaluationFormXSectionXQuestionXAnswer', 'id'), MAX(id)) FROM table_name;
