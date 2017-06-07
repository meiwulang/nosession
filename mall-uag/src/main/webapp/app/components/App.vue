<template>
    <div style=" line-height: 60px; font-size: 16px; color: #333333; margin-left: 20px;">
        这个页面尚未改造 <a target="_blank" href="javascript:void(0);" @click="turnpage">点击这里查看旧版页面</a>
    </div>
</template>

<script>
    export default{
        data(){
            return {
                
            }
        },
        methods : {
        		turnpage(){
        			window.open(location.hash.replace("#","/html") + ".jsp","_blank");
        		}
        }
    }
</script>
