package ebay.followher;

import Helpers.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowseItem extends Activity {

	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browseitem);

		initializeWebView();

	}

	private void initializeWebView() {
		// TODO Auto-generated method stub
		webView = (WebView) this.findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.requestFocus();
		webView.loadUrl(Constants.EBAY_ITEM_PAGE);
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					// title.setText("�������")��
				} else {
					// title.setText("������.......");
				}
			}
		});
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, android.net.http.SslError error) {
				// ��д�˷���������webview����https����
				handler.proceed();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCoder, KeyEvent event) {
//		if(webView.canGoBack() == false && keyCoder == KeyEvent.KEYCODE_BACK)
//			return false;
//		
//		if (webView.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
//			webView.goBack(); // goBack()��ʾ����webView����һҳ��
//			return true;
//		}
//		return false;
		Intent i = new Intent(BrowseItem.this, Home.class);
    	startActivity(i);
		return false;
	}

}
