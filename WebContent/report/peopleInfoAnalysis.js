/**
 * 报表统计--人口信息统计分析
 */

Ext.namespace("report.analysisPeople");

report.analysisPeople.URL = {
	analysis: '/report/analysis-people!peopleInfoAnalysis.do',
	exports: '/report/analysis-people!export.do'
};

report.analysisPeople.View = function(basePath){
	var _this = this;
	
	this.communityIdText = new Ext.form.ComboBox({
		fieldLabel: '所属社区'
		,hiddenName : 'communityId'
		,displayField: 'text'
		,width: 150
		,value:this_user_CommunityId
		,disabled:this_user_CommunityId==null?false:true
		,store: hs.StoreFactory.getComboStore(comboJsonData["COMMUNITY"],true)
		,listeners : {
			 select : function(combo, record, index) {
				 _this.reseauIdText.setValue('');
			 }
		}
	});
	
	var reseauReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "result",
		id : "id"
	}, Ext.data.Record.create(['id', 'text', 'cid']));
		
	var reseauStore = new Ext.data.Store({
		reader : reseauReader,
		data: comboJsonData["RESEAU"]
	});
	this.reseauStore = reseauStore;
	
	var r = new reseauStore.recordType({ id: '', text: ' - 不限 - ',cid:''}, Ext.id());
	reseauStore.insert(0, r);
	
	var reseauIdText = new Ext.form.ComboBox({
		 fieldLabel: '所属网格'
		,hiddenName : 'reseauId'
		,displayField: 'text'
		,width: 150
		,store:reseauStore
		,listeners : {
			'expand' : function(combo, record, index) {
				var _cid = _this.communityIdText.getValue();
				 reseauStore.filterBy(function(record, id) {
					 if(_cid =='' && record.get("cid") == '') {
						 return true;
					 }
					 else {
						 var cid = record.get("cid");
						 if (cid == _cid || cid =="")
							 return true;
						 else
							 return false;
					 }
				 });
			 }
		}
	});
	this.reseauIdText = reseauIdText;
	
	var data=[['按社区', 'byCommunity'],['按网格', 'byReseau'],['居住事由', 'live_cause'],['籍贯(省级)', '1'],['籍贯(市级)', '2'],['籍贯(县级)', '3'],['政治面貌', 'policital_status'],['文化程度', 'education']];
	var axis={
			'byCommunity':{'caption':'按社区统计人员信息','xAxisName':'社区','yAxisName':'人数'},
			'byReseau':{'caption':'按网格统计人员信息','xAxisName':'网格','yAxisName':'人数'},
			'live_cause':{'caption':'按居住事由统计人员信息','xAxisName':'居住事由','yAxisName':'人数'},
			'1':{'caption':'按籍贯(省级)统计人员信息','xAxisName':'籍贯(省级)','yAxisName':'人数'},
			'2':{'caption':'按籍贯(市级)统计人员信息','xAxisName':'籍贯(市级)','yAxisName':'人数'},
			'3':{'caption':'按籍贯(县级)统计人员信息','xAxisName':'籍贯(县级)','yAxisName':'人数'},
			'policital_status':{'caption':'按政治面貌统计人员信息','xAxisName':'政治面貌','yAxisName':'人数'},
			'education':{'caption':'按文化程度统计人员信息','xAxisName':'文化程度','yAxisName':'人数'}
		};
	var storeDs=new Ext.data.SimpleStore({data:data,fields:["key","value"]});
	storeDs.loadData(data);
	var categoryText = new Ext.form.ComboBox({
		fieldLabel:'统计类别',
		name: 'category',
		value:'byCommunity',
		typeAhead: true,
		forceSelection: false,
		store: storeDs,
		triggerAction: 'all',
		valueField: 'value',
		displayField: 'key',
		mode: 'local',
		width: 150
	});
	this.categoryText = categoryText;
	
	var provinceIdText = new Ext.form.Hidden({
		name : 'provinceId',
		id : 'provinceId'
	});
	this.provinceIdText = provinceIdText;
	
	var cityIdText = new Ext.form.Hidden({
		name : 'cityId',
		id : 'cityId'
	});
	this.cityIdText = cityIdText;
	
	
	var dataChart=[['柱状图', '0', basePath+'/resources/hs/icons/chart_bar.png'],['折线图', '1', basePath+'/resources/hs/icons/chart_curve.png'],['饼状图', '2', basePath+'/resources/hs/icons/chart_pie.png']]; 
	var chartDs=new Ext.data.SimpleStore({data:dataChart,fields:["key","value","img"]});
	chartDs.loadData(dataChart);
	var et=chartDs.getAt(0).get("value");
	var typeComb=new Ext.form.ComboBox({
		fieldLabel:'图形类型',
		name:'typeChart',
		id:'typeChart',
		typeAhead: true,
		forceSelection :true,
		store:chartDs,
		loadingText:'正在加载...',
		editable:false,
		triggerAction: 'all',
		valueField: 'value',
		displayField: 'key',
		tpl: '<tpl for="."><div x-combo-list-item" class="x-combo-list-item"><img src="{img}" width="16" height="16">&nbsp;{key}</div></tpl>',
		mode: 'local',
		width: 120
	});		
	typeComb.setValue(et);
	
	var returnBtn = new Ext.Button({
		text: '<<返回',
		hidden:true,
		handler: function(){
			if(_this.categoryText.getValue() == '3'){
				categoryText.setValue('2');
			}else if(_this.categoryText.getValue() == '2'){
				categoryText.setValue('1');
				returnBtn.hide();
			}else if(_this.categoryText.getValue() == 'byReseau'){
				_this.communityIdText.setValue('');
				categoryText.setValue('byCommunity');
				returnBtn.hide();
			}
			
			store2.loadData();
		}
	});
	
	var store2 = hs.StoreFactory.getStore(report.analysisPeople.URL.analysis,
			[{name:'COMMUNITY_ID'},{name:'ANALYSIS_COUNT'},{name:'LABEL'}]);
	
	store2.loadData = function(communityId,reseauId,type) {
		hs.StoreHelper.beforeload(store2, {
			communityId:_this.communityIdText.getValue(),
			reseauId:_this.reseauIdText.getValue(),
			category:_this.categoryText.getValue(),
			provinceId:_this.provinceIdText.getValue(),
			cityId:_this.cityIdText.getValue()
		});
		store2.load({params : {start : 0,limit : 30}});
	};
	
	typeComb.on('select', function() {
		store2.loadData();
	});
	
	var searchByTolocation = new Ext.Button({
		text: '统计',
		width: 80,
		style:'margin-left:80px;margin-top:5px;',
		iconCls:'hs-button-search',
		handler: function(){
			store2.loadData();
		}
	});
	searchByTolocation.handler();
	
	//左边统计工具栏
	var selectPanel2 = new Ext.Panel({
		id: 'leftToolbar',
	  	layout: 'form',
	  	region : 'west',
	  	title: '人口信息分析',
	  	frame: true,
	  	collapsible: true,//可以折叠
	  	width: 250, //宽度
	  	minSize: 250,//可以被拉到最小的宽度
	    maxSize: 250,//可以被拉到最大的宽度
	    margins:'2 0 2 2',//样式表，内补丁大小
	    autoScroll: true,
	    labelAlign: 'right',
	    defaults : {
			labelWidth : 60
		},
	    items: [{
		    xtype:'fieldset',
		    title: '统计条件',
			labelAlign: 'right',
			bodyBorder: false,
		    items :[
				_this.communityIdText,
				_this.reseauIdText,
				_this.categoryText,
				searchByTolocation
		    ]
		}]
	});
	
	var gridPanel = new Ext.grid.GridPanel({
		title:"列表展现",
		ds: store2,
		border:false,
		stripeRows:true,
		loadMask: {msg:'数据加载中，请稍候...'},
		cm: new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{header: "类别",dataIndex: 'LABEL',width: 180,align:'left'},
			{header: "人数",dataIndex: 'ANALYSIS_COUNT',	width: 80 ,align:'right'}
	    ]),
	    enableColumnHide: false,
		enableColumnMove: false,
		enableColumnResize: true,
		enableHdMenu: false,
		enableRowHeightSync: false,
		monitorWindowResize: false,
		stripeRows:true,
		trackMouseOver: true,
		tbar: [{
			text: '导出',
			iconCls:'icon_excel',
			handler: function(){
				var p = Ext.urlEncode(Ext.applyIf(store2.baseParams, axis[store2.baseParams.category]));
				window.open(basePath+report.analysisPeople.URL.exports+'?'+p);
			}
		}],
		bbar: new Ext.PagingToolbar({
            pageSize: PAGE_SIZE,
            store: store2,
            displayInfo: true,
            plugins: [
            	new Ext.ux.plugins.PageComboResizer()
            ]
        })
	});
	
	// 省级统计时，单击图表，显示该省所有市的统计
	showByProvince = function(communityId) {
		_this.provinceIdText.setValue(communityId);
		categoryText.setValue('2');
		store2.loadData();
		returnBtn.show();
	};
	
	// 市统计时，单击图表，显示该市所有县的统计
	showByCounty = function(cityId) {
		_this.cityIdText.setValue(cityId);
		categoryText.setValue('3');
		store2.loadData();
		returnBtn.show();
	};
	
	// 社区统计时，单击图表，显示该社区所有网格的统计
	showByReseau = function(communityId) {
		_this.communityIdText.setValue(communityId);
		categoryText.setValue('byReseau');
		store2.loadData();
		returnBtn.show();
	};
	
    store2.on('load',function(){
    	if(store2.getCount()!=null){
    		var type = categoryText.getValue();
	    	var strXml = "<graph caption='"+ axis[type].caption +
	    			"' rotateyaxisName='0' yAxisName='" + axis[type].yAxisName + "' xAxisName='" + axis[type].xAxisName + "' baseFontSize='12' animation='true' decimals='1' formatANALYSIS_COUNTberScale='1' formatANALYSIS_COUNTber='1' showPercentValues='1' chartBottomMargin='40' >";
	
	    	store2.each(function(rec, index) {
	    		if(type == '1') {
	    			strXml = strXml+ "<set label='"+ rec.data.LABEL +"' value='"+ rec.data.ANALYSIS_COUNT +"' link='JavaScript:showByProvince(\"" + rec.data.COMMUNITY_ID + "\")' />";
	    		} else if(type == '2'){
	    			strXml = strXml+ "<set label='"+ rec.data.LABEL +"' value='"+ rec.data.ANALYSIS_COUNT +"' link='JavaScript:showByCounty(\"" + rec.data.COMMUNITY_ID + "\")' />";
	    		}else if(type == 'byCommunity'){
	    			strXml = strXml+ "<set label='"+ rec.data.LABEL +"' value='"+ rec.data.ANALYSIS_COUNT +"' link='JavaScript:showByReseau(\"" + rec.data.COMMUNITY_ID + "\")' />";
	    		}else{
	    			strXml = strXml+ "<set label='"+ rec.data.LABEL +"' value='"+ rec.data.ANALYSIS_COUNT +"' />";
	    		}
			});
	    	
	    	strXml = strXml + "</graph>";
	    	//加载图表
	    	var chartTypeStr = Ext.getCmp('typeChart').getValue();
	    	var chart;
	    	if(chartTypeStr == 0){
	    		chart = new FusionCharts(basePath+"/resources/FusionCharts/Column3D.swf?ChartNoDataText=无数据显示",
	    				"myChartId", "100%", chartPanel.getHeight()==0?gridPanel.getHeight():chartPanel.getHeight());
	    	}else if(chartTypeStr == 1){
	    		chart = new FusionCharts(basePath+"/resources/FusionCharts/Line.swf?ChartNoDataText=无数据显示", "myChartId", "100%",
	    				chartPanel.getHeight()==0?gridPanel.getHeight():chartPanel.getHeight());
	    	}else{
	    		chart = new FusionCharts(basePath+"/resources/FusionCharts/Pie3D.swf?ChartNoDataText=无数据显示", "myChartId", "100%",
	    				chartPanel.getHeight()==0?gridPanel.getHeight():chartPanel.getHeight());
	    	}
			     
	    	chart.addParam("wmode","Opaque");
			chart.setDataXML(strXml);
			chart.render("divChart");
			
			// 设置数据列表
			gridPanel.reconfigure(store2, new Ext.grid.ColumnModel([
               new Ext.grid.RowNumberer(),
               {header: axis[type].xAxisName,dataIndex: 'LABEL',width: 180,align:'left'},
               {header: axis[type].yAxisName,dataIndex: 'ANALYSIS_COUNT',	width: 80 ,align:'right'}
            ]));
    	}
	});
    
    var chartPanel = new Ext.Panel({
	  	id: 'chartPanel',
	  	title:'图表展示',
	  	tbar:[typeComb,'->',returnBtn],
	  	contentEl:'divChart'
	});
    
  //窗体布局
	var viewport = new Ext.Viewport({
	     layout:'border',
	     items:[
	         selectPanel2,
	         new Ext.TabPanel({
		     		region:'center',
		     		deferredRender:false,
		     		margins:'2 2 2 0',
		     		activeTab:0,
		     		items:[
		     		    chartPanel,gridPanel
					]
		     	})
	      ]
	});
};