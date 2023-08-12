<template>
  <the-header></the-header>
  <the-navigator></the-navigator>
  <router-view></router-view>
</template>

<script>
import TheHeader from "@/components/layout/TheHeader";
import TheNavigator from "@/components/layout/TheNavigator";

export default {
  name: 'App',
  components: {
    TheNavigator,
    TheHeader,
  },
  created() {
    this.$store.dispatch('tryLogin');
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
