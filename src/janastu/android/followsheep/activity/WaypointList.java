package janastu.android.followsheep.activity;

import janastu.android.followsheep.db.TrackContentProvider;
import janastu.android.followsheep.db.WaypointListAdapter;
import janastu.android.followsheep.db.TrackContentProvider.Schema;
import android.app.ListActivity;
import android.database.Cursor;
import android.widget.CursorAdapter;

/**
 * Activity that lists the previous waypoints tracked by the user.
 * 
 * @author Nicolas Guillaumin
 * 
 */
public class WaypointList extends ListActivity {

	@Override
	protected void onResume() {
		Long trackId = getIntent().getExtras().getLong(Schema.COL_TRACK_ID);
		
		Cursor cursor = getContentResolver().query(TrackContentProvider.waypointsUri(trackId),
				null, null, null, Schema.COL_TIMESTAMP + " asc");
		startManagingCursor(cursor);
		setListAdapter(new WaypointListAdapter(WaypointList.this, cursor));
		
		super.onResume();
	}

	@Override
	protected void onPause() {
		CursorAdapter adapter = (CursorAdapter) getListAdapter();
		if (adapter != null) {
			// Properly close the adapter cursor
			Cursor cursor = adapter.getCursor();
			stopManagingCursor(cursor);
			cursor.close();
			setListAdapter(null);
		}

		super.onPause();
	}

}
