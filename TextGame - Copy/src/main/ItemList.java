package main;

import records.ItemListRecord;

public class ItemList {
    private final ItemListRecord attributes;
    private final String itemName;
    private final int useItem;
    private final int itemPrice;

    public ItemList(ItemListRecord attributes){
        this.attributes = attributes;
        this.itemName = attributes.itemName();
        this.useItem = attributes.useItem();
        this.itemPrice = attributes.itemPrice();
    }
    public String getItemName(){return itemName;}
    public int getUseItem(){ return useItem;}
    public int getItemPrice(){ return itemPrice;}
}

class TimeStabilizer extends ItemList {
    public TimeStabilizer(){
        super(new ItemListRecord("Time Stabilizer (Health Potion)", 50, 50)); {}
    }
}

class QuantumElixir extends ItemList{
    public QuantumElixir(){
        super(new ItemListRecord("Quantum Elixir (Energy Potion)", 100, 40)); {}
    }
}

class PlasmaBurst extends ItemList{
    public PlasmaBurst(){
        super(new ItemListRecord("Plasma Burst (Fireball Spell)", 150, 100)); {}
    }
}

class IonBlade extends ItemList{
    public IonBlade(){
        super(new ItemListRecord("Ion Blade (Iron Sword)", 250, 200)); {}
    }
}

class NeutroniumArmor extends ItemList{
    public NeutroniumArmor(){
        super(new ItemListRecord("Neutronium Armor (Energy Beam & Skip Enemy Next Turn)", 200, 150)); {}
    }
}
