package com.gavant.sudoku.exception;

/**
 * Runtime exception thrown when the sudoku file is not found
 * @author cyril
 *
 */
public class SudokuFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SudokuFileNotFoundException() {
	}

	public SudokuFileNotFoundException(String message) {
		super(message);
	}

}
