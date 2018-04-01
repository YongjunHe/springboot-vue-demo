<template>
  <el-row>
    <el-col :span="6" :offset="9">
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <template v-if="form.type === 'college'">
          <el-form-item label="地理位置" prop="location">
            <el-input size="medium" v-model="form.location"></el-input>
          </el-form-item>
        </template>
        <template v-else>
          <el-form-item label="用户名" prop="name">
            <el-input size="medium" v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input size="medium" v-model="form.email"></el-input>
          </el-form-item>
        </template>
        <template v-if="form.type === 'staff'">
          <el-form-item label="机构识别码" prop="collegeId">
            <el-input size="medium" v-model="form.collegeId"></el-input>
          </el-form-item>
          <el-form-item label="职位" prop="title">
            <el-input size="medium" v-model="form.title"></el-input>
          </el-form-item>
        </template>
        <el-form-item label="密码" prop="password">
          <el-input size="medium" type="password" v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input size="medium" type="password" v-model="form.checkPassword"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option label="学生" value="student"></el-option>
            <el-option label="经理" value="staff"></el-option>
            <el-option label="机构" value="college"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit('form')">注册</el-button>
          <el-button @click="reset('form')">重置</el-button>
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
          name: '',
          email: '',
          location: '',
          collegeId: '',
          title: '',
          password: '',
          checkPassword: '',
          type: ''
        },
        rules: {
          name: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur.change'}
          ],
          email: [
            {required: true, message: '请输入邮箱地址', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change'}
          ],
          location: [
            {required: true, message: '请输入地理位置', trigger: 'blur'}
          ],
          collegeId: [
            {required: true, message: '请输入机构识别码', trigger: 'blur'},
            {min: 7, max: 7, message: '长度为 7 个字符', trigger: 'blur,change'}
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
    methods: {
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let local_this = this;
            if (this.form.type === 'student') {
              this.$axios.get('/' + this.form.type + '/register', {
                params: {
                  name: this.form.name,
                  email: this.form.email,
                  password: this.form.password
                }
              }).then(function (response) {
                console.log(response);
                if (response.data !== 0) {
                  local_this.$message({
                    message: 'Register successfully! Please refer to the activation link in your email-box',
                    type: 'success',
                  });
                  local_this.$router.push({path: '/home/login'});
                } else {
                  local_this.$message({
                    message: 'Please try again',
                    type: 'warning',
                  });
                }
              }).catch(function (error) {
                console.log(error);
                local_this.$message.error(error);
              });
            } else if (this.form.type === 'staff') {
              this.$axios.get('/' + this.form.type + '/register', {
                params: {
                  name: this.form.name,
                  email: this.form.email,
                  password: this.form.password,
                  title: this.form.title,
                  collegeId: this.form.collegeId
                }
              }).then(function (response) {
                console.log(response);
                if (response.data !== 0) {
                  local_this.$message({
                    message: 'Register Successfully!',
                    type: 'success',
                  });
                  local_this.$router.push({path: '/home/login'});
                } else {
                  local_this.$message({
                    message: 'Please try again!',
                    type: 'warning',
                  });
                }
              }).catch(function (error) {
                console.log(error);
                local_this.$message.error(error);
              });
            } else {
              this.$axios.get('/' + this.form.type + '/register', {
                params: {
                  location: this.form.location,
                  password: this.form.password
                }
              }).then(function (response) {
                console.log(response);
                if (response.data !== 0) {
                  local_this.$message({
                    message: 'Register Successfully! Your collegeId is ' + response.data,
                    type: 'success',
                  });
                  local_this.$router.push({path: '/home/login'});
                } else {
                  local_this.$message({
                    message: 'Please try again!',
                    type: 'warning',
                  });
                }
              }).catch(function (error) {
                console.log(error);
                local_this.$message.error(error);
              });
            }
          } else {
            this.$message.error('error register!');
            return false;
          }
        });
      },
      reset(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>
