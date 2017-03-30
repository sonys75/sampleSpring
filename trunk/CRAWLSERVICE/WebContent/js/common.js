
function menuclick(id)
{
	if(document.getElementById("submenu"+id).style.display == "none"){
		document.getElementById("submenu"+id).style.display = "";
	}else{
		document.getElementById("submenu"+id).style.display = "none";
	}
}

/**
 * 1바이트문자열 체크
 * 문자열이 1바이트로만 되어 있으면 True, 2바이트 문자가 섞여 있으면 False
 *
 * @param strInput
 * @returns {Boolean}
 */
function ChkOneByte(strInput){
	var i = 0;
	var charCheck = "";
	var ChkStr = strInput.toLowerCase();
	var ChkStrLen = ChkStr.length;
	for(i=0; i< ChkStrLen; i++){
		charCheck = ChkStr.charAt(i);
		if (escape(charCheck).length > 4)
		{
			return false;
			break;
		}
	}
	return true;
}


/**
 * 특수문자열 체크
 * 문자열에 특수문자 있으면 False, 특수문자가 없으면 True
 *
 * @param ChkStr
 * @returns {Boolean}
 */
function ChkSpecialByte(ChkStr){
	var charCheck = "";
	var ChkStrLen = ChkStr.length;
	
	var ChkStrArr = new Array("~","`","!","@","#","$","%","^","&","*","(",")","=","+","[","{","]","}","\\","|",";",":","'",'"',",","<",".",">","/","?");
	var ChkStrArrLen = ChkStrArr.length;
	
	for(var i=0; i< ChkStrLen; i++){
		charCheck = ChkStr.charAt(i);
		for(var j=0;j<ChkStrArrLen;j++){
			if (ChkStrArr[j]==charCheck){
				return false;
				break;
			}
		}
	}
	return true;
}


/**
 * 검색문자열 체크
 * 문자열에 검색시 사용할수 있는 문자("?","*","&","|")가 아닌 다른 문자가 있으면 False,
 * 검색시 사용할수 있는 특수문자만 포함되어 있으면 True
 * 2007.10.15- 손용산 수정내용('@'문자 배열에서 제외:원어에서 포함된것 있음)
 *
 * @param ChkStr
 * @returns {Boolean}
 */
function ChkSearchStr(ChkStr){
	var ChkStrArr = new Array("~","`","!","#","$","%","^","(",")","-","_","=","+","[","{","]","}","\\",";",":","'",'"',",","<",".",">","/");
	var ChkStrArrLen = ChkStrArr.length;
	for ( var i = 0; i < ChkStrArrLen; i++)
	{
		if (ChkStr.lastIndexOf(ChkStrArr[i])!="-1")
		{
			return false;
		}
	}
	return true;
}
 
/**
 * 이메일 체크
 * 이메일 형식에 맞으면 True, 다르면 False
 *
 * @param ChkStr
 * @returns {Boolean}
 */
function ChkEmail(ChkStr) {
	var invalidChars = "\"|&;<>!*\'\\"   ;
	for (var i = 0; i < invalidChars.length; i++) {
		if (ChkStr.indexOf(invalidChars.charAt ) != -1) {
			return false;
		}
	}
	if (ChkStr.indexOf("@")==-1){
		return false;
	}
	if (ChkStr.indexOf(" ") != -1){
		return false;
	}
	if (window.RegExp) {
		var reg1str = "(@.*@)|(\\.\\.)|(@\\.)|(\\.@)|(^\\.)";
		var reg2str = "^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$";
		var reg1 = new RegExp (reg1str);
		var reg2 = new RegExp (reg2str);
	
		if (reg1.test(ChkStr) || !reg2.test(ChkStr)) {
			return false;
		}
	}
	return true;
}

/**
 * 새창 여는 스크립트
 * 화면의 가운데에 열리게 한다.
 * @param Url
 * @param OpenName
 * @param Wid		오픈할 창 사이즈(가로)
 * @param Hei		오픈할 창 사이즈(세로)
 */
