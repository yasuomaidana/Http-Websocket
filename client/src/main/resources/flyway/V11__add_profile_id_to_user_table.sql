ALTER TABLE user ADD COLUMN profile_id BIGINT;
ALTER TABLE user ADD FOREIGN KEY (profile_id) REFERENCES profile(id);