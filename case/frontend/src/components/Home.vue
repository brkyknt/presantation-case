<template>
  <el-row>
    <h1>{{ msg }}</h1>
  </el-row>
</template>

<script>
export default {
  name: "presentation-organizer",
  props: {
    msg: String,
  },
};
</script>
