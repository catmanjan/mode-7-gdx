package au.com.twosquared.mode7;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;

public class Mode7Sprite implements Comparable<Mode7Sprite> {

	public Pixmap pixmap;

	public int sort;
	public Vector2 screen;
	public Vector2 size;
	public Vector2 position;

	public Mode7Sprite(Pixmap pixmap) {
		this.pixmap = pixmap;

		screen = new Vector2();
		size = new Vector2();
		position = new Vector2();
	}

	@Override
	public int compareTo(Mode7Sprite other) {
		return Integer.compare(sort, other.sort);
	}

}
