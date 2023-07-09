INSERT INTO users (USERNAME, PASSWORD, ENABLED, FIRST_NAME)
VALUES ('user', '$2a$10$QCJXkfCPqQ7oFsJ/mCkzk.D9dpsORC0j0vRFnjy7r3E9t.KaHVuUW', true, 'Jack');
INSERT INTO users (USERNAME, PASSWORD, ENABLED, FIRST_NAME)
VALUES ('admin', '$2a$10$GfKM3Y0Q.5F/DHIEnTs17.dSJzgVvb8bd9tzdysnN9NR0PJ6RbQzW', true, 'Jill');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('user', 'ROLE_USER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('admin', 'ROLE_ADMIN');