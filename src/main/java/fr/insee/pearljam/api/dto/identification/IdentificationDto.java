package fr.insee.pearljam.api.dto.identification;

import fr.insee.pearljam.api.domain.Identification;

public class IdentificationDto {

    private Boolean move;

    private String identification;

    private String access;

    private String situation;

    private String category;

    private String occupant;

    public IdentificationDto() {
    }

    public IdentificationDto(Identification ident) {
        this.move = ident.isMove();
        this.identification = ident.getIdentification();
        this.access = ident.getAccess();
        this.situation = ident.getSituation();
        this.category = ident.getCategory();
        this.occupant = ident.getOccupant();
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

}
