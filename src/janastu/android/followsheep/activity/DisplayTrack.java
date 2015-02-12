package janastu.android.followsheep.activity;

import janastu.android.followsheep.db.TrackContentProvider.Schema;
import janastu.android.followsheep.util.ThemeValidator;
import janastu.android.followsheep.view.DisplayTrackView;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ViewGroup.LayoutParams;

/**
 * Displays current track in 2D view.
 * 
 * @author Nicolas Guillaumin
 *
 */
public class DisplayTrack extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = DisplayTrack.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Set application theme according to user settings
		setTheme(getResources().getIdentifier(ThemeValidator.getValidTheme(
				PreferenceManager.getDefaultSharedPreferences(this), getResources()), null, null));
		
		super.onCreate(savedInstanceState);
		
		// Create special view and displays it
		DisplayTrackView dtv = new DisplayTrackView(this, getIntent().getExtras().getLong(Schema.COL_TRACK_ID));
		dtv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		setTitle(getTitle() + ": #" + getIntent().getExtras().getLong(Schema.COL_TRACK_ID));
		setContentView(dtv);		
	}	
	
}
