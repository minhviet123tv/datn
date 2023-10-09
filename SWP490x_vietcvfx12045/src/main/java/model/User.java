package model;

/*
 * Dùng đối tượng user để xử lý đơn giản tài khoản nói chung
 * Còn xử lý đa dạng thì dùng AccountUser
 */
public class User {
	private String email;
	private String password;
	private String message = "";
	
	public User() {
	}

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean check() {

		if (!email.matches("\\w+@\\w+\\.\\w+") && !email.matches("\\w+@\\w+\\.\\w+\\.\\w+") && !email.matches("\\w+\\.\\w+@\\w+\\.\\w+") && !email.matches("\\w+\\.\\w+@\\w+\\.\\w+\\.\\w+")) {
			message = "Email phải có cấu trúc: text@text.text hoặc text.text@text.text hoặc text.text@text.text.text";
			return false;
		}
		
		if (password.length() < 8 || password.length() > 255) {
			message = "Mật khẩu phải từ 8-255 ký tự";
			return false;
		} else if (password.matches("\\w*\\s+\\w*")) {
			message = "Mật khẩu không được chứa KHOẢNG TRỐNG!";
			return false;
		}

		message = "Email & Password đã đúng cú pháp yêu cầu!";
		return true;
	}
	
	
}
