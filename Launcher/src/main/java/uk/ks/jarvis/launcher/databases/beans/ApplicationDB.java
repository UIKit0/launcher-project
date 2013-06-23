package uk.ks.jarvis.launcher.databases.beans;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by ksk on 5/30/13.
 */
public class ApplicationDB {

    private static final long serialVersionUID = -787482382347497L;

    @DatabaseField(id = true)
    private String applicationPackage;

    @DatabaseField
    private String applicationLabel;

    @DatabaseField
    private Boolean isLike;

    @DatabaseField
    private Boolean isNewApp;

    @DatabaseField
    private String processName;

    @DatabaseField
    private Long firstInstallTime;

    @DatabaseField
    private Long countLaunch;

    public String getApplicationPackage() {
        return applicationPackage;
    }

    public void setApplicationPackage(String applicationPackage) {
        this.applicationPackage = applicationPackage;
    }

    public String getApplicationLabel() {
        return applicationLabel;
    }

    public void setApplicationLabel(String applicationLabel) {
        this.applicationLabel = applicationLabel;
    }

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }

    public Boolean getNewApp() {
        return isNewApp;
    }

    public void setNewApp(Boolean newApp) {
        isNewApp = newApp;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Long getFirstInstallTime() {
        return firstInstallTime;
    }

    public void setFirstInstallTime(Long firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }

    public Long getCountLaunch() {
        return countLaunch;
    }

    public void setCountLaunch(Long countLaunch) {
        this.countLaunch = countLaunch;
    }
}
