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
import duelistmod.relics.VampiricPendant;
import duelistmod.variables.Strings;

public class CombatIconViewer 
{
	private Hitbox summons;
	private Hitbox grave;
	private Hitbox entomb;
	private Hitbox soul;
	private Hitbox vendread;
	private Hitbox mayakashi;
	private Hitbox ghostrick;
	private Hitbox shir;
	private Hitbox vampire;
	private static final int summonWidth = 100;
	private static final int summonHeight = 100;
	private static final int graveWidth = 100;
	private static final int graveHeight = 100;
	private static final int entombWidth = 100;
	private static final int entombHeight = 100;
	private static final int soulWidth = 100;
	private static final int soulHeight = 100;	
	private static final int vampWidth = 100;
	private static final int vampHeight = 100;	
	private static final int shirWidth = 100;
	private static final int shirHeight = 100;	
	private static final int vendreadWidth = 100;
	private static final int vendreadHeight = 100;	
	private static final int ghostWidth = 100;
	private static final int ghostHeight = 100;	
	private static final int mayaWidth = 100;
	private static final int mayaHeight = 100;

	// dragging
	private int moveStateSummons = 0;
	private int moveStateGrave = 0;
	private int moveStateSouls = 0;	
	private float summons_X;
	private float summons_Y;
	private float grave_X;
	private float grave_Y;
	private float souls_X;
	private float souls_Y;
	private float startSummonsX;
	private float startSummonsY;
	private float startGraveX;
	private float startGraveY;
	private float startSoulsX;
	private float startSoulsY;
	
	private boolean entombCombat = true;

	public CombatIconViewer() 
	{
		summons = new Hitbox(summonWidth * Settings.scale, summonHeight * Settings.scale);
		grave = new Hitbox(graveWidth * Settings.scale, graveHeight * Settings.scale); 	
		soul = new Hitbox(soulWidth * Settings.scale, soulHeight * Settings.scale);		
		vendread = new Hitbox(vendreadWidth * Settings.scale, vendreadHeight * Settings.scale); 	
		mayakashi = new Hitbox(mayaWidth * Settings.scale, mayaHeight * Settings.scale); 	
		ghostrick = new Hitbox(ghostWidth * Settings.scale, ghostHeight * Settings.scale); 	
		shir = new Hitbox(shirWidth * Settings.scale, shirHeight * Settings.scale); 	
		vampire = new Hitbox(vampWidth * Settings.scale, vampHeight * Settings.scale); 	
		entomb = new Hitbox(entombWidth * Settings.scale, entombHeight * Settings.scale); 	
		summons.move(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.79f);
		soul.move(Settings.WIDTH / 2.15f, Settings.HEIGHT * 0.79f);
		grave.move(Settings.WIDTH / 1.85f, Settings.HEIGHT * 0.79f);	
		entomb.move(Settings.WIDTH / 1.65f, Settings.HEIGHT * 0.79f);	
		ghostrick.move(Settings.WIDTH / 3.0f, Settings.HEIGHT * 0.79f);
		mayakashi.move(Settings.WIDTH / 2.85f, Settings.HEIGHT * 0.79f);
		shir.move(Settings.WIDTH / 2.7f, Settings.HEIGHT * 0.79f);
		vampire.move(Settings.WIDTH / 2.55f, Settings.HEIGHT * 0.79f);
		vendread.move(Settings.WIDTH / 2.4f, Settings.HEIGHT * 0.79f);
	}

