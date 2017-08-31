-- ApplicationRole
INSERT INTO ApplicationRole (id, name, description)
VALUES  (1, 'ADMIN', 'Application Administrator'),
        (2, 'USER', 'System User');

-- See https://workspace.gft.com/index.php?id=4725&L=0 for Job Families and Levels

-- JobFamily
INSERT INTO JobFamily (id, name, description)
VALUES  (1, 'Application Management Services', 'Application Management Services Description'),
        (2, 'Architecture', 'Architecture Description'),
        (3, 'Business Consulting', 'Business Consulting Description'),
        (4, 'Corporate Services', 'Corporate Services Description'),
        (5, 'Data', 'Data Description'),
        (6, 'Digital', 'Digital Description'),
        (7, 'Project Development', 'Project Development Description'),
        (8, 'Project Governance', 'Project Governance Description'),
        (9, 'Sales', 'Sales Description'),
        (10, 'Testing', 'Testing Description');

-- JobLevel
INSERT INTO JobLevel (id, jobFamilyId, name, description, expertise)
VALUES  (1, 1, 'L1', 'L1 description', 'Entry'),
        (2, 1, 'L2', 'L2 description', 'Skilled'),
        (3, 1, 'L3', 'L3 description', 'Expert'),
        (4, 1, 'L4', 'L4 description', 'Professional'),
        (5, 1, 'L5', 'L5 description', 'Senior'),
        (6, 1, 'L6', 'L6 description', 'Seasoned'),
        (7, 1, 'L7', 'L7 description', 'Leader'),
        (11, 2, 'L4', 'L4 description', 'Professional'),
        (12, 2, 'L5', 'L5 description', 'Senior'),
        (13, 2, 'L6', 'L6 description', 'Seasoned'),
        (14, 2, 'L7', 'L7 description', 'Leader'),
        (15, 3, 'L1', 'L1 description', 'Entry'),
        (16, 3, 'L2', 'L2 description', 'Skilled'),
        (17, 3, 'L3', 'L3 description', 'Expert'),
        (18, 3, 'L4', 'L4 description', 'Professional'),
        (19, 3, 'L5', 'L5 description', 'Senior'),
        (20, 3, 'L6', 'L6 description', 'Seasoned'),
        (21, 3, 'L7', 'L7 description', 'Leader'),
        (22, 4, 'L1', 'L1 description', 'Entry'),
        (23, 4, 'L2', 'L2 description', 'Skilled'),
        (24, 4, 'L3', 'L3 description', 'Expert'),
        (25, 4, 'L4', 'L4 description', 'Professional'),
        (26, 4, 'L5', 'L5 description', 'Senior'),
        (27, 4, 'L6', 'L6 description', 'Seasoned'),
        (28, 4, 'L7', 'L7 description', 'Leader'),
        (29, 5, 'L1', 'L1 description', 'Entry'),
        (30, 5, 'L2', 'L2 description', 'Skilled'),
        (31, 5, 'L3', 'L3 description', 'Expert'),
        (32, 5, 'L4', 'L4 description', 'Professional'),
        (33, 5, 'L5', 'L5 description', 'Senior'),
        (34, 5, 'L6', 'L6 description', 'Seasoned'),
        (35, 5, 'L7', 'L7 description', 'Leader'),
        (36, 6, 'L1', 'L1 description', 'Entry'),
        (37, 6, 'L2', 'L2 description', 'Skilled'),
        (38, 6, 'L3', 'L3 description', 'Expert'),
        (39, 6, 'L4', 'L4 description', 'Professional'),
        (40, 6, 'L5', 'L5 description', 'Senior'),
        (41, 6, 'L6', 'L6 description', 'Seasoned'),
        (42, 6, 'L7', 'L7 description', 'Leader'),
        (43, 7, 'L1', 'L1 description', 'Entry'),
        (44, 7, 'L2', 'L2 description', 'Skilled'),
        (45, 7, 'L3', 'L3 description', 'Expert'),
        (46, 7, 'L4', 'L4 description', 'Professional'),
        (47, 7, 'L5', 'L5 description', 'Senior'),
        (48, 7, 'L6', 'L6 description', 'Seasoned'),
        (49, 7, 'L7', 'L7 description', 'Leader'),
        (50, 8, 'L1', 'L1 description', 'Entry'),
        (51, 8, 'L2', 'L2 description', 'Skilled'),
        (52, 8, 'L3', 'L3 description', 'Expert'),
        (53, 8, 'L4', 'L4 description', 'Professional'),
        (54, 8, 'L5', 'L5 description', 'Senior'),
        (55, 8, 'L6', 'L6 description', 'Seasoned'),
        (56, 8, 'L7', 'L7 description', 'Leader'),
        (60, 9, 'L4', 'L4 description', 'Professional'),
        (61, 9, 'L5', 'L5 description', 'Senior'),
        (62, 9, 'L6', 'L6 description', 'Seasoned'),
        (63, 9, 'L7', 'L7 description', 'Leader'),
        (64, 10, 'L1', 'L1 description', 'Entry'),
        (65, 10, 'L2', 'L2 description', 'Skilled'),
        (66, 10, 'L3', 'L3 description', 'Expert'),
        (67, 10, 'L4', 'L4 description', 'Professional'),
        (68, 10, 'L5', 'L5 description', 'Senior'),
        (69, 10, 'L6', 'L6 description', 'Seasoned'),
        (70, 10, 'L7', 'L7 description', 'Leader');

