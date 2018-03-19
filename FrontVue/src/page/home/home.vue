<template>
  <div>
    <el-row>
      <el-button type="primary" plain><router-link to="/home/helloworld">HelloWorld</router-link></el-button>
      <el-button type="success" plain><router-link to="/home/login">Login</router-link></el-button>
      <el-button type="info" plain><router-link to="/home/register">Register</router-link></el-button>
    </el-row>
    <router-view/>
  </div>
</template>

<script>
  export default {
    name: "home"
  }
</script>

<style scoped>

</style>
