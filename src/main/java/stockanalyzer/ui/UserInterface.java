package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import stockanalyzer.download.Downloader;
import stockanalyzer.download.ParallelDownloader;
import stockanalyzer.download.SequentialDownloader;
import stockanalyzer.exception.yahooApiException;
import stockanalyzer.ctrl.Controller;

public class UserInterface {

    private Controller ctrl = new Controller();

    public void getDataFromCtrl1() {
        try {
            ctrl.process("AMZN");
        } catch (yahooApiException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromCtrl2() {
        try {
            ctrl.process("TSLA");
        } catch (yahooApiException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromCtrl3() {
        try {
            ctrl.process("GOOG");
        } catch (yahooApiException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromCtrl4() {
        try {
            ctrl.process("AMZNH");
        } catch (yahooApiException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromCtrl5() {
        try {
            ctrl.process("TSLAH");
        } catch (yahooApiException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromCtrl6() {
        try {
            ctrl.process("GOOGH");
        } catch (yahooApiException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromCtrl7Down() {
        long first;
        long second;
        Downloader pDown = new ParallelDownloader();
        SequentialDownloader sqDown = new SequentialDownloader();

        List<String> listATX = Arrays.asList("OMV.VI",
                "EBS.VI", "DOC.VI", "SBO.VI", "RBI.VI", "VIG.VI", "TKA.VI", "VOE.VI", "FACC.VI", "ANDR.VI", "VER.VI",
                "WIE.VI", "CAI.VI", "BG.VI", "POST.VI", "LNZ.VI", "UQA.VI", "SPI.VI", "ATS.VI", "IIA.VI");
        first = System.currentTimeMillis();
        ctrl.downloadTickers(listATX, sqDown);
        second = System.currentTimeMillis();
        System.out.printf("Sequent. Time: %dms\n",  first-second);
        first = System.currentTimeMillis();
        ctrl.downloadTickers(listATX, pDown);
        second = System.currentTimeMillis();
        System.out.printf("Parall. Time: %dms\n",  first-second);
    }


    public void start() {
        Menu<Runnable> menu = new Menu<>("User Interfacx");
        menu.setTitel("Wählen Sie aus:");
        menu.insert("a", "Choice 1 AMZN", this::getDataFromCtrl1);
        menu.insert("b", "Choice 2 TSLA", this::getDataFromCtrl2);
        menu.insert("c", "Choice 3 GOOG", this::getDataFromCtrl3);
        menu.insert("d", "Choice 4 History AMZN:", this::getDataFromCtrl4);
        menu.insert("e", "Choice 5 History TSLA:", this::getDataFromCtrl5);
        menu.insert("f", "Choice 6 History GOOG:", this::getDataFromCtrl6);
        menu.insert("g", "Download Tickers", this::getDataFromCtrl7Down);
        menu.insert("q", "Quit", null);
        Runnable choice;
        while ((choice = menu.exec()) != null) {
            choice.run();
        }
        ctrl.closeConnection();
        System.out.println("Program finished");
    }


    protected String readLine() {
        String value = "\0";
        BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = inReader.readLine();
        } catch (IOException e) {
        }
        return value.trim();
    }

    protected Double readDouble(int lowerlimit, int upperlimit) {
        Double number = null;
        while (number == null) {
            String str = this.readLine();
            try {
                number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
                System.out.println("Please enter a valid number:");
                continue;
            }
            if (number < lowerlimit) {
                System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
                System.out.println("Please enter a lower number:");
                number = null;
            }
        }
        return number;
    }
}
