/**
 * Created by sn on 2017/2/3.
 */
import Vue from 'vue';
import VueRouter from 'vue-router';
import ElementUI from 'element-ui';
//import 'element-ui/lib/theme-default/index.css';
//import 'element-ui/lib/theme-default/fonts/element-icons.ttf';
//import 'element-ui/lib/theme-default/fonts/element-icons.woff';
import './../css/main.scss';

const App=resolve => require(['./../components/App.vue'], resolve)
const NotFound=resolve => require(['./../components/NotFound.vue'], resolve)
const memberManagement = resolve => require(['./../components/memberManagement.vue'],resolve);
const inviteCode = resolve => require(['./../components/inviteCode.vue'],resolve);
const newCarType = resolve => require(['./../components/newCarType.vue'],resolve);
const carManagement = resolve => require(['./../components/carManagement.vue'],resolve);
const appImgSetting = resolve => require(['./../components/appImgSetting.vue'],resolve);
const metadataSetting = resolve => require(['./../components/metadataSetting.vue'],resolve);
const productBrandSetting = resolve => require(['./../components/productBrandSetting.vue'],resolve);
const carBrandSetting = resolve => require(['./../components/carBrandSetting.vue'],resolve);
const orderManagement = resolve => require(['./../components/orderManagement.vue'],resolve);
const categoryFirst = resolve => require(['./../components/categoryFirst.vue'],resolve);
const categorySecond = resolve => require(['./../components/categorySecond.vue'],resolve);
const categoryThird = resolve => require(['./../components/categoryThird.vue'],resolve);
const brandIntroduction = resolve => require(['./../components/brandIntroduction.vue'],resolve);
const activityManagement = resolve => require(['./../components/activityManagement.vue'],resolve);
const activityGoods = resolve => require(['./../components/activityGoods.vue'],resolve);
const appVersion = resolve => require(['./../components/appVersion.vue'],resolve);
const Feedback = resolve => require(['./../components/Feedback.vue'],resolve);
const AccountReceipt = resolve => require(['./../components/AccountReceipt.vue'],resolve);
const newProduct = resolve => require(['./../components/newProduct.vue'],resolve);
const groundingProduct = resolve => require(['./../components/groundingProduct.vue'],resolve);
const prepareProduct = resolve => require(['./../components/prepareProduct.vue'],resolve);
const modifyProduct = resolve => require(['./../components/modifyProduct.vue'],resolve);
Vue.use(VueRouter);
Vue.use(ElementUI);
const router = new VueRouter({
        routes: [
            { path: '/', component: memberManagement},
            { path: '*', component: App },
            
            { path: '/memberManagement', component: memberManagement },
            { path: '/inviteCode', component: inviteCode },
           
            { path: '/categoryFirst', component: categoryFirst },
            { path: '/categorySecond', component: categorySecond },
            { path: '/categoryThird', component: categoryThird },
            
            { path: '/newCarType', component: newCarType },
            { path: '/carManagement', component: carManagement },
            
            { path: '/newProduct', component: newProduct },
            { path: '/modifyProduct/:id', component: modifyProduct },
            { path: '/groundingProduct', component: groundingProduct },
            { path: '/prepareProduct', component: prepareProduct },

            { path: '/orderManagement', component: orderManagement },
            { path: '/orderManagement/:status', component: orderManagement },
            
            { path: '/AccountReceipt', component: AccountReceipt },
            { path: '/carBrandSetting', component: carBrandSetting },
            { path: '/productBrandSetting', component: productBrandSetting },
           	{ path: '/metadataSetting', component: metadataSetting },
            
            { path: '/brandIntroduction', component: brandIntroduction },
            { path: '/activityManagement', component: activityManagement },
            { path: '/activityGoods/:id', component: activityGoods },
            
            { path: '/appVersion', component: appVersion },
            { path: '/Feedback', component: Feedback }
            
        ]
    }
);

export default router; //将路由器导出