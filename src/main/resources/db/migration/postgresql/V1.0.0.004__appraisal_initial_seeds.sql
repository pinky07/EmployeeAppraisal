INSERT INTO Appraisal (id, name, description, startDate, endDate)
VALUES  (1, 'Appraisal 2017', 'Appraisal 2017 Description', '2017-12-01 00:00:00-06', '2018-01-31 00:00:00-06'),
        (2, 'Appraisal 2016', 'Appraisal 2017 Description', '2016-12-01 00:00:00-06', '2017-01-31 00:00:00-06'),
        (3, 'Appraisal 2015', 'Appraisal 2017 Description', '2015-12-01 00:00:00-06', '2016-01-31 00:00:00-06');

INSERT INTO EvaluationForm (id, name, description)
VALUES
        -- L1, L2, L3
        (1, 'L1,L2,L3 - Self Evaluation Form', 'Junior and Mid Self Evaluation Form'),
        (2, 'L1,L2,L3 - Reference Evaluation Form', 'Junior and Mid Reference Evaluation Form'),
        (3, 'L1,L2,L3 - Mentor Evaluation Form', 'Junior and Mid Mentor Consolidated Evaluation Form'),

        -- L1, L2, L3 - New Hire
        (4, 'L1,L2,L3 - New Hire - Self Evaluation Form', 'Junior and Mid Self Evaluation Form'),
        (5, 'L1,L2,L3 - New Hire - Reference Evaluation Form', 'Junior and Mid Reference Evaluation Form'),
        (6, 'L1,L2,L3 - New Hire - Mentor Evaluation Form', 'Junior and Mid Mentor Consolidated Evaluation Form'),

        -- L4, L5
        (7, 'L4,L5 - Self Evaluation Form', 'Senior Self Evaluation Form'),
        (8, 'L4,L5 - Reference Evaluation Form', 'Senior Reference Evaluation Form'),
        (9, 'L4,L5 - Mentor Evaluation Form', 'Senior Mentor Consolidated Evaluation Form'),

        -- L4, L5 - New Hire
        (10, 'L4,L5 - New Hire - Self Evaluation Form', 'Senior Self Evaluation Form'),
        (11, 'L4,L5 - New Hire - Reference Evaluation Form', 'Senior Reference Evaluation Form'),
        (12, 'L4,L5 - New Hire - Mentor Evaluation Form', 'Senior Mentor Consolidated Evaluation Form'),

        -- L6, L7
        (13, 'L6,L7 - Self Evaluation Form', 'Management Self Evaluation Form'),
        (14, 'L6,L7 - Reference Evaluation Form', 'Management Reference Evaluation Form'),
        (15, 'L6,L7 - Mentor Evaluation Form', 'Management Consolidated Evaluation Form'),

        -- L6, L7 - New Hire
        (16, 'L6,L7 - New Hire - Self Evaluation Form', 'Management Self Evaluation Form'),
        (17, 'L6,L7 - New Hire - Reference Evaluation Form', 'Management Reference Evaluation Form'),
        (18, 'L6,L7 - New Hire - Mentor Evaluation Form', 'Management Consolidated Evaluation Form');


INSERT INTO EvaluationFormSection (id, name, description)
VALUES  (1, 'Core Values', '-'),
        (2, 'Qualities', '-'),
        (3, 'Promotion', '-'),
        (4, 'Strengths', '-'),
        (5, 'Areas of Improvement', '-'),
        (6, 'Project Contributions', '-'),
        (7, 'Additional Contributions', '-'),
        (8, 'Career Goals', '-');

INSERT INTO ScoreType (id, definition)
VALUES  (1, 'Comment'),
        (2, 'Yes/No'),
        (3, 'Likert scale');

INSERT INTO ScoreValue (id, scoreTypeId, value, description)
VALUES  (1, 1, 'NA', 'Comment'),
        (2, 2, 'Yes', '-'),
        (3, 2, 'No', '-'),
        (4, 3, 'Strongly disagree', '-'),
        (5, 3, 'Disagree', '-'),
        (6, 3, 'Neither agree nor disagree', '-'),
        (7, 3, 'Agree', '-'),
        (8, 3, 'Strongly agree', '-');

