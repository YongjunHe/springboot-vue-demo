<template>
  <el-row>
    <el-col :span="6" :offset="9">
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <template v-if="form.type === 'college'">
          <el-form-item label="机构识别码" prop="collegeId">
            <el-input size="medium" v-model="form.collegeId"></el-input>
          </el-form-item>
        </template>
        <template v-else>
          <el-form-item label="邮箱" prop="email">
            <el-input size="medium" v-model="form.email"></el-input>
          </el-form-item>
        </template>
        <el-form-item label="密码" prop="password">
          <el-input size="medium" type="password" v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option label="学生" value="student"></el-option>
            <el-option label="经理" value="staff"></el-option>
            <el-option label="机构" value="college"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit('form')">登录</el-button>
          <el-button @click="reset('form')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    data() {
      return {
        form: {
          email: '',
          collegeId: '',
          password: '',
          type: '',
        },
        rules: {
          collegeId: [
            {required: true, message: '请输入机构识别码', trigger: 'blur'},
            {min: 7, max: 7, message: '长度为 7 个字符', trigger: 'blur,change'}
          ],
          email: [
            {required: true, message: '请输入邮箱地址', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 5, max: 10, message: '长度在 5 到 15 个字符', trigger: 'blur.change'}
          ],
          type: [
            {required: true, message: '请至少选择一个类型', trigger: 'change'}
          ]
        }
      }
    },
    mounted: function () {
      if (this.$route.query.type !== undefined)
        this.$message({
          message: this.$route.query.message,
          type: this.$route.query.type,
        });
    },
    methods: {
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let local_this = this;
            if (this.form.type !== 'college') {
              this.$axios.get('/' + this.form.type + '/login', {
                params: {
                  email: this.form.email,
                  password: this.form.password
                }
              }).then(function (response) {
                console.log(response);
                if (!response.data) {
                  local_this.$message({
                    message: 'Please try again',
                    type: 'warning',
                  });
                } else {
                  window.sessionStorage.setItem('userId', local_this.form.email);
                  window.sessionStorage.setItem('userType', local_this.form.type);
                  local_this.$router.push({path: '/account/homepage'});
                }
              }).catch(function (error) {
                console.log(error);
                local_this.$message.error(error);
              });
            } else {
              this.$axios.get('/' + this.form.type + '/login', {
                params: {
                  collegeId: this.form.collegeId,
                  password: this.form.password
                }
              }).then(function (response) {
                console.log(response);
                if (!response.data) {
                  local_this.$message({
                    message: 'Please try again',
                    type: 'warning',
                  });
                } else {
                  window.sessionStorage.setItem('userId', local_this.form.collegeId);
                  window.sessionStorage.setItem('userType', local_this.form.type);
                  local_this.$router.push({path: '/account/homepage'});
                }
              }).catch(function (error) {
                console.log(error);
                local_this.$message.error(error);
              });
            }
          } else {
            this.$message.error('error login!');
            return false;
          }
        });
      },
      reset(formName) {
        this.$refs[formName].resetFields();
      },
    }
  }
</script>

<style scoped>

</style>
