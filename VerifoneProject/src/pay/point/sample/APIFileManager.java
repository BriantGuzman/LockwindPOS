
package pay.point.sample;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

import java.io.FileInputStream;
import javax.print.*;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.io.InputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;


public class APIFileManager {

	private String filename;

	private File file;
	private Scanner inputFile;
	private FileWriter  output;
	private PrintWriter outputFile;

	public APIFileManager() {
		this("APIFileManagerDemo.txt");
	}
	public APIFileManager(String filename) {
		setFileName(filename);		
	}
	
	private void setFileName(String filename) { this.filename = filename; }
	public String getFileName() {  return this.filename; }
	private void setFile() { file = new File( this.getFileName() ); }
	private File getFile() {  return file; }
	
	private void setInputFile() {
		try {  inputFile = new Scanner( this.getFile() ); }
		catch(FileNotFoundException e) { System.out.println("PrintReceipt: Error: file not found"); }
	}
	public Scanner getInputFile() {
		if(file == null) { System.out.println("PrintReceipt: Error: file not found."); }
		else { } return inputFile;
	}
	private void setOutput() {
		try { output = new FileWriter(this.getFileName());
		}catch(IOException e) {
			System.out.println("APIFileManager: Error: file not found");
			System.out.println(e.toString()); }
	}
	private void setOutputAppend() {
		try { output = new FileWriter(this.getFileName(),true);
		}catch(IOException e) {
			System.out.println("APIFileManager: Error: file not found");
			System.out.println(e.toString()); }
	}
	
	private void setOutputFile() { 
		try {  outputFile = new PrintWriter( this.getFileName() ); }
		catch(FileNotFoundException e) {
			System.out.println("APIFileManager: Error: file not found");
			System.out.println(e.toString()); }
	}


	public void openRead() { setFile(); setInputFile(); }
	public void openWrite() { setOutput(); setOutputFile(); }
	public void openWriteAppend() { setOutputAppend(); setOutputFile(); }
	
	public void closeRead() { inputFile.close(); }
	public void closeWrite() { outputFile.close(); }
	
	public String getFirstLine() { 
		openRead();
		String temp = getInputFile().nextLine();
		closeRead();
		return temp;
	}
	
	public void writeLine(String line) { 
		openWrite();
		outputFile.println(line);
		closeWrite();
	}
	public void writeLineAppend(String line) { 
		openWriteAppend();
		outputFile.println(line);
		closeWrite();
	}

	
	public void incrementInteger() { 
		int x = 0;

		this.openRead();
		x = Integer.parseInt(this.getFirstLine() );
		x++;
		this.closeRead();
		
		this.openWrite();
		writeLine(String.valueOf(x));
		closeWrite();
	}
	
	public static void main(String[] args) {
		
		APIFileManager test = new APIFileManager("invoice_number.txt");
		System.out.println(test.getFirstLine() );
		//test.incrementInteger();
		test.writeLineAppend("Hello World");
	}

	
}
