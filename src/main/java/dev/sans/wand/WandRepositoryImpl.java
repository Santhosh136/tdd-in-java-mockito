package dev.sans.wand;

public class WandRepositoryImpl implements WandRepository {

    public WandRepositoryImpl() {
        System.out.println("Creating wand repo");
    }

    @Override
    public boolean save(Wand wand) {
        System.out.println("Adding wand to db");
        return true;
    }
}