function fncWinOpen(Url,OpenName,Wid,Hei){
	var ScreenWidth		= screen.width;
	var ScreenHeight	= screen.height;
	var OpenWinWidth	= Wid;
	var OpenWinHeight	= Hei;
	var OpenLeft		= (ScreenWidth - OpenWinWidth) / 2;
	var OpenTop			= (ScreenHeight - OpenWinHeight) / 2;
	var OpenWinStatus="left="+OpenLeft+",top="+OpenTop+",toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=yes,width="+OpenWinWidth+",height="+OpenWinHeight+"";
	var windowID = window.open(Url,OpenName,OpenWinStatus);
	return windowID;
}

/**
 * 새창 여는 스크립트
 * 화면의 가운데에 열리게 한다.
 * @param Url
 * @param OpenName
 * @param Wid		오픈할 창 사이즈(가로)
 * @param Hei		오픈할 창 사이즈(세로)
 */
function fncWinScrollOpen(Url,OpenName,Wid,Hei){
	var ScreenWidth		= screen.width;
	var ScreenHeight	= screen.height;
	var OpenWinWidth	= Wid;
	var OpenWinHeight	= Hei;
	var OpenLeft		= (ScreenWidth - OpenWinWidth) / 2;
	var OpenTop			= (ScreenHeight - OpenWinHeight) / 2;
	var OpenWinStatus="left="+OpenLeft+",top="+OpenTop+",toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=yes,width="+OpenWinWidth+",height="+OpenWinHeight+"";
	var windowID = window.open(Url,OpenName,OpenWinStatus);
	return windowID;
}

/**
 * 첨부파일 체크
 * 첨부파일 형식에 맞으면 True, 다르면 False
 *
 * @param strInput
 * @returns
 */
function fncCheckFile(strInput){
	var str = strInput.toLowerCase();
	var chk = str.lastIndexOf(".");
	if(chk=="-1"){
		return "파일이 아닙니다.";
	}
	var exec = str.substring(chk+1);
	if(exec=="jsp" || exec=="com" || exec=="bat" || exec=="exe" || exec=="js" || exec=="asp" || exec=="java" || exec=="php" || exec=="dll"){
		return "서버에서 실행가능한 파일은 업로드가 제한 됩니다.\n\n예) jsp, com, exe, bat, js, asp, java, php, dll";
	}
	return true;
}

/**
 * 첨부파일 확장자 체크
 * 첨부파일 실행가능파일 형식 True, 아니면 False
 *
 * @param strInput
 * @returns
 */
function fncCheckExecutiveFileExt(strInput){
	var str = strInput.toLowerCase();
	var chk = str.lastIndexOf(".");
	if (chk < 0) {
		return false;
	}
	var exec = str.substring(chk+1).toLowerCase();
	if ("_jsp_exe_com_bat_js_asp_java_php_dll_".indexOf("_"+exec+"_") >= 0) {
		return true;
	}
	return false;
}

/**
 * 이미지파일 체크
 * 이미지파일 형식에 맞으면 True, 다르면 False
 *
 * @param strInput
 * @returns
 */
function fncCheckImgFile(strInput){
	var str = strInput.toLowerCase();
	var chk = str.lastIndexOf(".");
	if(chk=="-1"){
		return "파일이 아닙니다.";
	}
	var exec = str.substring(chk+1);
	if(exec!="jpg" && exec!="gif" && exec!="jpeg" && exec!="bmp" && exec!="png"){
		return "이미지 파일만 등록해 주세요.";
	}
	return true;
}

/**
 * 스킨이미지파일 체크
 * 이미지파일 형식에 맞으면 True, 다르면 False
 *
 * @param strInput
 * @returns
 */
function fncCheckSkinFile(strInput){
	var str = strInput.toLowerCase();
	var chk = str.lastIndexOf(".");
	if(chk=="-1"){
		return "파일이 아닙니다.";
	}
	var exec = str.substring(chk+1);
	if(exec!="bmp" && exec!="png"){
		return "이미지 파일만 등록해 주세요.";
	}
	return true;
}

/**
 * 해당 폼의 입력데이터 길이를 체크한다.
 *
 * @param obj
 * @param lenMax
 */
