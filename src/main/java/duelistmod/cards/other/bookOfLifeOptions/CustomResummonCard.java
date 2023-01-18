package duelistmod.cards.other.bookOfLifeOptions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.characters.TheDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class CustomResummonCard extends DuelistCard 
{
    // TEXT DECLARATION
    private static final CardStrings cardStrings = getCardStrings();
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXT = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_CRC;
    private int noOfCards;
    private boolean randomCardChoice;
    private boolean randomTarg;
    private boolean targetAllEnemy;
    private CardTags restrictOptionsTag;
    private int locationIndex;
    // /STAT DECLARATION/
    
    // Just used to load the card into the game
    public CustomResummonCard()
    {
    	this(false, 0, 0, true, Tags.MONSTER, 0, true, true, true, false);
    	this.rawDescription = "Created by Book of Life";
    	this.initializeDescription();
    }

    public CustomResummonCard(boolean targetAllEnemy, int totalManaCost, int noOfCards, boolean randomCardChoice, CardTags restrictOptionsTag, int locationIndex, boolean randomTarget, boolean exh, boolean eth, boolean cardinal) 
    {
        super(getCARDID(), NAME, getIMG(), getCost(totalManaCost), getDesc(targetAllEnemy, noOfCards, randomCardChoice, restrictOptionsTag, locationIndex, randomTarget, exh, eth, cardinal), TYPE, COLOR, RARITY, getTarget(randomTarget, targetAllEnemy));
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
        if (cardinal) { this.tags.add(Tags.CARDINAL); }
        this.exhaust = exh;
        this.isEthereal = eth;
        this.noOfCards = noOfCards;
        this.locationIndex = locationIndex;
        this.randomCardChoice = randomCardChoice;
        this.restrictOptionsTag = restrictOptionsTag;
        this.randomTarg = randomTarget;
        this.targetAllEnemy = targetAllEnemy;
        this.baseMagicNumber = this.magicNumber = noOfCards;
    }
    
    @Override
	public AbstractCard makeStatEquivalentCopy()
	{
		AbstractCard card = super.makeStatEquivalentCopy();
		if (card instanceof CustomResummonCard)
		{
			CustomResummonCard dCard = (CustomResummonCard)card;
			if (this.hasTag(Tags.CARDINAL)) { dCard.tags.add(Tags.CARDINAL); }
			dCard.tributes = this.tributes;
			dCard.summons = this.summons;
			dCard.isTributesModified = this.isTributesModified;
			dCard.isSummonsModified = this.isSummonsModified;
			dCard.isTributesModifiedForTurn = this.isTributesModifiedForTurn;
			dCard.isMagicNumModifiedForTurn = this.isMagicNumModifiedForTurn;
			dCard.isSummonsModifiedForTurn = this.isSummonsModifiedForTurn;
			dCard.extraSummonsForThisTurn = this.extraSummonsForThisTurn;
			dCard.extraTributesForThisTurn = this.extraTributesForThisTurn;
			dCard.moreSummons = this.moreSummons;
			dCard.moreTributes = this.moreTributes;
			dCard.originalMagicNumber = this.originalMagicNumber;
			dCard.inDuelistBottle = this.inDuelistBottle;
			dCard.baseTributes = this.baseTributes;
			dCard.baseSummons = this.baseSummons;
			dCard.isSummonModPerm = this.isSummonModPerm;
			dCard.isTribModPerm = this.isTribModPerm;
			dCard.exhaust = this.exhaust;
			dCard.originalDescription = this.originalDescription;
	        dCard.isEthereal = this.isEthereal;
	        dCard.noOfCards = this.noOfCards;
	        dCard.locationIndex = this.locationIndex;
	        dCard.randomCardChoice = this.randomCardChoice;
	        dCard.restrictOptionsTag = this.restrictOptionsTag;
	        dCard.randomTarg = this.randomTarg;
	        dCard.targetAllEnemy = this.targetAllEnemy;
			ArrayList<CardTags> monsterTags = getAllMonsterTypes(this);
			dCard.tags.addAll(monsterTags);
			dCard.savedTypeMods = this.savedTypeMods;
			dCard.target = this.target;
			//dCard.baseDamage = this.baseDamage;
			if (this.hasTag(Tags.MEGATYPED))
			{
				dCard.tags.add(Tags.MEGATYPED);
			}
		}
		return card;
	}
    
    @Override
	public String onSave()
	{
		String saveAttributes = "";
		saveAttributes += this.hasTag(Tags.CARDINAL) + "~";
		saveAttributes += this.isEthereal + "~";
		saveAttributes += this.exhaust + "~";
		saveAttributes += this.randomTarg + "~";
		saveAttributes += this.targetAllEnemy + "~";
		saveAttributes += this.randomCardChoice + "~";
		saveAttributes += this.noOfCards + "~";
		saveAttributes += this.locationIndex + "~";
		saveAttributes += getTag(this.restrictOptionsTag) + "~";
		if (this.target.equals(CardTarget.ALL_ENEMY)) { saveAttributes += 0 + "~"; }
		else { saveAttributes += 1 + "~"; }
		saveAttributes += this.rawDescription;
		return saveAttributes;
	}
    
    public String saveString()
    {
    	String saveAttributes = "";
		saveAttributes += this.hasTag(Tags.CARDINAL) + "~";
		saveAttributes += this.isEthereal + "~";
		saveAttributes += this.exhaust + "~";
		saveAttributes += this.randomTarg + "~";
		saveAttributes += this.targetAllEnemy + "~";
		saveAttributes += this.randomCardChoice + "~";
		saveAttributes += this.noOfCards + "~";
		saveAttributes += this.locationIndex + "~";
		saveAttributes += getTag(this.restrictOptionsTag) + "~";
		if (this.target.equals(CardTarget.ALL_ENEMY)) { saveAttributes += 0 + "~"; }
		else { saveAttributes += 1 + "~"; }
		saveAttributes += this.rawDescription;
		return saveAttributes;
    }
    
    public void loadAttributes(String attributeString)
    {
    	// If no saved string, just return
		if (attributeString == null) { return; }
		
		// Otherwise, get the saved string and split it into components
		String[] savedStrings = attributeString.split("~");
		if (savedStrings.length < 11) { return; }
		boolean exh = false;
		boolean eth = false;
		boolean cardin = false;
		boolean randomCard = false;
		boolean randomTarg = false;
		if (savedStrings[0].equals("false") || savedStrings[0].equals("FALSE")) { cardin = false; }
		else { cardin = true; }
		if (savedStrings[1].equals("false") || savedStrings[1].equals("FALSE")) { eth = false; }
		else { eth = true; }
		if (savedStrings[2].equals("false") || savedStrings[2].equals("FALSE")) { exh = false; }
		else { exh = true; }
		if (savedStrings[3].equals("false") || savedStrings[3].equals("FALSE")) { randomTarg = false; }
		else { randomTarg = true; }
		if (savedStrings[4].equals("false") || savedStrings[4].equals("FALSE")) { targetAllEnemy = false; }
		else { targetAllEnemy = true; }
		if (savedStrings[5].equals("false") || savedStrings[5].equals("FALSE")) { randomCard = false; }
		else { randomCard = true; }
		int no = Integer.parseInt(savedStrings[6]);
		int loca = Integer.parseInt(savedStrings[7]);
		int tag = Integer.parseInt(savedStrings[8]);
		int target = Integer.parseInt(savedStrings[9]);
		String desc = savedStrings[10];
        if (cardin) { this.tags.add(Tags.CARDINAL); }
        this.exhaust = exh;
        this.isEthereal = eth;
        this.noOfCards = no;
        this.locationIndex = loca;
        this.randomCardChoice = randomCard;
        this.restrictOptionsTag = getTagFromSave(tag);
        this.randomTarg = randomTarg;
        this.baseMagicNumber = this.magicNumber = this.noOfCards;
        if (target == 0) { this.target = CardTarget.ALL_ENEMY; }
        else { this.target = CardTarget.ENEMY; }
        this.rawDescription = desc;
        this.initializeDescription();
    }
	
	@Override
	public void onLoad(String attributeString)
	{
		// If no saved string, just return
		if (attributeString == null) { return; }
		
		// Otherwise, get the saved string and split it into components
		String[] savedStrings = attributeString.split("~");
		if (savedStrings.length < 11) { return; }
		boolean exh = false;
		boolean eth = false;
		boolean cardin = false;
		boolean randomCard = false;
		boolean randomTarg = false;
		if (savedStrings[0].equals("false") || savedStrings[0].equals("FALSE")) { cardin = false; }
		else { cardin = true; }
		if (savedStrings[1].equals("false") || savedStrings[1].equals("FALSE")) { eth = false; }
		else { eth = true; }
		if (savedStrings[2].equals("false") || savedStrings[2].equals("FALSE")) { exh = false; }
		else { exh = true; }
		if (savedStrings[3].equals("false") || savedStrings[3].equals("FALSE")) { randomTarg = false; }
		else { randomTarg = true; }
		if (savedStrings[4].equals("false") || savedStrings[4].equals("FALSE")) { targetAllEnemy = false; }
		else { targetAllEnemy = true; }
		if (savedStrings[5].equals("false") || savedStrings[5].equals("FALSE")) { randomCard = false; }
		else { randomCard = true; }
		int no = Integer.parseInt(savedStrings[6]);
		int loca = Integer.parseInt(savedStrings[7]);
		int tag = Integer.parseInt(savedStrings[8]);
		int target = Integer.parseInt(savedStrings[9]);
		String desc = savedStrings[10];
        if (cardin) { this.tags.add(Tags.CARDINAL); }
        this.exhaust = exh;
        this.isEthereal = eth;
        this.noOfCards = no;
        this.locationIndex = loca;
        this.randomCardChoice = randomCard;
        this.restrictOptionsTag = getTagFromSave(tag);
        this.randomTarg = randomTarg;
        this.baseMagicNumber = this.magicNumber = this.noOfCards;
        if (target == 0) { this.target = CardTarget.ALL_ENEMY; }
        else { this.target = CardTarget.ENEMY; }
        this.rawDescription = desc;
        this.initializeDescription();
	}
	
	private static int getTag(CardTags restrict)
	{
		if (restrict == null) { return 0; }
		else if (restrict.equals(Tags.MONSTER)) { return 1; }
		else if (restrict.equals(Tags.ZOMBIE)) { return 2; }
		else if (restrict.equals(Tags.UNDEAD)) { return 3; }
		else if (restrict.equals(Tags.POSSESSED)) { return 4; }
		else if (restrict.equals(Tags.MAYAKASHI)) { return 5; }
		else if (restrict.equals(Tags.VAMPIRE)) { return 6; }
		else if (restrict.equals(Tags.SHIRANUI)) { return 7; }
		else if (restrict.equals(Tags.GHOSTRICK)) { return 8; }
		else if (restrict.equals(Tags.VENDREAD)) { return 9; }
		return 0;
	}
	
	private static CardTags getTagFromSave(int save)
	{
		if (save == 0) { return null; }
		else if (save == 1) { return Tags.MONSTER; }
		else if (save == 2) { return Tags.ZOMBIE; }
		else if (save == 3) { return Tags.UNDEAD; }
		else if (save == 4) { return Tags.POSSESSED; }
		else if (save == 5) { return Tags.MAYAKASHI; }
		else if (save == 6) { return Tags.VAMPIRE; }
		else if (save == 7) { return Tags.SHIRANUI; }
		else if (save == 8) { return Tags.GHOSTRICK; }
		else if (save == 9) { return Tags.VENDREAD; }
		return null;
	}
    
    public static CardTarget getTarget(boolean randomTarget, boolean targetAllEnemy)
    {
    	if (targetAllEnemy) { return CardTarget.ALL_ENEMY; }
    	if (!randomTarget) { return CardTarget.ENEMY; }
    	else { return CardTarget.ALL_ENEMY; }
    }
    
    public static int getCost(int cost)
    {
    	if (cost > -1) { return cost; }
    	else { return 0; }
    }
    
    public static String getDesc(boolean targetAllEnemy, int noOfCards, boolean randomCardChoice, CardTags restrict, int locationIndex, boolean randomTarg, boolean exh, boolean eth, boolean cardinal)
    {
    	String toRet = "";
    	
    	// Cardinal & Ethereal
    	if (cardinal) { toRet += EXT[26]; }
    	if (eth) { toRet += EXT[25]; }
    	
    	// "Resummon " + number of cards
    	if (randomCardChoice) { toRet += "Resummon " + noOfCards; }
    	
    	// "Resummon up to " + number of cards
    	else { toRet += EXT[0] + noOfCards; }
    	
    	
    	// If random target, add the word random before the type of card
    	if (randomCardChoice) { toRet += EXT[1]; }
    	
    	// Type of card
    	if (restrict == null)
    	{
    		if (noOfCards != 1) { toRet += EXT[3]; }
    		else { toRet += EXT[29]; }
    	}
    	else
    	{
    		if (noOfCards != 1)
    		{
	    		if (restrict.equals(Tags.MONSTER)) { toRet += EXT[2]; }
	    		else if (restrict.equals(Tags.ZOMBIE)) { toRet += EXT[4]; }
	    		else if (restrict.equals(Tags.UNDEAD)) { toRet += EXT[5]; }
	    		else if (restrict.equals(Tags.POSSESSED)) { toRet += EXT[6]; }
	    		else if (restrict.equals(Tags.MAYAKASHI)) { toRet += EXT[7]; }
	    		else if (restrict.equals(Tags.VAMPIRE)) { toRet += EXT[8]; }
	    		else if (restrict.equals(Tags.SHIRANUI)) { toRet += EXT[9]; }
	    		else if (restrict.equals(Tags.GHOSTRICK)) { toRet += EXT[10]; }
	    		else if (restrict.equals(Tags.VENDREAD)) { toRet += EXT[11]; }
    		}
    		else
    		{
    			if (restrict.equals(Tags.MONSTER)) { toRet += EXT[28]; }
        		else if (restrict.equals(Tags.ZOMBIE)) { toRet += EXT[30]; }
        		else if (restrict.equals(Tags.UNDEAD)) { toRet += EXT[31]; }
        		else if (restrict.equals(Tags.POSSESSED)) { toRet += EXT[32]; }
        		else if (restrict.equals(Tags.MAYAKASHI)) { toRet += EXT[33]; }
        		else if (restrict.equals(Tags.VAMPIRE)) { toRet += EXT[34]; }
        		else if (restrict.equals(Tags.SHIRANUI)) { toRet += EXT[35]; }
        		else if (restrict.equals(Tags.GHOSTRICK)) { toRet += EXT[36]; }
        		else if (restrict.equals(Tags.VENDREAD)) { toRet += EXT[37]; }
    		}
    	}
    	
    	// "from"
    	toRet += EXT[12];
    	
    	// Location of Resummoned cards
    	switch (locationIndex)
    	{
	    	case 0:
	    		toRet += EXT[13];
	    		break;
	    	case 1:
	    		toRet += EXT[14];
	    		break;
	    	case 2:
	    		toRet += EXT[15];
	    		break;
	    	case 3:
	    		toRet += EXT[16];
	    		break;
	    	case 4:
	    		toRet += EXT[17];
	    		break;
	    	case 5:
	    		toRet += EXT[18];
	    		break;
	    	case 6:
	    		toRet += EXT[19];
	    		break;
	    	case 7:
	    		toRet += EXT[20];
	    		break;
	    	case 8:
	    		toRet += EXT[21];
	    		break;
	    	case 9:
	    		toRet += EXT[22];
	    		break;
	    	default:
	    		break;
    	}
    	
    	// Random target or not
    	if (targetAllEnemy) { toRet += "on ALL enemies."; }
    	else if (randomTarg) { toRet += EXT[23]; }
    	else { toRet += EXT[24]; }
    	
    	// Exhaust
    	if (exh) { toRet += EXT[27]; }
    	
    	return toRet;
    }
    
    private ArrayList<AbstractCard> getGroup()
    {
    	ArrayList<AbstractCard> toRet = new ArrayList<>();
    	switch (locationIndex)
    	{
	    	case 0:
	    		if (player().hand.group.size() > 0) { for (AbstractCard c : player().hand.group) { if (!c.uuid.equals(this.uuid)) { toRet.add(c.makeStatEquivalentCopy()); }}}
	    		break;
	    	case 1:
	    		if (player().drawPile.group.size() > 0) { for (AbstractCard c : player().drawPile.group) { toRet.add(c.makeStatEquivalentCopy()); }}
	    		break;
	    	case 2:
	    		if (player().discardPile.group.size() > 0) { for (AbstractCard c : player().discardPile.group) { toRet.add(c.makeStatEquivalentCopy()); }}
	    		break;
	    	case 3:
	    		if (player().exhaustPile.group.size() > 0) { for (AbstractCard c : player().exhaustPile.group) { toRet.add(c.makeStatEquivalentCopy()); }}
	    		break;
	    	case 4:
	    		if (TheDuelist.resummonPile.group.size() > 0) { for (AbstractCard c : TheDuelist.resummonPile.group) { toRet.add(c.makeStatEquivalentCopy()); }}
	    		break;
	    	case 5:
	    		SummonPower pow = getSummonPower();
	    		if (pow != null) {
					for (AbstractCard c : pow.getCardsSummoned()) {
						toRet.add(c.makeStatEquivalentCopy());
					}
				}
	    		break;
	    	case 6:
	    		if (player().masterDeck.group.size() > 0) { for (AbstractCard c : player().masterDeck.group) { toRet.add(c.makeStatEquivalentCopy()); }}
	    		break;
	    	case 7:
	    		if (player().hand.group.size() > 0) { for (AbstractCard c : player().hand.group) { toRet.add(c.makeStatEquivalentCopy()); }}
	    		if (player().drawPile.group.size() > 0) { for (AbstractCard c : player().drawPile.group) { toRet.add(c.makeStatEquivalentCopy()); }}
	    		if (player().discardPile.group.size() > 0) { for (AbstractCard c : player().discardPile.group) { toRet.add(c.makeStatEquivalentCopy()); }}
	    		break;
	    	case 8:
	    		if (TheDuelist.cardPool.group.size() > 0) { for (AbstractCard c : TheDuelist.cardPool.group) { toRet.add(c.makeStatEquivalentCopy()); }}
	    		break;
	    	case 9:
	    		if (DuelistMod.myCards.size() > 0) { for (AbstractCard c : DuelistMod.myCards) { toRet.add(c.makeStatEquivalentCopy()); }}
	    		break;
	    	default:
	    		break;
    	}
    	return toRet;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> selectedPool = getGroup();
    	ArrayList<AbstractCard> cardsToResummon = new ArrayList<>();
    	
    	if (restrictOptionsTag != null)
    	{
    		for (AbstractCard c : selectedPool)
    		{
    			if (c.hasTag(restrictOptionsTag))
    			{
    				cardsToResummon.add(c.makeStatEquivalentCopy());
    			}
    		}
    	}
    	else
    	{
    		for (AbstractCard c : selectedPool) { cardsToResummon.add(c.makeStatEquivalentCopy()); }
    	}
    	
    	if (randomCardChoice)
    	{
	    	while (cardsToResummon.size() > this.magicNumber)
	    	{
	    		cardsToResummon.remove(AbstractDungeon.cardRandomRng.random(cardsToResummon.size() - 1));
	    	}
	    	
	    	for (AbstractCard c : cardsToResummon)
	    	{
	    		if (this.targetAllEnemy)
	    		{
	    			resummonOnAllEnemies(c, this.upgraded);
	    		}
	    		else if (this.randomTarg)
	    		{
	    			AbstractMonster mon = AbstractDungeon.getRandomMonster();
	    			if (mon != null) { resummon(c, mon, false, this.upgraded); }
	    		}
	    		else { resummon(c, m, false, this.upgraded); }
	    	}
    	}
    	
    	else if (cardsToResummon.size() > 0)
    	{
    		if (this.targetAllEnemy)
    		{
    			this.addToBot(new CardSelectScreenResummonAction(true, cardsToResummon, this.magicNumber));
    		}
    		else if (randomTarg)
    		{
    			this.addToBot(new CardSelectScreenResummonAction(cardsToResummon, this.magicNumber, null, true, true));
    		}
    		else
    		{
    			this.addToBot(new CardSelectScreenResummonAction(cardsToResummon, this.magicNumber, m, true, false));
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CustomResummonCard(this.targetAllEnemy, this.cost, this.noOfCards, this.randomCardChoice, this.restrictOptionsTag, this.locationIndex, this.randomTarg, this.exhaust, this.isEthereal, this.hasTag(Tags.CARDINAL));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(1);
            this.rawDescription = getDesc(this.targetAllEnemy, this.magicNumber, this.randomCardChoice, this.restrictOptionsTag, this.locationIndex, this.randomTarg, this.exhaust, this.isEthereal, this.hasTag(Tags.CARDINAL));
            this.initializeDescription(); 
        }
    }
    




	










	
	// AUTOSETUP - ID/IMG - Id, Img name, and class name all must match to use this
    public static String getCARDID()
    {
    	return DuelistMod.makeID(getCurClassName());
    }
    
	public static CardStrings getCardStrings()
    {
    	return CardCrawlGame.languagePack.getCardStrings(getCARDID());
    }
    
    public static String getIMG()
    {
    	return DuelistMod.makeCardPath(getCurClassName() + ".png");
    }
    
    public static String getCurClassName()
    {
    	return (new CurClassNameGetter()).getClassName();
    }

    public static class CurClassNameGetter extends SecurityManager{
    	public String getClassName(){
    		return getClassContext()[1].getSimpleName();
    	}
    }
    // END AUTOSETUP
}