INSERT INTO EvaluationFormQuestion (id, scoreTypeId, name, description)
VALUES

        -- Core Values Section
        (1, 3, 'Committed', 'Engages to internal projects taking actions to finish the tasks and motivating others to join the effort. Creates value for Rule Financial by creating value for our clients. Builds long-term client relationships to deliver long-term value. Is committed to our clients.'),
        (2, 3, 'Courageous', 'Acts as a role model, always trying to do the right thing and standing up for what is right even when he or she has to stand alone. Takes responsibility for our own development. Is committed to providing growth opportunities for all. Embraces opportunities for personal development'),
        (3, 3, 'Collaborative', 'Contributes to recruitment activities such as: job fairs, interviews or any other HR/PMO/ internal project. Believes that teams achieve more than their members working alone. Celebrates the contribution of each individual to their team. Collaborates in order to succeed'),
        (4, 3, 'Caring', 'We  ensure everyone counts. We are objective in evaluating and recognizing performance. We show everyone equal respect. We treat others as we expect to be treated.'),
        (5, 3, 'Creative', 'Proposes effective solutions and present innovative ideas to improve a practice/process/dept.; looks beyond the obvious and analyzes the root cause of issues. We aspire to meet the highest standards through rigor and'),

        -- Qualities Section
        (6, 3, 'Autonomy', 'Independent, displays initiative instead of waiting to be told, is proactive, is disciplined, doesn’t need to be reminded about duties, minimal supervision is required.'),
        (7, 3, 'Coaching', 'Acts as a person who can be asked for advise and encourages professional and personal development of others.  Shows a sincere interest in employees and the solutions to their problems.'),
        (8, 3, 'Communication', 'Conveys information to others clearly, concisely, assertively and effectively (both orally and written). Effectively communicates upward, downward and horizontally.'),
        (9, 3, 'Client Focus', 'Demonstrates a client-first attitude when prioritizing work and making decisions. Builds and encourages a relationship based on trust and transparency with the client.'),
        (10, 3, 'Dependability', 'Is being able to be relied upon to complete pending tasks or to solve problems or situations.'),
        (11, 3, 'Leadership', 'Influences and creates a positive impact on the organization, coaches and helps other members grow, is a role model, is a good listener, has empathy, negotiates and makes decisions effectively.'),
        (12, 3, 'Positiveness', 'Remains optimistic and upbeat, embraces challenge positively, provides objective feedback.'),
        (13, 3, 'Problem solving', 'Displays a practical approach to solving problems, develops creative solutions and proposes multiple alternatives, effectively solves problems rather than symptoms.'),
        (14, 3, 'Time Management', 'Schedules tasks efficiently to meet deadlines, prioritizes own work, is able to handle multiple tasks effectively, drives follow-up items to completion, keeps meetings on schedule, makes effective use of discretionary time.'),
        (15, 3, 'Work Ethics', 'Behaves in a professional manner, is polite and respectful to others, is honest, is accountable of own decisions, shows respect to others beliefs and opinions.'),
        (16, 3, 'Team Work', 'Encourages collaboration within the team, shares ideas and expertise, focuses on achieving results together as a unit.'),
        (17, 3, 'Work Quality', 'Tasks and duties are performed with accuracy, quality and thoroughness. Deliverables/outputs are created with the best quality as possible and delivered on a timely manner.'),
        (18, 3, 'Efficiency', 'Executes tasks or duties meeting deadlines, KPI''s, SLA or budget requirements.'),
        (19, 3, 'Planning', 'Effectively formulates strategies, tactics, and plans to drive results. Embraces and adapts to change.'),
        (20, 3, 'Role Specific Skills', 'Understands own duties and responsibilities, has the level of proficiency required to accomplish work with the necessary tools and resources. Produces all necessary outputs according to standards and expectations.'),

        -- Promotion
        (21, 2, '-', 'Are you ready for promotion?'),
        (22, 2, '-', 'Is your peer ready for promotion?'),
        (23, 1, '-', 'Is your mentee ready for promotion?'),

        -- Strengths
        (24, 1, '-', 'What are your self strengths?'),
        (25, 1, '-', 'What are your peer''s strengths?'),
        (26, 1, '-', 'What are your mentee''s strengths?'),

        -- Areas of Improvement
        (27, 1, '-', 'What are your areas of improvements?'),
        (28, 1, '-', 'What are your peer''s areas of improvements?'),
        (29, 1, '-', 'What are your mentee''s areas of improvements?'),

        -- Project Contributions
        (30, 1, '-', 'What are your project contributions?'),
        (31, 1, '-', 'What are your peer''s project contributions?'),
        (32, 1, '-', 'What are your mentee''s project contributions?'),

        -- Additional Contributions
        (33, 1, '-', 'What are your additional contributions?'),
        (34, 1, '-', 'What are your peer''s additional contributions?'),
        (35, 1, '-', 'What are your mentee''s additional contributions?'),

        -- Career Goals
        (36, 1, '-', 'What are your career goals?');

