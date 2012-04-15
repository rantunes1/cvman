CREATE TABLE PROFESSIONALINFO_PUBLICATION (ProfessionalInfo_ID BIGINT NOT NULL, publications_ID BIGINT NOT NULL, PRIMARY KEY (ProfessionalInfo_ID, publications_ID))
CREATE TABLE PROFESSIONALINFO$CERTIFICATION$ISSUINGAUTHORITY (ID BIGINT AUTO_INCREMENT NOT NULL, NAME VARCHAR(255), VERSION BIGINT, LOCATION_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE PERSONNAME (ID BIGINT AUTO_INCREMENT NOT NULL, SURNAME VARCHAR(255), FIRSTNAME VARCHAR(255), VERSION BIGINT, PRIMARY KEY (ID))
CREATE TABLE PERSONALINFO (ID BIGINT AUTO_INCREMENT NOT NULL, PICTURE LONGBLOB, CITIZENSHIPCOUNTRY INTEGER, GENDER INTEGER, MARITALSTATUS INTEGER, PRIMARYLANGUAGE INTEGER, VERSION BIGINT, MOTHERNAME_ID BIGINT, NAME_ID BIGINT, FATHERNAME_ID BIGINT, BIRTHINFO_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE ROLE (ID BIGINT AUTO_INCREMENT NOT NULL, NAME VARCHAR(255), VERSION BIGINT, PRIMARY KEY (ID))
CREATE TABLE ABSTRACTPUBLICATIONINFO (ID BIGINT AUTO_INCREMENT NOT NULL, DTYPE VARCHAR(255), TITLE VARCHAR(255), ABSTRACTTEXT VARCHAR(255), ISSN VARCHAR(255), DATE DATE, COMMENT VARCHAR(255), VERSION BIGINT, COPYRIGHT_ID BIGINT, EVENTDATE DATE, EVENTLOCATIONNAME VARCHAR(255), EVENTNAME VARCHAR(255), EVENTLOCATION_ID BIGINT, PUBLISHERNAME VARCHAR(255), ISBN VARCHAR(255), NUMBEROFPAGES INTEGER, PUBLISHERLOCATION_ID BIGINT, EDITION VARCHAR(255), CHAPTER VARCHAR(255), PAGENUMBERS VARCHAR(255), ISSUE VARCHAR(255), VOLUME VARCHAR(255), LANGUAGE INTEGER, JOURNALNAME VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE PROFESSIONALINFO$CERTIFICATION (ID BIGINT AUTO_INCREMENT NOT NULL, NAME VARCHAR(255), ISSUEDATE DATE, VERSION BIGINT, ISSUINGAUTHORITY_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE PERSONALINFO_PERSONCONTACTS (PersonalInfo_ID BIGINT NOT NULL, contacts_ID BIGINT NOT NULL, PRIMARY KEY (PersonalInfo_ID, contacts_ID))
CREATE TABLE PERSONALINFO$BIRTHINFO (ID BIGINT AUTO_INCREMENT NOT NULL, BIRTHDATE DATE, VERSION BIGINT, BIRTHLOCATION_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE person_info_professional (person_id BIGINT NOT NULL, profissional_info_id BIGINT NOT NULL, PRIMARY KEY (person_id, profissional_info_id))
CREATE TABLE PROFESSIONALINFO_PROFESSIONALINFO$EMPLOYER (ProfessionalInfo_ID BIGINT NOT NULL, employers_ID BIGINT NOT NULL, PRIMARY KEY (ProfessionalInfo_ID, employers_ID))
CREATE TABLE PROFESSIONALINFO$COPYRIGHT (ID BIGINT AUTO_INCREMENT NOT NULL, TEXT VARCHAR(255), YEAR VARCHAR(255), VERSION BIGINT, PRIMARY KEY (ID))
CREATE TABLE PROFESSIONALINFO$EMPLOYER (ID BIGINT AUTO_INCREMENT NOT NULL, NAME VARCHAR(255), HEADCOUNT VARCHAR(255), URL VARCHAR(255), VERSION BIGINT, EMPLOYMENTPERIOD_ID BIGINT, CONTACTS_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE PROFESSIONALINFO (ID BIGINT AUTO_INCREMENT NOT NULL, EXECUTIVESUMMARY VARCHAR(255), PROFILENAME VARCHAR(255), VERSION BIGINT, PRIMARY KEY (ID))
CREATE TABLE PROFESSIONALINFO$CONTRIBUTOR (ID BIGINT AUTO_INCREMENT NOT NULL, ROLE VARCHAR(255), VERSION BIGINT, NAME_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE ABSTRACTPUBLICATIONINFO_PROFESSIONALINFO$CONTRIBUTOR (AbstractPublicationInfo_ID BIGINT NOT NULL, contributors_ID BIGINT NOT NULL, PRIMARY KEY (AbstractPublicationInfo_ID, contributors_ID))
CREATE TABLE user_roles (user_id BIGINT NOT NULL, role_id BIGINT NOT NULL, PRIMARY KEY (user_id, role_id))
CREATE TABLE PROFESSIONALINFO_PROFESSIONALINFO$CERTIFICATION (ProfessionalInfo_ID BIGINT NOT NULL, certifications_ID BIGINT NOT NULL, PRIMARY KEY (ProfessionalInfo_ID, certifications_ID))
CREATE TABLE PROFESSIONALINFO$EMPLOYER_PROFESSIONALINFO$POSITION (ProfessionalInfo$Employer_ID BIGINT NOT NULL, positions_ID BIGINT NOT NULL, PRIMARY KEY (ProfessionalInfo$Employer_ID, positions_ID))
CREATE TABLE PROFESSIONALINFO$POSITION (ID BIGINT AUTO_INCREMENT NOT NULL, TITLE VARCHAR(255), RELATIONSHIPTYPE INTEGER, DESCRIPTION VARCHAR(255), UNITNAME VARCHAR(255), VERSION BIGINT, EMPLOYMENTPERIOD_ID BIGINT, ADDRESS_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE PERSONCONTACTS$EMAIL (ID BIGINT AUTO_INCREMENT NOT NULL, EMAILADDRESS VARCHAR(255), VERSION BIGINT, PRIMARY KEY (ID))
CREATE TABLE USERCONNECTION (ID BIGINT AUTO_INCREMENT NOT NULL, USERID BIGINT, PROVIDERID VARCHAR(255), PROVIDERUSERID VARCHAR(255), VERSION BIGINT, PRIMARY KEY (ID))
CREATE TABLE PERSONCONTACTS (ID BIGINT AUTO_INCREMENT NOT NULL, DTYPE VARCHAR(31), CONTACTSPROFILE INTEGER NOT NULL, VERSION BIGINT, MOBILEPHONE_ID BIGINT, ADDRESS_ID BIGINT, TELEPHONE_ID BIGINT, EMAIL_ID BIGINT, ROLE VARCHAR(255), NAME_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE LOCATION (ID BIGINT AUTO_INCREMENT NOT NULL, DTYPE VARCHAR(31), POSTALCODE VARCHAR(255), COUNTRYDIVISION VARCHAR(255), COUNTRY INTEGER, CITY VARCHAR(255), VERSION BIGINT, BUILDINGNAME VARCHAR(255), FLOOR VARCHAR(255), STREETNAME VARCHAR(255), POSTOFFICEBOX VARCHAR(255), BUILDINGNUMBER VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE PERSONCONTACTS$PHONECHANNEL (ID BIGINT AUTO_INCREMENT NOT NULL, DTYPE VARCHAR(31), COUNTRYDIALING VARCHAR(255), DIALNUMBER VARCHAR(255), VERSION BIGINT, PRIMARY KEY (ID))
CREATE TABLE CVUSER (ID BIGINT AUTO_INCREMENT NOT NULL, USERNAME VARCHAR(255), EMAIL VARCHAR(255), NAME VARCHAR(255), COMPANY VARCHAR(255), COUNTRYCODE VARCHAR(255), MOBILENUMBER VARCHAR(255), PASSWORD VARCHAR(255), COUNTRY VARCHAR(255), USERTYPE INTEGER, VERSION BIGINT, PRIMARY KEY (ID))
CREATE TABLE person_info_personal (person_id BIGINT NOT NULL, personal_info_id BIGINT NOT NULL, PRIMARY KEY (person_id, personal_info_id))
CREATE TABLE PERSON (ID BIGINT AUTO_INCREMENT NOT NULL, DTYPE VARCHAR(31), USERID BIGINT, LANGUAGEID INTEGER, VERSION BIGINT, PRIMARY KEY (ID))
CREATE TABLE PUBLICATION (ID BIGINT AUTO_INCREMENT NOT NULL, DESCRIPTION VARCHAR(255), TYPE INTEGER NOT NULL, VERSION BIGINT, INFO_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE PROFESSIONALINFO$TIMEPERIOD (ID BIGINT AUTO_INCREMENT NOT NULL, STARTDATE DATE, ENDDATE DATE, VERSION BIGINT, PRIMARY KEY (ID))
ALTER TABLE PROFESSIONALINFO_PUBLICATION ADD CONSTRAINT PROFESSIONALINFO_PUBLICATION_ProfessionalInfo_ID FOREIGN KEY (ProfessionalInfo_ID) REFERENCES PROFESSIONALINFO (ID)
ALTER TABLE PROFESSIONALINFO_PUBLICATION ADD CONSTRAINT FK_PROFESSIONALINFO_PUBLICATION_publications_ID FOREIGN KEY (publications_ID) REFERENCES PUBLICATION (ID)
ALTER TABLE PROFESSIONALINFO$CERTIFICATION$ISSUINGAUTHORITY ADD CONSTRAINT PRFSSIONALINFOCERTIFICATIONISSUINGAUTHORITYLCTONID FOREIGN KEY (LOCATION_ID) REFERENCES LOCATION (ID)
ALTER TABLE PERSONALINFO ADD CONSTRAINT FK_PERSONALINFO_NAME_ID FOREIGN KEY (NAME_ID) REFERENCES PERSONNAME (ID)
ALTER TABLE PERSONALINFO ADD CONSTRAINT FK_PERSONALINFO_BIRTHINFO_ID FOREIGN KEY (BIRTHINFO_ID) REFERENCES PERSONALINFO$BIRTHINFO (ID)
ALTER TABLE PERSONALINFO ADD CONSTRAINT FK_PERSONALINFO_FATHERNAME_ID FOREIGN KEY (FATHERNAME_ID) REFERENCES PERSONNAME (ID)
ALTER TABLE PERSONALINFO ADD CONSTRAINT FK_PERSONALINFO_MOTHERNAME_ID FOREIGN KEY (MOTHERNAME_ID) REFERENCES PERSONNAME (ID)
ALTER TABLE ABSTRACTPUBLICATIONINFO ADD CONSTRAINT FK_ABSTRACTPUBLICATIONINFO_EVENTLOCATION_ID FOREIGN KEY (EVENTLOCATION_ID) REFERENCES LOCATION (ID)
ALTER TABLE ABSTRACTPUBLICATIONINFO ADD CONSTRAINT FK_ABSTRACTPUBLICATIONINFO_PUBLISHERLOCATION_ID FOREIGN KEY (PUBLISHERLOCATION_ID) REFERENCES LOCATION (ID)
ALTER TABLE ABSTRACTPUBLICATIONINFO ADD CONSTRAINT FK_ABSTRACTPUBLICATIONINFO_COPYRIGHT_ID FOREIGN KEY (COPYRIGHT_ID) REFERENCES PROFESSIONALINFO$COPYRIGHT (ID)
ALTER TABLE PROFESSIONALINFO$CERTIFICATION ADD CONSTRAINT PROFESSIONALINFO$CERTIFICATION_ISSUINGAUTHORITY_ID FOREIGN KEY (ISSUINGAUTHORITY_ID) REFERENCES PROFESSIONALINFO$CERTIFICATION$ISSUINGAUTHORITY (ID)
ALTER TABLE PERSONALINFO_PERSONCONTACTS ADD CONSTRAINT FK_PERSONALINFO_PERSONCONTACTS_contacts_ID FOREIGN KEY (contacts_ID) REFERENCES PERSONCONTACTS (ID)
ALTER TABLE PERSONALINFO_PERSONCONTACTS ADD CONSTRAINT FK_PERSONALINFO_PERSONCONTACTS_PersonalInfo_ID FOREIGN KEY (PersonalInfo_ID) REFERENCES PERSONALINFO (ID)
ALTER TABLE PERSONALINFO$BIRTHINFO ADD CONSTRAINT FK_PERSONALINFO$BIRTHINFO_BIRTHLOCATION_ID FOREIGN KEY (BIRTHLOCATION_ID) REFERENCES LOCATION (ID)
ALTER TABLE person_info_professional ADD CONSTRAINT FK_person_info_professional_profissional_info_id FOREIGN KEY (profissional_info_id) REFERENCES PROFESSIONALINFO (ID)
ALTER TABLE person_info_professional ADD CONSTRAINT FK_person_info_professional_person_id FOREIGN KEY (person_id) REFERENCES PERSON (ID)
ALTER TABLE PROFESSIONALINFO_PROFESSIONALINFO$EMPLOYER ADD CONSTRAINT PROFESSIONALINFOPROFESSIONALINFOEMPLOYERmployersID FOREIGN KEY (employers_ID) REFERENCES PROFESSIONALINFO$EMPLOYER (ID)
ALTER TABLE PROFESSIONALINFO_PROFESSIONALINFO$EMPLOYER ADD CONSTRAINT PRFSSNALINFOPROFESSIONALINFOEMPLOYERPrfssnalInfoID FOREIGN KEY (ProfessionalInfo_ID) REFERENCES PROFESSIONALINFO (ID)
ALTER TABLE PROFESSIONALINFO$EMPLOYER ADD CONSTRAINT FK_PROFESSIONALINFO$EMPLOYER_EMPLOYMENTPERIOD_ID FOREIGN KEY (EMPLOYMENTPERIOD_ID) REFERENCES PROFESSIONALINFO$TIMEPERIOD (ID)
ALTER TABLE PROFESSIONALINFO$EMPLOYER ADD CONSTRAINT FK_PROFESSIONALINFO$EMPLOYER_CONTACTS_ID FOREIGN KEY (CONTACTS_ID) REFERENCES PERSONCONTACTS (ID)
ALTER TABLE PROFESSIONALINFO$CONTRIBUTOR ADD CONSTRAINT FK_PROFESSIONALINFO$CONTRIBUTOR_NAME_ID FOREIGN KEY (NAME_ID) REFERENCES PERSONNAME (ID)
ALTER TABLE ABSTRACTPUBLICATIONINFO_PROFESSIONALINFO$CONTRIBUTOR ADD CONSTRAINT BSTRCTPBLCTNNFPROFESSIONALINFOCONTRIBUTORcntrbtrsD FOREIGN KEY (contributors_ID) REFERENCES PROFESSIONALINFO$CONTRIBUTOR (ID)
ALTER TABLE ABSTRACTPUBLICATIONINFO_PROFESSIONALINFO$CONTRIBUTOR ADD CONSTRAINT BSTRCTPBLCTNNFPRFSSNLNFOCONTRIBUTORbstrctPblctnnfD FOREIGN KEY (AbstractPublicationInfo_ID) REFERENCES ABSTRACTPUBLICATIONINFO (ID)
ALTER TABLE user_roles ADD CONSTRAINT FK_user_roles_user_id FOREIGN KEY (user_id) REFERENCES CVUSER (ID)
ALTER TABLE user_roles ADD CONSTRAINT FK_user_roles_role_id FOREIGN KEY (role_id) REFERENCES ROLE (ID)
ALTER TABLE PROFESSIONALINFO_PROFESSIONALINFO$CERTIFICATION ADD CONSTRAINT PRFSSNLNFPROFESSIONALINFOCERTIFICATIONPrfssnlnfoID FOREIGN KEY (ProfessionalInfo_ID) REFERENCES PROFESSIONALINFO (ID)
ALTER TABLE PROFESSIONALINFO_PROFESSIONALINFO$CERTIFICATION ADD CONSTRAINT PRFSSNLINFOPROFESSIONALINFOCERTIFICATIONcrtfctnsID FOREIGN KEY (certifications_ID) REFERENCES PROFESSIONALINFO$CERTIFICATION (ID)
ALTER TABLE PROFESSIONALINFO$EMPLOYER_PROFESSIONALINFO$POSITION ADD CONSTRAINT PRFSSNALINFOEMPLOYERPROFESSIONALINFOPOSITIONpstnsD FOREIGN KEY (positions_ID) REFERENCES PROFESSIONALINFO$POSITION (ID)
ALTER TABLE PROFESSIONALINFO$EMPLOYER_PROFESSIONALINFO$POSITION ADD CONSTRAINT PRFSSNLNFMPLYRPRFSSONALINFOPOSITIONPrfssnlnfmplyrD FOREIGN KEY (ProfessionalInfo$Employer_ID) REFERENCES PROFESSIONALINFO$EMPLOYER (ID)
ALTER TABLE PROFESSIONALINFO$POSITION ADD CONSTRAINT FK_PROFESSIONALINFO$POSITION_EMPLOYMENTPERIOD_ID FOREIGN KEY (EMPLOYMENTPERIOD_ID) REFERENCES PROFESSIONALINFO$TIMEPERIOD (ID)
ALTER TABLE PROFESSIONALINFO$POSITION ADD CONSTRAINT FK_PROFESSIONALINFO$POSITION_ADDRESS_ID FOREIGN KEY (ADDRESS_ID) REFERENCES LOCATION (ID)
ALTER TABLE PERSONCONTACTS ADD CONSTRAINT FK_PERSONCONTACTS_NAME_ID FOREIGN KEY (NAME_ID) REFERENCES PERSONNAME (ID)
ALTER TABLE PERSONCONTACTS ADD CONSTRAINT FK_PERSONCONTACTS_MOBILEPHONE_ID FOREIGN KEY (MOBILEPHONE_ID) REFERENCES PERSONCONTACTS$PHONECHANNEL (ID)
ALTER TABLE PERSONCONTACTS ADD CONSTRAINT FK_PERSONCONTACTS_EMAIL_ID FOREIGN KEY (EMAIL_ID) REFERENCES PERSONCONTACTS$EMAIL (ID)
ALTER TABLE PERSONCONTACTS ADD CONSTRAINT FK_PERSONCONTACTS_TELEPHONE_ID FOREIGN KEY (TELEPHONE_ID) REFERENCES PERSONCONTACTS$PHONECHANNEL (ID)
ALTER TABLE PERSONCONTACTS ADD CONSTRAINT FK_PERSONCONTACTS_ADDRESS_ID FOREIGN KEY (ADDRESS_ID) REFERENCES LOCATION (ID)
ALTER TABLE person_info_personal ADD CONSTRAINT FK_person_info_personal_person_id FOREIGN KEY (person_id) REFERENCES PERSON (ID)
ALTER TABLE person_info_personal ADD CONSTRAINT FK_person_info_personal_personal_info_id FOREIGN KEY (personal_info_id) REFERENCES PERSONALINFO (ID)
ALTER TABLE PUBLICATION ADD CONSTRAINT FK_PUBLICATION_INFO_ID FOREIGN KEY (INFO_ID) REFERENCES ABSTRACTPUBLICATIONINFO (ID)