-- Employee
INSERT INTO Employee (id, jobLevelId, applicationRoleId, email, firstName, lastName, gftIdentifier )
VALUES
        -- Admin
        (1, 1, 1, 'admin@gft.com', 'John', 'Doe', 'JODO'),

        -- Actual employees
        (2, 45, 2, 'ruben.jimenez@gft.com', 'Rubén', 'Jiménez', 'RNJZ'),
        (3, 45, 2, 'manuel.yepez@gft.com', 'Manuel', 'Yepez', 'MLYZ'),
        (4, 47, 2, 'Mario.Leon@gft.com', 'Mario', 'Leon', 'MOLN'),
        (5, 47, 2, 'Eduardo.Herrera@gft.com', 'Eduardo', 'Herrera', 'EOHA'),

        -- Mock Employees
        (6, 70, 2, 'mentor.1@gft.com', 'Christi', 'Goupil', 'ZZZA'),

        (7, 2, 2, 'mentee.1@gft.com', 'Rhea', 'Difiore', 'ZZZB'),
        (8, 3, 2, 'mentee.2@gft.com', 'Moises', 'Tolbert', 'ZZZC'),
        (9, 4, 2, 'mentee.3@gft.com', 'Madge', 'Dufault', 'ZZZD'),
        (10, 5, 2, 'mentee.4@gft.com', 'Corliss', 'Traver', 'ZZZE'),
        (11, 6, 2, 'mentee.5@gft.com', 'Melda', 'Broe', 'ZZZF'),

        (12, 2, 2, 'mentee.6@gft.com', 'Jennie', 'Finnie', 'ZZZG'),
        (13, 3, 2, 'mentee.7@gft.com', 'Jacquiline', 'Gilford', 'ZZZH'),
        (14, 4, 2, 'mentee.8@gft.com', 'Antone', 'Kinnison', 'ZZZI'),
        (15, 5, 2, 'mentee.9@gft.com', 'Wes', 'Touchet', 'ZZZJ'),
        (16, 6, 2, 'mentee.10@gft.com', 'Fumiko', 'Wheeless', 'ZZZK'),

        (17, 2, 2, 'mentee.11@gft.com', 'Glennie', 'Beebe', 'ZZZL'),
        (18, 3, 2, 'mentee.12@gft.com', 'Jocelyn', 'Speight', 'ZZZM'),
        (19, 4, 2, 'mentee.13@gft.com', 'Damon', 'Pothier', 'ZZZN'),
        (20, 5, 2, 'mentee.14@gft.com', 'Leeanna', 'Dankert', 'ZZZO'),
        (21, 6, 2, 'mentee.15@gft.com', 'Fabiola', 'Schleich', 'ZZZP'),

        (22, 2, 2, 'mentee.16@gft.com', 'Valentine', 'Drinkard', 'ZZZQ'),
        (23, 3, 2, 'mentee.17@gft.com', 'Carola', 'Trentham', 'ZZZR'),
        (24, 4, 2, 'mentee.18@gft.com', 'Alethea', 'Gust', 'ZZZS'),
        (25, 5, 2, 'mentee.19@gft.com', 'Estrella', 'Kuck', 'ZZZT'),
        (26, 6, 2, 'mentee.20@gft.com', 'Cheryl', 'Harner', 'ZZZU');

