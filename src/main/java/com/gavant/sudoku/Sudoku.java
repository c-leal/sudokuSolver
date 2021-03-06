package com.gavant.sudoku;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.gavant.sudoku.exception.InvalidCharacterException;
import com.gavant.sudoku.exception.SudokuFileNotFoundException;


/**
 * This class represents the sudoku board.
 * It contains some methods to fill the board from a file or create a file from the board.
 * 
 * @author cyril
 *
 */

public class Sudoku {

	public static final int BLANK_INT_VALUE = 0;
	public static final char BLANK_CHAR_VALUE = 'X';
	
	private int[][] board;
	private String fileNameWithoutExtension;

	
	public Sudoku() {		
	}
	
	public int getValue(int row, int col){
		return board[row][col];
	}
	
	public void setValue(int row, int col, int value){
		board[row][col] = value;
	}
	
	public SudokuCell getCell(int row, int col){
		return new SudokuCell(row, col, board[row][col]);
	}
	
	
	/**
	 * This method fills the board from a given file.
	 * 
	 * Note: according OO/SOLID principles, I am not sure if initBoard and createFileFromBoard methods should be declared here. 
	 * If we want to keep the "Single Responsibility Principle", maybe a SudokuHelper class with those methods makes more sense (need to discuss).
	 * 
	 * @param fileName: given file used to populate the board
	 */
	public void initBoard(String fileName){
		
		//Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		
		//If the file doesn't not exist
		if (classLoader.getResource(fileName) == null){
			throw new SudokuFileNotFoundException("File " + fileName + " not found");
		}
		
		File file = new File(classLoader.getResource(fileName).getFile());		
		
		fileNameWithoutExtension = file.getName().substring(0, file.getName().lastIndexOf(".")); 

		try (Scanner scanner = new Scanner(file)) {
			board = new int[9][9];
			int row = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.length() == 9){
					
					//Parse the file and fill the board 
					for (int col = 0; col < 9; col++) {
						Integer intVal = null;
						char charVal =line.charAt(col);
					
						//Converting X by 0, more convenient to manage array of integers 
						if(charVal == BLANK_CHAR_VALUE){
							intVal = BLANK_INT_VALUE;
						}else if(Character.isDigit(charVal)){
							intVal = Character.getNumericValue(charVal);
						}else{
							throw new InvalidCharacterException("Invalid character '" + charVal + "' in the file " + file.getName() );
						}
						
						board[row][col]=intVal;
					}
					row++;
				}
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	  }	


	/**
	 * This method creates a file of the current board.
	 * The outputfile will have the format {0}.sln.txt where {0} is the name of the original input file. 
	 * @return solution file name generated by the method
	 *  
	 * Note: according OO/SOLID principles, I am not sure if initBoard and createFileFromBoard methods should be declared here. 
	 * If we want to keep the "Single Responsibility Principle", maybe a SudokuHelper class wich contains those methods makes more sense (need to discuss).
	 * 
	 */
	public String createFileFromBoard(){
		String solutionFileName = "";
		try {
			StringBuilder builder = new StringBuilder();
			boolean puzzleSolved = true;
			
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					if(board[row][col] == BLANK_INT_VALUE){
						builder.append(BLANK_CHAR_VALUE);
						puzzleSolved = false;
					}else{
						builder.append(board[row][col]);
					}
				}
				builder.append("\r\n");
			}
			
			//If the puzzle is unsolvable, adding a note to the file 
			if(puzzleSolved == false){
				builder.append("\r\n This puzzle cannot be solved");
			}
			
			//Create the outputfile with the format {0}.sln.txt where {0} is the name of the original input file. 
			solutionFileName = fileNameWithoutExtension + ".sln.txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(solutionFileName));
			writer.write(builder.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return solutionFileName;
	}	

	/**
	 * This method print the board in the console (optional method)
	 */
	public void printBoard(){
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				System.out.print(board[row][col]);
			}
			System.out.println("");
		}
		System.out.println("******************************");	      
	}
	


}
