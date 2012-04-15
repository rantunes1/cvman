package com.glintt.cvm.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hr_xml._3.LanguageCodeEnumType;
import com.glintt.cvm.model.ProfessionalInfo.PublicationInfo;
import com.glintt.cvm.model.ProfessionalInfo.PublicationProfileCode;

@Entity
@XmlRootElement(name = "publication")
public class Publication extends AbstractEntity {
	private static final long serialVersionUID = -3314110483131404477L;

	private String description;
	@Column(nullable = false)
	private PublicationProfileCode type;
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	private AbstractPublicationInfo info;

	protected void setType(PublicationProfileCode type) {
		this.type = type;
	}

	protected void setInfo(AbstractPublicationInfo info) {
		this.info = info;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.info == null) ? 0 : this.info.hashCode());
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Publication other = (Publication) obj;
		if (this.info == null) {
			if (other.info != null) {
				return false;
			}
		} else if (!this.info.equals(other.info)) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		return true;
	}

	/**
	 * A literary composition forming materially part of a journal, magazine,
	 * encyclopædia, or other collection, but treating a specific topic
	 * distinctly and independently.
	 * 
	 * @author rantunes
	 * 
	 */
	@Entity
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
	 * A literary composition such as would occupy one or more volumes, without
	 * regard to the material form or forms in which it actually exists.
	 * 
	 * @author rantunes
	 * 
	 */
	@Entity
	public static class Book extends Publication.UnspecifiedPublication {
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
	 * Written notes, memoranda, letters, documents, etc. prepared for delivery
	 * at a conference.
	 * 
	 * @author rantunes
	 * 
	 */
	@Entity
	public static class ConferencePaper extends AbstractPublicationInfo {
		private static final long serialVersionUID = 3448578531475471506L;

		// The name of the speaking event.
		private String eventName;
		// The date of the event.
		@Temporal(TemporalType.DATE)
		private Date eventDate;
		// The name of the physicial or virtual location or facility hosting
		// a meeting or event.
		private String eventLocationName;
		@OneToOne(cascade = CascadeType.ALL)
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
	@Entity
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
		@OneToOne(cascade = CascadeType.ALL)
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