<template>
  <div>
    <el-container>
      <el-col :span="6" style="margin: auto">
        <el-row>
          <el-form :model="form" :rules="rules" ref="formName">
            <el-form-item label="Activity Name" prop="subject">
              <el-input v-model="form.subject"></el-input>
            </el-form-item>
            <el-form-item label="Time Duration" prop="timeDuration">
              <el-input type="number" v-model="form.timeDuration"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submitForm('formName', form)"
                >Add List</el-button
              >
              <el-button type="warning" @click="resetForm('formName')"
                >Reset Form</el-button
              >
            </el-form-item>
          </el-form>
        </el-row>
        <el-row>
          <el-table
            v-show="conferencePlanDtoList.length > 0"
            :data="conferencePlanDtoList"
            style="width: 100%"
          >
            <el-table-column prop="subject" label="Subject"> </el-table-column>
            <el-table-column prop="timeDuration" label="Time Duration">
            </el-table-column>
          </el-table>
        </el-row>
        <el-row style="margin-top: 20px">
          <el-button type="success" @click="push">Organize List</el-button>
        </el-row>
      </el-col>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      form: {
        subject: null,
        timeDuration: null,
      },
      conferencePlanDtoList: [],
      rules: {
        subject: [
          {
            required: true,
            message: "Please input Activity Name",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    submitForm(formName, form) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let flag = true;
          this.conferencePlanDtoList.forEach((e) => {
            if (e.subject == form.subject) {
              alert("This " + e.subject + " already saved!!");
              this.resetForm(formName);
              flag = false;
              return;
            }
          });
          if (flag) {
            this.conferencePlanDtoList.push(JSON.parse(JSON.stringify(form)));
          }
          this.resetForm(formName);
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    push() {
      var thizz = this;
      if (this.conferencePlanDtoList.length > 0)
        axios
          .post("/plan", { conferencePlanDtoList: thizz.conferencePlanDtoList })
          .then(function (response) {
            alert("Your presantation list has been organized!");
            thizz.$router.push({
              name: "organizedList",
              params: { list: response.data },
            });
            console.log(response);
          })
          .catch(function (error) {
            alert(error);
            console.log(error);
          });
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
