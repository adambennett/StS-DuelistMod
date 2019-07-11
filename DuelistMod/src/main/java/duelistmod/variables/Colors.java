package duelistmod.variables;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.CardHelper;

public class Colors {

	// Colors (RGB)
	
	// Character Colors
	public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);
	public static final Color DEFAULT_GREEN = CardHelper.getColor(0.0f, 39.0f, 0.0f);
	public static final Color DEFAULT_PURPLE = CardHelper.getColor(0.0f, 0.0f, 61.0f);
	public static final Color DEFAULT_YELLOW = CardHelper.getColor(70.0f, 53.0f, 28.0f);
	
	// Potion Colors in RGB
	public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
	//public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
	public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(32.0f, 222.0f, 190.0f); // Teal
	public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

}
