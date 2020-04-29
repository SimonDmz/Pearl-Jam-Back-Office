package fr.insee.pearljam.api.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
* Entity OrganizationUnit : represent the entity table in DB
* 
* @author Claudel Benjamin
* 
*/
@Entity
@Table
public class OrganizationUnit {
	
	@Id
	public String id;
	
	@Column(length=255)
	public String label;
	
	@Enumerated(EnumType.STRING)
	@Column(length=8)
	public OrganizationUnitType organizationUnitType;
	
	@OneToMany(mappedBy = "organizationUnit", cascade = CascadeType.ALL)
    private List<Visibility> visibilities;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the organisationUnitType
	 */
	public OrganizationUnitType getOrganisationUnitType() {
		return organizationUnitType;
	}

	/**
	 * @param organizationUnitType the organisationUnitType to set
	 */
	public void setOrganisationUnitType(OrganizationUnitType organizationUnitType) {
		this.organizationUnitType = organizationUnitType;
	}

	/**
	 * @return the visibilities
	 */
	public List<Visibility> getVisibilities() {
		return visibilities;
	}

	/**
	 * @param visibilities the visibilities to set
	 */
	public void setVisibilities(List<Visibility> visibilities) {
		this.visibilities = visibilities;
	}
	
}
