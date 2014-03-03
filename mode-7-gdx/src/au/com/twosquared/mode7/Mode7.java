package au.com.twosquared.mode7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Mode7 extends Pixmap {

	public Pixmap floor;
	public Texture texture;

	public float horizon = 100;
	public float angle = 0;
	public Vector3 camera;
	public Vector2 scale;
	public List<Mode7Sprite> sprites;

	public Mode7(int width, int height, Format format) {
		super(width, height, format);

		setFilter(Filter.NearestNeighbour);

		camera = new Vector3(0, 0, 1);
		scale = new Vector2(100, 100);
		sprites = new ArrayList<Mode7Sprite>();
	}

	public void render(SpriteBatch batch) {
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

		List<Mode7Sprite> visible = new ArrayList<Mode7Sprite>();

		for (Mode7Sprite sprite : sprites) {
			double dx = sprite.position.x - camera.x;
			double dy = sprite.position.y - camera.y;

			double sx = dx * cos + dy * sin;
			double sy = dy * cos - dx * sin;

			int sw = sprite.pixmap.getWidth();
			int sh = sprite.pixmap.getHeight();
			int w = (int) (sw * scale.x / sx);
			int h = (int) (sh * scale.y / sx);

			// Negative height, sprite is behind camera
			if (h < 1) {
				continue;
			}

			int x = (int) (scale.x / sx * sy) + width / 2;
			int y = (int) ((camera.z * scale.y) / sx + horizon);

			// Align sprite center-bottom
			sprite.screen.x = x - w / 2;
			sprite.screen.y = y - h;
			sprite.size.x = w;
			sprite.size.y = h;
			sprite.sort = y;

			visible.add(sprite);
		}

		Collections.sort(visible);

		for (Mode7Sprite sprite : visible) {
			int sw = sprite.pixmap.getWidth();
			int sh = sprite.pixmap.getHeight();
			int x = (int) sprite.screen.x;
			int y = (int) sprite.screen.y;
			int w = (int) sprite.size.x;
			int h = (int) sprite.size.y;

			drawPixmap(sprite.pixmap, 0, 0, sw, sh, x, y, w, h);
		}

		texture = new Texture(this, getFormat(), true);

		batch.draw(texture, 0, 0);
	}
}