<script setup>

import Header from "@/components/Header.vue";
import Aside from "@/components/Aside.vue";
import FormData from "@/views/user/manager/FormData.vue";

const title = "后台管理"

</script>

<template>
  <Header :title="title"/>
  <div class="content">
    <div>
      <Aside/>
    </div>
    <div style="flex: 1;">
      <FormData/>
    </div>
  </div>
</template>

<style scoped>
.content {
  display: flex;
}

</style>
