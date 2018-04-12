package org.product.webserver.md.entities;

import java.math.BigDecimal;
import java.util.Map;

public class Artifactory {

	private String name;
	private License license;
	private Map<Integer ,Repository> repositories;
	private BigDecimal hits;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public BigDecimal getHits() {
		return hits;
	}

	public void setHits(BigDecimal hits) {
		this.hits = hits;
	}

	public void setRepositories(Map<Integer, Repository> repositories) {
		this.repositories = repositories;
	}

	public Map<Integer, Repository> getRepositories() {
		return repositories;
	}
}