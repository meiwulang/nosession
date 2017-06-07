package com.hjh.mall.util;

import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.entity.Updatable;
import com.hjh.mall.context.HJYBizDataContext;

public class OpMarkHelper {

	public static void markOperator(Updatable updatable) {
		SessionIdentity sessionIdentity = HJYBizDataContext.getSessionIdentity();

		if (null != sessionIdentity) {
			updatable.setUpdate_user(sessionIdentity.getClientId());
		}
	}

}
