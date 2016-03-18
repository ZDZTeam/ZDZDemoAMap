
var data;
var selectedItem;

function str2data(str) {
	var data;
	var code = "data="+str+";";
	eval(code);
	return data;
}

function onPageLoad() {
	var map  = zdz.getView("map");
	selectedItem = -1;
	data = {};
	data.item = map.getClickItem();
	data.key  = map.getSearchKey();
	data.list = [];
	if("停车场" == data.key) {
		data.icon = "images/park.png";
	} else if("加油站" == data.key) {
		data.icon = "images/oil.png";
	} else {
		data.icon = "images/work.png";
	}
	if(data.item >= 0) {
		data.size = 1;
		selectedItem = data.item;
		data.list[0] = str2data(map.getSearchClickItem());
		data.list[0].icon = "images/poi_marker_"+(data.item + 1)+".png";
	} else {
		data.size = map.getSearchPageCount();
		var select = zdztool.getPref("fselect");
		for(var i = 0; i < data.size; i++) {
			data.list[i] = str2data(map.getSearchResult(i));
			if(i == select) 
				data.list[i].icon = "images/check_blue.png";
			else
				data.list[i].icon = "images/poi_marker_"+(i + 1)+".png";
		}
	}
	document.getElementById("title").innerHTML = data.key;
	zdztool.back("backBtn");
	zdztool.hrefindex("querybtn", "search.html", "left");
	zdztool.click("bigbtn", onNavi);
	var html = zdztool.templ('tmpl-detail', data, 'dlist');
	//alert(html);
	for(var i = 0; i < data.size; i++) {
		zdztool.click("icon_"  + i,  "setIconCheck("+i+");");
		zdztool.click("field_" + i,  "showItemMap("+i+");");
		zdztool.click("more_"  + i,  "showItemMap("+i+");");
	}
}
function setIconCheck(current) {
	zdztool.savePref({fselect : current});
	selectedItem = current;
	for(var j = 0; j < data.size; j++) {
		if(current == j)
			document.getElementById("img_" + j).src = "images/check_blue.png";
		else
			document.getElementById("img_" + j).src = "images/poi_marker_" + (j + 1) + ".png";
	}
}
function showItemMap(current) {
	var map  = zdz.getView("map");
	map.showSearchItem(current);
	zdztool.previous();
}
function onNavi() {
	if(selectedItem == null || selectedItem < 0) {
		zdztool.showAlert("错误",["请选择导航的目的地！"]);
		return;
	}
	zdztool.showAlert("错误",["请连接HUD设备！"]);
}
