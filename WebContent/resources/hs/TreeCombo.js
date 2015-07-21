/**
 * 
 * 支持功能： 1.自动宽度和高度调整；弹出层会根据依据树的宽高调整自身宽高，注意：设置listWidth属性后弹出
 * 层宽度将固定，树面板在需要时会出现水平滚动条；设置minListWidth属性可限定弹出层最小宽度， 默认为TreeCombo的宽度；
 * 2.自动寻找选中结点：弹出层展开后会根据当前值找到对应结点并根据其树中路径展开树，其余结点将 收缩。 3.程序赋值：通过setValue(String
 * nodeId);方法可通过代码设置TreeCombo的Value值，此时程序会
 * 自动找到树节点id对应的树节点text并将其显示到输入域中，如果找不到对应树节点，输入域会显示
 * 配置项valueNotFoundText的值提示这是个程序错误！
 * 4.自动检测：输入域默认是可以编辑的，ComboBox失去焦点后自动检测当前输入域中内容是否有对应的树
 * 结点，有的话设置value值为对应结点，没有则自动退回到上一次失去焦点时的状态；
 * 5.键盘导航：down(选择上一节点)、up（选择下一结点）、enter（选中当前选择结点）、left（收
 * 缩当前结点）、right（展开当前结点）、esc（取消编辑）、tab（取消编辑并跳转到下一个输入域）；
 * 6.忽略父节点：ignoreFolder可设置父节点不能被选中，此属性为true时用户点击父节点时面板不会 收缩、过滤时也会忽略父节点；
 * 也不会被赋值，调用setValue方法时如果找到的是父节点输入域会显示配置项valueNotFoundText的值；
 * 7.拼音首字母过滤：用户对输入域编辑时树节点都会根据输入域中字母进行拼音首字母过滤，匹配结点
 * 将保留并展开，内置缓存处理以提高多次查询的检索速度，可与键盘导航功能同时使用，最大限度提高 数据录入速度；
 * 8.延迟初始化：默认情况下lazyInit属性为true，此时tree会在TreeCombo获得焦点后才进行创建，这样做 可以提高页面加载速度。
 * 9.可输入状态下输入域允许粘贴，失去焦点后组件会找到对应树节点并为值域（value）赋值,找不到显示组件 的emptyText
 * 10.默认值支持：如果需要默认值可以将结点id赋给value，默认情况下因为使用延迟加载，默认值只有在组件
 * 获得焦点后才会通过树找到要结点text属性并显示到界面上，如果需要加载后就显示初始值可将lazyInit设置 为false
 * 
 * 注意事项： 1.如果树结点不是由root一次性加载，功能479将无法适用，此时请配置TreeCombo的editable属性为false；
 * 2.不同与官方的ComboBox，此下拉框没有forceSelection配置项（该配置项为false时用户可自定义下
 * 拉框内容），因为我不知道允许用户自定义内容有什么意义；
 * 3.为ComboBox配置tree属性必须配置Object直接量对象（用{}来声明的对象）不能是new创建的实例，
 * TreeCombo会负责树对象的创建和销毁； 4.树的根结点是不可见的，此时树在创建时就会执行root的expand方法开始加载其子结点，因而树的root
 * 结点必须以new Ext.tree.AsyncTreeNode方式进行声明，否则无法添加事件判断其加载完成状态。
 * 5.如果树节点过多，过滤功能将严重影响性能，此时可设置
 * 
 * @author chemzqm@gmail.com
 * @version 1.0.0
 * @createTime 2010-04-24 16:40:30
 * 
 */
Ext.ns('QM.ui');

