<template>
    <!-- action：上传的地址；
         data：上传时附带的额外参数；
         list-type:文件列表的类型；
         show-file-list：是否显示已上传文件列表；
         accept：接受上传的文件类型；
         file-list：上传的文件列表。 -->
    <div>
        <el-upload
            :action="dataObj.host"
            :data="dataObj"
            list-type="picture"
            :multiple="false"
            :show-file-list="showFileList"
            :file-list="fileList"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-success="handleUploadSuccess"
            :before-upload="beforeUpload">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传 JPG/PNG 文件，且不超过 10MB</div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="fileList[0].url" alt="">
        </el-dialog>
    </div>
</template>
<script>
import {policy} from './policy'
import {getUUID} from '@/utils'

export default {
    name: 'singleUpload',
    props: {
        value: String
    },
    computed: {
        imageUrl() {
            return this.value;
        },
        imageName() {
            if (this.value != null && this.value !== '') {
                return this.value.substr(this.value.lastIndexOf("/") + 1);
            } else {
                return null;
            }
        },
        fileList() {
            return [{
                name: this.imageName,
                url: this.imageUrl
            }]
        },
        showFileList: {
            get: function () {
                return this.value !== null && this.value !== '' && this.value !== undefined;
            },
            set: function (newValue) {
            }
        }
    },
    data() {
        return {
            dataObj: {
                policy: '',
                signature: '',
                OSSAccessKeyId: '',
                key: '',
                dir: '',
                host: '',
            },
            dialogVisible: false
        };
    },
    methods: {
        emitInput(val) {
            this.$emit('input', val)
        },

        // 点击文件列表中已上传的文件时的钩子
        handlePreview(file) {
            this.dialogVisible = true;
        },

        // 文件列表移除文件时的钩子
        handleRemove(file, fileList) {
            this.emitInput('');
        },

        // 文件上传成功时的钩子
        handleUploadSuccess(res, file) {
            console.log("上传成功...")
            this.showFileList = true;
            this.fileList.pop();
            this.fileList.push({
                name: file.name,
                url: this.dataObj.host + '/' + this.dataObj.key.replace("${filename}", file.name)
            });
            this.emitInput(this.fileList[0].url);
        },

        // 上传文件之前的钩子，参数为上传的文件，若返回 false 或者返回 Promise 且被 reject，则停止上传。
        beforeUpload(file) {
            let _self = this;
            return new Promise((resolve, reject) => {
                policy().then(response => {
                    _self.dataObj.policy = response.data.policy;
                    _self.dataObj.signature = response.data.signature;
                    _self.dataObj.OSSAccessKeyId = response.data.accessid;
                    _self.dataObj.key = response.data.dir + getUUID() + '_${filename}';
                    _self.dataObj.dir = response.data.dir;
                    _self.dataObj.host = response.data.host;
                    resolve(true)
                }).catch(err => {
                    reject(false)
                })
            })
        }
    }
}
</script>
