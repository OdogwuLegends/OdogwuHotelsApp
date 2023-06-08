package odogwuHotels.data.repositories;

public class Collect <E>{
    public void collect(E element){
        System.out.println(element);
    }

    public static void main(String[] args) {
        Collect<Collect> el = new Collect<>();

        el.collect(new Collect());
    }
}