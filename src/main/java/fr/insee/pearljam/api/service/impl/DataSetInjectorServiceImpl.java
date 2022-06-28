package fr.insee.pearljam.api.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.insee.pearljam.api.domain.ClosingCause;
import fr.insee.pearljam.api.domain.ClosingCauseType;
import fr.insee.pearljam.api.domain.SurveyUnit;
import fr.insee.pearljam.api.domain.Visibility;
import fr.insee.pearljam.api.repository.CampaignRepository;
import fr.insee.pearljam.api.repository.ClosingCauseRepository;
import fr.insee.pearljam.api.repository.SurveyUnitRepository;
import fr.insee.pearljam.api.service.DataSetInjectorService;

@Service
@Transactional
public class DataSetInjectorServiceImpl implements DataSetInjectorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSetInjectorServiceImpl.class);

	@Autowired
	private EntityManagerFactory emf;

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private ClosingCauseRepository clausingCauseRepository;

	@Autowired
	private SurveyUnitRepository surveyUnitRepository;

	public HttpStatus createDataSet() {
		EntityManager em = emf.createEntityManager();

		if (!campaignRepository.findAllIds().isEmpty()) {
			LOGGER.info("The database already contains a campaign, the dataset was not imported");
			return HttpStatus.NOT_MODIFIED;
		}
		LOGGER.info("Dataset creation start");
		try (
				InputStream sqlFileInputStream = getClass().getClassLoader()
						.getResource("dataset//insert_test_data.sql").openStream()) {
			BufferedReader sqlFileBufferedReader = new BufferedReader(new InputStreamReader(sqlFileInputStream));
			executeStatements(sqlFileBufferedReader, em);

		} catch (Exception e) {
			LOGGER.warn("Error during dataset creation");
			e.printStackTrace();
			return HttpStatus.NOT_MODIFIED;
		}
		LOGGER.info("Dataset creation end");
		return HttpStatus.OK;

	}

	public void updateDataSetDates() {
		// update visibilities
		campaignRepository.findById("SIMPSONS2020X00").get().getVisibilities().stream()
				.forEach(vis -> generateCurrentCampaignVisibility(vis));
		campaignRepository.findById("VQS2021X00").get().getVisibilities().stream()
				.forEach(vis -> generateCurrentCampaignVisibility(vis));
		campaignRepository.findById("ZCLOSEDX00").get().getVisibilities().stream()
				.forEach(vis -> generateClosedCampaignVisibility(vis));
		campaignRepository.findById("XCLOSEDX00").get().getVisibilities().stream()
				.forEach(vis -> generateClosedCampaignVisibility(vis));

		// update closingClause
		SurveyUnit su = surveyUnitRepository.findById("11").get();
		ClosingCause closingCause = new ClosingCause();
		closingCause.setSurveyUnit(su);
		closingCause.setType(ClosingCauseType.NPI);
		closingCause.setDate(DateUtils.addDays(new Date(), -3).getTime());
		ClosingCause savedClosingClause = clausingCauseRepository.save(closingCause);
		su.setClosingCause(savedClosingClause);

	}

	public HttpStatus deleteDataSet() {
		try (InputStream sqlFileInputStream = getClass().getClassLoader()
				.getResource("dataset//delete_data.sql").openStream()) {
			EntityManager em = emf.createEntityManager();
			BufferedReader sqlFileBufferedReader = new BufferedReader(new InputStreamReader(sqlFileInputStream));
			executeStatements(sqlFileBufferedReader, em);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpStatus.NOT_MODIFIED;
		}
		return HttpStatus.OK;

	}

	void executeStatements(BufferedReader br, EntityManager entityManager) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			entityManager.joinTransaction();
			entityManager.createNativeQuery(line).executeUpdate();
		}
	}

	void generateCurrentCampaignVisibility(Visibility visibility) {
		visibility.setManagementStartDate(DateUtils.addDays(new Date(), -7).getTime());
		visibility.setInterviewerStartDate(DateUtils.addDays(new Date(), -6).getTime());
		visibility.setIdentificationPhaseStartDate(DateUtils.addDays(new Date(), -5).getTime());
		visibility.setCollectionStartDate(DateUtils.addDays(new Date(), -4).getTime());
		visibility.setCollectionEndDate(DateUtils.addDays(new Date(), -3).getTime());
		visibility.setEndDate(DateUtils.addDays(new Date(), -1).getTime());
	}

	void generateClosedCampaignVisibility(Visibility visibility) {
		visibility.setManagementStartDate(DateUtils.addDays(new Date(), -4).getTime());
		visibility.setInterviewerStartDate(DateUtils.addDays(new Date(), -3).getTime());
		visibility.setIdentificationPhaseStartDate(DateUtils.addDays(new Date(), -2).getTime());
		visibility.setCollectionStartDate(DateUtils.addDays(new Date(), -1).getTime());
		visibility.setCollectionEndDate(DateUtils.addMonths(new Date(), 1).getTime());
		visibility.setEndDate(DateUtils.addMonths(new Date(), 2).getTime());
	}

}
