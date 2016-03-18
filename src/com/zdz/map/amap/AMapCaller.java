package com.zdz.map.amap;

import java.util.List;

import android.location.Location;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.zdzsoft.lib.ui.caller.ZDZViewCaller;

/**
 * 高德地图代理类
 * 
 * @author zdzsoft
 * @link www.zdzsoft.com
 * @Copyright BeiJing ZDZ Tech Co.LTD
 */
public class AMapCaller extends ZDZViewCaller implements LocationSource, AMapLocationListener, LocationSource.OnLocationChangedListener, OnMarkerClickListener {
	private MapView mapView;
	private AMap amap;
	private boolean mapLoad = false;
	private boolean maplocation = false;
	private String city = "北京";
	private PoiSearch search = null;
	private PoiSearch.Query query;
	private String searchkey;
	private APoiOverlay overlay;
	private PoiResult poiresult;
	private int load_index = 0;
	private int click_pos = 0;
	private int page_size = 30;

	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;

	public AMapCaller(MapView mapView) {
		this.mapView = mapView;
		amap = mapView.getMap();
		amap.setOnMarkerClickListener(this);
		amap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
			@Override
			public void onMapLoaded() {
				mapLoad = true;
				webPageAttrCallback("loadCallback", mapLoad);
			}

		});
	}

	/**
	 * 设置地图加载的回调函数
	 * 
	 * @param callback
	 *            回调函数
	 */
	public void setPageLoadCallback(String callback) {
		setAttr("loadCallback", callback);
		if (mapLoad) {
			webPageAttrCallback("loadCallback", mapLoad);
		}
	}

	/**
	 * 判断地图是否加载完成
	 * 
	 * @return
	 */
	public boolean isLoad() {
		return mapLoad;
	}

	/**
	 * 获取地图中心点x
	 * 
	 * @return
	 */
	public double getCenterX() {
		return amap.getCameraPosition().target.longitude;
	}

	/**
	 * 获取地图中心点x
	 * 
	 * @return
	 */
	public double getCenterY() {
		return amap.getCameraPosition().target.latitude;
	}

	/**
	 * 设置地图中心的
	 * 
	 * @param x
	 *            经度
	 * @param y
	 *            纬度
	 */
	public void setCenter(double x, double y) {
		LatLng position = new LatLng(y, x);
		CameraUpdate camera = CameraUpdateFactory.newLatLng(position);
		amap.animateCamera(camera);
	}

	/**
	 * 获取地图中心点x
	 * 
	 * @return
	 */
	public double getLocationX() {
		return amap.getMyLocation().getLongitude();
	}

	/**
	 * 获取地图中心点x
	 * 
	 * @return
	 */
	public double getLocationY() {
		return amap.getMyLocation().getLatitude();
	}

	/**
	 * 获取方向
	 * 
	 * @return
	 */
	public double getDirection() {
		return amap.getMyLocation().getBearing();
	}

	/**
	 * 获取速度
	 * 
	 * @return
	 */
	public double getSpeed() {
		return amap.getMyLocation().getSpeed();
	}

	/**
	 * 获取卫星数量
	 * 
	 * @return
	 */
	public int getSatelite() {
		return 0;
	}

	/**
	 * 获取定位精度
	 * 
	 * @return
	 */
	public double getAccuracy() {
		return amap.getMyLocation().getAccuracy();
	}

	/**
	 * 设置定位成功后的回调函数
	 * 
	 * @param callback
	 *            回调函数，回调参数：x, y, address, city code, city
	 */
	public void setLocationCallback(String callback) {
		setAttr("locationback", callback);
	}

	/**
	 * 定位当前位置
	 * 
	 * @param start
	 *            是否开始定位
	 */
	public void location(boolean start) {
		if (!maplocation) {
			amap.setLocationSource(this);// 设置定位监听
			amap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
			amap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
			// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
			amap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

			maplocation = true;
		}
		if (start) {
			activate(this);
		}
	}

	/**
	 * 停止定位
	 */
	public void stopLocation() {
		deactivate();
	}

	/**
	 * 销毁
	 */
	public void destory() {
		stopLocation();
		mapView = null;
	}

	/**
	 * 获取当前城市
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 设置当前城市
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null && amapLocation.getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);
			} else {
			}
		}
	}

	@Override
	public void onLocationChanged(Location loc) {
		webPageAttrCallback("locationback", loc.getLongitude(), loc.getLatitude());
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(context);
			mLocationOption = new AMapLocationClientOption();
			// 设置定位监听
			mlocationClient.setLocationListener(this);
			// 设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			// 设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}

	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
		maplocation = false;
		amap.setMyLocationEnabled(false);
	}

	/**
	 * 搜索关键字成功后的回调函数
	 * 
	 * @param callback
	 *            回调函数
	 */
	public void setSearchCallback(String callback) {
		setAttr("searchback", callback);
	}

	/**
	 * 点击记录时回调函数
	 * 
	 * @param callback
	 *            回调函数
	 */
	public void setSearchClickback(String callback) {
		setAttr("searchclickback", callback);
	}

	/**
	 * 回调点击事件
	 * 
	 * @param pos
	 */
	public void onSearchClick(int pos) {
		click_pos = pos;
		if (poiresult != null && 0 <= pos && pos < poiresult.getPois().size()) {
			startJson();
			PoiItem item = poiresult.getPois().get(pos);
			addJsonObj();
			addJsonAttr("name", item.getTitle());
			addJsonAttr("address", item.getAdName());
			addJsonAttr("phone", item.getTel());
			addJsonAttr("city", item.getCityName());
			addJsonAttr("type", item.getTypeDes());
			addJsonAttr("x", item.getLatLonPoint().getLongitude());
			addJsonAttr("y", item.getLatLonPoint().getLatitude());
			addJsonEnd();
			String json = stopJson();
			webPageAttrCallback("searchclickback", pos, json);
		}
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		if (poiresult != null && marker.getObject() != null) {
			PoiItem item = (PoiItem) marker.getObject();
			int pos = poiresult.getPois().indexOf(item);
			onSearchClick(pos);
		}
		return true;
	}

	/**
	 * 定位到指定记录
	 * 
	 * @param pos
	 */
	public void showSearchItem(int pos) {
		click_pos = pos;
		if (poiresult != null && 0 <= pos && pos < poiresult.getPois().size()) {
			overlay.zoomToSpan(pos);
		}
	}

	/**
	 * 获得点击的记录
	 * 
	 * @return
	 */
	public String getSearchClickItem() {
		if (poiresult != null && 0 <= click_pos && click_pos < poiresult.getPois().size()) {
			return getSearchResult(click_pos);
		}
		return "";
	}

	/**
	 * 获取关键字
	 * 
	 * @return
	 */
	public String getSearchKey() {
		return searchkey;
	}

	/**
	 * 获取点击的记录索引
	 * 
	 * @return
	 */
	public int getClickItem() {
		return click_pos;
	}

	/**
	 * 获取搜索的总记录数
	 * 
	 * @return
	 */
	public int getSearchTotal() {
		return poiresult == null ? 0 : poiresult.getPageCount() * page_size;
	}

	/**
	 * 获取当前页面下标
	 * 
	 * @return
	 */
	public int getSearchPage() {
		return poiresult == null ? 0 : load_index;
	}

	/**
	 * 获取当前页面数量
	 * 
	 * @return
	 */
	public int getSearchPageCount() {
		return poiresult == null ? 0 : poiresult.getPois().size();
	}

	/**
	 * 获取每页最大数量
	 * 
	 * @return
	 */
	public int getSearchPageCapacity() {
		return poiresult == null ? 0 : page_size;
	}

	/**
	 * 获取查询记录
	 * 
	 * @param pos
	 * @return
	 */
	public String getSearchResult(int pos) {
		if (poiresult != null && pos < poiresult.getPois().size()) {
			startJson();
			PoiItem info = poiresult.getPois().get(pos);
			addJsonObj();
			addJsonAttr("name", info.getTitle());
			addJsonAttr("address", info.getAdName());
			addJsonAttr("phone", info.getTel());
			addJsonAttr("city", info.getCityName());
			addJsonAttr("type", info.getTypeDes());
			addJsonAttr("x", info.getLatLonPoint().getLongitude());
			addJsonAttr("y", info.getLatLonPoint().getLatitude());
			addJsonEnd();
			String json = stopJson();
			return json;
		}
		return "";
	}

	/**
	 * 搜索关键字
	 * 
	 * @param key
	 *            关键字
	 */
	public void searchKey(String key) {
		searchLocation(key, getCenterX(), getCenterY());
	}

	/**
	 * 检索位置
	 * 
	 * @param key
	 *            位置
	 * @param x
	 *            坐标
	 * @param y
	 *            坐标
	 */
	public void searchLocation(String key, double x, double y) {
		load_index = 0;
		query = new PoiSearch.Query(key, "", city);
		query.setPageSize(page_size);
		query.setPageNum(load_index);
		search = new PoiSearch(context, query);
		LatLonPoint point = new LatLonPoint(y, x);
		search.setBound(new SearchBound(point, 5000, true));
		search.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
			@Override
			public void onPoiSearched(PoiResult result, int rCode) {
				if (rCode == 1000) {
					if (result != null && result.getQuery() != null) {// 搜索poi的结果
						if (result.getQuery().equals(query)) {// 是否是同一条
							poiresult = result;
							List<PoiItem> poiItems = poiresult.getPois();
							if (poiItems != null && poiItems.size() > 0) {
								amap.clear();// 清理之前的图标
								APoiOverlay poiOverlay = new APoiOverlay(context, amap, poiItems);
								poiOverlay.removeFromMap();
								poiOverlay.addToMap();
								poiOverlay.zoomToSpan();

								webPageAttrCallback("searchback", poiItems.size(), result.getPageCount() * poiItems.size(), load_index, result.getPageCount(), page_size);

							} else {
								webPageAttrCallback("searchback", 0);
							}
						}
					} else {
						webPageAttrCallback("searchback", 0);
					}
				} else {
					webPageAttrCallback("searchback", -1);
				}
			}

			@Override
			public void onPoiItemSearched(PoiItem item, int rCode) {
			}
		});
		searchkey = key;
		poiresult = null;
		search.searchPOIAsyn();
	}

	/**
	 * 上一页
	 */
	public void getPrevSearch() {
		if (load_index > 0 && search != null) {
			load_index--;
			poiresult = null;
			searchNextKey();
		}
	}

	/**
	 * 下一页
	 */
	public void getNextSearch() {
		if (search != null) {
			load_index++;
			poiresult = null;
			searchNextKey();
		}
	}

	private void searchNextKey() {
		if (load_index < 0) {
			load_index = 0;
		}
		if (poiresult != null) {
			if (load_index >= poiresult.getPageCount()) {
				load_index = load_index - 1;
			}
		}
		query.setPageNum(load_index);
		search.searchPOIAsyn();
	}
}
