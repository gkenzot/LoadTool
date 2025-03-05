package com.LoadTool.user;

public class UserNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4477066803246634463L;

	public UserNotFoundException(Long id) {
        super("Usuário com ID " + id + " não encontrado.");
    }

    public UserNotFoundException(String email) {
        super("Usuário com e-mail " + email + " não encontrado.");
    }
}