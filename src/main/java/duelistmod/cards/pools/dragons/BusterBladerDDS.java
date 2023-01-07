package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DynamicDamageCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class BusterBladerDDS extends DynamicDamageCard {
    public static final String ID = DuelistMod.makeID("BusterBladerDDS");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BusterBladerDDS.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;

    public BusterBladerDDS() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = this.originalDamage = 16;
        this.magicNumber = this.baseMagicNumber = 6;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WARRIOR);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	attack(m, AttackEffect.SLASH_HORIZONTAL, this.damage);
		tribute(p, this.tributes, false, this);
    }

	@Override
	public int damageFunction() {
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			int dragons = pow.getNumberOfTypeSummonedForTributes(Tags.DRAGON, this.tributes);
			return this.magicNumber * dragons;
		}
		return 0;
	}

    @Override
    public AbstractCard makeCopy() {
        return new BusterBladerDDS();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (DuelistMod.hasUpgradeBuffRelic) {
				this.upgradeDamage(4);
			}
            this.upgradeMagicNumber(2);
			this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

	@Override
	public String getID() { return ID; }

	public void onTribute(DuelistCard tributingCard) {}
	public void onResummon(int summons) {}
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
