package com.glintt.cvm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hr_xml._3.CountryCodeEnumType;
import org.hr_xml._3.LanguageCodeEnumType;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

@Entity
public class ProfessionalInfo extends AbstractOwnedEntity<Person> {
    private static final long serialVersionUID = 2456109426437042121L;

    @ManyToOne(cascade = CascadeType.ALL)
    private Person owner;

    private String profileName;
    private String executiveSummary;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Certification> certifications;
    private List<Publication> publications;

    @Override
    public void setOwner(Person owner) {
        if (owner == null) {
            throw new IllegalArgumentException("owner can't be null");
        }
        this.owner = owner;
    }

    @Override
    public Person getOwner() {
        if (this.owner == null) {
            throw new IllegalStateException("entity owner was not yet set");
        }
        return this.owner;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getExecutiveSummary() {
        return this.executiveSummary;
    }

    public void setExecutiveSummary(String executiveSummary) {
        this.executiveSummary = executiveSummary;
    }

    public List<Certification> getCertifications() {
        return this.certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

    public List<Publication> getPublications() {
        return this.publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    @Entity
    public static class Certification extends AbstractPojo {
        private static final long serialVersionUID = 8199623327088878247L;

        private String name;
        @Temporal(TemporalType.DATE)
        private Date issueDate;
        @OneToOne(cascade = CascadeType.ALL)
        private IssuingAuthority issuingAuthority;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getIssueDate() {
            return this.issueDate;
        }

        public void setIssueDate(Date issueDate) {
            this.issueDate = issueDate;
        }

        public IssuingAuthority getIssuingAuthority() {
            return this.issuingAuthority;
        }

        public void setIssuingAuthority(IssuingAuthority issuingAuthority) {
            this.issuingAuthority = issuingAuthority;
        }

        @Entity
        public static class IssuingAuthority extends AbstractPojo {
            private static final long serialVersionUID = 7587925526133195307L;

            private String name;
            private CountryCodeEnumType country;
            private String city;
            private String postalCode;

            public String getName() {
                return this.name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public CountryCodeEnumType getCountry() {
                return this.country;
            }

            public void setCountry(CountryCodeEnumType country) {
                this.country = country;
            }

            public String getCity() {
                return this.city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getPostalCode() {
                return this.postalCode;
            }

            public void setPostalCode(String postalCode) {
                this.postalCode = postalCode;
            }

        }

    }

    public enum PublicationProfileCode {
        ARTICLE, BOOK, CONFERENCE_PAPER, UNSPECIFIED;
    }

    public interface PublicationInfo extends Serializable {
        /**
         * @return A descriptive or distinctive name of the publication
         */
        String getTitle();

        /**
         * 
         * @return The date of the publication.
         */
        Date getDate();

        String getAbstractText();

        String getComment();

        /**
         * The International Standard Serial Number is an eight-digit number
         * which identifies all periodical publications as such, including
         * electronic serials. Each ISSN assigned to a serial publication is
         * registered in an international database: the ISSN Register. The ISSN
         * is defined by the ISO 3297 standard. The ISSN can be applied to
         * series of books. A book belonging to a particular series will have
         * both an ISSN (identifying the series) and an ISBN (identifying the
         * given monograph as such).
         * 
         * @return
         */
        String getIssn();

        Copyright getCopyright();

        /**
         * Identifies by name, the author, editor, illustrator, or contributor
         * // of the publication.
         * 
         * @return
         */
        List<Contributor> getContributors();
    }

    private static abstract class AbstractPublicationInfo implements PublicationInfo {
        private static final long serialVersionUID = 4877136416392413343L;

        private String title;
        private Date date;
        private String abstractText;
        private String comment;
        private String issn;
        private Copyright copyright;
        private List<Contributor> contributors;

        @Override
        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public Date getDate() {
            return this.date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        @Override
        public String getAbstractText() {
            return this.abstractText;
        }

        public void setAbstractText(String abstractText) {
            this.abstractText = abstractText;
        }

        @Override
        public String getComment() {
            return this.comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        @Override
        public String getIssn() {
            return this.issn;
        }

        public void setIssn(String issn) {
            this.issn = issn;
        }

        @Override
        public Copyright getCopyright() {
            return this.copyright;
        }

        public void setCopyright(Copyright copyright) {
            this.copyright = copyright;
        }

        @Override
        public List<Contributor> getContributors() {
            return this.contributors;
        }

        public void setContributors(List<Contributor> contributors) {
            this.contributors = contributors;
        }

    }

    /**
     * The exclusive right given by law for a certain term of years to an
     * author, composer, designer, etc. (or his assignee), to print, publish,
     * and sell copies of his original work.
     * 
     * @author rantunes
     * 
     */
    public static class Copyright implements Serializable {
        private static final long serialVersionUID = -3767039591083709707L;

        // A year associated with a copyright.
        private String year;
        // The name of the copyright holder and a description of the claimed
        // rights to the work.
        // For example, 'All Rights Reserved'.
        // May include textual description of the date(s) related to the
        // copyright claim.
        private String text;

        public String getYear() {
            return this.year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

    /**
     * Identifies by name, the author, editor, illustrator, or contributor of
     * the publication.
     * 
     * @author rantunes
     * 
     */
    public static class Contributor implements Serializable {
        private static final long serialVersionUID = 9181399038147853365L;

        private PersonName name;
        // The typical publication function performed by a person; e.g. author,
        // editor, illustrator.
        private String role;

        public PersonName getName() {
            return this.name;
        }

        public void setName(PersonName name) {
            this.name = name;
        }

        public String getRole() {
            return this.role;
        }

        public void setRole(String role) {
            this.role = role;
        }

    }

    public static class Publication implements Serializable {
        private static final long serialVersionUID = -3314110483131404477L;

        private String description;
        private final PublicationProfileCode type;
        private final PublicationInfo info;

        public Publication(PublicationProfileCode publicationType, PublicationInfo publicationInfo) {
            this(publicationType, publicationInfo, null);
        }

        public Publication(PublicationProfileCode publicationType, PublicationInfo publicationInfo, String description) {
            this.type = publicationType;
            this.info = publicationInfo;
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public PublicationProfileCode getType() {
            return this.type;
        }

        public PublicationInfo getInfo() {
            return this.info;
        }

        /**
         * A literary composition forming materially part of a journal,
         * magazine, encyclopædia, or other collection, but treating a specific
         * topic distinctly and independently.
         * 
         * @author rantunes
         * 
         */
        public static class Article extends AbstractPublicationInfo {
            private static final long serialVersionUID = -2913162075597884603L;

            private String journalName;
            private String volume;
            private String issue;
            // The page number or page range where an article appears.
            private String pageNumbers;
            // The language in which the publication is printed.
            private LanguageCodeEnumType language;

            public String getJournalName() {
                return this.journalName;
            }

            public void setJournalName(String journalName) {
                this.journalName = journalName;
            }

            public String getVolume() {
                return this.volume;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public String getIssue() {
                return this.issue;
            }

            public void setIssue(String issue) {
                this.issue = issue;
            }

            public String getPageNumbers() {
                return this.pageNumbers;
            }

            public void setPageNumbers(String pageNumbers) {
                this.pageNumbers = pageNumbers;
            }

            public LanguageCodeEnumType getLanguage() {
                return this.language;
            }

            public void setLanguage(LanguageCodeEnumType language) {
                this.language = language;
            }

        }

        /**
         * A literary composition such as would occupy one or more volumes,
         * without regard to the material form or forms in which it actually
         * exists.
         * 
         * @author rantunes
         * 
         */
        public static class Book extends UnspecifiedPublication {
            private static final long serialVersionUID = 6006057696164145408L;

            private String edition;
            private String chapter;

            public String getEdition() {
                return this.edition;
            }

            public void setEdition(String edition) {
                this.edition = edition;
            }

            public String getChapter() {
                return this.chapter;
            }

            public void setChapter(String chapter) {
                this.chapter = chapter;
            }

        }

        /**
         * Written notes, memoranda, letters, documents, etc. prepared for
         * delivery at a conference.
         * 
         * @author rantunes
         * 
         */
        public static class ConferencePaper extends AbstractPublicationInfo {
            private static final long serialVersionUID = 3448578531475471506L;

            // The name of the speaking event.
            private String eventName;
            // The date of the event.
            private Date eventDate;
            // The name of the physicial or virtual location or facility hosting
            // a meeting or event.
            private String eventLocationName;
            private Location eventLocation;

            public String getEventName() {
                return this.eventName;
            }

            public void setEventName(String eventName) {
                this.eventName = eventName;
            }

            public Date getEventDate() {
                return this.eventDate;
            }

            public void setEventDate(Date eventDate) {
                this.eventDate = eventDate;
            }

            public String getEventLocationName() {
                return this.eventLocationName;
            }

            public void setEventLocationName(String eventLocationName) {
                this.eventLocationName = eventLocationName;
            }

            public Location getEventLocation() {
                return this.eventLocation;
            }

            public void setEventLocation(Location eventLocation) {
                this.eventLocation = eventLocation;
            }

        }

        /**
         * Bibliographic information for other publications, such as a Thesis,
         * Whitepaper, Report, Technical Documentation, etc.
         * 
         * @author rantunes
         * 
         */
        public static class UnspecifiedPublication extends AbstractPublicationInfo {
            private static final long serialVersionUID = 3356659353741010693L;

            // The International Standard Book Number is a 10-digit number
            // that uniquely identifies books and book-like products published
            // internationally.
            // The ISBN is defined by the ISO ISO 2108 standard.
            private String isbn;
            // The number of pages in a book or other publication.
            private int numberOfPages;
            // The name of the publisher.
            private String publisherName;
            // A particular place; local position of the publisher.
            private Location publisherLocation;

            public String getIsbn() {
                return this.isbn;
            }

            public void setIsbn(String isbn) {
                this.isbn = isbn;
            }

            public int getNumberOfPages() {
                return this.numberOfPages;
            }

            public void setNumberOfPages(int numberOfPages) {
                this.numberOfPages = numberOfPages;
            }

            public String getPublisherName() {
                return this.publisherName;
            }

            public void setPublisherName(String publisherName) {
                this.publisherName = publisherName;
            }

            public Location getPublisherLocation() {
                return this.publisherLocation;
            }

            public void setPublisherLocation(Location publisherLocation) {
                this.publisherLocation = publisherLocation;
            }

        }
    }
}
