package org.product.webserver.md;

import com.google.common.collect.Maps;
import org.product.webserver.md.entities.*;
import org.product.webserver.utils.AspectsUtils;
import org.product.webserver.utils.ChangeInterceptor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;


@Service
public class ConfigurationService {

	private Config config;

	@PostConstruct
	public void init() {
		config = AspectsUtils.recreateInstanceWithInterceptor(generateDefaultData(),
				Config.class,new ChangeInterceptor("config"));
	}

	public Artifactory getArtifactory() {
		return config.getArtifactory();
	}

	//todo  eladh >> simple data init need to be in the test
	private Config generateDefaultData() {
		Config config = new Config();
		Artifactory artifactory = new Artifactory();
		artifactory.setName("DevServer");
		artifactory.setLicense(new License("ABC", "elad hirsch"));

		Map<Integer ,Repository> repositories = Maps.newConcurrentMap();
		IntStream.range(0,5).forEach(value -> repositories.put(value ,new Repository(value , getRandomRepoType())));
		artifactory.setRepositories(repositories);
		config.setArtifactory(artifactory);

		return config;
	}

	private RepoType getRandomRepoType() {
		Random random = new Random();
		RepoType[] values = RepoType.values();
		return values[random.nextInt(values.length)];
	}
}