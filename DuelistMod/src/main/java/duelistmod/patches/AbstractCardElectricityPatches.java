package duelistmod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.abstracts.*;

public class AbstractCardElectricityPatches
{
    @SpirePatch(clz = AbstractCard.class, method = "applyPowers")
    public static class ApplyPowersPatch
    {
        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance)
        {
        	applyPowersToMagicNumber(__instance);
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "calculateCardDamage")
    public static class CalculatePatch
    {
        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance, AbstractMonster mo)
        {
        	applyPowersToMagicNumber(__instance);
        }
    }
    
    private static void applyPowersToMagicNumber(AbstractCard c)
    {
    	boolean wasMagicTrueAlready = c.isMagicNumberModified;
		c.isMagicNumberModified = false;
		float tmp = (float)c.baseMagicNumber;
		for (final AbstractPower p : AbstractDungeon.player.powers) 
		{
			if (p instanceof DuelistPower)
			{
				DuelistPower pow = (DuelistPower)p;
				tmp = pow.modifyMagicNumber(tmp, c);
			}
		}
		
		for (final AbstractPotion p : AbstractDungeon.player.potions) 
		{
			if (p instanceof DuelistPotion)
			{
				DuelistPotion pow = (DuelistPotion)p;
				tmp = pow.modifyMagicNumber(tmp, c);
			}
		}
		
		for (final AbstractOrb p : AbstractDungeon.player.orbs) 
		{
			if (p instanceof DuelistOrb)
			{
				DuelistOrb pow = (DuelistOrb)p;
				tmp = pow.modifyMagicNumber(tmp, c);
			}
		}
		
		for (final AbstractRelic p : AbstractDungeon.player.relics) 
		{
			if (p instanceof DuelistRelic)
			{
				DuelistRelic pow = (DuelistRelic)p;
				tmp = pow.modifyMagicNumber(tmp, c);
			}
		}
		if (AbstractDungeon.player.stance instanceof DuelistStance)
		{
			DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
			tmp = stance.modifyMagicNumber(tmp, c);
		}
		if (c.magicNumber != MathUtils.floor(tmp) || wasMagicTrueAlready) 
		{
			c.isMagicNumberModified = true;
		}
		if (tmp < 0.0f) 
		{
			tmp = 0.0f;
		}
		c.magicNumber = MathUtils.floor(tmp);
    }
}
