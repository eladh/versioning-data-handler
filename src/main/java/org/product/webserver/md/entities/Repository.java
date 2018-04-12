package org.product.webserver.md.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Repository {

	private Integer id;
	private RepoType repoType;

	@JsonCreator
	public Repository(Integer id, RepoType repoType) {
		this.id = id;
		this.repoType = repoType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RepoType getRepoType() {
		return repoType;
	}

	public void setRepoType(RepoType repoType) {
		this.repoType = repoType;
	}
}