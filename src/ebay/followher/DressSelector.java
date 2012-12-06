package ebay.followher;

import java.io.File;
import java.io.FileNotFoundException;

import Helpers.ClothesAdder;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class DressSelector extends Activity {

	public String clothesType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtiy_dressselector);

		clothesType = getIntent().getStringExtra("clothes");

		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, 1);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent(DressSelector.this, Home.class);
			startActivity(i);
			this.finish();
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Log.e("uri", uri.toString());
			Log.e("uri", uri.getPath());
			ContentResolver cr = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(cr
						.openInputStream(uri));
				ImageView imageView = (ImageView) findViewById(R.id.dress_image);
				imageView.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(), e);
			}

			// get the picture path
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(uri, projection, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();

			if (clothesType.equals("upper")) {
				ClothesAdder.upper.objPath = cursor.getString(column_index)
						.split("_sample")[0] + ".3DS";
			}
			if (clothesType.equals("under")) {
				ClothesAdder.under.objPath = cursor.getString(column_index)
						.split("_sample")[0] + ".3DS";
			}
			if (clothesType.equals("shoes")) {
				ClothesAdder.shoes.objPath = cursor.getString(column_index)
						.split("_sample")[0] + ".3DS";
			}

			Button btn = (Button) findViewById(R.id.dress_commit);
			btn.setText("Commit and Add Texture");
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (clothesType.equals("upper")) {
						Intent i = new Intent(DressSelector.this, TextureSelector.class);
						i.putExtra("clothes", "upper");
						startActivity(i);
						DressSelector.this.finish();
					}
					if (clothesType.equals("under")) {
						Intent i = new Intent(DressSelector.this, TextureSelector.class);
						i.putExtra("clothes", "under");
						startActivity(i);
						DressSelector.this.finish();
					}
					if (clothesType.equals("shoes")) {
						Intent i = new Intent(DressSelector.this, TextureSelector.class);
						i.putExtra("clothes", "shoes");
						startActivity(i);
						DressSelector.this.finish();
					}
				}
			});
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
