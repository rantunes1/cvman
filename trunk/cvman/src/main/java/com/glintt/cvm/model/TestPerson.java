package com.glintt.cvm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import org.hr_xml._3.CountryCodeEnumType;
import org.hr_xml._3.GenderCodeEnumType;
import org.hr_xml._3.LanguageCodeEnumType;
import org.hr_xml._3.MaritalStatusCodeEnumType;

import com.glintt.cvm.model.PersonalInfo.BirthInfo;
import com.glintt.cvm.model.PersonalInfo.UserProfile;
import com.glintt.cvm.model.PersonalInfo.UserProfile.Email;
import com.glintt.cvm.model.PersonalInfo.UserProfile.MobilePhone;
import com.glintt.cvm.model.PersonalInfo.UserProfile.Telephone;
import com.glintt.cvm.model.PersonalInfo.UserProfileCode;
import com.glintt.cvm.model.ProfessionalInfo.Certification;
import com.glintt.cvm.model.ProfessionalInfo.Certification.IssuingAuthority;
import com.glintt.cvm.model.ProfessionalInfo.Contributor;
import com.glintt.cvm.model.ProfessionalInfo.Copyright;
import com.glintt.cvm.model.ProfessionalInfo.Publication;
import com.glintt.cvm.model.ProfessionalInfo.Publication.Article;
import com.glintt.cvm.model.ProfessionalInfo.Publication.Book;
import com.glintt.cvm.model.ProfessionalInfo.Publication.ConferencePaper;
import com.glintt.cvm.model.ProfessionalInfo.Publication.UnspecifiedPublication;
import com.glintt.cvm.model.ProfessionalInfo.PublicationProfileCode;

@Entity
public class TestPerson extends Person {
    private static final long serialVersionUID = 8579660817063564713L;

