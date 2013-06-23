package uk.ks.jarvis.launcher.fragments;

import java.util.ArrayList;
import java.util.List;

import uk.ks.jarvis.launcher.beans.Application;
import uk.ks.jarvis.launcher.helpers.ApplicationHelper;

/**
 * Created by root on 6/19/13.
 */
public class AllApplicationListFragment extends BaseListFragment {

	@Override
	protected List<Application> fillApplicationList() {
		List<Application> result = new ArrayList<Application>();
		getApplications(result);
		getWallpapers(result);
		return result;
	}

	private void getWallpapers(List<Application> result) {
		for(Application application: new ApplicationHelper(getActivity()).getWallpaper()) {
			result.add(application);
		}
	}

	private void getApplications(List<Application> result) {
		for(Application application: new ApplicationHelper(getActivity()).getApplication()) {
			result.add(application);
		}
	}

	@Override
	protected void updateListView() {

	}
}
