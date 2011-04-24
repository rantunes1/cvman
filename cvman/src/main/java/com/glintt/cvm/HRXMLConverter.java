package com.glintt.cvm;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.hr_xml._3.AddressType;
import org.hr_xml._3.ArticleType;
import org.hr_xml._3.BibliographyType;
import org.hr_xml._3.BirthPlaceType;
import org.hr_xml._3.BookType;
import org.hr_xml._3.Candidate;
import org.hr_xml._3.CandidatePersonType;
import org.hr_xml._3.CandidateProfileType;
import org.hr_xml._3.CertificationType;
import org.hr_xml._3.CertificationsType;
import org.hr_xml._3.ChannelCodeType;
import org.hr_xml._3.CitizenshipCountryCodeType;
import org.hr_xml._3.CommunicationABIEType;
import org.hr_xml._3.ConferencePaperType;
import org.hr_xml._3.CopyrightType;
import org.hr_xml._3.CountryCodeEnumType;
import org.hr_xml._3.CountryCodeType;
import org.hr_xml._3.EffectiveDatedIndicatorType;
import org.hr_xml._3.EmployerHistoryType;
import org.hr_xml._3.EmploymentHistoryType;
import org.hr_xml._3.EmploymentPeriodType;
import org.hr_xml._3.FamilyNameType;
import org.hr_xml._3.FormattedPublicationDescriptionType;
import org.hr_xml._3.FreeFormDateType;
import org.hr_xml._3.GenderCodeType;
import org.hr_xml._3.InternetDomainNameType;
import org.hr_xml._3.IssuingAuthorityType;
import org.hr_xml._3.LanguageCodeEnumType;
import org.hr_xml._3.LanguageCodeType;
import org.hr_xml._3.LocationSummaryType;
import org.hr_xml._3.MaritalStatusCodeType;
import org.hr_xml._3.OrganizationContactType;
import org.hr_xml._3.OrganizationNameType;
import org.hr_xml._3.OtherPublicationType;
import org.hr_xml._3.PersonNameType;
import org.hr_xml._3.PositionHistoryType;
import org.hr_xml._3.PositionLocationType;
import org.hr_xml._3.PositionTitleType;
import org.hr_xml._3.PublicationContributorType;
import org.hr_xml._3.PublicationHistoryType;
import org.hr_xml._3.PublicationRoleCodeType;
import org.hr_xml._3.PublicationType;
import org.hr_xml._3.ResourceRelationshipCodeEnumType;
import org.hr_xml._3.ResourceRelationshipCodeType;
import org.hr_xml._3.UseCodeType;
import org.openapplications.oagis._9.CodeType;
import org.openapplications.oagis._9.CountrySubDivisionCodeType;
import org.openapplications.oagis._9.DescriptionType;
import org.openapplications.oagis._9.NameType;
import org.openapplications.oagis._9.NoteType;
import org.openapplications.oagis._9.TextType;

import com.glintt.cvm.model.Address;
import com.glintt.cvm.model.Location;
import com.glintt.cvm.model.OrganizationContacts;
import com.glintt.cvm.model.Person;
import com.glintt.cvm.model.PersonContacts;
import com.glintt.cvm.model.PersonContacts.Email;
import com.glintt.cvm.model.PersonContacts.MobilePhone;
import com.glintt.cvm.model.PersonContacts.Telephone;
import com.glintt.cvm.model.PersonName;
import com.glintt.cvm.model.PersonalInfo;
import com.glintt.cvm.model.PersonalInfo.BirthInfo;
import com.glintt.cvm.model.PersonalInfo.PersonContactsProfile;
import com.glintt.cvm.model.ProfessionalInfo;
import com.glintt.cvm.model.ProfessionalInfo.Certification;
import com.glintt.cvm.model.ProfessionalInfo.Certification.IssuingAuthority;
import com.glintt.cvm.model.ProfessionalInfo.Contributor;
import com.glintt.cvm.model.ProfessionalInfo.Copyright;
import com.glintt.cvm.model.ProfessionalInfo.Employer;
import com.glintt.cvm.model.ProfessionalInfo.Position;
import com.glintt.cvm.model.ProfessionalInfo.PublicationInfo;
import com.glintt.cvm.model.ProfessionalInfo.PublicationProfileCode;
import com.glintt.cvm.model.ProfessionalInfo.TimePeriod;
import com.glintt.cvm.model.Publication;
import com.glintt.cvm.model.Publication.Article;
import com.glintt.cvm.model.Publication.Book;
import com.glintt.cvm.model.Publication.ConferencePaper;
import com.glintt.cvm.model.Publication.UnspecifiedPublication;
import com.glintt.cvm.model.TestPerson;

