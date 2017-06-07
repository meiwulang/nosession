/**
 * Created by sn on 2017/2/28.
 */

import Vue from 'vue';
import Vuex from 'vuex';
import state from './state';
import * as actions from './actions';
import * as  getters from './getters';
import mutations from './mutations';
import home from './modules/home';

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export default new Vuex.Store({
    state,
    actions,
    getters,
    mutations,
    modules:{
        home
    },
    strict: debug
})