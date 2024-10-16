package com.app.Controller;

import com.app.Entity.book;
import com.app.Entity.category;
import com.app.service.bookService;
import com.app.service.categoryService;
import com.app.utils.SaveFileUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.NumberFormat;
import java.util.*;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    bookService bookService;
    @Autowired
    categoryService categoryService;


    SaveFileUntil saveFileUntil;

    @GetMapping("/shop")
    public String ViewListBook(Model model,  @RequestParam(defaultValue = "0", value = "page",required = false) int page){
        int pageSize = 12;
        addModel(model,pageSize,page,0,null);
        model.addAttribute("categoryId",0);

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "shop";
    }

    @GetMapping("/bookid")
    public String GetOneBook(Model model, @RequestParam("id")Integer bookID){
        Optional<book> bookSearch = bookService.findById(bookID);
        Map<Integer,String> priceMap = new HashMap<>();
        priceMap = priceList();
        model.addAttribute("priceList",priceMap);
        if(bookSearch.isPresent()){
            model.addAttribute("searchBook",bookSearch.get());
        }else {
        }
        Page<book> bookContentSeen  = bookService.findAll(PageRequest.of(1,5));
        Page<book> similarCategory = bookService.findByCategory(PageRequest.of(1,6),bookSearch.get().getCategory());
        model.addAttribute("bookViewed",bookContentSeen.getContent());
        model.addAttribute("similarCategory",similarCategory.getContent());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "product2";
    }

    @GetMapping("/category")
    public  String getBookByCategory(Model model, @RequestParam("categoryId") Integer categoryId,@RequestParam(defaultValue = "0", value = "page",required = false) int page){
        int pageSize = 12;
        Optional<category> category_id = categoryService.findById(categoryId);
        model.addAttribute("category",category_id.get());
        model.addAttribute("categoryId",category_id.get().getId());

        addModel(model,pageSize,page,1,category_id.get());
        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "shop";
    }

    @GetMapping("/search")
    public String getSearch(Model model , @RequestParam("searchKey") String keySearch,@RequestParam(defaultValue = "0", value = "page",required = false) int page){
        System.out.println("search key "+keySearch);
        int pageSize = 12;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<book>bookListPage = bookService.findBysearchName(pageable,keySearch);
        List<book>bookList  = bookListPage.getContent();
        int totalPages = bookListPage.getTotalPages();
        if(bookList.size() <1){
            model.addAttribute("resultSearch","không tìm thấy sách: "+ keySearch);
        }
        model.addAttribute("listBook",bookList);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);
        List<category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("searchKey",keySearch);

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "shop";
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

    public void addModel(Model model,Integer pageSize,int page,int TypePage,category CategoryId){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<book> bookPage;

        if(TypePage == 0 ){
           bookPage = bookService.findAll(pageable);
        }else{
            bookPage = bookService.findByCategory(pageable,CategoryId);
        }
        Page<book> bookContentSeen  = bookService.findAll(PageRequest.of(1,5));
        List<book> bookList = bookPage.getContent();
        int totalPages = bookPage.getTotalPages();

        List<category> categoryList = categoryService.findAll();
        model.addAttribute("bookViewed",bookContentSeen.getContent());
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("listBook",bookList);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);
        model.addAttribute("priceList",priceList());
    }
}
