package com.hjh.mall.category.util;

import com.hjh.mall.category.context.HJYBizDataContext;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.entity.Updatable;

public class OpMarkHelper {

	public static void markOperator(Updatable updatable) {
		SessionIdentity sessionIdentity = HJYBizDataContext.getSessionIdentity();

		if (null != sessionIdentity) {
			updatable.setUpdate_user(sessionIdentity.getClientId());
		}
	}

}
