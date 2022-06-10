package fr.insee.pearljam.api.service;

import fr.insee.pearljam.api.domain.Identification;

public interface IdentificationService {

    public Identification findBySurveyUnitId(String id);
}
