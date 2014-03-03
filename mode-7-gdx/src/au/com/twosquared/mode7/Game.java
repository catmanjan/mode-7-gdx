package au.com.twosquared.mode7;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game implements ApplicationListener {

	OrthographicCamera camera;
	SpriteBatch batch;
	Mode7 mode7;

	@Override
	public void create() {
		camera = new OrthographicCamera(256, 256);
		camera.position.set(128, 128, 0);
		camera.update();

		batch = new SpriteBatch();

		Pixmap trackSprite = new Pixmap(Gdx.files.internal("data/track.png"));
		Pixmap carSprite = new Pixmap(Gdx.files.internal("data/car.png"));

		mode7 = new Mode7(256, 256, Format.RGB565);
		mode7.camera.set(431, 345 + 16, 16);
		mode7.horizon = 30;
		mode7.angle = (float) (-Math.PI / 2);
		mode7.floor = trackSprite;

		Mode7Sprite car = new Mode7Sprite(carSprite);
		car.position.set(431, 345);
		mode7.sprites.add(car);

		car = new Mode7Sprite(carSprite);
		car.position.set(431, 321);
		mode7.sprites.add(car);

		car = new Mode7Sprite(carSprite);
		car.position.set(431, 297);
		mode7.sprites.add(car);

		car = new Mode7Sprite(carSprite);
		car.position.set(431, 273);
		mode7.sprites.add(car);

		car = new Mode7Sprite(carSprite);
		car.position.set(447, 333);
		mode7.sprites.add(car);

		car = new Mode7Sprite(carSprite);
		car.position.set(447, 310);
		mode7.sprites.add(car);

		car = new Mode7Sprite(carSprite);
		car.position.set(447, 285);
		mode7.sprites.add(car);

		car = new Mode7Sprite(carSprite);
		car.position.set(447, 261);
		mode7.sprites.add(car);
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

			mode7.angle -= 1f * (float) Math.cos(rot) * delta;
			mode7.horizon += 20f * (float) Math.sin(rot) * delta;
			mode7.camera.x += 30f * (float) Math.cos(mode7.angle) * delta;
			mode7.camera.y += 30f * (float) Math.sin(mode7.angle) * delta;
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
