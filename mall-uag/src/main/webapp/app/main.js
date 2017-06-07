/**
 * Created by lixiangyang on 2017/4/27.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var mock = true;//是否模拟数据
import Vue from 'vue';
import Vuex from 'vuex';
import ElementUI from 'element-ui';
import VueResource from 'vue-resource';
import Config from 'config';
import Common from './js/common';
import router from './js/routers';
import store from './vuex/store';
import './css/main.scss';
Vue.use(VueResource);
Vue.use(Vuex);
Vue.use(ElementUI);

//Vue全局配置
Common.deepClone(Config.vue, Vue.config);    //解决跨域问题
Vue.http.options.xhr = {withCredentials: true};

const methods = {
	logout(){
		this.$confirm('确定退出登陆吗？', '提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			type: 'warning'
		}).then(() => {
			location.href = "/logout"
		}).catch(() => {
		});
	}
}
function created(){
	var that=this;
    that.$http.get(Config.apiUrl+Config.urlsEnum.menu).then(response=> {
    		var jsonData=response.data;
    		that.$data.menuList=jsonData;
    		
//  		jsonData.map(function(item,i){
//  			
//  			item.list.map(function(child,j){
//  				if(location.hash == "#"+child.link){
//  					that.$data.defaultActive = i+"-"+j;
//  				}
//  			})
//  		});
    		var hash = location.hash;
    		setTimeout(function(){
    			if(hash == "#/"){
    				var tar = $(".hjh-menu a").eq(0);	
    				tar.parents(".el-menu").siblings(".el-submenu__title").trigger("click");
	    			tar.parent().trigger("click");
    			}
	    		$(".hjh-menu a").each(function(){
	    			if($(this).attr("href")==hash){
	    				$(this).parents(".el-menu").siblings(".el-submenu__title").trigger("click");
	    				$(this).parent().trigger("click");
	    			}
	    		});
	    		$(document).on("keypress",function(e){
	    			if($(".searchForm").length && e.keyCode==13){
	    				$(".searchBtn").trigger("click");
	    			}
	    		});
    		},100);
    		
    });
}
function data(){
	return {
		menuList : [],
		defaultActive : "0-0",
		uniqueOpened : true
	}
}
const app = new Vue({
	data,
    router,
    created,
    methods
}).$mount('#app');