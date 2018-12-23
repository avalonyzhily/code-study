//*********************************jsPlumb基础信息配置区域*********************************

//根蒂根基连接线样式
var connectorPaintStyle = {
    lineWidth: 2,
    strokeStyle: "#34a5f2",
    joinstyle: "round",
    outlineColor: "#34a5f2",
    outlineWidth: 1
};

// 鼠标悬浮在连接线上的样式
var connectorHoverStyle = {
    lineWidth: 2,
    strokeStyle: "#9fb8fe",
    outlineWidth: 1,
    outlineColor: "#9fb8fe"
};

var hollowCircle = {
    endpoint: ["Rectangle", {width: 10,height:10}],  //端点的外形
    connectorStyle: connectorPaintStyle,//连接线的色彩,大小样式
    connectorHoverStyle: connectorHoverStyle,
    paintStyle: {
        strokeStyle: "#297fba",
        fillStyle: "#297fba",
        opacity: 0.5,
        radius: 2,
        lineWidth: 4
    },//端点的色彩样式
    isSource: true,    //是否可以拖动(作为连线出发点)
    connector: ["Flowchart", {curviness: 100}],//设置连线为贝塞尔曲线
    isTarget: true,    //是否可以放置(连线终点)
    maxConnections: -1,    // 设置连接点最多可以连接几条线
    connectorOverlays: [["Arrow", {width: 15, length: 15, location: 1}]]
};

//*********************************jsPlumb基础信息配置区域*********************************

//*********************************流程图数据操作区域*********************************
var list = [];//全部的连接点列表

//序列化全部流程图数据,json格式
function save() {

    var connects = [];
    list = jsPlumb.getConnections();
    for (var i in list) {
        var connector = list[i];
        var endPoints = connector.endpoints;
        var pageSourceId = list[i]['sourceId'];
        var pageTargetId = list[i]['targetId']
        var endPoint1 = endPoints[0];
        var endPoint2 = endPoints[1];
        var connect = {};
        connect["parentNode"] = pageSourceId.replace("roundedRect-"+"D"+"-","");
        connect["childNode"] = pageTargetId.replace("roundedRect-"+"D"+"-","");
        if (endPoint1.elementId == pageSourceId) {
            connect["endPointSourceId"] = endPoint1.getUuid();
            connect["endPointTargetId"] = endPoint2.getUuid();
        }
        if (endPoint2.elementId == pageSourceId) {
            connect["endPointSourceId"] = endPoint2.getUuid();
            connect["endPointTargetId"] = endPoint1.getUuid();
        }
        var linkUuid = connector.getParameter("linkUuid");
        if(linkUuid){
            linkUuid = linkUuid.replace("link-"+"D"+"-","");
            connect["linkUuid"] = linkUuid;
        }
        connect["sortieEffective"] = connector.getLabel();
        connects.push(connect);
    }

    var blocks = [];
    $(".droppable .draggable").each(function (idx, elem) {
        var elem = $(elem);
        blocks.push({
            dgUuid: elem.attr('id').replace("roundedRect-D-",""),
            modelUuid:parentUuid,
            dgName:elem.text(),
            xCoordinate: parseInt(elem.css("left"), 10),
            yCoordinate: parseInt(elem.css("top"), 10),
        });


    });
    var linksAndBlocks = {"blocks":JSON.stringify(blocks),"connects":JSON.stringify(connects)};

    return linksAndBlocks;

}

