/**
 * Created by sn on 2017/2/28.
 */
import * as types from './mutation-types'
const mutations = {
    [types.SHOW_PAGE](state, show){
        state.show = show ? show : "home";
        state.page = state.pages[show];
    }
};
export default mutations;
