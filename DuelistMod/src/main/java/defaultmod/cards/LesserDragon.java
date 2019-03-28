package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class LesserDragon extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("LesserDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.LESSER_DRAGON);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.FIRE;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public LesserDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 4;
        this.upgradeDmg = 3;
        this.summons = 1;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.DRAGON);
        this.tags.add(DefaultMod.LEGEND_BLUE_EYES);
        this.tags.add(DefaultMod.GOOD_TRIB);
        this.misc = 0;
		this.originalName = this.name;
		this.isSummon = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	attack(m, AFX, this.damage);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new LesserDragon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(this.upgradeDmg);
            //this.upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		if (tributingCard.hasTag(DefaultMod.DRAGON) && !AbstractDungeon.player.hasPower(GravityAxePower.POWER_ID)) 
		{ 
			if (!AbstractDungeon.player.hasPower(MountainPower.POWER_ID)) { applyPowerToSelf(new StrengthPower(AbstractDungeon.player, 1)); }
			else { applyPowerToSelf(new StrengthPower(AbstractDungeon.player, 2)); }
		}
	}

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var)
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
    	attack(m, AFX, this.damage);
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
    	attack(m, AFX, this.damage);
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}