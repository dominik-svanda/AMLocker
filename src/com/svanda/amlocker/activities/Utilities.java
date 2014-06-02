package com.svanda.amlocker.activities;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
/**
 * Provide lists of installed applications and home launchers
 * @author DomiNIK
 *
 */
public class Utilities {
	
	/**
	 * Get all installed application on mobile and return a list
	 * @param	c	Context of application
	 * @return	list of installed applications
	 */
	public static List<ResolveInfo> getInstalledApplications(Context c) {
		PackageManager pm = c.getPackageManager();
		Intent i = new Intent("android.intent.action.MAIN");
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> lst = pm.queryIntentActivities(i, 0);
		return lst;
	}
	/**
	 * Get all installed home launchers on mobile device and return list
	 * @param c Context of application
	 * @return list of installed launchers
	 */
	public static List<ResolveInfo> getLaunchers(Context c) {
		PackageManager pm = c.getPackageManager();
		Intent i = new Intent("android.intent.action.MAIN");
		i.addCategory("android.intent.category.HOME");
		List<ResolveInfo> lst = pm.queryIntentActivities(i, 0);
		return lst;
	}
}
