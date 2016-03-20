package com.zdz.map.amap;

import android.os.RemoteException;
import android.util.Log;

import com.amap.api.maps.MapsInitializer;
import com.zdzsoft.lib.ui.ZDZApplication;

/**
 * 高德地图初始化 Application
 * 
 * @author zdzsoft
 * @link www.zdzsoft.com
 * @Copyright BeiJing ZDZ Tech Co.LTD
 */
public class AMapApplication extends ZDZApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			loadNative();
			MapsInitializer.initialize(this);
		} catch (RemoteException e) {
			Log.e("AMap", "Cannot init gao de map!", e);
		}
	}
	
	private void loadNative() {
		System.loadLibrary("gdinamapv4sdk752");
		System.loadLibrary("gdinamapv4sdk752ex");
	}

}
