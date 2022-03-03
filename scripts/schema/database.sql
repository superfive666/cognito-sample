-- create the database with the owner being postgres
-- encoding UTF-8
-- unlimited connection concurrently
create database opms with
    owner = postgres
    encoding = 'UTF8'
    connection limit = -1;

-- create a blank schema for the application
-- NOTE: granting of the schema privileges will be on individual DB users
create schema if not exists opms;

-- create app access role
-- read only role
create role opms_qry_role;
grant usage on schema opms to opms_qry_role;
-- normal CRUD access: insert, update, delete ONLY
create role opms_crud_role;
grant usage on schema opms to opms_crud_role;

-- create DB application user(s)
create user opms_app_user with
    password 'P@ssw0rd!'
    valid until '2022-12-31'
    -- additional direct access grant (not advised)
    -- createdb createuser createrole
    ;
grant usage on schema opms to opms_app_user;
grant opms_qry_role to opms_app_user;
grant opms_crud_role to opms_app_user;

-- create additional users (developers, readonly users, etc.)

-- Database objects: table, view, trigger, sequence, etc will be included in individual schema related change files
-- Usage/Access on db objects will be granted specific upon object creation to role only (not advised to grant on specific user)