<template>
  <div>
    <el-row>
      <ul>
        <h3>Organized List</h3>
        <li v-for="(item, index) in list" :key="item">
          <ul style="margin-top: 50px; margin-left: -3%">
            <h4>Track {{ index + 1 }}</h4>
            <li v-for="i in item" :key="i">
              {{ i }}
            </li>
          </ul>
        </li>
      </ul>
    </el-row>
    <el-row style="margin-top: 30px">
      <el-button type="danger" @click="back">Organize List</el-button>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  computed: {
    list() {
      return this.$route.params.list;
    },
  },
  methods: {
    back() {
      this.$router.push({
        name: "formPage",
      });
    },
  },
};
</script>

<style scoped>
ul {
  list-style-type: none;
}
</style>