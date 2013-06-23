package uk.ks.jarvis.launcher.adapters;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import uk.ks.jarvis.launcher.R;
import uk.ks.jarvis.launcher.beans.Application;
import uk.ks.jarvis.launcher.databases.HelperFactory;

/**
 * Created by ksk on 5/29/13.
 */
public class ListViewAdapter extends BaseAdapter {

    private final FragmentActivity activity;
    private final List<Application> applicationList;
    private final LayoutInflater inflater;
    private List<ActivityManager.RunningAppProcessInfo> processes;
    private List<String> favoriteApplicationList;

    public ListViewAdapter(FragmentActivity activity, List<Application> applicationList) {
        this.activity = activity;
        this.applicationList = applicationList;
        this.inflater = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fillFavoriteApplicationList();
    }

    @Override
    public int getCount() {
        return applicationList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, final View view, ViewGroup viewGroup) {
        View contentView = view;
        if (view == null) {
            contentView = inflater.inflate(R.layout.list_row, null);
        }
        TextView label = (TextView)contentView.findViewById(R.id.label); // title
        TextView isLaunch = (TextView)contentView.findViewById(R.id.isLaunch); // duration
        ImageView appIcon =(ImageView)contentView.findViewById(R.id.icon); // thumb image
	    TextView tags = (TextView) contentView.findViewById(R.id.tags);
        final ImageView isAppLike =(ImageView)contentView.findViewById(R.id.isLike); // thumb image

        label.setText(applicationList.get(i).getAppLabel());
	    tags.setText(getTags(applicationList.get(i).getAppPackage()));
        appIcon.setImageDrawable(applicationList.get(i).getAppIcon());
        isLaunch.setText(isAppRunning(applicationList.get(i).getProcessName()));
        isAppLike.setImageDrawable(applicationList.get(i).getLikeApp() ? activity.getResources().getDrawable(R.drawable.ic_action_star) : activity.getResources().getDrawable(R.drawable.ic_action_un_star));

        isAppLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view != null) {
                    if (applicationList.get(i).getLikeApp()) {
                        isAppLike.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_action_un_star));
                        Toast.makeText(view.getContext(), applicationList.get(i).getAppLabel() + " is remove from like list :(", Toast.LENGTH_LONG).show();
                    } else {
                        isAppLike.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_action_star));
                        Toast.makeText(view.getContext(), applicationList.get(i).getAppLabel() + " is add to like list :)", Toast.LENGTH_LONG).show();
                    }
                }
                try {
                    saveApplicationInToLikeList(i);
                    //favoriteApplicationList = HelperFactory.getHelper().getApplicationDAO().getAllLikeApplicationsPackages();
                    //notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        return contentView;
    }

    private String isAppRunning(String processName){
        ActivityManager manager =
                (ActivityManager) activity.getSystemService(activity.ACTIVITY_SERVICE);
        processes = manager.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo r : processes){
            if(r.processName.equals(processName)){
                return "Yes";
            }
        }
        return "No";
    }

    private void fillFavoriteApplicationList() {
        try {
            favoriteApplicationList = HelperFactory.getHelper().getApplicationDAO().getAllLikeApplicationsPackages();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void saveApplicationInToLikeList(int i) throws SQLException {
        HelperFactory.getHelper().getApplicationDAO().saveApplicationsDBLikeDislike(applicationList.get(i).getAppPackage(), !applicationList.get(i).getLikeApp());
    }

	private String getTags(String applicationPackage) {
		List<String> tags = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			tags = HelperFactory.getHelper().getApplicationTagsDAO().getAllApplicationTagsByPackage(applicationPackage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(String tag: tags) {
			stringBuffer.append(tag);
			stringBuffer.append("; ");
		}

		return stringBuffer.toString();
	}
}
