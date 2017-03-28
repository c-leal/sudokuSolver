package com.gavant.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gavant.sudoku.exception.InvalidCharacterException;
import com.gavant.sudoku.exception.SudokuFileNotFoundException;

/**
 * This is a JUnit test of the SudokuSolver.
 * It tests the common case as well as some exceptions (file not found, invalid character in the file, unsolvable puzzle)    
 * @author cyril
 *
 */
public class SudokuSolverTest {
	private static final SudokuSolver s = new SudokuSolver();

	@Test
	public void testSolvePuzzleWithSuccess(){
		Sudoku s1 = new Sudoku();
		s1.initBoard("puzzle1.txt");
		s.solveSudoku(s1);
		
		//Puzzle solved
		assertTrue(s.isBoardFull());

		//Solution file created
		String filename = s1.createFileFromBoard();
		 
		assertEquals(filename,"puzzle1.sln.txt");
	}

	@Test(expected = SudokuFileNotFoundException.class)
	public void testSolvePuzzleWithFileNotFound(){
		Sudoku s2 = new Sudoku();
		s2.initBoard("unknownFile.txt");
	}
	
	@Test(expected = InvalidCharacterException.class)
	public void testSolvePuzzleWithInvalidCharacter(){
		Sudoku s3 = new Sudoku();
		s3.initBoard("invalidCharacterPuzzle.txt");
	}
	
	@Test
	public void testUnSolvablePuzzle(){
		Sudoku s4 = new Sudoku();
		s4.initBoard("unsolvablePuzzle.txt");
		s.solveSudoku(s4);
		
		//Puzzle unsolved
		assertTrue(!s.isBoardFull());
	}
	


}

