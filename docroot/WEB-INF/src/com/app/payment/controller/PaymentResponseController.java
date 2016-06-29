package com.app.payment.controller;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.app.dto.OrderDTO;
import com.app.dto.OrderPaymentWrapper;
import com.app.payment.service.HDFCPaymentService;
import com.app.payment.service.MWalletPaymentService;
import com.app.payment.service.PayTMPaymentService;
import com.app.payment.service.PayUPaymentService;
import com.app.util.PaymentConstants;
import com.app.util.RestUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class PaymentSummary
 */
@Controller(value = "PaymentResponseController")
@RequestMapping("VIEW")
public class PaymentResponseController extends MVCPortlet {

	private Logger logger = Logger.getLogger(PaymentResponseController.class);

	PayUPaymentService payUPaymentService;
	MWalletPaymentService mWalletPaymentService;
	HDFCPaymentService hdfcPaymentService;
	PayTMPaymentService paytmPaymentService;

	@RenderMapping
	public String doView(RenderRequest request, RenderResponse response, Model model) {

		String orderId = null;
		String rewardCount = "0";
		String deviceId = "";
		String message = "";
		boolean isOrderEligibleForCOD = false; // @Todo

		try {
			Map<String, String> params = RestUtil.extractParamsFromResponse(request);

			String pgType = params.get("pgType");
			if (pgType.equalsIgnoreCase("payU")) {
				orderId = params.get("txnid");
				deviceId = params.get("udf2");
				String status = payUPaymentService.processResponseFrmPayU(params, orderId, isOrderEligibleForCOD);

				String orderData = RestUtil.getOrderDetails(orderId, deviceId);
				String aggregatedOrderData = RestUtil.getAggregatedInboundOrder(orderId, deviceId);
				OrderDTO order = RestUtil.getOrderForRewards(orderData, orderId, deviceId);

				if (status.equalsIgnoreCase("SUCCESS")) {
					rewardCount = RestUtil.getRewardsCount(order);
				} else {
					status = PaymentConstants.FAILED;
					RestUtil.unblockInventory(deviceId, orderId);
				}

				request.setAttribute("amount", order.getTotalBasePrice());
				request.setAttribute("status", status.toUpperCase());
				request.setAttribute("orderId", orderId);
				request.setAttribute("orderDetail", orderData);
				request.setAttribute("aggregatedOrderDetail", aggregatedOrderData);
				request.setAttribute("rewardsPoint", rewardCount);
				request.setAttribute("message", message);
				request.setAttribute("showCODMsg", "");

			} else if (pgType.equalsIgnoreCase("COD")) {

				orderId = params.get("orderId");
				deviceId = params.get("deviceId");
				request.setAttribute("orderId", orderId);
				OrderPaymentWrapper paymentResponse = RestUtil.updateCODInboundOrder(orderId, params);

				String status = paymentResponse.getResponseCode();
				String orderData = RestUtil.getOrderDetails(orderId, deviceId);
				String aggregatedOrderData = RestUtil.getAggregatedInboundOrder(orderId, deviceId);
				OrderDTO order = RestUtil.getOrderForRewards(orderData, orderId, deviceId);
				if (status.equalsIgnoreCase("SUCCESS")) {
					rewardCount = RestUtil.getRewardsCount(order);
				} else {
					status = PaymentConstants.FAILED;
					message = paymentResponse.getErrorMessage(); // COD CAP
																	// Limit;
					RestUtil.unblockInventory(deviceId, orderId);
				}
				request.setAttribute("amount", order.getTotalBasePrice());
				request.setAttribute("status", status.toUpperCase());
				request.setAttribute("orderId", orderId);
				request.setAttribute("orderDetail", orderData);
				request.setAttribute("aggregatedOrderDetail", aggregatedOrderData);
				request.setAttribute("message", message);
				request.setAttribute(
						"showCODMsg",
						"You may receive an automated call from 0124-4132000 where you just need to press 1 to confirm your order. Missed our call! No worries simply call our customer care and they would assist you in confirming the order. Order will be dispatched and processed only after order confirmations.");
				request.setAttribute("rewardsPoint", rewardCount);

			} else if (pgType.equalsIgnoreCase("SC")) {

				orderId = params.get("orderId");
				deviceId = params.get("deviceId");
				request.setAttribute("orderId", orderId);
				String orderData = RestUtil.getOrderDetails(orderId, deviceId);
				String aggregatedOrderData = RestUtil.getAggregatedInboundOrder(orderId, deviceId);
				OrderDTO order = RestUtil.getOrderForRewards(orderData, orderId, deviceId);

				OrderPaymentWrapper paymentResponse = RestUtil.updateStoreCreditOrder(orderId, params);

				String status = paymentResponse.getResponseCode();

				if (status.equalsIgnoreCase("SUCCESS")) {
					rewardCount = RestUtil.getRewardsCount(order);
				} else {
					status = PaymentConstants.FAILED;
					message = paymentResponse.getErrorMessage();
					RestUtil.unblockInventory(deviceId, orderId);
				}
				request.setAttribute("amount", order.getTotalBasePrice());
				request.setAttribute("status", status.toUpperCase());
				request.setAttribute("orderId", orderId);
				request.setAttribute("orderDetail", orderData);
				request.setAttribute("aggregatedOrderDetail", aggregatedOrderData);
				request.setAttribute("message", message);
				request.setAttribute("rewardsPoint", rewardCount);

			} else if (pgType.equalsIgnoreCase("MWALL")) {
				orderId = params.get("orderid");
				deviceId = params.get("deviceId");
				String status = mWalletPaymentService.processResponseFrmMWallet(params, orderId, deviceId,
						isOrderEligibleForCOD);

				String orderData = RestUtil.getOrderDetails(orderId, deviceId);
				String aggregatedOrderData = RestUtil.getAggregatedInboundOrder(orderId, deviceId);
				OrderDTO order = RestUtil.getOrderForRewards(orderData, orderId, deviceId);
				if (status.equalsIgnoreCase("SUCCESS")) {
					rewardCount = RestUtil.getRewardsCount(order);
				} else {
					status = PaymentConstants.FAILED;
					RestUtil.unblockInventory(deviceId, orderId);
				}
				request.setAttribute("amount", order.getTotalBasePrice());
				/*
				 * if ("0".equalsIgnoreCase(params.get("statuscode"))) {
				 * request.setAttribute("status",
				 * PaymentConstants.SUCCESS.toUpperCase()); } else {
				 * request.setAttribute("status",
				 * PaymentConstants.FAILED.toUpperCase()); }
				 */
				request.setAttribute("status", status.toUpperCase());
				request.setAttribute("orderId", orderId);
				request.setAttribute("orderDetail", orderData);
				request.setAttribute("aggregatedOrderDetail", aggregatedOrderData);
				request.setAttribute("rewardsPoint", rewardCount);
				request.setAttribute("message", message);
				request.setAttribute("showCODMsg", "");

			} else if (pgType.equalsIgnoreCase("PAYTM")) {
				orderId = params.get("ORDERID");
				deviceId = params.get("deviceId");
				String status = paytmPaymentService.processResponseFrmPaytm(params, orderId, deviceId,
						isOrderEligibleForCOD);

				String orderData = RestUtil.getOrderDetails(orderId, deviceId);
				String aggregatedOrderData = RestUtil.getAggregatedInboundOrder(orderId, deviceId);
				OrderDTO order = RestUtil.getOrderForRewards(orderData, orderId, deviceId);
				if (status.equalsIgnoreCase("SUCCESS")) {
					rewardCount = RestUtil.getRewardsCount(order);
				} else {
					status = PaymentConstants.FAILED;
					RestUtil.unblockInventory(deviceId, orderId);
				}
				request.setAttribute("amount", order.getTotalBasePrice());
				/*
				 * if ((params.get("STATUS")).equalsIgnoreCase("TXN_SUCCESS")) {
				 * request.setAttribute("status",
				 * PaymentConstants.SUCCESS.toUpperCase()); }
				 */
				request.setAttribute("status", status.toUpperCase());
				request.setAttribute("orderId", orderId);
				request.setAttribute("orderDetail", orderData);
				request.setAttribute("aggregatedOrderDetail", aggregatedOrderData);
				request.setAttribute("rewardsPoint", rewardCount);
				request.setAttribute("message", message);
				request.setAttribute("showCODMsg", "");
			} else if (pgType.equalsIgnoreCase("HDFC")) {

				String responseError = params.get("responseError");
				orderId = params.get("orderId");
				deviceId = params.get("deviceId");
				String orderData = RestUtil.getOrderDetails(orderId, deviceId);
				String aggregatedOrderData = RestUtil.getAggregatedInboundOrder(orderId, deviceId);
				OrderDTO order = RestUtil.getOrderForRewards(orderData, orderId, deviceId);
				if (responseError.equalsIgnoreCase("NoError")) {

					request.setAttribute("amount", order.getTotalBasePrice());
					request.setAttribute("status", params.get("status").toUpperCase());
					request.setAttribute("orderId", orderId);
					request.setAttribute("orderDetail", orderData);
					request.setAttribute("aggregatedOrderDetail", aggregatedOrderData);
					request.setAttribute("rewardsPoint", params.get("rewardsPoint"));
					request.setAttribute("message", message);
					request.setAttribute("showCODMsg", "");
				} else {
					RestUtil.unblockInventory(deviceId, orderId);
					logger.error("Error while executing HDFC response in PaymentResponseController" + responseError);
				}
			} else if (pgType.equalsIgnoreCase("CITRUS")) {
				orderId = params.get("TxId");
				deviceId = params.get("deviceId");
				String status = params.get("TxStatus");

				String orderData = RestUtil.getOrderDetails(orderId, deviceId);
				String aggregatedOrderData = RestUtil.getAggregatedInboundOrder(orderId, deviceId);
				OrderDTO order = RestUtil.getOrderForRewards(orderData, orderId, deviceId);
				if (status.equalsIgnoreCase("SUCCESS")) {
					rewardCount = RestUtil.getRewardsCount(order);
				} else {
					status = PaymentConstants.FAILED;
					RestUtil.unblockInventory(deviceId, orderId);
				}
				request.setAttribute("amount", order.getTotalBasePrice());

				request.setAttribute("status", status.toUpperCase());
				request.setAttribute("orderId", orderId);
				request.setAttribute("orderDetail", orderData);
				request.setAttribute("aggregatedOrderDetail", aggregatedOrderData);
				request.setAttribute("rewardsPoint", rewardCount);
				request.setAttribute("message", message);
				request.setAttribute("showCODMsg", "");
			}
			request.setAttribute("checkout", new StringBuilder(PropsUtil.get("checkout_url")).append(deviceId)
					.toString());
		} catch (Exception e) {
			logger.error("Exception while executing processResponsePayment() for ordrerId [" + orderId + "], ", e);
		}

		return "PaymentSummaryView";

	}

	public PayUPaymentService getPayUPaymentService() {
		return payUPaymentService;
	}

	public void setPayUPaymentService(PayUPaymentService payUPaymentService) {
		this.payUPaymentService = payUPaymentService;
	}

	public MWalletPaymentService getmWalletPaymentService() {
		return mWalletPaymentService;
	}

	public void setmWalletPaymentService(MWalletPaymentService mWalletPaymentService) {
		this.mWalletPaymentService = mWalletPaymentService;
	}

	public HDFCPaymentService getHdfcPaymentService() {
		return hdfcPaymentService;
	}

	public void setHdfcPaymentService(HDFCPaymentService hdfcPaymentService) {
		this.hdfcPaymentService = hdfcPaymentService;
	}

	public PayTMPaymentService getPaytmPaymentService() {
		return paytmPaymentService;
	}

	public void setPaytmPaymentService(PayTMPaymentService paytmPaymentService) {
		this.paytmPaymentService = paytmPaymentService;
	}

}
