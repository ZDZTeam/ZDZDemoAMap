package com.zdz.map.system;

import android.os.Bundle;

import com.zdzsoft.lib.ui.activity.ZDZWebLauncher;
import com.zdzsoft.lib.ui.anim.AnimClearBackEvent;
import com.zdzsoft.lib.ui.anim.ZDZAnim;

/**
 * 主类
 * 
 * @author zdzsoft
 * @link www.zdzsoft.com
 * @Copyright BeiJing ZDZ Tech Co.LTD
 */
public class MainActivity extends ZDZWebLauncher {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBackground(R.drawable.welcome);
		setAnim(ZDZAnim.TYPE_FADE, 2000).addEvent(new AnimClearBackEvent(this));
		loadLocal();
	}

}
