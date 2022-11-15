import { createWebHistory, createRouter } from "vue-router";
import { RouteRecordRaw } from "vue-router";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    alias: "/books",
    name: "Books",
    component: () => import("./components/BookList.vue"),
  },
  {
    path: "/books/:id",
    name: "books-details",
    component: () => import("./components/BookDetails.vue"),
  },
  {
    path: "/add",
    name: "books-add",
    component: () => import("./components/BookAdd.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;