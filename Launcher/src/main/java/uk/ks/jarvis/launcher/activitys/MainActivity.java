    package uk.ks.jarvis.launcher.activitys;

import android.support.v4.app.FragmentTransaction;
import java.sql.SQLException;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import java.util.List;

import uk.ks.jarvis.launcher.R;
import uk.ks.jarvis.launcher.beans.Application;
import uk.ks.jarvis.launcher.databases.HelperFactory;
import uk.ks.jarvis.launcher.databases.dao.ApplicationDAO;
import uk.ks.jarvis.launcher.fragments.FavoriteApplicationListFragment;
import uk.ks.jarvis.launcher.helpers.ApplicationHelper;

public class MainActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HelperFactory.setHelper(getApplicationContext());

        saveApplicationToDatabase();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.holder_fragment, new FavoriteApplicationListFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void saveApplicationToDatabase() {
        List<Application> applicationList = new ApplicationHelper(this).getApplication();
        try {
            ApplicationDAO applicationDao = HelperFactory.getHelper().getApplicationDAO();
            for(Application application: applicationList){
                applicationDao.saveApplicationsDB(application);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
