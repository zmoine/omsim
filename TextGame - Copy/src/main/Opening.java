package main;
import records.DialogueRecord;

public class Opening {
    private final DialogueRecord[] opening;

    public Opening() {
        opening = new DialogueRecord[13];
        opening[0] = new DialogueRecord("The Restorer drifts alone through the silent expanse, haunted by memories of the team he once had. They had come together, bound by a shared purpose to uncover the truth behind the Fracture. But now... it’s only him.\n");
        opening[1] = new DialogueRecord("Years ago, they set out as a united front, a team of explorers braving hostile landscapes and corrupted machines. One by one, his comrades fell, victims to the very remnants they sought to understand.\n");
        opening[2] = new DialogueRecord("Five years have passed since then. Five years of solitude, evading monsters born from broken technologies—machines twisted into unnatural, hostile forms. Their cries echo through his memories, a constant reminder of those he lost.\n");
        opening[3] = new DialogueRecord("Now, he finds himself before an object he could have never anticipated: a ship as large as a planet, drifting silently against the backdrop of empty stars. It’s a relic of a forgotten age, dormant yet ominous, as if guarding secrets as ancient as the galaxy itself.\n");
        opening[4] = new DialogueRecord("The Restorer's heart pounds as he enters the ship, its dark corridors stretching endlessly. He feels a strange familiarity in this place, as if the ship remembers him, calling him forward with whispers from the past.\n");
        opening[5] = new DialogueRecord("As he ventures deeper, he finds fragments of data, pieces of records scattered throughout the ship. The information is fractured, but one word keeps resurfacing: ‘The Fracture.’ He knows he's on the path to understanding.\n");
        opening[6] = new DialogueRecord("Among the data fragments, a holographic recording flickers to life. A voice, weary and broken, speaks of a 'cataclysmic experiment' gone wrong—a device meant to connect worlds but which tore apart reality itself.\n");
        opening[7] = new DialogueRecord("The Restorer listens, transfixed. He learns that this ship had been a research vessel, one of many designed to stabilize the galaxy's ecosystems. But something malfunctioned, triggering a chain reaction that corrupted the machines, turning them into monsters.\n");
        opening[8] = new DialogueRecord("Finally, he finds it—a blueprint of the device that caused the Fracture, along with a note: 'Only by journeying to the past can the Fracture be undone. But beware... the cost will be immense.'\n");
        opening[9] = new DialogueRecord("The Restorer grips the blueprint tightly. Here lies his chance, a way to go back, to stop the devastation before it began. But he knows the risk. Time itself is unstable, and venturing backward could unravel his own existence.\n");
        opening[10] = new DialogueRecord("He stands in silence, feeling the weight of the moment. His friends, his galaxy, the fractured civilizations—all of it rests on his shoulders now. 'I will finish what we started,' he whispers. 'I’ll go back. I’ll change everything.'\n");
        opening[11] = new DialogueRecord("With renewed purpose, he sets the coordinates, preparing to activate the ship’s long-dormant core. The past calls to him, a second chance to save what was lost.\n");
        opening[12] = new DialogueRecord("As the ship hums to life, the Restorer braces himself, knowing that his journey is far from over. He can feel the memories of his team beside him, urging him forward into the unknown, back to where it all began.\n");
    }

    public DialogueRecord[] getOpening(){
        return opening;
    }
}