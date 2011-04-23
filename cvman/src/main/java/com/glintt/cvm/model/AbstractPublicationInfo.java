package com.glintt.cvm.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;

import com.glintt.cvm.model.ProfessionalInfo.Contributor;
import com.glintt.cvm.model.ProfessionalInfo.Copyright;
import com.glintt.cvm.model.ProfessionalInfo.PublicationInfo;

@Entity
@DiscriminatorColumn(length = 255)
public abstract class AbstractPublicationInfo extends AbstractPojo implements PublicationInfo {
    private static final long serialVersionUID = 4877136416392413343L;

    private String title;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String abstractText;
    private String comment;
    private String issn;

    @OneToOne(cascade = CascadeType.ALL)
    private Copyright copyright;
    @OneToMany(cascade = CascadeType.ALL)
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractPublicationInfo other = (AbstractPublicationInfo) obj;
        if (this.getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!this.getId().equals(other.getId()))
            return false;
        if (this.title == null) {
            if (other.title != null)
                return false;
        } else if (!this.title.equals(other.title))
            return false;
        return true;
    }

}