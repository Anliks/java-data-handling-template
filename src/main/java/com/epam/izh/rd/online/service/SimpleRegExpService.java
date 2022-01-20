package com.epam.izh.rd.online.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        File file = new File("src\\main\\resources\\sensitive_data.txt");
      String str = "";
        String ret = "";
      try{
          str = new String(Files.readAllBytes(Paths.get(file.toURI())));
          Pattern pattern = Pattern.compile("(\\s\\d{4})(\\s\\d{4})(\\s\\d{4})(\\s\\d{4})");
          Matcher matcher = pattern.matcher(str.trim());
          if (matcher.find()) {
            ret = matcher.replaceAll("$1 **** ****$4");
          }
      }catch (IOException e){e.printStackTrace();}
        return ret;
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        File file = new File("src\\main\\resources\\sensitive_data.txt");
        String str;
        String ret="";
        try{
            str = new String(Files.readAllBytes(Paths.get(file.toURI())));
            Pattern pattern = Pattern.compile("\\$.payment_amount.");
            Matcher matcher = pattern.matcher(str.trim());
            String one = Integer.toString((int)paymentAmount);
            ret = matcher.replaceAll(one);
            Pattern pattern1 = Pattern.compile("\\$.balance.");
            Matcher matcher1 = pattern1.matcher(ret.trim());
            one=Integer.toString((int) balance);
            ret = matcher1.replaceAll(one);
        }catch (IOException e){e.printStackTrace();}

        return ret;
    }
}
