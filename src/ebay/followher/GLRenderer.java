package ebay.followher;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

public class GLRenderer implements Renderer {
	private World world;
	private FrameBuffer fb;
	private Object3D soilder;
	private String[] texturesName = { "snork" };
	private Light sun;
	private float scale = 2f;
	// ���߶��� ��ز���
	private int an = 2;
	private float ind = 0;
	private boolean stop = false;
	// ֹͣ
	public void stop() {
		stop = true;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		doAnim();
		if (!stop) {
			// ���touchTurn��Ϊ0,��Y����תtouchTure�Ƕ�
			if (Home.touchTurn != 0) {
				// ��ת�������ת��Y�ɸ�������W��ǣ�����˳ʱ�뷽��Ϊ��ֵ��,Ӧ�õ�������һ����Ⱦʱ��
				soilder.rotateY(Home.touchTurn);
				// ��touchTurn��0
				Home.touchTurn = 0;
			}
			// ����ɫ���FrameBuffer
			fb.clear(RGBColor.WHITE);
			// �任�͵ƹ����ж����
			world.renderScene(fb);
			// ����
			world.draw(fb);
			// ��ʾ
			fb.display();
		}else {
            if (fb != null) {
                fb.dispose();
                fb = null;
            }
		}

	}

	/**
	 * ʵ�ֶ����Ĵ���
	 * */
	private void doAnim() {
		// TODO Auto-generated method stub
		// ÿһ֡��0.018f
		ind += 0.018f;
		if (ind > 1f) {
			ind -= 1f;
		}
		// ���ڴ˴�������������ind��ֵΪ0-1(jpct-ae�涨),0��ʾ��һ֡��1Ϊ���һ֡��
		// ����an���������������˼��sub-sequence�����keyframe(3ds��),��Ϊ��һ��
		// �����Ķ���������seq��sub-sequence����������Ϊ2��ʾִ��sub-sequence�Ķ�����
		// ����������Ϊ2�ҾͲ�̫�����ˣ�����������Ч���᲻��Ȼ�������Ҿ�����ʱ����
		// ����Ϊ2
		soilder.animate(ind, an);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		// ���FrameBuffer��ΪNULL,�ͷ�fb��ռ��Դ
		if (fb != null) {
			fb.dispose();
		}
		fb = new FrameBuffer(gl, width, height);

		if (Home.master == null) {
			world = new World();
			world.setAmbientLight(20, 20, 20);
			sun = new Light(world);
			sun.setIntensity(250, 250, 250);
			// TextureManager.getInstance()ȡ��һ��Texturemanager����
			// addTexture(textureName,texture)���һ���������ֻ�Ǻ����ǵ�texturesName��һ������
			TextureManager.getInstance().addTexture(texturesName[0],
					new Texture(LoadImg.bmp));
			// ��assets�ļ����ж�ȡsoilder.md2�ļ���ʵ����Object3D snork
			soilder = Loader.loadMD2(LoadAssets.loadf("soilder.md2"), scale);
			// ��תsoilder����"�ʵ�λ��"
			soilder.translate(-90, 0, 0);
			// ����ǽ�������ӽ�ȥ
			soilder.setTexture(texturesName[0]);
			// �ͷŲ�����Դ
			soilder.strip();
			// ����
			soilder.build();
			// ��snork��ӵ�World������
			world.addObject(soilder);

			Camera cam = world.getCamera();
			cam.moveCamera(Camera.CAMERA_MOVEOUT, 50);

			SimpleVector sv = new SimpleVector();
			// ����ǰSimpleVector��x,y,zֵ��Ϊ������SimpleVector(cube.getTransformedCenter())��ֵ
			sv.set(soilder.getTransformedCenter());
			// Y�����ϼ�ȥ100
			sv.y -= 120;
			// Z�����ϼ�ȥ100
			sv.z -= 120;
			// ���ù�Դλ��
			sun.setPosition(sv);

			cam.lookAt(soilder.getTransformedCenter());

			// if(Home.master == null)
			// Home.master = new Home();
		}

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	}
	
//	@Override
//	public void surfaceDestroyed(SurfaceHolder holder)
//		
//	}
	
}