//通过json加载流程图
function loadChartByJSON(data) {
    var unpack = JSON.parse(data);
    if (!unpack) {
        return false;
    }
    for (var i = 0; i < unpack['block'].length; i++) {
        var BlockId = "";
        var BlockContent = "";
        if("D"=="D"){
            BlockId = unpack['block'][i]['dgUuid'];
            BlockContent = unpack['block'][i]['dgName'];
        }else if("D"=="S"){
            BlockId = unpack['block'][i]['stationUuid'];
            BlockContent = unpack['block'][i]['stationName'];
        }else if("D"=="A"){
            BlockId = unpack['block'][i]['aoUuid'];
            BlockContent = unpack['block'][i]['aoName'];
        }
        BlockContent = BlockContent?BlockContent:"";
        var BlockX = unpack['block'][i]['xCoordinate'];
        var BlockY = unpack['block'][i]['yCoordinate'];

        BlockId = "roundedRect-"+"D"+"-"+BlockId;

        var boxInsetShadowStyle = '10px 10px ' + blockStyleConfig.fillBlurRange + "px 20px " + blockStyleConfig.fillBlurColor + ' inset';

        $('#chart-container').append("<div class=\"draggable roundedRect new-roundedRect\" id=\"" + BlockId + "\">" + BlockContent + "</div>");
        $("#" + BlockId)
            .css("left", parseInt(BlockX))
            .css("top", parseInt(BlockY))
            .css("position", "absolute")
            .css("margin", "0px")
            .css("width", blockStyleConfig.width)
            .css("height", blockStyleConfig.height)
            .css('font', blockStyleConfig.font)
            .css('font-size', blockStyleConfig.fontSize)
            .css('text-align', blockStyleConfig.fontAlign)
            .css('color', blockStyleConfig.fontColor)
            .css('border-radius', blockStyleConfig.borderRadius)
            .css('background', blockStyleConfig.backgroundColor)
            .css('box-shadow', boxInsetShadowStyle)
            .css('border-style', blockStyleConfig.borderStyle)
            .css('border-color', blockStyleConfig.borderColor)
            .css('box-shadow', blockStyleConfig.shadow)
            .css('font-style', blockStyleConfig.fontStyle)
            .css('font-weight', blockStyleConfig.fontWeight)
            .css('font-underline', blockStyleConfig.fontUnderline)
            .css('line-height', blockStyleConfig.lineHeight);

        jsPlumb.addEndpoint(BlockId, {uuid: BlockId + "-TopCenter", anchors: "TopCenter"}, hollowCircle);
        jsPlumb.addEndpoint(BlockId, {uuid: BlockId + "-RightMiddle", anchors: "RightMiddle"}, hollowCircle);
        jsPlumb.addEndpoint(BlockId, {uuid: BlockId + "-BottomCenter", anchors: "BottomCenter"}, hollowCircle);
        jsPlumb.addEndpoint(BlockId, {uuid: BlockId + "-LeftMiddle", anchors: "LeftMiddle"}, hollowCircle);

        jsPlumb.draggable(BlockId);
        $("#" + BlockId).draggable({
           containment: "parent",
	       drag:function(event,ui){
	    	    jsPlumb.repaint($(this));
		        var height = $("#chart-container").height();
		        var top = ui.offset.top;
		        if(parseInt(height)<(parseInt(top)+15)){
		            $("#chart-container").height(parseInt(top)+20);
		        }
		    }
        });//保证拖动不跨界

        $("#" + BlockId).on("contextmenu", openRightMenu);

    }

    for (i = 0; i < unpack['connects'].length; i++) {
        var endPointSourceId = unpack['connects'][i]['endPointSourceId'];
        var endPointTargetId = unpack['connects'][i]['endPointTargetId'];
        var linkUuid = unpack['connects'][i]['linkUuid'];
        var label = unpack['connects'][i]['sortieEffective']
        jsPlumb.connect({
            uuids: [endPointSourceId, endPointTargetId],
            parameters:{"linkUuid":"link-"+"D"+"-"+linkUuid},
            label:label,
            deleteEndpointsOnDetach:false});
    }
    return true;
}

