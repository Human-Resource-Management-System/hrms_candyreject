
package service_interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.input.output.EmployeeLeaveInputModel;

public interface MailServiceInterface {

	boolean sendOtpMail(String to, String otp);

	public void sendOfferEmail(HttpServletRequest request, HttpServletResponse response, models.OfferModel offerModel,
			String to) throws Exception;

	public void sendLeaveRequestMail(HttpServletRequest request, HttpServletResponse response,
			EmployeeLeaveInputModel leave, String managerEmail, String hrEmail) throws Exception;

	public void sendPaySlipMail(HttpServletRequest request, HttpServletResponse response,
			models.input.output.EmployeePayRollOutputModel payRollOutput, String email) throws Exception;

}