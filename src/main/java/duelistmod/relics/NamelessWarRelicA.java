package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.variables.Strings;

public class NamelessWarRelicA extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("NamelessWarRelicA");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
    
    private float MODIFIER_AMT;

	public NamelessWarRelicA() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.MAGICAL);
		this.MODIFIER_AMT = 0.10f;
	}
	
    @Override
    public void atBattleStart() 
    {
        this.flash();
        for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) 
        {
            if (m.currentHealth > (int)(m.maxHealth * (1.0f - this.MODIFIER_AMT))) {
                m.currentHealth = (int)(m.maxHealth * (1.0f - this.MODIFIER_AMT));
                m.healthBarUpdatedEvent();
            }
        }
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new NamelessWarRelicA();
	}
}
