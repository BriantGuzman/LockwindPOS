package pay.point.sample;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;
import javax.print.event.PrintJobAdapter;
import javax.print.DocPrintJob;
import javax.print.event.PrintJobEvent;


public class PrintJobWatcher {
    boolean done = false;
    
    PrintJobWatcher(DocPrintJob job) {
        job.addPrintJobListener(new PrintJobAdapter() {
            
            public void printJobCanceled(PrintJobEvent pje) {allDone();}
            public void printJobCompleted(PrintJobEvent pje) {allDone();}
            public void printJobFailed(PrintJobEvent pje) {allDone();}
            
            public void printJobNoMoreEvents(PrintJobEvent pje) {allDone();}
            void allDone() {synchronized (PrintJobWatcher.this) {done = true;
                System.out.println("Printing done ...");PrintJobWatcher.this.notify();}}});}
    
    public synchronized void waitForDone() {
        try {
            while (!done) {wait();}}
        catch (InterruptedException e){}}}
//-------------------------------------------------------------------------------CLASS PRINT RECEIPT END

