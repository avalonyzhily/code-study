/**
 * 右键菜单
 * Created by avalonyzhily on 17-8-23.
 */
//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外  
function forbidBackSpace(e) {  
    var ev = e || window.event; //获取event对象   
    var obj = ev.target || ev.srcElement; //获取事件源   
    var t = obj.type || obj.getAttribute('type'); //获取事件源类型   
    //获取作为判断条件的事件类型   
    var vReadOnly = obj.readOnly;  
    var vDisabled = obj.disabled;  
    //处理undefined值情况   
    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;  
    vDisabled = (vDisabled == undefined) ? true : vDisabled;  
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，   
    //并且readOnly属性为true或disabled属性为true的，则退格键失效   
    var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);  
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效   
    var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";  
    //判断   
    if (flag2 || flag1) return false;  
}  
//禁止后退键 作用于Firefox、Opera  
document.onkeypress = forbidBackSpace;  
//禁止后退键  作用于IE、Chrome  
document.onkeydown = forbidBackSpace; 

document.onclick = hidden;
var notInit = {"flag":false};
//
var rightSelectObj = {"selectedBlock": undefined,"selectedConn":undefined};
function openRightMenu(e) {
    hidden();
    var e = e || event;
    var rightMenu = $("#click-menu");
    var left = e.pageX + document.body.scrollLeft-5;
    var top = e.pageY + document.body.scrollTop-5;
    rightMenu.css("left", left.toString() + "px");
    rightMenu.css("top", top.toString() + "px");
    rightMenu.css("visibility", "visible");
    rightMenu.on("click", transfer);
    rightMenu.on("contextmenu", no_context_menu);
    rightSelectObj.selectedBlock = e.srcElement || e.target;
    return false;
}

function transfer(e) {
    e = e || event;
    e.cancelBubble = true;
}

function no_context_menu(e) {
    e = e || event;
    e = e || event;
    e.cancelBubble = true;
    return;
}

function hidden() {
    $("#click-menu").css("visibility", "hidden");
    $("#click-menu-conn").css("visibility", "hidden");
}

function deleteSelectBlockObj() {
    //要先保存父元素的DOM,因为出现确认对话框之后(this)会消失
    var rightSelectedObj = $(rightSelectObj.selectedBlock);
    var rightSelectedObjID = rightSelectedObj.attr('id');
    $("#delete-confirm").dialog({
        resizable: false,
        height: "auto",
        width: 300,
        modal: true,
        buttons: {
            "确认": function () {
                var uuid = rightSelectedObjID.replace("roundedRect-"+"D"+"-","");
                jsPlumb.removeAllEndpoints(rightSelectedObjID);
                rightSelectedObj.remove();
                $("#delete-confirm").dialog("close");
                popMessageTips("删除成功!");

            },
            "取消": function () {
                $(this).dialog("close");
                return false;
            }
        }
    });
}
function deleteSelectConnObj() {
    //要先保存父元素的DOM,因为出现确认对话框之后(this)会消失
    var rightSelectedObj = rightSelectObj.selectedConn;
    var linkUuid = rightSelectedObj.getParameter("linkUuid");
    $("#delete-confirm").dialog({
        resizable: false,
        height: "auto",
        width: 300,
        modal: true,
        buttons: {
            "确认": function () {
                if(linkUuid){
                    //说明已经保存过
                    var uuid = linkUuid.replace("link-"+"D"+"-","");
                    jsPlumb.detach(rightSelectedObj);
                    $("#delete-confirm").dialog("close");
                    popMessageTips("删除成功");
                }else{
                    jsPlumb.detach(rightSelectedObj);
                    $("#delete-confirm").dialog("close");
                }
            },
            "取消": function () {
                $(this).dialog("close");
                return false;
            }
        }
    });
}

function closeWin() {
    resetForm();
    rightSelectObj.selectedBlock = undefined;
    rightSelectObj.selectedConn = undefined;
}

function popMessageTips(info) {
    $("#message-tips-p").text(info);
    $("#message-tips").dialog({
        resizable: false,
        height: "auto",
        width: 400,
        modal: true,
        buttons: {
            "确认": function () {
                $("#message-tips").dialog("close");
            }
        }});
}