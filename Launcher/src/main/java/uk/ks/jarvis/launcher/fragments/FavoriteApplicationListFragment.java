package uk.ks.jarvis.launcher.fragments;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.ks.jarvis.launcher.beans.Application;
import uk.ks.jarvis.launcher.databases.HelperFactory;
import uk.ks.jarvis.launcher.helpers.ApplicationHelper;

/**
 * Created by ksk on 5/29/13.
 */
public class FavoriteApplicationListFragment extends BaseListFragment {

	private ArrayList<Application> l;

	@Override
    protected List<Application> fillApplicationList() {
        List<String> likeApplicationList = getLikeApplicationDB();
        List<Application> result = new ArrayList<Application>();
        if (likeApplicationList.isEmpty()) {
            return new ArrayList<Application>();
        }
	    l = new ApplicationHelper(getActivity()).getApplication();
        for(Application application: new ApplicationHelper(getActivity()).getApplication()) {
            if(likeApplicationList.contains(application.getAppPackage())) {
                application.setLikeApp(Boolean.TRUE);
                result.add(application);
            }
        }
        return result;
    }

    @Override
    protected void updateListView() {
        adapter.notifyDataSetChanged();
    }

    private List<String> getLikeApplicationDB() {
        try {
            return HelperFactory.getHelper().getApplicationDAO().getAllLikeApplications();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
