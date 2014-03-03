package au.com.twosquared.mode7;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game implements ApplicationListener {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Mode7 mode7;

	@Override
	public void create() {
		camera = new OrthographicCamera(256, 256);
		camera.position.set(128, 128, 0);
		camera.update();

		batch = new SpriteBatch();

		mode7 = new Mode7(256, 256, Format.RGB888);
		mode7.floor = new Pixmap(Gdx.files.internal("data/grass.png"));
		mode7.camera.set(0, 0, 16);

		Pixmap tree = new Pixmap(Gdx.files.internal("data/tree.png"));

		for (int x = 0; x < 1000; x++) {
			Mode7Sprite sprite = new Mode7Sprite(tree);
			sprite.position.x = (float) (Math.random() * 2000) - 1000;
			sprite.position.y = (float) (Math.random() * 2000) - 1000;
			mode7.sprites.add(sprite);
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		if (Gdx.input.isTouched()) {
			float dx = w / 2 - x;
			float dy = h / 2 - y;
			double r = Math.atan2(dy, dx);

			mode7.angle -= (float) Math.cos(r) * delta;
			mode7.horizon += 100f * (float) Math.sin(r) * delta;
		}

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		mode7.render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
