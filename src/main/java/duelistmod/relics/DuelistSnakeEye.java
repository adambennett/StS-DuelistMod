package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.Util;
import duelistmod.powers.duelistPowers.MegaconfusionPower;

public class DuelistSnakeEye extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("DuelistSnakeEye");
	public static final String IMG =  DuelistMod.makeRelicPath("DuelistSnakeEye.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("DuelistSnakeEye_Outline.png");

	public DuelistSnakeEye() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.FLAT);
	}
	
	@Override
	public int getPrice()
	{
		return 350;
	}
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		if (AbstractDungeon.ascensionLevel < 10 && Util.getChallengeLevel() < 0) { return true; }
		else { return false; }
	}
	
    @Override
    public void atPreBattle() {
        this.flash();
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MegaconfusionPower(3)));
    }
    
    @Override
    public void onEquip() {
        final AbstractPlayer player = AbstractDungeon.player;
        player.masterHandSize += 3;
    }
    
    @Override
    public void onUnequip() {
        final AbstractPlayer player = AbstractDungeon.player;
        player.masterHandSize -= 3;
    }

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new DuelistSnakeEye();
	}
}
