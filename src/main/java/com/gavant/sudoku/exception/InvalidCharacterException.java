package com.gavant.sudoku.exception;

public class InvalidCharacterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCharacterException() {
	}

	public InvalidCharacterException(String message) {
		super(message);
	}


}