public class HRXMLConverter {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private TextType createLocalizedText(LanguageCodeEnumType languageId, String value) {
        return createLocalizedText(languageId, value, new TextType());
    }

    private <E extends TextType> E createLocalizedText(LanguageCodeEnumType languageId, String value, E obj) {
        if (obj != null) {
            obj.setLanguageID(languageId.value());
            obj.setValue(value);
        }
        return obj;
    }

    private NameType createName(LanguageCodeEnumType languageId, String value) {
        NameType name = new NameType();
        name.setLanguageID(languageId.value());
        name.setValue(value);
        return name;
    }

    private CodeType createCode(LanguageCodeEnumType languageId, String value) {
        CodeType code = new CodeType();
        code.setLanguageID(languageId.value());
        code.setValue(value);
        return code;
    }

    private CountryCodeType createCountryCode(CountryCodeEnumType code) {
        CountryCodeType cct = new CountryCodeType();
        cct.setValue(code.value());
        return cct;
    }

    private CountrySubDivisionCodeType createCountrySubDivisionCode(LanguageCodeEnumType languageId, String code) {
        CountrySubDivisionCodeType csdct = new CountrySubDivisionCodeType();
        csdct.setLanguageID(languageId.value());
        csdct.setValue(code);
        return csdct;
    }

    private FreeFormDateType formatDate(Date date, String format) {
        FreeFormDateType formatted = new FreeFormDateType();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatted.getFormattedDateTime().add(formatter.format(date));
        return formatted;
    }

    /**
     * @return
     */
    private CandidatePersonType createCandidatePerson(PersonalInfo personalInfo, LanguageCodeEnumType lang) {
        CandidatePersonType person = new CandidatePersonType();

        PersonNameType userName = new PersonNameType();
        userName.getGivenName().add(createName(lang, personalInfo.getName().getFirstName()));
        userName.getFamilyName().add(createLocalizedText(lang, personalInfo.getName().getSurname(), new FamilyNameType()));
        person.setPersonName(userName);

        PersonNameType fatherName = new PersonNameType();
        fatherName.getGivenName().add(createName(lang, personalInfo.getFatherName().getFirstName()));
        fatherName.getFamilyName().add(createLocalizedText(lang, personalInfo.getFatherName().getSurname(), new FamilyNameType()));
        person.setFatherName(fatherName);

        PersonNameType motherName = new PersonNameType();
        motherName.getGivenName().add(createName(lang, personalInfo.getMotherName().getFirstName()));
        motherName.getFamilyName().add(createLocalizedText(lang, personalInfo.getMotherName().getSurname(), new FamilyNameType()));
        person.setMotherName(motherName);

        GenderCodeType gender = new GenderCodeType();
        gender.setValue(personalInfo.getGender());
        person.setGenderCode(gender);

        MaritalStatusCodeType msct = new MaritalStatusCodeType();
        msct.setValue(personalInfo.getMaritalStatus().value());
        person.setMaritalStatusCode(msct);

        BirthInfo birthInfo = personalInfo.getBirthInfo();
        Location birthLocation = birthInfo.getBirthLocation();
        if (birthLocation != null) {
            BirthPlaceType bpt = new BirthPlaceType();
            bpt.setCountryCode(createCountryCode(birthLocation.getCountry()));
            bpt.getCountrySubDivisionCode().add(createCountrySubDivisionCode(lang, birthLocation.getCountryDivision()));
            bpt.setCityName(createName(lang, birthLocation.getCity()));
            person.setBirthPlace(bpt);
        }
        person.getBirthDate().add(birthInfo.getBirthDate());

        CitizenshipCountryCodeType ccc = new CitizenshipCountryCodeType();
        ccc.setValue(personalInfo.getCitizenshipCountry().value());
        person.getCitizenshipCountryCode().add(ccc);

        // person.getResidencyCountryCode().add(); - get from Address
        // person.getNationalityCode().add(nc); - get from BirthInfo

        LanguageCodeType lct = new LanguageCodeType();
        lct.setValue(personalInfo.getPrimaryLanguage().value());
        person.getPrimaryLanguageCode().add(lct);

        processCommunicationChannels(person, personalInfo, lang);

        return person;
    }

