<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="email" label="邮箱">
    </el-table-column>
    <el-table-column prop="name" label="姓名" :formatter="formatter">
    </el-table-column>
    <el-table-column prop="password" label="密码">
    </el-table-column>
    <el-table-column prop="title" label="职位"
                     :filters=this.titleList
                     :filter-method="filterTitle">
      <template slot-scope="scope">
        <el-tag :type="scope.row.title === 'teacher' ? 'primary' : 'success'">
          {{scope.row.title}}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="college" label="机构识别码" sortable
                     :filters=this.collegeList
                     :filter-method="filterCollege">
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    name: "staffs",
    data() {
      return {
        tableData: [],
        titleList: [],
        collegeList: []
      }
    },
    mounted: function () {
      if (window.sessionStorage.getItem('userType') === 'staff') {
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showAllStaffs')
          .then((response) => {
            console.log(response);
            if (!response.data) {
              this.$message({
                message: 'Failed to load the data!',
                type: 'warning',
              });
            } else {
              this.tableData = response.data;
              let tempList1 = [];
              let tempList2 = [];
              for (let i = 0; i < response.data.length; i++) {
                let title = response.data[i].title;
                let college = response.data[i].college;
                if (tempList1.indexOf(title) === -1) {
                  tempList1.push(title);
                  this.titleList.push({
                    text: title,
                    value: title
                  });
                }
                if (tempList2.indexOf(college) === -1) {
                  tempList2.push(college);
                  this.collegeList.push({
                    text: college,
                    value: college
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
        this.$axios.get('/' + window.sessionStorage.getItem('userType') + '/showStaffs', {
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
            let tempList1 = [];
            let tempList2 = [];
            for (let i = 0; i < response.data.length; i++) {
              let title = response.data[i].title;
              let college = response.data[i].college;
              if (tempList1.indexOf(title) === -1) {
                tempList1.push(title);
                this.titleList.push({
                  text: title,
                  value: title
                });
              }
              if (tempList2.indexOf(college) === -1) {
                tempList2.push(college);
                this.collegeList.push({
                  text: college,
                  value: college
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
      formatter(row, column) {
        return row.name;
      },
      filterTitle(value, row) {
        return row.title === value;
      },
      filterCollege(value, row, column) {
        const property = column['property'];
        return row[property] === value;
      }
    }
  }
</script>

<style scoped>

</style>
