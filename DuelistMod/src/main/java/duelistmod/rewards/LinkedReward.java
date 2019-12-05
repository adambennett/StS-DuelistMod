package duelistmod.rewards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.vfx.*;

import basemod.abstracts.CustomReward;
import duelistmod.helpers.Util;

@SuppressWarnings("unused")
public class LinkedReward extends RewardItem
{
	private boolean isBoss;
	private Color reticleColor;
	private ArrayList<AbstractGameEffect> effects;

	public LinkedReward(RewardType type, BoosterPack linkedBooster, int gold, RelicTier tier, PotionRarity rarity, BoosterPack booster) {
		super();
		this.relicLink = linkedBooster;
		linkedBooster.relicLink = this;
		if (this.type.equals(RewardType.GOLD))
		{
			this.outlineImg = null;
	        this.img = null;
	        this.goldAmt = 0;
	        this.bonusGold = 0;
	        this.effects = new ArrayList<AbstractGameEffect>();
	        this.hb = new Hitbox(460.0f * Settings.scale, 90.0f * Settings.scale);
	        this.flashTimer = 0.0f;
	        this.isDone = false;
	        this.ignoreReward = false;
	        this.redText = false;
	        this.reticleColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
	        this.type = RewardType.GOLD;
	        this.goldAmt = gold;
	        this.applyGoldBonus(false);
		}
		else if (this.type.equals(RewardType.POTION))
		{
			ArrayList<AbstractPotion> potions = PotionHelper.getPotionsByRarity(rarity);
			AbstractPotion potion = potions.get(AbstractDungeon.cardRandomRng.random(potions.size() - 1));
			this.outlineImg = null;
	        this.img = null;
	        this.goldAmt = 0;
	        this.bonusGold = 0;
	        this.effects = new ArrayList<AbstractGameEffect>();
	        this.hb = new Hitbox(460.0f * Settings.scale, 90.0f * Settings.scale);
	        this.flashTimer = 0.0f;
	        this.isDone = false;
	        this.ignoreReward = false;
	        this.redText = false;
	        this.reticleColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
	        this.type = RewardType.POTION;
	        this.potion = potion;
	        this.text = potion.name;
		}
		else if (this.type.equals(RewardType.RELIC))
		{
			AbstractRelic relic = AbstractDungeon.returnRandomRelic(tier);
			this.outlineImg = null;
	        this.img = null;
	        this.goldAmt = 0;
	        this.bonusGold = 0;
	        this.effects = new ArrayList<AbstractGameEffect>();
	        this.hb = new Hitbox(460.0f * Settings.scale, 90.0f * Settings.scale);
	        this.flashTimer = 0.0f;
	        this.isDone = false;
	        this.ignoreReward = false;
	        this.redText = false;
	        this.reticleColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
	        this.type = RewardType.RELIC;
	        this.relic = relic;
	        (relic.hb = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale)).move(-1000.0f, -1000.0f);
	        this.text = relic.name;
		}
		
