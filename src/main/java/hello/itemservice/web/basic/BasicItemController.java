package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //를 쓰면 final이 붙은 애들 가지고 아래의 생성자를 만들어준다.
public class BasicItemController {

    private final ItemRepository itemRepository;

    //@Autowired //스프링에서 생성자가 하나면 생략 가능
    /*
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    } */
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}") //itemId만 들어오면 됨
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add") //add라는 같은 URL이어도 GET 방식이면 addForm으로 호출하고
    public String addForm() {
        return "basic/addForm";
    }

    //@PostMapping("/add") //같은 URL이어도 POST 방식이면 save을 호출하도록 하였다.
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {

        Item item = new Item();
        //          new Item(itemName, price, quantity) 써도 됨
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        //model에 item 담아서 상품 상세 페이지로 이동하기 위해서

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {

    /* ModelAttribute가 자동으로 만들어줌
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
    */

        itemRepository.save(item);

    /* ModelAttribute는 model에 넣어주는 기능도 수행
    대신 ModelAttribute() 괄호 안의 이름이 같아야 한다.
        model.addAttribute("item", item); //자동 추가, 생략 가능
    */

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
    /*
       @ModelAttribute("item")의 ("item")을 지우면(name을 직접 넣지 않으면)
       클래스명(Item)의 첫글자를 소문자로 바꿔서(item) ModelAttribute에 넣어준다.
       @ModelAttribute HelloData item 이라면
       helloData가 될 것이다.
    */
        itemRepository.save(item);

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV4(Item item) { //@ModelAttribute도 생략 가능
        itemRepository.save(item);
        return "basic/item";
    } //String int 같은 단순 타입들이 오면 RequestParam
    //Item같은 객체가 오면 ModelAttribute가 적용된다.

    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit") //edit도 GET방식, POST방식 2개로 나눠보자
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}"; //상품 상세로 완전히 처음부터 이동한 것
    }



    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
