package com.zdz.map.amap;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.zdzsoft.lib.ui.ZDZUIFactory;

import android.content.Context;
import android.view.View;

/**
 * 高德地图视图类工厂
 * 
 * @author zdzsoft
 * @link www.zdzsoft.com
 * @Copyright BeiJing ZDZ Tech Co.LTD
 */
public class AMapFactory implements ZDZUIFactory {

	/**
	 * 创建自定义插件视图
	 * 
	 * @param context
	 *            环境
	 * @param classPath
	 *            类路径
	 * @param source
	 *            参数信息
	 * @return 自定义插件视图
	 * @throws Exception
	 *             创建失败
	 */
	@Override
	public View createView(Context context, String classPath, String source) {
		if (source != null && "navi".equalsIgnoreCase(source.trim())) {
			return createNaviView(context);
		}
		return createMapView(context);
	}

	private View createMapView(Context context) {
		AMapOptions options = new AMapOptions();
		options.logoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
		options.mapType(AMap.MAP_TYPE_NORMAL);
		options.compassEnabled(false);
		options.scaleControlsEnabled(false);
		options.zoomControlsEnabled(false);
		options.rotateGesturesEnabled(true);
		options.scrollGesturesEnabled(true);
		options.tiltGesturesEnabled(true);
		options.zoomGesturesEnabled(true);
		MapView view = new MapView(context, options);
		view.onCreate(null);
		return view;
	}

	private View createNaviView(Context context) {
		AMapNaviViewOptions options = new AMapNaviViewOptions();
		AMapNaviView view = new AMapNaviView(context, options);
		view.onCreate(null);
		return view;
	}

	/**
	 * 创建自定义插件视图的代理类
	 * 
	 * @param view
	 *            自定义插件视图
	 * @return 代理类
	 */
	public Object createViewCaller(View view) {
		if (view instanceof AMapNaviView) {
			return new AMapNaviCaller((AMapNaviView) view);
		}
		if (view instanceof MapView) {
			return new AMapCaller((MapView) view);
		}
		return null;
	}
}
