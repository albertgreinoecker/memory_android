package at.ac.htlinn.memorytest;


public class Card {
    private int value;
    private boolean visible;

    public Card(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
       /* if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;*/
        Card card = (Card) o;
        return value == card.value;
    }

    public boolean isVisible(){
        return visible;
    }
    public void setVisible(boolean visible){
        this.visible = visible;
    }
    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", visible=" + visible +
                '}';
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
