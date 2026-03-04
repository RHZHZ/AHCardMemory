package cn.rhzhz.memorycards.Entity;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class Flashcard {
    private int id;
    private String image;
    private String category;
    private String content;
    private String remembered;
    private int chooseday;
    private int lastReview;
    private String startTime;
    public Flashcard delMemory(Flashcard flashcard) {
        if(flashcard.getRemembered()=="true"){
            Flashcard cardNew=flashcard;
            cardNew.setChooseday(flashcard.getLastReview()*2);
            System.out.println("上次复习:"+cardNew.getLastReview()+"天前，"+"因为记住："+flashcard.getRemembered());
            System.out.println("下次复习："+cardNew.getChooseday()+"天后！");
            cardNew.setLastReview(flashcard.getLastReview()*2);
            cardNew.setRemembered("false");
            return cardNew;
        }
        Flashcard cardNew=flashcard;
        cardNew.setChooseday(1);
        System.out.println("上次复习:"+cardNew.getLastReview()+"天前，"+"因为记住："+flashcard.getRemembered());
        System.out.println("下次复习："+cardNew.getChooseday()+"天后！");
        cardNew.setLastReview(1);
        return cardNew;

    }
}