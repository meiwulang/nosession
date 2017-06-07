/**
 * Created by sn on 2017/2/28.
 */
import * as types from './mutation-types';
export const showPage = ({commit}, show) => {
    commit(types.SHOW_PAGE, show)
};
