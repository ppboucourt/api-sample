package co.tmunited.bluebook.web;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Month;
import java.time.YearMonth;

public class Test {

    protected static int total = 10;
    public void call() throws IOException {
        FileWriter fw = new FileWriter("");
        int h = 6;
        StringBuilder sb = new StringBuilder("buffering");
        sb.rep
        total++;

        System.out.println(total);
        Double d = null;
    }

    public static void yearMonth() {
        YearMonth ye1 = YearMonth.now();
        YearMonth ye2 = YearMonth.of(2016, Month.FEBRUARY);

        System.out.println(ye1 + " " + ye2);
    }

    public static void main(String[] args) {
        yearMonth();
    }
}


