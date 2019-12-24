<template>
  <div>
  <el-container>
    <el-header>
      <Header></Header>
      <Nav></Nav>
    </el-header>
    <el-main>

    </el-main>
  </el-container>
  </div>
</template>

<script>
    import Header from "../../components/Header/Header";
    import Nav from "../../components/Nav/Nav";

    export default {
        name: "center",
      components: {Nav, Header},
       mounted() {
          if((this.$store.state.token==="")||(this.$store.state.id!==this.$store.state.id)){
            this.$message.error('请先登录');
            this.$router.replace('/login')
          }
      }
    }
</script>

<style>
  body{
    background-image: url(../../assets/image/fruit1.jpg);
    background-size:cover;
    left: 0;
    min-height: 580px;
    width: 100%;
    height: 100%;
    overflow: scroll;
  }


</style>
