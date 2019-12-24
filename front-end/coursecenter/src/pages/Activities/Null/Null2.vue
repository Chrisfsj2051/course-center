<template>
    
</template>

<script>
  export default {
    beforeRouteEnter(to, from, next) {
      next(vm => {
        vm.$router.replace({
          path: '/center/activities/paperlist',
          query: {
            paper_type: vm.$route.query.paper_type
          }
        })
      })
    }
  }
</script>

<style scoped>

</style>
