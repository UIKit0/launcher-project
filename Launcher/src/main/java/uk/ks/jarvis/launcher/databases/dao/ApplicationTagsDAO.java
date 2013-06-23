package uk.ks.jarvis.launcher.databases.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.ks.jarvis.launcher.databases.HelperFactory;
import uk.ks.jarvis.launcher.databases.beans.ApplicationDB;
import uk.ks.jarvis.launcher.databases.beans.ApplicationTagsDB;

/**
 * Created by root on 6/20/13.
 */
public class ApplicationTagsDAO extends BaseDaoImpl<ApplicationTagsDB, Long> {

	public ApplicationTagsDAO(ConnectionSource connectionSource,
	                      Class<ApplicationTagsDB> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}

	public void saveTagForApplication(String appPackage, String tag) throws SQLException {
		ApplicationTagsDB applicationTagsDB = new ApplicationTagsDB();

		applicationTagsDB.setApplicationPackage(appPackage);
		applicationTagsDB.setApplicationTag(tag);

		HelperFactory.getHelper().getApplicationTagsDAO().createIfNotExists(applicationTagsDB);
	}

	public List<String> getAllApplicationTagsByPackage(String applicationPackage) throws SQLException{
		List<String> result = new ArrayList<String>();
		QueryBuilder<ApplicationTagsDB, Long> queryBuilder =
				HelperFactory.getHelper().getApplicationTagsDAO().queryBuilder();
		queryBuilder.where().eq("applicationPackage",applicationPackage);
		PreparedQuery<ApplicationTagsDB> preparedQuery = queryBuilder.prepare();
		for (ApplicationTagsDB app : HelperFactory.getHelper().getApplicationTagsDAO().query(preparedQuery)){
			result.add(app.getApplicationTag());
		}
		return result;
	}
}