    private BibliographyType setBibliographicData(BibliographyType type, PublicationInfo info, LanguageCodeEnumType lang) {
        type.setPublicationTitle(info.getTitle());
        Date publicationDate = info.getDate();
        if (publicationDate != null) {
            type.setPublicationDate(formatDate(publicationDate, DATE_FORMAT));
        }
        Collection<Contributor> contributors = info.getContributors();
        if (contributors != null) {
            for (Contributor contributor : contributors) {
                PersonName contributorName = contributor.getName();
                if (contributorName == null || "".equals(contributorName)) {
                    continue;
                }

                PublicationContributorType contributorType = new PublicationContributorType();

                PersonNameType contributorNameType = new PersonNameType();
                contributorNameType.getGivenName().add(createName(lang, contributorName.getFirstName()));
                contributorNameType.getFamilyName().add(
                        createLocalizedText(lang, contributorName.getSurname(), new FamilyNameType()));
                contributorType.setPersonName(contributorNameType);

                String contributorRole = contributor.getRole();
                if (contributorRole != null && !"".equals(contributorRole)) {
                    PublicationRoleCodeType roleType = new PublicationRoleCodeType();
                    roleType.setValue(contributorRole);
                    contributorType.setPublicationRoleCode(roleType);
                }

                type.getPublicationContributor().add(contributorType);
            }
        }

        String abstractText = info.getAbstractText();
        if (abstractText != null && !"".equals(abstractText)) {
            type.setAbstract(abstractText);
        }

        String comments = info.getComment();
        if (comments != null && !"".equals(comments)) {
            NoteType note = new NoteType();
            note.setLanguageID(lang.value());
            note.setValue(comments);
            type.getComment().add(note);
        }

        Copyright copyright = info.getCopyright();
        if (copyright != null) {
            CopyrightType copyrightType = new CopyrightType();
            copyrightType.setCopyrightText(createLocalizedText(lang, copyright.getText()));
            // @todo copyright year
        }
        return type;
    }

