
function updateSearchKey(key) {
	key = key == null ? "" : key;
	var js = 'updateTitleKey("'+key+'");';
	var title = zdz.getView("title");
	title.eval(js);
}

function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

function onPageLoad() {
	zdztool.full();
	zdztool.searchline("#0099e8");
	zdztool.input("fsearch",  "fsearch");
	zdztool.back("backBtn");
	zdztool.click("searchbtn", onKeySearch);
	zdztool.click("parkBtn", onParkSearch);
	zdztool.click("oilBtn",  onOilSearch);
	updateSearchKey("");
}
function onKeySearch() {
	var key = document.getElementById("fsearch").value;
	key = key == null ? "" : trim(key);
	if(key.length == 0) {
		zdztool.showAlert("错误",["搜索关键字不能为空！"]);
		return;
	}
	zdztool.savePref({fsearch : key, fselect : -1});

	updateSearchKey(key);
	var map = zdz.getView("map");
	map.setSearchCallback("onKeyResult");
	map.searchKey(key);
	zdztool.showLoading();
}
function onKeyResult(size,total,page,max_page,capacity) {
	zdztool.hideLoading();
	var key = document.getElementById("fsearch");
	if(total < 0) {
		zdztool.showAlert("查找 \""+key+"\" 失败!");
		return ;
	}
	if(total == 0) {
		zdztool.showAlert("附近没有找到 \""+key+"\" !");
		return ;
	}
	zdztool.previous();
}
function onParkSearch() {
	zdztool.savePref({fselect : -1});
	updateSearchKey("停车场");
	var map = zdz.getView("map");
	map.setSearchCallback("onParkResult");
	map.searchKey("停车场");
	zdztool.showLoading();
}
function onParkResult(size,total,page,max_page,capacity) {
	zdztool.hideLoading();
	if(total < 0) {
		zdztool.showAlert("查找停车场失败!");
		return ;
	}
	if(total == 0) {
		zdztool.showAlert("附近没有找到停车场!");
		return ;
	}
	zdztool.previous();
}
function onOilSearch() {
	zdztool.savePref({fselect : -1});
	updateSearchKey("加油站");
	var map = zdz.getView("map");
	map.setSearchCallback("onOilResult");
	map.searchKey("加油站");
	zdztool.showLoading();
}
function onOilResult(size,total,page,max_page,capacity) {
	zdztool.hideLoading();
	if(total < 0) {
		zdztool.showAlert("查找加油站失败!");
		return ;
	}
	if(total == 0) {
		zdztool.showAlert("附近没有找到加油站!");
		return ;
	}
	zdztool.previous();
}