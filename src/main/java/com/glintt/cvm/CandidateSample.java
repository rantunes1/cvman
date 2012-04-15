package com.glintt.cvm;

import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.hr_xml._3.AddressType;
import org.hr_xml._3.BirthPlaceType;
import org.hr_xml._3.Candidate;
import org.hr_xml._3.CandidatePersonType;
import org.hr_xml._3.CandidateProfileType;
import org.hr_xml._3.CertificationType;
import org.hr_xml._3.CertificationsType;
import org.hr_xml._3.ChannelCodeEnumType;
import org.hr_xml._3.ChannelCodeType;
import org.hr_xml._3.CitizenshipCountryCodeType;
import org.hr_xml._3.CommunicationABIEType;
import org.hr_xml._3.CountryCodeEnumType;
import org.hr_xml._3.CountryCodeType;
import org.hr_xml._3.FamilyNameType;
import org.hr_xml._3.GenderCodeEnumType;
import org.hr_xml._3.GenderCodeType;
import org.hr_xml._3.LanguageCodeEnumType;
import org.hr_xml._3.LanguageCodeType;
import org.hr_xml._3.MaritalStatusCodeEnumType;
import org.hr_xml._3.MaritalStatusCodeType;
import org.hr_xml._3.PersonNameType;
import org.hr_xml._3.UseCodeType;
import org.openapplications.oagis._9.CodeType;
import org.openapplications.oagis._9.NameType;
import org.openapplications.oagis._9.TextType;

public class CandidateSample {

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

    /**
     * @return
     */
    private CandidatePersonType createCandidatePerson(LanguageCodeEnumType lang) {
        CandidatePersonType person = new CandidatePersonType();

        PersonNameType personName = new PersonNameType();
        personName.getGivenName().add(createName(lang, "João"));
        personName.getFamilyName().add(createLocalizedText(lang, "Silva", new FamilyNameType()));
        person.setPersonName(personName);

        GenderCodeType gender = new GenderCodeType();
        gender.setValue(GenderCodeEnumType.MALE);
        person.setGenderCode(gender);

        MaritalStatusCodeType msct = new MaritalStatusCodeType();
        msct.setValue(MaritalStatusCodeEnumType.MARRIED.value());
        person.setMaritalStatusCode(msct);

        BirthPlaceType bpt = new BirthPlaceType();
        bpt.setCountryCode(createCountryCode(CountryCodeEnumType.PT));
        bpt.setCityName(createName(lang, "Lisboa"));
        person.setBirthPlace(bpt);

        CitizenshipCountryCodeType ccc = new CitizenshipCountryCodeType();
        ccc.setValue(CountryCodeEnumType.PT.value());
        person.getCitizenshipCountryCode().add(ccc);

        LanguageCodeType lct = new LanguageCodeType();
        lct.setValue(lang.value());
        person.getPrimaryLanguageCode().add(lct);

        person.getBirthDate().add("01-01-1970");

        CommunicationABIEType addressContainer = new CommunicationABIEType();
        UseCodeType addressUseCode = new UseCodeType();
        addressUseCode.setValue("address");
        addressContainer.setUseCode(addressUseCode);
        AddressType address = new AddressType();
        address.setLanguageCode(lang.value());
        address.setStreetName(createName(lang, "Rua da Palma"));
        address.setBuildingName(createName(lang, "C.C. Martim Moniz"));
        address.setBuildingNumber(createLocalizedText(lang, "N.º 2"));
        address.setFloor(createLocalizedText(lang, "Loja 3"));
        address.setCityName(createName(lang, "Lisboa"));
        address.setCountryCode(createCountryCode(CountryCodeEnumType.PT));
        address.setCurrentAddressIndicator(Boolean.TRUE);
        address.setPostalCode(createCode(lang, "1100-111"));
        address.setPostOfficeBox(createLocalizedText(lang, "LISBOA"));
        addressContainer.setAddress(address);
        person.getCommunication().add(0, addressContainer);

        CommunicationABIEType email = new CommunicationABIEType();
        ChannelCodeType emailCh = new ChannelCodeType();
        emailCh.setValue(ChannelCodeEnumType.EMAIL.value());
        email.setChannelCode(emailCh);
        email.setHTMLPreferredIndicator(Boolean.TRUE);
        email.setPreferredIndicator(Boolean.TRUE);
        email.setURI("joao.silva@somedomain.net");
        person.getCommunication().add(1, email);

        CommunicationABIEType mobileBusiness = new CommunicationABIEType();
        ChannelCodeType mbChann = new ChannelCodeType();
        mbChann.setValue(ChannelCodeEnumType.MOBILE_TELEPHONE.value());
        mobileBusiness.setChannelCode(mbChann);
        UseCodeType mbUseCode = new UseCodeType();
        mbUseCode.setValue("business");
        mobileBusiness.setUseCode(mbUseCode);
        mobileBusiness.setCountryDialing(createLocalizedText(lang, "351"));
        mobileBusiness.setDialNumber(createLocalizedText(lang, "911 111 111"));
        person.getCommunication().add(2, mobileBusiness);

        CommunicationABIEType mobilePersonal = new CommunicationABIEType();
        ChannelCodeType mpChann = new ChannelCodeType();
        mpChann.setValue(ChannelCodeEnumType.MOBILE_TELEPHONE.value());
        mobilePersonal.setChannelCode(mpChann);
        UseCodeType mpUseCode = new UseCodeType();
        mpUseCode.setValue("personal");
        mobilePersonal.setUseCode(mpUseCode);
        mobilePersonal.setCountryDialing(createLocalizedText(lang, "351"));
        mobilePersonal.setDialNumber(createLocalizedText(lang, "922 222 222"));
        person.getCommunication().add(3, mobilePersonal);

        CommunicationABIEType telephone = new CommunicationABIEType();
        ChannelCodeType tChann = new ChannelCodeType();
        tChann.setValue(ChannelCodeEnumType.TELEPHONE.value());
        telephone.setChannelCode(tChann);
        telephone.setCountryDialing(createLocalizedText(lang, "351"));
        telephone.setDialNumber(createLocalizedText(lang, "212 345 678"));
        person.getCommunication().add(4, telephone);

        return person;
    }

