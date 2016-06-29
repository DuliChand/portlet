package com.app.payment.controller;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.app.dto.OrderDTO;
import com.app.payment.service.HDFCPaymentService;
import com.app.payment.service.MWalletPaymentService;
import com.app.payment.service.PayUPaymentService;
import com.app.util.PaymentConstants;
import com.app.util.RestUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class HDFCResponseHandle
 */
@Controller(value = "HDFCResponseController")
@RequestMapping("VIEW")
public class HDFCResponseController extends MVCPortlet {

	private Logger logger = Logger.getLogger(HDFCResponseController.class);

	PayUPaymentService payUPaymentService;
	MWalletPaymentService mWalletPaymentService;
	HDFCPaymentService hdfcPaymentService;

	@RenderMapping
	public String doView(RenderRequest request, RenderResponse response,
			Model model) {
		String redirectURL = hdfcPaymentService.getHDFCRedirectUrl(request);
		request.setAttribute("redirectURL", redirectURL);
		String orderId = null;
		String rewardCount = "0";
		String deviceId = null;

		String fnyTokenName = PropsUtil.get("FNY_TOKEN_NAME");
		boolean isOrderEligibleForCOD = false; // @Todo
		Map<String, String> params = RestUtil
				.extractParamsFromResponse(request);
		try {
			if (params.get("ResError") != null) {
				orderId = params.get("ResTrackId");
				request.setAttribute("responseError", params.get("ResError"));
				request.setAttribute("amount", params.get("ResAmount"));

				Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					for (int i = 0; i < cookies.length; i++) {
						if (cookies[i].getName().equals(fnyTokenName)) {
							deviceId = cookies[i].getValue();
							break;
						}
					}
				}
				RestUtil.updateGatewayConnectionFailed(orderId, deviceId,
						"HDFCCC");
				logger.error("Error while executing HDFCResponseController doView()"
						+ params.get("ResError"));
				request.setAttribute("status", "FAILURE");
				request.setAttribute("orderId", orderId);
				request.setAttribute("orderDetail", "");
				request.setAttribute("rewardsPoint", rewardCount);

			} else {

				String pgType = params.get("udf3");
				if (pgType.equalsIgnoreCase("HDFC")) {
					orderId = params.get("trackid");
					deviceId = params.get("udf4");
					String result = params.get("result");
					String orderData = RestUtil.getOrderDetails(orderId,
							deviceId);
					OrderDTO order = RestUtil.getOrderForRewards(orderData,
							orderId, deviceId);
					String status = hdfcPaymentService.processResponseFrmHDFC(
							request, params, order, isOrderEligibleForCOD);
					if (status.equalsIgnoreCase("SUCCESS")) {
						rewardCount = RestUtil.getRewardsCount(order);
					}
					if (result.equals("CAPTURED")) {
						request.setAttribute("status",
								PaymentConstants.SUCCESS.toUpperCase());
					} else {
						request.setAttribute("status",
								PaymentConstants.FAILED.toUpperCase());
					}

					request.setAttribute("amount", params.get("amt"));
					request.setAttribute("orderId", orderId);
					request.setAttribute("rewardsPoint", rewardCount);
					request.setAttribute("deviceId", deviceId);
					request.setAttribute("responseError", "NoError");

				}

			}
		} catch (Exception e) {
			logger.error("Exception while executing HDFCResponseController for orderId ["
					+ orderId + "], " + e);
		}
		return "HDFCResponseToConfirmation";

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

	public void setmWalletPaymentService(
			MWalletPaymentService mWalletPaymentService) {
		this.mWalletPaymentService = mWalletPaymentService;
	}

	public HDFCPaymentService getHdfcPaymentService() {
		return hdfcPaymentService;
	}

	public void setHdfcPaymentService(HDFCPaymentService hdfcPaymentService) {
		this.hdfcPaymentService = hdfcPaymentService;
	}

}
