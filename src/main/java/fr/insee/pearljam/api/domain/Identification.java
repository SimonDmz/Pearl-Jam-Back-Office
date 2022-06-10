package fr.insee.pearljam.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Identification implements Serializable {

    private static final long serialVersionUID = 1987l;

    /**
     * Identification id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean move;

    @Column
    private String identification;

    @Column
    private String access;

    @Column
    private String situation;

    @Column
    private String category;

    @Column
    private String occupant;

    /**
     * The SurveyUnit associated to Identification
     */
    @OneToOne
    private SurveyUnit surveyUnit;

    public Identification() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isMove() {
        return this.move;
    }

    public Boolean getMove() {
        return this.move;
    }

    public void setMove(Boolean move) {
        this.move = move;
    }

    public String getIdentification() {
        return this.identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getAccess() {
        return this.access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getSituation() {
        return this.situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOccupant() {
        return this.occupant;
    }

    public void setOccupant(String occupant) {
        this.occupant = occupant;
    }

    public SurveyUnit getSurveyUnit() {
        return this.surveyUnit;
    }

    public void setSurveyUnit(SurveyUnit surveyUnit) {
        this.surveyUnit = surveyUnit;
    }
}
