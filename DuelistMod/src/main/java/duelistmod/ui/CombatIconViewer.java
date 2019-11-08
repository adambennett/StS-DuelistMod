package duelistmod.ui;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.characters.TheDuelist;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Strings;

public class CombatIconViewer 
{
	private Hitbox summons;
	private Hitbox grave;
	private static final int summonWidth = 100;
	private static final int summonHeight = 100;
	private static final int graveWidth = 100;
	private static final int graveHeight = 100;

	// dragging
	private int moveStateSummons = 0;
	private int moveStateGrave = 0;
	private float summons_X;
	private float summons_Y;
	private float grave_X;
	private float grave_Y;
	private float startSummonsX;
	private float startSummonsY;
	private float startGraveX;
	private float startGraveY;

	public CombatIconViewer() 
	{
		summons = new Hitbox(summonWidth * Settings.scale, summonHeight * Settings.scale);
		grave = new Hitbox(graveWidth * Settings.scale, graveHeight * Settings.scale); 	
		summons.move(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.79f);
		grave.move(Settings.WIDTH / 1.85f, Settings.HEIGHT * 0.79f);
	}

	public void update() 
	{
		summons.update();
		grave.update();
		
		// drag and move Summons
		if (InputHelper.justClickedLeft) 
		{
			if (summons.hovered) 
			{
				summons_X = summons.cX - InputHelper.mX;
				summons_Y = summons.cY - InputHelper.mY;
				moveStateSummons = 1;
				startSummonsX = InputHelper.mX;
				startSummonsY = InputHelper.mY;
			}
		}
		
		// drag and move Graveyard
		if (InputHelper.justClickedLeft) 
		{
			if (grave.hovered) 
			{
				grave_X = grave.cX - InputHelper.mX;
				grave_Y = grave.cY - InputHelper.mY;
				moveStateGrave = 1;
				startGraveX = InputHelper.mX;
				startGraveY = InputHelper.mY;
			}
		}
		
		// View summon list on right click
		if (InputHelper.justClickedRight && AbstractDungeon.player.hasPower(SummonPower.POWER_ID) && summons.hovered)
		{
			CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
			SummonPower pow = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			
			if (pow.actualCardSummonList.size() > 0)
			{
				for (DuelistCard c : pow.actualCardSummonList) { tmp.addToBottom(c); }
				
				AbstractDungeon.gameDeckViewScreen = new DuelistCardViewScreen(tmp, "Summon List");
				DuelistMod.wasViewingSummonCards = true;
				((DuelistCardViewScreen)AbstractDungeon.gameDeckViewScreen).open();
			}
			else
			{
				AbstractPlayer p = AbstractDungeon.player;
				AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0f, "My Summon Zones are #rempty.", true));
			}
		}
		
		// View Graveyard on right click
		if (InputHelper.justClickedRight && grave.hovered)
		{
			if (TheDuelist.resummonPile.group.size() > 0)
			{
				AbstractDungeon.gameDeckViewScreen = new DuelistCardViewScreen(TheDuelist.resummonPile, "Graveyard (Resummoned Cards)");
				DuelistMod.wasViewingSummonCards = true;
				((DuelistCardViewScreen)AbstractDungeon.gameDeckViewScreen).open();
			}
			else
			{
				AbstractPlayer p = AbstractDungeon.player;
				AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0f, "No cards Resummoned this combat.", true));
			}
		}

		if (moveStateSummons > 0) 
		{
			if (InputHelper.justReleasedClickLeft) 	{ moveStateSummons = 0; } 
			else 
			{
				float x = Math.min(Math.max(InputHelper.mX + summons_X, 0.05f * Settings.WIDTH), 0.95f * Settings.WIDTH);
				float y = Math.min(Math.max(InputHelper.mY + summons_Y, 0.3f * Settings.HEIGHT), 0.85f * Settings.HEIGHT);
				if ((startSummonsX - InputHelper.mX) * (startSummonsX - InputHelper.mX) + (startSummonsY - InputHelper.mY) * (startSummonsY - InputHelper.mY) > 64)	{ moveStateSummons = 2; }
				if (moveStateSummons == 2) { summons.move(x, y); }
			}
		}
		
		if (moveStateGrave > 0) 
		{
			if (InputHelper.justReleasedClickLeft) 	{ moveStateGrave = 0; } 
			else 
			{
				float x = Math.min(Math.max(InputHelper.mX + grave_X, 0.05f * Settings.WIDTH), 0.95f * Settings.WIDTH);
				float y = Math.min(Math.max(InputHelper.mY + grave_Y, 0.3f * Settings.HEIGHT), 0.85f * Settings.HEIGHT);
				if ((startGraveX - InputHelper.mX) * (startGraveX - InputHelper.mX) + (startGraveY - InputHelper.mY) * (startGraveY - InputHelper.mY) > 64)	{ moveStateGrave = 2; }
				if (moveStateGrave == 2) { grave.move(x, y); }
			}
		}
	}

	public void render(SpriteBatch sb) 
	{
		
		sb.draw(loadTexture(DuelistMod.makeIconPath("SummonIconSmall.png")),summons.cX,summons.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		sb.draw(loadTexture(DuelistMod.makeIconPath("SpellIconSmall.png")),grave.cX,grave.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);

		if (DuelistCard.getSummons(AbstractDungeon.player) > 0) 
		{
			FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistCard.getSummons(AbstractDungeon.player),summons.cX,summons.cY,Settings.GREEN_TEXT_COLOR);
		}
		
		if (TheDuelist.resummonPile.group.size() > 0) { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + TheDuelist.resummonPile.group.size(),grave.cX,grave.cY,Settings.BLUE_TEXT_COLOR); }
		else { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + TheDuelist.resummonPile.group.size(),grave.cX,grave.cY,Settings.RED_TEXT_COLOR); }
		
		if (this.summons.hovered) 
		{
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, Strings.configSummonsIconText, getSummonTipBody());
		}
		
		if (this.grave.hovered) 
		{
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, "Graveyard", getGraveTipBody());
		}
		summons.render(sb);
		grave.render(sb);
	}
	
	private String getGraveTipBody()
	{
		String result = "[#2aecd7]Right [#2aecd7]click [#2aecd7]to [#2aecd7]view [#2aecd7]cards [#2aecd7]Resummoned [#2aecd7]this [#2aecd7]combat.";
		return result;
	}

	public String getSummonTipBody() {
		String result = "";
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID)) { result = "[#2aecd7]Right [#2aecd7]click [#2aecd7]to [#2aecd7]view [#2aecd7]summons. NL " + AbstractDungeon.player.getPower(SummonPower.POWER_ID).description; }
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
