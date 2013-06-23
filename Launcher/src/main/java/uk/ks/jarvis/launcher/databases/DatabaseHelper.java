package uk.ks.jarvis.launcher.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import uk.ks.jarvis.launcher.databases.beans.ApplicationDB;
import uk.ks.jarvis.launcher.databases.beans.ApplicationTagsDB;
import uk.ks.jarvis.launcher.databases.dao.ApplicationDAO;
import uk.ks.jarvis.launcher.databases.dao.ApplicationTagsDAO;

/**
 * Created by ksk on 5/30/13.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "jarvis_launcher.db";
    private static final int DATABASE_VERSION = 6;

    private ApplicationDAO applicationDAO = null;
	private ApplicationTagsDAO applicationTagsDAO = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ApplicationDB.class);
            TableUtils.createTable(connectionSource, ApplicationTagsDB.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, ApplicationDB.class, Boolean.TRUE);
            TableUtils.dropTable(connectionSource, ApplicationTagsDB.class, Boolean.TRUE);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    public ApplicationDAO getApplicationDAO() throws SQLException {
        if (applicationDAO == null) {
            applicationDAO = new ApplicationDAO(getConnectionSource(), ApplicationDB.class);
        }

        return applicationDAO;
    }

	public ApplicationTagsDAO getApplicationTagsDAO() throws SQLException {
		if (applicationTagsDAO == null) {
			applicationTagsDAO = new ApplicationTagsDAO(getConnectionSource(), ApplicationTagsDB.class);
		}

		return applicationTagsDAO;
	}

    @Override
    public void close(){
        super.close();
        applicationDAO = null;
	    applicationTagsDAO = null;
    }
}
