package uk.ks.jarvis.launcher.databases.beans;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by root on 6/20/13.
 */
public class ApplicationTagsDB {

	private static final long serialVersionUID = -881874382347497L;

	@DatabaseField(id = true)
	private Long id;

	@DatabaseField(canBeNull = false, uniqueCombo=true)
	private String applicationPackage;

	@DatabaseField(canBeNull = false, uniqueCombo=true)
	private String applicationTag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationPackage() {
		return applicationPackage;
	}

	public void setApplicationPackage(String applicationPackage) {
		this.applicationPackage = applicationPackage;
	}

	public String getApplicationTag() {
		return applicationTag;
	}

	public void setApplicationTag(String applicationTag) {
		this.applicationTag = applicationTag;
	}
}
