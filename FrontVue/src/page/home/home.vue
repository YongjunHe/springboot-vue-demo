<template>
  <div>
    <el-row>
      <el-button type="primary" plain><router-link to="/home/helloworld">HelloWorld</router-link></el-button>
      <el-button type="success" plain><router-link to="/home/login">Login</router-link></el-button>
      <el-button type="info" plain><router-link to="/home/register">Register</router-link></el-button>
      <el-button type="warning" plain><router-link to="/home/homepage">Homepage</router-link></el-button>
      <el-button type="danger" plain><router-link to="/home/orders">Orders</router-link></el-button>
    </el-row>
    <router-view/>
  </div>
</template>

<script>
  export default {
    name: "home",
    mounted: function(){
      if (this.$route.query.type!==undefined)
        this.$message({
          message: this.$route.query.message,
          type: this.$route.query.type,
        });
    }
  }
</script>

<style scoped>

</style>
