package stockanalyzer.ctrl;

import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;
import yahoofinance.Stock;
import stockanalyzer.exception.yahooApiException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class Controller  {

    YahooFinance yahooFinance = new YahooFinance();


    public void process(String ticker) throws yahooApiException {
        System.out.println("Start process");
        Stock stock = null;
        try {
            switch (ticker) {
                case "AMZN":
                    getData("AMZN");
                    break;
                case "TSLA":
                    getData("TSLA");
                    break;
                case "GOOG":
                    getData("GOOG");
                    break;
                case "AMZNH":
                    stock = yahoofinance.YahooFinance.get("AMZN");
                    stock.getHistory().forEach(System.out::println);
                    break;
                case "TSLAH":
                    stock = yahoofinance.YahooFinance.get("TSLA");
                    stock.getHistory().forEach(System.out::println);
                    break;
                case "GOOGH":
                    stock = yahoofinance.YahooFinance.get("GOOG");
                    stock.getHistory().forEach(System.out::println);
                    ;
                    break;
                default:
                    System.out.println("ERROR");
            }
        } catch (yahooApiException | IOException e){
            throw new yahooApiException("failed "+e.getMessage());

        }
    }


        //TODO implement Error handling

        //TODO implement methods for
        //1) Daten laden
        //2) Daten Analyse


    public Object getData(String searchString) throws yahooApiException {
      try {
          List<String> ticker = Arrays.asList(searchString);
          YahooResponse response = yahooFinance.getCurrentData(ticker);
          QuoteResponse quotes = response.getQuoteResponse();
          quotes.getResult().forEach(quote -> System.out.println(quote.getShortName() + ";" + quote.getAsk() + ";" + quote.getBid() + " " + quote.getRegularMarketPreviousClose()));
          return null;}
          catch (yahooApiException e) {
              throw new yahooApiException("Error in getting Data " + e.getMessage());
      }

    }


    public void closeConnection() {

    }
}
