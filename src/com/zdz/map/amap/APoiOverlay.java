package com.zdz.map.amap;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.PoiItem;
import com.zdz.map.system.R;

/**
 * 结果集显示
 * 
 * @author zdzsoft
 * @link www.zdzsoft.com
 * @Copyright BeiJing ZDZ Tech Co.LTD
 */
public class APoiOverlay {
	private Context context;
	private AMap mamap;
	private List<PoiItem> mPois;
	private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();

	public APoiOverlay(Context context, AMap amap, List<PoiItem> pois) {
		this.context = context;
		mamap = amap;
		mPois = pois;
	}

	/**
	 * 添加Marker到地图中。
	 * 
	 * @since V2.1.0
	 */
	public void addToMap() {
		for (int i = 0; i < mPois.size(); i++) {
			Marker marker = mamap.addMarker(getMarkerOptions(i));
			PoiItem item = mPois.get(i);
			marker.setObject(item);
			mPoiMarks.add(marker);
		}
	}

	/**
	 * 去掉PoiOverlay上所有的Marker。
	 * 
	 * @since V2.1.0
	 */
	public void removeFromMap() {
		for (Marker mark : mPoiMarks) {
			mark.remove();
		}
	}

	/**
	 * 移动镜头到当前的视角。
	 * 
	 * @since V2.1.0
	 */
	public void zoomToSpan() {
		if (mPois != null && mPois.size() > 0) {
			if (mamap == null)
				return;
			LatLngBounds bounds = getLatLngBounds();
			mamap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
		}
	}

	/**
	 * 移动镜头到当前的视角。
	 * 
	 * @since V2.1.0
	 */
	public void zoomToSpan(int index) {
		if (mPois != null && mPois.size() > 0 && 0 <= index && index < mPois.size()) {
			if (mamap == null)
				return;
			LatLngBounds bounds = getLatLngBounds(index);
			mamap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
		}
	}

	private LatLngBounds getLatLngBounds() {
		LatLngBounds.Builder b = LatLngBounds.builder();
		for (int i = 0; i < mPois.size(); i++) {
			b.include(new LatLng(mPois.get(i).getLatLonPoint().getLatitude(), mPois.get(i).getLatLonPoint().getLongitude()));
		}
		return b.build();
	}

	private LatLngBounds getLatLngBounds(int i) {
		LatLngBounds.Builder b = LatLngBounds.builder();
		b.include(new LatLng(mPois.get(i).getLatLonPoint().getLatitude(), mPois.get(i).getLatLonPoint().getLongitude()));

		return b.build();
	}

	private MarkerOptions getMarkerOptions(int index) {
		return new MarkerOptions().position(new LatLng(mPois.get(index).getLatLonPoint().getLatitude(), mPois.get(index).getLatLonPoint().getLongitude())).title(getTitle(index))
				.snippet(getSnippet(index)).icon(getBitmapDescriptor(index));
	}

	protected String getTitle(int index) {
		return mPois.get(index).getTitle();
	}

	protected String getSnippet(int index) {
		return mPois.get(index).getSnippet();
	}

	/**
	 * 从marker中得到poi在list的位置。
	 * 
	 * @param marker
	 *            一个标记的对象。
	 * @return 返回该marker对应的poi在list的位置。
	 * @since V2.1.0
	 */
	public int getPoiIndex(Marker marker) {
		for (int i = 0; i < mPoiMarks.size(); i++) {
			if (mPoiMarks.get(i).equals(marker)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 返回第index的poi的信息。
	 * 
	 * @param index
	 *            第几个poi。
	 * @return poi的信息。poi对象详见搜索服务模块的基础核心包（com.amap.api.services.core）中的类 <strong><a href="../../../../../../Search/com/amap/api/services/core/PoiItem.html"
	 *         title="com.amap.api.services.core中的类">PoiItem</a></strong>。
	 * @since V2.1.0
	 */
	public PoiItem getPoiItem(int index) {
		if (index < 0 || index >= mPois.size()) {
			return null;
		}
		return mPois.get(index);
	}

	protected BitmapDescriptor getBitmapDescriptor(int index) {
		if (index < 30) {
			BitmapDescriptor icon = BitmapDescriptorFactory.fromAsset("images/poi_marker_" + (index + 1) + ".png");
			return icon;
		} else {
			BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_other_highlight));
			return icon;
		}
	}

}
