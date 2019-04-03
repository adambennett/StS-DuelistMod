package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.orbCards.*;
import duelistmod.patches.*;

public class BadToken extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("BadToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GREEDPOT_AVATAR);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public BadToken() { super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); }
    public BadToken(String tokenName) { super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); }
    
    
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Remove all player powers
    	//ArrayList<AbstractPower> test = p.powers; for (AbstractPower a : test) { removePower(a, p);  }    	
    	/*
    	for (int i = 0; i < 6; i++)
    	{
    		// Channel test orb
    		RandomOrbHelperDebug.channelRandomOrb();
    	}
    	*/    	
    	//RandomOrbHelperDebug.channelRandomOrb();    	
    	//applyPowerToSelf(new ObliteratePower(p, p));
    	//applyPowerToSelf(new ExodiaRenewalPower(p, p));    	
    	// Channel test orb
		//AbstractOrb testOrb = new Air();
		//channel(testOrb);    	
    	//RandomEffectsHelper.addFromRandomSetToHand();
    	//DuelistCard blueEyesA = new BlueEyes();
    	//String be = blueEyesA.originalName;
    	//addMonsterToHandModTributes(be, -blueEyesA.tributes, true);
    	//AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(blueEyesA, -blueEyesA.tributes, true));
    	//AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(blueEyesA));    	
    	//DuelistCard blueEyesB = new RedEyes();
    	//String re = blueEyesB.originalName;
    	//addMonsterToHandModTributes(re, -blueEyesB.tributes, false);    	
    	//AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(blueEyesB, -blueEyesB.tributes, false));
    	//AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(blueEyesB));
    	
    	
    	ArrayList<DuelistCard> orbCards = new ArrayList<DuelistCard>();
    	orbCards.add(new AirOrbCard());
    	orbCards.add(new BlackOrbCard());
    	orbCards.add(new BufferOrbCard());
    	orbCards.add(new ConsumerOrbCard());
    	orbCards.add(new DragonOrbCard());
    	orbCards.add(new EarthOrbCard());
    	orbCards.add(new FireOrbCard());
    	orbCards.add(new GadgetOrbCard());
    	orbCards.add(new GateOrbCard());
    	orbCards.add(new GlitchOrbCard());
    	orbCards.add(new LavaOrbCard());
    	orbCards.add(new MetalOrbCard());
    	orbCards.add(new MillenniumOrbCard());
    	orbCards.add(new MistOrbCard());
    	orbCards.add(new MonsterOrbCard());
    	orbCards.add(new MudOrbCard());
    	orbCards.add(new ReducerOrbCard());
    	orbCards.add(new SandOrbCard());
    	orbCards.add(new ShadowOrbCard());
    	orbCards.add(new SmokeOrbCard());
    	orbCards.add(new SplashOrbCard());
    	orbCards.add(new StormOrbCard());
    	orbCards.add(new SummonerOrbCard());
    	ArrayList<DuelistCard> toHand = new ArrayList<DuelistCard>();
    	for (int i = 0; i < 4; i++)
    	{
    		DuelistCard randomOrbCard = orbCards.get(AbstractDungeon.cardRandomRng.random(orbCards.size() - 1));
    		while (toHand.contains(randomOrbCard))
    		{
    			randomOrbCard = orbCards.get(AbstractDungeon.cardRandomRng.random(orbCards.size() - 1));
    		}
    		toHand.add((DuelistCard) randomOrbCard.makeCopy());
    	}
    	
    	for (DuelistCard c : toHand)
    	{
    		if (c != null)
    		{
    			addCardToHand(c);
    		}
    	}
    	
    	DuelistCard.invertAll(1);
    }
   
    
    
    
    
    @Override public AbstractCard makeCopy() { return new BadToken(); }
	@Override public void onTribute(DuelistCard tributingCard) {}
	@Override public void onResummon(int summons) { }
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade() {}
	@Override
	public String getID() {
		return ID;
	}
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}