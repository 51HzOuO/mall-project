<script setup>
import {ArrowDown} from '@element-plus/icons-vue'

const props = defineProps({
  title: {
    type: String,
    default: "这是一个标题"
  }
});

</script>
<template>
  <div class="content">
    <div class="child1">{{ props.title }}</div>
    <div class="child2"></div>
    <div class="child3">
      <el-dropdown>
    <span class="el-dropdown-link">
      Admin
      <el-icon class="el-icon--right">
        <arrow-down/>
      </el-icon>
    </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="">个人信息</el-dropdown-item>
            <el-dropdown-item>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<style scoped>
.content {
  height: 50px;
  line-height: 50px;
  border-bottom: 1px solid #ccc;
  display: flex;
}

.child1 {
  display: flex;
  align-items: center;
  width: 200px;
  padding-left: 30px;
  font-weight: bold;
  color: dodgerblue;
}

.child2 {
  flex: 1;
}

.child3 {
  padding-right: 30px;
  display: flex;
  align-items: center;
}

.child3 .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}

.el-dropdown-link:focus {
  outline: none;
}
</style>