INSERT INTO AppraisalXEvaluationForm (id, appraisalId, evaluationFormId)
VALUES  

        -- Appraisal 2017
        (1, 1, 1), -- L1,L2,L3 - Self Evaluation Form
        (2, 1, 2), -- L1,L2,L3 - Reference Evaluation Form
        (3, 1, 3), -- L1,L2,L3 - Mentor Evaluation Form
        (4, 1, 4), -- L1,L2,L3 - New Hire - Self Evaluation Form
        (5, 1, 5), -- L1,L2,L3 - New Hire - Reference Evaluation Form
        (6, 1, 6), -- L1,L2,L3 - New Hire - Mentor Evaluation Form
        (7, 1, 7), -- L4,L5 - Self Evaluation Form
        (8, 1, 8), -- L4,L5 - Reference Evaluation Form
        (9, 1, 9), -- L4,L5 - Mentor Evaluation Form
        (10, 1, 10), -- L4,L5 - New Hire - Self Evaluation Form
        (11, 1, 11), -- L4,L5 - New Hire - Reference Evaluation Form
        (12, 1, 12), -- L4,L5 - New Hire - Mentor Evaluation Form
        (13, 1, 13), -- L6,L7 - Self Evaluation Form
        (14, 1, 14), -- L6,L7 - Reference Evaluation Form
        (15, 1, 15), -- L6,L7 - Mentor Evaluation Form
        (16, 1, 16), -- L6,L7 - New Hire - Self Evaluation Form
        (17, 1, 17), -- L6,L7 - New Hire - Reference Evaluation Form
        (18, 1, 18), -- L6,L7 - New Hire - Mentor Evaluation Form

        -- Appraisal 2016
        (19, 1, 1), -- L1,L2,L3 - Self Evaluation Form
        (20, 1, 2), -- L1,L2,L3 - Reference Evaluation Form
        (21, 1, 3), -- L1,L2,L3 - Mentor Evaluation Form
        (22, 1, 4), -- L1,L2,L3 - New Hire - Self Evaluation Form
        (23, 1, 5), -- L1,L2,L3 - New Hire - Reference Evaluation Form
        (24, 1, 6), -- L1,L2,L3 - New Hire - Mentor Evaluation Form
        (25, 1, 7), -- L4,L5 - Self Evaluation Form
        (26, 1, 8), -- L4,L5 - Reference Evaluation Form
        (27, 1, 9), -- L4,L5 - Mentor Evaluation Form
        (28, 1, 10), -- L4,L5 - New Hire - Self Evaluation Form
        (29, 1, 11), -- L4,L5 - New Hire - Reference Evaluation Form
        (30, 1, 12), -- L4,L5 - New Hire - Mentor Evaluation Form
        (31, 1, 13), -- L6,L7 - Self Evaluation Form
        (32, 1, 14), -- L6,L7 - Reference Evaluation Form
        (33, 1, 15), -- L6,L7 - Mentor Evaluation Form
        (34, 1, 16), -- L6,L7 - New Hire - Self Evaluation Form
        (35, 1, 17), -- L6,L7 - New Hire - Reference Evaluation Form
        (36, 1, 18), -- L6,L7 - New Hire - Mentor Evaluation Form

        -- Appraisal 2015
        (37, 1, 1), -- L1,L2,L3 - Self Evaluation Form
        (38, 1, 2), -- L1,L2,L3 - Reference Evaluation Form
        (39, 1, 3), -- L1,L2,L3 - Mentor Evaluation Form
        (40, 1, 4), -- L1,L2,L3 - New Hire - Self Evaluation Form
        (41, 1, 5), -- L1,L2,L3 - New Hire - Reference Evaluation Form
        (42, 1, 6), -- L1,L2,L3 - New Hire - Mentor Evaluation Form
        (43, 1, 7), -- L4,L5 - Self Evaluation Form
        (44, 1, 8), -- L4,L5 - Reference Evaluation Form
        (45, 1, 9), -- L4,L5 - Mentor Evaluation Form
        (46, 1, 10), -- L4,L5 - New Hire - Self Evaluation Form
        (47, 1, 11), -- L4,L5 - New Hire - Reference Evaluation Form
        (48, 1, 12), -- L4,L5 - New Hire - Mentor Evaluation Form
        (49, 1, 13), -- L6,L7 - Self Evaluation Form
        (50, 1, 14), -- L6,L7 - Reference Evaluation Form
        (51, 1, 15), -- L6,L7 - Mentor Evaluation Form
        (52, 1, 16), -- L6,L7 - New Hire - Self Evaluation Form
        (53, 1, 17), -- L6,L7 - New Hire - Reference Evaluation Form
        (54, 1, 18); -- L6,L7 - New Hire - Mentor Evaluation Form

