<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="orderid" label="订单号">
    </el-table-column>
    <el-table-column prop="courseid" label="课程号"
                     :filters=this.courseList
                     :filter-method="filterCourse">
      <template slot-scope="scope">
        <el-tag>{{scope.row.courseid}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="semail" label="学生邮箱">
    </el-table-column>
    <el-table-column prop="score" label="得分" v-if="userType === 'student'">
    </el-table-column>
    <el-table-column label="得分" v-if="userType === 'staff'">
      <template slot-scope="scope">
        <el-input v-model="scope.row.score"></el-input>
      </template>
    </el-table-column>
    <el-table-column label="操作" v-if="userType === 'staff'">
      <template slot-scope="scope">
        <el-button size="mini" type="primary" @click="mark">判分</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    name: "scores",
    data() {
      return {
        tableData: [],
        courseList: [],
        userType: window.sessionStorage.getItem('userType'),
      }
    },
    mounted: function () {
      if (window.sessionStorage.getItem('userType') !== 'college') {
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showScores', {
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
              let courseid = response.data[i].courseid;
              if (tempList.indexOf(courseid) === -1) {
                tempList.push(courseid);
                this.courseList.push({
                  text: courseid,
                  value: courseid
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
      filterCourse(value, row) {
        return row.courseid === value;
      },
      mark() {
        this.$axios.post('/staff/releaseScores', this.tableData)
          .then((response) => {
            console.log(response);
            if (response.data !== 0) {
              this.$message({
                message: 'Mark successfully!',
                type: 'success',
              });
            } else {
              this.$message({
                message: 'Failed to mark!',
                type: 'warning',
              });
            }
          })
          .catch((error) => {
            console.log(error);
            this.$message.error(error);
          });
      }
    }
  }
</script>

<style scoped>

</style>
