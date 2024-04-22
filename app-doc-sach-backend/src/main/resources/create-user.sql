-- Active: 1703168100323@@127.0.0.1@3306@webdoctruyen

-- 127.0.0.1:3306
-- Database: appdocsach

-- Create user 'appdocsach' with all privileges;
CREATE USER 'appdocsach' @'%' IDENTIFIED BY 'password';
-- Grant select privilege to all databases;
GRANT SELECT ON *.* TO 'appdocsach' @'%' WITH GRANT OPTION;
-- Grant all privileges to all databases;
GRANT ALL PRIVILEGES ON *.* TO 'appdocsach' @'%' WITH GRANT OPTION;

-- SELECT * FROM `user` WHERE username = 'admin';

-- DROP DATABASE appdocsach;