INSERT INTO EvaluationFormXJobLevel (id, evaluationFormId, jobLevelId)
VALUES

        -- L1, L2, L3

        (1, 1, 1), -- L1
        (2, 1, 15), -- L1
        (3, 1, 22), -- L1
        (4, 1, 29), -- L1
        (5, 1, 36), -- L1
        (6, 1, 43), -- L1
        (7, 1, 50), -- L1
        (8, 1, 64), -- L1
        (9, 1, 2), -- L2
        (10, 1, 16), -- L2
        (11, 1, 23), -- L2
        (12, 1, 30), -- L2
        (13, 1, 37), -- L2
        (14, 1, 44), -- L2
        (15, 1, 51), -- L2
        (16, 1, 65), -- L2
        (17, 1, 3), -- L3
        (18, 1, 17), -- L3
        (19, 1, 24), -- L3
        (20, 1, 31), -- L3
        (21, 1, 38), -- L3
        (22, 1, 45), -- L3
        (23, 1, 52), -- L3
        (24, 1, 66), -- L3
        (25, 2, 1), -- L1
        (26, 2, 15), -- L1
        (27, 2, 22), -- L1
        (28, 2, 29), -- L1
        (29, 2, 36), -- L1
        (30, 2, 43), -- L1
        (31, 2, 50), -- L1
        (32, 2, 64), -- L1
        (33, 2, 2), -- L2
        (34, 2, 16), -- L2
        (35, 2, 23), -- L2
        (36, 2, 30), -- L2
        (37, 2, 37), -- L2
        (38, 2, 44), -- L2
        (39, 2, 51), -- L2
        (40, 2, 65), -- L2
        (41, 2, 3), -- L3
        (42, 2, 17), -- L3
        (43, 2, 24), -- L3
        (44, 2, 31), -- L3
        (45, 2, 38), -- L3
        (46, 2, 45), -- L3
        (47, 2, 52), -- L3
        (48, 2, 66), -- L3
        (49, 3, 1), -- L1
        (50, 3, 15), -- L1
        (51, 3, 22), -- L1
        (52, 3, 29), -- L1
        (53, 3, 36), -- L1
        (54, 3, 43), -- L1
        (55, 3, 50), -- L1
        (56, 3, 64), -- L1
        (57, 3, 2), -- L2
        (58, 3, 16), -- L2
        (59, 3, 23), -- L2
        (60, 3, 30), -- L2
        (61, 3, 37), -- L2
        (62, 3, 44), -- L2
        (63, 3, 51), -- L2
        (64, 3, 65), -- L2
        (65, 3, 3), -- L3
        (66, 3, 17), -- L3
        (67, 3, 24), -- L3
        (68, 3, 31), -- L3
        (69, 3, 38), -- L3
        (70, 3, 45), -- L3
        (71, 3, 52), -- L3
        (72, 3, 66), -- L3
        (73, 4, 1), -- L1
        (74, 4, 15), -- L1
        (75, 4, 22), -- L1
        (76, 4, 29), -- L1
        (77, 4, 36), -- L1
        (78, 4, 43), -- L1
        (79, 4, 50), -- L1
        (80, 4, 64), -- L1
        (81, 4, 2), -- L2
        (82, 4, 16), -- L2
        (83, 4, 23), -- L2
        (84, 4, 30), -- L2
        (85, 4, 37), -- L2
        (86, 4, 44), -- L2
        (87, 4, 51), -- L2
        (88, 4, 65), -- L2
        (89, 4, 3), -- L3
        (90, 4, 17), -- L3
        (91, 4, 24), -- L3
        (92, 4, 31), -- L3
        (93, 4, 38), -- L3
        (94, 4, 45), -- L3
        (95, 4, 52), -- L3
        (96, 4, 66), -- L3
        (97, 5, 1), -- L1
        (98, 5, 15), -- L1
        (99, 5, 22), -- L1
        (100, 5, 29), -- L1
        (101, 5, 36), -- L1
        (102, 5, 43), -- L1
        (103, 5, 50), -- L1
        (104, 5, 64), -- L1
        (105, 5, 2), -- L2
        (106, 5, 16), -- L2
        (107, 5, 23), -- L2
        (108, 5, 30), -- L2
        (109, 5, 37), -- L2
        (110, 5, 44), -- L2
        (111, 5, 51), -- L2
        (112, 5, 65), -- L2
        (113, 5, 3), -- L3
        (114, 5, 17), -- L3
        (115, 5, 24), -- L3
        (116, 5, 31), -- L3
        (117, 5, 38), -- L3
        (118, 5, 45), -- L3
        (119, 5, 52), -- L3
        (120, 5, 66), -- L3
        (121, 6, 1), -- L1
        (122, 6, 15), -- L1
        (123, 6, 22), -- L1
        (124, 6, 29), -- L1
        (125, 6, 36), -- L1
        (126, 6, 43), -- L1
        (127, 6, 50), -- L1
        (128, 6, 64), -- L1
        (129, 6, 2), -- L2
        (130, 6, 16), -- L2
        (131, 6, 23), -- L2
        (132, 6, 30), -- L2
        (133, 6, 37), -- L2
        (134, 6, 44), -- L2
        (135, 6, 51), -- L2
        (136, 6, 65), -- L2
        (137, 6, 3), -- L3
        (138, 6, 17), -- L3
        (139, 6, 24), -- L3
        (140, 6, 31), -- L3
        (141, 6, 38), -- L3
        (142, 6, 45), -- L3
        (143, 6, 52), -- L3
        (144, 6, 66), -- L3

        -- L4, L5

        (145, 7, 4), -- L4
        (146, 7, 11), -- L4
        (147, 7, 18), -- L4
        (148, 7, 25), -- L4
        (149, 7, 32), -- L4
        (150, 7, 39), -- L4
        (151, 7, 46), -- L4
        (152, 7, 53), -- L4
        (153, 7, 60), -- L4
        (154, 7, 67), -- L4
        (155, 7, 5), -- L5
        (156, 7, 12), -- L5
        (157, 7, 19), -- L5
        (158, 7, 26), -- L5
        (159, 7, 33), -- L5
        (160, 7, 40), -- L5
        (161, 7, 47), -- L5
        (162, 7, 54), -- L5
        (163, 7, 61), -- L5
        (164, 7, 68), -- L5
        (165, 8, 4), -- L4
        (166, 8, 11), -- L4
        (167, 8, 18), -- L4
        (168, 8, 25), -- L4
        (169, 8, 32), -- L4
        (170, 8, 39), -- L4
        (171, 8, 46), -- L4
        (172, 8, 53), -- L4
        (173, 8, 60), -- L4
        (174, 8, 67), -- L4
        (175, 8, 5), -- L5
        (176, 8, 12), -- L5
        (177, 8, 19), -- L5
        (178, 8, 26), -- L5
        (179, 8, 33), -- L5
        (180, 8, 40), -- L5
        (181, 8, 47), -- L5
        (182, 8, 54), -- L5
        (183, 8, 61), -- L5
        (184, 8, 68), -- L5
        (185, 9, 4), -- L4
        (186, 9, 11), -- L4
        (187, 9, 18), -- L4
        (188, 9, 25), -- L4
        (189, 9, 32), -- L4
        (190, 9, 39), -- L4
        (191, 9, 46), -- L4
        (192, 9, 53), -- L4
        (193, 9, 60), -- L4
        (194, 9, 67), -- L4
        (195, 9, 5), -- L5
        (196, 9, 12), -- L5
        (197, 9, 19), -- L5
        (198, 9, 26), -- L5
        (199, 9, 33), -- L5
        (200, 9, 40), -- L5
        (201, 9, 47), -- L5
        (202, 9, 54), -- L5
        (203, 9, 61), -- L5
        (204, 9, 68), -- L5
        (205, 10, 4), -- L4
        (206, 10, 11), -- L4
        (207, 10, 18), -- L4
        (208, 10, 25), -- L4
        (209, 10, 32), -- L4
        (210, 10, 39), -- L4
        (211, 10, 46), -- L4
        (212, 10, 53), -- L4
        (213, 10, 60), -- L4
        (214, 10, 67), -- L4
        (215, 10, 5), -- L5
        (216, 10, 12), -- L5
        (217, 10, 19), -- L5
        (218, 10, 26), -- L5
        (219, 10, 33), -- L5
        (220, 10, 40), -- L5
        (221, 10, 47), -- L5
        (222, 10, 54), -- L5
        (223, 10, 61), -- L5
        (224, 10, 68), -- L5
        (225, 11, 4), -- L4
        (226, 11, 11), -- L4
        (227, 11, 18), -- L4
        (228, 11, 25), -- L4
        (229, 11, 32), -- L4
        (230, 11, 39), -- L4
        (231, 11, 46), -- L4
        (232, 11, 53), -- L4
        (233, 11, 60), -- L4
        (234, 11, 67), -- L4
        (235, 11, 5), -- L5
        (236, 11, 12), -- L5
        (237, 11, 19), -- L5
        (238, 11, 26), -- L5
        (239, 11, 33), -- L5
        (240, 11, 40), -- L5
        (241, 11, 47), -- L5
        (242, 11, 54), -- L5
        (243, 11, 61), -- L5
        (244, 11, 68), -- L5
        (245, 12, 4), -- L4
        (246, 12, 11), -- L4
        (247, 12, 18), -- L4
        (248, 12, 25), -- L4
        (249, 12, 32), -- L4
        (250, 12, 39), -- L4
        (251, 12, 46), -- L4
        (252, 12, 53), -- L4
        (253, 12, 60), -- L4
        (254, 12, 67), -- L4
        (255, 12, 5), -- L5
        (256, 12, 12), -- L5
        (257, 12, 19), -- L5
        (258, 12, 26), -- L5
        (259, 12, 33), -- L5
        (260, 12, 40), -- L5
        (261, 12, 47), -- L5
        (262, 12, 54), -- L5
        (263, 12, 61), -- L5
        (264, 12, 68), -- L5

        -- L6, L7

        (265, 13, 6), -- L6
        (266, 13, 13), -- L6
        (267, 13, 20), -- L6
        (268, 13, 27), -- L6
        (269, 13, 34), -- L6
        (270, 13, 41), -- L6
        (271, 13, 48), -- L6
        (272, 13, 55), -- L6
        (273, 13, 62), -- L6
        (274, 13, 69), -- L6
        (275, 13, 7), -- L7
        (276, 13, 14), -- L7
        (277, 13, 21), -- L7
        (278, 13, 28), -- L7
        (279, 13, 35), -- L7
        (280, 13, 42), -- L7
        (281, 13, 49), -- L7
        (282, 13, 56), -- L7
        (283, 13, 63), -- L7
        (284, 13, 70), -- L7
        (285, 14, 6), -- L6
        (286, 14, 13), -- L6
        (287, 14, 20), -- L6
        (288, 14, 27), -- L6
        (289, 14, 34), -- L6
        (290, 14, 41), -- L6
        (291, 14, 48), -- L6
        (292, 14, 55), -- L6
        (293, 14, 62), -- L6
        (294, 14, 69), -- L6
        (295, 14, 7), -- L7
        (296, 14, 14), -- L7
        (297, 14, 21), -- L7
        (298, 14, 28), -- L7
        (299, 14, 35), -- L7
        (300, 14, 42), -- L7
        (301, 14, 49), -- L7
        (302, 14, 56), -- L7
        (303, 14, 63), -- L7
        (304, 14, 70), -- L7
        (305, 15, 6), -- L6
        (306, 15, 13), -- L6
        (307, 15, 20), -- L6
        (308, 15, 27), -- L6
        (309, 15, 34), -- L6
        (310, 15, 41), -- L6
        (311, 15, 48), -- L6
        (312, 15, 55), -- L6
        (313, 15, 62), -- L6
        (314, 15, 69), -- L6
        (315, 15, 7), -- L7
        (316, 15, 14), -- L7
        (317, 15, 21), -- L7
        (318, 15, 28), -- L7
        (319, 15, 35), -- L7
        (320, 15, 42), -- L7
        (321, 15, 49), -- L7
        (322, 15, 56), -- L7
        (323, 15, 63), -- L7
        (324, 15, 70), -- L7
        (325, 15, 6), -- L6
        (326, 16, 13), -- L6
        (327, 16, 20), -- L6
        (328, 16, 27), -- L6
        (329, 16, 34), -- L6
        (330, 16, 41), -- L6
        (331, 16, 48), -- L6
        (332, 16, 55), -- L6
        (333, 16, 62), -- L6
        (334, 16, 69), -- L6
        (335, 16, 7), -- L7
        (336, 16, 14), -- L7
        (337, 16, 21), -- L7
        (338, 16, 28), -- L7
        (339, 16, 35), -- L7
        (340, 16, 42), -- L7
        (341, 16, 49), -- L7
        (342, 16, 56), -- L7
        (343, 16, 63), -- L7
        (344, 16, 70), -- L7
        (345, 17, 6), -- L6
        (346, 17, 13), -- L6
        (347, 17, 20), -- L6
        (348, 17, 27), -- L6
        (349, 17, 34), -- L6
        (350, 17, 41), -- L6
        (351, 17, 48), -- L6
        (352, 17, 55), -- L6
        (353, 17, 62), -- L6
        (354, 17, 69), -- L6
        (355, 17, 7), -- L7
        (356, 17, 14), -- L7
        (357, 17, 21), -- L7
        (358, 17, 28), -- L7
        (359, 17, 35), -- L7
        (360, 17, 42), -- L7
        (361, 17, 49), -- L7
        (362, 17, 56), -- L7
        (363, 17, 63), -- L7
        (364, 17, 70), -- L7
        (365, 18, 6), -- L6
        (366, 18, 13), -- L6
        (367, 18, 20), -- L6
        (368, 18, 27), -- L6
        (369, 18, 34), -- L6
        (370, 18, 41), -- L6
        (371, 18, 48), -- L6
        (372, 18, 55), -- L6
        (373, 18, 62), -- L6
        (374, 18, 69), -- L6
        (375, 18, 7), -- L7
        (376, 18, 14), -- L7
        (377, 18, 21), -- L7
        (378, 18, 28), -- L7
        (379, 18, 35), -- L7
        (380, 18, 42), -- L7
        (381, 18, 49), -- L7
        (382, 18, 56), -- L7
        (383, 18, 63), -- L7
        (384, 18, 70); -- L7

