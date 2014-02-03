package uk.ks.jarvis.launcher.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.sql.SQLException;
import java.util.List;

import uk.ks.jarvis.launcher.R;
import uk.ks.jarvis.launcher.beans.Application;
import uk.ks.jarvis.launcher.databases.HelperFactory;
import uk.ks.jarvis.launcher.databases.dao.ApplicationTagsDAO;
import uk.ks.jarvis.launcher.helpers.ApplicationHelper;

/**
 * Created by ksk on 5/29/13.
 */
public class DetailApplicationFragment extends Fragment {

	private View view;
	private FragmentActivity activity;
	private ApplicationHelper appsHelper;
	protected BaseAdapter adapter;
	private ListView lView;
	private List<Application> applicationList;
	private Button addTagButton;
	private Button removeAppButton;
	private EditText addTagField;
	private Intent intent;
	private ApplicationTagsDAO applicationTagsDAO;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.detail_fragment, container, false);
		addTagButton = (Button) view.findViewById(R.id.addTagButton);
		removeAppButton = (Button) view.findViewById(R.id.removeAppButton);
		addTagField = (EditText) view.findViewById(R.id.addTagField);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		activity = getActivity();
		intent = activity.getIntent();
		applicationTagsDAO = getApplicationTagsDAO();
		appsHelper = new ApplicationHelper(activity);

		addTagButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				saveTagForApplication();
//				Toast.makeText(activity.getApplicationContext(), , Toast.LENGTH_LONG).show();
			}
		});

		removeAppButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				appsHelper.removeApp(intent.getStringExtra(Application.APPLICATION_PACKAGE));
			}
		});
		super.onActivityCreated(savedInstanceState);
	}

	private void saveTagForApplication() {
		String[] tags = addTagField.getText().toString().replaceAll("\\s", "").split("\\.");
		for(int i=0; i<tags.length; i++) {
			saveTag(tags[i]);
		}
	}

	private void saveTag(String tag) {
		try {
			applicationTagsDAO.saveTagForApplication(intent.getStringExtra(Application.APPLICATION_PACKAGE), tag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ApplicationTagsDAO getApplicationTagsDAO() {
		try {
			return HelperFactory.getHelper().getApplicationTagsDAO();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}