function fncChkByte(obj,lenMax)
{
	var strCheck = obj.value;
	var lenCheck = strCheck.length;

	var i = 0;
	var lenByte = 0;
	var len = 0;
	var charCheck = "";
	var checedString = "";

	for(i  =0; i < lenCheck; i++){
		charCheck = strCheck.charAt(i);
		if (escape(charCheck).length > 4)
		{
			lenByte += 2;
		}else{
			lenByte++;
		}

		if(lenByte <= lenMax){
			len = i + 1;
		}
	}

	if(lenByte > lenMax){
		alert( lenMax + " Byte 를 초과 입력할수 없습니다. \n초과된 내용은 자동으로 삭제 됩니다. ");
		checedString = strCheck.substr(0, len);
		obj.value = checedString;
		obj.focus();
	}
}

/**
 * 필수입력 항목의 입력여부를 확인하고
 * 미입력시 메시지를 표시한다.
 * 입력이 되어 있는 경우 True, 아닌 경우 False를 반환한다.
 *
 * @param obj
 * @param msg
 * @returns {Boolean}
 */
function fn_isNull(obj, msg)
{
	var t = obj.value;

	if (t == null || t == '' || t.length <= 0)
	{
		alert(msg);
		obj.focus();
		return false;
	}
	return true;
}

/**
 * 폼의 필수입력항목 입력여부를 체크한다.
 *
 * @param msg	미입력시의 표시 메시지
 * @param arr	입력체크 항목의 Array
 * @returns {Boolean}
 */
function fn_checkNullFields(msg, arr) {
	var obj;
	for ( var i = 0; i < arr.length; i++) {
		try {
			obj = document.getElementById(arr[i]);
			if(!fn_isNull( obj, fn_getSuffixAdd(obj.title, msg))) {
				return false;
			}
		} catch(e) {
			;
		}
	}
	return true;
}

/**
 * 메시지 표시시 항목명의 받침여부를 확인해 [은/는]을 선택적으로 표시한다.
 * @param strInput
 * @param msg
 * @returns
 */
function fn_getSuffixAdd(strInput, msg) {

	var sFix;
	var iChk = strInput.charCodeAt(strInput.length - 1);

	if ( ( iChk - 16 ) % 28 == 0 )
		sFix = "는 ";
	else
		sFix = "은 ";

	return strInput + sFix + msg;
}

/**
 * 체크 박스 전체 선택 및 전체 해제를 한다.
 *
 * @param obj	폼오브젝트
 * @param val	체크박스 체크여부
 */
function fn_toggleCheckbox(obj, val)
{
	if ( obj[0] == undefined || obj[0] == null )
	{
		obj.checked = val;
	} else
	{
		for ( var i=0; i<obj.length; i++)
		{
			obj[i].checked = val;
		}
	}
}

/**
 * 체크 박스 전체 선택 및 전체 해제를 한다.
 *
 * @param object
 * @param field
 */
function fn_selectAllCheckBox(object, field)
{
	if(object.checked) {
		for ( var i = 0; i < field.length; i++) {
			field[i].checked = true;
		}
	} else {
		for ( var i = 0; i < field.length; i++) {
			field[i].checked = false;
		}
	}
}

/**
 * 체크박스 체크 갯수를 리턴한다.
 *
 * @param field
 * @returns {Number}
 */
function fn_countChkBox(field)
{
	var count = 0;
	if (field.length == null && field.checked ==true)
	{
		count = 0;
	} else
	{
		for ( var i=0; i<field.length; i++)
		{

			if (field[i].checked == true)
			{
				count += 1;
			}
		}
	}

	return count;
}
/**
 * 체크박스 체크 여부를 확인한다.
 *
 * @param checkBox
 * @returns {Boolean}
 */
function fn_verifyCheckBoxSelect(checkBox)
{
	var field = document.getElementsByName(checkBox);

	for ( var i = 0; i < field.length; i++) {
		if(field[i].checked == true) return true;
	}

	return false;
}

/**
 * 체크박스중 체크되어 있는 항목이 있는 경우 첫번째 체크박스의 값을 리턴한다.
 *
 * @param checkBox
 * @returns String
 */
function fn_checkBoxSelectedOnlyOne(checkBox)
{
	for ( var i = 0; i < checkBox.length; i++) {
		if(checkBox[i].checked) {
			return checkBox[i].value;
		}
	}
}


/**
 * @param formName
 * @param atch_file_id
 * @param file_sn
 * @param target
 */
