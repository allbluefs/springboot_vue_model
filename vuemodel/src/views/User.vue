<template>
  <div style="padding: 20px">
    <!--搜索-->
    <div style="float: left">
      <el-form :inline="true" :model="params" class="demo-form-inline">
        <el-form-item>
          <el-input v-model.trim="params.username" @keyup.enter="query" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="query">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="add">新增</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="update">修改</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="del">删除</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!--表格数据-->
    <el-table
        ref="multipleTable"
        :data="page.list"
        tooltip-effect="dark"
        style="width: 100%">
      <el-table-column
          type="selection">
      </el-table-column>
      <el-table-column
          prop="userId"
          label="用户id">
      </el-table-column>
      <el-table-column
          prop="username"
          label="用户名">
      </el-table-column>
      <el-table-column
          prop="chineseName"
          label="姓名">
      </el-table-column>
      <el-table-column
          prop="roleName"
          label="userType">
        <template #default="scope">
          <el-tag type="success" size="small" v-if="scope.row.userType===1">普通用户</el-tag>
          <el-tag type="success" size="small" v-if="scope.row.userType===2">员工</el-tag>
          <el-tag type="success" size="small" v-if="scope.row.userType===3">管理员</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="phoneNumber"
          label="手机号">
      </el-table-column>
      <!--            <el-table-column-->
      <!--                    prop=""-->
      <!--                    label="操作">-->
      <!--                <template slot-scope="scope">-->
      <!--                    <el-link type="primary" :underline="false" @click="aaa(scope.row.userId)">操作</el-link>-->
      <!--                </template>-->
      <!--            </el-table-column>-->
    </el-table>

    <div style="margin: 20px 30%">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="page.currPage"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="page.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="page.totalCount"
          style="text-align: center">
      </el-pagination>
    </div>

  </div>


</template>

<script>
import request from "@/utils/request";

export default {
  name: "User",
  data() {
    return {
      params: {
        username: '',
        page: '1',
        limit: '10'
      },
      page: {},
    }
  },
  methods: {
    add() {
    },
    del() {
    },
    update() {
    },
    query() {
    },
    loadPage(flag) {
      if (flag) {
        let params = {}
        params.page = '1'
        this.listUser(params)
      } else {
        this.listUser(this.params)
      }
    },
    listUser(params) {
      request.post('/sys/user/list', params).then(res => {
        if (res.code === 200) {

          this.page = res.page
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  },
  created() {
    this.loadPage(true)
  }
}
</script>

<style scoped>

</style>