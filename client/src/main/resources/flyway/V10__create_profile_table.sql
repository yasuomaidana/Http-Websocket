CREATE TABLE profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  profile_picture_url VARCHAR(255),
  FOREIGN KEY (user_id) REFERENCES user(id)
);