package com.hjh.mall.bizapi.biz.goods.middle.util;

import com.hjh.mall.common.core.context.DataContext;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.entity.Updatable;

public class OpMarkHelper {

	public static void markOperator(Updatable updatable) {
		SessionIdentity sessionIdentity = DataContext.getSessionIdentity();

		if (null != sessionIdentity) {
			updatable.setUpdate_user(sessionIdentity.getClientId());
		}
	}

}
