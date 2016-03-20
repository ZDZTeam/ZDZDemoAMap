package com.zdz.map.amap;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.zdzsoft.lib.ui.ZDZViewCaller;

/**
 * 高德地图导航
 * 
 * @author zdzsoft
 * @link www.zdzsoft.com
 * @Copyright BeiJing ZDZ Tech Co.LTD
 */
public class AMapNaviCaller extends ZDZViewCaller implements AMapNaviListener, AMapNaviViewListener {
	private AMapNaviView mAMapNaviView;
	private AMapNavi mAMapNavi;
	private TTSController mTtsManager;
	private NaviLatLng mEndLatlng = new NaviLatLng(39.925846, 116.432765);
	private NaviLatLng mStartLatlng = new NaviLatLng(39.925041, 116.437901);
	private List<NaviLatLng> mStartList = new ArrayList<NaviLatLng>();
	private List<NaviLatLng> mEndList = new ArrayList<NaviLatLng>();
	private List<NaviLatLng> mWayPointList;

	public AMapNaviCaller(AMapNaviView mapView) {
		mAMapNaviView = mapView;

		mTtsManager = TTSController.getInstance(context.getApplicationContext());
		mTtsManager.init();
		mTtsManager.startSpeaking();

		mAMapNavi = AMapNavi.getInstance(context.getApplicationContext());
		mAMapNavi.addAMapNaviListener(this);
		mAMapNavi.addAMapNaviListener(mTtsManager);
		mAMapNavi.setEmulatorNaviSpeed(150);
	}

	/**
	 * 销毁
	 */
	public void destory() {
		if (mAMapNaviView != null) {
			mAMapNaviView.onDestroy();
			mAMapNaviView = null;
		}
		if (mAMapNavi != null) {
			mAMapNavi.stopNavi();
			mAMapNavi.destroy();
			mAMapNavi = null;
		}
		if (mTtsManager != null) {
			mTtsManager.destroy();
			mTtsManager = null;
		}
	}

	@Override
	public void onLockMap(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onNaviBackClick() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onNaviCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviMapMode(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviSetting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviTurnClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviViewLoaded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNextRoadClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScanViewButtonClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnUpdateTrafficFacility(TrafficFacilityInfo arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hideCross() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hideLaneInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyParallelRoad(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onArriveDestination() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onArrivedWayPoint(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCalculateMultipleRoutesSuccess(int[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCalculateRouteFailure(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCalculateRouteSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndEmulatorNavi() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetNavigationText(int arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGpsOpenStatus(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInitNaviFailure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInitNaviSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChange(AMapNaviLocation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviInfoUpdate(NaviInfo arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviInfoUpdated(AMapNaviInfo arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReCalculateRouteForTrafficJam() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReCalculateRouteForYaw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartNavi(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTrafficStatusUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showCross(AMapNaviCross arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showLaneInfo(AMapLaneInfo[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub

	}
}