	public void update() 
	{
		summons.update();
		grave.update();
		entomb.update();
		soul.update();
		ghostrick.update();
		mayakashi.update();
		shir.update();
		vampire.update();
		vendread.update();
		
		// drag and move Summons
		if (InputHelper.justClickedLeft && !AbstractDungeon.isScreenUp)
		{
			if (summons.hovered) 
			{
				summons_X = summons.cX - InputHelper.mX;
				summons_Y = summons.cY - InputHelper.mY;
				moveStateSummons = 1;
				startSummonsX = InputHelper.mX;
				startSummonsY = InputHelper.mY;
			}

			else if (grave.hovered) 
			{
				grave_X = grave.cX - InputHelper.mX;
				grave_Y = grave.cY - InputHelper.mY;
				moveStateGrave = 1;
				startGraveX = InputHelper.mX;
				startGraveY = InputHelper.mY;
			}

			else if (soul.hovered) 
			{
				souls_X = soul.cX - InputHelper.mX;
				souls_Y = soul.cY - InputHelper.mY;
				moveStateSouls = 1;
				startSoulsX = InputHelper.mX;
				startSoulsY = InputHelper.mY;
			}
			
			else if (entomb.hovered)
			{
				entombCombat = !entombCombat;
				updateEntombBody();
			}
		}
		
		// View summon list on right click
		if (InputHelper.justClickedRight && AbstractDungeon.player.hasPower(SummonPower.POWER_ID) && summons.hovered && !AbstractDungeon.isScreenUp)
		{
			CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
			SummonPower pow = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			
			if (pow.actualCardSummonList.size() > 0)
			{
				for (DuelistCard c : pow.actualCardSummonList) { tmp.addToBottom(c); }
				DuelistMod.duelistCardViewScreen.open(tmp, "Summon List");
			}
			else
			{
				AbstractPlayer p = AbstractDungeon.player;
				AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0f, "My Summon Zones are #rempty.", true));
			}
		}
		
		// View Graveyard on right click
		if (InputHelper.justClickedRight && grave.hovered && !AbstractDungeon.isScreenUp)
		{
			if (TheDuelist.resummonPile.group.size() > 0)
			{
				DuelistMod.duelistCardViewScreen.open(TheDuelist.resummonPile, "Graveyard (Resummoned Cards)");
			}
			else
			{
				AbstractPlayer p = AbstractDungeon.player;
				AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0f, "No cards Resummoned this combat.", true));
			}
		}
		
		// View Entomb on right click
		if (InputHelper.justClickedRight && entomb.hovered && !AbstractDungeon.isScreenUp)
		{
			String screenText = "";
			if (DuelistMod.entombedCardsCombat.size() > 0 && entombCombat)
			{
				screenText = "Entombed (Combat)";
				CardGroup newGroup = new CardGroup(CardGroupType.UNSPECIFIED);
				newGroup.group.addAll(DuelistMod.entombedCardsCombat);
				DuelistMod.duelistCardViewScreen.open(newGroup, screenText);
			}
			else if (DuelistMod.entombedCards.size() > 0 && !entombCombat)
			{
				screenText = "Entombed";
				CardGroup newGroup = new CardGroup(CardGroupType.UNSPECIFIED);
				newGroup.group.addAll(DuelistMod.entombedCards);
				DuelistMod.duelistCardViewScreen.open(newGroup, screenText);
			}
			else
			{
				AbstractPlayer p = AbstractDungeon.player;
				AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0f, "No cards Entombed", true));
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
		
		if (moveStateSouls > 0) 
		{
			if (InputHelper.justReleasedClickLeft) 	{ moveStateSouls = 0; } 
			else 
			{
				float x = Math.min(Math.max(InputHelper.mX + souls_X, 0.05f * Settings.WIDTH), 0.95f * Settings.WIDTH);
				float y = Math.min(Math.max(InputHelper.mY + souls_Y, 0.3f * Settings.HEIGHT), 0.85f * Settings.HEIGHT);
				if ((startSoulsX - InputHelper.mX) * (startSoulsX - InputHelper.mX) + (startSoulsY - InputHelper.mY) * (startSoulsY - InputHelper.mY) > 64)	{ moveStateSouls = 2; }
				if (moveStateSouls == 2) { soul.move(x, y); }
			}
		}
	}

	public void render(SpriteBatch sb) 
	{
		boolean showingEntomb = false;
		boolean showingCombatEntomb = false;
		sb.draw(loadTexture(DuelistMod.makeIconPath("SummonIconSmall.png")),summons.cX,summons.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		sb.draw(loadTexture(DuelistMod.makeIconPath("Grave.png")),grave.cX,grave.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		sb.draw(loadTexture(DuelistMod.makeIconPath("Souls.png")),soul.cX,soul.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		
		if (entombCombat && DuelistMod.entombedCardsCombat.size() > 0) {
			showingEntomb = true;
			showingCombatEntomb = true;
			sb.draw(loadTexture(DuelistMod.makeIconPath("Entomb.png")),entomb.cX,entomb.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		}
		else if (!entombCombat && DuelistMod.entombedCards.size() > 0) {
			showingEntomb = true;
			sb.draw(loadTexture(DuelistMod.makeIconPath("Entomb.png")),entomb.cX,entomb.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		}
		if (!showingEntomb && (DuelistMod.entombedCards.size() > 0 || DuelistMod.entombedCardsCombat.size() > 0)) {
			showingEntomb = true;
			if (DuelistMod.entombedCardsCombat.size() > 0) { showingCombatEntomb = true; }
			sb.draw(loadTexture(DuelistMod.makeIconPath("Entomb.png")),entomb.cX,entomb.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		}
		
		
		if (DuelistMod.ghostrickPlayed > 0 && DuelistMod.ghostrickPlayEffect)
		{
			sb.draw(loadTexture(DuelistMod.makeIconPath("Souls.png")),ghostrick.cX,ghostrick.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		}
		
		if (DuelistMod.mayakashiPlayed > 0 && DuelistMod.mayakashiPlayEffect)
		{
			sb.draw(loadTexture(DuelistMod.makeIconPath("Souls.png")),mayakashi.cX,mayakashi.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		}
		
		if (DuelistMod.shiranuiPlayed > 0 && DuelistMod.shiranuiPlayEffect)
		{
			sb.draw(loadTexture(DuelistMod.makeIconPath("Souls.png")),shir.cX,shir.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		}
		
		if (DuelistMod.vampiresPlayed > 0 && DuelistMod.vampiresPlayEffect)
		{
			sb.draw(loadTexture(DuelistMod.makeIconPath("Souls.png")),vampire.cX,vampire.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		}
		
		if (DuelistMod.vendreadPlayed > 0 && DuelistMod.vendreadPlayEffect)
		{
			sb.draw(loadTexture(DuelistMod.makeIconPath("Souls.png")),vendread.cX,vendread.cY,17 / 2.0f,17 / 2.0f,17,17,Settings.scale * 2,Settings.scale * 2,0, 0, 0, 17, 17, false, false);
		}
		
		if (DuelistCard.getSummons(AbstractDungeon.player) > 0) 
		{
			FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistCard.getSummons(AbstractDungeon.player),summons.cX,summons.cY,Settings.GREEN_TEXT_COLOR);
		}
		
		if (showingEntomb) {
			if (showingCombatEntomb) {
				if (DuelistMod.entombedCardsCombat.size() > 0) { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistMod.entombedCardsCombat.size(),entomb.cX,entomb.cY,Settings.BLUE_TEXT_COLOR); }
				else { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"0",entomb.cX,entomb.cY,Settings.RED_TEXT_COLOR); }
			}
			else {
				if (DuelistMod.entombedCards.size() > 0) { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistMod.entombedCards.size(),entomb.cX,entomb.cY,Settings.BLUE_TEXT_COLOR); }
				else { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"0",entomb.cX,entomb.cY,Settings.RED_TEXT_COLOR); }
			}
		}
		
		if (DuelistMod.currentZombieSouls > 0) { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistMod.currentZombieSouls,soul.cX,soul.cY,Settings.BLUE_TEXT_COLOR); }
		else { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistMod.currentZombieSouls,soul.cX,soul.cY,Settings.RED_TEXT_COLOR); }

		if (TheDuelist.resummonPile.group.size() > 0) { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + TheDuelist.resummonPile.group.size(),grave.cX,grave.cY,Settings.BLUE_TEXT_COLOR); }
		else { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"0",grave.cX,grave.cY,Settings.RED_TEXT_COLOR); }
		
		if (DuelistMod.ghostrickPlayed > 0 && DuelistMod.ghostrickPlayEffect) { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistMod.ghostrickPlayed,ghostrick.cX,ghostrick.cY,Settings.BLUE_TEXT_COLOR); }
		if (DuelistMod.mayakashiPlayed > 0 && DuelistMod.mayakashiPlayEffect) { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistMod.mayakashiPlayed,mayakashi.cX,mayakashi.cY,Settings.BLUE_TEXT_COLOR); }
		if (DuelistMod.shiranuiPlayed > 0 && DuelistMod.shiranuiPlayEffect) { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistMod.shiranuiPlayed,shir.cX,shir.cY,Settings.BLUE_TEXT_COLOR); }
		if (DuelistMod.vampiresPlayed > 0 && DuelistMod.vampiresPlayEffect) { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistMod.vampiresPlayed,vampire.cX,vampire.cY,Settings.BLUE_TEXT_COLOR); }
		if (DuelistMod.vendreadPlayed > 0 && DuelistMod.vendreadPlayEffect) { FontHelper.renderFontCentered(sb,FontHelper.powerAmountFont,"" + DuelistMod.vendreadPlayed,vendread.cX,vendread.cY,Settings.BLUE_TEXT_COLOR); }

		if (this.summons.hovered) 
		{
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, Strings.configSummonsIconText, getSummonTipBody());
		}
		
		if (this.grave.hovered) 
		{
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, "Graveyard", getGraveTipBody());
		}
		
		if (this.soul.hovered) 
		{
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, "Souls", getSoulTipBody());
		}
		
		if (this.entomb.hovered && showingEntomb) 
		{
			String text = "Entomb";
			if (showingCombatEntomb) { text = "Entomb (Combat)"; }
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, text, getEntombTipBody(showingCombatEntomb));
		}
		
		if (this.ghostrick.hovered && DuelistMod.ghostrickPlayed > 0 && DuelistMod.ghostrickPlayEffect)
		{
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, "Ghostrick", getGhostTipBody());
		}
		
		if (this.mayakashi.hovered && DuelistMod.mayakashiPlayed > 0 && DuelistMod.mayakashiPlayEffect)
		{
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, "Mayakashi", getMayaTipBody());
		}
		
		if (this.shir.hovered && DuelistMod.shiranuiPlayed > 0 && DuelistMod.shiranuiPlayEffect)
		{
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, "Shiranui", getShirTipBody());
		}
		
		if (this.vampire.hovered && DuelistMod.vampiresPlayed > 0 && DuelistMod.vampiresPlayEffect)
		{
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, "Vampire", getVampTipBody());
		}
		
		if (this.vendread.hovered && DuelistMod.vendreadPlayed > 0 && DuelistMod.vendreadPlayEffect)
		{
			TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, "Vendread", getVendTipBody());
		}
		
		summons.render(sb);
		grave.render(sb);
		soul.render(sb);
		if (showingEntomb) { entomb.render(sb); }
		if (DuelistMod.ghostrickPlayed > 0 && DuelistMod.ghostrickPlayEffect) { ghostrick.render(sb); }
		if (DuelistMod.mayakashiPlayed > 0 && DuelistMod.mayakashiPlayEffect) { mayakashi.render(sb); }
		if (DuelistMod.shiranuiPlayed > 0 && DuelistMod.shiranuiPlayEffect) { shir.render(sb); }
		if (DuelistMod.vampiresPlayed > 0 && DuelistMod.vampiresPlayEffect) { vampire.render(sb); }
		if (DuelistMod.vendreadPlayed > 0 && DuelistMod.vendreadPlayEffect) { vendread.render(sb); }
	}
	
	private String getGhostTipBody()
	{
		int max = (DuelistMod.ghostrickNeedPlayed + 1);
		return "" + DuelistMod.ghostrickPlayed + "/" + max + " #yGhostrick cards played. NL When you play " + max + "/" + max + ", reset this counter and #yResummon a random monster from your #yGraveyard on a random enemy.";
	}
	
	private String getMayaTipBody()
	{
		int max = (DuelistMod.mayakashiNeedPlayed + 1);
		return "" + DuelistMod.mayakashiPlayed + "/" + max + " #yMayakashi cards played. NL When you play " + max + "/" + max + ", reset this counter and apply a random #b2 turn debuff to a random enemy.";
	}
	
	private String getShirTipBody()
	{
		int max = (DuelistMod.shiranuiNeedPlayed + 1);
		return "" + DuelistMod.shiranuiPlayed + "/" + max + " #yShiranui cards played. NL When you play " + max + "/" + max + ", reset this counter and gain #b1 #yDexterity.";
	}
	
	private String getVampTipBody()
	{
		int vamp = DuelistMod.vampiresNeedPlayed + 1;
		if (AbstractDungeon.player.hasRelic(VampiricPendant.ID)) { vamp -= 5; }
		return "" + DuelistMod.vampiresPlayed + "/" + vamp + " #yVampire cards played. NL When you play " + vamp + "/" + vamp + ", reset this counter and siphon #b5 #yTemporary #yHP from ALL enemies.";
	}
	
	private String getVendTipBody()
	{
		int max = (DuelistMod.vendreadNeedPlayed + 1);
		return "" + DuelistMod.vendreadPlayed + "/" + max + " #yVendread cards played. NL When you play " + max + "/" + max + ", reset this counter and gain #b1 #yStrength.";
	}
	
	private String getSoulTipBody()
	{
		return "#ySouls: " + DuelistMod.currentZombieSouls + " NL #ySouls are required to #yResummon #yZombies. Gain #ySouls by #yTributing #yZombies for other #yZombies.";
	}
	
	private void updateEntombBody()
	{
		entomb.update();
	}
	
	private String getEntombTipBody(boolean showingCombat)
	{
		String result = "";
		if (showingCombat) {
			result = "[#2aecd7]Right [#2aecd7]click [#2aecd7]to [#2aecd7]view [#2aecd7]cards [#2aecd7]Entombed [#2aecd7]this [#2aecd7]combat. NL NL [#2aecd7]Left [#2aecd7]click [#2aecd7]to [#2aecd7]switch [#2aecd7]list [#2aecd7]to [#2aecd7]Entombed [#2aecd7]this [#2aecd7]run.";
		}
		else {
			result = "[#2aecd7]Right [#2aecd7]click [#2aecd7]to [#2aecd7]view [#2aecd7]cards [#2aecd7]Entombed [#2aecd7]this [#2aecd7]run. NL NL [#2aecd7]Left [#2aecd7]click [#2aecd7]to [#2aecd7]switch [#2aecd7]list [#2aecd7]to [#2aecd7]Entombed [#2aecd7]this [#2aecd7]combat.";
		}
		return result;
	}
	
	private String getGraveTipBody()
	{
		return "[#2aecd7]Right [#2aecd7]click [#2aecd7]to [#2aecd7]view [#2aecd7]cards [#2aecd7]Resummoned [#2aecd7]this [#2aecd7]combat.";
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
	private static final HashMap<String, Texture> imgMap = new HashMap<>();
	public static Texture loadTexture(String path) 
	{
		if (!imgMap.containsKey(path)) 
		{
			imgMap.put(path, ImageMaster.loadImage(path));
		}
		return imgMap.get(path);
	}
}
