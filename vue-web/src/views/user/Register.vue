<template>
  <head>
    
  </head>
  <el-form
      ref="ruleFormRef"
      :model="ruleForm"
      :rules="rules"
      label-width="140px"
      class="demo-ruleForm"
      :size="formSize"
      status-icon
  >
    <el-form-item label="Username" prop="username">
      <el-input v-model="ruleForm.username"/>
    </el-form-item>
    <el-form-item label="Email" prop="email">
      <el-input v-model="ruleForm.email"/>
    </el-form-item>
    <el-form-item label="Password" prop="password">
      <el-input v-model="ruleForm.password" type="password" show-password/>
    </el-form-item>
    <el-form-item label="Confirm Password" prop="confirmPassword">
      <el-input v-model="ruleForm.confirmPassword" type="password" show-password/>
    </el-form-item>
    <el-form-item label="Full Name" prop="fullName">
      <el-input v-model="ruleForm.fullName"/>
    </el-form-item>
    <el-form-item label="Date of Birth" prop="dateOfBirth">
      <el-date-picker
          v-model="ruleForm.dateOfBirth"
          type="date"
          placeholder="Select date"
      />
    </el-form-item>
    <el-form-item label="Gender" prop="gender">
      <el-radio-group v-model="ruleForm.gender">
        <el-radio label="male">Male</el-radio>
        <el-radio label="female">Female</el-radio>
        <el-radio label="other">Other</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="Country" prop="country">
      <el-select v-model="ruleForm.country" placeholder="Select country">
        <el-option label="USA" value="usa"/>
        <el-option label="China" value="china"/>
        <el-option label="Japan" value="japan"/>
        <el-option label="Germany" value="germany"/>
        <el-option label="Other" value="other"/>
      </el-select>
    </el-form-item>
    <el-form-item label="Agreement" prop="agreement">
      <el-checkbox v-model="ruleForm.agreement">
        I agree to the terms and conditions
      </el-checkbox>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">Register</el-button>
      <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import {reactive, ref} from 'vue'
import {ElMessage} from 'element-plus'

const formSize = ref('default')
const ruleFormRef = ref()
const ruleForm = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  fullName: '',
  dateOfBirth: '',
  gender: '',
  country: '',
  agreement: false
})

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please input the password'))
  } else {
    if (ruleForm.confirmPassword !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('confirmPassword', () => null)
    }
    callback()
  }
}
const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please input the password again'))
  } else if (value !== ruleForm.password) {
    callback(new Error("Two inputs don't match!"))
  } else {
    callback()
  }
}

const rules = reactive({
  username: [
    {required: true, message: 'Please input username', trigger: 'blur'},
    {min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur'},
  ],
  email: [
    {required: true, message: 'Please input email address', trigger: 'blur'},
    {type: 'email', message: 'Please input correct email address', trigger: ['blur', 'change']},
  ],
  password: [
    {required: true, validator: validatePass, trigger: 'blur'},
    {min: 6, message: 'Password must be at least 6 characters', trigger: 'blur'},
  ],
  confirmPassword: [
    {required: true, validator: validatePass2, trigger: 'blur'},
  ],
  fullName: [
    {required: true, message: 'Please input full name', trigger: 'blur'},
  ],
  dateOfBirth: [
    {type: 'date', required: true, message: 'Please pick a date', trigger: 'change'},
  ],
  gender: [
    {required: true, message: 'Please select gender', trigger: 'change'},
  ],
  country: [
    {required: true, message: 'Please select country', trigger: 'change'},
  ],
  agreement: [
    {
      validator: (rule, value, callback) => {
        if (value === false) {
          callback(new Error('Please agree to the terms and conditions'))
        } else {
          callback()
        }
      },
      trigger: 'change',
    },
  ],
})

const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
      ElMessage.success('Registration successful!')
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
