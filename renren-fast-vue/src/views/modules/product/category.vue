<template>
    <div>
        <div style="line-height: 35px; margin-bottom: 20px">
            <el-switch v-model="isDraggable"
                       style="margin-right: 10px"
                       active-text="开启拖拽"
                       inactive-text="关闭拖拽"
                       active-color="#13ce66"
                       inactive-color="#ff4949">
            </el-switch>

            <el-button v-if="isDraggable"
                       style="margin-right: 10px"
                       type="primary"
                       size="small"
                       round
                       @click="batchSave">
                批量保存
            </el-button>

            <el-button type="danger"
                       size="small"
                       round
                       @click="batchDelete">
                批量删除
            </el-button>
        </div>

        <el-tree empty-text="暂无数据..."
                 :highlight-current="true"
                 :data="categories"
                 :props="defaultProps"
                 :expand-on-click-node="false"
                 show-checkbox node-key="catId"
                 :default-expanded-keys="expandedKey"
                 :draggable="isDraggable"
                 :allow-drop="allowDrop"
                 @node-drop="handleDrop"
                 ref="categoryTree">
            <span class="custom-tree-node" slot-scope="{ node, data }">
                <span>{{ node.label }}</span>
                <span>
                    <!-- 当前节点为一、二级分类才显示 Append -->
                    <el-button v-if="node.level <= 2" type="text" size="mini"
                               @click="() => append(data)"> Append</el-button>
                    <!-- Edit -->
                    <el-button type="text" size="mini" @click="edit(data)"> Edit</el-button>
                    <!-- 当前节点没有子节点才显示 Delete -->
                    <el-button v-if="node.childNodes.length === 0" type="text" size="mini"
                               @click="() => remove(node, data)"> Delete</el-button>
                </span>
            </span>
        </el-tree>

        <el-dialog :title="dialogTitle"
                   :visible.sync="dialogFormVisible"
                   :center="true" width="30%"
                   :close-on-click-modal="false">
            <el-form :model="category">
                <el-form-item label="分类名称" prop="name">
                    <el-input v-model="category.name" autocomplete="off" @keyup.enter.native="addCategory"></el-input>
                </el-form-item>
                <el-form-item label="图标" prop="icon">
                    <el-input v-model="category.icon" autocomplete="off" @keyup.enter.native="addCategory"></el-input>
                </el-form-item>
                <el-form-item label="计量单位" prop="productUnit">
                    <el-input v-model="category.productUnit" autocomplete="off" @keyup.enter.native="addCategory"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitSaveOrUpdate">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "category",
    data() {
        return {
            parentId: [],
            isDraggable: false,
            updateNodes: [],
            maxLevel: 1,
            categories: [],
            expandedKey: [],
            dialogFormVisible: false,
            dialogTitle: "",
            submitType: "",
            category: {name: "", parentCid: 0, catLevel: 0, showStatus: 1, sort: 0, icon: "", productUnit: "", catId: null},
            defaultProps: {
                // 指定哪个属性作为子树节点对象展示（CategoryEntity 的 List<CategoryEntity> children 属性）
                children: 'children',
                // 指定哪个属性作为标签的值展示（CategoryEntity 的 name 属性）
                label: 'name'
            }
        };
    },
    created() {
        this.load();
    },
    methods: {
        // 树形展示分类
        load() {
            this.$http({
                url: this.$http.adornUrl("/product/category/list/tree"),
                method: "get"
            }).then(({data}) => {
                this.categories = data.data
            })
        },

        // 批量删除
        batchDelete() {
            // getCheckedNodes(leafOnly, includeHalfChecked)：若节点可被选择（即 show-checkbox 为 true），则返回目前被选中的节点所组成的数组。
            let checkedNodes = this.$refs.categoryTree.getCheckedNodes();
            let batchDeleteCatIds = [];
            for (let i = 0; i < checkedNodes.length; i++) {
                batchDeleteCatIds.push(checkedNodes[i].catId);
            }
            console.log(checkedNodes);
            console.log(checkedNodes[0].parentCid);
            console.log(batchDeleteCatIds);

            this.$confirm("此操作将永久删除, 是否继续?", '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'error'
            }).then(() => {
                this.$http({
                    url: this.$http.adornUrl("/product/category/delete"),
                    method: "post",
                    data: this.$http.adornData(batchDeleteCatIds, false)
                }).then(() => {
                    this.successMessage("删除成功");
                    this.load();
                    // 设置默认展开的分类
                    this.expandedKey = [checkedNodes[0].parentCid];
                }).catch(() => {
                    this.errorMessage("删除失败");
                })
            }).catch(() => {
                this.errorMessage("已取消删除");
            });
        },

        // 批量修改
        batchSave() {
            this.$http({
                url: this.$http.adornUrl("/product/category/update/sort"),
                method: "post",
                data: this.$http.adornData(this.updateNodes, false)
            }).then(() => {
                this.successMessage("分类顺序修改成功");
                this.load();
                this.expandedKey = this.parentId;
            }).catch(() => {
                this.errorMessage("分类顺序修改失败");
            });
            // 每次拖拽后把数据清空，否则要修改的节点将会越拖越多
            this.updateNodes = [];
        },

        /**
         * 拖拽成功完成时触发的事件
         * @param draggingNode  被拖拽的节点
         * @param dropNode  结束拖拽时最后进入的节点
         * @param dropType  被拖拽节点的放置位置（before、after、inner）
         * @param ev    event
         */
        handleDrop(draggingNode, dropNode, dropType, ev) {
            // 拖拽后的 ParentId 和 兄弟节点
            let parentId = 0;
            let siblings = null;
            if (dropType == "before" || dropType == "after") {
                parentId = dropNode.parent.data.catId == undefined ? 0: dropNode.parent.data.catId;
                siblings = dropNode.parent.childNodes;
            } else {
                parentId = dropNode.data.catId;
                siblings = dropNode.childNodes;
            }

            // 遍历所有的兄弟节点：如果是拖拽节点，传入 catId、sort、parentCid、catLevel；如果是兄弟节点传入 catId、sort。
            for (let i = 0; i < siblings.length; i++) {
                // 如果遍历的是当前正在拖拽的节点
                if (siblings[i].data.catId == draggingNode.data.catId){
                    let catLevel = draggingNode.level;
                    // 当前节点的层级发生变化
                    if (siblings[i].level != draggingNode.level){
                        catLevel = siblings[i].level;
                        // 修改子节点的层级
                        this.updateChildNodeLevel(siblings[i]);
                    }
                    this.updateNodes.push({catId: siblings[i].data.catId, sort: i, parentCid: parentId, catLevel: catLevel});
                }else{
                    this.updateNodes.push({catId: siblings[i].data.catId, sort: i});
                }
            }
            this.parentId.push(parentId);
            this.maxLevel = 1;
        },

        // 修改拖拽节点的子节点的层级
        updateChildNodeLevel(node){
            if (node.childNodes.length > 0){
                for (let i = 0; i < node.childNodes.length; i++){
                    // 遍历子节点，传入 catId、catLevel
                    let cNode = node.childNodes[i].data;
                    this.updateNodes.push({catId: cNode.catId, catLevel: node.childNodes[i].level});
                    // 处理子节点的子节点
                    this.updateChildNodeLevel(node.childNodes[i]);
                }
            }
        },

        /**
         * 拖拽时判断目标节点能否被放置
         * @param draggingNode  被拖拽的节点
         * @param dropNode  结束拖拽时最后进入的节点
         * @param type 被拖拽节点的放置位置三种情况（prev、inner、next）
         */
        allowDrop(draggingNode, dropNode, type) {
            this.maxLevel = draggingNode.level;
            this.countCurrentNodeDeep(draggingNode);
            let maxDeep = Math.abs(this.maxLevel - draggingNode.level) + 1;
            if (type == "inner") {
                return maxDeep + dropNode.level <= 3;
            } else {
                return maxDeep + dropNode.parent.level <= 3;
            }
        },

        // 求出当前节点的最大深度
        countCurrentNodeDeep(node) {
            if (node.childNodes != null && node.childNodes.length > 0) {
                for (let i = 0; i < node.childNodes.length; i++) {
                    if (node.childNodes[i].level > this.maxLevel) {
                        this.maxLevel = node.childNodes[i].level;
                    }
                    this.countCurrentNodeDeep(node.childNodes[i]);
                }
            }
        },

        // 弹出对话框（添加）
        append(data) {
            this.submitType = "add";
            this.dialogTitle = "添加分类";
            this.dialogFormVisible = true;
            // 当前要添加的分类的 父分类ID
            this.category.parentCid = data.catId;
            // 当前要添加的分类的 level
            this.category.catLevel = data.catLevel * 1 + 1;
            this.category.catId = null;
            this.category.name = "";
            this.category.icon = "";
            this.category.productUnit = "";
            this.category.sort = 0;
            this.category.showStatus = 1;
        },

        // 添加分类
        addCategory() {
            this.$http({
                url: this.$http.adornUrl("/product/category/save"),
                method: "post",
                data: this.$http.adornData(this.category, false)
            }).then(() => {
                this.successMessage("添加成功");
                this.dialogFormVisible = false;
                this.load();
                // 设置默认展开的分类
                this.expandedKey = [this.category.parentCid]
            }).catch(() => {
                this.errorMessage("添加失败");
                this.dialogFormVisible = false;
            })
        },

        // 弹出对话框（修改）
        edit(data) {
            this.submitType = "edit";
            this.dialogTitle = "修改分类";
            this.dialogFormVisible = true;

            // 发送请求获取节点的最新数据
            this.$http({
                url: this.$http.adornUrl(`/product/category/info/${data.catId}`),
                method: "get"
            }).then(({data}) => {
                this.category.name = data.category.name;
                this.category.catId = data.category.catId;
                this.category.icon = data.category.icon;
                this.category.productUnit = data.category.productUnit;
                this.category.parentCid = data.category.parentCid;
            })
        },

        // 修改分类
        editCategory() {
            // this.category 中不包含所有字段的值，例如 show-status 等。
            // 因此不能直接将其作为参数发起更新请求，会导致其他字段被默认值覆盖；只发送需要更新的字段。
            let {catId, name, icon, productUnit} = this.category;
            this.$http({
                url: this.$http.adornUrl("/product/category/update"),
                method: "post",
                data: this.$http.adornData({catId, name, icon, productUnit}, false)
            }).then(() => {
                this.successMessage("修改成功");
                this.dialogFormVisible = false;
                this.load();
                // 设置默认展开的分类
                this.expandedKey = [this.category.parentCid];
            }).catch(() => {
                this.errorMessage("修改失败")
                this.dialogFormVisible = false;
            })
        },

        // 添加 or 修改
        submitSaveOrUpdate() {
            if (this.submitType === "add") {
                this.addCategory();
            }
            if (this.submitType === "edit") {
                this.editCategory();
            }
        },

        // 删除分类
        remove(node, data) {
            let ids = [data.catId];

            // Confirm 是否删除
            this.$confirm(`此操作将永久删除【${data.name}】分类, 是否继续?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'error'
            }).then(() => {
                this.$http({
                    url: this.$http.adornUrl("/product/category/delete"),
                    method: "post",
                    data: this.$http.adornData(ids, false)
                }).then(() => {
                    this.successMessage("删除成功");
                    this.load();
                    // 设置默认展开的分类
                    this.expandedKey = [node.parent.data.catId]
                })
            }).catch(() => {
                this.errorMessage("已取消删除");
            });
        },

        // 成功消息提示
        successMessage(msg) {
            this.$message({
                type: 'success',
                dangerouslyUseHTMLString: true,     // 使用 HTML 片段
                message: `<strong><i>${msg}！</i></strong>`,
                center: true,       // 文字居中
                showClose: true     // 可关闭
            })
        },

        // 失败消息提示
        errorMessage(msg) {
            this.$message({
                type: 'info',
                dangerouslyUseHTMLString: true,
                message: `<strong><i>${msg}！</i></strong>`,
                center: true,
                showClose: true
            })
        }
    }
}
</script>
