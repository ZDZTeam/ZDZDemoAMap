
function onPageLoad() {
	var map = zdz.getView("map");
	map.setPageLoadCallback("onInitMap");
	map.setSearchClickback("onClickMapRecord");
	zdztool.searchline("#0099e8");
	zdztool.click("menuBtn",   onShowMenu);
	zdztool.click("fsearch",   onSearchMap);
}
function onShowMenu() {
	zdztool.open("sidemenu.html", "left");
}
function onInitMap() {
	var map = zdz.getView("map");
	map.setLocationCallback("onMapLocation");
	/*map.location();*/
}
function onMapLocation(x, y, address, city_code, city) {
	var map = zdz.getView("map");
	map.setCenter(x, y);
}
function onSearchMap() {
	var map = zdz.getView("map");
	if(map.getSearchTotal() <= 0) {
		zdztool.open("search.html");
	} else {
		map.onSearchClick(-1);
		zdztool.open("detail.html");
	}
}
function onClickMapRecord(index, record) {
	//alert("onClickMapRecord index="+index+", record="+record);
	//var jdata;
	//var code = "jdata="+record+";";
	//eval(code);
	//alert(jdata.name);
	zdztool.open("detail.html");
}
function updateTitleKey(value) {
	document.getElementById("fsearch").value = value == null ? "" : value;
}
