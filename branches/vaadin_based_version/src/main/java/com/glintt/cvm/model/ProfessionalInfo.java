package com.glintt.cvm.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hr_xml._3.ResourceRelationshipCodeEnumType;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

import com.glintt.cvm.api.AbstractOwnedEntity;

@Entity
public class ProfessionalInfo extends AbstractOwnedEntity<Person> {
	private static final long serialVersionUID = 2456109426437042121L;

	private String profileName;
	private String executiveSummary;

	@ManyToOne(cascade = CascadeType.ALL)
	private Person owner;

	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Certification> certifications;

	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Publication> publications;

	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Employer> employers;

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

	public Collection<Certification> getCertifications() {
		return this.certifications;
	}

	public void setCertifications(Collection<Certification> certifications) {
		this.certifications = certifications;
	}

	public Collection<Publication> getPublications() {
		return this.publications;
	}

	public void setPublications(Collection<Publication> publications) {
		this.publications = publications;
	}

	public Collection<Employer> getEmployers() {
		return this.employers;
	}

	public void setEmployers(Collection<Employer> employers) {
		this.employers = employers;
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

			@OneToOne(cascade = CascadeType.ALL)
			private Location location;

			public String getName() {
				return this.name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public Location getLocation() {
				return this.location;
			}

			public void setLocation(Location location) {
				this.location = location;
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
		Collection<Contributor> getContributors();
	}

	/**
	 * The exclusive right given by law for a certain term of years to an
	 * author, composer, designer, etc. (or his assignee), to print, publish,
	 * and sell copies of his original work.
	 * 
	 * @author rantunes
	 * 
	 */
	@Entity
	public static class Copyright extends AbstractPojo {
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
	@Entity
	public static class Contributor extends AbstractPojo {
		private static final long serialVersionUID = 9181399038147853365L;

		@OneToOne(cascade = CascadeType.ALL)
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

	@Entity
	public static class Employer extends AbstractPojo {
		private static final long serialVersionUID = -8555972669444760251L;

		private String name;
		private String url;
		private String headcount;

		@OneToOne(cascade = CascadeType.ALL)
		private OrganizationContacts contacts;

		@OneToOne(cascade = CascadeType.ALL)
		private TimePeriod employmentPeriod;

		@OneToMany(cascade = CascadeType.ALL)
		private Collection<Position> positions;

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getHeadcount() {
			return this.headcount;
		}

		public void setHeadcount(String headcount) {
			this.headcount = headcount;
		}

		public String getUrl() {
			return this.url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public OrganizationContacts getContacts() {
			return this.contacts;
		}

		public void setContacts(OrganizationContacts contacts) {
			this.contacts = contacts;
		}

		public TimePeriod getEmploymentPeriod() {
			return this.employmentPeriod;
		}

		public void setEmploymentPeriod(TimePeriod employmentPeriod) {
			this.employmentPeriod = employmentPeriod;
		}

		public Collection<Position> getPositions() {
			return this.positions;
		}

		public void setPositions(Collection<Position> positions) {
			this.positions = positions;
		}
	}

	@Entity
	public static class Position extends AbstractPojo {
		private static final long serialVersionUID = -7655905137694310988L;

		private String title;
		private String description;
		private String unitName;
		private ResourceRelationshipCodeEnumType relationshipType;

		@OneToOne(cascade = CascadeType.ALL)
		private Address address;

		@OneToOne(cascade = CascadeType.ALL)
		private TimePeriod employmentPeriod;

		public String getTitle() {
			return this.title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return this.description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getUnitName() {
			return this.unitName;
		}

		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}

		public ResourceRelationshipCodeEnumType getRelationshipType() {
			return this.relationshipType;
		}

		public void setRelationshipType(ResourceRelationshipCodeEnumType relationshipType) {
			this.relationshipType = relationshipType;
		}

		public Address getAddress() {
			return this.address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public TimePeriod getEmploymentPeriod() {
			return this.employmentPeriod;
		}

		public void setEmploymentPeriod(TimePeriod employmentPeriod) {
			this.employmentPeriod = employmentPeriod;
		}

	}

	@Entity
	public static class TimePeriod extends AbstractPojo {
		private static final long serialVersionUID = -6711183449975831043L;

		@Temporal(TemporalType.DATE)
		private Date startDate;

		@Temporal(TemporalType.DATE)
		private Date endDate;

		public Date getStartDate() {
			return this.startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return this.endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

	}
}
