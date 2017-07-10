package com.izanpin.common.util;

/**
 * Created by pengyuancong on 2017/2/27.
 */
public class Html {
    public static String htmlEscape(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        input = input.replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll(" ", "&nbsp;");
        input = input.replaceAll("“", "&ldquo;");
        input = input.replaceAll("”", "&rdquo;");
        input = input.replaceAll("…", "&hellip;");
        input = input.replaceAll("'", "&#39;");   //IE暂不支持单引号的实体名称,而支持单引号的实体编号,故单引号转义成实体编号,其它字符转义成实体名称
        input = input.replaceAll("\"", "&quot;"); //双引号也需要转义，所以加一个斜线对其进行转义
        input = input.replaceAll("\n", "<br/>");  //不能把\n的过滤放在前面，因为还要对<和>过滤，这样就会导致<br/>失效了
        return input;
    }

    public static String htmlUnescape(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        input = input.replaceAll("&amp;", "&");
        input = input.replaceAll("&lt;", "<");
        input = input.replaceAll("&gt;", ">");
        input = input.replaceAll("&nbsp;", " ");
        input = input.replaceAll("&ldquo;", "“");
        input = input.replaceAll("&rdquo;", "”");
        input = input.replaceAll("&hellip;", "…");
        input = input.replaceAll("&#39;", "'");
        input = input.replaceAll("&quot;", "\"");
        input = input.replaceAll("<br/>", "\n");
        input = input.replaceAll("<br />", "\n");
        input = input.replaceAll("<br>", "");

        input = input.replaceAll("<p>", "");
        input = input.replaceAll("</p>", "");
        input = input.replaceAll("<img.*>", "");
        input = input.replaceAll("<b>", "");
        input = input.replaceAll("</b>", "");
        return input;
    }
}
