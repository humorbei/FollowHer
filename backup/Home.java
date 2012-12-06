package ebay.followher;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;


import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class Home extends Activity {
	/** Called when the activity is first created. */

	GLSurfaceView glView;

	GLRenderer renderer;
	
	public static Home master;
	
	public static float xpos = -1;
	public static float touchTurn = 0;
	
	private static final int MENU_CHOOSE_PHOTO = 1;
	private static final int MENU_GET_ITEM = 2;
	private static final int MENU_EXIT = 3;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		if (master != null) {
            copy(master);
        }
		
		super.onCreate(savedInstanceState);

		//	����ͼƬ
		LoadImg.loadi(getResources());
		
		//����Assets�ļ����µ��ļ�
		new LoadAssets(getResources());
		
		glView = new GLSurfaceView(this);

		renderer = new GLRenderer();

		glView.setRenderer(renderer);

		setContentView(glView);
	}
	
    private void copy(Object src) {
        try {
            // ����һ�����飬���а���Ŀǰ�����ĵ������ֶε�Filed����
            Field[] fs = src.getClass().getDeclaredFields();
            // ����fs����
            for (Field f : fs) {
                // �����������ϰ���־��ֵ����־����Ϊfalse��ʹ���ʼ�飬����Ϊtrue��������á�
                f.setAccessible(true);
                // ��ȡ����ֵȫ��װ�뵱ǰ����
                f.set(this, f.get(src));
            }
        } catch (Exception e) {
            // �׳�����ʱ�쳣
            throw new RuntimeException(e);
        }
    }

	// ��дonPause()
    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }

    // ��дonResume()
    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }

    // ��дonStop()
    @Override
    protected void onStop() {
        super.onStop();
    }
    
    public boolean onTouchEvent(MotionEvent me) {

        // ������ʼ
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            // ���水�µĳ�ʼx,yλ����xpos,ypos��
            xpos = me.getX();
            return true;
        }
        // ��������
        if (me.getAction() == MotionEvent.ACTION_UP) {
            // ����x,y����ת�Ƕ�Ϊ��ʼֵ
            xpos = -1;
            touchTurn = 0;
            return true;
        }

        if (me.getAction() == MotionEvent.ACTION_MOVE) {
            // ����x,yƫ��λ�ü�x,y���ϵ���ת�Ƕ�
            float xd = me.getX() - xpos;
            xpos = me.getX();
            // ��x��Ϊ����������������Ϊ��������������Ϊ��
            touchTurn = xd / -100f;
            return true;
        }

        // ÿMoveһ�����ߺ���
        try {
            Thread.sleep(15);
        } catch (Exception e) {
            // No need for this...
        }

        return super.onTouchEvent(me);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    menu.add(0, MENU_GET_ITEM, 0, "Get Clothes");
	    menu.add(1, MENU_CHOOSE_PHOTO, 1, "Idol Yourself");
	    menu.add(2, MENU_EXIT, 2, "EXIT");
//	    SubMenu animMenu = menu.addSubMenu("Get Clothes");
//		int menuItem = 101;
//		for (SkinClip clip : masterNinja.getSkinClipSequence()) {
//			animMenu.add(0, menuItem++, 1, "Anim: " + clip.getName());
//		}
	    return true;
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case MENU_EXIT:
		        this.finish();
		    case MENU_GET_ITEM:
		    	Intent BrowseItem = new Intent(Home.this, BrowseItem.class);
		    	startActivity(BrowseItem);
		        return true;
		    case MENU_CHOOSE_PHOTO:
		    	Intent getPhoto = new Intent(Home.this, GetPhoto.class);
		    	startActivity(getPhoto);
		    	return true;
	    }
//	    if (item.getItemId() > 100) {
//	        animation = item.getItemId() - 100;
//	        return true;
//	    }
	    return false;
	}
	
}

// ����ͼƬ��
class LoadImg {
	public static Bitmap bmp;

	public static void loadi(Resources res) {
		bmp = BitmapFactory.decodeResource(res, R.drawable.soilder);
	}
}

// ����assets��
class LoadAssets {
	public static Resources res;

	public LoadAssets(Resources resources) {
		res = resources;
	}

	public static InputStream loadf(String fileName) {
		AssetManager am = res.getAssets();
		try {
			return am.open(fileName, AssetManager.ACCESS_UNKNOWN);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
