/**
 * Created by sn on 2017/2/28.
 */

import * as types from './../mutation-types';

const state = {
    show: "",
    page:{},
    pages: {
        rank:{
            title:"RANK",
            content:"This a page of rank！"
        },
        home:{
            title:"HOME",
            content:"This a page of home！"
        }
    },
};

const getters = {
    show: state => state.show,
    page: state => state.page,
    pages: state => state.pages
};

const actions = {
    getPages ({dispatch}, pages) {
        dispatch(types.GET_PAGES, pages)
    }
};
const mutations = {
    [types.GET_PAGES] (state, pages) {
        state.pages = pages;
    }
};

export default {
    state,
    getters,
    actions,
    mutations
};
