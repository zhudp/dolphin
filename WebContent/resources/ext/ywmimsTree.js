var ywmimsTree = function() {
		// shorthand
	var Tree = Ext.tree;
	return {
		params : null,
		init : function() {
			// yui-ext tree
			var tree = new Tree.TreePanel(this.params["container"], {
				animate : true,
				loader : new Tree.TreeLoader( {
					dataUrl : this.params["url"]
				}),
				containerScroll : true,
				enableDD : false,
				ddScroll : true
			});
			var params = this.params;
			
			// add a tree sorter in folder mode
			new Tree.TreeSorter(tree, {
				folderSort : true,
				property : "id"
			});
			// set the root node
			var root = new Tree.AsyncTreeNode( {
				text : this.params["rootName"],
				draggable : false, // disable root node dragging
				id : this.params["rootId"],
				cls : 'fold'
			});
			
			tree.setRootNode(root);
			this.reload = function() {
				var params = "";
				for (i = 0;i < tree.selectedNode.length; i++) {
					if (i == 0) {
						params = params + "?id=" + tree.selectedNode[i];
					} else {
						params = params + "&id=" + tree.selectedNode[i];
					}
				}
				tree.loader.dataUrl = this.params["url"] + params;
				if(tree.currentNode.leaf){
					tree.currentNode.leaf = false;
				}
				tree.getLoader().load(tree.currentNode,function(){
					tree.expandPath(tree.currentNode.getPath());
				});
				
			};

			tree.selectedNode = new Array();
			tree.currentNode = tree.root;

			// 这段代码就是用于reload
			// 即每次展开节点都要重新载入数据，有的需求如多人同时编辑会需要
			// 去掉这段就不会reload
			this.setSelectNode = function(id) {

				var node = tree.getNodeById(id);
				var tmpNode = node;
				tree.selectNodeAdd(id);
				while (tmpNode != tree.root) {
					tree.selectNodeAdd(tmpNode.parentNode.id);
					tmpNode = tmpNode.parentNode;
				}
			}
			// 新增一个节点时设置当前节点的父节点为展开节点
			this.setCurrentParentNode = function(id) {

				var node = tree.getNodeById(id);
				if (typeof node == 'undefined' || node == null) {
					return;
				}
				 alert(node);
				var currentNode = node;
				tree.selectNodeAdd(currentNode.id);
				while (currentNode != tree.root) {
					tree.selectNodeAdd(currentNode.parentNode.id);
					currentNode = currentNode.parentNode;
				}

			}

			this.setCurrentNode = function(id) {
				var node = tree.getNodeById(id);
				tree.currentNode = node;
			}
			// 添加当前展开的节点
			tree.selectNodeAdd = function(id) {
				var duplicated = false;
				for (i = 0;i < tree.selectedNode.length; i++) {
					if (id == tree.selectedNode[i]) {
						return;
					}
				}
				tree.selectedNode[tree.selectedNode.length] = id;
			}
			
			tree.on('click',function(node, e) {
				tree.currentNode = node;
				if (node.isExpanded()) {
					node.collapse();
	
				} else {
					if (typeof node.parentNode != "undefined"
							&& node.parentNode != null) {
						tree.selectedNode[tree.selectedNode.length] = node.parentNode.id;
	
						var currentNode = node;
						tree.selectNodeAdd(currentNode.id);
						while (currentNode != tree.root) {
							tree.selectNodeAdd(currentNode.parentNode.id);
							currentNode = currentNode.parentNode;
						}
					}
					if (!node.leaf) {
						node.expand();
					}
				}
				if (node == tree.root) {
					return;
				}
				window.frames[params["editFrame"]].location.replace(params["editUrl"] + node.id);
		  });

			tree.on('collapse', function(node, e) {
				if (node.leaf) {
					return;
				}
				// 在已展开节点集中删除刚收缩的节点集
				if (typeof node.parentNode != "undefined"
						&& node.parentNode != null) {
					for (i = 0;i < tree.selectedNode.length; i++) {
						if (tree.selectedNode[i] == node.id) {
							tree.selectedNode.splice(i, 1);
						}
					}
				}
			});

			// 得到当前选定的节点
			this.getSelectedNode = function() {
				return tree.getSelectionModel().getSelectedNode();
			};
			// render the tree
			
			tree.render();
			root.expand(false, /* no anim */false);
			//alert(root.attributes);
			
		}
	};
}();

function treeReload() {
	ywmimsTree.reload();

}

function setSelectedNode(id) {
	ywmimsTree.setSelectNode();
}

function setCurrentNode(id) {
	ywmimsTree.setCurrentNode(id);
}

function setCurrentParentNode(id) {
	ywmimsTree.setCurrentParentNode(id);
}