package main;
import records.DialogueRecord;

public class NihraDialogue {
    private final DialogueRecord[] nihraopening;
    private final DialogueRecord[] nihralevel1;
    private final DialogueRecord[] nihralevel2;
    private final DialogueRecord[] nihralevel3;

    public NihraDialogue() {
        nihraopening = new DialogueRecord[7];
        nihraopening[0] = new DialogueRecord("The Restorer settles into the ship's cockpit, his heart racing with a blend of hope and determination. For the first time in years, the path forward feels clear, a glimmer of purpose amidst the ruins of his memories.");
        nihraopening[1] = new DialogueRecord("He sets the course for Nihra Galaxy—a place he had once only heard rumors of. They had always said it was lost to the Fracture, overrun by creatures born from twisted machinery and lingering echoes of its shattered ecosystems.");
        nihraopening[2] = new DialogueRecord("As the ship enters Nihra's orbit, an eerie stillness blankets the galaxy. Far below, remnants of cities lie buried under ash and dust, their towers scorched and broken, a testament to the Fracture’s devastation.");
        nihraopening[3] = new DialogueRecord("The Restorer stepped cautiously through the empty corridors of Nihra, each step echoing through the silence, like a whisper from ancient graves.System Voice: \"Unregistered entity detected. Security protocols activating.\"");
        nihraopening[4] = new DialogueRecord("A faint smirk played across The Restorer's face. \"Looks like I'm not welcome here... Let's see what these guardians of Nihra have become.\"");
        nihraopening[5] = new DialogueRecord("System Voice: \"Deploying Wraith Drones for intruder elimination.\"");
        nihraopening[6] = new DialogueRecord("The Restorer  raises his weapon, ready for the encounter. \"One by one, I’ll cut through every last corrupted line in their circuits.\"");

        nihralevel1 = new DialogueRecord[6];
        nihralevel1[0] = new DialogueRecord("The Restorer's footsteps echoed through the remnants of Nihra's forsaken cities. The Wraith Drones had fallen, but a new, more dangerous presence loomed on the horizon.");
        nihralevel1[1] = new DialogueRecord("Suddenly, a deep voice rumbled through the broken streets. Commander Synthrax: \"You’ve done well to survive this long, but you will not leave this galaxy unscathed.\"");
        nihralevel1[2] = new DialogueRecord("The Restorer stopped, eyes scanning the skyline. A towering figure emerged from the shadows, its metal body gleaming with an unsettling precision. Commander Synthrax, the galaxy's fallen protector, now an embodiment of the Fracture's corruption.");
        nihralevel1[3] = new DialogueRecord("The Restorer’s voice was low but steady. \"I've seen enough of your kind. You’re just another machine that needs to be shut down.\"");
        nihralevel1[4] = new DialogueRecord("Commander Synthrax's voice was cold, mechanical. \"I am no mere machine, Restorer. I am the last of the true guardians. My purpose is to ensure Nihra’s survival, even if that means crushing everything that threatens it. Including you.\"");
        nihralevel1[5] = new DialogueRecord("With a roar, Commander Synthrax lunged forward, the ground trembling underfoot. The Restorer raised his weapon, ready for the fight of his life. \"Let’s see who’s left standing at the end of this.\"");

        nihralevel2 = new DialogueRecord[7];
        nihralevel2[0] = new DialogueRecord("The Restorer's journey had taken him deep into the heart of Nihra, a place where even the stars seem to avoid. The air was thick with the energy of something far older than the galaxy itself, a force lingering in the very core of the planet.");
        nihralevel2[1] = new DialogueRecord("He steps into a vast chamber, its walls pulsing with a faint, ominous glow. The floor beneath his feet hums with power, a steady vibration that echoes through his bones. This is no ordinary place—this is the heart of Nihra’s corruption.");
        nihralevel2[2] = new DialogueRecord("In the center of the chamber, a massive data terminal flickers with encrypted streams of information, the final piece he needs to complete the puzzle. But standing before it, a towering figure blocks the path, its form radiating a dark aura.");
        nihralevel2[3] = new DialogueRecord("From the shadows, a booming voice fills the chamber. Core Guardian: \"Restorer... I have awaited your arrival. You have disrupted the cycle, but you will never escape the fate set before you.\"");
        nihralevel2[4] = new DialogueRecord("The Restorer scans the room, eyes locking on the terminal. He knows this is the moment—the last piece of data needed to travel back through time and restore humanity. He steps forward, ready to face whatever comes next.");
        nihralevel2[5] = new DialogueRecord("The Core Guardian’s voice rumbles, filled with authority and malice. \"This data you seek is not for your hands. You are not worthy of the knowledge that lies within. It is my duty to ensure that only those who understand its true meaning can access it.\"");
        nihralevel2[6] = new DialogueRecord("The Restorer's expression hardens. \"The galaxy may have fallen, but I will restore humanity. Whatever you are guarding, it will not stop me. I need that data to undo the Fracture. This ends now.\"");

        nihralevel3 = new DialogueRecord[5];
        nihralevel3[0] = new DialogueRecord("With the Core Guardian vanquished, the chamber falls silent. The Restorer approaches a glowing terminal, its screen flickering to life. A hidden compartment slowly opens, revealing a small chip pulsing with strange energy.");
        nihralevel3[1] = new DialogueRecord("The Restorer carefully takes the chip, feeling the weight of the moment. As they insert it into their data cache, the chip pulses brighter, its energy now flowing through their system. A surge of data floods in, fragments of a forgotten truth.");
        nihralevel3[2] = new DialogueRecord("The data is fragmented, but the path remains unclear. Despite the victory, the Restorer finds only this single piece, one small part of a much larger puzzle.");
        nihralevel3[3] = new DialogueRecord("The Restorer spends some time searching deeper into the Nihra galaxy, scouring its ruins and forgotten corners, hoping to uncover more. But the galaxy offers no further answers, only silence and broken remnants of what once was.");
        nihralevel3[4] = new DialogueRecord("Frustrated but undeterred, the Restorer returns to their ship, knowing that the journey is far from over. The chip may be a vital clue, but many more worlds must be explored to find the remaining pieces of the truth.");
    }

    public DialogueRecord[] getNihraOpeningDialogue(){
        return nihraopening;
    }
    public DialogueRecord[] getNihralevel1Dialogue(){
        return nihralevel1;
    }
    public DialogueRecord[] getNihralevel2Dialogue(){
        return nihralevel2;
    }
    public DialogueRecord[] getNihralevel3Dialogue(){
        return nihralevel3;
    }
}