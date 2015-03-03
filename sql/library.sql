--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: glassfish; Tablespace: 
--

CREATE TABLE categories (
    cat_pcode integer NOT NULL,
    cat_name character varying(128) NOT NULL
);


ALTER TABLE public.categories OWNER TO glassfish;

--
-- Name: categories_cat_pcode_seq; Type: SEQUENCE; Schema: public; Owner: glassfish
--

CREATE SEQUENCE categories_cat_pcode_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categories_cat_pcode_seq OWNER TO glassfish;

--
-- Name: categories_cat_pcode_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: glassfish
--

ALTER SEQUENCE categories_cat_pcode_seq OWNED BY categories.cat_pcode;


--
-- Name: publications; Type: TABLE; Schema: public; Owner: glassfish; Tablespace: 
--

CREATE TABLE publications (
    pub_pcode integer NOT NULL,
    pub_name character varying(128) NOT NULL,
    pub_description character varying(255) NOT NULL,
    pub_catcode integer NOT NULL,
    pub_usrcode integer NOT NULL,
    pub_data bytea NOT NULL,
    pub_contenttype character varying(128) NOT NULL,
    pub_filename character varying(255) DEFAULT 'filename'::character varying NOT NULL
);


ALTER TABLE public.publications OWNER TO glassfish;

--
-- Name: publications_pub_pcode_seq; Type: SEQUENCE; Schema: public; Owner: glassfish
--

CREATE SEQUENCE publications_pub_pcode_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.publications_pub_pcode_seq OWNER TO glassfish;

--
-- Name: publications_pub_pcode_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: glassfish
--

ALTER SEQUENCE publications_pub_pcode_seq OWNED BY publications.pub_pcode;


--
-- Name: users; Type: TABLE; Schema: public; Owner: glassfish; Tablespace: 
--

CREATE TABLE users (
    usr_pcode integer NOT NULL,
    usr_fullname character varying(128) NOT NULL,
    usr_login character varying(50) NOT NULL,
    usr_password character varying(128) NOT NULL,
    usr_admin boolean NOT NULL
);


ALTER TABLE public.users OWNER TO glassfish;

--
-- Name: users_usr_pcode_seq; Type: SEQUENCE; Schema: public; Owner: glassfish
--

CREATE SEQUENCE users_usr_pcode_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_usr_pcode_seq OWNER TO glassfish;

--
-- Name: users_usr_pcode_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: glassfish
--

ALTER SEQUENCE users_usr_pcode_seq OWNED BY users.usr_pcode;


--
-- Name: cat_pcode; Type: DEFAULT; Schema: public; Owner: glassfish
--

ALTER TABLE ONLY categories ALTER COLUMN cat_pcode SET DEFAULT nextval('categories_cat_pcode_seq'::regclass);


--
-- Name: pub_pcode; Type: DEFAULT; Schema: public; Owner: glassfish
--

ALTER TABLE ONLY publications ALTER COLUMN pub_pcode SET DEFAULT nextval('publications_pub_pcode_seq'::regclass);


--
-- Name: usr_pcode; Type: DEFAULT; Schema: public; Owner: glassfish
--

ALTER TABLE ONLY users ALTER COLUMN usr_pcode SET DEFAULT nextval('users_usr_pcode_seq'::regclass);


--
-- Name: categories_pk; Type: CONSTRAINT; Schema: public; Owner: glassfish; Tablespace: 
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT categories_pk PRIMARY KEY (cat_pcode);


--
-- Name: publications_pk; Type: CONSTRAINT; Schema: public; Owner: glassfish; Tablespace: 
--

ALTER TABLE ONLY publications
    ADD CONSTRAINT publications_pk PRIMARY KEY (pub_pcode);


--
-- Name: users_pk; Type: CONSTRAINT; Schema: public; Owner: glassfish; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pk PRIMARY KEY (usr_pcode);


--
-- Name: publications_categories_fk; Type: FK CONSTRAINT; Schema: public; Owner: glassfish
--

ALTER TABLE ONLY publications
    ADD CONSTRAINT publications_categories_fk FOREIGN KEY (pub_catcode) REFERENCES categories(cat_pcode) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: publications_users_fk; Type: FK CONSTRAINT; Schema: public; Owner: glassfish
--

ALTER TABLE ONLY publications
    ADD CONSTRAINT publications_users_fk FOREIGN KEY (pub_usrcode) REFERENCES users(usr_pcode) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