    private CandidateProfileType createCandidateProfile(ProfessionalInfo professionalInfo, LanguageCodeEnumType lang) {
        CandidateProfileType profile = new CandidateProfileType();

        profile.setLanguageCode(lang.value());

        String profileName = professionalInfo.getProfileName();
        if (profileName != null) {
            profile.setProfileName(createLocalizedText(lang, profileName));
        }

        String executiveSummary = professionalInfo.getExecutiveSummary();
        if (executiveSummary != null) {
            profile.setExecutiveSummary(createLocalizedText(lang, executiveSummary));
        }

        Collection<Certification> certifications = professionalInfo.getCertifications();
        if (certifications != null) {
            CertificationsType certs = new CertificationsType();
            for (Certification certification : certifications) {
                CertificationType cert = new CertificationType();
                cert.setCertificationName(createLocalizedText(lang, certification.getName()));
                Date certificateIssueDate = certification.getIssueDate();
                if (certificateIssueDate != null) {
                    cert.setFirstIssuedDate(formatDate(certificateIssueDate, DATE_FORMAT));
                }
                IssuingAuthority issuingAuthority = certification.getIssuingAuthority();
                if (issuingAuthority != null) {
                    IssuingAuthorityType issuingAuthorityType = new IssuingAuthorityType();
                    issuingAuthorityType.setName(createName(lang, issuingAuthority.getName()));
                    Location iaLocation = issuingAuthority.getLocation();
                    if (iaLocation != null) {
                        LocationSummaryType location = new LocationSummaryType();
                        location.setCountryCode(createCountryCode(iaLocation.getCountry()));
                        location.setCityName(createName(lang, iaLocation.getCity()));
                        location.setPostalCode(createCode(lang, iaLocation.getPostalCode()));
                        issuingAuthorityType.setLocationSummary(location);
                    }
                    cert.setIssuingAuthority(issuingAuthorityType);
                }
                certs.getCertification().add(cert);
            }
            profile.setCertifications(certs);
        }

        Collection<Publication> publications = professionalInfo.getPublications();
        if (publications != null) {
            PublicationHistoryType pubs = new PublicationHistoryType();
            for (Publication publication : publications) {
                PublicationInfo info = publication.getInfo();
                if (info == null) {
                    continue;
                }

                PublicationType pub = new PublicationType();

                String description = publication.getDescription();
                if (description != null && !"".equals(description.trim())) {
                    FormattedPublicationDescriptionType pubDescription = new FormattedPublicationDescriptionType();
                    pubDescription.setValue(publication.getDescription());
                    pub.getFormattedPublicationDescription().add(pubDescription);
                }

                PublicationProfileCode publicationType = publication.getType();
                BibliographyType type = null;
                switch (publicationType) {
                case ARTICLE: {
                    type = setBibliographicData(new ArticleType(), info, lang);
                    Article article = (Article) info;
                    String issn = article.getIssn();
                    if (issn != null) {
                        ((ArticleType) type).setISSN(issn);
                    }
                    String journalName = article.getJournalName();
                    if (journalName != null) {
                        ((ArticleType) type).setJournalName(journalName);
                    }
                    String volume = article.getVolume();
                    if (volume != null) {
                        ((ArticleType) type).setVolume(volume);
                    }
                    String issue = article.getIssue();
                    if (issue != null) {
                        ((ArticleType) type).setIssue(issue);
                    }
                    String pageNumbers = article.getPageNumbers();
                    if (pageNumbers != null) {
                        ((ArticleType) type).setPageNumberFormat(pageNumbers);
                    }
                    LanguageCodeEnumType language = article.getLanguage();
                    if (language != null) {
                        LanguageCodeType lct = new LanguageCodeType();
                        lct.setValue(article.getLanguage().value());
                        ((ArticleType) type).setPublicationLanguage(lct);
                    }
                    pub.getArticle().add((ArticleType) type);
                    break;
                }
                case BOOK: {
                    type = setBibliographicData(new BookType(), info, lang);
                    Book book = (Book) info;
                    String issn = book.getIssn();
                    if (issn != null) {
                        ((BookType) type).setISSN(issn);
                    }
                    String isbn = book.getIsbn();
                    if (isbn != null) {
                        ((BookType) type).setISBN(isbn);
                    }
                    int numberOfPages = book.getNumberOfPages();
                    if (numberOfPages > 0) {
                        ((BookType) type).setNumberOfPages(new BigDecimal(numberOfPages));
                    }
                    String publisherName = book.getPublisherName();
                    if (publisherName != null) {
                        ((BookType) type).setPublisherName(createLocalizedText(lang, publisherName));
                    }
                    Location publisherLocation = book.getPublisherLocation();
                    if (publisherLocation != null) {
                        String locationTxt = publisherLocation.getCity() + ", " + publisherLocation.getCountry();
                        ((BookType) type).setPublisherLocation(locationTxt);
                    }
                    String edition = book.getEdition();
                    if (edition != null) {
                        ((BookType) type).setEdition(edition);
                    }
                    String chapter = book.getChapter();
                    if (chapter != null) {
                        ((BookType) type).setChapter(chapter);
                    }
                    pub.getBook().add((BookType) type);
                    break;
                }
                case CONFERENCE_PAPER: {
                    type = setBibliographicData(new ConferencePaperType(), info, lang);
                    ConferencePaper conferencePaper = (ConferencePaper) info;
                    String eventName = conferencePaper.getEventName();
                    if (eventName != null) {
                        ((ConferencePaperType) type).setEventName(createLocalizedText(lang, eventName));
                    }
                    Date eventDate = conferencePaper.getEventDate();
                    if (eventDate != null) {
                        ((ConferencePaperType) type).setEventDate(formatDate(eventDate, DATE_FORMAT));
                    }
                    String eventLocationName = conferencePaper.getEventLocationName();
                    if (eventLocationName != null) {
                        ((ConferencePaperType) type).setVenueName(createLocalizedText(lang, eventLocationName));
                    }
                    pub.getConferencePaper().add((ConferencePaperType) type);
                    Location eventLocation = conferencePaper.getEventLocation();
                    if (eventLocation != null) {
                        LocationSummaryType location = new LocationSummaryType();
                        location.setCountryCode(createCountryCode(eventLocation.getCountry()));
                        location.setCityName(createName(lang, eventLocation.getCity()));
                        location.setPostalCode(createCode(lang, eventLocation.getPostalCode()));
                        ((ConferencePaperType) type).setLocationSummary(location);
                    }
                    break;
                }
                case UNSPECIFIED: {
                    type = setBibliographicData(new OtherPublicationType(), info, lang);
                    UnspecifiedPublication unspecified = (UnspecifiedPublication) info;
                    String issn = unspecified.getIssn();
                    if (issn != null) {
                        ((OtherPublicationType) type).setISSN(issn);
                    }
                    String isbn = unspecified.getIsbn();
                    if (isbn != null) {
                        ((OtherPublicationType) type).setISBN(isbn);
                    }
                    int numberOfPages = unspecified.getNumberOfPages();
                    if (numberOfPages > 0) {
                        ((OtherPublicationType) type).setNumberOfPages(new BigDecimal(numberOfPages));
                    }
                    String publisherName = unspecified.getPublisherName();
                    if (publisherName != null) {
                        ((OtherPublicationType) type).setPublisherName(createLocalizedText(lang, publisherName));
                    }
                    Location publisherLocation = unspecified.getPublisherLocation();
                    if (publisherLocation != null) {
                        String locationTxt = publisherLocation.getCity() + ", " + publisherLocation.getCountry();
                        ((OtherPublicationType) type).setPublisherLocation(locationTxt);
                    }
                    pub.getOtherPublication().add((OtherPublicationType) type);
                    break;
                }
                default:
                    throw new UnsupportedOperationException("unknown publication type : " + publicationType);

                }
                pubs.getPublication().add(pub);
            }
            profile.setPublicationHistory(pubs);
        }

        Collection<Employer> employers = professionalInfo.getEmployers();
        if (employers != null) {
            EmploymentHistoryType employmentHist = new EmploymentHistoryType();
            for (Employer employer : employers) {
                EmployerHistoryType employerHistType = new EmployerHistoryType();

                employerHistType.setOrganizationName(createLocalizedText(lang, employer.getName(), new OrganizationNameType()));
                employerHistType.setHeadcountNumeric(new BigInteger(employer.getHeadcount()));
                OrganizationContacts contacts = employer.getContacts();
                if (contacts != null) {
                    OrganizationContactType oct = new OrganizationContactType();
                    oct.setContactName(createLocalizedText(lang, contacts.getName().getFullName()));
                    oct.setRoleName(createLocalizedText(lang, contacts.getRole()));
                    addCommunicationChannels(oct.getCommunication(), contacts, lang, 0);
                    employerHistType.setOrganizationContact(oct);
                }
                String organizationURL = employer.getUrl();
                if (organizationURL != null) {
                    InternetDomainNameType domainName = new InternetDomainNameType();
                    domainName.setValue(organizationURL);
                    employerHistType.setInternetDomainName(domainName);
                }
                TimePeriod employementPeriod = employer.getEmploymentPeriod();
                if (employementPeriod != null) {
                    EmploymentPeriodType ep = convertTimePeriodToEmploymentPeriod(employementPeriod);
                    employerHistType.setEmploymentPeriod(ep);
                }
                Collection<Position> positions = employer.getPositions();
                if (positions != null) {
                    for (Position position : positions) {
                        PositionHistoryType ph = new PositionHistoryType();
                        ph.setPositionTitle(createLocalizedText(lang, position.getTitle(), new PositionTitleType()));
                        String description = position.getDescription();
                        if (description != null) {
                            DescriptionType desc = new DescriptionType();
                            desc.setValue(description);
                            desc.setLanguageID(lang.value());
                            ph.getDescription().add(desc);
                        }
                        ResourceRelationshipCodeEnumType relationshipType = position.getRelationshipType();
                        if (relationshipType != null) {
                            ResourceRelationshipCodeType rct = new ResourceRelationshipCodeType();
                            rct.setValue(relationshipType.value());
                            ph.setResourceRelationshipCode(rct);
                        }
                        String unitName = position.getUnitName();
                        if (unitName != null) {
                            ph.setOrganizationUnitName(createLocalizedText(lang, unitName));
                        }
                        Address address = position.getAddress();
                        if (address != null) {
                            PositionLocationType location = new PositionLocationType();
                            AddressType locationAddress = convertAddressToAddressType(address, lang);
                            location.setAddress(locationAddress);
                            ph.setPositionLocation(location);
                        }
                        TimePeriod employmentPeriod = position.getEmploymentPeriod();
                        if (employmentPeriod != null) {
                            EmploymentPeriodType ep = convertTimePeriodToEmploymentPeriod(employementPeriod);
                            ph.setEmploymentPeriod(ep);
                        }

                        // ph.getPersonCompetency().add(e);

                        // ph.setCurrentIndicator(value);
                        // ph.setURI(value);
                        // ph.getJobCategoryCode().add(e);
                        // ph.getJobLevel().add(e);
                        // ph.setPositionRemuneration(value);
                        // ph.setEmploymentVerification(value);

                        employerHistType.getPositionHistory().add(ph);
                    }
                }

                // employerHistType.getPersonContact().add(e);
                // employerHistType.setCurrentIndicator(value);
                // employerHistType.getIndustryCode().add(e);
                // employerHistType.setEmploymentVerification(value);

                employmentHist.getEmployerHistory().add(employerHistType);
            }
            profile.setEmploymentHistory(employmentHist);
        }

        // profile.setEducationHistory(value);
        // profile.setExperienceSummary(value);
        // profile.setLicenses(value);
        // profile.setMilitaryHistory(value);
        // profile.setPersonQualifications(value);
        // profile.setSpeakingHistory(value);

        profile.getAttachment();

        return profile;
    }

