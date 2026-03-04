package cn.rhzhz.memorycards.Entity;

import lombok.Data;

@Data
public class memoryDel extends Flashcard {
    private int id;
    private String image;
    private String category;
    private String content;
    private String remembered;
    private int chooseday;
    private int lastReview;
    private String startTime;


}
