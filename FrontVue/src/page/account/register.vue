<template>
  <el-row>
    <el-col :span="6" :offset="9">
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input type="password" v-model="form.checkPassword"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option label="学生" value="student"></el-option>
            <el-option label="经理" value="manager"></el-option>
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
          password: '',
          checkPassword: '',
          type: ''
        },
        rules: {
          name: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
          ],
          email: [
            {required: true, message: '请输入邮箱地址', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 5, max: 10, message: '长度在 5 到 15 个字符', trigger: 'blur'}
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
            alert('register!');
          } else {
            console.log('error register!!');
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