function fn_downFile(formName, atch_file_id, file_sn, target){
	var frm = document.getElementById(formName);
	if(atch_file_id != undefined && atch_file_id != null && atch_file_id != '') {
		frm.atch_file_id.value = atch_file_id;
	}
	
	if(file_sn != undefined && file_sn != null && file_sn != '') {
		frm.file_sn.value = file_sn;
	}
	
	var t = frm.target;
	
	if(target != undefined && target != null && target != "") {
		frm.target = target;
	} else {
//		t = blankName;
//		frm.target = "KCUE_BLANK_FR";
	}
	
	frm.action = "/common/file/FileDownload.do";
	frm.submit();

	frm.target = t;
}

/**
 * @param formName
 * @param file_path
 * @param file_name
 * @param target
 */
function fn_downPathFile(formName, file_path, file_name, target){
	var frm = document.getElementById(formName);
	
	var t = frm.target;
	
	if(target != undefined && target != null && target != "") {
		frm.target = target;
	} else {
//		t = blankName;
//		frm.target = "KCUE_BLANK_FR";
	}

	frm.file_path.value = file_path;
	frm.file_name.value = file_name;

	frm.action = "/common/downloadFile.do";
	frm.submit();

	frm.target = t;
}

//함수명 : fn_isNumber
//설     명 : 입력값에 숫자만 있는지 체크
//인     자 : frm(폼명)
//리     턴 : 없음
//사용법 : fn_isNumber(registerForm.sndTm3);
function fn_isNumber(str)
{
	if (str.value.search(/[^0-9]/g)==-1)
	{
		return true;
	} else
	{
		str.value = "";
		str.focus();
		return false;
	}
}

function fncChkPhone(str) {
	if (str.value.search(/[^0-9,-]/g)==-1)
	{
		return true;
	} else
	{
		str.value = "";
		str.focus();
		return false;
	}
} 

//함 수  명 : fncValidateCellPhone
//설     명 : 입력값에 휴대폰 번호 대역과 일치하는지 여부
//인     자 : str
//리     턴 : true,false
//사 용  법 : 
function fncValidateCellPhone(str){
	var ChkStrArr = new Array("010","011","013","016","017","018","019");
	var ChkStrArrLen = ChkStrArr.length;
	var arrCell;
	var arrChk=false;
	
	if(str==null) return false;
	
	if(str.indexOf("-") > -1){			//전화번호를 '-'로 입력하였을 경우
		arrCell = str.split("-");
		if(arrCell.length !=3 ){
			return false;
		}
	}else{
		if(str.length<10){
			return false;
		}
		 
		arrCell=[];
		arrCell[0] = str.substr(0,3); 
		arrCell[1] = str.substr(3,str.length-7);
		arrCell[2] = str.substr(str.length-4, str.length);
	}
	
	for ( var i = 0; i < ChkStrArrLen; i++){
		if (ChkStrArr[i]==arrCell[0]){
			arrChk=true;
		}
	}
	if(!arrChk ){
		return false;
	}
	if(arrCell[1].length!=3 && arrCell[1].length!=4){
		return false;
	}
	if(arrCell[2].length!=4){
		return false;
	}
	
	return true;
}

//함 수  명 : fncValidateTelePhone
//설     명 : 입력값에 일번전화번호 대역과 일치하는지 여부
//인     자 : str
//리     턴 : true,false
//사 용  법 : 
function fncValidateTelePhone(str){
	var ChkStrArr = new Array("02","051","053","032","062","042","052","031","033","043","041","063","061","054","055","064");
	var ChkStrArrLen = ChkStrArr.length;
	var arrCell;
	var arrChk=false;
	
	if(str==null) return false;
	
	if(str.indexOf("-") > -1){			//전화번호를 '-'로 입력하였을 경우
		arrCell = str.split("-");
		if(arrCell.length !=3 ){
			return false;
		}
	}else{
		if(str.length<9){
			return false;
		}
		 
		arrCell=[];
		if(str.indexfOf("02")==0){
			arrCell[0] = str.substr(0,2); 
			arrCell[1] = str.substr(2,str.length-6);
		}else{
			arrCell[0] = str.substr(0,3); 
			arrCell[1] = str.substr(3,str.length-7);
		}
		arrCell[2] = str.substr(str.length-4, str.length);
	}
	
	for ( var i = 0; i < ChkStrArrLen; i++){
		if (ChkStrArr[i]==arrCell[0]){
			arrChk=true;
		}
	}
	if(!arrChk ){
		return false;
	}
	if(arrCell[1].length!=3 && arrCell[1].length!=4){
		return false;
	}
	if(arrCell[2].length!=4){
		return false;
	}
	
	return true;
}

