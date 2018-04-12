package org.product.webserver.webservices;

import org.product.webserver.md.ConfigurationService;
import org.product.webserver.md.entities.Artifactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */

@RestController
@RequestMapping("/app")
public class AppWS {

	private final ConfigurationService configurationService;

	@Autowired
	public AppWS(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@GetMapping("/")
	public Artifactory getArtifactory(){
		return configurationService.getArtifactory();
	}
}