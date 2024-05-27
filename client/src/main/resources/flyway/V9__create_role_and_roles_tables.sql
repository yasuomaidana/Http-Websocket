CREATE TABLE role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);
 ALTER TABLE user DROP COLUMN roles;
CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (role_id) REFERENCES role(id)
);