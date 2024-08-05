<script setup>
import {Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref, computed} from "vue";
import {genFileId} from "element-plus";
import axios from "axios";

const allData = ref([]);
const tableData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return allData.value.slice(start, end);
});

const insertPop = ref(false);
const upload = ref();
const currentPage = ref(1);
const pageSize = ref(15);
const total = computed(() => allData.value.length);

onMounted(async function () {
  await fetchAllData();
});

const fetchAllData = async () => {
  try {
    const response = await axios.get('http://localhost:8080/getAllFurn');
    allData.value = response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

const handlePageChange = (page) => {
  currentPage.value = page;
};

const addData = reactive({
  name: "",
  company: "",
  price: 0.0,
  stock: 1,
  upload: null
});

const handleExceed = function (files) {
  upload.value.clearFiles();
  const file = files[0];
  file.uid = genFileId();
  upload.value.handleStart(file);
};

const handleUploadChange = (file) => {
  addData.upload = file.raw;
};

const handleRemove = () => {
  addData.upload = null;
};

const submitForm = () => {
  submitForm0();
  console.log('Submitting form with data:', addData);
  insertPop.value = false;
  addData.name = "";
  addData.company = "";
  addData.price = 0.0;
  addData.stock = 1;
  addData.upload = null;
  upload.value.clearFiles();
};

const submitForm0 = async () => {
  try {
    const formData = {};

    for (const key in addData) {
      if (key === 'upload' && addData[key]) {
        const upload = new FormData();
        upload.append('upload', addData[key]);
        const imgPath = await axios.post('http://localhost:8080/uploadFurnImg', upload, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        formData['imgPath'] = imgPath.data;
        console.log(imgPath);
      } else {
        formData[key] = addData[key];
      }
    }

    const response = await axios.post('http://localhost:8080/addFurn', formData, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    console.log('Form submitted successfully:', response.data);
    insertPop.value = false;
    await fetchAllData(); // Refresh all data after adding new item
  } catch (error) {
    console.error('Error submitting form:', error);
  }
};

// 新增搜索功能
const searchQuery = ref('');
const filteredData = computed(() => {
  return allData.value.filter(item =>
      item.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      item.company.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const handleSearch = () => {
  currentPage.value = 1; // 重置到第一页
};
</script>

<template>
  <div style="margin: 5px;">
    <el-input
        v-model="searchQuery"
        style="width: 240px"
        size="default"
        placeholder="搜索家具名或厂商"
        :prefix-icon="Search"
    />
    <el-button type="primary" style="margin-left: 5px" @click="handleSearch">查询</el-button>
    <el-button type="primary" style="margin-left: 5px;" @click="insertPop=true">添加</el-button>
  </div>
  <el-table :data="tableData" stripe border>
    <el-table-column prop="id" label="ID" sortable/>
    <el-table-column prop="name" label="家具名"/>
    <el-table-column prop="company" label="厂商"/>
    <el-table-column prop="price" label="价格"/>
    <el-table-column prop="sales" label="销量"/>
    <el-table-column prop="stock" label="库存"/>
    <el-table-column label="" fixed="right" width="130px">
      <template #default="scope">
        <el-button text type="primary" size="small">修改</el-button>
        <el-button text type="danger" size="small">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div style="display: flex;justify-content: center;margin-top: 30px">
    <el-pagination
        @current-change="handlePageChange"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="prev, pager, next"
        background
    />
  </div>
  <el-dialog v-model="insertPop" title="添加家具" width="500" draggable>
    <el-form>
      <el-form-item label="家具名称">
        <el-input v-model="addData.name"></el-input>
      </el-form-item>
      <el-form-item label="厂商">
        <el-input v-model="addData.company"></el-input>
      </el-form-item>
      <el-form-item label="价格">
        <el-input-number v-model="addData.price" :min="0" :precision="2"/>
      </el-form-item>
      <el-form-item label="库存">
        <el-input-number v-model="addData.stock" :min="1" :max="100000"/>
      </el-form-item>
      <el-form-item label="家具图片">
        <el-upload
            ref="upload"
            accept="image/*"
            :limit="1"
            :on-exceed="handleExceed"
            :on-change="handleUploadChange"
            :on-remove="handleRemove"
            :auto-upload="false"
            list-type="picture"
        >
          <template #trigger>
            <el-button type="primary">上传图片</el-button>
          </template>
        </el-upload>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="insertPop = false">取消</el-button>
        <el-button type="primary" @click="submitForm">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
</style>
