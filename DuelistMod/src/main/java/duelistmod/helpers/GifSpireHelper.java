package duelistmod.helpers;

import java.util.ArrayList;

import GifTheSpire.util.GifAnimation;
import duelistmod.DuelistMod;

public class GifSpireHelper 
{
	// Animated Cards
	public static GifAnimation pharaohBlessing;
	public static GifAnimation attackMetronome;
	public static GifAnimation powerMetronome;
	public static GifAnimation rainbowMedicine;
	public static GifAnimation orbMetronome;	
	public static GifAnimation skillMetronome;	
	private ArrayList<GifAnimation> animations;
	private ArrayList<GifAnimation> pharaohAnimations;
	
	public GifSpireHelper()
	{
		pharaohBlessing = new GifAnimation(DuelistMod.makeAnimatedPath("PharaohBlessing.png"), 2, 11, 0, 0, 1, 1, false);
		skillMetronome = new GifAnimation(DuelistMod.makeAnimatedPath("SkillMetronome.png"), 8, 61, 0, 0, 1, 1, false);
		attackMetronome = new GifAnimation(DuelistMod.makeAnimatedPath("AttackMetronome.png"), 16, 14, 0, 0, 1, 1, false);
		powerMetronome = new GifAnimation(DuelistMod.makeAnimatedPath("PowerMetronome.png"), 16, 19, 0, 0, 1, 1, false);
		rainbowMedicine = new GifAnimation(DuelistMod.makeAnimatedPath("RainbowMedicine.png"), 10, 10, 0, 0, 1, 1, false);
		orbMetronome = new GifAnimation(DuelistMod.makeAnimatedPath("OrbMetronome.png"), 8, 11, 0, 0, 1, 1, false);
		animations = new ArrayList<GifAnimation>();
		pharaohAnimations = new ArrayList<GifAnimation>();
		animations.add(pharaohBlessing);
		animations.add(skillMetronome);
		animations.add(attackMetronome);
		animations.add(powerMetronome);
		animations.add(orbMetronome);
		animations.add(rainbowMedicine);
		for (GifAnimation g : this.pharaohAnimations) { this.animations.add(g); }
		setupCards();
		setFrameSpeeds();
	}
	
	private void setupCards()
	{
		for (GifAnimation g : this.animations) { g.create(); }
		pharaohBlessing.addAsCardAnimation("theDuelist:PharaohBlessing");
		rainbowMedicine.addAsCardAnimation("theDuelist:RainbowMedicine");
		orbMetronome.addAsCardAnimation("theDuelist:OrbMetronome");	
		attackMetronome.addAsCardAnimation("theDuelist:AttackMetronome");
		attackMetronome.addAsCardAnimation("theDuelist:UncommonAttackMetronome");
		attackMetronome.addAsCardAnimation("theDuelist:RareAttackMetronome");
		attackMetronome.addAsCardAnimation("theDuelist:AttackTrapMetronome");		
		powerMetronome.addAsCardAnimation("theDuelist:PowerMetronome");
		powerMetronome.addAsCardAnimation("theDuelist:RarePowerMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:Metronome");
		skillMetronome.addAsCardAnimation("theDuelist:SkillMetronome");
		skillMetronome.addAsCardAnimation("theDuelist:RareSkillMetronome");		
		skillMetronome.addAsCardAnimation("theDuelist:UncommonMetronome");		
		skillMetronome.addAsCardAnimation("theDuelist:TrapMetronome");
		skillMetronome.addAsCardAnimation("theDuelist:SpellMetronome");		
		skillMetronome.addAsCardAnimation("theDuelist:RareBlockMetronome");
		skillMetronome.addAsCardAnimation("theDuelist:BlockSpellMetronome");
		skillMetronome.addAsCardAnimation("theDuelist:BlockMetronome");		
		skillMetronome.addAsCardAnimation("theDuelist:AlloyedMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:AncientMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:AquaMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:ArcaneMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:ArcaneMonsterMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:ArcaneSpellMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:BugMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:CombatMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:CommonSpireMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:CyberMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:DeskbotMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:DinosaurMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:DragonMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:FieldMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:FiendMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:GemKnightMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:GhostrickMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:InsectMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:LabyrinthNightmareMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:LegendBlueEyesMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:MachineMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:MachineMonsterMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:MagnetMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:MaliciousMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:MayakashiMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:MegatypeMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:MetalRaidersMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:MonsterMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:NaturiaMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:OverflowMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:PelagicMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:PlantMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:PotMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:PredaplantMetronome");	
		attackMetronome.addAsCardAnimation("theDuelist:RareDragonMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:RareMonsterMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:RareNatureMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:RareSpireMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:RecklessMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:RockMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:RoseMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:ShiranuiMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:SpellcasterMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:SpiderMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:SpireMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:StampedingMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:StartingDeckMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:SuperheavyMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:ThalassicMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:TidalMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:TokenMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:UncommonMonsterMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:UncommonSpireMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:UndeadMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:VampireMetronome");	
		attackMetronome.addAsCardAnimation("theDuelist:WarriorMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:WyrmMetronome");	
		skillMetronome.addAsCardAnimation("theDuelist:ZombieMetronome");
	}
	
	private void setFrameSpeeds()
	{
		float defaultVal = 0.15f;
		attackMetronome.setAnimationspeed(defaultVal);
		powerMetronome.setAnimationspeed(defaultVal);
		skillMetronome.setAnimationspeed(defaultVal);
		rainbowMedicine.setAnimationspeed(0.65f);
		orbMetronome.setAnimationspeed(0.45f);	
	}
	
	public void hidePharaohAnimations(boolean set)
	{
		for (GifAnimation g : this.pharaohAnimations)
		{
			g.ishidden = set;
		}
	}
}
