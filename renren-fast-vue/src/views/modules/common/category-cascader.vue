<template>
    <!--
    使用说明：
    1）、引入category-cascader.vue
    2）、语法：<category-cascader :catelogPath.sync="catelogPath"></category-cascader>
        解释：
          catelogPath：指定的值是cascader初始化需要显示的值，应该和父组件的catelogPath绑定;
          由于有sync修饰符，所以cascader路径变化以后自动会修改父的catelogPath，这是结合子组件this.$emit("update:catelogPath",v);做的
          -->
    <div>
        <el-cascader filterable
                     clearable
                     placeholder="试试搜索：手机"
                     v-model="paths"
                     :options="categories"
                     :props="setting"
        ></el-cascader>
    </div>
</template>

<script>

export default {
    components: {},
    props: {
        catelogPath: {
            type: Array,
            default() {
                return [];
            }
        }
    },
    data() {
        return {
            setting: {
                value: "catId",
                label: "name",
                children: "children"
            },
            categories: [],
            paths: this.catelogPath
        };
    },
    watch: {
        catelogPath(v) {
            this.paths = this.catelogPath;
        },
        paths(v) {
            this.$emit("update:catelogPath", v);
            // 还可以使用pubsub-js进行传值
            this.PubSub.publish("catPath", v);
        }
    },
    methods: {
        getCategories() {
            this.$http({
                url: this.$http.adornUrl("/product/category/list/tree"),
                method: "get"
            }).then(({data}) => {
                this.categories = data.data;
            });
        }
    },
    created() {
        this.getCategories();
    }
};
</script>
