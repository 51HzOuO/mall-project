<script setup>
import {Search} from "@element-plus/icons-vue";
import {computed, onBeforeMount, reactive, ref, toRaw, watch} from "vue";
import {ElMessage, genFileId} from "element-plus";
import {addFurn, deleteFurn, getAllFurnByPage, updateFurn, uploadFurnImg} from "@/api/index.js";
import axios from "axios";

const tableData = ref([])


const total = ref(0);
const insertPop = ref(false);
const updateFurnPop = ref(false);
const editingFurn = ref(null);
const upload = ref();
const currentPage = ref(1);
const pageSize = 15;


const fetchAllData = async () => {
  const response = await getAllFurnByPage(currentPage.value, pageSize, searchQuery.value);
  tableData.value = response.data.list;
  total.value = response.data.total;
};

watch(currentPage, fetchAllData)

onBeforeMount(async function () {
  await fetchAllData();
})

const fits = [
  'fill',
  'contain',
  'cover',
  'none',
  'scale-down',
]

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
};

const handleUploadImg = (upload) => {
  const form = new FormData();
  form.append('upload', upload);
  return uploadFurnImg(form)
}

const submitForm0 = async () => {
  try {
    const formData = {};

    for (const key in addData) {
      if (key === 'upload' && addData[key]) {
        formData['imgPath'] = (await handleUploadImg(addData[key])).data
      } else {
        formData[key] = addData[key];
      }
    }

    const response = await addFurn(formData)
    console.log('Form submitted successfully:', response.data);
    ElMessage({
      message: 'success!',
      type: 'success',
    })
    insertPop.value = false;
    addData.name = "";
    addData.company = "";
    addData.price = 0.0;
    addData.stock = 1;
    addData.upload = null;
    upload.value.clearFiles();
    await fetchAllData(); // Refresh all data after adding new item
  } catch (error) {
    console.log('Error submitting form:', error.response.data);
    ElMessage({
      message: 'error!',
      type: 'error',
    })
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
  fetchAllData(searchQuery)
};

const handleDelete = async (id) => {
  try {
    const response = await deleteFurn(id)
    console.log('Delete successfully:', response.data);
    await fetchAllData(); // Refresh all data after deleting item
  } catch (error) {
    console.error('Error deleting item:', error);
  }
};
const handleEdit = (row) => {
  editingFurn.value = {...row};
  updateFurnPop.value = true;
};

const submitEditForm = async () => {
  try {
    if (addData['upload'])
      editingFurn.value.imgPath = (await handleUploadImg(addData['upload'])).data
    const response = await updateFurn(toRaw(editingFurn.value));
    console.log('Furniture updated successfully:', response.data);
    ElMessage({
      message: 'success',
      type: 'success',
    })
    updateFurnPop.value = false;
    await fetchAllData(); // Refresh all data after updating item
  } catch (error) {
    console.error('Error updating furniture:', error);
  }
};
</script>

<template>
  <div style="margin: 5px;">
    <el-input
        v-model="searchQuery"
        :prefix-icon="Search"
        placeholder="搜索家具名或厂商"
        size="default"
        style="width: 240px"
    />
    <el-button style="margin-left: 5px" type="primary" @click="handleSearch">查询</el-button>
    <el-button style="margin-left: 5px;" type="primary" @click="insertPop=true">添加</el-button>
  </div>
  <el-table :data="tableData" border stripe>
    <el-table-column label="ID" prop="id" sortable/>
    <el-table-column label="家具名" prop="name"/>
    <el-table-column label="厂商" prop="company"/>
    <el-table-column label="价格" prop="price"/>
    <el-table-column label="销量" prop="sales"/>
    <el-table-column label="库存" prop="stock"/>
    <el-table-column fixed="right" label="" width="130px">
      <template #default="scope">
        <el-button size="small" text type="primary" @click="handleEdit(scope.row)">修改</el-button>
        <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(scope.row.id)">
          <template #reference>
            <el-button size="small" text type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <div style="display: flex;justify-content: center;margin-top: 30px">
    <el-pagination
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        background
    />
  </div>
  <el-dialog v-model="insertPop" draggable title="添加家具" width="500">
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
        <el-input-number v-model="addData.stock" :max="100000" :min="1"/>
      </el-form-item>
      <el-form-item label="家具图片">
        <el-upload
            ref="upload"
            :auto-upload="false"
            :limit="1"
            :on-change="handleUploadChange"
            :on-exceed="handleExceed"
            :on-remove="handleRemove"
            accept="image/*"
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

  <el-dialog v-model="updateFurnPop" draggable title="修改家具" width="500">
    <el-form v-if="editingFurn">
      <el-form-item label="家具名称">
        <el-input v-model="editingFurn.name"></el-input>
      </el-form-item>
      <el-form-item label="厂商">
        <el-input v-model="editingFurn.company"></el-input>
      </el-form-item>
      <el-form-item label="价格">
        <el-input-number v-model="editingFurn.price" :min="0" :precision="2"/>
      </el-form-item>
      <el-form-item label="销量">
        <el-input-number v-model="editingFurn.sales" :min="0"/>
      </el-form-item>
      <el-form-item label="库存">
        <el-input-number v-model="editingFurn.stock" :max="100000" :min="1"/>
      </el-form-item>
      <el-form-item>
        <el-image
            :fit="fits"
            :src="axios.defaults.baseURL+'/getFurnImg?url='+editingFurn.imgPath"
            style="width: 800px">
        </el-image>
      </el-form-item>
      <el-upload
          ref="upload"
          :auto-upload="false"
          :limit="1"
          :on-change="handleUploadChange"
          :on-exceed="handleExceed"
          :on-remove="handleRemove"
          accept="image/*"
          list-type="picture"
      >
        <template #trigger>
          <el-button type="primary">上传图片</el-button>
        </template>
      </el-upload>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="updateFurnPop = false">取消</el-button>
        <el-button type="primary" @click="submitEditForm">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
</style>
