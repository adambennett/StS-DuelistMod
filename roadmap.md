General:
- Fix basemod dev console application of all custom powers
- Full dynamic text block support for all card text

Main Menu:
- Move 'Play' for the Duelist into separate menu with more layers - decks, location (spire/kingdom/etc), settings, anything else
	- Add custom main menu option for special Duelist settings screen
	- Add stats screen inside special settings menu - cards seen, heart kills with each deck, etc.

Starter Decks:
- Give all decks 2 unlock criteria (score and something unique for each deck, triggering either should unlock deck)

Art/UI:
- Card pool relics and other in-run settings moved to a top panel option menu
- Improve sorting options for card pool screens (allow sort by monster/spell/trap, fix arrows on big card view screen, etc.)
    - Hook into Compendium Filters mod and add extra sorting options?
- In combat: Add enemy combat icons (like floating summon icon, graveyard) that enables right-click to view enemy duelist info
- Add tooltip card previews for any appropriate cards (for any card that specifically references another card or token, things like Messenger of Peace, etc.)
- Add unique Power icons for all custom powers
- Add unique Relic icons for all custom relics
- Add mid-combat icons for Bug/Spider counters, similar to Zombie sub-type counters (Mayakashi, etc)
- Add Pegasus, Joey, Mako Tsunami, Tristan, Bakura, Tea and other character models
- Tribute and Summon card icons
	- Create or subcontract out creation of icons similar to StSLib common keyword icons
	- Add config option in Visual settings to switch card text for new icons

Enemies:
- Add new duelist enemies
    - Orb monster
        - Scripted moves
        - Use duelist orbs to punish certain builds
    - Nob variations
        - Get stronger when you tribute/summon
        - Get stronger when you channel
    - Anti-Resummon enemy
        - Resists resummon damage
        - Gets stronger when you resummon
    - Mirror duelist
        - Plays your hand each turn
        - Determines the best outcome based on the moves available at the time
    - Elite Thief
        - Does more damage
        - Steals relics instead of gold (max of 2-3 per battle?)
        - Sticks around 1-2 more turns
        - Has more health
        - Blocks for higher amount
        - Bigger (visually)

Balance Changes:
- Give Rocks some unique effects/mechanics and more cards
- Make Duelist orbs work with Lock On

Card Pools:
- Properly setup all base game card additions for all pools
- Filter/update basic card set for each deck
- Completely Redesign: Standard, Toon, Warrior, Megatype, Increment, Ojama/Beast, Creator, Ascended I, Ascended II
- Moderately Rework: Spellcaster, Fiend, Plant, Insect, Metronome
- Design and Implement: Ascended III, Pharaoh I, Pharaoh II, Pharaoh III, Pharaoh IV, Pharaoh V

Bug Fixes
- Invert actions are fairly unstable and do not always work as expected - use via BaseMod console in tandem with channel command for obvious examples
- Character select screen with character level < 5: needs to show score progress instead of level progress under relics
- Properly rewrite 'attack multiple random enemies' functions (and anything similar, like Constrict multiple random enemies) as an action
- Fix 'canCancel' implementation issues, remove all instances of 'CancelCard' if possible
- Improve handling of The Courier relic to the point where it does not need to be removed from the relic pool
- Tribute and Summon modification logic
	- Check issues with Cyber Dragon cards that deal with modified tribute/energy cost
		- Cyber Dragon Core interaction

Config Menu Improvements
- Card specific configs
    - Add configs for all currently listed cards to modify percentages, add damage, etc
    - Add global card configs
    - Add a few more Token configs
    - Move Nameless Tomb card configs to event configs menu
- Orb specific configs
    - Add configs for some orbs to modify specific roll percentages
- Relic specific configs
    - Add configs for some Duelist relics to:
        - Modify relic values and percent chances
- Power specific configs
  - Modify power values, triggers, etc
- Stance specific configs
    - Add configs to modify stance values and percent chances
- Potion specific configs
    - Add configs for all Duelist potions to:
        - Modify specific potion values and percent chances
        - Add bonus damage to any damage potions
- Event specific configs
  - Add config to nameless tomb event to add exhaust to all red-border nameless tomb cards
- All config menu pages
	- Reset page to default
	- Randomize page settings
- General settings
	- Reset all config settings to default
	- Randomize all config settings
- Gameplay Settings
	- Fix 'Card Pool Relics' toggle
- Card Pool settings
	- Pool Fill settings: add "Default, but force 75 card limit"
- Metrics Settings
  - Implement 'View My Runs' button functionality

Cross-Mod Compatability
- Spire With Friends
    - Add Challenge Mode selector to lobby menu
    - Check for other possible supporting options that can be added
    - Check modes other than Co-Op to ensure starting deck is properly supported in any mode
- Intent graphs
  - Add support for enemy duelists
- Together in Spire
  - Check for compatibility and support starting decks similar to Spire with Friends if needed
- Block Reminders
  - Add support for various Duelist mechanics that grant Block at the end of turn
