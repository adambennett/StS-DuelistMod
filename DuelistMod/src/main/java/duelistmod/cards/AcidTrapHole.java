package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;

public class AcidTrapHole extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("AcidTrapHole");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.ACID_TRAP);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public AcidTrapHole() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 5;
        this.baseDamage = this.damage = 0;
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.ALL);
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    
    	int monstersToPull = this.magicNumber;
    	ArrayList<AbstractCard> drawPile = player().drawPile.group;
    	ArrayList<AbstractCard> toDiscard = new ArrayList<AbstractCard>();
    	ArrayList<AbstractCard> randomMonsters = new ArrayList<AbstractCard>();
    	ArrayList<DuelistCard> drawPileMonsters = new ArrayList<DuelistCard>();
    	int damageTotal = 0;
    	
    	for (AbstractCard c : drawPile)
    	{
    		if (c.hasTag(Tags.MONSTER))
    		{
    			drawPileMonsters.add((DuelistCard)c);
    			if (DuelistMod.debug) { System.out.println("theDuelist:AcidTrapHole:use() ---> added " + c.originalName + " to drawPileMonsters"); }
    		}
    	}
    	
    	if (!(drawPileMonsters.size() > this.magicNumber))
    	{
    		monstersToPull = drawPileMonsters.size();
    		if (DuelistMod.debug) { System.out.println("theDuelist:AcidTrapHole:use() ---> monstersToPull set to drawPileMonsters.size()"); }
    	}
    	
    	for (int i = 0; i < monstersToPull; i++)
    	{
    		DuelistCard random = returnRandomFromArray(drawPileMonsters);
    		while (randomMonsters.contains(random)) { random = returnRandomFromArray(drawPileMonsters); }
    		randomMonsters.add(random);
    		if (DuelistMod.debug) { System.out.println("theDuelist:AcidTrapHole:use() ---> added " + random.originalName + " to randomMonsters"); }
    	}
    	
    	for (int i = 0; i < randomMonsters.size(); i++)
    	{
    		damageTotal += randomMonsters.get(i).baseDamage;
			toDiscard.add(randomMonsters.get(i)); 
			if (DuelistMod.debug) { System.out.println("theDuelist:AcidTrapHole:use() ---> added " + randomMonsters.get(i).originalName + " to toDiscard :: damageTotal increased by: " + randomMonsters.get(i).baseDamage); }
    	}

    	for (AbstractCard c : toDiscard)
    	{
    		AbstractDungeon.player.drawPile.moveToDiscardPile(c);
    		if (DuelistMod.debug) { System.out.println("theDuelist:AcidTrapHole:use() ---> moved " + c.originalName + " to discard pile"); }
        	   		
    	}
    	this.baseDamage = this.damage = damageTotal;
    	if (this.damage > 0) { attack(m, AFX, this.damage); }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new AcidTrapHole();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{

	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{

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