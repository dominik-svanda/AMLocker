package com.svanda.amlocker.activities;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class Utilities {
	
	/*
	 * Get all installed application on mobile and return a list
	 * @param	c	Context of application
	 * @return	list of installed applications
	 */
	public static List<ResolveInfo> getInstalledApplications(Context c) {
		//return c.getPackageManager().getInstalledPackages(PackageManager.GET_META_DATA);
		PackageManager pm = c.getPackageManager();
		Intent i = new Intent("android.intent.action.MAIN");
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> lst = pm.queryIntentActivities(i, 0);
		return lst;
	}
	public static List<ResolveInfo> getLaunchers(Context c) {
		PackageManager pm = c.getPackageManager();
		Intent i = new Intent("android.intent.action.MAIN");
		i.addCategory("android.intent.category.HOME");
		List<ResolveInfo> lst = pm.queryIntentActivities(i, 0);
		return lst;
	}
	/*public static boolean launchApp(Context c, PackageManager pm, String pkgName) {
		// query the intent for lauching 
		Intent intent = pm.getLaunchIntentForPackage(pkgName);
		// if intent is available
		if(intent != null) {
			try {
				// launch application
				c.startActivity(intent);
				// if succeed
				return true;
			
			// if fail
			} catch(ActivityNotFoundException ex) {
				// quick message notification
				Toast toast = Toast.makeText(c, "Application Not Found", Toast.LENGTH_LONG);
				// display message
				toast.show();
			}
		}
		// by default, fail to launch
		return false;
	}*/
}
