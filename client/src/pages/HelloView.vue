<template>
  <div>
    {{ data }}
  </div>
</template>

<script>
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "HelloView",
  data() {
    return {
      data: {}
    }
  },
  created() {
    this.do();
  },
  methods: {
    do() {
      axios
          .get("http://localhost:8080/api/v1/hello")
          .then((response) => {
            console.log(response);
            this.data = response.data;
          }).catch((error) => {
        console.log(error);
      })
    }
  }
}
</script>

<style scoped>

</style>
