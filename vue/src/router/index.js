import {createRouter, createWebHistory} from 'vue-router'
import Manage from "@/views/Manage";
import Login from "@/views/Login";
import Home from "@/views/Home";
import Recognition from "@/views/Recognition"
import Book from "@/views/Book"
import Register from "@/views/register"
import Read from "@/views/Read"
import Bookupload from  "@/views/Bookupload"
import Person from "@/views/person"
import Info from "@/views/info"

const routes = [
    {
        path: '/',
        name: 'Manage',
        component: Manage,
        redirect: '/home',
        children: [
            {   path:'/home',
                name:'Home',
                component: Home
            },
            {   path:'/recognition',
                name:'Recognition',
                component: Recognition
            },
            {   path:'/book',
                name:'Book',
                component: Book
            },
            {   path:'/bookupload',
                name:'Bookupload',
                component: Bookupload
            },
            {   path:'/read',
                name:'Read',
                component: Read
            },
            {   path:'/person',
                name:'Person',
                component: Person
            },
            {   path:'/info',
                name:'Info',
                component: Info
            },
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
    },
    {
        path: '/register',
        name: 'Register',
        component: Register,
    },

]

const router = createRouter({
    mode: 'hash',
    base: process.env.BASE_URL,
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