    private CandidateProfileType createCandidateProfile() {
        CandidateProfileType profile = new CandidateProfileType();

        TextType profileName = new TextType();
        profileName.setValue("Automatically generated Europass CV and xslt from EuroCv");
        profile.setProfileName(profileName);

        CertificationsType certs = new CertificationsType();
        CertificationType cert = new CertificationType();
        // cert.setCertificationName(value);
        // cert.setCertificationTypeCode(value);
        // cert.setFirstIssuedDate(value);
        // cert.setIssuingAuthority(value);
        certs.getCertification().add(cert);
        profile.setCertifications(certs);

        // profile.setEducationHistory(value);
        // profile.setEmploymentHistory(value);
        // profile.setExecutiveSummary(value);
        // profile.setExperienceSummary(value);
        // profile.setLanguageCode(value);
        // profile.setLicenses(value);
        // profile.setMilitaryHistory(value);
        // profile.setPersonQualifications(value);
        // profile.setPublicationHistory(value);
        // profile.setSpeakingHistory(value);

        profile.getAttachment();

        return profile;
    }

    public static void main(String[] args) throws Exception {
        CandidateSample sample = new CandidateSample();

        Candidate candidate = new Candidate();

        CandidatePersonType person = sample.createCandidatePerson(LanguageCodeEnumType.PT);
        candidate.setCandidatePerson(person);

        CandidateProfileType profile = sample.createCandidateProfile();
        candidate.getCandidateProfile().add(profile);

        JAXBContext ctx = JAXBContext.newInstance("org.hr_xml._3");

        Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(candidate, new FileOutputStream("candidateOut.xml"));

    }
}
