package com.app.Controller;

import com.app.Entity.book;
import com.app.Entity.category;
import com.app.service.bookService;
import com.app.service.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping({"/home",""})
public class HomeController {


    @Autowired
    bookService bookService;
    @Autowired
    com.app.service.categoryService categoryService;


    @GetMapping({"/view",""})
    public String doGetHome(Model model,  @RequestParam(defaultValue = "0", value = "page",required = false) int page
        ){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(2, pageSize);
        Page<book> bookPage = bookService.findAll(pageable);
        List<book> bookList2 = bookPage.getContent();
        model.addAttribute("listBook2",bookList2);
        pageable = PageRequest.of(3, pageSize);
        bookPage = bookService.findAll(pageable);
        List<book> bookList3 = bookPage.getContent();
        model.addAttribute("listBook3",bookList3);
        String messNotificationLogin = (String) model.getAttribute("messNotificationLogin");
        if(messNotificationLogin != null){
            model.addAttribute("messNotificationLogin",messNotificationLogin);
        }
        addModel(model, page , -1);

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);

        return "index";
    }


    @GetMapping("/view/book")
    public String doGetBook(Model model,@RequestParam("book_id")Integer id){

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "index";
    }

    public Map<Integer,String> priceList(){
        Locale japanLocale = new Locale("ja", "JP");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(japanLocale);

        List<book> bookList = bookService.findAll();
        Map<Integer,String> bookMap = new HashMap<>();
        for(int i = 0 ; i < bookList.size() ;i++){
            book item = bookList.get(i);
            bookMap.put(i+1,currencyFormatter.format(item.getPrice()));
        }
        return bookMap;
    }
    public void addModel(Model model,Integer pageHome, Integer pageShop){
        int pageSize = 0;
        int page = 0 ;
        if(pageHome >= 0 ){
            pageSize =5;
        }else{
            pageSize =12;
        }
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<book> bookPage = bookService.findAll(pageable);
        List<book> bookList = bookPage.getContent();
        int totalPages = bookPage.getTotalPages();

        model.addAttribute("listBook",bookList);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);
        model.addAttribute("priceList",priceList());
    }

}
