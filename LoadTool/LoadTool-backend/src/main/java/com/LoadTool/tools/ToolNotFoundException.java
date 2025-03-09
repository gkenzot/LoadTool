package com.LoadTool.tools;

public class ToolNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 637432567219387603L;

	public ToolNotFoundException(Long id) {
        super("Ferramenta com ID " + id + " não encontrada.");
    }
}