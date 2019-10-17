package com.cn.ql.frame.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/6.
 */

public class WebViewUtils {

    public static void loadHtml(WebView webView, String content) {
        loadHtml(webView, content, 0);
    }

    @SuppressLint("JavascriptInterface")
    public static void loadHtml(final WebView webView, String content, int type) {

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:MyApp.resize(document.body.getBoundingClientRect().height)");
                super.onPageFinished(view, url);
            }
        });

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        webView.setBackgroundColor(0); // 设置背景色
        Drawable drawable = webView.getBackground();
        if (drawable != null) {
            drawable.setAlpha(0); // 设置填充透明度 范围：0-255
        }
        String html = delHTMLTag(content);
        if (type == 1){
            html = delHTMLTag(content, type);
        }
        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

        webView.addJavascriptInterface(webView.getContext(), "App");
    }

    @JavascriptInterface
    public static void resize(final Activity act, final float height, final WebView webView) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                webView.setLayoutParams(new LinearLayout.LayoutParams(act.getResources().getDisplayMetrics().widthPixels, (int) (height * act.getResources().getDisplayMetrics().density)));
            }
        });
    }

    private static String delHTMLTag(String htmlStr, int type) {
        htmlStr = "<header><meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no'></header><html> \n" +
                "<head> \n" +
                "<style type=\"text/css\"> \n" +
                "body {font-size:15px;}\n" +
                "</style> \n" +
                "</head> \n" +
                "<body><script type='text/javascript'>window.onload = function(){\n" +
                "var $img = document.getElementsByTagName('img');\n" +
                "var maxwidth = 300.000000;for(var p in  $img){\n" +
                "if($img[p].width > maxwidth){\n" +
                "$img[p].style.width = '100%';\n" +
                "}\n" +
                "$img[p].style.height ='auto'\n" +
                "}\n" +
                "}</script>"
                +htmlStr+
                "</body></html>";
        return htmlStr; // 返回文本字符串
    }

    private static String delHTMLTag(String htmlStr) {
        //htmlStr = "<div style=\\\"word-wrap:break-word;word-break:break-all;\\\">"+htmlStr+"</div><script>var pic = document.getElementsByTagName(\\\"img\\\");for (var i = 0; i < pic.length; i++) {pic[i].style.maxWidth = \\\"100%%\\\";pic[i].style.maxHeight = \\\"100%%\\\";};</script>";
        String img = "<img[^>]+>";
        Pattern imgp = Pattern.compile(img);
        Matcher html = imgp.matcher(htmlStr);
        while (html.find()) {
            Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(
                    html.group());
            while (m.find()) {
                String mgroup = m.group(1);
                String ig = "<img style=\"box-sizing: border-box; width: 100%; height: auto !important;\" src=\""
                        + mgroup + "\" />";
                htmlStr = htmlStr.replace(html.group(), ig);
            }
        }
        return htmlStr; // 返回文本字符串
    }

    public static void startExplorer(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(path);
        intent.setData(content_url);
        context.startActivity(intent);
    }
}
