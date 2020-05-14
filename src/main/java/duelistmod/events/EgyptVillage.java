package duelistmod.events;

import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.cards.*;

public class EgyptVillage extends DuelistEvent {


    public static final String ID = DuelistMod.makeID("EgyptVillage");
    public static final String IMG = DuelistMod.makeEventPath("EgyptVillage.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
    private boolean alphaUpgrade = false;
    private boolean betaUpgrade = false;
    private boolean gammaUpgrade = false;
    private boolean valkUpgrade = false;
   // private boolean relicSelected = true;
   // private RelicSelectScreen relicSelectScreen;

    public EgyptVillage() {
        super(NAME, DESCRIPTIONS[0], IMG);
        //this.noCardsInRewards = true;
        
        boolean hasAlpha = false;
        boolean hasBeta = false;
        boolean hasGamma = false;
        boolean hasValk = false;
        
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
        {
        	if (c instanceof AlphaMagnet) { hasAlpha = true; if (c.upgraded) { this.alphaUpgrade = true; }}
        	if (c instanceof BetaMagnet)  { hasBeta = true; if (c.upgraded) { this.betaUpgrade = true; }}
        	if (c instanceof GammaMagnet) { hasGamma = true; if (c.upgraded) { this.gammaUpgrade = true; }}
        	if (c instanceof ValkMagnet)  { hasValk = true; if (c.upgraded) { this.valkUpgrade = true; }}
        }
        
        // Receive Alpha Electro - 0
        if (hasAlpha && !alphaUpgrade) { imageEventText.setDialogOption(OPTIONS[0], new AlphaElectro()); }
        else if (hasAlpha) { AbstractCard a = new AlphaElectro(); a.upgrade(); imageEventText.setDialogOption(OPTIONS[0], a);}
        else { imageEventText.setDialogOption(OPTIONS[6], true); }
        
        // Receive Beta Electro - 1
        if (hasBeta && !betaUpgrade)  { imageEventText.setDialogOption(OPTIONS[1], new BetaElectro()); }
        else if (hasBeta) { AbstractCard a = new BetaElectro(); a.upgrade(); imageEventText.setDialogOption(OPTIONS[1], a);}
        else { imageEventText.setDialogOption(OPTIONS[7], true); }
        
        // Receive Gamma Electro - 2
        if (hasGamma && !gammaUpgrade) { imageEventText.setDialogOption(OPTIONS[2], new GammaElectro()); }
        else if (hasGamma) { AbstractCard a = new GammaElectro(); a.upgrade(); imageEventText.setDialogOption(OPTIONS[2], a);}
        else { imageEventText.setDialogOption(OPTIONS[8], true); }
       
        // Receive Delta Magnet - 3
        imageEventText.setDialogOption(OPTIONS[3], new DeltaMagnet());
       
        // Receive Beserkion - 4
        if (hasValk && !valkUpgrade)  { imageEventText.setDialogOption(OPTIONS[4], new Berserkion());  }
        else if (hasValk) { AbstractCard a = new Berserkion(); a.upgrade(); imageEventText.setDialogOption(OPTIONS[4], a);}
        else { imageEventText.setDialogOption(OPTIONS[9], true); }
        
       
        // Leave - 6
        imageEventText.setDialogOption(OPTIONS[5]);
    }

    @Override
    protected void buttonEffect(int i) 
    {
    	AbstractCard holder = null;
        switch (screenNum) 
        {
            case 0:
            	switch (i) 
            	{
	            	// Alpha Electro
	            	case 0:
	            		this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		holder = new AlphaElectro();
	            		if (this.alphaUpgrade) { holder.upgrade(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(holder, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		List<String> cardList = new ArrayList<String>();
	            		cardList.add("Alpha Electromagnet Warrior");
	            		logMetric(NAME, "Alpha Electro", cardList, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
	            		screenNum = 1;
	            		break;
	
	            	// Beta Electro
	            	case 1:
	            		this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		holder = new BetaElectro();
	            		if (this.betaUpgrade) { holder.upgrade(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(holder, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		List<String> cardListB = new ArrayList<String>();
	            		cardListB.add("Beta Electromagnet Warrior");
	            		logMetric(NAME, "Beta Electro", cardListB, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
	            		screenNum = 1;
	            		break;
	
	            	// Gamma Electro
	            	case 2:
	            		this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		holder = new GammaElectro();
	            		if (this.gammaUpgrade) { holder.upgrade(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(holder, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		List<String> cardListC = new ArrayList<String>();
	            		cardListC.add("Gamma Electromagnet Warrior");
	            		logMetric(NAME, "Gamma Electro", cardListC, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
	            		screenNum = 1;
	            		break;
	
	            	// Delta Magnet
	            	case 3:
	            		this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new DeltaMagnet(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		List<String> cardListD = new ArrayList<String>();
	            		cardListD.add("Delta Electromagnet Warrior");
	            		logMetric(NAME, "Delta Electro", cardListD, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
	            		screenNum = 1;
	            		break;
	            	
	            	// Berserkion
	            	case 4:
	            		this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		holder = new Berserkion();
	            		if (this.valkUpgrade) { holder.upgrade(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(holder, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		List<String> cardListE = new ArrayList<String>();
	            		cardListE.add("Berserkion Electromagna");
	            		logMetric(NAME, "Berserkion", cardListE, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
	            		screenNum = 1;
	            		break;

	            	// Leave
	            	case 5:
	            		this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		logMetric(NAME, "Leave");
	            		screenNum = 1;
	            		break;
            	}
            	break;
            case 1:
                openMap();
                break;
        }
    }
}