function onlyNumber2() {
	// ←(백스페이스) = 8, TAB = 9, ENTER = 13
	// ←(중간) = 37, ↑(중간) = 38, →(중간) = 39, ↓(중간) = 40
	// INSERT = 45, DELETE = 46
	// 0 ~ 9
	// 0 ~ 9
	// .
	if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 13 ||
			event.keyCode == 37 || event.keyCode == 38 || event.keyCode == 39 || event.keyCode == 40 ||
			event.keyCode == 45 || event.keyCode == 46 ||
			(event.keyCode >= 48 && event.keyCode <= 57) ||
			(event.keyCode >= 96 && event.keyCode <= 105) ||
			event.keyCode == 190 || event.keyCode == 110 ) {
		return;
	}
	
	event.returnValue=false;
}

var KEY_DASH = 189;

function onlyNumber3(obj, acceptKey) {
	// ←(백스페이스) = 8, TAB = 9, ENTER = 13
	// ←(중간) = 37, ↑(중간) = 38, →(중간) = 39, ↓(중간) = 40
	// INSERT = 45, DELETE = 46
	// 0 ~ 9
	// 0 ~ 9
	// .
	if (event.keyCode == 16 || event.keyCode == 16) {
		event.returnValue=false;
		return;
	}
//	alert(event.keyCode);
	if (event.keyCode == acceptKey) {
		return;
	}
	if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 13 ||
			event.keyCode == 37 || event.keyCode == 38 || event.keyCode == 39 || event.keyCode == 40 ||
			event.keyCode == 45 || event.keyCode == 46 ||
			(event.keyCode >= 48 && event.keyCode <= 57) ||
			(event.keyCode >= 96 && event.keyCode <= 105) ||
			event.keyCode == 190 || event.keyCode == 110 ) {
		return;
	}
	
	event.returnValue=false;
}

function findMem()
{
	var url = "/member/manage/Member_searchinfo_popup.do?gubun=0";
	var pwin = fncWinOpen(url,"findmem",450,450);
	pwin.focus();
}

/**
 * SNS 연동 코드
 * 게시판 제목과 url 주소 sns에 올린다.
 * @param snsTitle,url
 * @returns
 */
//트윗터 연동
function goLinkTwitter(snsTitle,url) 
{
 var sPagesize = "width=787,height=407,scrollbars=yes";
 var sLink = "http://twitter.com/home?status="+encodeURIComponent(snsTitle)+" "+ encodeURIComponent(url);
 var a = window.open(sLink, 'twitter',sPagesize);
 if( a ) 
 {
         a.focus();
    }
 return ;
}

//페이스북 연동
function goLinkFaceBook(snsTitle,url) 
{
 
 var sPagesize = "width=615,height=407,scrollbars=yes";
 var sLink = "http://www.facebook.com/sharer.php?u=" + encodeURIComponent(url) + "&t=" + encodeURIComponent(snsTitle);
 var a = window.open(sLink, 'facebook',sPagesize);
 if(a) 
 {
         a.focus();
    }
 return ;
}

//미투데이 연동
function goLinkMe2Day(snsTitle,url) 
{
var sPagesize = "width=986,height=626,scrollbars=yes";
var sLink = "http://me2day.net/posts/new?new_post[body] = "+ encodeURIComponent(url) + "&new_post[tags]" + encodeURIComponent(snsTitle);
var a = window.open(sLink, 'me2Day',sPagesize);
if(a)
{
     a.focus();
}
 return ;
}

//인쇄
function fncScreenPrint(cssname) {
	var txts = "/include/ajaxScreenPrint.do?cssfile="+cssname;
	window.open(txts,'print','width=735,height=650,scrollbars=yes');void(0);
}

/**
 * 공통검색 스크립트
 * 
 * document의 첫번째 form을 서브밋 한다.
 * @returns
 */
function fncSearch(){
	var f = document.forms[0];
	f.submit();
	return;
}

