package fr.insee.pearljam.api.controller;

import io.swagger.v3.oas.annotations.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* HealthCheck is the Controller using to check if API is alive
* 
* @author Laurent Caouissin
* 
*/
@RestController
@RequestMapping(path = "/api")
public class HealthCheckController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckController.class);

	@Operation(summary = "Healthcheck, check if api is alive")
	@GetMapping(path = "/healthcheck")
	public ResponseEntity<Object> healthCheck() {
		LOGGER.debug("HealthCheck");
		return ResponseEntity.ok().build();
		
	}
}
