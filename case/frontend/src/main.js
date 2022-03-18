import Vue from "vue";
import App from "./App.vue";
import VueRouter from "vue-router";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import locale from "element-ui/lib/locale/lang/tr-TR";

Vue.config.productionTip = false;

Vue.use(ElementUI, { size: "small", zIndex: 3000, locale });
Vue.use(VueRouter);

import FormPage from "@/components/FormPage";
import OrganizedPresentationList from "@/components/OrganizedPresentationList";

const routes = [
  { path: "/", name: "formPage", component: FormPage, props: true },
  {
    path: "/organizedList",
    name: "organizedList",
    component: OrganizedPresentationList,
    props: true,
  },
];

const router = new VueRouter({
  routes,
});

new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");
