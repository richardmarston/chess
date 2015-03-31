DROP TABLE users IF EXISTS;

CREATE TABLE users (
  id         INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(30),
);
CREATE INDEX name ON users (name);
