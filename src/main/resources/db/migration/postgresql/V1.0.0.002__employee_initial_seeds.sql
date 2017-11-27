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
        (6, 53, 2, 'Ahmet.Naranjo@gft.com', 'Ahmet', 'Naranjo', 'ATNO'),
        (7, 46, 2, 'Allan.Rivera@gft.com', 'Allan', 'Rivera', 'ANRA'),
        (8, 54, 2, 'Bernal.Kou@gft.com', 'Bernal', 'Kou', 'BLKU'),
        (9, 46, 2, 'Cesar.Vezga@gft.com', 'Cesar', 'Vezga', 'CRVA'),
        (10, 46, 2, 'Elias.Segura@gft.com', 'Elías', 'Segura', 'ESSA'),
        (11, 45, 2, 'Erick.Chavarria@gft.com', 'Erick', 'Chavarría', 'EKCA'),
        (12, 45, 2, 'Esteban.Ramirez@gft.com', 'Esteban', 'Ramirez', 'ENRZ'),
        (13, 47, 2, 'Henry.Smith@gft.com', 'Henry'git , 'Smith', 'HYSO'),
        (14, 45, 2, 'Imad.Marmoud@gft.com', 'Imad', 'Marmoud', 'IDMD'),
        (15, 45, 2, 'Fabio.Bermudez@gft.com', 'José Fabio', 'Bermúdez', 'JEBZ'),
        (16, 46, 2, 'Jose.Moraga@gft.com', 'José', 'Moraga', 'JEMG'),
        (17, 45, 2, 'Jose.Jimenez@gft.com', 'José', 'Jiménez', 'JEJZ'),
        (18, 52, 2, 'Juan.Piedra@gft.com', 'Juan', 'Piedra', 'JAPA'),
        (19, 44, 2, 'Katherine.Alfaro@gft.com', 'Katherine', 'Alfaro', 'KEAO'),
        (20, 46, 2, 'Mario.Lopez@gft.com', 'Mario', 'López', 'MOLP'),
        (21, 45, 2, 'Pablo.Gonzalez@gft.com', 'Pablo', 'González', 'POGZ'),
        (22, 45, 2, 'Ricardo.Aguero@gft.com', 'Ricardo', 'Aguero', 'ROAR'),
        (23, 46, 2, 'sebastian.estrada@gft.com', 'Sebastián', 'Estrada', 'SNEA'),
        (24, 55, 2, 'Luberth.Morera@gft.com', 'Luberth', 'Morera', 'LHMA'),
        (25, 45, 1, 'Meenu.Juneja@gft.com', 'Meenu', 'Juneja', 'MUJA'),
        (26, 45, 2, 'Pinky.Agrawal@gft.com', 'Pinky', 'Agrawal', 'PIAL'),
        (27, 23, 2, 'Diana.Campos@gft.com', 'Diana', 'Campos', 'DACS'),
        (28, 24, 2, 'irene.leer@gft.com', 'Irene', 'Leer', 'IELR'),
        (29, 22, 2, 'Sara.Jimenez@gft.com', 'Sara', 'Jimenez', 'SAJI'),
        (30, 24, 2, 'susy.gonzalez@gft.com', 'Susy', 'Gonzalez', 'SYGE'),
        (31, 23, 2, 'adriana.astua@gft.com', 'Adriana', 'Astua', 'ANAU'),
        (32, 25, 2, 'Sussan.Cordero@gft.com', 'Sussan', 'Cordero', 'SNCO');

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
        (1, 1, 1, 1, '2017-01-01 00:00:00-06', null),
        (2, 2, 2, 1, '2017-01-01 00:00:00-06', null),
        (3, 3, 3, 1, '2017-01-01 00:00:00-06', null),
        (4, 4, 4, 1, '2017-01-01 00:00:00-06', null),
        (5, 5, 5, 1, '2017-01-01 00:00:00-06', null),
        (6, 6, 6, 1, '2017-01-01 00:00:00-06', null),
        (7, 7, 7, 1, '2017-01-01 00:00:00-06', null),
        (8, 8, 8, 1, '2017-01-01 00:00:00-06', null),
        (9, 9, 9, 1, '2017-01-01 00:00:00-06', null),
        (10, 10, 10, 1, '2017-01-01 00:00:00-06', null),
        (11, 11, 11, 1, '2017-01-01 00:00:00-06', null),
        (12, 12, 12, 1, '2017-01-01 00:00:00-06', null),
        (13, 13, 13, 1, '2017-01-01 00:00:00-06', null),
        (14, 14, 14, 1, '2017-01-01 00:00:00-06', null),
        (15, 15, 15, 1, '2017-01-01 00:00:00-06', null),
        (16, 16, 16, 1, '2017-01-01 00:00:00-06', null),
        (17, 17, 17, 1, '2017-01-01 00:00:00-06', null),
        (18, 18, 18, 1, '2017-01-01 00:00:00-06', null),
        (19, 19, 19, 1, '2017-01-01 00:00:00-06', null),
        (20, 20, 20, 1, '2017-01-01 00:00:00-06', null),
        (21, 21, 21, 1, '2017-01-01 00:00:00-06', null),
        (22, 22, 22, 1, '2017-01-01 00:00:00-06', null),
        (23, 23, 23, 1, '2017-01-01 00:00:00-06', null),
        (24, 24, 24, 1, '2017-01-01 00:00:00-06', null),

        -- Mario Leon mentees
        (25, 4, 19, 3, '2017-01-01 00:00:00-06', null),
        (26, 4, 9, 3, '2017-01-01 00:00:00-06', null),
        (27, 4, 11, 3, '2017-01-01 00:00:00-06', null),
        (28, 4, 20, 3, '2017-01-01 00:00:00-06', null),
        (29, 4, 22, 3, '2017-01-01 00:00:00-06', null),

        -- Eduardo Herrera mentees
        (30, 5, 7, 3, '2017-01-01 00:00:00-06', null),
        (31, 5, 14, 3, '2017-01-01 00:00:00-06', null),
        (32, 5, 15, 3, '2017-01-01 00:00:00-06', null),
        (33, 5, 21, 3, '2017-01-01 00:00:00-06', null),
        (34, 5, 23, 3, '2017-01-01 00:00:00-06', null),
      
        -- Bernal Kou mentees
        (35, 8, 10, 3, '2017-01-01 00:00:00-06', null),
        (36, 8, 12, 3, '2017-01-01 00:00:00-06', null),
        (37, 8, 13, 3, '2017-01-01 00:00:00-06', null),
        (38, 8, 16, 3, '2017-01-01 00:00:00-06', null),
      
        -- Ahmet Naranjo mentees
        (39, 6, 17, 3, '2017-01-01 00:00:00-06', null),
        (40, 6, 18, 3, '2017-01-01 00:00:00-06', null),

        -- Luberth Morera mentees
        (41, 24, 8, 3, '2017-01-01 00:00:00-06', null),
        (42, 24, 4, 3, '2017-01-01 00:00:00-06', null),
        (43, 24, 5, 3, '2017-01-01 00:00:00-06', null),

         -- Manuel Yepez mentees
        (44, 3, 6, 3, '2017-01-01 00:00:00-06', null),
        (45, 6, 24, 2, '2017-01-01 00:00:00-06', null),

        -- Meenu Changes for self and mentee
        (46, 25, 25, 1, '2017-01-01 00:00:00-06', null),
        (47, 26, 26, 1, '2017-01-01 00:00:00-06', null),
        (48, 25, 26, 3, '2017-01-01 00:00:00-06', null),

        -- Meenu Changes for references
         (49, 25, 11, 2, '2017-01-01 00:00:00-06', null),
         (50, 25, 20, 4, '2017-01-01 00:00:00-06', null),

        -- Eduardo Changes for references
         (51, 5, 7, 2, '2017-01-01 00:00:00-06', null),
         (52, 5, 21, 4, '2017-01-01 00:00:00-06', null),
        -- Pinky Changes for mentee
         (53, 26, 3, 3, '2017-01-01 00:00:00-06', null),

         -- Pinky Changes for references
         (54, 26, 12, 2, '2017-01-01 00:00:00-06', null),
         (55, 26, 13, 4, '2017-01-01 00:00:00-06', null),

         -- Sussan Changes for mentee
        (56, 32, 27, 3, '2017-01-01 00:00:00-06', null),
        (57, 32, 28, 3, '2017-01-01 00:00:00-06', null),
        (58, 32, 29, 3, '2017-01-01 00:00:00-06', null),
        (59, 32, 30, 3, '2017-01-01 00:00:00-06', null),
        (60, 32, 31, 3, '2017-01-01 00:00:00-06', null);