INSERT INTO AppraisalXEvaluationFormXEmployeeRelationship (id, appraisalXEvaluationFormId, employeeRelationshipId, evaluationStatus)
VALUES

        -- Rubén Jiménez (id 2) - Self Evaluation
        (1, 1, 2, 'PENDING'),

        -- Rubén Jiménez (id 2) - Reference Evaluation
        (2, 2, 31, 'PENDING'),
        (3, 2, 32, 'PENDING'),
        (4, 2, 33, 'PENDING'),

        -- Rubén Jiménez (id 2) - Mentor Evaluation
        (5, 3, 12, 'PENDING'),

        -- Manuel Yépez (id 3) - Self Evaluation
        (6, 1, 3, 'PENDING'),

        -- Manuel Yépez (id 3) - Reference Evaluation
        (7, 2, 34, 'PENDING'),
        (8, 2, 35, 'PENDING'),
        (9, 2, 36, 'PENDING'),

        -- Manuel Yépez (id 3) - Mentor Evaluation
        (10, 3, 13, 'PENDING');


INSERT INTO EvaluationFormXSectionXQuestion (evaluationFormId, evaluationFormSectionId, evaluationFormQuestionId)
VALUES

        -- L1,L2,L3 - Self Evaluation Form

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
        -- Promotion
        (1, 3, 21),
        -- Strengths
        (1, 4, 24),
        -- Areas of improvement
        (1, 5, 27),
        -- Project Contributions
        (1, 6, 30),
        -- Additional Contributions
        (1, 7, 33),
        -- Career Goals
        (1, 8, 36),

        -- L1,L2,L3 - Reference Evaluation Form
        
        -- Core Values Section
        (2, 1, 1),
        (2, 1, 2),
        (2, 1, 3),
        (2, 1, 4),
        (2, 1, 5),
        -- Qualities Section
        (2, 2, 6),
        (2, 2, 7),
        (2, 2, 8),
        (2, 2, 9),
        (2, 2, 10),
        (2, 2, 11),
        (2, 2, 12),
        (2, 2, 13),
        (2, 2, 14),
        (2, 2, 15),
        (2, 2, 16),
        (2, 2, 17),
        (2, 2, 18),
        (2, 2, 19),
        (2, 2, 20),
        -- Promotion
        (2, 3, 22),
        -- Strengths
        (2, 4, 25),
        -- Areas of improvement
        (2, 5, 28),
        -- Project Contributions
        (2, 6, 31),
        -- Additional Contributions
        (2, 7, 34),
        -- Career Goals
        (2, 8, 36),

        -- L1,L2,L3 - Mentor Evaluation Form
        -- Core Values Section
        (3, 1, 1),
        (3, 1, 2),
        (3, 1, 3),
        (3, 1, 4),
        (3, 1, 5),
        -- Qualities Section
        (3, 2, 6),
        (3, 2, 7),
        (3, 2, 8),
        (3, 2, 9),
        (3, 2, 10),
        (3, 2, 11),
        (3, 2, 12),
        (3, 2, 13),
        (3, 2, 14),
        (3, 2, 15),
        (3, 2, 16),
        (3, 2, 17),
        (3, 2, 18),
        (3, 2, 19),
        (3, 2, 20),
        -- Promotion
        (3, 3, 23),
        -- Strengths
        (3, 4, 26),
        -- Areas of improvement
        (3, 5, 29),
        -- Project Contributions
        (3, 6, 32),
        -- Additional Contributions
        (3, 7, 35),
        -- Career Goals
        (3, 8, 36);

-- INSERT INTO EmployeeEvaluationFormAnswer (id, evaluationFormXSectionXQuestionId, appraisalXEvaluationFormXEmployeeRelationshipId, scoreValueId, comment)
-- VALUES  ();
