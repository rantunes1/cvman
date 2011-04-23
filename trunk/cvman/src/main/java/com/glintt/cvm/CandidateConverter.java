package com.glintt.cvm;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.hr_xml._3.FamilyNameType;
import org.hr_xml._3.FormattedPublicationDescriptionType;
import org.hr_xml._3.FreeFormDateType;
import org.hr_xml._3.GenderCodeType;
import org.hr_xml._3.IssuingAuthorityType;
import org.hr_xml._3.LanguageCodeEnumType;
import org.hr_xml._3.LanguageCodeType;
import org.hr_xml._3.LocationSummaryType;
import org.hr_xml._3.MaritalStatusCodeType;
import org.hr_xml._3.OtherPublicationType;
import org.hr_xml._3.PersonNameType;
import org.hr_xml._3.PublicationContributorType;
import org.hr_xml._3.PublicationHistoryType;
import org.hr_xml._3.PublicationRoleCodeType;
import org.hr_xml._3.PublicationType;
import org.hr_xml._3.UseCodeType;
import org.openapplications.oagis._9.CodeType;
import org.openapplications.oagis._9.CountrySubDivisionCodeType;
import org.openapplications.oagis._9.NameType;
import org.openapplications.oagis._9.NoteType;
import org.openapplications.oagis._9.TextType;

import com.glintt.cvm.model.Address;
import com.glintt.cvm.model.Location;
import com.glintt.cvm.model.Person;
import com.glintt.cvm.model.PersonName;
import com.glintt.cvm.model.PersonalInfo;
import com.glintt.cvm.model.PersonalInfo.BirthInfo;
import com.glintt.cvm.model.PersonalInfo.UserProfileCode;
import com.glintt.cvm.model.ProfessionalInfo;
import com.glintt.cvm.model.ProfessionalInfo.Certification;
import com.glintt.cvm.model.ProfessionalInfo.Certification.IssuingAuthority;
import com.glintt.cvm.model.ProfessionalInfo.Contributor;
import com.glintt.cvm.model.ProfessionalInfo.Copyright;
import com.glintt.cvm.model.ProfessionalInfo.PublicationInfo;
import com.glintt.cvm.model.ProfessionalInfo.PublicationProfileCode;
import com.glintt.cvm.model.Publication;
import com.glintt.cvm.model.Publication.Article;
import com.glintt.cvm.model.Publication.Book;
import com.glintt.cvm.model.Publication.ConferencePaper;
import com.glintt.cvm.model.Publication.UnspecifiedPublication;
import com.glintt.cvm.model.TestPerson;
import com.glintt.cvm.model.UserProfile;
import com.glintt.cvm.model.UserProfile.Email;
import com.glintt.cvm.model.UserProfile.MobilePhone;
import com.glintt.cvm.model.UserProfile.Telephone;

public class CandidateConverter {

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

        addCommunicationChannels(person, personalInfo, lang);

