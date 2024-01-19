package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data 써도 되지만 예측하기 힘들다. 권장X
@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price; //가격이 null로 들어갈 가능성도 있음
    private Integer quantity; //수량이 null로 들어갈 가능성도 있음

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