    public TestPerson() {
        super(LanguageCodeEnumType.PT);
        PersonalInfo personalInfo = new PersonalInfo();

        personalInfo.setName(new PersonName("João", "Oliveira Silva"));
        personalInfo.setFatherName(new PersonName("António", "Silva"));
        personalInfo.setMotherName(new PersonName("Maria", "Oliveira"));

        personalInfo.setGender(GenderCodeEnumType.MALE);
        personalInfo.setMaritalStatus(MaritalStatusCodeEnumType.MARRIED);
        personalInfo.setCitizenshipCountry(CountryCodeEnumType.PT);
        personalInfo.setPrimaryLanguage(LanguageCodeEnumType.PT);

        BirthInfo birthInfo = new PersonalInfo.BirthInfo();
        Location birthLocation = new Location();
        birthInfo.setBirthDate("01-01-1970");
        birthLocation.setCountry(CountryCodeEnumType.PT);
        birthLocation.setCountryDivision("Sintra");
        birthLocation.setCity("Queluz");
        birthInfo.setBirthLocation(birthLocation);
        personalInfo.setBirthInfo(birthInfo);

        UserProfile personalProfile = new PersonalInfo.UserProfile(UserProfileCode.PERSONAL);
        Address personalAddress = new Address();
        personalAddress.setStreetName("Rua da Palma");
        personalAddress.setBuildingName("C.C. Martim Moniz");
        personalAddress.setBuildingNumber("N.º 2");
        personalAddress.setFloor("Loja 3");
        personalAddress.setCity("Lisboa");
        personalAddress.setCountry(CountryCodeEnumType.PT);
        personalAddress.setPostalCode("1100-111");
        personalAddress.setPostOfficeBox("LISBOA");
        personalProfile.setAddress(personalAddress);
        Email personalEmail = new PersonalInfo.UserProfile.Email();
        personalEmail.setEmailAddress("joao.silva@somedomain.net");
        personalProfile.setEmail(personalEmail);
        MobilePhone personalMobile = new PersonalInfo.UserProfile.MobilePhone();
        personalMobile.setCountryDialing("351");
        personalMobile.setDialNumber("911 111 111");
        personalProfile.setMobilePhone(personalMobile);
        personalInfo.addProfile(personalProfile);
        Telephone personalTelephone = new PersonalInfo.UserProfile.Telephone();
        personalTelephone.setCountryDialing("351");
        personalTelephone.setDialNumber("212 345 678");
        personalProfile.setTelephone(personalTelephone);

        UserProfile businessProfile = new PersonalInfo.UserProfile(UserProfileCode.BUSINESS);
        MobilePhone businessMobile = new PersonalInfo.UserProfile.MobilePhone();
        businessMobile.setCountryDialing("351");
        businessMobile.setDialNumber("922 222 222");
        businessProfile.setMobilePhone(businessMobile);
        personalInfo.addProfile(businessProfile);

        ProfessionalInfo professionalInfo = new ProfessionalInfo();

        professionalInfo.setProfileName("Sample CV generated from curricula WebApp");
        professionalInfo.setExecutiveSummary("executive summary text...");

        List<Certification> certifications = new ArrayList<Certification>();
        Certification certification1 = new Certification();
        certification1.setName("nome da certificação 1");
        certification1.setIssueDate(new Date());
        IssuingAuthority ia1 = new IssuingAuthority();
        ia1.setName("issuing authority 1");
        ia1.setCountry(CountryCodeEnumType.PT);
        ia1.setCity("Lisboa");
        ia1.setPostalCode("1100-002 LISBOA");
        certification1.setIssuingAuthority(ia1);
        certifications.add(certification1);
        Certification certification2 = new Certification();
        certification2.setName("nome da certificação 2");
        IssuingAuthority ia2 = new IssuingAuthority();
        ia2.setName("issuing authority 2");
        ia2.setCountry(CountryCodeEnumType.ES);
        ia2.setCity("Madrid");
        certification2.setIssuingAuthority(ia2);
        certifications.add(certification2);
        professionalInfo.setCertifications(certifications);

        List<Publication> publications = new ArrayList<Publication>();
        Copyright exampleCopyright = new Copyright();
        exampleCopyright.setYear("2011");
        exampleCopyright.setText("all rights reserved");
        List<Contributor> exampleContributors = new ArrayList<Contributor>();
        Contributor contributor1 = new Contributor();
        contributor1.setName(new PersonName("Herman", "José"));
        contributor1.setRole("co-author");
        exampleContributors.add(contributor1);
        Contributor contributor2 = new Contributor();
        contributor2.setName(new PersonName("Camilo", "de Oliveira"));
        contributor2.setRole("ilustrador");
        exampleContributors.add(contributor2);
        Article articleContent = new Article();
        articleContent.setTitle("article title");
        articleContent.setDate(new Date());
        articleContent.setAbstractText("abstract text of article");
        articleContent.setComment("comment for the article");
        articleContent.setIssn("1534-0481");
        articleContent.setCopyright(exampleCopyright);
        articleContent.setContributors(exampleContributors);
        articleContent.setJournalName("Diário de Notícias");
        articleContent.setVolume("III");
        articleContent.setIssue("343/2011");
        articleContent.setPageNumbers("1-3, 12, 48-51");
        articleContent.setLanguage(LanguageCodeEnumType.FR);
        Publication article = new Publication(PublicationProfileCode.ARTICLE, articleContent, "article description");
        publications.add(article);
        publications.add(new Publication(PublicationProfileCode.ARTICLE, null));
        publications.add(new Publication(PublicationProfileCode.ARTICLE, new Article()));
        UnspecifiedPublication otherContent = new UnspecifiedPublication();
        otherContent.setTitle("unspecified title");
        otherContent.setDate(new Date());
        otherContent.setAbstractText("abstract text of unspecified");
        otherContent.setComment("comment for the unspecified");
        otherContent.setIssn("1234-5678");
        otherContent.setIsbn("1234567890");
        otherContent.setNumberOfPages(34);
        otherContent.setPublisherName("editor para unspecified");
        Location exampleLocation = new Location();
        exampleLocation.setCountry(CountryCodeEnumType.PT);
        exampleLocation.setCity("Lisboa");
        otherContent.setPublisherLocation(exampleLocation);
        Publication other = new Publication(PublicationProfileCode.UNSPECIFIED, otherContent);
        publications.add(other);
        publications.add(new Publication(PublicationProfileCode.UNSPECIFIED, null));
        publications.add(new Publication(PublicationProfileCode.UNSPECIFIED, new UnspecifiedPublication()));
        professionalInfo.setPublications(publications);
        Book bookContent = new Book();
        bookContent.setTitle("book title");
        bookContent.setDate(new Date());
        bookContent.setAbstractText("abstract text of book");
        bookContent.setComment("comment for the book");
        bookContent.setIssn("1234-5678");
        bookContent.setIsbn("1234567890");
        bookContent.setEdition("1st.");
        bookContent.setChapter("4");
        Publication book = new Publication(PublicationProfileCode.BOOK, bookContent, "book description");
        publications.add(book);
        publications.add(new Publication(PublicationProfileCode.BOOK, null));
        publications.add(new Publication(PublicationProfileCode.BOOK, new Book()));
        ConferencePaper paperContent = new ConferencePaper();
        paperContent.setTitle("conference paper title");
        paperContent.setDate(new Date());
        paperContent.setAbstractText("abstract text of conference paper");
        paperContent.setComment("comment for the conference paper");
        paperContent.setEventName("event name");
        paperContent.setEventDate(new Date());
        paperContent.setEventLocationName("pavilhão atlântico");
        paperContent.setEventLocation(exampleLocation);
        Publication paper = new Publication(PublicationProfileCode.CONFERENCE_PAPER, paperContent, "conference paper description");
        publications.add(paper);
        publications.add(new Publication(PublicationProfileCode.CONFERENCE_PAPER, null));
        publications.add(new Publication(PublicationProfileCode.CONFERENCE_PAPER, new ConferencePaper()));

        this.setPersonalInfo(personalInfo);
        this.setProfessionalInfo(professionalInfo);

    }
}