-- Relationship
INSERT INTO Relationship (id, name, description)
VALUES  (1, 'SELF', 'Self relationship'),
        (2, 'PEER', 'Peer relationship'),
        (3, 'MENTOR', 'Mentor relationship'),
        (4, 'LEAD', 'Lead relationship'),
        (5, 'OTHER', 'Other relationship');

-- EmployeeRelationship
INSERT INTO EmployeeRelationship (id,  sourceEmployeeId, targetEmployeeId, relationshipId, startDate, endDate)
VALUES

        -- Self relationships
        (1, 1, 1, 1, '2017-01-01 00:00:00', null),
        (2, 2, 2, 1, '2017-01-01 00:00:00', null),
        (3, 3, 3, 1, '2017-01-01 00:00:00', null),
        (4, 4, 4, 1, '2017-01-01 00:00:00', null),
        (5, 5, 5, 1, '2017-01-01 00:00:00', null),
        (6, 6, 6, 1, '2017-01-01 00:00:00', null),
        (7, 7, 7, 1, '2017-01-01 00:00:00', null),
        (8, 8, 8, 1, '2017-01-01 00:00:00', null),
        (9, 9, 9, 1, '2017-01-01 00:00:00', null),
        (10, 10, 10, 1, '2017-01-01 00:00:00', null),
        (11, 11, 11, 1, '2017-01-01 00:00:00', null),

        -- Mentor: Mentor 1 / Mentee: Rubén Jiménez
        (12, 6, 2, 3, '2017-01-01 00:00:00', null),

        -- Mentor: Mentor 1 / Mentee: Manuel Yepez
        (13, 6, 3, 3, '2017-01-01 00:00:00', null),

        -- Mentor: Mentor 1 / Mentee: Mario Leon
        (14, 6, 4, 3, '2017-01-01 00:00:00', null),

        -- Mentor: Mentor 1 / Mentee: Eduardo Herrera
        (15, 6, 5, 3, '2017-01-01 00:00:00', null),

        -- Rubén Jiménez mentees
        (16, 2, 7, 3, '2017-01-01 00:00:00', null),
        (17, 2, 8, 3, '2017-01-01 00:00:00', null),
        (18, 2, 9, 3, '2017-01-01 00:00:00', null),
        (19, 2, 10, 3, '2017-01-01 00:00:00', null),
        (20, 2, 11, 3, '2017-01-01 00:00:00', null),

        -- Manuel Yepez mentees
        (161, 3, 12, 3, '2017-01-01 00:00:00', null),
        (171, 3, 13, 3, '2017-01-01 00:00:00', null),
        (181, 3, 14, 3, '2017-01-01 00:00:00', null),
        (191, 3, 15, 3, '2017-01-01 00:00:00', null),
        (201, 3, 16, 3, '2017-01-01 00:00:00', null),

        -- Mario Leon mentees
        (21, 4, 17, 3, '2017-01-01 00:00:00', null),
        (22, 4, 18, 3, '2017-01-01 00:00:00', null),
        (23, 4, 19, 3, '2017-01-01 00:00:00', null),
        (24, 4, 20, 3, '2017-01-01 00:00:00', null),
        (25, 4, 21, 3, '2017-01-01 00:00:00', null),

        -- Eduardo Herrera mentees
        (26, 5, 22, 3, '2017-01-01 00:00:00', null),
        (27, 5, 23, 3, '2017-01-01 00:00:00', null),
        (28, 5, 24, 3, '2017-01-01 00:00:00', null),
        (29, 5, 25, 3, '2017-01-01 00:00:00', null),
        (30, 5, 26, 3, '2017-01-01 00:00:00', null),

        -- NOTE: Mentee 1 is the lead of the other Mentees
        -- All the mentees reference each other

        -- Rubén Jiménez References
        (31, 2, 3, 2, '2017-01-01 00:00:00', null),
        (32, 2, 4, 2, '2017-01-01 00:00:00', null),
        (33, 2, 5, 2, '2017-01-01 00:00:00', null),

        -- Manuel Yepez References
        (34, 3, 2, 2, '2017-01-01 00:00:00', null),
        (35, 3, 4, 2, '2017-01-01 00:00:00', null),
        (36, 3, 5, 2, '2017-01-01 00:00:00', null),

        -- Mario Leon References
        (37, 4, 2, 2, '2017-01-01 00:00:00', null),
        (38, 4, 3, 2, '2017-01-01 00:00:00', null),
        (39, 4, 5, 2, '2017-01-01 00:00:00', null),

        -- Eduardo Herrera References
        (40, 5, 2, 2, '2017-01-01 00:00:00', null),
        (41, 5, 3, 2, '2017-01-01 00:00:00', null),
        (42, 5, 4, 2, '2017-01-01 00:00:00', null),

        -- Mentee 1 References
        (43, 7, 8, 5, '2017-01-01 00:00:00', null),
        (44, 7, 9, 5, '2017-01-01 00:00:00', null),
        (45, 7, 10, 5, '2017-01-01 00:00:00', null),
        (46, 7, 11, 5, '2017-01-01 00:00:00', null),

        -- Mentee 2 References
        (47, 8, 7, 4, '2017-01-01 00:00:00', null),
        (48, 8, 9, 2, '2017-01-01 00:00:00', null),
        (49, 8, 10, 2, '2017-01-01 00:00:00', null),
        (50, 8, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 3 References
        (51, 9, 7, 4, '2017-01-01 00:00:00', null),
        (52, 9, 8, 2, '2017-01-01 00:00:00', null),
        (53, 9, 10, 2, '2017-01-01 00:00:00', null),
        (54, 9, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 4 References
        (55, 10, 7, 4, '2017-01-01 00:00:00', null),
        (56, 10, 8, 2, '2017-01-01 00:00:00', null),
        (57, 10, 9, 2, '2017-01-01 00:00:00', null),
        (58, 10, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 5 References
        (59, 11, 7, 4, '2017-01-01 00:00:00', null),
        (60, 11, 8, 2, '2017-01-01 00:00:00', null),
        (61, 11, 9, 2, '2017-01-01 00:00:00', null),
        (62, 11, 10, 2, '2017-01-01 00:00:00', null),

        -- Mentee 6 References
        (63, 12, 8, 5, '2017-01-01 00:00:00', null),
        (64, 12, 9, 5, '2017-01-01 00:00:00', null),
        (65, 12, 10, 5, '2017-01-01 00:00:00', null),
        (66, 12, 11, 5, '2017-01-01 00:00:00', null),

        -- Mentee 7 References
        (67, 13, 7, 4, '2017-01-01 00:00:00', null),
        (68, 13, 9, 2, '2017-01-01 00:00:00', null),
        (69, 13, 10, 2, '2017-01-01 00:00:00', null),
        (70, 13, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 8 References
        (71, 14, 7, 4, '2017-01-01 00:00:00', null),
        (72, 14, 8, 2, '2017-01-01 00:00:00', null),
        (73, 14, 10, 2, '2017-01-01 00:00:00', null),
        (74, 14, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 9 References
        (75, 15, 7, 4, '2017-01-01 00:00:00', null),
        (76, 15, 8, 2, '2017-01-01 00:00:00', null),
        (77, 15, 9, 2, '2017-01-01 00:00:00', null),
        (78, 15, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 10 References
        (79, 16, 7, 4, '2017-01-01 00:00:00', null),
        (80, 16, 8, 2, '2017-01-01 00:00:00', null),
        (81, 16, 9, 2, '2017-01-01 00:00:00', null),
        (82, 16, 10, 2, '2017-01-01 00:00:00', null),

        -- Mentee 11 References
        (83, 17, 8, 5, '2017-01-01 00:00:00', null),
        (84, 17, 9, 5, '2017-01-01 00:00:00', null),
        (85, 17, 10, 5, '2017-01-01 00:00:00', null),
        (86, 17, 11, 5, '2017-01-01 00:00:00', null),

        -- Mentee 12 References
        (87, 18, 7, 4, '2017-01-01 00:00:00', null),
        (88, 18, 9, 2, '2017-01-01 00:00:00', null),
        (89, 18, 10, 2, '2017-01-01 00:00:00', null),
        (90, 18, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 13 References
        (91, 19, 7, 4, '2017-01-01 00:00:00', null),
        (92, 19, 8, 2, '2017-01-01 00:00:00', null),
        (93, 19, 10, 2, '2017-01-01 00:00:00', null),
        (94, 19, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 14 References
        (95, 20, 7, 4, '2017-01-01 00:00:00', null),
        (96, 20, 8, 2, '2017-01-01 00:00:00', null),
        (97, 20, 9, 2, '2017-01-01 00:00:00', null),
        (98, 20, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 15 References
        (99, 21, 7, 4, '2017-01-01 00:00:00', null),
        (100, 21, 8, 2, '2017-01-01 00:00:00', null),
        (101, 21, 9, 2, '2017-01-01 00:00:00', null),
        (102, 21, 10, 2, '2017-01-01 00:00:00', null),

        -- Mentee 16 References
        (103, 22, 8, 5, '2017-01-01 00:00:00', null),
        (104, 22, 9, 5, '2017-01-01 00:00:00', null),
        (105, 22, 10, 5, '2017-01-01 00:00:00', null),
        (106, 22, 11, 5, '2017-01-01 00:00:00', null),

        -- Mentee 17 References
        (107, 23, 7, 4, '2017-01-01 00:00:00', null),
        (108, 23, 9, 2, '2017-01-01 00:00:00', null),
        (109, 23, 10, 2, '2017-01-01 00:00:00', null),
        (110, 23, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 18 References
        (111, 24, 7, 4, '2017-01-01 00:00:00', null),
        (112, 24, 8, 2, '2017-01-01 00:00:00', null),
        (113, 24, 10, 2, '2017-01-01 00:00:00', null),
        (114, 24, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 19 References
        (115, 25, 7, 4, '2017-01-01 00:00:00', null),
        (116, 25, 8, 2, '2017-01-01 00:00:00', null),
        (117, 25, 9, 2, '2017-01-01 00:00:00', null),
        (118, 25, 11, 2, '2017-01-01 00:00:00', null),

        -- Mentee 20 References
        (119, 26, 7, 4, '2017-01-01 00:00:00', null),
        (120, 26, 8, 2, '2017-01-01 00:00:00', null),
        (121, 26, 9, 2, '2017-01-01 00:00:00', null),
        (122, 26, 10, 2, '2017-01-01 00:00:00', null);
