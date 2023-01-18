package duelistmod.cards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class LegendaryExodia extends DuelistCard 
{

    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("LegendaryExodia");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.LEGENDARY_EXODIA);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.BLUNT_HEAVY;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public LegendaryExodia() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.EXODIA);
        this.tags.add(Tags.EXODIA_DECK);
		this.exodiaDeckCopies = 1;
		this.setupStartingCopies();
        this.damage = this.baseDamage = 15;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 3;
        this.magicNumber = this.baseMagicNumber = 10;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
    	boolean foundExxod = false;
    	if (p.hasPower(SummonPower.POWER_ID))
    	{
    		SummonPower pow = (SummonPower)p.getPower(SummonPower.POWER_ID);
    		int exoi = pow.getNumberOfTypeSummoned(Tags.EXODIA_PIECE);
    		if (exoi > 0) { foundExxod = true; }
    	}
    	ArrayList<DuelistCard> tributeList = tribute(p, this.tributes, false, this);
    	int dmg = 0;
    	if (tributeList.size() > 0)
    	{
	    	for (DuelistCard c : tributeList)
	    	{
	    		if (c.hasTag(Tags.EXODIA_PIECE))
	    		{
	    			dmg += this.magicNumber;
	    		}
	    	}  	
    	}
    	if (foundExxod) { attack(m); }
    	if (dmg > 0) { specialAttack(m, AFX, dmg); }
    }

    @Override
    public void triggerOnGlowCheck() 
    {
    	super.triggerOnGlowCheck();
    	boolean foundExxod = false;
    	if (player().hasPower(SummonPower.POWER_ID))
    	{
    		SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
    		int exoi = pow.getNumberOfTypeSummoned(Tags.EXODIA_PIECE);
    		if (exoi > 0) { foundExxod = true; }
    	}
    	if (foundExxod)
    	{
    		this.glowColor = Color.GOLD;
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new LegendaryExodia();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.upgradeMagicNumber(5);
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
        }
    }
    













}
