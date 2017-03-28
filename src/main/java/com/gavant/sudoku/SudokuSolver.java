package com.gavant.sudoku;

/**
 * This class aims to solve the sudoku puzzle by using the backtracking algorithm.  
 * @author cyril
 *
 */
public class SudokuSolver {
	
	/**
	 * Sudoku which needs to be solved
	 */
	private Sudoku currentSudoku;
		
	public SudokuSolver() {
	}
	
	/**
	 * This is the method which solves the sudoku passed in parameter 
	 * @param sudoku: given sudoku 
	 */
	public void solveSudoku(Sudoku sudoku){
		currentSudoku = sudoku;

		//Finding the first available cell 
		//We assume that the board contains at least 1 available cell (not fully occupied) 
		SudokuCell firstAvailableCell = findNextAvailableCell();

		//Solving the sudoku, starting with the first available cell
		solve(firstAvailableCell.getRow(), firstAvailableCell.getCol());
	}
	
	/**
	 * Method which solves the sudoku by position (row,col)
	 * @param row
	 * @param col
	 * @return true when solved
	 */
	private boolean solve(int row, int col ){
		
		//Sudoku solved
		if(isBoardFull()){
			return true;
		}
		
		//Testing value from 1 to 9
		for(int value = 1; value <= 9; value++){
			if(isValidPosition(row, col, value)){
				//Setting value
				currentSudoku.setValue(row, col, value);
								
				SudokuCell nextAvailableCell = findNextAvailableCell();
				
				//If there is no more available cells that means the sudoku is solved.
				if(nextAvailableCell == null){
					return true;
				}
				
				//recursive call
				if(solve(nextAvailableCell.getRow(), nextAvailableCell.getCol())){
					return true;
				}else{
					//Undo value
					currentSudoku.setValue(row, col, 0);
				}
				
			}
		}
		
		return false;
		
	}
	

	/**
	 * Check if the board is full (doesn't contain any Blank character)
	 * @return
	 */
	public boolean isBoardFull(){
	      for( int row = 0; row < 9; row++ ){
	          for( int col = 0; col < 9; col++ ){
	             if( currentSudoku.getValue(row, col) == Sudoku.BLANK_INT_VALUE ){
	            	 return false;
	             }
	          }
	      }
	      return true;
	}
	
	/**
	 * This method checks if the position is valid based on sudoku restriction (unique value by row/col/3x3box).
	 * 
	 * @param row
	 * @param col
	 * @param value
	 * @return true if the position is valid.
	 */
	private boolean isValidPosition(int row, int col, int value){
		
		//The cell is not available, contains already a value from 1-9
		if(currentSudoku.getValue(row, col) !=  Sudoku.BLANK_INT_VALUE ){
			return false;
		}
		
		//The value exists in the same row.
		if(sameValueInRow(row, value)){
			return false;
		}
		
		//The value exists in the same column.
		if(sameValueInColumn(col, value)){
			return false;
		}

		//The value exists in the 3x3 box
		if(sameValueIn3x3Box(row,col,value)){
			return false;
		}
		
		return true;
	}
	
	/**
	 * The method checks if the value passed in parameter exists in the given row.
	 * 
	 * @param row
	 * @param value
	 * @return true if the value exists in the row
	 */
	private boolean sameValueInRow(int row, int value){
		for (int col = 0; col < 9; col++) {
			if (currentSudoku.getValue(row, col) == value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The method checks if the value passed in parameter exists in the given column.
	 * @param col
	 * @param value
	 * @return true if the value exists in the column
	 */
	private boolean sameValueInColumn(int col, int value){
		for (int row = 0; row < 9; row++){
			if (currentSudoku.getValue(row, col) == value){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * The method checks if the value passed in parameter exists in its 3x3 box.
	 * @param row
	 * @param col
	 * @param value
	 * @return true if the value is in its 3x3 box
	 */
	private boolean sameValueIn3x3Box(int row, int col, int value){
		int firstRowIndex = (row / 3) * 3 ;
		int firstColIndex = (col / 3) * 3 ;
		
		for (int rowInBox = 0; rowInBox < 3; rowInBox++){
			for (int colInBox = 0; colInBox < 3; colInBox++){
				if (currentSudoku.getValue(firstRowIndex + rowInBox, firstColIndex + colInBox) == value){
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * The method finds the next available cell (not occupied)
	 * @return the next available cell
	 */
	private SudokuCell findNextAvailableCell() {

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (currentSudoku.getValue(row, col)  == 0) {
					return currentSudoku.getCell(row, col);
				}
			}
		}
		return null;
	}
		

}
