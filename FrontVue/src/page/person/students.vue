<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="email" label="邮箱">
    </el-table-column>
    <el-table-column prop="name" label="姓名">
    </el-table-column>
    <el-table-column prop="password" label="密码">
    </el-table-column>
    <el-table-column prop="level" label="级别"
                     :filters=this.levelList
                     :filter-method="filterLevel">
      <template slot-scope="scope">
        <el-tag :type="scope.row.level === 0 ? 'primary' : 'success'">
          {{scope.row.level}}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="deposit" label="余额">
    </el-table-column>
    <el-table-column prop="point" label="积分">
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope" v-if="userType === 'staff'">
        <el-button size="mini" type="primary" @click="handleUpgrade(scope.$index, scope.row)">升级</el-button>
        <el-button size="mini" type="danger" @click="handleDegrade(scope.$index, scope.row)">降级</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    name: "students",
    data() {
      return {
        tableData: [],
        levelList: [],
        userType: window.sessionStorage.getItem('userType')
      }
    },
    mounted: function () {
      if (window.sessionStorage.getItem('userType') === 'staff') {
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showAllStudents')
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
                let level = response.data[i].level;
                if (tempList.indexOf(level) === -1) {
                  tempList.push(level);
                  this.levelList.push({
                    text: level,
                    value: level
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
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showStudents', {
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
              let level = response.data[i].level;
              if (tempList.indexOf(level) === -1) {
                tempList.push(level);
                this.levelList.push({
                  text: level,
                  value: level
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
      filterLevel(value, row) {
        return row.level === value;
      },
      handleDegrade(index, row) {
        if (row.level !== 0) {
          this.$axios.get('/student/modifyMembership', {
            params: {
              email: row.email,
              level: row.level - 1
            }
          }).then((response) => {
            console.log(response);
            if (response.data !== 0) {
              row.level = row.level - 1;
            }
          }).catch((error) => {
            console.log(error);
            this.$message.error(error);
          });
        }
      },
      handleUpgrade(index, row) {
        this.$axios.get('/student/modifyMembership', {
          params: {
            email: row.email,
            level: row.level + 1
          }
        }).then((response) => {
          console.log(response);
          if (response.data !== 0) {
            row.level = row.level + 1;
          }
        }).catch((error) => {
          console.log(error);
          this.$message.error(error);
        });
      }
    }
  }
</script>

<style scoped>

</style>
