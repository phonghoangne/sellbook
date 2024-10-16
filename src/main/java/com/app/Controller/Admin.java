package com.app.Controller;

import com.app.Entity.*;
import com.app.service.*;
import com.app.service.Impl.bill_bookServiceImpl;
import com.app.utils.SaveFileUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class Admin {
    @Autowired
    categoryService categoryService;
    @Autowired
    bookService bookService;
    @Autowired
    billService billService;
    @Autowired
    accountService accountService;
    @Autowired
    cartService cartService;
    @Autowired
    bill_bookServiceImpl bill_bookService;
    @GetMapping("")
    public  String getAdmin(Model model){
        return "curdHome";
    }

    @GetMapping("/account")
    public  String getAdminAccount(Model model,@RequestParam(defaultValue = "0", value = "page",required = false) int page){
        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<account> accountPage = accountService.findAll(pageable);
        List<account> accountList = accountPage.getContent();
        int totalPages = accountPage.getTotalPages();
        model.addAttribute("accountList",accountList);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);
        model.addAttribute("editAccount",new account());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdAccount";
    }

    @GetMapping("/account/edit")
    public  String getAdminEditAccount(Model model,@RequestParam(defaultValue = "0", value = "page",required = false) int page,
                                       @RequestParam("id")Integer id){
        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<account> accountPage = accountService.findAll(pageable);
        List<account> accountList = accountPage.getContent();
        int totalPages = accountPage.getTotalPages();
        Optional<account> editAccount = accountService.findById(id);

        model.addAttribute("accountList",accountList);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);

        model.addAttribute("editAccount",editAccount.get());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdAccount";
    }

    @PostMapping("/account/add")
    public String postAdminAccount(Model model,@ModelAttribute("editAccount") account accountRegister
            , @RequestParam(defaultValue = "0", value = "page",required = false) int page){
        System.out.println("account: "+accountRegister.toString());
        accountService.save(accountRegister);
        model.addAttribute("success","lưu thành công");
        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<account> accountPage = accountService.findAll(pageable);
        List<account> accountList = accountPage.getContent();
        int totalPages = accountPage.getTotalPages();

        model.addAttribute("accountList",accountList);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);

        model.addAttribute("editAccount",new account());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdAccount";
    }

    @GetMapping("/account/remove")
    public String removedminAccount(Model model,@RequestParam("accountId") Integer accountId
            , @RequestParam(defaultValue = "0", value = "page",required = false) int page){
        account ac = accountService.findById(accountId).get();
        accountService.delete(ac);
        model.addAttribute("success","xoá thành công");
        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<account> accountPage = accountService.findAll(pageable);
        List<account> accountList = accountPage.getContent();
        int totalPages = accountPage.getTotalPages();

        model.addAttribute("accountList",accountList);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);

        model.addAttribute("editAccount",new account());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdAccount";
    }
    //book
    @GetMapping("/book")
    public  String getAdminBook(Model model, @RequestParam(defaultValue = "0", value = "page",required = false) int page){
        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<book> bookPage = bookService.findAll(pageable);
        List<book> bookList = bookPage.getContent();
        int totalPages = bookPage.getTotalPages();
        List<category> categoryList = categoryService.findAll();


        model.addAttribute("categoryList",categoryList);
        model.addAttribute("listBook",bookList);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);
        model.addAttribute("editBook",new book());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdBook";
    }

    @GetMapping("/book/edit")
    public  String getAdminBook(Model model, @RequestParam(defaultValue = "0", value = "page",required = false) int page,@RequestParam("id")Integer id
    ,MultipartFile file){

        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<book> bookPage = bookService.findAll(pageable);
        List<book> bookList = bookPage.getContent();
        int totalPages = bookPage.getTotalPages();
        List<category> categoryList = categoryService.findAll();
        Optional<book> editbook = bookService.findById(id);
        System.out.println("month: "+editbook.get().getPublicationDate());
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("listBook",bookList);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);
        model.addAttribute("editBook",editbook.get());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdBook";
    }
    @PostMapping("/book/save")
    public String save(Model model, @ModelAttribute("editBook") book editBook,@RequestParam(value = "file",required = false) MultipartFile file){
        try {
            Path pathUpload = Path.of("src/uploads");
            if(!file.isEmpty()){
                SaveFileUntil.save(file,pathUpload);
                editBook.setImg(file.getOriginalFilename());
            }
            bookService.save(editBook);
            model.addAttribute("messageSave","Save success");
            model.addAttribute("editBook",new book());
        }catch (Exception e ){
            e.printStackTrace();
        }

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdBook";
    }
    @GetMapping("/book/delete")
    public  String deleteAdminBook(Model model, @RequestParam(defaultValue = "0", value = "page",required = false) int page,@RequestParam("id")Integer id){
        book bookdelete = bookService.findById(id).get();
        bookService.delete(bookdelete);

        model.addAttribute("success","xoá thành công");
        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<book> bookPage = bookService.findAll(pageable);
        List<book> bookList = bookPage.getContent();
        int totalPages = bookPage.getTotalPages();
        List<category> categoryList = categoryService.findAll();

        model.addAttribute("categoryList",categoryList);
        model.addAttribute("listBook",bookList);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);
        model.addAttribute("editBook",new book());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdBook";
    }

    @GetMapping("/category")
    public  String getAdminCategory(Model model){
        List<category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("editCategory",new category());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdCategory";
    }

    @GetMapping("/category/edit")
    public  String getAdminCategory(Model model,@RequestParam("id") Integer id){
        List<category> categoryList = categoryService.findAll();
        Optional<category> editCategory = categoryService.findById(id);

        model.addAttribute("categoryList",categoryList);
        model.addAttribute("editCategory",editCategory.get());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdCategory";
    }

    @PostMapping("/category/edit")
    public  String postAdminCategory(Model model,@ModelAttribute("editCategory") category editCategory){
        List<category> categoryList = categoryService.findAll();
//        category check = categoryService.findById(cate)
        categoryService.save(editCategory);
        model.addAttribute("success","save thành công");
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("editCategory",new category());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdCategory";
    }


    @GetMapping("/bill")
    public  String getAdminBill(Model model,@RequestParam(defaultValue = "0", value = "page",required = false) int page){
        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<bill> billPage = billService.findAll(pageable);
        List<bill> billList = billPage.getContent();
        int totalPages = billPage.getTotalPages();
        model.addAttribute("listBill",billList);
        System.out.println("total: "+ totalPages);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);
        model.addAttribute("billEdit",new bill());
        Map<Integer,List<cart>>  mapBillCart = new HashMap<>();
        System.out.println("cart1"+billList.get(0).toString());


        model.addAttribute("billCart",mapBillCart);

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdBill";
    }


    @GetMapping("/view/bill")
    public String viewBill(Model model,@RequestParam(value="id",required = false,defaultValue = "1") Integer billId,@RequestParam(defaultValue = "0", value = "page",required = false) int page){
        bill b = billService.findById(billId);
        List<bill_detail> billBooks = bill_bookService.findAllByBill(b);
        List<book> bookBill = new ArrayList<>();
        BigDecimal totalBill = BigDecimal.ZERO;
        for(bill_detail item : billBooks){
            totalBill = totalBill.add(item.getTotalProduct());
            bookBill.add(item.getBook());
        }

        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<bill> billPage = billService.findAll(pageable);
        List<bill> billList = billPage.getContent();
        int totalPages = billPage.getTotalPages();
        model.addAttribute("listBill",billList);
        System.out.println("total: "+ totalPages);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",page);
        model.addAttribute("bill_book", bookBill);
        model.addAttribute("billView",billBooks.get(0));
        model.addAttribute("ListbillView",billBooks);
        model.addAttribute("totalBill",totalBill);

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "curdBill";
    }

}