		// Another booster pack otherwise
		else 
		{
			this.outlineImg = null;
	        this.img = null;
	        this.goldAmt = 0;
	        this.bonusGold = 0;
	        this.effects = new ArrayList<AbstractGameEffect>();
	        this.hb = new Hitbox(460.0f * Settings.scale, 90.0f * Settings.scale);
	        this.flashTimer = 0.0f;
	        this.isDone = false;
	        this.ignoreReward = false;
	        this.redText = false;
	        this.reticleColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
	        this.type = RewardType.CARD;
	        this.isBoss = (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss);
	        this.cards = AbstractDungeon.getRewardCards();
	        this.text = RewardItem.TEXT[2];
		}
	}

	@Override
	public boolean claimReward() 
	{
		if (this.relicLink != null) {
            this.relicLink.isDone = true;
            this.relicLink.ignoreReward = true;
        }
		switch (this.type) 
		{
	        case GOLD: {
	            CardCrawlGame.sound.play("GOLD_GAIN");
	            if (this.bonusGold == 0) {
	                AbstractDungeon.player.gainGold(this.goldAmt);
	            }
	            else {
	                AbstractDungeon.player.gainGold(this.goldAmt + this.bonusGold);
	            }
	            return true;
	        }
	        case POTION: {
	            if (AbstractDungeon.player.hasRelic("Sozu")) {
	                AbstractDungeon.player.getRelic("Sozu").flash();
	                return true;
	            }
	            if (AbstractDungeon.player.obtainPotion(this.potion)) {
	                if (!TipTracker.tips.get("POTION_TIP")) {
	                    AbstractDungeon.ftue = new FtueTip(RewardItem.LABEL[0], RewardItem.MSG[0], Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, this.potion);
	                    TipTracker.neverShowAgain("POTION_TIP");
	                }
	                CardCrawlGame.metricData.addPotionObtainData(this.potion);
	                return true;
	            }
	            return false;
	        }
	        case RELIC: {
	            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
	                return false;
	            }
	            if (!this.ignoreReward) {
	                this.relic.instantObtain();
	                CardCrawlGame.metricData.addRelicObtainData(this.relic);
	            }
	            return true;
	        }
	        case CARD: {
	            if (AbstractDungeon.player.hasRelic("Question Card")) {
	                AbstractDungeon.player.getRelic("Question Card").flash();
	            }
	            if (AbstractDungeon.player.hasRelic("Busted Crown")) {
	                AbstractDungeon.player.getRelic("Busted Crown").flash();
	            }
	            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
	                AbstractDungeon.cardRewardScreen.open(this.cards, this, "Keep 1 Card from the Pack");
	                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
	            }
	            return false;
	        }
	        default: {
	        	Util.log("ERROR: Claim Reward() failed");
	            return false;
	        }
		}
	}
	
	private void applyGoldBonus(final boolean theft) {
        final int tmp = this.goldAmt;
        this.bonusGold = 0;
        if (theft) {
            this.text = this.goldAmt + RewardItem.TEXT[0];
        }
        else {
            if (!(AbstractDungeon.getCurrRoom() instanceof TreasureRoom)) {
                if (AbstractDungeon.player.hasRelic("Golden Idol")) {
                    this.bonusGold += MathUtils.round(tmp * 0.25f);
                }
                if (ModHelper.isModEnabled("Midas")) {
                    this.bonusGold += MathUtils.round(tmp * 2.0f);
                }
                if (ModHelper.isModEnabled("MonsterHunter")) {
                    this.bonusGold += MathUtils.round(tmp * 1.5f);
                }
            }
            if (this.bonusGold == 0) {
                this.text = this.goldAmt + RewardItem.TEXT[1];
            }
            else {
                this.text = this.goldAmt + RewardItem.TEXT[1] + " (" + this.bonusGold + ")";
            }
        }
    }

	@SuppressWarnings("incomplete-switch")
	@Override
	public void render(final SpriteBatch sb) {
        if (this.hb.hovered) {
            sb.setColor(new Color(0.4f, 0.6f, 0.6f, 1.0f));
        }
        else {
            sb.setColor(new Color(0.5f, 0.6f, 0.6f, 0.8f));
        }
        if (this.hb.clickStarted) {
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, this.y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.scale * 0.98f, Settings.scale * 0.98f, 0.0f, 0, 0, 464, 98, false, false);
        }
        else {
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, this.y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 464, 98, false, false);
        }
        if (this.flashTimer != 0.0f) {
            sb.setColor(0.6f, 1.0f, 1.0f, this.flashTimer * 1.5f);
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, this.y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.scale * 1.03f, Settings.scale * 1.15f, 0.0f, 0, 0, 464, 98, false, false);
            sb.setBlendFunction(770, 771);
        }
        sb.setColor(Color.WHITE);
        switch (this.type) {
            case CARD: {
                if (this.isBoss) {
                    sb.draw(ImageMaster.REWARD_CARD_BOSS, RewardItem.REWARD_ITEM_X - 32.0f, this.y - 32.0f - 2.0f * Settings.scale, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
                    break;
                }
                sb.draw(ImageMaster.REWARD_CARD_NORMAL, RewardItem.REWARD_ITEM_X - 32.0f, this.y - 32.0f - 2.0f * Settings.scale, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
                break;
            }
            case GOLD: {
                sb.draw(ImageMaster.UI_GOLD, RewardItem.REWARD_ITEM_X - 32.0f, this.y - 32.0f - 2.0f * Settings.scale, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
                break;
            }
            case RELIC: {
                this.relic.renderWithoutAmount(sb, new Color(0.0f, 0.0f, 0.0f, 0.25f));
                if (!this.hb.hovered) {
                    break;
                }
                this.relic.renderTip(sb);
                break;
            }
            case POTION: {
                this.potion.renderLightOutline(sb);
                this.potion.render(sb);
                this.potion.generateSparkles(this.potion.posX, this.potion.posY, true);
                break;
            }
        }
        Color color;
        if (this.hb.hovered) {
            color = Settings.GOLD_COLOR;
        }
        else {
            color = Settings.CREAM_COLOR;
        }
        if (this.redText) {
            color = Settings.RED_TEXT_COLOR;
        }
        FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, this.text, 833.0f * Settings.scale, this.y + 5.0f * Settings.scale, 1000.0f * Settings.scale, 0.0f, color);
        if (!this.hb.hovered) {
            for (final AbstractGameEffect e : this.effects) {
                e.render(sb);
            }
        }
        if (Settings.isControllerMode) {
            this.renderReticle(sb, this.hb);
        }
        if (this.type == RewardType.SAPPHIRE_KEY) {
            this.renderRelicLink(sb);
        }
        this.hb.render(sb);
    }
	
	private void renderRelicLink(final SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.RELIC_LINKED, this.hb.cX - 64.0f, this.y - 64.0f + 52.0f * Settings.scale, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 128, 128, false, false);
    }

}
