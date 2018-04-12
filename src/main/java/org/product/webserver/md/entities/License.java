
package org.product.webserver.md.entities;

import com.fasterxml.jackson.annotation.JsonCreator;




public class License {

	private String key;
	private String owner;

	@JsonCreator
	public License(String key, String owner) {
		this.key = key;
		this.owner = owner;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}