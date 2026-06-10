package com.plurasight.sneakerdrops;

import com.plurasight.sneakerdrops.data.BrandRepository;
import com.plurasight.sneakerdrops.data.SneakerRepository;
import com.plurasight.sneakerdrops.models.Brand;
import com.plurasight.sneakerdrops.models.Sneaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class StartupRunner implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final SneakerRepository sneakerRepository;

    @Autowired
    public StartupRunner(BrandRepository brandRepository, SneakerRepository sneakerRepository) {
        this.brandRepository = brandRepository;
        this.sneakerRepository = sneakerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running){
            System.out.println("\n ===== Sneaker =====");
            System.out.println("1) List all sneakers");
            System.out.println("2) Search by model");
            System.out.println("3) Search by price");
            System.out.println("4) Search by year");
            System.out.println("5) Advanced Search");
            System.out.println("6) View by id");
            System.out.println("7) Add new sneaker");
            System.out.println("8) Update sneaker");
            System.out.println("9) Delete sneaker");
            System.out.println("0) Quit");
            System.out.print("Choose: ");

            switch (scanner.nextInt()){
                case 1 -> listSneaker();
                case 2 -> findByModel(scanner);
                case 3 -> findByPrice(scanner);
                case 4 -> findByYear(scanner);
                case 5 -> advancedSearch(scanner);
                case 6 -> viewById(scanner);
                case 7 -> addSneaker(scanner);
                case 8 -> updateSneaker(scanner);
                case 9 -> deleteSneaker(scanner);
                case 0 -> running = false;
                default -> System.out.println("Unknown option.");
            }
        }


    }

    private void addSneaker(Scanner scanner){
        scanner.nextLine();
        System.out.println("Model: ");
        String model = scanner.nextLine();
        System.out.println("Price: ");
        double price = scanner.nextDouble();
        System.out.println("Year: ");
        int year = scanner.nextInt();

        sneakerRepository.save(new Sneaker(model, price, year));
        System.out.println("Added!");
    }

    private void updateSneaker(Scanner scanner){
        System.out.println("Sneaker id: ");
        long id = scanner.nextLong();
        Sneaker sneaker = sneakerRepository.findById(id).orElseThrow(()-> new RuntimeException("No sneaker with id " + id));
        System.out.println("New Price: ");
        sneaker.setPrice(scanner.nextDouble());
        sneakerRepository.save(sneaker);
        System.out.println("Updated!");
    }

    private void deleteSneaker(Scanner scanner){
        System.out.println("Sneaker id: ");
        long id = scanner.nextLong();
        if(sneakerRepository.existsById(id)){
            sneakerRepository.deleteById(id);
            System.out.println("Deleted.");
        }else{
            System.out.println("No sneaker with that id.");
        }
    }



    private void advancedSearch(Scanner scanner){
        System.out.print("Maximum Price: ");
        double maxPrice = scanner.nextDouble();
        System.out.print("Minimum year: ");
        int minYear = scanner.nextInt();
        for(Sneaker s : sneakerRepository.findByMaxPriceAndMinYear(maxPrice, minYear)){
            System.out.println(s.getModel() + " (" + s.getPrice() + ", " + s.getReleaseYear() + ")");
        }
    }

    private void viewById(Scanner scanner){
        System.out.print("Sneaker id: ");
        long id = scanner.nextLong();
        Sneaker sneaker = sneakerRepository.findById(id).orElse(null);
        if(sneaker == null){
            System.out.println("No sneaker with that id.");
        }else{
            System.out.println(sneaker.getId() + " - " + sneaker.getModel() + " (" + sneaker.getPrice() + ")");
        }
    }

    private void listSneaker(){
        System.out.println("You have " + sneakerRepository.count() + " sneakers:");
        for (Sneaker s : sneakerRepository.findAll()){
            System.out.println(s.getId() + " - " + s.getModel() + " (" + s.getPrice() + ")");
        }
    }

    private void findByModel(Scanner scanner){
        scanner.nextLine();
        System.out.print("Model name: ");
        String text = scanner.nextLine();
        for(Sneaker s : sneakerRepository.findByModelContaining(text)){
            System.out.println(s.getModel() + " (" + s.getPrice() + ")");
        }
    }

    private void findByPrice(Scanner scanner){
        System.out.print("Max price: ");
        double price = scanner.nextDouble();
        for (Sneaker s : sneakerRepository.findByPriceLessThan(price)){
            System.out.println(s.getModel() + " (" + s.getPrice() + ")");
        }
    }

    private void findByYear(Scanner scanner){
        System.out.print("Year: ");
        int year = scanner.nextInt();
        for (Sneaker s : sneakerRepository.findByReleaseYear(year)){
            System.out.println(s.getModel() + " (" + s.getReleaseYear() + ")");
        }
    }

    private void seedData(){
        if(brandRepository.count() == 0){
            brandRepository.save(new Brand("Nike"));
            brandRepository.save(new Brand("Adidas"));
            brandRepository.save(new Brand("New Balance"));

        }

        if(sneakerRepository.count() == 0){
            sneakerRepository.save(new Sneaker("Air Jordan 1", 65, 1985));
            sneakerRepository.save(new Sneaker("Air Force 1", 90, 1982));
            sneakerRepository.save(new Sneaker("Yeezy Boost 250 V2", 220, 2016));
            sneakerRepository.save(new Sneaker("Adidas Superstar", 100, 1970));
            sneakerRepository.save(new Sneaker("New Balance 550", 110, 1989));
            sneakerRepository.save(new Sneaker("New Balance 990", 100, 1982));
        }
    }
}
