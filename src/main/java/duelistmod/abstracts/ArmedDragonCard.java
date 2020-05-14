package duelistmod.abstracts;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.cards.pools.dragons.*;

public abstract class ArmedDragonCard extends DuelistCard {

	public ArmedDragonCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE,
			CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
	}
	
	
	public static void armedProtectorLvlUp()
	{
		lvlUpDraw();
		lvlUpDiscard();
		lvlUpExhaust();
		lvlUpHand();
	}

	public static void lvlUpDraw()
	{
		for (int i = 0; i < AbstractDungeon.player.drawPile.group.size(); i++)
		{
			AbstractCard current = AbstractDungeon.player.drawPile.group.get(i);
			if (current instanceof ArmedDragon3)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon5();
				if (upgrade) { ad5.upgrade(); }
	            AbstractDungeon.player.drawPile.group.set(i, ad5);
			}
			
			else if (current instanceof ArmedDragon5)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon7();
				if (upgrade) { ad5.upgrade(); }
	            AbstractDungeon.player.drawPile.group.set(i, ad5);
			}
			
			else if (current instanceof ArmedDragon7)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon10();
				if (upgrade) { ad5.upgrade(); }
	            AbstractDungeon.player.drawPile.group.set(i, ad5);
			}
			else if (current instanceof ArmedDragon10)
			{
				current.upgrade();
			}
		}
	}
	
	public static void lvlUpDiscard()
	{
		for (int i = 0; i < AbstractDungeon.player.discardPile.group.size(); i++)
		{
			AbstractCard current = AbstractDungeon.player.discardPile.group.get(i);
			if (current instanceof ArmedDragon3)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon5();
				if (upgrade) { ad5.upgrade(); }
	            AbstractDungeon.player.discardPile.group.set(i, ad5);
			}
			
			else if (current instanceof ArmedDragon5)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon7();
				if (upgrade) { ad5.upgrade(); }
	            AbstractDungeon.player.discardPile.group.set(i, ad5);
			}
			
			else if (current instanceof ArmedDragon7)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon10();
				if (upgrade) { ad5.upgrade(); }
	            AbstractDungeon.player.discardPile.group.set(i, ad5);
			}
			else if (current instanceof ArmedDragon10)
			{
				current.upgrade();
			}
		}
	}
	
	public static void lvlUpHand()
	{
		for (int i = 0; i < AbstractDungeon.player.hand.group.size(); i++)
		{
			AbstractCard current = AbstractDungeon.player.hand.group.get(i);
			if (current instanceof ArmedDragon3)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon5();
				if (upgrade) { ad5.upgrade(); }
	            ad5.current_x = target.current_x;
	            ad5.current_y = target.current_y;
	            ad5.target_x = target.target_x;
	            ad5.target_y = target.target_y;
	            ad5.drawScale = 1.0f;
	            ad5.targetDrawScale = target.targetDrawScale;
	            ad5.angle = target.angle;
	            ad5.targetAngle = target.targetAngle;
	            ad5.superFlash(Color.WHITE.cpy());
	            AbstractDungeon.player.hand.group.set(i, ad5);
	            AbstractDungeon.player.hand.glowCheck();
			}
			
			else if (current instanceof ArmedDragon5)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon7();
				if (upgrade) { ad5.upgrade(); }
	            ad5.current_x = target.current_x;
	            ad5.current_y = target.current_y;
	            ad5.target_x = target.target_x;
	            ad5.target_y = target.target_y;
	            ad5.drawScale = 1.0f;
	            ad5.targetDrawScale = target.targetDrawScale;
	            ad5.angle = target.angle;
	            ad5.targetAngle = target.targetAngle;
	            ad5.superFlash(Color.WHITE.cpy());
	            AbstractDungeon.player.hand.group.set(i, ad5);
	            AbstractDungeon.player.hand.glowCheck();
			}
			
			else if (current instanceof ArmedDragon7)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon10();
				if (upgrade) { ad5.upgrade(); }
	            ad5.current_x = target.current_x;
	            ad5.current_y = target.current_y;
	            ad5.target_x = target.target_x;
	            ad5.target_y = target.target_y;
	            ad5.drawScale = 1.0f;
	            ad5.targetDrawScale = target.targetDrawScale;
	            ad5.angle = target.angle;
	            ad5.targetAngle = target.targetAngle;
	            ad5.superFlash(Color.WHITE.cpy());
	            AbstractDungeon.player.hand.group.set(i, ad5);
	            AbstractDungeon.player.hand.glowCheck();
			}
			else if (current instanceof ArmedDragon10)
			{
				current.upgrade();
			}
		}
	}
	
	public static void lvlUpExhaust()
	{
		for (int i = 0; i < AbstractDungeon.player.exhaustPile.group.size(); i++)
		{
			AbstractCard current = AbstractDungeon.player.exhaustPile.group.get(i);
			if (current instanceof ArmedDragon3)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon5();
				if (upgrade) { ad5.upgrade(); }
	            AbstractDungeon.player.exhaustPile.group.set(i, ad5);
			}
			
			else if (current instanceof ArmedDragon5)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon7();
				if (upgrade) { ad5.upgrade(); }
	            AbstractDungeon.player.exhaustPile.group.set(i, ad5);
			}
			
			else if (current instanceof ArmedDragon7)
			{
				AbstractCard target = current;
				boolean upgrade = target.upgraded;
				AbstractCard ad5 = new ArmedDragon10();
				if (upgrade) { ad5.upgrade(); }
	            AbstractDungeon.player.exhaustPile.group.set(i, ad5);
			}
			else if (current instanceof ArmedDragon10)
			{
				current.upgrade();
			}
		}
	}
	
	public void lvlUpNoExhaust()
	{
		if (this instanceof ArmedDragon5)
		{		
			AbstractCard ad = new ArmedDragon7();
			if (this.upgraded) { ad.upgrade(); }
			//this.addToBot(new WaitAction(1.0f));
			this.addToBot(new MakeTempCardInDiscardAction(ad, 1));
		}
		else if (this instanceof ArmedDragon7)
		{		
			AbstractCard ad = new ArmedDragon10();
			if (this.upgraded) { ad.upgrade(); }
			//this.addToBot(new WaitAction(1.0f));
			this.addToBot(new MakeTempCardInDiscardAction(ad, 1));
		}
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub

	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub

	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void upgrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void use(AbstractPlayer arg0, AbstractMonster arg1) {
		// TODO Auto-generated method stub

	}

}
