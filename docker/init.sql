CREATE SCHEMA creditx_dev;
CREATE SCHEMA creditx_prd;

CREATE TABLE creditx_dev.credit_risk
(

  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  credit_limit DOUBLE PRECISION NOT NULL,
  risk_type VARCHAR(1) NOT NULL,
  interest_percentage DOUBLE PRECISION NOT NULL

);

INSERT INTO creditx_dev.credit_risk (name,credit_limit,risk_type,interest_percentage) VALUES ('Rogerio mineiro', 2000.0,'A',0);
INSERT INTO creditx_dev.credit_risk (name,credit_limit,risk_type,interest_percentage) VALUES ('Marcio Rodrigues', 2334.0,'A',0);
INSERT INTO creditx_dev.credit_risk (name,credit_limit,risk_type,interest_percentage) VALUES ('Fenando test', 34543.0,'A',0);
INSERT INTO creditx_dev.credit_risk (name,credit_limit,risk_type,interest_percentage) VALUES ('Mario ss', 35535.0,'B',10);
INSERT INTO creditx_dev.credit_risk (name,credit_limit,risk_type,interest_percentage) VALUES ('Fernando teixeira', 2000.0,'C',20);

CREATE TABLE creditx_prd.credit_risk
(

  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  credit_limit DOUBLE PRECISION NOT NULL,
  risk_type VARCHAR(1) NOT NULL,
  interest_percentage DOUBLE PRECISION NOT NULL

);

INSERT INTO creditx_prd.credit_risk (name,credit_limit,risk_type,interest_percentage) VALUES ('Rogerio mineiro', 2000.0,'A',0);
INSERT INTO creditx_prd.credit_risk (name,credit_limit,risk_type,interest_percentage) VALUES ('otavio Rodrigues', 2334.0,'A',0);
INSERT INTO creditx_prd.credit_risk (name,credit_limit,risk_type,interest_percentage) VALUES ('Fenando test', 34543.0,'A',0);
INSERT INTO creditx_prd.credit_risk (name,credit_limit,risk_type,interest_percentage) VALUES ('Mario ss', 35535.0,'B',10);
INSERT INTO creditx_prd.credit_risk (name,credit_limit,risk_type,interest_percentage) VALUES ('Fernando teixeira', 2000.0,'C',20);
