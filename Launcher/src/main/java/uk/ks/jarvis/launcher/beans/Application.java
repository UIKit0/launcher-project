package uk.ks.jarvis.launcher.beans;

import android.graphics.drawable.Drawable;

/**
 * Created by ksk on 5/29/13.
 */
public class Application {

    private String appPackage;
    private Drawable appIcon;
    private String appLabel;
    private Boolean isLikeApp;
    private Boolean isNewApp;
    private String processName;
    private Long firstInstallTime;
    private Long countLaunch;

	public static final String APPLICATION_PACKAGE = "APPLICATION_PACKAGE";

    public Application(String appPackage, Drawable appIcon, String appLabel, Boolean likeApp,
                       Boolean newApp, String processName, Long firstInstallTime, Long countLaunch) {
        this.appPackage = appPackage;
        this.appIcon = appIcon;
        this.appLabel = appLabel;
        isLikeApp = likeApp;
        isNewApp = newApp;
        this.processName = processName;
        this.firstInstallTime = firstInstallTime;
        this.countLaunch = countLaunch;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public Boolean getLikeApp() {
        return isLikeApp;
    }

    public void setLikeApp(Boolean likeApp) {
        isLikeApp = likeApp;
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
