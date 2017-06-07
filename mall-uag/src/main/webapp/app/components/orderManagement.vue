<template>
    <div class="orderManagement">
    		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>订单管理</el-breadcrumb-item>
				<el-breadcrumb-item>订单管理</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" ref="searchForm" class="demo-form-inline searchForm">
				<el-form-item>
					<el-input v-model="searchForm.productNoLike" placeholder="请输入商品编号" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.productTitleLike" placeholder="请输入商品标题" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.brandNameLike" filterable placeholder="请选择商品品牌" style="width: 150px;">
						<el-option v-for="item in brandList" :key="item.brand_name" :label="item.brand_name" :value="item.brand_name">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.orderNoLike" placeholder="请输入订单编号" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.userMobileLike" placeholder="买家联系方式" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-col :span="11">
						<el-input v-model="searchForm.inviteCodeBegin" placeholder="邀请码区间查询开始"></el-input>
					</el-col>
					<el-col class="line" :span="2" style="text-align: center;">－</el-col>
					<el-col :span="11">
						<el-input v-model="searchForm.inviteCodeEnd" placeholder="邀请码区间查询结束"></el-input>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.payType" placeholder="请选择支付方式" style="width:150px;">
						<el-option label="银行汇款" value="2"></el-option>
						<el-option label="支付宝" value="1"></el-option>
						<el-option label="微信" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.estimateDeliveryDescLike" placeholder="预计发货日期" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-date-picker v-model="deliveryDate" type="datetimerange" :picker-options="pickerOptions" @change="deliveryDateChange" placeholder="发货日期" align="right">
					</el-date-picker>
				</el-form-item>
				<el-form-item>
					<el-date-picker v-model="createDate" type="datetimerange" :picker-options="pickerOptions" @change="createDateChange" placeholder="下单日期" align="right">
					</el-date-picker>
				</el-form-item>
				<el-form-item>
					<el-button class="searchBtn" type="primary" @click="submitForm('searchForm')">搜索</el-button>
					<el-button @click="resetForm('searchForm')">清除</el-button>
					<el-button @click="exportTable">导出</el-button>
				</el-form-item>
			</el-form>
		</div>
		<!-- 列表查询表单 end -->
		<div class="block-white">
			<el-tabs v-model="activeName" @tab-click="handleTabsClick">
				<el-tab-pane v-for="item in tabs" :label="item.title" :name="item.name"></el-tab-pane>
			</el-tabs>
		</div>
		<!-- 订单列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" :data="OrderList.items" 
				ref="multipleTable"
				border stripe style="width: 100%" max-height="550">
				<el-table-column label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column label="商品信息" align="center" width="450">
					<template scope="scope">
						<div class="order-scope">
							订单编号：<a @click="checkOrder(scope.row)">{{scope.row.orderNo}}</a>
							订单时间：{{scope.row.createTime}}
						</div>
						<ul class="productsList">
							<li v-for="item in scope.row.productList">
								<div class="product-pic">
									<img :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.pictureCode">
								</div>
								<div class="product-info" @click="checkProductDetail(item)">
									<div class="product-name">{{item.productName}}</div>
									<div class="product-id">{{item.productId}}</div>
									<div class="product-brand">
										<div class="fl">品牌：{{item.brandName}}</div>
										<div class="fr">类目：{{item.categoryName || item.orderItemList[0].categoryName}}</div>
									</div>
								</div>
							</li>
						</ul>
					</template>
				</el-table-column>
				
				<el-table-column label="订单详情" align="center">
					<el-table-column prop="" label="规格" width="250">
						<template scope="scope">
							<div class="order-scope">
								<div v-if="scope.row.orderStatus==1 && scope.row.payDeposit && !scope.row.payBalance">
									<span v-if="scope.row.balanceDateCount>0">余款延期：{{scope.row.balanceDateCount}}天</span>
									<span v-if="scope.row.balanceDateCount<=0">定金未确认收款，请联系客服</span>
								</div>
								
								<div v-if="scope.row.orderStatus==2 && scope.row.payDeposit && !scope.row.payBalance">
									<span v-if="scope.row.balanceDateCount>0">{{scope.row.balanceDateCount}}天后可付款</span>
									<span v-if="scope.row.balanceDateCount<=0" style="color:#f00;">等待支付余款</span>
								</div>
								<div v-if="scope.row.terminalDate">
									订单关闭时间：{{scope.row.terminalDate}}
								</div>
							</div>
							<ul class="Especificaciones" v-for="item in scope.row.productList">
								<li v-for="_item in item.orderItemList">
									<span>{{_item.productStandardMust}} | {{_item.productOptionalFirst}} | {{_item.productOptionalSecond}}</span>
								</li>
							</ul>
						</template>
					</el-table-column>
					<el-table-column prop="" label="商品价格" width="120" align="center" :show-overflow-tooltip="showtooltip">
						<template scope="scope">
							<div class="productPrice" v-for="item in scope.row.productList">
								<div v-for="_item in item.orderItemList">
									{{_item.price.toFixed(2)}}/{{_item.unit}}
								</div>
							</div>
						</template>
					</el-table-column>
					<el-table-column prop="" label="采购数量" width="120" align="center" :show-overflow-tooltip="showtooltip">
						<template scope="scope">
							<div class="productPrice" v-for="item in scope.row.productList">
								<div v-for="_item in item.orderItemList">
									<span style="color:#f00;">{{_item.quantity}}</span>
								</div>
							</div>
						</template>
					</el-table-column>
					<el-table-column prop="" label="金额" align="center" width="120" :show-overflow-tooltip="showtooltip">
						<template scope="scope">
							<div class="productPrice" v-for="item in scope.row.productList">
								<div v-for="_item in item.orderItemList">
									{{_item.amount.toFixed(2)}}
								</div>
							</div>
						</template>
					</el-table-column>
				</el-table-column>
				<el-table-column prop="transactionPayAmount" label="订单总额" align="center" width="120" :show-overflow-tooltip="showtooltip">
					<template scope="scope">
						<div style="color:#f00;">{{scope.row.transactionPayAmount}}</div>
					</template>
				</el-table-column>
				<el-table-column prop="" label="支付信息" align="center" width="200" :show-overflow-tooltip="showtooltip">
					<template scope="scope">
						<div v-if="activeName!=='status1' && activeName!=='status2'">
							
							<div v-if="scope.row.transactionType==1">
								<div>定金：{{(scope.row.orderStatus==1&&scope.row.payDeposit)?"待确认":("&yen;"+scope.row.transactionDeposit)}}</div>
								<div v-if="!scope.row.payBalance" style="color:#f00;">
									待付余款：&yen;{{scope.row.transactionBalance||(scope.row.transactionPayAmount-scope.row.transactionDeposit).toFixed(2)}}
								</div>
								<div v-if="scope.row.transactionActualPayBalance && scope.row.payBalance">
									余款：{{scope.row.transactionActualPayBalance}}
								</div>
								<div v-if="(scope.row.orderStatus==1||scope.row.orderStatus==2) && scope.row.payBalance">
									余款：待确认
								</div>
							</div>
							<div v-if="scope.row.transactionType==0">
								收款金额：{{
								!scope.row.payBalance ? "待确认" : ("&yen;"+(scope.row.transactionActualPayAmount||scope.row.transactionPayAmount))
								}}
							</div>

						</div>
					</template>
				</el-table-column>
				<el-table-column prop="" label="买家信息" align="center" width="300" :show-overflow-tooltip="showtooltip">
					<template scope="scope">
						<div><a class="consigneeLink" @click="checkBuyer(scope.row)">{{scope.row.userName}}</a></div>
						<div>{{scope.row.userMobile}}</div>
						<div>{{scope.row.inviteCode}}</div>
					</template>
				</el-table-column>
				<el-table-column prop="" label="发货信息" width="150" align="center" :show-overflow-tooltip="showtooltip">
					<template scope="scope">
						<div v-if="scope.row.estimateDeliveryDesc">{{scope.row.estimateDeliveryDesc}}</div>
						<div v-if="scope.row.postageCalculation">运费：&yen;{{scope.row.postage}}</div>
						<div><a class="consigneeLink" @click="checkConsignee(scope.row)">收货地址</a></div>
						<div v-if="scope.row.buyerComments">
							<a class="consigneeLink" @click="checkComment(scope.row)">买家留言</a>
						</div>
					</template>
				</el-table-column>
				<el-table-column fixed="right" label="操作" align="center" width="120">
					<template scope="scope">
						<div data-title="待核算" v-if="scope.row.orderStatus==1 && !scope.row.postage && (activeName=='status1'||activeName=='status0')">
							<el-button @click.native.prevent="accountingPostage(scope.row)" type="info">运费核算</el-button>
						</div>
						<div data-title="待付款" v-if="scope.row.orderStatus==1 && scope.row.postage && !scope.row.payDeposit && !scope.row.payBalance && (activeName=='status2'||activeName=='status0')">
							<el-button @click.native.prevent="accountingPostage(scope.row)" type="warning">修改运费</el-button>
						</div>
						<div data-title="已付定金－未确认收款" v-if="scope.row.orderStatus==1 && scope.row.payDeposit && !scope.row.payBalance && (activeName=='status3'||activeName=='status0')">
							<el-button @click.native.prevent="eventCheckProofs(scope.row)" type="info">支付凭证</el-button>
							<el-button @click.native.prevent="eventModifyProofs(scope.row)" type="warning">编辑凭证</el-button>
							<el-button @click.native.prevent="eventDeposit(scope.row)" type="info">确认收款</el-button>
							<el-button @click.native.prevent="eventCancelOrder(scope.row)" type="danger">取消订单</el-button>
						</div>
						<div data-title="已付定金－已确认收款" v-if="scope.row.orderStatus==2 &&  scope.row.payDeposit && !scope.row.payBalance && (activeName=='status3'||activeName=='status0')">
							<el-button @click.native.prevent="eventCheckProofs(scope.row)" type="info">支付凭证</el-button>
						</div>
						<div data-title="已付款" v-if="(scope.row.orderStatus==1||scope.row.orderStatus==2) && scope.row.payBalance && (activeName=='status4'||activeName=='status0')">
							<el-button @click.native.prevent="eventCheckProofs(scope.row)" type="info">支付凭证</el-button>
							<el-button @click.native.prevent="eventModifyProofs(scope.row)" type="warning">编辑凭证</el-button>
							<el-button @click.native.prevent="eventBalance(scope.row)" type="info">确认收款</el-button>
							<el-button @click.native.prevent="eventCancelOrder(scope.row)" type="danger" v-if="scope.row.transactionType==0">取消订单</el-button>
						</div>

						<div data-title="退款中" v-if="scope.row.orderStatus==9">
							<el-button @click.native.prevent="eventCheckProofs(scope.row)" type="info">支付凭证</el-button>
							<el-button @click.native.prevent="eventRefundOrderComplete(scope.row)" type="info">退款确认</el-button>
						</div>

						<div data-title="等待卖家发货" v-if="scope.row.orderStatus==3">
							<el-button @click.native.prevent="eventDelivery(scope.row)" type="info">发货</el-button>
						</div>

						<div data-title="卖家已发货" v-if="scope.row.orderStatus==4">
							<el-button @click.native.prevent="eventGetLogistics(scope.row)" type="info">发货信息</el-button>
							<el-button @click.native.prevent="eventReceived(scope.row)" type="info">确认到货</el-button>
						</div>
						<div data-title="成功的订单" v-if="scope.row.orderStatus==5 || scope.row.orderStatus==6">
							<el-button @click.native.prevent="eventCheckProofs(scope.row)" type="info">支付凭证</el-button>
							<el-button @click.native.prevent="eventGetLogistics(scope.row)" type="info">发货信息</el-button>
						</div>
						<div data-title="关闭的订单" v-if="scope.row.orderStatus==11 || scope.row.orderStatus==12">
							<el-button @click.native.prevent="eventCheckRefund(scope.row)" type="info">退款信息</el-button>
						</div>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 订单列表 end -->
		<!-- 翻页组件 -->
		<div class="block-white">
			<el-pagination
		      @current-change="handleCurrentChange"
		      :current-page="page"
		      :page-size="pageSize"
		      layout="total, prev, pager, next, jumper"
		      :total="OrderList.total">
		    </el-pagination>
		</div>
		<!-- 翻页组件 end -->

		<!-- 查看订单详情 -->
		<el-dialog title="订单详情" v-model="dialogOrderDetailVisible">
			<div style="height: 570px; overflow: hidden; overflow-y: auto; padding-right: 20px; line-height:30px;">
				<el-row>
					<el-col :span="12">订单编号：{{dialogOrderDetailData.orderNo}}</el-col>
					<el-col :span="12" style="text-align:right">订单状态：{{dialogOrderDetailData.orderStatusDescription}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="8">买家：{{dialogOrderDetailData.consigneeName}}</el-col>
					<el-col :span="8" style="text-align:center;">联系电话：{{dialogOrderDetailData.consigneeMobile}}</el-col>
					<el-col :span="8" style="text-align:right;">邀请码：{{dialogOrderDetailData.inviteCode}}</el-col>
				</el-row>
				<el-collapse v-model="collapseActiveNames" style="margin-top:20px;">
					<el-collapse-item title="订单明细" name="1">
						<div v-for="item in dialogOrderDetailData.productList" style="padding-bottom:20px; overflow:hidden;">
							<div>{{item.productName}}</div>
							<div style="color:#999;">{{item.productId}}</div>
							<div>
								<span>品牌：{{item.brandName}}</span>
								<span style="margin-left:30px;">类目：{{item.categoryName}}</span>
							</div>
							<el-table :data="item.orderItemList" border stripe show-summary sum-text="商品总价" style="width: 100%">
								<el-table-column prop="productStandardMust" label="规格">
									<template scope="scope">
										<span>{{scope.row.productStandardMust}} | {{scope.row.productOptionalFirst}} | {{scope.row.productOptionalSecond}}</span>
									</template>
								</el-table-column>
								<el-table-column prop="price" label="商品价格(元)">
									<template scope="scope">
										{{scope.row.price.toFixed(2)}}/{{scope.row.unit}}
									</template>
								</el-table-column>
								<el-table-column prop="quantity" label="采购数量">
									<template scope="scope">
										{{scope.row.quantity}}
									</template>
								</el-table-column>
								<el-table-column prop="amount" label="金额(元)">
									<template scope="scope">
										{{scope.row.amount.toFixed(2)}}
									</template>
								</el-table-column>
							</el-table>
						</div>
						<el-row>
							<el-col :span="4">
							运费：
							<span v-if="!dialogOrderDetailData.postage" style='color:#f00;'>待核算</span>
							<span v-if="dialogOrderDetailData.postage">{{dialogOrderDetailData.postage}}</span>
							</el-col>
							<el-col :span="20" style="text-align:center;">订单总额：{{dialogOrderDetailData.transactionAmount}}</el-col>
						</el-row>
					</el-collapse-item>
					<el-collapse-item title="待付款明细" name="2">

					</el-collapse-item>
					<el-collapse-item title="运费核算" name="3">

					</el-collapse-item>
				</el-collapse>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button @click="dialogOrderDetailVisible = false">关 闭</el-button>
			</div>
		</el-dialog>

		<!-- 查看买家信息 -->
		<el-dialog title="会员信息" v-model="dialogBuyerVisible">
			<el-row style="height:40px;">
				<el-col :span="12">用户编号：{{dialogBuyerData.client_code}}</el-col>
				<el-col :span="12">邀请码：{{dialogBuyerData.invite_code}}</el-col>
			</el-row>
			<el-collapse v-model="buyActiveNames">
				<el-collapse-item title="单位信息" name="1">
					<div>单位名称：{{dialogBuyerData.enterprise_name}}</div>
					<div>单位简称：{{dialogBuyerData.enterprise_short_name}}</div>
					<div>地址：{{dialogBuyerData.enterprise_address}}</div>
					<div>主营：{{dialogBuyerData.major_business}}</div>
					<div>联系人：{{dialogBuyerData.enterprise_linkman}}</div>
					<div>联系人电话：{{dialogBuyerData.enterprise_tel}}</div>
				</el-collapse-item>
				<el-collapse-item title="用户信息" name="2">
					<div>昵称：{{dialogBuyerData.nick_name}}</div>
					<div>性别：{{dialogBuyerData.sex}}</div>
					<div>用户地区：{{dialogBuyerData.address}}</div>
				</el-collapse-item>
				<el-collapse-item title="注册信息" name="3">
					<div>注册时间：{{dialogBuyerData.init_date}}</div>
					<div style="color:#f00;">账号状态：{{dialogBuyerData.status}}</div>
					<div>注册手机：{{dialogBuyerData.mobile_tel}}</div>
				</el-collapse-item>
			</el-collapse>
		</el-dialog>

		<!-- 查看收货地址 -->
		<el-dialog title="收货地址" v-model="dialogConsigneeVisible">
			<el-form :model="dialogConsigneeData" label-width="100px" class="demo-form-inline">
				<el-form-item label="收货人：">{{dialogConsigneeData.consigneeName}}</el-form-item>
				<el-form-item label="收货地址：">{{dialogConsigneeData.consigneeAddress}}</el-form-item>
				<el-form-item label="联系电话：">{{dialogConsigneeData.consigneeMobile}}</el-form-item>
			</el-form>
		</el-dialog>

		<!-- 查看买家留言 -->
		<el-dialog title="买家留言" v-model="dialogCommentVisible">
			<el-form :model="dialogCommentData" label-width="100px" class="demo-form-inline">
				<el-row>
					<el-col :span="12">
						<el-form-item label="订单编号：">{{dialogCommentData.orderNo}}</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="邀请码：">{{dialogCommentData.inviteCode}}</el-form-item>
					</el-col>
				</el-row>
				<div style="padding:20px">
					{{dialogCommentData.buyerComments}}
				</div>
			</el-form>
		</el-dialog>

		<!-- 查看商品详情 -->
		<el-dialog title="商品信息" v-model="dialogGoodsDetailVisible">
			<div class="goodsDetail" style="height: 450px; overflow: hidden; overflow-y: auto;">
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">商品名称</el-col>
					<el-col :span="20">{{dialogGoodsDetailData.goods_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">类目</el-col>
					<el-col :span="20">{{dialogGoodsDetailData.third_category_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">品牌</el-col>
					<el-col :span="20">{{dialogGoodsDetailData.brand_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">计量单位</el-col>
					<el-col :span="20">{{dialogGoodsDetailData.unit_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">占位图</el-col>
					<el-col :span="20">
						<img v-if="dialogGoodsDetailData.ad_url" style="display: inline-block; width: 80px; height: 30px;" :src="ossUrl+dialogGoodsDetailData.ad_url">
						<span v-if="!dialogGoodsDetailData.ad_url">无</span>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">适用机型</el-col>
					<el-col :span="20">
						<span class="carModelList" v-for="item in dialogGoodsDetailData.carModelList">
							{{item.car_brand_name}} | {{item.car_type}} | {{item.car_models_name}}
						</span>
						<div v-if="dialogGoodsDetailData.adapt_all_models==1">适用全部机型</div>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">banner图</el-col>
					<el-col :span="20">
						<ul class="banner-list">
							<li v-for="item in dialogGoodsDetailData.bannerList">
								<img :src="ossUrl+item.banner_url">
							</li>
						</ul>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">商品详图</el-col>
					<el-col :span="20">
						<ul class="detail-list">
							<li v-for="item in dialogGoodsDetailData.detailList">
								<img :src="ossUrl+item.detail_url">
								<span>{{item.pic_desc}}</span>
							</li>
						</ul>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">客服电话</el-col>
					<el-col :span="10">{{dialogGoodsDetailData.tel}}</el-col>
					<el-col :span="10">预付定金：{{dialogGoodsDetailData.font_money_rate}}%</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">商品介绍</el-col>
					<el-col :span="20">
						<ul class="param-list">
							<li v-for="item in dialogGoodsDetailData.infoList">
								<span>名称<span>{{item.info_title}}</span></span>
								<span>值<span>{{item.info_content}}</span></span>
							</li>
						</ul>
					</el-col>
				</el-row>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button @click="dialogGoodsDetailVisible = false">关 闭</el-button>
			</div>
		</el-dialog>

		<!-- 支付凭证 -->
		<el-dialog :title="isProofsModify?'编辑凭证':'支付凭证'" v-model="dialogProofsVisible">
			<div v-for="proofData in dialogProofsData">
				<el-form :model="proofData" :ref="proofData" label-width="120px" class="demo-form-inline">
					<el-form-item label="付  款  人：">
						<el-input type="text" v-model="proofData.paymentUserName" :readonly="!isProofsModify" placeholder="付款人" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item label="付款银行：">
						<el-input type="text" v-model="proofData.bankAccount" :readonly="!isProofsModify" placeholder="付款银行" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item label="付款账号：">
						<el-input type="text" v-model="proofData.paymentAccount" :readonly="!isProofsModify" placeholder="付款账号" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item label="付款金额：">
						<el-input type="number" v-model="proofData.amount" :readonly="!isProofsModify" placeholder="付款金额" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item>
						<div v-if="proofData.paymentProofSnapshot">
							<img style="max-width:100%;" :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+proofData.paymentProofSnapshot">
						</div>
						<div v-if="!proofData.paymentProofSnapshot">未上传凭证</div>
						
					</el-form-item>
					<el-form-item label="上传时间：">
						{{proofData.createDate}}
					</el-form-item>
					<el-form-item>
						<el-button type="info" v-if="isProofsModify">修改</el-button>
					</el-form-item>
				</el-form>	
			</div>
		</el-dialog>

		<!-- 编辑支付凭证 -->
		<el-dialog :title="isProofsModify?'编辑凭证':'支付凭证'" v-model="dialogProofsModifyVisible" class="dialogProofsModify">
			
				<el-form v-if="dialogProofsData[0]" :model="dialogProofsData[0]" :ref="dialogProofsData[0]" label-width="120px" class="demo-form-inline">
					<el-form-item label="付  款  人：">
						<el-input type="text" v-model="dialogProofsData[0].paymentUserName" :minlength="1" :maxlength="10" :readonly="!isProofsModify" placeholder="付款人" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item label="付款银行：">
						<el-input type="text" v-model="dialogProofsData[0].bankAccount" :maxlength="30" :readonly="!isProofsModify" placeholder="付款银行" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item label="付款账号：">
						<el-input type="text" v-model="dialogProofsData[0].paymentAccount" :minlength="1" :maxlength="20" :readonly="!isProofsModify" placeholder="付款账号" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item label="付款金额：">
						<el-input type="number" v-model="dialogProofsData[0].amount" :maxlength="10" :readonly="!isProofsModify" placeholder="付款金额" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item label="凭证图片：">
						<el-row>
							<el-col :span="11">
								<el-input v-model="dialogProofsData[0].paymentProofSnapshot" type="hidden"></el-input>
								<el-upload
								  class="upload-demo"
								  action="https://jsonplaceholder.typicode.com/posts/"
								  :auto-upload="false"
								  :on-change="handleFileChange"
								  :on-remove="handleFileRemove"
								  :file-list="proofs0List">
								  <el-button size="small" type="primary">{{!proofs0List.length?"上传凭证":"修改"}}</el-button>
								</el-upload>
							</el-col>
							<el-col :span="1"></el-col>
							<el-col :span="12">
								<img style="max-width:100%;" :src="dialogProofsData[0].priviewSnapshot">
							</el-col>
						</el-row>
					</el-form-item>
					<el-form-item label="上传时间：">
						<div style="line-height:36px;">{{dialogProofsData[0].createDate}}</div>
					</el-form-item>
				</el-form>
				<el-form v-if="dialogProofsData[1]" :model="dialogProofsData[1]" :ref="dialogProofsData[1]" label-width="120px" class="demo-form-inline">
					<el-form-item label="付  款  人：">
						<el-input type="text" v-model="dialogProofsData[1].paymentUserName" :readonly="!isProofsModify" placeholder="付款人" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item label="付款银行：">
						<el-input type="text" v-model="dialogProofsData[1].bankAccount" :readonly="!isProofsModify" placeholder="付款银行" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item label="付款账号：">
						<el-input type="text" v-model="dialogProofsData[1].paymentAccount" :readonly="!isProofsModify" placeholder="付款账号" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item label="付款金额：">
						<el-input type="number" v-model="dialogProofsData[1].amount" :readonly="!isProofsModify" placeholder="付款金额" style="width:100%;"></el-input>
					</el-form-item>
					<el-form-item label="凭证图片：">
						<el-row>
							<el-col :span="11" style="line-height:0;">
								<el-input v-model="dialogProofsData[1].paymentProofSnapshot" type="hidden"></el-input>
								<el-upload
								  class="upload-demo"
								  action="https://jsonplaceholder.typicode.com/posts/"
								  :auto-upload="false"
								  :on-change="handleFileChange1"
								  :on-remove="handleFileRemove1"
								  :file-list="proofs1List">
								  <el-button size="small" type="primary">{{!proofs1List.length?"上传凭证":"修改"}}</el-button>
								</el-upload>
							</el-col>
							<el-col :span="1"></el-col>
							<el-col :span="12">
								<img style="max-width:100%;" :src="dialogProofsData[1].priviewSnapshot">
							</el-col>
						</el-row>
					</el-form-item>
					<el-form-item label="上传时间：" class="proofsUploadDate">
						<div style="line-height:36px;">{{dialogProofsData[1].createDate}}</div>
					</el-form-item>
				</el-form>	
				<div style="text-align:center;">
					<el-button type="primary" @click="handleModifyProofs(dialogProofsData)">保存</el-button>
				</div>
		</el-dialog>

		<!-- 运费核算 -->
		<el-dialog :title="this.activeName=='status0'?'运费核算':'修改运费'" v-model="postageVisible">
			<el-form :model="dialogpostageData" :ref="dialogpostageData" label-width="120px" class="demo-form-inline">
				<el-form-item label="订单编号">
					{{dialogpostageData.orderNo}}
				</el-form-item>
				<el-form-item label="运费(元)" prop="postage" :rules="{ required: true, message: '请设置运费' }">
					<el-input type="number" v-model="dialogpostageData.postage" placeholder="运费" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item label="余款延期(天)" prop="balanceDateCount" v-if="dialogpostageData.transactionType==1" :rules="{ required: true, message: '请设填写余款延期天数' }">
					<el-input type="number" v-model="dialogpostageData.balanceDateCount" placeholder="余款延期天数" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item label="预计发货日期" prop="estimateDeliveryDesc" :rules="[{ required: true, message: '请填写预计发货日期' },{ min: 1,max:20, message: '发货日期1-20字符' }]">
					
					<el-input v-model="dialogpostageData.estimateDeliveryDesc" :maxlength="20" placeholder="填写预计发货日期" style="width:300px;"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="postageAndDeliveryDate(dialogpostageData)">保存</el-button>
				<el-button @click="postageVisible = false">关 闭</el-button>
			</div>
		</el-dialog>

		<!-- 退款确认 -->
		<el-dialog title="你确定退款吗？" v-model="dialogRefundOrderVisible">
			<el-form :model="dialogRefundOrderData" :ref="dialogRefundOrderData" label-width="120px" class="demo-form-inline">
				<div style="color:#f00; text-align:center; padding-bottom:20px;">请与买家做好确认，并输入实际退款金额</div>
				<el-form-item label="退款金额">
					<el-input type="number" v-model="dialogRefundOrderData.refundingAmount" placeholder="输入退款金额" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item label="退款说明">
					<el-input type="text" v-model="dialogRefundOrderData.refundingExplain" :minlength="1" :maxlength="20" placeholder="输入退款说明" style="width:100%;"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="handleRefundOrderComplete(dialogpostageData)" :disabled="dialogRefundOrderData.refundingAmount&&dialogRefundOrderData.refundingExplain">确认退款</el-button>
				<el-button @click="dialogRefundOrderVisible = false">关 闭</el-button>
			</div>
		</el-dialog>

		<!-- 发货 -->
		<el-dialog title="发货信息" v-model="deliveryVisible" class="dialogDelivery">
			<el-form :model="dialogDeliveryData" :ref="dialogDeliveryData" label-width="120px" class="demo-form-inline">
				<el-form-item label="订单编号">
					<el-input type="text" v-model="dialogDeliveryData.orderNo" disabled placeholder="" style="width:200px;"></el-input>
				</el-form-item>
				<el-form-item label="联系人">
					<el-input type="text" v-model="dialogDeliveryData.senderName" placeholder="" style="width:200px;"></el-input>
				</el-form-item>
				<el-form-item label="联系电话">
					<el-input type="text" v-model="dialogDeliveryData.senderMobile" placeholder="" style="width:200px;"></el-input>
				</el-form-item>
				<el-form-item label="物流名称" prop="logisticsCompany" :rules="[{ required: true, message: '请填写物流名称' },{min:2,max:10, message: '请填写物流名称' }]">
					<el-input type="text" v-model="dialogDeliveryData.logisticsCompany" :minlength="2" :maxlength="10" placeholder="" style="width:200px;"></el-input>
				</el-form-item>
				<el-form-item label="运单号" >
					<el-input type="text" v-model="dialogDeliveryData.logisticsNo" placeholder="" style="width:200px;"></el-input>
				</el-form-item>
				<el-form-item label="发货时间" prop="deliveryDate" :rules="{ required: true, message: '请填写发货时间' }">
					<el-date-picker id="deliveryDate" v-model="dialogDeliveryData.deliveryDate" format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="请填写发货时间"></el-date-picker>
				</el-form-item>
				<el-form-item label="运单照片">
					<el-input v-model="dialogDeliveryData.logisticsNoteSnapshot" type="hidden"></el-input>
						<el-upload
						  class="upload-demo"
						  action="https://jsonplaceholder.typicode.com/posts/"
						  :auto-upload="false"
						  :on-change="handleLogisticsNoteSnapshotChange"
						  :on-remove="handleLogisticsNoteSnapshotRemove"
						  :file-list="LogisticsNoteSnapshotList">
						  <el-button size="small" type="primary">点击上传</el-button>
						</el-upload>
				</el-form-item>
				
			</el-form>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="handleDelivery(dialogDeliveryData)">保存</el-button>
				<el-button @click="deliveryVisible = false">关 闭</el-button>
			</div>
		</el-dialog>

		<!-- 查看发货信息 -->
		<el-dialog title="发货信息" v-model="dialogLogisticsVisible">
			<el-form :model="dialogLogisticsData" label-width="100px" class="demo-form-inline">
				<el-form-item label="操作员：">{{dialogLogisticsData.createUserName}}</el-form-item>
				<el-form-item label="操作时间：">{{dialogLogisticsData.createDate}}</el-form-item>
				<el-form-item label="联系人：">{{dialogLogisticsData.senderName}}</el-form-item>
				<el-form-item label="联系电话：">{{dialogLogisticsData.senderMobile}}</el-form-item>
				<el-form-item label="物流名称：">{{dialogLogisticsData.logisticsCompany}}</el-form-item>
				<el-form-item label="运单号：">{{dialogLogisticsData.logisticsNo}}</el-form-item>
				<el-form-item label="发货时间：">{{dialogLogisticsData.deliveryDate}}</el-form-item>
				<el-form-item label="运单照片：">
					<img style="max-width:100%;" :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+dialogLogisticsData.logisticsNoteSnapshot">
				</el-form-item>
			</el-form>
		</el-dialog>

		<!-- 查看退款信息 -->
		<el-dialog title="退款信息" v-model="dialogRefundInfoVisible">
			<el-form :model="dialogRefundInfoData" label-width="100px" class="demo-form-inline">
				<el-form-item label="退款金额：">{{Number(dialogRefundInfoData.refundingAmount).toFixed(2)}}元</el-form-item>
				<el-form-item label="退款说明：">{{dialogRefundInfoData.refundingExplain}}</el-form-item>
			</el-form>
		</el-dialog>
    </div>
</template>
<style type="text/css">
.orderManagement .el-table__body .el-button{
	display:block; margin: 10px auto 0;
}
.orderManagement .el-table__body .el-button:last-child{
	 margin: 10px auto 10px;
}
.orderManagement .el-dialog__body .el-form-item__content
{
	line-height: 36px;
}
.orderManagement .dialogDelivery .el-dialog__body .el-form-item__content,
.orderManagement .dialogProofsModify .el-dialog__body .el-form-item__content{
	line-height: 0;
}

</style>
<script>
	import './../css/order.scss';
	import Common from './../js/common';
    export default{
        data(){
            return{
            	tabs : [
            			{title:"全部",name:"status0"},{title:"待核算",name:"status1"},{title:"待付款",name:"status2"},{title:"已付定金",name:"status3"},
            			{title:"已付款",name:"status4"},{title:"退款中",name:"status5"},{title:"待发货",name:"status6"},{title:"已发货",name:"status7"},
            			{title:"成功的订单",name:"status8"},{title:"关闭的订单",name:"status9"}
            		],
                searchForm : this.getDefaultDataForSearch(),		//搜索项表单信息
                OrderList : {},									//订单列表数据缓存
                brandList : [],									//品牌列表
                exceptDeliveryDare : '',							//搜索项－创建开始时间
                deliveryDate : '',								//搜索项－创建结束时间
                createDate : '',
                activeName : "status0",
                ossUrl : "http://test-hjh.oss-cn-shanghai.aliyuncs.com/",
                page : 1,										//当前列表页码
                pageSize : 10,									//列表－每页条数
                loading : false,									//列表加载状态
                brandListApi : "/goodsbrand/queryforweb"	,		//品牌列表接口地址
                orderListApi : "/order-manager-service/orders",	//订单列表接口地址
                queryGoodsApi: "/queryGoods",				//商品信息接口地址
                clientListApi: "./../getClientList",				//买家信息接口地址
                showtooltip : true,								//是否当内容过长被隐藏时显示tooltip true显示 flase不显示
                dialogBuyerVisible : false,				//买家资料－弹窗显示状态
                dialogBuyerData : {},					//买家资料－数据
                dialogConsigneeVisible : false,			//收获地址－弹窗显示状态
                dialogConsigneeData : {},				//收货地址－数据
                dialogCommentVisible : false,			//买家留言－弹窗显示状态
                dialogCommentData : {},					//买家留言－数据
                dialogOrderDetailVisible : false,		//订单详情－弹窗显示状态
                dialogOrderDetailData : {},				//订单详情－数据
                dialogGoodsDetailVisible : false,		//商品详情－弹窗显示状态
                dialogGoodsDetailData : {},				//商品详情－数据
                postageVisible : false,					//运费设置－弹窗显示状态
                dialogpostageData : {					//运费设置－数据
                	balanceDateCount : 0
                },
                deliveryVisible : false,				//发货－弹窗显示状态
                dialogDeliveryData : {},				//发货－数据
                dialogRefundOrderVisible : false,		//退款确认－弹窗显示状态
                dialogRefundOrderData : {},				//退款确认－数据
                dialogProofsVisible : false,			//支付凭证－弹窗显示状态
                dialogProofsData : [],					//支付凭证－数据
                dialogProofsModifyVisible : false,
                proofs0List : [],
                proofs1List : [],
                isProofsModify : false,
                dialogLogisticsVisible : false,			//发货信息－弹窗显示状态
                dialogLogisticsData : {},				//发货信息－数据
                dialogRefundInfoVisible : false,
                dialogRefundInfoData : {},
                collapseActiveNames : ["1","2","3"],
                buyActiveNames : ["1","2","3"],
                LogisticsNoteSnapshotList : [],
                pickerOptions: {
					shortcuts: [{
						text: '最近一周',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近一个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
							picker.$emit('pick', [start, end]);
						}
					}]
				},
            }
       	},
		created(){
			var status = this.$route.params.status || 0;
			this.activeName = "status" + status;
			document.title = "后台管理系统-订单管理";
			this.getParamByTabs();
			this.getOrderListByCache();
			this.getBrandList();
			var that = this;
			window.onhashchange = function(){
				var status = location.hash.match(/\d+/g);
				status = status ? status.join("") : 0;
				that.activeName = "status" + status;
				
				$(".hjh-menu a").each(function(){
		    			if($(this).attr("href")==location.hash){
		    				$(this).parent().addClass("is-active").siblings().removeClass("is-active")
		    		}
		    	});
				if(that.ClickTargetIsTab){
					that.ClickTargetIsTab = false;
					return;
				}else{
					that.getOrderListByTabs();
				}
			}
		},
		methods : {
			/* 切换订单状态tab后根据tab值 设置查询入参 */
			getParamByTabs(){
				var i = Number(this.activeName.replace("status",""));
				var param = {};
				switch(i){
					//待核算
					case 1 :
						param = {
							orderStatus : ["NON_PAYMENT"],
							isPostageCalculation : false
						}
						break;
					//待付款
					case 2 : 
						param = {
							orderStatus : ["NON_PAYMENT"],
							isPostageCalculation : true,
							haveDepositPayProof : false,
							haveFullPayProof : false
						}
						break;
					//已付定金
					case 3 : 
						param = {
							orderStatus : ["NON_PAYMENT","PART_PAID"],
							haveDepositPayProof : true,
							haveFullPayProof : false
						}
						break;
					//已付款
					case 4 : 
						param = {
							orderStatus : ["NON_PAYMENT","PART_PAID"],
							haveFullPayProof : true
						}
						break;
					//退款中
					case 5 :
						param = {
							orderStatus : ["REFUNDING"]
						}
						break;
					//待发货
					case 6 :
						param = {
							orderStatus : ["UN_DELIVERY"]
						}
						break;
					//已发货
					case 7 :
						param = {
							orderStatus : ["DELIVERED"]
						}
						break;
					//成功的订单
					case 8 :
						param = {
							orderStatus : ["RECEIVED","SUCCESS"]
						}
						break;
					//关闭的订单
					case 9 :
						param = {
							orderStatus : ["CLOSED_BUYER_CANCEL","CLOSED_SELLER_CANCEL","CLOSED_REFUNDED","CLOSED_RETURN_GOODS","CLOSED_EXCEPTION"]
						}
						break;
				};
				this.paramsOrderStatus = param;
			},
			/** 发货日期发生改变 **/
			deliveryDateChange(val){
				if(!val){
					this.searchForm.deliveryDateBegin = "";
					this.searchForm.deliveryDateEnd = "";
				}else{
					var arr = val.split(" - ");
					this.searchForm.deliveryDateBegin = arr[0].replace(/\s+/g,"T")+".442Z";
					this.searchForm.deliveryDateEnd = arr[1].replace(/\s+/g,"T")+".442Z";	
				}
			},
			/** 下单日期发生改变 **/
			createDateChange(val){
				if(!val){
					this.searchForm.createDateBegin = "";
					this.searchForm.createDateEnd = "";
				}else{
					var arr = val.split(" - ");
					this.searchForm.createDateBegin = arr[0].replace(/\s+/g,"T")+".442Z";
					this.searchForm.createDateEnd = arr[1].replace(/\s+/g,"T")+".442Z";	
				}
			},
			/** tab状态选项卡切换回调 **/
			handleTabsClick(tab){
				this.ClickTargetIsTab = true;
				this.setMenuActive();
				this.getOrderListByTabs();
			},
			/** 静态切换菜单选中状态 **/
			setMenuActive(){
				var i = Number(this.activeName.replace("status",""));
				location.href = "#/orderManagement"+ (i ? ("/"+i) : "");
				$(".hjh-menu a").each(function(){
		    		if($(this).attr("href")==location.hash){
		    			$(this).parent().addClass("is-active").siblings().removeClass("is-active")
		    		}
		    	});
			},
			/** 根据tab选中状态加在对应数据列表 **/
			getOrderListByTabs(){
				this.getParamByTabs();
				this.submitForm();
			},
			//搜索项表单初始化
			getDefaultDataForSearch(){
				return {
					productNoLike : '',
					productTitleLike : '',
					brandNameLike : '',
					orderNoLike : '',
					userMobileLike : '',
					inviteCodeBegin : '',
					inviteCodeEnd : '',
					payType : ''
				}
			},
			//翻页回调
			handleCurrentChange(val){
				this.$data.page = val;
				this.searchDataCache.pageNum = val;
				this.getOrderList();
			},
			//订单列表 点击订单编号查看订单详情
			checkOrder(data){
				this.$http.post("/order-manager-service/order/progress",{
					access_token : localStorage.access_token,
					orderId : data.orderId
				}).then(res=>{});
				//return;
				this.dialogOrderDetailData = data;
				this.dialogOrderDetailVisible = true;
				return;
				var param = {
					access_token : localStorage.access_token,
					orderId : data.orderId
				};
				this.$http.post("/dev/order-manager-service/order/orderId",param).then(res=>{
					if(res.data.error_no==0){
						this.dialogOrderDetailData = res.data.data;
					}else{
						this.$message({
							type: "warning",
							message: res.data.error_info || "系统繁忙,稍后再试"
						});
					}
				});
			},
			//查看商品信息
			checkProductDetail(item){
				//return;
				var param = {
					goods_id : item.productId,
					goods_name : item.productName
				};
				this.$http.post(this.queryGoodsApi,param).then(response=>{
					var jsondata = response.data;
					if(jsondata.error_no==0 && jsondata.result_list.length){
						this.dialogGoodsDetailData = jsondata.result_list[0];
						this.dialogGoodsDetailVisible = true;
					}else{
						this.$message({
							type: "warning",
							message: "商品详情数据查询异常"
						});
					}
				});
			},
			//查看买家信息
			checkBuyer(item){
				var param = Common.filterParamByEmptyValue({
					//nick_name : item.userName
					client_id : item.userId
				});
				this.$http.post("/getClientList",param).then(response=>{
                	var jsondata = response.data;
					if(jsondata.error_no==0 && jsondata.result_list.length){
						this.dialogBuyerData = jsondata.result_list[0];
						this.dialogBuyerVisible = true;
					}else{
						this.$message({
							type: "warning",
							message: "买家信息数据查询异常"
						});
					}
				});
			},
			//查看收货地址
			checkConsignee(data){
				this.dialogConsigneeVisible = true;
				this.dialogConsigneeData = data;
			},
			/** 查看买家留言 **/
			checkComment(data){
				this.dialogCommentVisible = true;
				this.dialogCommentData = data;
			},
			/** 查看支付凭证 **/
			eventCheckProofs(item){
				var api = "/order-manager-service/order/orderId/pay/getproofs",
					param = {
						access_token : localStorage.access_token,
						orderId : item.orderId
					};
				this.$http.post("/order-manager-service/order/orderId/pay/getproofs",param).then(response=>{
					var jsondata = response.data;
					if(jsondata.error_no==0){
						this.dialogProofsData = jsondata.data;
						this.dialogProofsData = this.dialogProofsData.map(function(item){
							item.createDate = Common.time2DateFormat(item.createDate);
							item.amount = Number(item.amount).toFixed(2);
							return item;
						});
						this.isProofsModify = false;
						this.dialogProofsVisible = true;
					}else{
						this.$message({
							type: "warning",
							message: jsondata.error_info
						});
					}
				});
			},
			/** 点击按钮－编辑支付凭证 **/
			eventModifyProofs(item){
				var api = "/order-manager-service/order/orderId/pay/getproofs",
					param = {
						access_token : localStorage.access_token,
						orderId : item.orderId
					};
				this.$http.post("/order-manager-service/order/orderId/pay/getproofs",param).then(response=>{
					var jsondata = response.data;
					if(jsondata.error_no==0){
						this.dialogProofsData = jsondata.data;
						var that = this;
						this.dialogProofsData = this.dialogProofsData.map((item,index)=>{
							item.priviewSnapshot = "http://test-hjh.oss-cn-shanghai.aliyuncs.com/" + item.paymentProofSnapshot;
							item.createDate = Common.time2DateFormat(item.createDate);
							item.amount = Number(item.amount).toFixed(2);
							if(index==0){
								that.proofs0List = [{
						          name: item.paymentProofSnapshot,
						          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.paymentProofSnapshot,
						          status: 'finished'
						        }]
							}
							if(index==1){
								that.proofs1List = [{
						          name: item.paymentProofSnapshot,
						          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.paymentProofSnapshot,
						          status: 'finished'
						        }]
							}
							return item;
						});

						this.isProofsModify = true;
						this.dialogProofsModifyVisible = true;
					}else{
						this.$message({
							type: "warning",
							message: jsondata.error_info
						});
					}
				});
			},
			/** 修改支付凭证 **/
			handleModifyProofs(item){
				var access_token = localStorage.access_token;
				var that = this,reg = /data:\S+\"/g;
				this.proofUpdatedCount = 0;
				item.map(child=>{
					//var arr = child.paymentProofSnapshot.match(reg);
					var bool = child.paymentProofSnapshot.indexOf("data:")==0;
					var param = {
							access_token : access_token,
							amount : child.amount,
							bankAccount : child.bankAccount,
							payProofId : child.id,
							paymentAccount : child.paymentAccount,
							paymentProofSnapshot : child.paymentProofSnapshot,
							paymentUserName : child.paymentUserName,
							orderId : child.orderId
						};
					if(bool){
						that.uploadCompressImg(child.paymentProofSnapshot,function(img_uri){
							param.paymentProofSnapshot = img_uri;
							that.updateProof(param);
						});
					}else{
						that.updateProof(param);
					}
				});
			},
			/** 上传图片 **/
			uploadCompressImg(result,cb){
				var that = this,
					param = {
						access_token : localStorage.access_token,
						base64Str : result,
						imgType : 19
					};
				that.$http.post("/json/901210",param).then(response => {
					var jsondata = response.data;
					if(jsondata.error_no==0){
						cb(jsondata.img_uri);
					}else{
						that.$message({
							type: "warning",
							message: (jsondata.error_on +":"+ jsondata.error_info)
						});
					}
					
				});
			},
			/** 更新凭证 **/
			updateProof(param){
				var api = "/order-manager-service/order/orderId/pay/proof";
				this.$http.post(api,param).then(response=>{
					var jsondata = response.data;
					this.$message({
						type: jsondata.error_no==0 ? "success" : "warning",
						message: jsondata.error_no==0 ? "修改成功" : (jsondata.error_no +":"+ jsondata.error_info)
					});
					if(jsondata.error_no==0){
						this.proofUpdatedCount++;
						if(this.proofUpdatedCount == this.dialogProofsData.length){
							this.isProofsModify = true;
							this.dialogProofsModifyVisible = false;
						}
					}
				});
			},
			/** 支付凭证1的上传文件变化 **/
			handleFileChange(_file,_filelist){
				this.proofs0List = [_filelist[_filelist.length-1]];
				var that = this;
				var reader = new FileReader(); 
					reader.readAsDataURL(_file.raw);
					reader.onload = function(e){
						var img = new Image();
							img.src = this.result;
							img.onload = function(){
								that.dialogProofsData[0].paymentProofSnapshot = Common.compressImg(img,0.8,"image/jpeg");
								that.dialogProofsData[0].priviewSnapshot = Common.compressImg(img,0.8,"image/jpeg");
								$(".el-upload-list").eq(0).children("li").last().prev().remove();
							}
					}
			},
			/** 支付凭证1的上传文件删除 **/
			handleFileRemove(_file,_filelist){
				if(!_filelist.length){
					this.dialogProofsData[0].paymentProofSnapshot = "";
					this.dialogProofsData[0].priviewSnapshot = "";
					this.proofs0List = [];
				}
			},
			/** 支付凭证2的上传文件变化 **/
			handleFileChange1(_file,_filelist){
				this.proofs1List = [_filelist[_filelist.length-1]];
				var that = this;
				var reader = new FileReader(); 
					reader.readAsDataURL(_file.raw);
					reader.onload = function(e){
						var img = new Image();
							img.src = this.result;
							img.onload = function(){
								that.dialogProofsData[1].paymentProofSnapshot = Common.compressImg(img,0.8,"image/jpeg");
								that.dialogProofsData[1].priviewSnapshot = Common.compressImg(img,0.8,"image/jpeg");
								$(".el-upload-list").eq(1).children("li").last().prev().remove();
							}
					}
			},
			/** 支付凭证2的上传文件删除 **/
			handleFileRemove1(_file,_filelist){ 
				if(!_filelist.length){
					this.dialogProofsData[1].paymentProofSnapshot = "";
					this.dialogProofsData[1].priviewSnapshot = "";
					this.proofs1List = [];
				}
			},
			/**  点击按钮－运费核算 **/
			accountingPostage(item){
				//var time = new Date(item.estimateDeliveryDesc).getTime();
				this.dialogpostageData = {
					orderNo : item.orderNo,
					balanceDateCount : item.balanceDateCount,
					//estimateDeliveryDesc : time ? new Date(item.estimateDeliveryDesc) : "",
					estimateDeliveryDesc : item.estimateDeliveryDesc,
					orderId : item.orderId,
					postage : item.postage,
					transactionType : item.transactionType
				};
				
				this.postageVisible = true;
			},
			/** 提交数据－运费核算 **/
			postageAndDeliveryDate(formdata){
				this.$refs[this.dialogpostageData].validate((valid) => {
					if (valid) {
						var param = {
							access_token : localStorage.access_token,
							balanceDateCount : formdata.balanceDateCount,
							estimateDeliveryDesc : formdata.estimateDeliveryDesc || Common.time2DateFormat(formdata.estimateDeliveryDesc).split(" ")[0],
							orderId : formdata.orderId,
							postage : formdata.postage
						},api = "/order-manager-service/order/orderId/transaction/postageAndDeliveryDate";
						//if(formdata.transactionType==0){
						//	delete param.balanceDateCount;
						//}
						this.$http.post(api,param).then(response=>{
							var jsondata = response.data;
							this.$message({
								type: jsondata.error_no==0 ? "success" : "warning",
								message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
							});
							if(jsondata.error_no==0){
								this.postageVisible = false;
								this.getOrderListByCache();
							}
						});
					}
				});
			},
			/** 已付定金－确认收款 **/
			eventDeposit(item){
				this.$confirm('确认收款，即视为实际收款金额等于应支付定金！', '确认收款', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var api = "/order-manager-service/order/orderId/transaction/deposit",
						param = {
							access_token : localStorage.access_token,
							orderId : item.orderId,
							deposit : item.transactionDeposit
						};
					this.$http.post(api,param).then(response=>{
						var jsondata = response.data;
							this.$message({
								type: jsondata.error_no==0 ? "success" : "warning",
								message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
							});
							if(jsondata.error_no==0){
								this.getOrderListByCache();
							}
					});
				});
			},
			/** 已付款－确认收款 **/
			eventBalance(item){
				this.$confirm('请确认实际收款总额等于订单总额。', '确认收款', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var api = item.transactionType==1 ? "/order-manager-service/order/orderId/transaction/balance" : "/order-manager-service/order/transaction/actualAmount",
						param = {
							access_token : localStorage.access_token,
							orderId : item.orderId,
							balance : item.transactionBalance
						};
						if(item.transactionType==0){
							delete param.balance;
							param.amount = item.transactionPayAmount;
						}
					console.log(api,param);
					this.$http.post(api,param).then(response=>{
						var jsondata = response.data;
							this.$message({
								type: jsondata.error_no==0 ? "success" : "warning",
								message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
							});
							if(jsondata.error_no==0){
								this.getOrderListByCache();
							}
					});
				});
			},
			/** 点击按钮－取消订单 **/
			eventCancelOrder(item){
				this.$confirm('实际已付款的订单请勿取消订单！取消订单后订单关闭！', '取消订单', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var api = "/order-manager-service/order/cancelOrder",
						param = {
							access_token : localStorage.access_token,
							orderId : item.orderId
						};
					this.$http.post(api,param).then(response=>{
						var jsondata = response.data;
							this.$message({
								type: jsondata.error_no==0 ? "success" : "warning",
								message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
							});
							if(jsondata.error_no==0){
								this.getOrderListByCache();
							}
					});
				});
			},
			/** 点击按钮－退款确认 **/
			eventRefundOrderComplete(item){
				this.dialogRefundOrderData.max = item.transactionPayAmount;
				this.dialogRefundOrderData.orderId = item.orderId;
				this.dialogRefundOrderVisible = true;
			},
			/** 提交操作－退款确认 **/
			handleRefundOrderComplete(){

				if(!this.dialogRefundOrderData.refundingAmount){
					this.$message({
						type: "warning",
						message: "请填写退款金额"
					});
					return;
				}
				if(this.dialogRefundOrderData.refundingAmount > this.dialogRefundOrderData.max){
					this.$message({
						type: "warning",
						message: "退款金额超额"
					});
					return;
				}
				if(!this.dialogRefundOrderData.refundingExplain){
					this.$message({
						type: "warning",
						message: "请填写退款说明"
					});
					return;
				}

				this.dialogRefundOrderData.access_token = localStorage.access_token;
				var api = "/order-manager-service/order/refundOrderComplete";
				this.$http.post(api,this.dialogRefundOrderData).then(response=>{
					var jsondata = response.data;
					this.$message({
						type: jsondata.error_no==0 ? "success" : "warning",
						message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
					});
					if(jsondata.error_no==0){
						this.dialogRefundOrderVisible = false;
						this.getOrderListByCache();
					}
				});
			},
			/** 点击按钮－发货 **/
			eventDelivery(item){
				this.dialogDeliveryData = {
					orderId : item.orderId,
					orderNo : item.orderNo,
					logisticsCompany : "",
					logisticsNo : "",
					logisticsNoteSnapshot : "",
					deliveryDate : ""
				};
				this.LogisticsNoteSnapshotList=[];
				this.deliveryVisible = true;
				$("#deliveryDate input").val("");
			},
			/** 提交操作－发货 **/
			handleDelivery(formdata){
				this.$refs[this.dialogDeliveryData].validate((valid) => {
					if(!valid){return;}
					this.dialogDeliveryData.access_token = localStorage.access_token;
					this.dialogDeliveryData.deliveryDate = Common.time2DateFormat(this.dialogDeliveryData.deliveryDate).replace(/\s+/g,"T")+".442Z";
					var api = "/order-manager-service/order/orderId/logistics/delivery";
					this.$http.post(api,this.dialogDeliveryData).then(response=>{
						var jsondata = response.data;
							this.$message({
								type: jsondata.error_no==0 ? "success" : "warning",
								message: jsondata.error_no==0 ? "操作成功" : (jsondata.error_no + jsondata.error_info)
							});
							if(jsondata.error_no==0){
								this.deliveryVisible = false;
								this.getOrderListByCache();
							}
					});

				});
			},
			/** 发货－选择运单照片 **/
			handleLogisticsNoteSnapshotChange(_file,_filelist){
				this.LogisticsNoteSnapshotList = [_filelist[_filelist.length-1]];
				var that = this;
				var reader = new FileReader(); 
					reader.readAsDataURL(_file.raw);
					reader.onload = function(e){
						var img = new Image();
							img.src = this.result;
							img.onload = function(){
								that.dialogDeliveryData.logisticsNoteSnapshot = Common.compressImg(img,0.8,"image/jpeg");
								$(".el-upload-list > li").last().prev().remove();
							}
					}
			},
			/** 发货－删除运单照片 **/
			handleLogisticsNoteSnapshotRemove(_file,_filelist){
				if(!_filelist.length){
					this.dialogDeliveryData.logisticsNoteSnapshot = "";
				}
			},
			/** 点击按钮－查看发货信息 **/
			eventGetLogistics(item){
				var api = "/order-manager-service/order/orderId/logistics/getLogistics",
					param = {
						access_token : localStorage.access_token,
						orderId : item.orderId
					};
				this.$http.post(api,param).then(response=>{
					var jsondata = response.data;
					if(jsondata.error_no==0){
						this.dialogLogisticsData = jsondata.data;
						this.dialogLogisticsData.createDate = Common.time2DateFormat(this.dialogLogisticsData.createDate);
						this.dialogLogisticsData.deliveryDate = Common.time2DateFormat(this.dialogLogisticsData.deliveryDate);
						this.dialogLogisticsVisible = true;
					}else{
						this.$message({
							type: "warning",
							message: jsondata.error_info
						});
					}
				});
			},
			/** 点击按钮确认到货 **/
			eventReceived(item){
				this.$confirm('确认到货后，该订单流程结束', '你确认已到货吗？', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var api = "/order-manager-service/order/orderId/logistics/received",
						param = {
							access_token : localStorage.access_token,
							orderId : item.orderId
						};
					this.$http.post(api,param).then(response=>{
						var jsondata = response.data;
							this.$message({
								type: jsondata.error_no==0 ? "success" : "warning",
								message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
							});
							if(jsondata.error_no==0){
								this.getOrderListByCache();
							}
					});
				});
			},
			/** 点击按钮－退款信息 **/
			eventCheckRefund(item){
				this.dialogRefundInfoVisible = true;
				this.dialogRefundInfoData = item;
			},
			/** 根据入参缓存进行查询 **/
			getOrderListByCache(params){
				params = Common.deepClone({pageNum:this.page,pageSize:this.pageSize},params||{});
				params = Common.deepClone(params,this.paramsOrderStatus);
				params = Common.filterParamByEmptyValue(params);
				this.searchDataCache = params;
				this.getOrderList();
			},
			//获取订单列表
			getOrderList(){
				if(this.loading) return;
				this.loading = true;
				var that = this;
				this.$http.post(this.orderListApi,this.searchDataCache).then(response=>{
					var jsonData = response.data;
					if(jsonData.error_no==0){
						if(jsonData.data){
							jsonData.data.items = jsonData.data.items.map(function(item){
								item.createTime = Common.time2DateFormat(item.createdDate);
								if(item.terminalDate){
									item.terminalDate = Common.time2DateFormat(item.terminalDate);
								}
								return item;
							});
						}
						that.$data.OrderList = jsonData.data || {};
						$(".el-table__body-wrapper").scrollTop(0);
					}else{
						that.$message({
							type: "warning",
							message: jsonData.error_info || "服务器异常"
						});
					}
					that.$data.loading = false;
				});
			},
			//获取品牌列表
			getBrandList(){
				var that = this;
				this.$http.post(this.brandListApi,{status:1,limit:10000000}).then(response=>{
					var jsondata = response.data;
					that.brandList = jsondata.error_no == 0 ? jsondata.result_list : [];
				});
			},
			//搜索
			submitForm(form){
				//console.log(this.searchForm.createDateBegin);
				this.$data.page = 1;
				this.getOrderListByCache(this.searchForm);
			},
			//清除
			resetForm(form){
				this.searchForm = this.getDefaultDataForSearch();
				this.exceptDeliveryDare = "";
				this.deliveryDate = "";
				this.createDate = "";
				this.searchForm.createDateBegin = "";
				this.searchForm.createDateEnd = "";
				this.searchForm.deliveryDateBegin = "";
				this.searchForm.deliveryDateEnd = "";
				this.searchForm.exceptDeliveryBegin = "";
				this.searchForm.exceptDeliveryEnd = "";
				this.submitForm();
			},
			//导出
			exportTable(){
				
			}
		}
    }
</script>