        return person;
    }

    private BibliographyType setBibliographicData(BibliographyType type, PublicationInfo info, LanguageCodeEnumType lang) {
        type.setPublicationTitle(info.getTitle());
        Date publicationDate = info.getDate();
        if (publicationDate != null) {
            type.setPublicationDate(formatDate(publicationDate, "dd-MM-yyyy"));
        }
        List<Contributor> contributors = info.getContributors();
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

        List<Certification> certifications = professionalInfo.getCertifications();
        if (certifications != null) {
            CertificationsType certs = new CertificationsType();
            for (Certification certification : certifications) {
                CertificationType cert = new CertificationType();
                cert.setCertificationName(createLocalizedText(lang, certification.getName()));
                Date certificateIssueDate = certification.getIssueDate();
                if (certificateIssueDate != null) {
                    cert.setFirstIssuedDate(formatDate(certificateIssueDate, "dd-MM-yyyy"));
                }
                IssuingAuthority issuingAuthority = certification.getIssuingAuthority();
                if (issuingAuthority != null) {
                    IssuingAuthorityType issuingAuthorityType = new IssuingAuthorityType();
                    issuingAuthorityType.setName(createName(lang, issuingAuthority.getName()));
                    LocationSummaryType location = new LocationSummaryType();
                    location.setCountryCode(createCountryCode(issuingAuthority.getCountry()));
                    location.setCityName(createName(lang, issuingAuthority.getCity()));
                    location.setPostalCode(createCode(lang, issuingAuthority.getPostalCode()));
                    issuingAuthorityType.setLocationSummary(location);
                    cert.setIssuingAuthority(issuingAuthorityType);
                }
                certs.getCertification().add(cert);
            }
            profile.setCertifications(certs);
        }

        List<Publication> publications = professionalInfo.getPublications();
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
                        ((ConferencePaperType) type).setEventDate(formatDate(eventDate, "dd-MM-yyyy"));
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

        // profile.setEducationHistory(value);
        // profile.setEmploymentHistory(value);
        // profile.setExperienceSummary(value);
        // profile.setLicenses(value);
        // profile.setMilitaryHistory(value);
        // profile.setPersonQualifications(value);
        // profile.setSpeakingHistory(value);

        profile.getAttachment();

        return profile;
    }

    // adds communication channels from all profiles
    private void addCommunicationChannels(CandidatePersonType person, PersonalInfo personalInfo, LanguageCodeEnumType lang) {
        int channelIndex = 0;
        for (UserProfileCode userProfileCode : PersonalInfo.UserProfileCode.values()) {
            UserProfile profile = personalInfo.getProfile(userProfileCode);
            if (profile != null) {
                channelIndex = addCommunicationChannelsForProfile(person, profile, personalInfo, lang, channelIndex);
            }
        }
    }

    /**
     * 
     * @param person
     * @param profile
     * @param personalInfo
     * @param lang
     * @param channelIndex
     *            the index for the next entry on the XML document
     * @return
     */
    private int addCommunicationChannelsForProfile(CandidatePersonType person, UserProfile profile, PersonalInfo personalInfo,
            LanguageCodeEnumType lang, int channelIndex) {
        UseCodeType useCode = new UseCodeType();
        useCode.setValue(profile.getProfileCode().toString().toLowerCase());

        Address profileAddress = profile.getAddress();
        if (profileAddress != null) {
            CommunicationABIEType addressContainer = new CommunicationABIEType();
            ChannelCodeType addressCh = new ChannelCodeType();
            addressCh.setValue(profileAddress.getChannelCode());
            addressContainer.setChannelCode(addressCh);
            addressContainer.setUseCode(useCode);
            AddressType address = new AddressType();
            address.setLanguageCode(lang.value());
            address.setStreetName(createName(lang, profileAddress.getStreetName()));
            address.setBuildingName(createName(lang, profileAddress.getBuildingName()));
            address.setBuildingNumber(createLocalizedText(lang, profileAddress.getBuildingNumber()));
            address.setFloor(createLocalizedText(lang, profileAddress.getFloor()));
            address.setCityName(createName(lang, profileAddress.getCity()));
            address.setCountryCode(createCountryCode(profileAddress.getCountry()));
            address.setCurrentAddressIndicator(Boolean.TRUE);
            address.setPostalCode(createCode(lang, profileAddress.getPostalCode()));
            address.setPostOfficeBox(createLocalizedText(lang, profileAddress.getPostOfficeBox()));
            addressContainer.setAddress(address);
            person.getCommunication().add(channelIndex++, addressContainer);
        }
        Email profileEmail = profile.getEmail();
        if (profileEmail != null) {
            CommunicationABIEType email = new CommunicationABIEType();
            ChannelCodeType emailCh = new ChannelCodeType();
            emailCh.setValue(profileEmail.getChannelCode());
            email.setChannelCode(emailCh);
            email.setUseCode(useCode);
            email.setHTMLPreferredIndicator(Boolean.TRUE);
            email.setPreferredIndicator(Boolean.TRUE);
            email.setURI(profileEmail.getEmailAddress());
            person.getCommunication().add(channelIndex++, email);
        }
        MobilePhone profileMobile = profile.getMobilePhone();
        if (profileMobile != null) {
            CommunicationABIEType mobilePhone = new CommunicationABIEType();
            ChannelCodeType mbChann = new ChannelCodeType();
            mbChann.setValue(profileMobile.getChannelCode());
            mobilePhone.setChannelCode(mbChann);
            mobilePhone.setUseCode(useCode);
            mobilePhone.setCountryDialing(createLocalizedText(lang, profileMobile.getCountryDialing()));
            mobilePhone.setDialNumber(createLocalizedText(lang, profileMobile.getDialNumber()));
            person.getCommunication().add(channelIndex++, mobilePhone);
        }
        Telephone profilePhone = profile.getTelephone();
        if (profilePhone != null) {
            CommunicationABIEType telephone = new CommunicationABIEType();
            ChannelCodeType tChann = new ChannelCodeType();
            tChann.setValue(profilePhone.getChannelCode());
            telephone.setChannelCode(tChann);
            telephone.setUseCode(useCode);
            telephone.setCountryDialing(createLocalizedText(lang, profilePhone.getCountryDialing()));
            telephone.setDialNumber(createLocalizedText(lang, profilePhone.getDialNumber()));
            person.getCommunication().add(channelIndex++, telephone);

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
        new CandidateConverter().convertAndSave(new TestPerson(), new FileOutputStream("candidateOut.xml"));
    }
}