function addBlock(data){
            var trueId = "roundedRect-"+"D"+"-"; //增加复杂度,以便于前端识别
            var left = parseInt(data.xCoordinate);
            var top = parseInt(data.yCoordinate);
                trueId = trueId+data.dgUuid.toString();
            var blockName = data.dgName?data.dgName:"";

            //在div内append元素
            $("#chart-container").append("<div class=\"draggable roundedRect new-roundedRect\" id=\"" + trueId + "\">" + blockName + "</div>");

            $("#"+trueId).css("left", left).css("top", top).css("position", "absolute").css("margin", "0px");
            $("#"+trueId).on("contextmenu", openRightMenu);

            //用jsPlumb添加锚点
            jsPlumb.addEndpoint(trueId, {uuid: trueId + "-TopCenter", anchors: "TopCenter"}, hollowCircle);
            jsPlumb.addEndpoint(trueId, {uuid: trueId + "-RightMiddle", anchors: "RightMiddle"}, hollowCircle);
            jsPlumb.addEndpoint(trueId, {uuid: trueId + "-BottomCenter", anchors: "BottomCenter"}, hollowCircle);
            jsPlumb.addEndpoint(trueId, {uuid: trueId + "-LeftMiddle", anchors: "LeftMiddle"}, hollowCircle);
            jsPlumb.draggable(trueId);
           $("#" + trueId).draggable({
        	    containment: "parent",
                drag:function(event,ui){
                	jsPlumb.repaint($(this));
	                var height = $("#chart-container").height();
	                var top = ui.offset.top;
	                if(parseInt(height)<(parseInt(top)+15)){
	                    $("#chart-container").height(parseInt(top)+20);
	                }
           	    }
           });//保证拖动不跨界
}
//*********************************流程图数据操作区域*********************************
$(document).ready(function () {

    //**************************************UI控制部分***************************************

    //上方工具栏的按钮点击事件
    $('.fl-btn').click(function (event) {
        //取被点击按钮的ID
        var flBtnID = $(this).attr('id');
        switch (flBtnID) {
            case 'chart-save':
                //分享或保存(生成JSON)
                save();
                break;
            default:
                break;
        }
    });

    //***********************************元素拖动控制部分************************************

    //允许元素拖动
    $(".list-content .area").children().draggable({
        //revert: "valid",//拖动之后原路返回
        helper: "clone",//复制自身
        scope: "dragflag"//标识
    });

    $(".droppable").droppable({
        accept: ".draggable", //只接受来自类.dragable的元素
        scope: "dragflag",
        drop: function (event, ui) {
            //获取鼠标坐标
            var left = parseInt(ui.offset.left - $(this).offset().left);
            var top = parseInt(ui.offset.top - $(this).offset().top) + 4;
            if(top<25){
            	top = 25;
            }
            //向后台插入一条节点数据
            var data = {"xCoordinate":left,"yCoordinate":top};
                data["nodeType"] = "D";
                data["dgName"] = " ";
                data["dgUuid"] = Math.ceil(Math.random()*100000);
            addBlock(data);
        }
    });
    //连接事件
    jsPlumb.bind("connection", function (conn, originalEvent) {
        if(notInit.flag){
            rightSelectObj.selectedConn = conn.connection;
            //这里要做一个检查相同的节点不能连两根线
            var id = conn.connection["id"];
            var sourceNodeId = conn.connection["sourceId"];
            var targetNodeId = conn.connection["targetId"];
            var sourceNodeText = $("#"+sourceNodeId).text();
            var targetNodeText = $("#"+targetNodeId).text();
            if((sourceNodeText=="开始"&&targetNodeText=="结束")
            		||(targetNodeText=="开始"&&sourceNodeText=="结束")){
            	popMessageTips("连接无效!");
                jsPlumb.detach(conn);
                originalEvent.preventDefault();
                return false;
            }
            var conns = jsPlumb.getConnections();
            if(conns&&conns.length>0){
                for(var i=0;i<conns.length;i++){
                    var conntemp = conns[i];
                    var sourceNodeIdtemp = conntemp["sourceId"];
                    var targetNodeIdtemp = conntemp["targetId"];
                    if(id!=conntemp["id"]&&(sourceNodeId == sourceNodeIdtemp && targetNodeId == targetNodeIdtemp)
                        ||(sourceNodeId == targetNodeIdtemp && targetNodeId == sourceNodeIdtemp)){
                        popMessageTips("相同节点不能多次连线!");
                        jsPlumb.detach(conn);
                        originalEvent.preventDefault();
                        return false;
                    }
                }
            }
            originalEvent.preventDefault();
            return false;
        }
    });

    jsPlumb.bind("contextmenu", function (conn, originalEvent) {
        hidden();
        var e = originalEvent;
        var rightMenu = $("#click-menu-conn");
        var left = e.pageX + document.body.scrollLeft-5;
        var top = e.pageY + document.body.scrollTop-5;
        rightMenu.css("left", left.toString() + "px");
        rightMenu.css("top", top.toString() + "px");
        rightMenu.css("visibility", "visible");
        rightMenu.on("click", transfer);
        rightMenu.on("contextmenu", no_context_menu);
        rightSelectObj.selectedConn= conn;
        originalEvent.preventDefault();
        return false;
    });

    $(".draggable").draggable({
        drag:function(event,ui){
            var height = $("#chart-container").height();
            var top = ui.offset.top;
            if(parseInt(height)<(parseInt(top)+15)){
                $("#chart-container").height(parseInt(top)+20);
            }
        }
    });
    //***********************************元素拖动控制部分************************************
});