    private EmploymentPeriodType convertTimePeriodToEmploymentPeriod(TimePeriod employementPeriod) {
        EmploymentPeriodType ep = new EmploymentPeriodType();
        ep.setStartDate(formatDate(employementPeriod.getStartDate(), DATE_FORMAT));
        Date endDate = employementPeriod.getEndDate();
        if (endDate != null) {
            ep.setEndDate(formatDate(endDate, DATE_FORMAT));
        } else {
            EffectiveDatedIndicatorType edi = new EffectiveDatedIndicatorType();
            edi.setValue(true);
            ep.setCurrentIndicator(edi);
        }
        return ep;
    }

    // adds communication channels from all profiles
    private void processCommunicationChannels(CandidatePersonType person, PersonalInfo personalInfo, LanguageCodeEnumType lang) {
        int channelIndex = 0;
        for (PersonContactsProfile userProfileCode : PersonalInfo.PersonContactsProfile.values()) {
            PersonContacts contacts = personalInfo.getPersonContacts(userProfileCode);
            if (contacts != null) {
                channelIndex = addCommunicationChannels(person.getCommunication(), contacts, lang, channelIndex);
            }
        }
    }

    private AddressType convertAddressToAddressType(Address address, LanguageCodeEnumType lang) {
        AddressType addressType = new AddressType();
        addressType.setLanguageCode(lang.value());
        addressType.setStreetName(createName(lang, address.getStreetName()));
        addressType.setBuildingName(createName(lang, address.getBuildingName()));
        addressType.setBuildingNumber(createLocalizedText(lang, address.getBuildingNumber()));
        addressType.setFloor(createLocalizedText(lang, address.getFloor()));
        addressType.setCityName(createName(lang, address.getCity()));
        addressType.setCountryCode(createCountryCode(address.getCountry()));
        addressType.setCurrentAddressIndicator(Boolean.TRUE);
        addressType.setPostalCode(createCode(lang, address.getPostalCode()));
        addressType.setPostOfficeBox(createLocalizedText(lang, address.getPostOfficeBox()));
        return addressType;
    }

