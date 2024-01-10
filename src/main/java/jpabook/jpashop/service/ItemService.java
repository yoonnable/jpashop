package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional //(readOnly = false)
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 변경 감지 기능 사용 예시
    @Transactional
    public Item updateItem(Long itemId, Book bookItem) {
        Item findItem = itemRepository.findOne(itemId); // 영속 상태
        findItem.setPrice(bookItem.getPrice()); // 이렇게 데이터를 바꿔주면 자동으로 update된다.
        return findItem;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }
}
