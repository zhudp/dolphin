Ext.namespace("analysis.company");

analysis.company.URL = {
		analysis: '/report/analysis-company!analysis.do',
		exports: '/report/analysis-company!export.do'
};

analysis.company.View = function(basePath){
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
	
	var data=[['按社区', 'byCommunity'],['按网格', 'byReseau'],['企业类别', 'byCompanyType'],['员工人数', 'byPeopleCount']];
	var axis={
		'byCompanyType':{'caption':'按企业类别统计企业信息','xAxisName':'企业类别','yAxisName':'企业数'},
		'byPeopleCount':{'caption':'按企业员工人数统计企业信息','xAxisName':'企业名称','yAxisName':'员工数'},
		'byCommunity':{'caption':'按社区统计企业信息','xAxisName':'社区','yAxisName':'企业数'},
		'byReseau':{'caption':'按网格统计企业信息','xAxisName':'网格','yAxisName':'企业数'}
	};
	var storeDs=new Ext.data.SimpleStore({data:data,fields:["key","value"]});
	storeDs.loadData(data);
	var typeText = new Ext.form.ComboBox({
		fieldLabel:'统计类别',
		name: 'type',
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
	this.typeText = typeText;
	
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
			_this.communityIdText.setValue(this_user_CommunityId);
			typeText.setValue('byCommunity');
			store.loadData();
			returnBtn.hide();
		}
	});
	
	var store = hs.StoreFactory.getStore(analysis.company.URL.analysis,
		[{name:'COMMUNITY_ID'},{name:'LABEL'},{name:'ANALYSIS_COUNT'}]);
	store.loadData = function(communityId,reseauId,type) {
		hs.StoreHelper.beforeload(store, {
			communityId:_this.communityIdText.getValue(),
			reseauId:_this.reseauIdText.getValue(),
			type:_this.typeText.getValue()
		});
		store.load({params : {start : 0,limit : gridPanel.getBottomToolbar().pageSize || PAGE_SIZE}});
	};
	
	typeComb.on('select', function() {
		store.loadData();
	});
	
	var searchBtn = new Ext.Button({
		text: '统计',
		width: 80,
		style:'margin-left:80px;margin-top:5px;',
		iconCls:'hs-button-search',
		handler: function(){
			store.loadData();
		}
	});
	
	
	//左边统计工具栏
	var searchPanel = new Ext.Panel({
	  	id: 'leftToolbar',
	  	layout: 'form',
	  	region : 'west',
	  	title: '企业结构分析',
	  	frame: true,
	  	collapsible: true,//可以折叠
	  	width: 250, //宽度
	  	minSize: 250,//可以被拉到最小的宽度
	    maxSize: 250,//可以被拉到最大的宽度
	    margins:'2 0 2 2',//样式表，内补丁大小
	    autoScroll: true,
	    labelAlign: 'right',
	    defaults : {
			labelWidth : 55
		},
	    items: [{
		    xtype:'fieldset',
		    title: '统计条件',
			labelAlign: 'right',
			bodyBorder: false,
		    items :[
		        _this.communityIdText,
		        _this.reseauIdText,
		        _this.typeText,
		        searchBtn
		    ]
		}]
	});
    
    var chartPanel = new Ext.Panel({
	  	title:'图表展示',
	  	tbar:[typeComb,'->',returnBtn],
	  	contentEl:'divChart'
	});
    
	var gridPanel = new Ext.grid.GridPanel({
		title:"列表展现",
		ds: store,
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
				
				var p = Ext.urlEncode(Ext.applyIf(store.baseParams, axis[store.baseParams.type]));
				window.open(basePath+analysis.company.URL.exports+'?'+p);
			}
		}],
		bbar: new Ext.PagingToolbar({
            pageSize: PAGE_SIZE,
            store: store,
            displayInfo: true,
            plugins: [
            	new Ext.ux.plugins.PageComboResizer()
            ]
        })
	});
	
	// 社区统计时，单击图表，显示该社区所有网格的统计
	showByReseau = function(communityId) {
		_this.communityIdText.setValue(communityId);
		typeText.setValue('byReseau');
		store.loadData();
		returnBtn.show();
	};
	
    store.on('load',function(){
    	if(store.getCount()!=null){
    		
    		// 设置图表
    		var type = typeText.getValue();
	    	var strXml = "<graph caption='"+ axis[type].caption +
	    			"' rotateYAxisName='90' yAxisName='" + axis[type].yAxisName + "' xAxisName='" + axis[type].xAxisName 
	    			+ "' baseFontSize='12' animation='true' decimals='1' formatNumberScale='1' formatNumber='1' showPercentValues='1' chartBottomMargin='40' >";
	
	    	store.each(function(rec, index) {
	    		if(type == 'byCommunity') {
	    			strXml = strXml+ "<set label='"+ rec.data.LABEL +"' value='"+ rec.data.ANALYSIS_COUNT +"' link='JavaScript:showByReseau(\"" + rec.data.COMMUNITY_ID + "\")' />";
	    		} else {
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
			gridPanel.reconfigure(store, new Ext.grid.ColumnModel([
               new Ext.grid.RowNumberer(),
               {header: axis[type].xAxisName,dataIndex: 'LABEL',width: 180,align:'left'},
               {header: axis[type].yAxisName,dataIndex: 'ANALYSIS_COUNT',	width: 80 ,align:'right'}
            ]));
    	}
	});
    
    searchBtn.handler();
    var tabPanel = new Ext.TabPanel({
 		region:'center',
 		deferredRender:false,
 		margins:'2 2 2 0',
 		activeTab:0,
 		items:[
 		    chartPanel
 		    ,gridPanel
		]
 	});

	//窗体布局
	var viewport = new Ext.Viewport({
	     layout:'border',
	     items:[
	         searchPanel,
	         tabPanel
	     ]
	});
};