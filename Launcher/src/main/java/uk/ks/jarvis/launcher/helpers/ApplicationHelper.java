package uk.ks.jarvis.launcher.helpers;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.service.wallpaper.WallpaperService;
import android.support.v4.app.FragmentActivity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.ks.jarvis.launcher.beans.Application;

/**
 * Created by ksk on 5/29/13.
 */
public class ApplicationHelper {

    private long isNewAppDuration = 604800000L;
    private FragmentActivity activity;

    public ApplicationHelper(FragmentActivity activity) {
        this.activity = activity;
    }

    public ArrayList<Application> getApplication() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> list = activity.getApplicationContext().getPackageManager().queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED);
        return getApplicationFromResolverInfo(list);
    }

	public List<Application> getWallpaper() {
		return getServiceFromResolverInfo(activity.getApplicationContext().getPackageManager().queryIntentServices(new Intent(WallpaperService.SERVICE_INTERFACE), PackageManager.PERMISSION_GRANTED));
	}

	private ArrayList<Application> getApplicationFromResolverInfo(List<ResolveInfo> list) {
		ArrayList<Application> results = new ArrayList<Application>();
		PackageManager pm = this.activity.getPackageManager();
		long currentDate = new Date().getTime();
		for (ResolveInfo rInfo : list) {
			results.add(new Application(
					rInfo.activityInfo.packageName,
					rInfo.activityInfo.loadIcon(pm),
					rInfo.activityInfo.applicationInfo.loadLabel(pm).toString(),
					Boolean.FALSE,
					(currentDate-getFirstInstallTime(rInfo.activityInfo.packageName)) < isNewAppDuration,
					rInfo.activityInfo.processName,
					getFirstInstallTime(rInfo.activityInfo.packageName),
					0l
			));
		}
		return results;
	}

	private ArrayList<Application> getServiceFromResolverInfo(List<ResolveInfo> list) {
		ArrayList<Application> results = new ArrayList<Application>();
		PackageManager pm = this.activity.getPackageManager();
		long currentDate = new Date().getTime();
		for (ResolveInfo rInfo : list) {
			results.add(new Application(
					rInfo.serviceInfo.packageName,
					rInfo.serviceInfo.loadIcon(pm),
					rInfo.serviceInfo.applicationInfo.loadLabel(pm).toString(),
					Boolean.FALSE,
					(currentDate-getFirstInstallTime(rInfo.serviceInfo.packageName)) < isNewAppDuration,
					rInfo.serviceInfo.processName,
					getFirstInstallTime(rInfo.serviceInfo.packageName),
					0l
			));
		}
		return results;
	}

	public Long getFirstInstallTime(String appPackage) {
        try {
            return activity.getApplicationContext().getPackageManager().getPackageInfo(appPackage, 0).firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return new Date().getTime();
    }

    public void killProcess(String packageName){
	    Integer processId = android.os.Process.getUidForName(packageName);
        android.os.Process.killProcess(processId);
    }

    public void removeApp(String packageName){
        Uri packageURI = Uri.parse("package:"+packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        activity.startActivity(uninstallIntent);
    }

    public void startApp(String packageName){
        Intent LaunchIntent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
        activity.startActivity(LaunchIntent);
    }

	public void memoryGetter() {
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		ActivityManager activityManager = (ActivityManager) activity.getSystemService(activity.ACTIVITY_SERVICE);
		activityManager.getMemoryInfo(mi);
	}
}
