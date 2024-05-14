import {createApp} from 'vue'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import request from "@/utils/request"
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import axios from "axios";

createApp(App).use(store).use(router).use(ElementPlus, {locale: zhCn}).mount('#app')

//设置axios请求的地址默认是'/api'
axios.defaults.baseURL = "/api";
