<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="id" label="机构识别码">
    </el-table-column>
    <el-table-column prop="password" label="密码">
    </el-table-column>
    <el-table-column prop="location" label="地理位置">
    </el-table-column>
    <el-table-column prop="finance" label="财务">
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
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button size="mini" type="primary" @click="handleApproval(scope.$index, scope.row)">审批</el-button>
        <el-button size="mini" type="info" @click="handleSettle(scope.$index, scope.row)">结算</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    name: "colleges",
    data() {
      return {
        tableData: [],
        levelList: []
      }
    },
    mounted: function () {
      if (window.sessionStorage.getItem('userType') === 'staff') {
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showAllColleges')
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
      } else {
        this.$router.push({path: '/account/login'});
      }
    },
    methods: {
      filterLevel(value, row) {
        return row.level === value;
      },
      handleApproval(index, row) {
        if (row.level === 0) {
          this.$axios.get('/staff/approveCollege', {
            params: {
              collegeId: row.id
            }
          }).then((response) => {
            console.log(response);
            if (response.data !== 0) {
              row.level = 1;
            }
          }).catch((error) => {
            console.log(error);
            this.$message.error(error);
          });
        }
      },
      handleSettle(index, row) {
        this.$axios.get('/staff/settleAccount', {
          params: {
            collegeId: row.id
          }
        }).then((response) => {
          console.log(response);
          if (response.data !== 0) {
            row.finance = response.data;
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
