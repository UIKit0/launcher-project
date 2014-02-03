package uk.ks.jarvis.launcher.fragments;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import uk.ks.jarvis.launcher.R;
import uk.ks.jarvis.launcher.adapters.ListViewAdapter;
import uk.ks.jarvis.launcher.beans.Application;
import uk.ks.jarvis.launcher.helpers.ApplicationHelper;

/**
 * Created by ksk on 6/1/13.
 */
public abstract class BaseListFragment extends Fragment implements SearchView.OnQueryTextListener {

    private View view;
    private FragmentActivity activity;
    private ApplicationHelper appsHelper;
    protected BaseAdapter adapter;
    private ListView lView;
    private List<Application> applicationList;
	private Intent intent;

	private SearchView mSearchView;
	private MenuItem searchItem;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	    setHasOptionsMenu(true);
	    intent = getActivity().getIntent();
        activity = getActivity();
        appsHelper = new ApplicationHelper(activity);
        applicationList = fillApplicationList();//appsHelper.getApplication();
        adapter = new ListViewAdapter(activity, applicationList);
//	    setupSearchView(searchItem);
        lView = (ListView) getActivity().findViewById(R.id.list_fragment);
        lView.setAdapter(adapter);

        lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                appsHelper.removeApp(applicationList.get(position).getAppPackage());
//                applicationList = fillApplicationList();// appsHelper.getApplication();
                appsHelper.startApp(applicationList.get(position).getAppPackage());
                lView.invalidateViews();
	            return true;
            }
        });

//        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                appsHel per.startApp(applicationList.get(position).getAppPackage());
//                lView.invalidateViews();
//            }
//        });

	    lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			    intent.putExtra(Application.APPLICATION_PACKAGE, applicationList.get(position).getAppPackage());
		        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
			    transaction.replace(R.id.holder_fragment, new DetailApplicationFragment());
			    transaction.addToBackStack(null);
			    transaction.commit();
		    }
	    });
	    super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart () {
        super.onStart();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		getActivity().getMenuInflater().inflate(R.menu.main, menu);

		searchItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) searchItem.getActionView();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.addToBackStack(null);

		switch (item.getItemId())
		{
			case R.id.like_application:
				transaction.replace(R.id.holder_fragment, new FavoriteApplicationListFragment());
				transaction.commit();
				return true;
			case R.id.all_application:
				transaction.replace(R.id.holder_fragment, new AllApplicationListFragment());
				transaction.commit();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

    protected abstract List<Application> fillApplicationList();
    protected abstract void updateListView();


	private void setupSearchView(MenuItem searchItem) {

		if (isAlwaysExpanded()) {
			mSearchView.setIconifiedByDefault(false);
		} else {
			searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
					| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}

		SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
		if (searchManager != null) {
			List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

			SearchableInfo info = searchManager.getSearchableInfo(activity.getComponentName());
			for (SearchableInfo inf : searchables) {
				if (inf.getSuggestAuthority() != null
						&& inf.getSuggestAuthority().startsWith("applications")) {
					info = inf;
				}
			}
			mSearchView.setSearchableInfo(info);
		}

		mSearchView.setOnQueryTextListener(this);
	}

	public boolean onQueryTextChange(String newText) {
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	public boolean onClose() {
		return false;
	}

	protected boolean isAlwaysExpanded() {
		return false;
	}
}