/**
 * 공통검색 스크립트
 * 
 * document의 첫번째 form을 서브밋 한다.
 * @returns
 */
function fncAjaxCommonSearch(fnc){
	eval(fnc+"()");
	return;
}

/**
 * 공통검색조건 초기화 스크립트
 * 
 * formName: 초기화할 폼이름
 * initParam: 초기화할 파라미터 name
 * afterCallFnc: 초기화 후 실행할 함수
 * @returns
 */
function fncAjaxCommonInitSearch(formName,initParam,afterCallFnc){
	var f= eval("document."+formName);
	var arrParam = initParam.split(",");
	
	for(var j=0;j<arrParam.length;j++){
		var initName = eval("f."+ arrParam[j]);
		initName.value ="";
	}

	eval(afterCallFnc+"()");
	return;
}

/**
 * 두날짜의 날짜 간격을 가져온다. 
 *
 */
function fncDateDiff(startDate, endDate){
	startDate = startDate.replace(/-/g,"");
	endDate =  endDate.replace(/-/g,"");

	var sYear  = startDate.substring(0,4);
	var sMonth  = startDate.substring(4,6);
	var sDay  = startDate.substring(6,8);
	
	var eYear  = endDate.substring(0,4);
	var eMonth  = endDate.substring(4,6);
	var eDay  = endDate.substring(6,8);
	
	var sDate = new Date(sYear,sMonth,sDay);
	var eDate = new Date(eYear,eMonth,eDay);
	
	return Math.round((eDate - sDate) / (1000*60*60*24) / 31);
}

/**
 * 두날짜의 날짜 간격을 가져온다. 
 *
 */
function fncDateTimeDiff(startDateTime, endDateTime){
	startDateTime = startDateTime.replace(/[^0-9]/g,"");
	endDateTime =  endDateTime.replace(/[^0-9]/g,"");

	//alert("startDateTime : "+startDateTime+",endDateTime : "+endDateTime);
	
	var sYear  = startDateTime.substring(0,4);
	var sMonth  = startDateTime.substring(4,6);
	var sDay  = startDateTime.substring(6,8);
	var sHour  = startDateTime.substring(8,10);
	var sMin  = startDateTime.substring(10,12);
	var sSec  = startDateTime.substring(12,14);
	
	var eYear  = endDateTime.substring(0,4);
	var eMonth  = endDateTime.substring(4,6);
	var eDay  = endDateTime.substring(6,8);
	var eHour  = endDateTime.substring(8,10);
	var eMin  = endDateTime.substring(10,12);
	var eSec  = endDateTime.substring(12,14);
	
	var sDate = new Date(sYear,sMonth,sDay,sHour,sMin);
	var eDate = new Date(eYear,eMonth,eDay,eHour,eMin);
	
	return Math.round((eDate - sDate) / (1000*60*60));
}

//두객체의 날수 차이를 리턴 할 경우 :   return (to_dt.getTime() - from_dt.getTime()) / 1000 / 60 / 60 / 24;   
//두객체의 시간 차이를 리턴 할 경우 :   return (to_dt.getTime() - from_dt.getTime()) / 1000 / 60 / 60;    
//두객체의 분 차이를 리턴 할 경우 :   return (to_dt.getTime() - from_dt.getTime()) / 1000 / 60;   
//두객체의 초 차이를 리턴 할 경우 :   return (to_dt.getTime() - from_dt.getTime()) / 1000;   

// 시간계산
// sDate :시작일, shour: 시간, stime :분
// eDate :종료일, ehour: 시간, etime :분
// ex) calDateRange('2011-05-05','10','00', '2011-05-05','11','00');
function calDateRange(sDate, shour, stime, eDate, ehour, etime) {
	var FORMAT = "-";
	
	// FORMAT을 포함한 길이 체크
	if (sDate.length != 10 || eDate.length != 10)
	return null;
	
	// FORMAT이 있는지 체크
	if (sDate.indexOf(FORMAT) < 0 || eDate.indexOf(FORMAT) < 0)
	return null;
	
	// 년도, 월, 일로 분리
	var start_dt = sDate.split(FORMAT);
	var end_dt = eDate.split(FORMAT);
	// 월 - 1(자바스크립트는 월이 0부터 시작하기 때문에...)
	// Number()를 이용하여 08, 09월을 10진수로 인식하게 함.
	start_dt[1] = (Number(start_dt[1]) - 1) + "";
	end_dt[1] = (Number(end_dt[1]) - 1) + "";
	
	var from_dt = new Date(start_dt[0], start_dt[1], start_dt[2], shour, stime);
	var to_dt = new Date(end_dt[0], end_dt[1], end_dt[2], ehour, etime);
	var chkDate = (to_dt.getTime() - from_dt.getTime());
	if(chkDate < 0 ){
	alert("종료시간이 시작시간 이전이면 안됩니다. 다시 확인하세요.");
	return false;
	}
	return (to_dt.getTime() - from_dt.getTime()) / 1000 / 60;
}


