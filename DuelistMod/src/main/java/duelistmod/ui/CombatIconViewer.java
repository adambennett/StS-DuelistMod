package duelistmod.ui;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.powers.SummonPower;

public class CombatIconViewer 
{
	private Hitbox hb;
	private static final int width = 100;
	private static final int height = 100;

	// dragging
	private int moveState = 0;
	private float dx;
	private float dy;
	private float startx;
	private float starty;

	public CombatIconViewer() 
	{
		hb = new Hitbox(width * Settings.scale, height * Settings.scale);
		hb.move(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.79f);
	}

	public void update() 
	{
		hb.update();

		// dragging
		if (InputHelper.justClickedLeft) 
		{
			if (hb.hovered) 
			{
				dx = hb.cX - InputHelper.mX;
				dy = hb.cY - InputHelper.mY;
				moveState = 1;
				startx = InputHelper.mX;
				starty = InputHelper.mY;
			}
		}

		if (moveState > 0) 
		{
			if (InputHelper.justReleasedClickLeft) 	{ moveState = 0; } 
			else 
			{
				float x = Math.min(Math.max(InputHelper.mX + dx, 0.05f * Settings.WIDTH), 0.95f * Settings.WIDTH);
				float y = Math.min(Math.max(InputHelper.mY + dy, 0.3f * Settings.HEIGHT), 0.85f * Settings.HEIGHT);
				if ((startx - InputHelper.mX) * (startx - InputHelper.mX) + (starty - InputHelper.mY) * (starty - InputHelper.mY) > 64)	{ moveState = 2; }
				if (moveState == 2) { hb.move(x, y); }
			}
		}
	}

	public void render(SpriteBatch sb) 
	{
		for (int i = 0; i < 1; i++) 
		{
			sb.draw(loadTexture(DuelistMod.makeIconPath("SummonIconSmall.png")),hb.cX,hb.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		}

		if (DuelistCard.getSummons(AbstractDungeon.player) > 0) 
		{
			FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistCard.getSummons(AbstractDungeon.player),hb.cX,hb.cY,Settings.GREEN_TEXT_COLOR);
		}

		if (this.hb.hovered) 
		{
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, Strings.configSummonsIconText, getTipBody());
		}
		hb.render(sb);
	}

	public String getTipBody() {
		String result = "";
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID)) { result = AbstractDungeon.player.getPower(SummonPower.POWER_ID).description; }
		return result;
	}

	public String highlightedText(String text) {
		return text.replaceAll("(?<=\\s|^)(?=\\S)", "#y");
	}
	
	// Use map to avoid reloading the icon every time this updates
	private static HashMap<String, Texture> imgMap = new HashMap<>();
	public static Texture loadTexture(String path) 
	{
		if (!imgMap.containsKey(path)) 
		{
			imgMap.put(path, ImageMaster.loadImage(path));
		}
		return imgMap.get(path);
	}
}
