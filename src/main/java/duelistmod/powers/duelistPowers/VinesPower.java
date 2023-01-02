package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.Util;
import duelistmod.powers.SummonPower;
import duelistmod.powers.enemyPowers.ResistNatureEnemyPower;
import duelistmod.relics.*;
import duelistmod.variables.Tags;

public class VinesPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("VinesPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("VinesPower.png");
	public boolean skipConfigChecks;
	public boolean naturalDisaster;
	
	public VinesPower(int amt) {
		this(amt, false);
	}

	public VinesPower(int amt, boolean skipConfigChecks) {
		this.skipConfigChecks = skipConfigChecks;
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.canGoNegative = false;
		this.img = new Texture(IMG);
		this.source = AbstractDungeon.player;
		this.amount = amt;
		updateDescription();
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		updateDescription();
		if (this.amount2 > 0) { this.flash(); dmgEnemies(); }
	}

	@Override
	public void updateDescription()
	{
		calcDamage();
		if (this.amount2 < 0) { this.amount2 = 0; }
		this.description = DESCRIPTIONS[0] + this.amount2;
	}
	
	private void calcDamage()
	{
		AbstractPlayer p = AbstractDungeon.player;
		int natsSummoned = 0;
		if (p.hasPower(SummonPower.POWER_ID)) { SummonPower pow = (SummonPower)p.getPower(SummonPower.POWER_ID); natsSummoned = pow.getNumberOfTypeSummoned(Tags.NATURIA); }
		if (natsSummoned > 0)
		{
			float mod = natsSummoned * 0.3f;
			int dmg = (int)(mod * this.amount);
			if (p.hasRelic(NatureOrb.ID)) { dmg = (int)(dmg * 1.2f); }
			if (p.hasPower(NaturiaForestPower.POWER_ID) && p.hasPower(StrengthPower.POWER_ID)) 
			{
				int amt = p.getPower(StrengthPower.POWER_ID).amount;
				float modi = (amt/20.0f) + 1.0f;
				dmg = (int)(dmg * modi);
			}
			this.amount2 = dmg;
		}
		else { this.amount2 = 0; }
		this.amount2 += DuelistMod.naturiaVinesDmgMod;
	}
	
	@Override
	public void onTribute(DuelistCard c, DuelistCard c2) { updateDescription(); }
	
	@Override
	public void onSummon(DuelistCard c, int amt) { updateDescription(); }

	@Override
	public void onResummon(DuelistCard card) {
		if (card.hasTag(Tags.NATURIA)) {
			gainVines();
		}
	}
	
	@Override
	public void onPlayCard(final AbstractCard card, final AbstractMonster m) {
		if (card.hasTag(Tags.NATURIA)) {
			gainVines();
		}
    }

	public void gainVines() {
		int amt = 1;
		if (AbstractDungeon.player.hasRelic(NaturiaRelic.ID)) {
			amt++;
		}
		AbstractPower power = Util.vinesPower(amt);
		if (power instanceof VinesPower) {
			Util.leavesVinesCommonOptionHandler(DuelistMod.vinesOption);
			this.amount += power.amount;
			for (AbstractPower pow : AbstractDungeon.player.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onGainVines(); }}
			for (AbstractRelic r : AbstractDungeon.player.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onGainVines(); }}
			updateDescription();
		} else if (power instanceof LeavesPower) {
			DuelistCard.applyPowerToSelf(power);
		}
	}
	
	private void dmgEnemies()
	{
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
		{
			if (!mon.isDead && !mon.isDying && !mon.isDeadOrEscaped() && !mon.halfDead)
			{
				if (mon.hasPower(ResistNatureEnemyPower.POWER_ID))
				{
					Util.log("Vines Power attacking target with resistance");
					float res = ((ResistNatureEnemyPower)mon.getPower(ResistNatureEnemyPower.POWER_ID)).calc();
					Util.log("Calculated modifier for vines damage: " + res);
					int dmg = (int) (this.amount2 * ((1.0f) - (res/10.0f)));
					if (dmg > 0) 
					{ 
						if (p.hasPower(NaturiaVeinPower.POWER_ID) && mon.hasPower(VulnerablePower.POWER_ID)) 
						{
							if (p.hasRelic(PaperFrog.ID)) { dmg = (int) (dmg * 1.75f); }
							else { dmg = (int) (dmg * 1.5f); }
							DuelistCard.vinesAttack(mon, dmg);
						}
						else { DuelistCard.vinesAttack(mon, dmg); }
						
					}
					else { Util.log("Vines power damage was 0 for " + mon.name); }
				}
				else
				{
					Util.log("Normal vines attack for " + mon.name);
					if (p.hasPower(NaturiaVeinPower.POWER_ID) && mon.hasPower(VulnerablePower.POWER_ID)) 
					{
						if (p.hasRelic(PaperFrog.ID)) { this.amount2 = (int) (this.amount2 * 1.75f); }
						else { this.amount2 = (int) (this.amount2 * 1.5f); }
						DuelistCard.vinesAttack(mon, this.amount2);
					}
					else { DuelistCard.vinesAttack(mon, this.amount2); }
				}
			}
		}
	}
}