QM.ui.TreeCombo = Ext.extend(Ext.form.TriggerField, {
			shadow : 'sides',
			/**
			 * @cfg minListWidth 弹出层最小宽度，必须大于ComboBox宽度，与listWidth同时使用时无效
			 */
			/**
			 * @cfg listWidth 弹出层固定宽度，设置后弹出层不根据树宽度进行调整，必须大于ComboBox宽度
			 * 
			 */
			/**
			 * @cfg hiddenName 隐藏表单域名，form方式提交时需要，负责把value传到后台
			 */
			/**
			 * @cfg listClass 阴影层样式
			 */

			listAlign : 'tl-bl?',
			maxHeight : 300,
			queryDelay : 300,// 查询函数缓冲时间(缓冲时间内再次调用将取消上次调用)
			valueNotFoundText : '请选择...',
			triggerAction : 'all',// 下拉按钮点击时查询条件'all'查询出所有数据 'query'根据输入项进行前端匹配
			ignoreFolder : true,// 父节点不做为数据源
			lazyInit : true,// 控件获得焦点时才会初始化下拉框包括树
			loadingText : '加载中...',
			emptyText : '请选择...',
			forceSelection : true,// 输入框的值只能是列表存有的值
			enableQuery : true,

			initComponent : function() {
				this.hiddenPkgs = [];// 隐藏的分支节点
				QM.ui.TreeCombo.superclass.initComponent.call(this);
				this.addEvents('expand', 'collapse', 'beforeselect', 'select');
			},
			onRender : function(ct, position) {
				QM.ui.TreeCombo.superclass.onRender.call(this, ct, position);
				if (this.hiddenName) {
					this.hiddenField = this.el.insertSibling({
								tag : 'input',
								type : 'hidden',
								name : this.hiddenName,
								id : (this.hiddenId || this.hiddenName)
							}, 'before', true);
				}
				if (this.lazyInit) {
					this.on('focus', this.initList, this, {
								single : true
							});
				} else {
					this.initList();
				}
			},

			initEvents : function() {
				QM.ui.TreeCombo.superclass.initEvents.call(this);
				this.keyNav = new Ext.KeyNav(this.el, {
							"up" : this.onKeyDown,
							"down" : function(e) {
								if (!this.isExpanded()) {
									this.onTriggerClick();
								} else {
									this.onKeyDown(e);
								}
							},
							"left" : this.onKeyDown,
							"right" : this.onKeyDown,
							"enter" : function() {
								var node = this.tree.selModel.getSelectedNode();
								this.onTreeClick(node);
							},
							"esc" : function(e) {
								this.collapse();
							},
							"tab" : function(e) {
								this.collapse();
								return true;
							},
							scope : this,
							forceKeyDown : true
						});
				this.dqTask = new Ext.util.DelayedTask(this.initQuery, this);
				if (!this.enableKeyEvents) {
					this.mon(this.el, 'keyup', this.onKeyUp, this);
				}
			},
			// 上下左右回车让TreeSelectionModel来辅助实现
			onKeyDown : function(e) {
				var sm = this.tree.getSelectionModel();
				if (sm) {
					sm.onKeyDown(e);
				}
				this.el.focus();
			},
			initQuery : function() {
				this.doQuery(this.getRawValue());
			},
			onKeyUp : function(e) {
				var k = e.getKey();
				if (this.editable !== false && this.readOnly !== true && (k == e.BACKSPACE || !e.isSpecialKey())) {
					this.dqTask.delay(this.queryDelay);
				}
				Ext.form.ComboBox.superclass.onKeyUp.call(this, e);
			},
			initList : function() {
				if (!this.list) {
					var cls = 'x-combo-list', listParent = Ext.getDom(this.getListParent() || Ext.getBody()), zindex = parseInt(
							Ext.fly(listParent).getStyle('z-index'), 10);
					if (this.ownerCt && !zindex) {// 找到父容器定义的z-index
						this.findParentBy(function(ct) {
									zindex = parseInt(ct.getPositionEl().getStyle('z-index'), 10);
									return !!zindex;
								});
					}
					this.list = new Ext.Layer({
								parentEl : listParent,
								shadow : this.shadow,
								cls : [cls, this.listClass].join(' '),
								constrain : false,
								zindex : (zindex || 12000) + 5
							});
					if (!this.minListWidth) {
						this.minListWidth = this.wrap.getWidth();
					}
					this.list.setStyle('width', this.minListWidth);
					this.list.setStyle('height', 'auto');
					this.list.swallowEvent('mousewheel');
					this.innerList = this.list.createChild({
								cls : cls + '-inner'
							});
					this.initInner();
				}
			},
			initInner : function() {
				Ext.apply(this.tree, {
							applyTo : this.innerList,
							border : false,
							rootVisible : false,
							autoScroll : true
						});
				var root = this.tree.root;
				if (root instanceof Ext.tree.AsyncTreeNode) {
					root.on('beforeload', this.onBeforeRootLoad, this, {
								single : true
							});
					root.on('load', this.onRootLoad, this, {
								single : true
							});
				}
				this.tree = Ext.create(this.tree, 'treepanel');
				this.tree.on({// 加载完毕后再给树添加监听
					scope : this,
					expandnode : this.onTreeResize,
					collapsenode : this.onTreeResize,
					click : this.onTreeClick
				});
				if (this.editable) {
					this.filter = new QM.ux.TreeFilter(this.tree, {
								ignoreFolder : this.ignoreFolder,
								clearAction : 'collapse'
							});
				}
				if (this.value) {
					this.setValue(this.value);
				}
			},
			// @private
			onRootLoad : function() {
				this.isLoading = false;
				if (this.value) {
					this.setValue(this.value);
				}
				this.innerList.child('.loading-indicator').remove();
				if (this.isExpanded()) {
					this.onLoad();
				}
			},
			// @private
			onTreeResize : function() {
				if (this.isExpanded() && this.isQuerying !== true) {
					this.restrict();
					this.el.focus();
				}
			},
			// @private
			onBeforeRootLoad : function() {
				this.isLoading = true;
				this.innerList.insertFirst({
							tag : 'div',
							cls : 'loading-indicator',
							html : this.loadingText
						});
			},
			// @private
			onLoad : function() {
				if (!this.hasFocus) {
					return;
				}
				this.expand();
				if (!this.selectByNode(this.value, true)) {
					this.selectByNode(this.tree.root.firstChild, true);// 没有的话选中第一个结点
				}
				if (this.editable) {
					this.el.focus();
				}
			},
			isExpanded : function() {
				return this.list && this.list.isVisible();
			},
			expand : function() {
				if (this.isExpanded() || !this.hasFocus) {
					return;
				}
				this.list.show();
				this.mon(Ext.getDoc(), {
							scope : this,
							mousewheel : this.collapseIf,
							mousedown : this.collapseIf
						});
				this.fireEvent('expand', this);
			},
			collapseIf : function(e) {
				if (!e.within(this.wrap) && !e.within(this.list)) {
					this.collapse();
				}
			},
			// @public
			collapse : function() {
				if (!this.isExpanded()) {
					return;
				}
				this.list.hide();
				Ext.getDoc().un('mousewheel', this.collapseIf, this);
				Ext.getDoc().un('mousedown', this.collapseIf, this);
				this.fireEvent('collapse', this);
			},
			// 重置弹出层宽度
			restrict : function() {
				this.innerList.dom.style.width = '10px';// 外层挤压，值太小会被Chrome忽略
				var body = this.tree.body.dom;
				wpad = this.list.getFrameWidth('lr'), // 边宽
				wa = Math.max(body.clientWidth, body.offsetWidth, body.scrollWidth), w = Math.max(wa, this.minListWidth - wpad);
				w = this.listWidth ? this.listWidth : w;
				this.list.setWidth(w + wpad);
				this.innerList.setWidth(w);
				this.list.alignTo.apply(this.list, [this.el].concat(this.listAlign));
				return;

			},
			getListParent : function() {
				return document.body;
			},
			onTriggerClick : function() {
				if (this.readOnly || this.disabled) {
					return;
				}
				if (this.isExpanded()) {
					this.collapse();
					this.el.focus();
				} else {
					this.onFocus({});
					if (this.filter) {
						this.filter.clear();
					}
					this.onLoad();
				}
			},
			doQuery : function(q) {
				q = Ext.isEmpty(q) ? '' : q;
				if (!this.isLoading && this.filter) {
					this.filter.filter(q);
				}
				if (this.filter.isCleared()) {
					this.tree.root.firstChild.select();
				} else if (this.filter.hasMatch()) {
					this.filter.matches[0].select();
				}
				this.el.focus();
				this.expand();
				this.restrict();
			},
			onTreeClick : function(node) {
				if (this.fireEvent('beforeselect', this, node) !== false) {
					if (this.ignoreFolder && !node.leaf)
						return;
					this.setValue(node);
					this.collapse();
					this.fireEvent('select', this, node);
				}
			},
			// @public 确保树已加载所需结点再调用此方法，如果传的是id但是找不到结点value域将置空
			setValue : function(node) {// 根据TreeNode的id或者TreeNode对象设置值，显示TreeNode的text属性
				if (!this.tree.rendered || this.isLoading) {
					return null;
				}
				if (typeof node == 'string') {
					node = this.tree.getNodeById(node);
				}
				var text;
				if (!node || (this.ignoreFolder && !node.leaf)) {
					text = this.valueNotFoundText;
				} else {
					text = node.text;
				}
				QM.ui.TreeCombo.superclass.setValue.call(this, text);
				if (this.hiddenField) {
					if(node!=null)
					  this.hiddenField.value = node.id;
					else
					  this.hiddenField.value ='';
				}
				this.lastSelectionText = text;
				this.value = node ? node.id : '';
				return this;
			},
			getValue : function() {
				return Ext.isDefined(this.value) ? this.value : '';
			},
			clearValue : function() {
				if (this.hiddenField) {
					this.hiddenField.value = '';
				}
				this.setRawValue('');
				this.lastSelectionText = '';
				this.value = '';
			},
			// private
			validateBlur : function() {
				return !this.list || !this.list.isVisible();
			},
			// 检查输入值是不是列表里有的，有的话设置对应value
			beforeBlur : function() {
				var val = this.getRawValue();
				node = this.tree.root.findChild('text', val, true);
				if (!node) {
					if (val.length > 0 && val != this.emptyText) {
						this.el.dom.value = Ext.value(this.lastSelectionText, '');// 值空或是上一次输入
					} else {// 输入域清空，所有值清空
						this.clearValue();
					}
				} else if (node) {
					this.setValue(node);
				}
			},
			// 根据id值或node对象选择到相应node并显示出来，scrollIntoView是否需要滚动
			selectByNode : function(node, scrollIntoView) {
				if (!Ext.isEmpty(node, true)) {
					if (typeof node == 'string') {
						node = this.tree.getNodeById(node);
					}
					if (node) {
						this.tree.collapseAll();
						this.tree.expandPath(node.getPath());// 只展开选中结点
						node.select();
						if (scrollIntoView === true)
							node.ensureVisible();
						return true;
					}
				}
				return false;
			},
			// private
			postBlur : function() {
				QM.ui.TreeCombo.superclass.postBlur.call(this);
				this.collapse();
				this.inKeyMode = false;
			},
			onDestroy : function() {
				if (this.dqTask) {
					this.dqTask.cancel();
					this.dqTask = null;
				}
				Ext.destroy(this.tree, this.list, this.filter);
				QM.ui.TreeCombo.superclass.onDestroy.call(this);
			}
		});

Ext.reg('treecombo', QM.ui.TreeCombo);
