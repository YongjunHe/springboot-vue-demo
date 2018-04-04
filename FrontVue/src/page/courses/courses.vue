<template>
  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="课程号">
      </el-table-column>
      <el-table-column prop="college" label="机构识别码">
      </el-table-column>
      <el-table-column prop="type" label="类型"
                       :filters=this.typeList
                       :filter-method="filterType">
        <template slot-scope="scope">
          <el-tag>{{scope.row.type}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="temail" label="任课教师">
      </el-table-column>
      <el-table-column prop="size" label="班级人数">
      </el-table-column>
      <el-table-column prop="period" label="课时">
      </el-table-column>
      <el-table-column prop="price" label="价格">
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-popover placement="right" width="400" trigger="click">
            <el-table :data="scope.row.scheduleList">
              <el-table-column property="courseid" label="课程号"></el-table-column>
              <el-table-column property="starttime" label="上课时间"></el-table-column>
              <el-table-column property="endtime" label="下课时间"></el-table-column>
            </el-table>
            <el-button size="mini" type="primary" slot="reference">课程表</el-button>
          </el-popover>
          <el-popover placement="right" width="400" trigger="click">
            <el-table :data="scope.row.studentList">
              <el-table-column property="email" label="邮箱"></el-table-column>
              <el-table-column property="name" label="用户名"></el-table-column>
            </el-table>
            <el-button size="mini" type="primary" slot="reference">学员表</el-button>
          </el-popover>
          <el-button v-if="userType === 'college'" size="mini" type="warning"
                     @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button v-if="userType === 'college'" size="mini" type="warning"
                     @click="handleAdd(scope.$index, scope.row)">添加
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-form :model="form" :rules="rules" ref="form" label-width="80px" v-show="form.seen">
      <el-form-item label="课程号" prop="id">
        <el-input :disabled=true size="medium" v-model="form.id"></el-input>
      </el-form-item>
      <el-form-item label="机构识别码" prop="collegeId">
        <el-input :disabled=true size="medium" v-model="form.collegeId"></el-input>
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择类型">
          <el-option label="数学" value="math"></el-option>
          <el-option label="外语" value="language"></el-option>
          <el-option label="编程" value="coding"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任课教师" prop="temail">
        <el-input size="medium" v-model="form.temail"></el-input>
      </el-form-item>
      <el-form-item label="班级人数" prop="size">
        <el-input size="medium" v-model="form.size"></el-input>
      </el-form-item>
      <el-form-item label="课时" prop="period">
        <el-input size="medium" v-model="form.period"></el-input>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input size="medium" v-model="form.price"></el-input>
      </el-form-item>
      <el-form-item label="课程表">
        <el-row v-for="(row ,index) in form.scheduleList">
          <el-date-picker type="datetime" placeholder="上课时间" v-model="row.starttime"></el-date-picker>
          <span>-</span>
          <el-date-picker type="datetime" placeholder="下课时间" v-model="row.endtime"></el-date-picker>
          <el-button type="danger" @click="removeSchedule(row, index)">删除</el-button>
        </el-row>
        <el-button type="primary" @click="addSchedule">新增课时</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit('form')">提交</el-button>
        <el-button @click="reset('form')">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: "courses",
    data() {
      return {
        tableData: [],
        typeList: [],
        userType: window.sessionStorage.getItem('userType'),
        form: {
          seen: false,
          addOperation: false,
          id: '',
          collegeId: '',
          type: '',
          temail: '',
          size: '',
          period: '',
          price: '',
          scheduleList: []
        },
        rules: {
          type: [
            {required: true, message: '请至少选择一个类型', trigger: 'change'}
          ],
          temail: [
            {required: true, message: '请输入邮箱地址', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change'}
          ],
          size: [
            {required: true, message: '请输入班级人数', trigger: 'blur'}
          ],
          period: [
            {required: true, message: '请输入课时', trigger: 'blur'}
          ],
          price: [
            {required: true, message: '请输入价格', trigger: 'blur'}
          ]
        }
      }
    },
    mounted: function () {
      if (window.sessionStorage.getItem('userType') === 'staff') {
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showAllCourses')
          .then((response) => {
            console.log(response);
            if (!response.data) {
              this.$message({
                message: 'Failed to load the data!',
                type: 'warning',
              });
            } else {
              this.tableData = response.data;
              let tempList = [];
              for (let i = 0; i < response.data.length; i++) {
                let type = response.data[i].type;
                if (tempList.indexOf(type) === -1) {
                  tempList.push(type);
                  this.typeList.push({
                    text: type,
                    value: type
                  });
                }
              }
            }
          })
          .catch((error) => {
            console.log(error);
            this.$message.error(error);
          });
      } else if (window.sessionStorage.getItem('userType') === 'college') {
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showCourses', {
          params: {
            collegeId: window.sessionStorage.getItem('userId')
          }
        }).then((response) => {
          console.log(response);
          if (!response.data) {
            this.$message({
              message: 'Failed to load the data!',
              type: 'warning',
            });
          } else {
            this.tableData = response.data;
            let tempList = [];
            for (let i = 0; i < response.data.length; i++) {
              let type = response.data[i].type;
              if (tempList.indexOf(type) === -1) {
                tempList.push(type);
                this.typeList.push({
                  text: type,
                  value: type
                });
              }
            }
          }
        }).catch((error) => {
          console.log(error);
          this.$message.error(error);
        });
      } else if (window.sessionStorage.getItem('userType') === 'student') {
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showCourses', {
          params: {
            email: window.sessionStorage.getItem('userId')
          }
        }).then((response) => {
          console.log(response);
          if (!response.data) {
            this.$message({
              message: 'Failed to load the data!',
              type: 'warning',
            });
          } else {
            this.tableData = response.data;
            let tempList = [];
            for (let i = 0; i < response.data.length; i++) {
              let type = response.data[i].type;
              if (tempList.indexOf(type) === -1) {
                tempList.push(type);
                this.typeList.push({
                  text: type,
                  value: type
                });
              }
            }
          }
        }).catch((error) => {
          console.log(error);
          this.$message.error(error);
        });
      } else {
        this.$router.push({path: '/account/login'});
      }
    },
    methods: {
      filterType(value, row) {
        return row.type === value;
      },
      handleEdit(index, row) {
        this.form.seen = true;
        this.form.addOperation = false;
        this.form.id = row.id;
        this.form.collegeId = row.college;
        this.form.type = row.type;
        this.form.temail = row.temail;
        this.form.size = row.size;
        this.form.period = row.period;
        this.form.price = row.price;
        this.form.scheduleList = row.scheduleList;
      },
      handleAdd(index, row) {
        this.form.seen = true;
        this.form.addOperation = true;
        this.form.collegeId = row.college;
        this.form.type = row.type;
        this.form.temail = row.temail;
        this.form.size = row.size;
        this.form.period = row.period;
        this.form.price = row.price;
        this.form.scheduleList = row.scheduleList;
      },
      removeSchedule(row, index) {
        this.form.scheduleList.splice(index, 1);
      },
      addSchedule() {
        this.form.scheduleList.push({
          courseid: this.form.id,
          starttime: null,
          endtime: null
        });
      },
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.form.addOperation) {
              this.$axios.post('/college/setupCourse', {
                id: 0,
                college: this.form.collegeId,
                type: this.form.type,
                temail: this.form.temail,
                size: this.form.size,
                period: this.form.period,
                price: this.form.price,
                scheduleList: this.form.scheduleList
              }).then((response) => {
                console.log(response);
                if (response.data !== 0) {
                  this.tableData.push({
                    id: response.data,
                    college: this.form.collegeId,
                    type: this.form.type,
                    temail: this.form.temail,
                    size: this.form.size,
                    period: this.form.period,
                    price: this.form.price,
                    scheduleList: this.form.scheduleList,
                    studentList: []
                  });
                  this.form.seen = false;
                } else {
                  this.$message({
                    message: 'Failed to setup course!',
                    type: 'warning',
                  });
                }
              }).catch((error) => {
                console.log(error);
                this.$message.error(error);
              });
            } else {
              this.$axios.post('/college/modifyCourse', {
                id: this.form.id,
                college: this.form.collegeId,
                type: this.form.type,
                temail: this.form.temail,
                size: this.form.size,
                period: this.form.period,
                price: this.form.price,
                scheduleList: this.form.scheduleList
              }).then((response) => {
                console.log(response);
                if (response.data !== 0) {
                  for (let i = 0; i < this.tableData.length; i++) {
                    if (this.tableData[i].id === this.form.id) {
                      this.tableData[i].college = this.form.collegeId;
                      this.tableData[i].type = this.form.type;
                      this.tableData[i].temail = this.form.temail;
                      this.tableData[i].size = this.form.size;
                      this.tableData[i].period = this.form.period;
                      this.tableData[i].price = this.form.price;
                      this.tableData[i].scheduleList = this.form.scheduleList;
                    }
                  }
                  this.form.seen = false;
                } else {
                  this.$message({
                    message: 'Failed to modify course!',
                    type: 'warning',
                  });
                }
              }).catch((error) => {
                console.log(error);
                this.$message.error(error);
              });
            }
          } else {
            this.$message.error('error submit!');
            return false;
          }
        })
      },
      reset(formName) {
        this.$refs[formName].resetFields();
        this.form.seen = false;
      }
    }
  }
</script>

<style scoped>

</style>