function ajaxError() {
	alert("정보를 읽어 오는데 실패 하였습니다.\n다시 실행하여 주십시오.");
}

/**
 * aJax검색화면에서 enter 키 누르면 fnc 에 정의된 함수를 호출한다. 
 * 
 * @param e :event
 * @param fnc : Call 할 function 명
 */
function fncKeyPressCall(e,fnc){
    var keyCode = e.keyCode;

    //firefox 키코드가져오기
    if(keyCode == 0){
    	keyCode = e.which;
    }

    if(keyCode == 13){
    	if(fnc.indexOf("(")>0){
    		eval(fnc);
    	}else{
    		eval(fnc+"()");
    	}
    }
}

/**
 * 현재 시간을 가져온다.
 */
function getCurrentTime(){ 
    var t = new Date(); 
    
	var year = t.getFullYear();
	var mon = (t.getMonth()+1);
	if(parseInt(mon)<10) mon = "0"+mon;
	var day = t.getDate();
	if(parseInt(day)<10) day = "0"+day;
	var hour = t.getHours();
	if(parseInt(hour)<10) hour = "0"+hour;
	var min = t.getMinutes();
	if(parseInt(min)<10) min = "0"+min;
	var sec = t.getSeconds();
	if(parseInt(sec)<10) sec = "0"+sec;
	
	var strCurrentTime = year + '년 ' + mon + '월 ' + day + '일 ' + hour + ":" + min + ":" + sec;
    return strCurrentTime;
}

/**
 * 다이어로그 모달창을 연다.
 * 
 * @param title
 * @param url
 * @returns {Boolean}
 */
function openDivModal(title,url,width,height) {
    //$('.opened-dialogs').dialog("close");

    $('<div class="opened-dialogs">').dialog({
        //position:  ['center',20],
        open: function () {
            $(this).load(url);
        },
        close: function(event, ui) {
	        $(this).remove();
        },
        title: title,
        minWidth: width,
        minHeight: height,
        modal: true
    }).css({height:height+"px", overflow:"auto"});

    return false;
}

/**
 * 다이어로그 모달창을 연다.
 * 
 * @param title
 * @param url
 * @returns {Boolean}
 */
function openDivModalResize(title,url,width,height,bResize) {
    //$('.opened-dialogs').dialog("close");

    $('<div class="opened-dialogs">').dialog({
    	resizable: bResize,
        open: function () {
            $(this).load(url);
        },
        close: function(event, ui) {
	        $(this).remove();
        },
        title: title,
        minWidth: width,
        minHeight: height,
        modal: true
    }).css({height:height+"px", overflow:"auto"});

    return false;
}

/**‪
 * 다이어로그 모달창을 연다.
 * 
 * @param title
 * @param url
 * @returns {Boolean}
 */
function openDivModal1(title,url,width,height) {
    //$('.opened-dialogs').dialog("close");

    $('<div class="opened-dialogs">').dialog({
        //position:  ['center',20],
        open: function () {
            $(this).load(url);
        },
        close: function(event, ui) {
	        $(this).remove();
        },
        title: title,
        minWidth: width,
        minHeight: height,
        modal: true,
        buttons: [
      			{
      				text: "적용",
      				click: function() {
      					$(".ui-button-text").click();
      				}
      			},
      			{
      				text: "닫기",
      				click: function() {
      					$(".ui-button-text").click();
      				}
      			}
      		]
    }).css({height:height+"px", overflow:"auto"});

    return false;
}

