<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="课程名"
                v-model="listQuery.name"></el-input>
      <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
      <el-button class="filter-item" v-if="courseManager_btn_add" style="margin-left: 10px;" @click="handleCreate"
                 type="primary" icon="edit">添加
      </el-button>

    </div>
    <el-table :key='tableKey' :data="list" v-loading.body="listLoading" border fit highlight-current-row
              style="width: 100%">

      <el-table-column align="center" label="id" width="65" v-if="form.show">
          <template scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="课程名">
        <template scope="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>

<!--      <el-table-column label="课程图片" min-width="20%" >-->
<!--        &lt;!&ndash; 图片的显示 &ndash;&gt;-->
<!--        <template   slot-scope="scope">-->
<!--          <img :src="scope.row.image"  min-width="70" height="70" />-->
<!--        </template>-->
<!--      </el-table-column>-->

      <el-table-column width="200px" align="center" label="课程图片">
        <template scope="scope">
          <img :src="scope.row.image"  min-width="100" height="100" />
<!--          <span>{{scope.row.image}}</span>-->
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="价格（元）">
        <template scope="scope">
          <span>{{scope.row.price}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="简介">
        <template scope="scope">
          <span>{{scope.row.synopsis}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" v-if="form.show" label="详细介绍">
        <template scope="scope">
          <span>{{scope.row.description}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="分类">
        <template scope="scope">
          <span>{{scope.row.classification}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="教师">
        <template scope="scope">
          <span>{{scope.row.teacherName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="课程状态">
        <template scope="scope">
          <span>{{scope.row.status}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="开课时间">
        <template scope="scope">
          <span>{{scope.row.startTime}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="修改时间" v-if="form.show">
        <template scope="scope">
          <span>{{scope.row.updateTime}}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="center" label="操作" width="150">
        <template scope="scope">
          <el-button v-if="courseManager_btn_edit" size="small" type="success" @click="handleUpdate(scope.row)">编辑
          </el-button>
          <el-button v-if="courseManager_btn_del" size="small" type="danger" @click="handleDelete(scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div v-show="!listLoading" class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page.sync="listQuery.page" :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit"
                     layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
    </div>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px">
        <el-form-item label="课程名" prop="name">
          <el-input v-model="form.name" placeholder="请输入课程名"></el-input>
        </el-form-item>

        <el-form-item label="价格" prop="price">
          <el-input v-model.number="form.price" placeholder="请输入价格"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="synopsis">
          <el-input v-model="form.synopsis" placeholder="请输入简介"></el-input>
        </el-form-item>
        <el-form-item label="详细介绍" prop="description">
          <el-input v-model="form.description" placeholder="请输入详细介绍"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="classification">
          <el-input v-model="form.classification" placeholder="请输入分类"></el-input>
        </el-form-item>
<!--        <el-form-item label="教师ID" prop="teacherId">-->
<!--          <el-input v-model="form.teacherId" placeholder="请输入教师ID"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="课程状态" prop="status">-->
<!--          <el-input v-model="form.status" placeholder="请输入课程状态"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="开课时间" prop="startTime">
              <el-date-picker v-model="form.startTime"  @change="formatTime" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" placeholder="开课时间">
              </el-date-picker>
        </el-form-item>



        <el-form-item label="课程图片" ref="uploadElement" prop="imageUrl">
          <el-input v-model="form.imageUrl" v-if="false"></el-input>
          <el-upload
            class="avatar-uploader"
            ref="upload"
            :show-file-list="false"
            action="/course/cloud/classroom/course"
            :before-upload="beforeUpload"
            :on-change="handleChange"
            :auto-upload="false"
            :data="form">
            <img v-if="form.imageUrl" :src="form.imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

<!--        <el-form-item prop="file" class="upload-img-form" ref="uploadElement">-->

<!--        </el-form-item>-->
<!--        <el-form-item label="修改时间" prop="updateTime">-->
<!--          <el-input v-model="form.updateTime" placeholder="请输入修改时间"></el-input>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel('form')">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="create('form')">确 定</el-button>
        <el-button v-else type="primary" @click="update('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
  input[type="file"] {
    display: none;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>

<script>
  import {
    page,
    addObj,
    getObj,
    delObj,
    putObj
  } from 'api/cloud/classroom/course/index';
  import { mapGetters } from 'vuex';

  export default {
    name: 'course',
    data() {
      return {
        formData: new FormData(),
        form: {
          show: false,
          name: '',
          image: undefined,
          price: 0,
          synopsis: '',
          description: '',
          classification: '',
          teacherId: undefined,
          teacherName: undefined,
          status: undefined,
          startTime: '',
          imageUrl: undefined,
          updateTime: undefined
        },
        rules: {
          name: [
            {
              required: true,
              message: '请输入课程名',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 20,
              message: '长度在 1 到 20 个字符',
              trigger: 'blur'
            }
          ], image: [
            {
              message: '请输入课程图片',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 500,
              message: '长度在 1 到 500 个字符',
              trigger: 'blur'
            }
          ], price: [
            { required: true, message: '请输入价格' },
            { type: 'number', message: '价格必须为数字值' }
          ], synopsis: [
            {
              message: '请输入简介',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 20,
              message: '长度在 1 到 20 个字符',
              trigger: 'blur'
            }
          ], description: [
            {
              message: '请输入详细介绍',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 200,
              message: '长度在 3 到 200 个字符',
              trigger: 'blur'
            }
          ], classification: [
            {
              message: '请输入分类',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 20,
              message: '长度在 1 到 20 个字符',
              trigger: 'blur'
            }
          ], teacherId: [
            {
              message: '请输入教师ID',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 20,
              message: '长度在 1 到 20 个字符',
              trigger: 'blur'
            }
          ], status: [
            {
              message: '请输入课程状态',
              trigger: 'blur'
            },
            {
              min: 3,
              max: 20,
              message: '长度在 3 到 20 个字符',
              trigger: 'blur'
            }
          ], updateTime: [
            {
              message: '请输入修改时间',
              trigger: 'blur'
            },
            {
              min: 3,
              max: 20,
              message: '长度在 3 到 20 个字符',
              trigger: 'blur'
            }
          ]
        },
        list: null,
        total: null,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 20,
          name: undefined
        },
        dialogFormVisible: false,
        dialogStatus: '',
        courseManager_btn_edit: false,
        courseManager_btn_del: false,
        courseManager_btn_add: false,
        textMap: {
          update: '编辑',
          create: '创建'
        },
        tableKey: 0
      }
    },
    created() {
      this.getList();
      // debugger;
      this.courseManager_btn_edit = this.elements['courseManager:btn_edit'];
      this.courseManager_btn_del = this.elements['courseManager:btn_del'];
      this.courseManager_btn_add = this.elements['courseManager:btn_add'];
    },
    computed: {
      ...mapGetters([
        'elements'
      ])
    },
    methods: {
      getList() {
        this.listLoading = true;
        page(this.listQuery)
          .then(response => {
            this.list = response.Courses;
            this.total = response.count;
            this.listLoading = false;
            // this.show = false;
          })
      },
      handleFilter() {
        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.limit = val;
        this.getList();
      },
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getList();
      },
      handleCreate() {
        this.resetTemp();
        this.dialogStatus = 'create';
        this.dialogFormVisible = true;
      },
      handleUpdate(row) {
        this.resetTemp();
        console.log(row);
        getObj(row.id)
          .then(response => {
            this.dialogFormVisible = true;
            // this.form = response;
            this.form.id = response.id;
            this.form.name = response.name;
            this.form.price = response.price;
            this.form.synopsis = response.synopsis;
            this.form.description = response.description;
            this.form.classification = response.classification;
            this.form.startTime = response.startTime;
            this.form.imageUrl = response.image;
            this.form.image = response.image;
            this.dialogStatus = 'update';
            // debugger;
          });
      },
      handleDelete(row) {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            delObj(row.id)
              .then(() => {
                this.$notify({
                  title: '成功',
                  message: '删除成功',
                  type: 'success',
                  duration: 2000
                });
                const index = this.list.indexOf(row);
                this.list.splice(index, 1);
              });
          });
      },
      create(formName) {
        const set = this.$refs;
        this.formData = new FormData();
        const formData = this.formData;
        const requestData = this.form;
        set[formName].validate(valid => {
          if (valid) {
            if (!requestData.imageUrl) {
              this.$notify({
                title: '添加失败',
                message: '请选择课程图片',
                type: 'warning',
                duration: 2000
              });
              return false;
            }
            set.upload.submit();
            formData.append('name', requestData.name);
            formData.append('price', requestData.price);
            formData.append('synopsis', requestData.synopsis);
            formData.append('description', requestData.description);
            formData.append('classification', requestData.classification);
            formData.append('startTime', requestData.startTime);
            addObj(formData)
              .then(() => {
                this.dialogFormVisible = false;
                this.getList();
                this.$notify({
                  title: '成功',
                  message: '创建成功',
                  type: 'success',
                  duration: 2000
                });
              })
          } else {
            return false;
          }
        });
      },
      cancel(formName) {
        this.dialogFormVisible = false;
        const set = this.$refs;
        set[formName].resetFields();
      },
      update(formName) {
        const set = this.$refs;
        this.formData = new FormData();
        const formData = this.formData;
        const requestData = this.form;
        set[formName].validate(valid => {
          if (valid) {
            set.upload.submit();
            formData.append('name', requestData.name);
            formData.append('price', requestData.price);
            formData.append('synopsis', requestData.synopsis);
            formData.append('description', requestData.description);
            formData.append('classification', requestData.classification);
            formData.append('startTime', requestData.startTime);
            formData.append('image', requestData.image);
            this.dialogFormVisible = false;
            this.form.password = undefined;
            putObj(this.form.id, formData).then(() => {
              this.dialogFormVisible = false;
              this.getList();
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              });
            });
          } else {
            return false;
          }
        });
      },
      formatTime(time) {
        this.form.startTime = time;   // date是绑定的值
      },
      handleChange(file) {
        this.form.imageUrl = URL.createObjectURL(file.raw);
      },

      beforeUpload(file) {
        this.formData.append('file', file)
        // debugger;
        return false;
      },
      resetTemp() {
        this.formData = new FormData();
        this.form = {
          name: '',
          image: '',
          price: 0,
          synopsis: '',
          description: '',
          classification: '',
          teacherId: undefined,
          teacherName: undefined,
          status: undefined,
          startTime: '',
          dialogImageUrl: undefined,
          imageUrl: undefined,
          updateTime: undefined
        };
      }
    }
  }


</script>
