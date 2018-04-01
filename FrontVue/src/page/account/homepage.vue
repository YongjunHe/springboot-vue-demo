<template>
  <el-row>
    <el-col :span="6" :offset="9">
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <template v-if="form.type === 'college'">
          <el-form-item label="机构识别码" prop="collegeId">
            <el-input size="medium" :disabled=true v-model="form.collegeId"></el-input>
          </el-form-item>
          <el-form-item label="地理位置" prop="location">
            <el-input size="medium" v-model="form.location"></el-input>
          </el-form-item>
          <el-form-item label="财务" prop="finance">
            <el-input size="medium" :disabled=true v-model="form.finance"></el-input>
          </el-form-item>
          <el-form-item label="级别" prop="level">
            <el-input size="medium" :disabled=true v-model="form.level"></el-input>
          </el-form-item>
        </template>
        <template v-else-if="form.type === 'staff'">
          <el-form-item label="邮箱" prop="email">
            <el-input size="medium" :disabled=true v-model="form.email"></el-input>
          </el-form-item>
          <el-form-item label="用户名" prop="name">
            <el-input size="medium" v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="职位" prop="title">
            <el-input size="medium" v-model="form.title"></el-input>
          </el-form-item>
          <el-form-item label="机构识别码" prop="collegeId">
            <el-input size="medium" v-model="form.collegeId"></el-input>
          </el-form-item>
        </template>
        <template v-else>
          <el-form-item label="邮箱" prop="email">
            <el-input size="medium" :disabled=true v-model="form.email"></el-input>
          </el-form-item>
          <el-form-item label="用户名" prop="name">
            <el-input size="medium" v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="级别" prop="level">
            <el-input size="medium" :disabled=true v-model="form.level"></el-input>
          </el-form-item>
          <el-form-item label="余额" prop="deposit">
            <el-input size="medium" :disabled=true v-model="form.deposit"></el-input>
          </el-form-item>
          <el-form-item label="积分" prop="point">
            <el-input size="medium" :disabled=true v-model="form.point"></el-input>
          </el-form-item>
        </template>
        <el-form-item label="密码" prop="password">
          <el-input size="medium" type="password" v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input size="medium" type="password" v-model="form.checkPassword"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select :disabled=true v-model="form.type" placeholder="请选择类型">
            <el-option label="学生" value="student"></el-option>
            <el-option label="经理" value="staff"></el-option>
            <el-option label="机构" value="college"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit('form')">修改</el-button>
          <el-button @click="logout()">退出</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.form.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        form: {
          collegeId: '',
          location: '',
          finance: '',
          name: '',
          email: '',
          level: '',
          deposit: '',
          point: '',
          title: '',
          password: '',
          checkPassword: '',
          type: ''
        },
        rules: {
          collegeId: [
            {required: true, message: '请输入机构识别码', trigger: 'blur'},
            {min: 7, max: 7, message: '长度为 7 个字符', trigger: 'blur,change'}
          ],
          location: [
            {required: true, message: '请输入地理位置', trigger: 'blur'}
          ],
          finance: [
            {required: true, message: '请输入金额', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur.change'}
          ],
          email: [
            {required: true, message: '请输入邮箱地址', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change'}
          ],
          level: [
            {required: true, message: '请输入级别', trigger: 'blur'}
          ],
          point: [
            {required: true, message: '请输入积分', trigger: 'blur'}
          ],
          deposit: [
            {required: true, message: '请输入金额', trigger: 'blur'}
          ],
          title: [
            {required: true, message: '请输入职位', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 5, max: 10, message: '长度在 5 到 15 个字符', trigger: 'blur.change'}
          ],
          checkPassword: [
            {required: true, validator: validatePass, trigger: 'blur'}
          ],
          type: [
            {required: true, message: '请至少选择一个类型', trigger: 'change'}
          ]
        }
      };
    },
    beforeMount: function () {
      if (!window.sessionStorage.getItem('userType')) {
        this.$router.push({path: '/home/login'});
      } else {
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/profile', {
          params: {
            userId: window.sessionStorage.getItem('userId'),
          }
        }).then((response) => {
          if (window.sessionStorage.getItem('userType') === 'college') {
            this.form.collegeId = response.data.id;
            this.form.location = response.data.location;
            this.form.finance = response.data.finance;
            this.form.level = response.data.level;
            this.form.password = response.data.password;
            this.form.checkPassword = response.data.password;
            this.form.type = window.sessionStorage.getItem('userType');
          } else if (window.sessionStorage.getItem('userType') === 'staff') {
            this.form.email = response.data.email;
            this.form.name = response.data.name;
            this.form.title = response.data.title;
            this.form.collegeId = response.data.college;
            this.form.password = response.data.password;
            this.form.checkPassword = response.data.password;
            this.form.type = window.sessionStorage.getItem('userType');
          } else {
            this.form.email = response.data.email;
            this.form.name = response.data.name;
            this.form.level = response.data.level;
            this.form.deposit = response.data.deposit;
            this.form.point = response.data.point;
            this.form.password = response.data.password;
            this.form.checkPassword = response.data.password;
            this.form.type = window.sessionStorage.getItem('userType');
          }
        }).catch((error) => {
          console.log(error);
          this.$router.push({path: '/home/login'});
        });
      }
    },
    methods: {
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (!this.form.type) {
            this.$router.push({path: '/home/login'});
          } else {
            this.$axios.get('/' + this.form.type + '/modifyAccount', {
              params: {
                collegeId: this.form.collegeId,
                location: this.form.location,
                password: this.form.password,
                email: this.form.email,
                name: this.form.name,
                title: this.form.title,
              }
            }).then((response) => {
              console.log(response);
              if (response.data !== 0) {
                this.$message({
                  message: 'Modify successfully!',
                  type: 'success',
                });
              } else {
                this.$message({
                  message: 'Please try again',
                  type: 'warning',
                });
              }
            }).catch((error) => {
              console.log(error);
              this.$message.error(error);
            });
          }
        });
      },
      logout() {
        this.$axios.get('/' + this.form.type + '/logout')
          .then(function (response) {
            console.log(response);
            window.sessionStorage.removeItem('userId');
            window.sessionStorage.removeItem('userType');
          })
          .catch(function (error) {
            console.log(error);
          });
        this.$router.push({path: '/home/login'});
      }
    }
  }
</script>
