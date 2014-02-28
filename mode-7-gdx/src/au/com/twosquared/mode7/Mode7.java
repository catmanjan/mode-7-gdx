package au.com.twosquared.mode7;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Mode7 extends Pixmap {

	public Texture texture;
	public Pixmap floor;

	public float horizon = 100;
	public float angle = 0;
	public Vector3 camera;
	public Vector2 scale;

	public Mode7(int width, int height, Format format) {
		super(width, height, format);

		texture = new Texture(this, format, false);
		camera = new Vector3(0, 0, 1);
		scale = new Vector2(200, 200);
	}

	public void update() {
		setColor(Color.BLACK);
		fill();

		int width = getWidth();
		int height = getHeight();

		double sin = Math.sin(angle);
		double cos = Math.cos(angle);

		for (int y = (int) horizon; y < height; y++) {
			float distance = (camera.z * scale.y) / (y - horizon);
			float ratio = distance / scale.x;

			double dx = -sin * ratio;
			double dy = cos * ratio;

			double sx = camera.x + distance * cos - width / 2 * dx;
			double sy = camera.y + distance * sin - width / 2 * dy;

			for (int x = 0; x < width; x++) {
				int px = (int) Math.abs(sx % floor.getWidth());
				int py = (int) Math.abs(sy % floor.getHeight());
				int pixel = floor.getPixel(px, py);

				sx += dx;
				sy += dy;

				setColor(pixel);
				drawPixel(x, y);
			}
		}

		texture = new Texture(this, getFormat(), false);
	}
}