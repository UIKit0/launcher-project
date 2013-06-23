package uk.ks.jarvis.launcher.databases.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ks.jarvis.launcher.beans.Application;
import uk.ks.jarvis.launcher.databases.HelperFactory;
import uk.ks.jarvis.launcher.databases.beans.ApplicationDB;

/**
 * Created by ksk on 5/30/13.
 */
public class ApplicationDAO extends BaseDaoImpl<ApplicationDB, String> {

    public ApplicationDAO(ConnectionSource connectionSource,
                          Class<ApplicationDB> dataClass) throws SQLException {
            super(connectionSource, dataClass);
    }

    public void saveApplicationsDB(Application application) throws SQLException {
        ApplicationDB applicationsDB = new ApplicationDB();

        applicationsDB.setApplicationPackage(application.getAppPackage());
        applicationsDB.setApplicationLabel(application.getAppLabel());
        applicationsDB.setLike(Boolean.FALSE);
        applicationsDB.setNewApp(Boolean.FALSE);
        applicationsDB.setProcessName(application.getProcessName());
        applicationsDB.setFirstInstallTime(application.getFirstInstallTime());
        applicationsDB.setCountLaunch(application.getCountLaunch());

        HelperFactory.getHelper().getApplicationDAO().createIfNotExists(applicationsDB);
    }

    public void saveApplicationsDBLikeDislike(String appPackage, Boolean isLike) throws SQLException {
        UpdateBuilder<ApplicationDB, String> updateBuilder =
                HelperFactory.getHelper().getApplicationDAO().updateBuilder();
        updateBuilder.updateColumnValue("isLike", isLike);
        updateBuilder.where().eq("applicationPackage", appPackage);

        updateBuilder.update();
    }

    public Map<String,Boolean> getAllApplicationsDB() throws SQLException{
        Map<String,Boolean> result = new HashMap<String, Boolean>();
        for(ApplicationDB applicationDB: this.queryForAll()) {
            result.put(applicationDB.getApplicationPackage(), applicationDB.getLike());
        }
        return result;
    }

    public List<String> getAllLikeApplications() throws SQLException{
        List<String> result = new ArrayList<String>();
        QueryBuilder<ApplicationDB, String> queryBuilder =
                HelperFactory.getHelper().getApplicationDAO().queryBuilder();
        queryBuilder.where().eq("isLike",true);
        PreparedQuery<ApplicationDB> preparedQuery = queryBuilder.prepare();
        for(ApplicationDB applicationsDB: HelperFactory.getHelper().getApplicationDAO().query(preparedQuery)) {
            result.add(applicationsDB.getApplicationPackage());
        }
        return result;
    }

    public List<String> getAllLikeApplicationsPackages() throws SQLException{
        List<String> result = new ArrayList<String>();
        QueryBuilder<ApplicationDB, String> queryBuilder =
                HelperFactory.getHelper().getApplicationDAO().queryBuilder();
        queryBuilder.where().eq("isLike",true);
        PreparedQuery<ApplicationDB> preparedQuery = queryBuilder.prepare();
        for (ApplicationDB app : HelperFactory.getHelper().getApplicationDAO().query(preparedQuery)){
            result.add(app.getApplicationPackage());
        }
        return result;
    }
}

