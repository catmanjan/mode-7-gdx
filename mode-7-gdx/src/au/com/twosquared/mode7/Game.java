package au.com.twosquared.mode7;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
		mode7.camera.set(0, 0, 32);

		Pixmap tree = new Pixmap(Gdx.files.internal("data/tree.png"));

		for (int x = 0; x < 100; x++) {
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

	private void input() {
		float delta = Gdx.graphics.getDeltaTime();
		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		if (Gdx.input.isTouched()) {
			float dx = w / 2 - x;
			float dy = h / 2 - y;
			double rot = Math.atan2(dy, dx);

			mode7.angle -= (float) Math.cos(rot) * delta;
			mode7.horizon += 100f * (float) Math.sin(rot) * delta;
		}

		if (Gdx.input.isKeyPressed(Keys.W)) {
			mode7.camera.x += 100f * (float) Math.cos(mode7.angle) * delta;
			mode7.camera.y += 100f * (float) Math.sin(mode7.angle) * delta;
		}

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			mode7.camera.z += 100f * delta;
		}

		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
			mode7.camera.z -= 100f * delta;
		}
	}

	@Override
	public void render() {
		input();

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
