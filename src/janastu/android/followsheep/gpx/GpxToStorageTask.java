package janastu.android.followsheep.gpx;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import janastu.android.followsheep.OSMTracker;
import janastu.android.followsheep.R;
import janastu.android.followsheep.db.DataHelper;
import janastu.android.followsheep.exception.ExportTrackException;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

public class GpxToStorageTask extends ExportTrackTask {

	private static final String TAG = GpxToStorageTask.class.getSimpleName();
     
	
	public GpxToStorageTask(Context context, long trackId) {
		super(context, trackId);		
	}
    @Override
    protected File getExportDirectory(Date startDate) throws ExportTrackException{
    	File sdRoot = DataHelper.getTrackDirectory(trackId);
    	
    	
    	// Create track directory if needed
    			if (! sdRoot.exists()) {
    				if (! sdRoot.mkdirs()) {
    					Log.w(TAG,"Failed to create directory [" 
    							+sdRoot.getAbsolutePath()+ "]");
    				}
    				
    				if (! sdRoot.exists()) {
    					// Specific hack for Google Nexus  S(See issue #168)
    					if (android.os.Build.MODEL.equals(OSMTracker.Devices.NEXUS_S)) {
    						// exportDirectoryPath always starts with "/"
    						
    						sdRoot.mkdirs();
    					}
    				}
    				
    				if (! sdRoot.exists()) {
    					throw new ExportTrackException(context.getResources().getString(R.string.error_create_track_dir,
    							sdRoot.getAbsolutePath()));
    				}
    			}
    	return sdRoot;
    }
    
	/*@Override
	protected File getExportDirectory(Date startDate) throws ExportTrackException {
		//File sdRoot = Environment.getExternalStorageDirectory();
		File sdRoot = DataHelper.getTrackDirectory(trackId);
		
		// The location that the user has specified gpx files 
		// and associated content to be written
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String userGPXExportDirectoryName = prefs.getString(
				OSMTracker.Preferences.KEY_STORAGE_DIR,	OSMTracker.Preferences.VAL_STORAGE_DIR);

		boolean directoryPerTrack = prefs.getBoolean(OSMTracker.Preferences.KEY_OUTPUT_DIR_PER_TRACK, 
				OSMTracker.Preferences.VAL_OUTPUT_GPX_OUTPUT_DIR_PER_TRACK);
				
		// Create the path to the directory to which we will be writing
		// Trim the directory name, as additional spaces at the end will 
		// not allow the directory to be created if required
		String exportDirectoryPath = userGPXExportDirectoryName.trim();
		String perTrackDirectory = "";
		if (directoryPerTrack) {
			// If the user wants a directory per track, then create a name for the destination directory
			// based on the start date of the track
			perTrackDirectory = File.separator + DataHelper.FILENAME_FORMATTER.format(startDate);
		}
		
		// Create a file based on the path we've generated above
		//File trackGPXExportDirectory = new File(sdRoot + exportDirectoryPath + perTrackDirectory);
		File trackGPXExportDirectory = new File(sdRoot + perTrackDirectory);

		// Create track directory if needed
		if (! trackGPXExportDirectory.exists()) {
			if (! trackGPXExportDirectory.mkdirs()) {
				Log.w(TAG,"Failed to create directory [" 
						+trackGPXExportDirectory.getAbsolutePath()+ "]");
			}
			
			if (! trackGPXExportDirectory.exists()) {
				// Specific hack for Google Nexus  S(See issue #168)
				if (android.os.Build.MODEL.equals(OSMTracker.Devices.NEXUS_S)) {
					// exportDirectoryPath always starts with "/"
					trackGPXExportDirectory = new File(exportDirectoryPath + perTrackDirectory);
					trackGPXExportDirectory.mkdirs();
				}
			}
			
			if (! trackGPXExportDirectory.exists()) {
				throw new ExportTrackException(context.getResources().getString(R.string.error_create_track_dir,
						trackGPXExportDirectory.getAbsolutePath()));
			}
		}

		return trackGPXExportDirectory;
	}*/	

	@Override
	protected boolean exportMediaFiles() {
		return false;
	}
	
	@Override
	protected boolean updateExportDate() {
		return false;
	}
}
