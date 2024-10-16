package com.app.Controller;

import com.app.Entity.*;
import com.app.service.*;
import com.app.service.Impl.bill_bookServiceImpl;
import com.app.utils.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    cartService cartService;
    @Autowired
    SessionService sessionService;
    @Autowired
    bookService bookService;
    @Autowired
    accountService accountService;
    @Autowired
    billService billService;
    @Autowired
    com.app.service.categoryService categoryService;
    @Autowired
    bill_bookServiceImpl bill_bookService;

    Locale japanLocale = new Locale("ja", "JP");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(japanLocale);

    @GetMapping("/view")
    public  String getCart(Model model, RedirectAttributes redirectAttributes){
        Integer account_id = sessionService.get("accountId");
        if(account_id== null){
            account_id = 0 ;
        }
        account accountSessionId = accountService.findById(account_id).orElse(null);

        if(accountSessionId== null){
            System.out.println("model null: ");
            redirectAttributes.addFlashAttribute("messNotificationLogin","Vui lòng đăng nhập để đặt hàng");
            return "redirect:/home/view";
        }
        addModel(model,false);

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        Page<book> bookContentSeen  = bookService.findAll(PageRequest.of(1,5));
        model.addAttribute("bookViewed",bookContentSeen.getContent());
        return  "cart";
    }

    @GetMapping("/add")
    public  String addCart(Model model, @RequestParam("bookId") Integer id ,RedirectAttributes redirectAttributes
            ,@RequestParam(value = "quantity" ,required = false,defaultValue = "1") Integer quantity){
        Integer account_id = sessionService.get("accountId");
        if(account_id== null){
            account_id = 0 ;
        }
        Optional<book> bookadd = bookService.findById(id);
        account accountadd = accountService.findById(account_id).orElse(null);

        if(accountadd== null){
            System.out.println("model null: ");
            redirectAttributes.addFlashAttribute("messNotificationLogin","Vui lòng đăng nhập để đặt hàng");
            return "redirect:/home/view";
        }
        if(cartService.findMaxId() == null){
            cartService.addItem(new cart(1,quantity,bookadd.get(),accountadd));
        }else{
            cartService.addItem(new cart(cartService.findMaxId()+1,quantity,bookadd.get(),accountadd));
        }
        addModel(model,false);

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);

        Page<book> bookContentSeen  = bookService.findAll(PageRequest.of(1,5));
        model.addAttribute("bookViewed",bookContentSeen.getContent());
        return  "cart";
    }

    @GetMapping("/pay")
    public  String getPay(Model model){
        addModel(model,false);
        Optional<account> accountadd = accountService.findById(sessionService.get("accountId"));
        List<cart> cartList = cartService.findByAccount(accountadd.get());
        BigDecimal totalCart = new BigDecimal(0);

            for(cart cart : cartList){
                totalCart.add(cart.getTotalProduct());
            }

        model.addAttribute("accountAdd",accountadd.get());
        model.addAttribute("cartPayment",cartList);
        model.addAttribute("priceTotalCart",currencyFormatter.format(totalCart));
        addModel(model,false);

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return  "payment";
    }

    @PostMapping("/bill")
    public  String postPay(Model model,@RequestParam("address")String address
            ,@RequestParam("phone")String phone,@RequestParam("note")String note){

        Optional<account> accountadd = accountService.findById(sessionService.get("accountId"));

        BigDecimal totalCart = new BigDecimal(0);
        List<cart> cartList = cartService.findByAccount(accountadd.get());
        for(cart cart : cartList){
            totalCart= totalCart.add(cart.getTotalProduct());
        }

        bill billNew = new bill();
//        billNew.setId(billService.findIdMax()+1);
        billNew.setNote(note);
        billNew.setPhoneNumber(phone);
        billNew.setAccount(accountadd.get());
        billNew.setAddress(address);
        billNew.setInvoiceDate(new Date());
        billNew.setTotalAmount(totalCart);
        billService.save(billNew);
        List<book> addBook = new ArrayList<>();
        HashMap<Integer, Integer> quantityBook= new HashMap<>();
        for(cart cart : cartList){
            bill_detail bill_detail = new bill_detail();
            quantityBook.put(cart.getBook().getId(),cart.getQuantity());
            addBook.add(cart.getBook());
            bill_detail.setBill(billNew);
            bill_detail.setBook(cart.getBook());
            bill_detail.setQty(cart.getQuantity());
            bill_detail.setTotalProduct(cart.getBook().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
            bill_bookService.save(bill_detail);
            cartService.remove(cart.getBook(),accountadd.get());
        }

        billNew.setBook(addBook);

        addModel( model,true);
        model.addAttribute("bill",billNew);
        model.addAttribute("priceTotalCart1",totalCart);
        model.addAttribute("quantiyBook",quantityBook);

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return  "bill";
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeItem(Model model, @RequestParam("bookId") Integer bookId){
        Optional<account> accountadd = accountService.findById(sessionService.get("accountId"));
        Optional<book> bookdelete = bookService.findById(bookId);
        cartService.remove(bookdelete.get(),accountadd.get());
        return  ResponseEntity.ok("");
    }

    public void addModel(Model model,Boolean flag_isbill){
        Optional<account> accountadd = accountService.findById(sessionService.get("accountId"));
        List<cart> cartList = null;
        if(flag_isbill){
           cartList = cartService.findByAccount(accountadd.get());
        }else {
            cartList = cartService.findByAccount(accountadd.get());
        }
        BigDecimal totalCart = BigDecimal.ZERO;
            for(cart cart : cartList){
                System.out.println("-----> "+cart.getTotalProduct());
                totalCart=  totalCart.add(cart.getTotalProduct());
            }

        System.out.println("total cart: "+ totalCart);
        model.addAttribute("cartList",cartList);
        model.addAttribute("priceList",priceList());
        model.addAttribute("priceTotalProduct",priceTotalProduct());
        model.addAttribute("priceTotalCart",totalCart);
    }
    public Map<Integer,String> priceList(){

        List<book> bookList = bookService.findAll();
        Map<Integer,String> bookMap = new HashMap<>();
        for(int i = 0 ; i < bookList.size() ;i++){
            book item = bookList.get(i);
            bookMap.put(i+1,currencyFormatter.format(item.getPrice()));
        }
        return bookMap;
    }
    public Map<Integer,String> priceTotalProduct(){

        Optional<account> accountadd = accountService.findById(sessionService.get("accountId"));
        List<book> bookList = bookService.findAll();
        Map<Integer,String> bookMap = new HashMap<>();
        int i = 0;
        for(book item : bookList){
            cart cart = cartService.findByBookAndAccount(item,accountadd.get());
            if(cart == null){
                System.out.println("null rồi");
            }else{
                System.out.println("cart item: "+cart.getTotalProduct());
                bookMap.put(item.getId(), String.valueOf(cart.getTotalProduct()));
            }
        }
        return bookMap;
    }

}
