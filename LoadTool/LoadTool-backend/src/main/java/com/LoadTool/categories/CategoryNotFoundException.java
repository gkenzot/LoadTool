package com.LoadTool.categories;

public class CategoryNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6693045414907815359L;

	public CategoryNotFoundException(Long id) {
        super("Categoria com ID " + id + " não encontrada.");
    }
}