    private CommunicationABIEType convertAddressChannel(Address address, UseCodeType useCode, LanguageCodeEnumType lang) {
        CommunicationABIEType addressContainer = new CommunicationABIEType();
        ChannelCodeType addressCh = new ChannelCodeType();
        addressCh.setValue(address.getChannelCode());
        addressContainer.setChannelCode(addressCh);
        addressContainer.setUseCode(useCode);
        AddressType addressType = convertAddressToAddressType(address, lang);
        addressContainer.setAddress(addressType);
        return addressContainer;
    }

    private CommunicationABIEType convertEmailChannel(Email email, UseCodeType useCode, LanguageCodeEnumType lang) {
        CommunicationABIEType emailContainer = new CommunicationABIEType();
        ChannelCodeType emailCh = new ChannelCodeType();
        emailCh.setValue(email.getChannelCode());
        emailContainer.setChannelCode(emailCh);
        emailContainer.setUseCode(useCode);
        emailContainer.setHTMLPreferredIndicator(Boolean.TRUE);
        emailContainer.setPreferredIndicator(Boolean.TRUE);
        emailContainer.setURI(email.getEmailAddress());
        return emailContainer;
    }

    private CommunicationABIEType convertMobilePhoneChannel(MobilePhone mobilePhone, UseCodeType useCode, LanguageCodeEnumType lang) {
        CommunicationABIEType mobilePhoneContainer = new CommunicationABIEType();
        ChannelCodeType mbChann = new ChannelCodeType();
        mbChann.setValue(mobilePhone.getChannelCode());
        mobilePhoneContainer.setChannelCode(mbChann);
        mobilePhoneContainer.setUseCode(useCode);
        mobilePhoneContainer.setCountryDialing(createLocalizedText(lang, mobilePhone.getCountryDialing()));
        mobilePhoneContainer.setDialNumber(createLocalizedText(lang, mobilePhone.getDialNumber()));
        return mobilePhoneContainer;
    }

