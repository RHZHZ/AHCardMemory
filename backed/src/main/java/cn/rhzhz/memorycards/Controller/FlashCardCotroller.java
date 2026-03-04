package cn.rhzhz.memorycards.Controller;


import cn.rhzhz.memorycards.Entity.Flashcard;
import cn.rhzhz.memorycards.Entity.memoryDel;
import cn.rhzhz.memorycards.Mapper.cardMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

//这个注解是会把所有数据渲染成json
@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST},
        allowedHeaders = {"Authorization", "Cache-Control", "Content-Type"})
@RestController
@RequestMapping("/card")
public class FlashCardCotroller {
    @Resource
    cardMapper mapper;
    @GetMapping("/getAll")
    public List<Flashcard> getAll(){

        return mapper.getAllCard();
    }

    @PostMapping("/search")
    public List<Flashcard> search(@RequestBody Map<String, String> category){
        String categorys = category.get("category");
        System.out.println("Received category: " + categorys);
        return mapper.search(categorys);
    }
    @PostMapping("/updatecard")
    public String updatecard(@RequestBody List<Flashcard> FlashcardGroup){
        try {
            // 调用服务层方法更新卡片
            for (Flashcard flashcard : FlashcardGroup) {
                System.out.println("更新前："+flashcard);
                Flashcard newcard=flashcard.delMemory(flashcard);
                System.out.println("更新后："+newcard);
                mapper.updateFlashcard(newcard);
                // 如果你需要获取更新后的Flashcard对象（通常不需要，因为只是更新），你可能需要再次查询数据库
                // updatedFlashcard = flashcardMapper.selectByPrimaryKey(flashcard.getId());
                // updatedFlashcards.add(updatedFlashcard);
            }
            // 返回更新状态，通常更新操作不返回实体列表
            return "Cards updated successfully";
        } catch (Exception e) {
            // 如果出现任何异常，返回错误响应
            return null;
        }
    }


    @PostMapping("/del")
    public String delCard(@RequestParam int id) {
        System.out.println("Received id: " + id);
        try {
            System.out.println("Received id: " + id);
            mapper.delCard(id);

            return "成功";
        } catch (Exception ex) {
            System.out.println(ex);
            return "失败";
        }
    }

    @PostMapping("/add")
    public String addCard(@RequestBody Flashcard flashcard) {
        // 添加 Flashcard 数据到数据库
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> data = restTemplate.getForObject("https://api.vvhan.com/api/wallpaper/views?type=json", Map.class);

            if (data != null && data.containsKey("url")) {
                flashcard.setImage(data.get("url"));
                System.out.println("图片get：" + data.get("url"));
                mapper.insertFlashcard(flashcard);
                return "成功";
            } else {
                return "失败";
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return "失败";
        }
    }
}
