package com.mainsys.challenge.controller;

import com.mainsys.challenge.model.Sales;
import com.mainsys.challenge.model.user.User;
import com.mainsys.challenge.repo.SalesRepository;
import com.mainsys.challenge.repo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SalesController {

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/sales/{userId}")
    public List<Sales> getSalesForUser(@PathVariable long userId) {
        System.out.println("Get Sales for user");

        List<Sales> sales = new ArrayList<>();
        salesRepository.findByUserId(userId).forEach(sales::add);
        return sales;
    }

    @GetMapping("/sales/year/{date}")
    public List<Sales> getSalesByDate(@PathVariable Date date) {
        System.out.println("Get Sales for year : "+date.toString());
        List<Sales> sales = new ArrayList<>();
        salesRepository.findByDate(date).forEach(sales::add);
        return sales;
    }

    @PostMapping("/sales/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Sales> createSales(@RequestBody Sales sale) {
        System.out.println(sale);
        if (sale.getAllParamsToCheck().containsValue(null)) {
            throw new IllegalArgumentException(
                    sale.getAllParamsToCheck().entrySet()
                            .stream()
                            .filter(param -> Objects.equals(param.getValue(), null))
                            .map(Map.Entry::getKey)
                            .findAny()+" can't get NULL !"
            );
        }
        User user = userRepository.findById(sale.getUser().getId()).get();
        double userProfit = user.getTotalProfit();
        userProfit += sale.getProductProfit();
        user.setTotalProfit(userProfit);
        userRepository.save(user);
        Sales saveSale = new Sales(
                sale.getProductName(),
                sale.getProductProfit(),
                sale.getDate(),
                user
        );
        Sales salesSaved = salesRepository.save(saveSale);
        return new ResponseEntity<>(salesSaved, HttpStatus.OK);
    }
    @DeleteMapping("/sales/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteSales(@PathVariable("id") long id) {
        System.out.println("Delete Sale with ID = "+ id + "...");
        User userToUpdate = salesRepository.findById(id).get().getUser();
        double userProfitLoss = userToUpdate.getTotalProfit();
        userProfitLoss -= salesRepository.findById(id).get().getProductProfit();
        userToUpdate.setTotalProfit(userProfitLoss);
        userRepository.save(userToUpdate);
        salesRepository.deleteById(id);
        return new ResponseEntity<>("Sale has been deleted", HttpStatus.OK);
    }

    @PutMapping(value = "/sales/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Sales> updateSales(@RequestBody Sales sales) {
        System.out.println("Updates Sales with ID = "+ sales.getId()+"...");

        Sales updatedSale = salesRepository.findById(sales.getId()).get();
        updatedSale.setDate(sales.getDate());
        updatedSale.setProductName(sales.getProductName());
        double oldProfit = updatedSale.getProductProfit();
        double newProfit = sales.getProductProfit();
        if  (oldProfit != newProfit ) {
         User userToUpdateTotalProfit = userRepository.findByUsername(sales.getUser().getUsername()).get();
            double newUserProfit = userToUpdateTotalProfit.getTotalProfit();
            if (oldProfit > newProfit) {
              newUserProfit -=   (oldProfit - newProfit);
              userToUpdateTotalProfit.setTotalProfit(newUserProfit);
          } else {
              newUserProfit += (oldProfit - newProfit);
              userToUpdateTotalProfit.setTotalProfit(newUserProfit);
          }
            userRepository.save(userToUpdateTotalProfit);
      }
        updatedSale.setProductProfit((long) sales.getProductProfit());

        updatedSale.setUser(sales.getUser());
        return new ResponseEntity<>(
                salesRepository.save(updatedSale),
                HttpStatus.OK
        );
    }

}