    private CommunicationABIEType convertPhoneChannel(Telephone phone, UseCodeType useCode, LanguageCodeEnumType lang) {
        CommunicationABIEType phoneContainer = new CommunicationABIEType();
        ChannelCodeType tChann = new ChannelCodeType();
        tChann.setValue(phone.getChannelCode());
        phoneContainer.setChannelCode(tChann);
        phoneContainer.setUseCode(useCode);
        phoneContainer.setCountryDialing(createLocalizedText(lang, phone.getCountryDialing()));
        phoneContainer.setDialNumber(createLocalizedText(lang, phone.getDialNumber()));
        return phoneContainer;
    }

    /**
     * 
     * @param person
     * @param contacts
     * @param personalInfo
     * @param lang
     * @param channelIndex
     *            the index for the next entry on the XML document
     * @return
     */
    private int addCommunicationChannels(List<CommunicationABIEType> communicationChannels, PersonContacts contacts,
            LanguageCodeEnumType lang, int channelIndex) {
        UseCodeType useCode = new UseCodeType();
        useCode.setValue(contacts.getContactsProfile().toString().toLowerCase());

        Address profileAddress = contacts.getAddress();
        if (profileAddress != null) {
            communicationChannels.add(channelIndex++, convertAddressChannel(profileAddress, useCode, lang));
        }
        Email profileEmail = contacts.getEmail();
        if (profileEmail != null) {
            communicationChannels.add(channelIndex++, convertEmailChannel(profileEmail, useCode, lang));
        }
        MobilePhone profileMobile = contacts.getMobilePhone();
        if (profileMobile != null) {
            communicationChannels.add(channelIndex++, convertMobilePhoneChannel(profileMobile, useCode, lang));
        }
        Telephone profilePhone = contacts.getTelephone();
        if (profilePhone != null) {
            communicationChannels.add(channelIndex++, convertPhoneChannel(profilePhone, useCode, lang));
        }

        return channelIndex;
    }

    private Candidate convertToCandidate(Person person) {
        Candidate candidate = new Candidate();

        PersonalInfo personalInfo = person.getPersonalInfo();
        if (personalInfo != null) {
            candidate.setCandidatePerson(createCandidatePerson(personalInfo, person.getLanguageId()));
        }
        ProfessionalInfo professionalInfo = person.getProfessionalInfo();
        if (professionalInfo != null) {
            candidate.getCandidateProfile().add(createCandidateProfile(professionalInfo, person.getLanguageId()));
        }

        return candidate;
    }

    // @todo review exception to be thrown
    public void convertAndSave(Person person, OutputStream target) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance("org.hr_xml._3");

        Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // @todo fix maven compilation
        // m.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new
        // NamespacePrefixMapper() {
        //
        // @Override
        // public String getPreferredPrefix(String namespaceUri, String
        // suggestion, boolean requirePrefix) {
        // if ("http://www.hr-xml.org/3".equals(namespaceUri)) {
        // return "hr";
        // }
        // if ("http://www.openapplications.org/oagis/9".equals(namespaceUri)) {
        // return "oa";
        // }
        // return suggestion;
        // }
        //
        // });

        Candidate candidate = convertToCandidate(person);
        m.marshal(candidate, target);

    }

    public static void main(String[] args) throws Exception {
        new HRXMLConverter().convertAndSave(new TestPerson(), new FileOutputStream("candidateOut.xml"));
    }
}
