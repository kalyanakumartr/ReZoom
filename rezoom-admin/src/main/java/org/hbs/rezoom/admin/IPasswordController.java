package org.hbs.rezoom.admin;

import org.hbs.rezoom.bean.PasswordFormBean;
import org.hbs.rezoom.bean.UserFormBean;
import org.hbs.rezoom.bean.path.IPathAdmin;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public interface IPasswordController extends IPathAdmin
{
	@PostMapping
	@RequestMapping(value = CHANGE_PASSWORD)
	ResponseEntity<?> changePassword(PasswordFormBean password);

	@PostMapping
	@RequestMapping(value = FORGOT_PASSWORD)
	ResponseEntity<?> forgotPassword(UserFormBean user);

	@PostMapping
	@RequestMapping(value = RESET_PASSWORD, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> resetPassword(PasswordFormBean pfBean);

}