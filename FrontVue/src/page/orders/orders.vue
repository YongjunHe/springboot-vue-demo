<template>
  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="订单号">
      </el-table-column>
      <el-table-column prop="email" label="联系人邮箱">
      </el-table-column>
      <el-table-column prop="college" label="机构识别码">
      </el-table-column>
      <el-table-column prop="type" label="类型">
      </el-table-column>
      <el-table-column prop="status" label="状态"
                       :filters="[{text: '未支付', value: 0}, {text: '已支付', value: 1}, {text: '已结算', value: 1}]"
                       :filter-method="filterStatus">
        <template slot-scope="scope">
          <el-tag>{{scope.row.status}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="creattime" label="创建时间">
      </el-table-column>
      <el-table-column prop="paytime" label="支付时间">
      </el-table-column>
      <el-table-column prop="settletime" label="结算时间">
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope" v-if="userType === 'student'">
          <el-button size="mini" type="primary"
                     @click="handleBook">预订
          </el-button>
          <el-button size="mini" type="error"
                     @click="handleCancel(scope.$index, scope.row)">退订
          </el-button>
          <el-button size="mini" type="info"
                     @click="handlePay(scope.$index, scope.row)">支付
          </el-button>
          <el-popover placement="right" width="400" trigger="click">
            <el-table :data="scope.row.studentList">
              <el-table-column property="email" label="邮箱"></el-table-column>
              <el-table-column property="name" label="用户名"></el-table-column>
            </el-table>
            <el-button size="mini" type="primary" slot="reference">学员表</el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <el-row>
      <el-col :span="8" :offset="8">
        <el-form :model="form" :rules="rules" ref="form" label-width="80px" v-show="form.seen">
          <el-form-item label="联系人邮箱" prop="email">
            <el-input :disabled=true size="medium" v-model="form.email"></el-input>
          </el-form-item>
          <el-form-item label="课程号" prop="courseId">
            <el-input size="medium" v-model="form.courseId"></el-input>
          </el-form-item>
          <el-form-item label="机构识别码" prop="collegeId">
            <el-input size="medium" v-model="form.collegeId"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-select v-model="form.type" placeholder="请选择类型">
              <el-option label="数学" value="math"></el-option>
              <el-option label="外语" value="language"></el-option>
              <el-option label="编程" value="coding"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="费用" prop="amount">
            <el-input :disabled=true size="medium" v-model="form.amount"></el-input>
          </el-form-item>
          <el-form-item label="学员表">
            <el-row v-for="(row ,index) in form.studentList">
              <el-col span="16">
                <el-input v-model="row.email"></el-input>
              </el-col>
              <el-col span="8">
                <el-button type="danger" @click="removeStudent(row, index)">删除</el-button>
              </el-col>
            </el-row>
            <el-button type="primary" @click="addStudent">新增学员</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit('form')">提交</el-button>
            <el-button @click="reset('form')">取消</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "orders",
    data() {
      return {
        tableData: [],
        userType: window.sessionStorage.getItem('userType'),
        form: {
          seen: false,
          email: '',
          courseId: '',
          collegeId: '',
          type: '',
          amount: 0,
          studentList: []
        },
        rules: {
          email: [
            {required: true, message: '请输入邮箱地址', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change'}
          ],
          amount: [
            {required: true, message: '请输入费用', trigger: 'blur'}
          ]
        }
      }
    },
    mounted: function () {
      if (window.sessionStorage.getItem('userType') === 'staff') {
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showAllOrders')
          .then((response) => {
            console.log(response);
            if (!response.data) {
              this.$message({
                message: 'Failed to load the data!',
                type: 'warning',
              });
            } else {
              this.tableData = response.data;
            }
          })
          .catch((error) => {
            console.log(error);
            this.$message.error(error);
          });
      } else if (window.sessionStorage.getItem('userType') === 'college') {
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showOrders', {
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
          }
        }).catch((error) => {
          console.log(error);
          this.$message.error(error);
        });
      } else if (window.sessionStorage.getItem('userType') === 'student') {
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showOrders', {
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
      filterStatus(value, row) {
        return row.status === value;
      },
      handleBook() {
        this.form.seen = true;
        this.form.email = window.sessionStorage.getItem('userId');
      },
      handleCancel(index, row) {
        this.$axios.get('/student/unsubscribe', {
          params: {
            email: row.email,
            orderId: row.id
          }
        }).then((response) => {
          console.log(response);
          if (response.data !== 0) {
            this.tableData.splice(index, 1);
          } else {
            this.$message({
              message: 'Failed to unsubscribe!',
              type: 'warning',
            });
          }
        }).catch((error) => {
          console.log(error);
          this.$message.error(error);
        });
      },
      handlePay(index, row) {
        if (row.status === 0) {
          this.$axios.get('/student/pay', {
            params: {
              email: row.email,
              orderId: row.id
            }
          }).then((response) => {
            console.log(response);
            if (response.data !== 0) {
              row.status = 1;
            } else {
              this.$message({
                message: 'Failed to pay!',
                type: 'warning',
              });
            }
          }).catch((error) => {
            console.log(error);
            this.$message.error(error);
          });
        }
      },
      removeStudent(row, index) {
        this.form.studentList.splice(index, 1);
        this.form.amount = this.form.studentList.length * 1000;
      },
      addStudent() {
        this.form.studentList.push({
          email: ''
        });
        this.form.amount = this.form.studentList.length * 1000;
      },
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios.post('/student/subscribe', {
              id: this.form.courseId,
              email: this.form.email,
              college: this.form.collegeId,
              type: this.form.type,
              amount: this.form.amount,
              studentList: this.form.studentList
            }).then((response) => {
              console.log(response);
              if (!response.data) {
                this.$message({
                  message: 'Failed to subscribe!',
                  type: 'warning',
                });
              } else {
                this.tableData = response.data;
                this.form.seen = false;
              }
            }).catch((error) => {
              console.log(error);
              this.$message.error(error);
            });
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
