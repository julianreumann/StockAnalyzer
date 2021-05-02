package stockanalyzer.ctrl;

import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;
import yahoofinance.Stock;
import stockanalyzer.exception.yahooApiException;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.*;
import java.util.Calendar;


public class Controller {

    YahooFinance yahooFinance = new YahooFinance();


    public void process(String ticker) throws yahooApiException, IOException {
        System.out.println("Start process");
        Stock stock = null;
        Calendar stockcal = Calendar.getInstance();
        stockcal.add(Calendar.WEEK_OF_MONTH,-2);
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
                    stock = yahoofinance.YahooFinance.get("");
                    stock.getHistory(stockcal,Interval.DAILY).forEach(System.out::println);
                    System.out.println("Data: "+ countData(stock));
                    System.out.println("Min: " + min(stock));
                    System.out.println("Max: " + max(stock));
                    System.out.println("Average: "+ average(stock));
                    break;
                case "TSLAH":
                    stock = yahoofinance.YahooFinance.get("TSLA");
                    stock.getHistory(stockcal,Interval.DAILY).forEach(System.out::println);
                    System.out.println("Data: "+ countData(stock));
                    System.out.println("Min: " + min(stock));
                    System.out.println("Max: " + max(stock));
                    System.out.println("Average: "+ average(stock));
                    break;
                case "GOOGH":
                    stock = yahoofinance.YahooFinance.get("GOOG");
                    stock.getHistory(stockcal, Interval.DAILY).forEach(System.out::println);
                    System.out.println("Data: "+ countData(stock));
                    System.out.println("Min: " + min(stock));
                    System.out.println("Max: " + max(stock));
                    System.out.println("Average: "+ average(stock));
                    break;
                default:
                    System.out.println("ERROR");
            }
        } catch (yahooApiException | IOException | NullPointerException e) {
            throw new yahooApiException("failed " + e.getMessage());
        }
    }

    public long countData(Stock stock) throws IOException {

        return stock.getHistory().size();

    }

    public double average(Stock stock) throws IOException {

        return stock.getHistory().stream().mapToDouble(q->q.getClose().doubleValue()).average().orElse(0.0);
    }

    public double min(Stock stock) throws IOException {

        return stock.getHistory().stream().mapToDouble(q->q.getClose().doubleValue()).average().orElse(0.0);
    }

    public double max(Stock stock) throws IOException {

        return stock.getHistory().stream().mapToDouble(q->q.getClose().doubleValue()).average().orElse(0.0);
    }

    public Object getData(String searchString) throws yahooApiException {
        try {
            List<String> ticker = Collections.singletonList(searchString);
            YahooResponse response = yahooFinance.getCurrentData(ticker);
            QuoteResponse quotes = response.getQuoteResponse();
            quotes.getResult().forEach(quote -> System.out.println(quote.getShortName() + ";" + quote.getAsk() + ";" + quote.getBid() + " " + quote.getRegularMarketPreviousClose()));
            return null;
        } catch (yahooApiException e) {
            throw new yahooApiException("Error in getting Data " + e.getMessage());
        }

    }


    public void closeConnection() {

    }
}
