package com.fullscore.thread;

public class ReportThread extends Thread {
    public synchronized void run() {
        System.out.println("Generating report in background thread");
    }
}